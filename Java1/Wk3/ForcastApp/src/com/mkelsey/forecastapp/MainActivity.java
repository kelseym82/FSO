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
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.forcastapp.R;
import com.mkelsey.library.Connection;
import com.mkelsey.library.SearchBar;

public class MainActivity extends Activity {
	
	//Declare Global Variables Here
	Context _context;
	LinearLayout _ll;
	SearchBar _search;
	Boolean _connected = false;
	com.mkelsey.library.GridView _gridView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		loadLayout();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	//Load Layout Method - Loads the layout parameters
		public void loadLayout(){
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
		
	//Forecast retrieval method
	private void getForecast(String zipCode){
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
			ForecastRequest wr = new ForecastRequest();
			wr.execute(finalURL);
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
					JSONObject json = new JSONObject(result);
					String _day = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("forecast").getString("day");
					String _low = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("forecast").getString("low");
					String _high = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("forecast").getString("high");
					String _condition = json.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("forecast").getString("text");				
					
					_gridView.setForecastInfo(_day, _low, _high, _condition);
					
				} catch (JSONException e) {
					Log.e("JSON", "JSON OBJECT EXCEPTION");
				}
				
			}
			
		}
	
		
		
		
}


	