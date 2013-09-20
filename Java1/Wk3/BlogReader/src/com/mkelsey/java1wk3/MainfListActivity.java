package com.mkelsey.java1wk3;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;

public class MainfListActivity extends ListActivity {

	protected String[] mBlogPostTitles;
	public static final int NUMBER_OF_POSTS = 20;
	public static final String TAG = MainfListActivity.class.getSimpleName();
	protected JSONObject mBlogData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainf_list);
		
		if (isNetworkAvailable()){
			GetBlogPostsTask getBlogPostTask = new GetBlogPostsTask();
			getBlogPostTask.execute();
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.title));
			builder.setMessage(getString(R.string.error_message));
			builder.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
		
		//Toast.makeText(this, getString(R.string.no_items), Toast.LENGTH_LONG).show();
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainf_list, menu);
		return true;
	}
	


	public void updateList() {
		if (mBlogData == null){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.title));
			builder.setMessage(getString(R.string.error_message));
			builder.setPositiveButton(android.R.string.ok, null);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		else {
			try {
				//Log.d(TAG, mBlogData.toString(2));
				JSONArray jsonPosts = mBlogData.getJSONArray("posts");
				mBlogPostTitles = new String[jsonPosts.length()];
				for (int i = 0; i < jsonPosts.length(); i++){
					JSONObject post = jsonPosts.getJSONObject(i);
					String title = post.getString("title");
					title = Html.fromHtml(title).toString();
					mBlogPostTitles[i] = title;
				}
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mBlogPostTitles);
				
				setListAdapter(adapter);
			} 
			catch (JSONException e) {
				Log.e(TAG, "Exception Caught: ", e);
			}
		}
		
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
				Log.e(TAG, "Exception caught: ", e);
				//e.printStackTrace();
			}
			catch (IOException e){
				Log.e(TAG, "Exception caught: ", e);
			}
			catch (Exception e){
				Log.e(TAG, "Exception caught: ", e);
			}
			
			return jsonResponse;
		}
		
		@Override
		protected void onPostExecute(JSONObject result){
			mBlogData = result;
			updateList();			
		}
	}

}
