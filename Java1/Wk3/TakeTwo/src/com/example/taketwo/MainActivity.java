package com.example.taketwo;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mkelsey.lib.FileStuff;
import com.mkelsey.lib.WebStuff;

public class MainActivity extends Activity {
	
	//Global Variables - Start with a "_" 
	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	BeerDisplay _weather;
	SpinnerDisplay _spinner;
	Boolean _connected = false;
	String _weatherName;
	Integer _abv;
	String _description;
	ArrayList<String> _beerNameList = new ArrayList<String>();
	ArrayList<String> _abvList = new ArrayList<String>();
	ArrayList<String> _desList = new ArrayList<String>();
	HashMap<String, String> _history;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_context = this;
		_appLayout = new LinearLayout(this);
		_history = getHistory();
		Log.i("HISTORY READ", _history.toString());
		
		_search = new SearchForm(_context, "Enter Zip Code", "Search");
		
		//EditText searchField = search.getField();
		
		//Search Button Handler
		Button searchButton = _search.getButton();
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("CLICK HANDLER", _search.getField().getText().toString());
				getWeatherData(_search.getField().getText().toString());
				
			}
		});
		
		//Detect Internet
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		}
		
		//Display Beer Data Handler
		_weather = new BeerDisplay(_context);
		
		//Spinner Handler
		_spinner = new SpinnerDisplay(_context);
		
		//Add view to the layout
		_appLayout.addView(_search);
		_appLayout.addView(_weather);
		_appLayout.addView(_spinner);
		
		_appLayout.setOrientation(LinearLayout.VERTICAL);
		setContentView(_appLayout);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void getWeatherData(String zipCode){
		String baseURL = "http://query.yahooapis.com/v1/public/yql?q=select%20item%20from%20weather.forecast%20where%20location%3D\""+ zipCode +"\"&format=json";
		String qs;
		try{
			qs = URLEncoder.encode(baseURL, "UTF-8");
		} catch (Exception e){
			Log.e("BAD URL", "ENCODING PROBLEM");
			qs = "";
		}
		URL finalURL;
		try{
			finalURL = new URL(baseURL);
			Log.i("BASE URL =",baseURL);
			WeatherRequest wr = new WeatherRequest();
			wr.execute(finalURL);
		} catch(MalformedURLException e) {
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getHistory(){
		Object stored = FileStuff.readObjectFile(_context, "history", false);
		
		HashMap<String, String> history;
		if(stored == null){
			Log.i("HISTORY", "NO HISTORY FILE FOUND");
			history = new HashMap<String, String>();
		} else {
			history = (HashMap<String, String>) stored;
		}
		return history;
	}
	
	private class WeatherRequest extends AsyncTask<URL, Void, String>{
		@Override
		protected String doInBackground(URL... urls) {
			String response = "";
			for(URL url: urls){
				response = WebStuff.getURLStringResponse(url);
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE", result);
			try {
				JSONObject json = new JSONObject(result);
				JSONObject results = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
				if(results.getString("title").compareTo("N/A")==0){
					Toast toast = Toast.makeText(_context, "Invalid Zip Code", Toast.LENGTH_LONG);
					toast.show();
				}else{
					Toast toast = Toast.makeText(_context, "Valid Zip Code showing " + results.getString("title"), Toast.LENGTH_LONG);
					toast.show();
					_history.put(results.getString("title"), results.toString());
					FileStuff.storeObjectFile(_context, "history", _history, false);
					FileStuff.storeStringFile(_context, "temp", results.toString(), true);
				}
			} catch (JSONException e) {
				Log.e("JSON", "JSON OBJECT EXCEPTION");
				//Toast toast = Toast.makeText(_context, "Invalid Zip Code", Toast.LENGTH_LONG);
				//32toast.show();
			}
			
		}
		
	}

}
