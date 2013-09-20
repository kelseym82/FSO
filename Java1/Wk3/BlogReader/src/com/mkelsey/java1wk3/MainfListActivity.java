package com.mkelsey.java1wk3;
//Michael Kelsey
//Fullsail Online - Java 1

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class MainfListActivity extends ListActivity {

	//Declared Variables
	protected String[] mBlogPostTitles;
	public static final int NUMBER_OF_POSTS = 20;
	public static final String TAG = MainfListActivity.class.getSimpleName();
	protected JSONObject mBlogData;
	protected ProgressBar mProgressBar;
	private final String KEY_TITLE = "title";
	private final String KEY_AUTHOR = "author";
			
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainf_list);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		
		if (isNetworkAvailable()){
			mProgressBar.setVisibility(View.VISIBLE);
			GetBlogPostsTask getBlogPostTask = new GetBlogPostsTask();
			getBlogPostTask.execute();
		}
		else {
			updateDisplayForError();
		}
		
		
		//Toast.makeText(this, getString(R.string.no_items), Toast.LENGTH_LONG).show();
	}
	
	
	//This is the onClickListener for the ListView.  It detects which item in the list view is selected.
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		JSONArray jsonPosts;
		try {
			jsonPosts = mBlogData.getJSONArray("posts");
			JSONObject jsonPost = jsonPosts.getJSONObject(position);
			String blogUrl = jsonPost.getString("url");
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(blogUrl));
			startActivity(intent);
		} catch (JSONException e) {
			logException(e);
		}
		
	}

	private boolean isNetworkAvailable() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		
		boolean isAvailable = false;
		if (networkInfo != null && networkInfo.isConnected()){
			isAvailable = true;
		}
		
		return isAvailable;
	}

	public void handleBlogResponse() {
		mProgressBar.setVisibility(View.INVISIBLE);
		
		if (mBlogData == null){
			updateDisplayForError();
		}
		else {
			try {
				//Log.d(TAG, mBlogData.toString(2));
				JSONArray jsonPosts = mBlogData.getJSONArray("posts");
				//This is a better way to use JSON Array.  Allows you to place multiple items without having to write more code.
				ArrayList<HashMap<String, String>> blogPosts = new ArrayList<HashMap<String, String>>();
				for (int i = 0; i < jsonPosts.length(); i++){
					JSONObject post = jsonPosts.getJSONObject(i);
					String title = post.getString(KEY_TITLE);
					title = Html.fromHtml(title).toString();
					String author = post.getString(KEY_AUTHOR);
					author = Html.fromHtml(author).toString();
					
					HashMap<String, String> blogPost = new HashMap<String, String>();
					blogPost.put(KEY_TITLE, title);
					blogPost.put(KEY_AUTHOR, author);
					
					blogPosts.add(blogPost);
				}
				
				String[] keys = {KEY_TITLE, KEY_AUTHOR};
				int[] ids = { android.R.id.text1, android.R.id.text2};
				SimpleAdapter adapter = new SimpleAdapter(this, blogPosts, android.R.layout.simple_list_item_2, keys, ids);
				
				setListAdapter(adapter);
					
			} 
			catch (JSONException e) {
				logException(e);
			}
		}
		
	}

	//Log exception method, used to save me time typing things out.
	private void logException(Exception e) {
		Log.e(TAG, "Exception Caught!", e);
	}
	
	
	//This is the pop up error message method used throughout the application
	private void updateDisplayForError() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getString(R.string.title));
		builder.setMessage(getString(R.string.error_message));
		builder.setPositiveButton(android.R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
		
		TextView emptyTextView = (TextView) getListView().getEmptyView();
		emptyTextView.setText(getString(R.string.no_items));
	}
	
	private class GetBlogPostsTask extends AsyncTask<Object, Void, JSONObject>{

		@Override
		protected JSONObject doInBackground(Object... arg0) {
			int responseCode = -1; 
			JSONObject jsonResponse = null;
			
			try {
				URL blogFeedURL = new URL("http://blog.teamtreehouse.com/api/get_recent_summary/?count=" + NUMBER_OF_POSTS);
				HttpURLConnection connection = (HttpURLConnection) blogFeedURL.openConnection();
				connection.connect();
				
				responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK){
					InputStream inputStream = connection.getInputStream();
					Reader reader = new InputStreamReader(inputStream);
					int contentLength = connection.getContentLength();
					char[] charArray = new char[contentLength];
					reader.read(charArray);
					String responseData = new String(charArray);

					jsonResponse = new JSONObject(responseData);
				} 
				else {
					Log.i(TAG, "Unsucessful HTTP Response Code: " + responseCode);
				}
				Log.i(TAG, "Code:" + responseCode);
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				logException(e);
				//e.printStackTrace();
			}
			catch (IOException e){
				logException(e);
			}
			catch (Exception e){
				logException(e);
			}
			
			return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(JSONObject result){
			mBlogData = result;
			handleBlogResponse();			
		}
	}

}
