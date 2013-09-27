package com.example.taketwo;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mkelsey.lib.FileStuff;
import com.mkelsey.lib.WebStuff;

public class MainActivity extends Activity {
	
	//Global Variables - Start with a "_" 
	Context _context;
	Boolean _connected = false;
	HashMap<String, String> _history;
	ImageView _image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form);
		_context = this;
	
		_image = (ImageView) findViewById(R.id.image);
		_history = getHistory();
		Log.i("HISTORY READ", _history.toString());
		
		
		//Search Button Handler
		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				EditText field = (EditText) findViewById(R.id.searchField);
				String zipCode = field.getText().toString();
				field.setText(zipCode);
				InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(field.getWindowToken(), 0);
				getWeatherData(zipCode);
				
			}
		});
		
		//Detect Internet Connection
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	//Gets the weather data from the Yahoo! Weather API in JSON format
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
	
	
	//Stores the data into history data file using the FileStuff.java
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
	
	
	//Async Task that runs in the back ground once the GO button is clicked, this also populates the TextViews
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
				JSONObject _locationResults = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
				JSONObject _weatherResults = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition");
				if(_locationResults.getString("title").equals("City not found")){
					Toast toast = Toast.makeText(_context, "Invalid Zip Code", Toast.LENGTH_LONG);
					toast.show();
				}else{
					Toast toast = Toast.makeText(_context, "Valid Zip Code showing " + _locationResults.getString("title"), Toast.LENGTH_LONG);
					toast.show();
					String text = _weatherResults.getString("text");
					((TextView) findViewById(R.id.data_location)).setText(_locationResults.getString("title"));
					((TextView) findViewById(R.id.data_temp)).setText(_weatherResults.getString("temp"));
					((TextView) findViewById(R.id.data_condition)).setText(text);
					//Changes the Image on the page dependent on the type of weather outside, there are 48 types of weather, for now I am only coding in s.
					if (text.equals("Partly Cloudy")){
						_image.setImageResource(R.drawable.partly_cloudy);
						Log.i("IMAGE SET", "PARTLY CLOUDY");
					} else if (text.equals("Isolated Thunderstorms")){
						_image.setImageResource(R.drawable.storms);
					} else if (text.equals("Few Showers")){
						_image.setImageResource(R.drawable.scatter_showers);
					} else if (text.equals("Sunny")){
						_image.setImageResource(R.drawable.sunny);
					} else if (text.equals("Showers")){
						_image.setImageResource(R.drawable.rain);
					}else if (text.equals("Mostly Cloudy")){
						_image.setImageResource(R.drawable.cloudy);
					}
					
					//_textView.setText(_weatherLocation + "\r\n" + "Current Temp: " + _weatherTemp + "\r\n" + "Current Weather: " + _weatherCondition);
					_history.put(_locationResults.getString("title"), _locationResults.toString());
					FileStuff.storeObjectFile(_context, "history", _history, false);
					FileStuff.storeStringFile(_context, "temp", _locationResults.toString(), true);
				}
			} catch (JSONException e) {
				Log.e("JSON", "JSON OBJECT EXCEPTION");
				//Toast toast = Toast.makeText(_context, "Invalid Zip Code", Toast.LENGTH_LONG);
				//32toast.show();
			}
			
		}
		
	}

}
