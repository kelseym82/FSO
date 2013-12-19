//Michael Kelsey
//Full Sail University
//Java 1 - Class 1312

package com.mkelsey.forecastapp;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.forcastapp.R;
import com.mkelsey.library.Connection;

public class MainActivity extends Activity {
	
	//Declare Global Variables Here
	Context _context;
	LinearLayout _ll;
	Boolean _connected = false;
	ImageView _image;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout);
		_context = this;
		_image = (ImageView) findViewById(R.id.image);
		_connected = Connection.getConnectionStatus(_context);

		
		//Search Button Handler
		Button searchButton = (Button) findViewById(R.id.searchButton);
		searchButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("NETWORK CONNECTION", Connection.getConnectionType(_context));
				EditText field = (EditText) findViewById(R.id.searchField);
				String zipCode = field.getText().toString();
				field.setText(zipCode);
				if (zipCode.length() !=5){
					AlertDialog.Builder alert = new AlertDialog.Builder(_context);
					alert.setTitle("Invalid Zip Code");
					alert.setMessage("Please enter a valid Zip Code and try again.");
					alert.setCancelable(false);
					alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialogInterface, int i) {
							dialogInterface.cancel();
						}
					});
				alert.show();
				} if (_connected = false){
					//Alert if not connected
		            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
		            alert.setTitle("No Network Connection");
		            alert.setMessage("Check your network settings and try again.");
		            alert.setCancelable(false);
		            alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialogInterface, int i) {
		                    dialogInterface.cancel();
		                }
		            });
		            alert.show();
				
		            //Disable button
		            //searchButton.setClickable(false);
				} else {
					getForecast(zipCode);
				}
			}
		});
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//Load Layout Method - Loads the layout parameters
	/*	public void loadLayout(){
			//Method Variables
			_context = this;
			_ll = new LinearLayout(this);
			//Set String Text from Resource File
			String _hintText = getResources().getString(R.string.hint_text);
			String _buttonText = getResources().getString(R.string.button_text);
			//Add Search Bar Button Handler
			_search = new SearchBar(_context, _hintText, _buttonText);
			Button searchButton = _search.getButton();
			//On Click Listener for Button
			searchButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					if (_search.getField().getText().toString().length() !=5){
						AlertDialog.Builder alert = new AlertDialog.Builder(_context);
						alert.setTitle("Invalid Zip Code");
						alert.setMessage("Please enter a valid Zip Code and try again.");
						alert.setCancelable(false);
						alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								dialogInterface.cancel();
							}
						});
					alert.show();
					} else {
						getForecast(_search.getField().getText().toString());
					}
				}
			});
			//Detect Network Connection - Will disable the button if no connection if found
			_connected = Connection.getConnectionStatus(_context);
			 if (_connected) {
		            Log.i("NETWORK CONNECTION", Connection.getConnectionType(_context));

		            // Enable button
		            searchButton.setClickable(true);

		        } else {
		            //Alert if not connected
		            AlertDialog.Builder alert = new AlertDialog.Builder(_context);
		            alert.setTitle("No Network Connection");
		            alert.setMessage("Check your network settings and try again.");
		            alert.setCancelable(false);
		            alert.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
		                @Override
		                public void onClick(DialogInterface dialogInterface, int i) {
		                    dialogInterface.cancel();
		                }
		            });
		            alert.show();

		            //Disable button
		            searchButton.setClickable(false);
		        }
			//Add GridView
			 _gridView = new com.mkelsey.library.GridView(_context);
			//Add views to linear layout
			 _ll.addView(_search);
			 _ll.addView(_gridView);
			 
			 //Set Orientation
			 _ll.setOrientation(LinearLayout.VERTICAL);
			 
			 //Set Content View
			 setContentView(_ll);
		}
	*/
	
	//Forecast retrieval method
	private void getForecast(String zipCode){
		//URL to API which adds a zipcode input to the middle of the URL
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
			ForecastRequest fr = new ForecastRequest();
			fr.execute(finalURL);
		} catch(MalformedURLException e) {
			Log.e("BAD URL", "MALFORMED URL");
			finalURL = null;
		}
	}
	
	//Async Task that runs in the back ground once the GO button is clicked, this also populates the TextViews
		private class ForecastRequest extends AsyncTask<URL, Void, String>{
			@Override
			protected String doInBackground(URL... urls) {
				String response = "";
				for(URL url: urls){
					response = Connection.getURLStringResponse(url);
				}
				return response;
			}
			
			@Override
			protected void onPostExecute(String result){
				Log.i("URL RESPONSE", result);
				try {
					//Pulls the JSON object data from the completed URL
					JSONObject json = new JSONObject(result);
					JSONObject _locationResults = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item");
					JSONObject _weatherResults = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition");
					//If "City not found" gives a toast error.
					if(_locationResults.getString("title").equals("City not found")){
						Toast toast = Toast.makeText(_context, "Invalid Zip Code", Toast.LENGTH_LONG);
						toast.show();
					}else{
						Toast toast = Toast.makeText(_context, "Valid Zip Code showing " + _locationResults.getString("title"), Toast.LENGTH_LONG);
						toast.show();
						//Sets the string text to be used to change the picture below
						String text = _weatherResults.getString("text");
						//populates the gridView from the strings received from JSON.
						((TextView) findViewById(R.id.data_location)).setText(_locationResults.getString("title"));
						((TextView) findViewById(R.id.data_temp)).setText(_weatherResults.getString("temp"));
						((TextView) findViewById(R.id.data_condition)).setText(_weatherResults.getString("text"));
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
						} else if (text.equals("Mostly Cloudy")){
							_image.setImageResource(R.drawable.cloudy);
						}
	                    
					}
				} catch (JSONException e) {
					//Redundant toast error, just in case the "City not found" error does not work.  I found this option to work 100% of the time when an invalid zipcode is used.
					Log.e("JSON", "JSON OBJECT EXCEPTION");
					Toast toast = Toast.makeText(_context, "Invalid Zip Code", Toast.LENGTH_LONG);
					toast.show();
				}
				
			}
			
		}
	
		
		
		
}


	