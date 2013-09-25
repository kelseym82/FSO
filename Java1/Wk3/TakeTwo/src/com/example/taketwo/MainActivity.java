package com.example.taketwo;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
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

import com.mkelsey.lib.WebStuff;

public class MainActivity extends Activity {
	
	//Global Variables - Start with a "_" 
	Context _context;
	LinearLayout _appLayout;
	SearchForm _search;
	BeerDisplay _beer;
	SpinnerDisplay _spinner;
	Boolean _connected = false;
	String _beerName;
	Integer _abv;
	String _description;
	ArrayList<String> _beerNameList = new ArrayList<String>();
	ArrayList<String> _abvList = new ArrayList<String>();
	ArrayList<String> _desList = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		_context = this;
		_appLayout = new LinearLayout(this);
		
		_search = new SearchForm(_context, "Enter Text", "Search");
		
		//EditText searchField = search.getField();
		
		//Search Button Handler
		Button searchButton = _search.getButton();
		
		searchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.i("CLICK HANDLER", _search.getField().getText().toString());
				getBeerData(_search.getField().getText().toString());
			}
		});
		
		//Detect Internet
		_connected = WebStuff.getConnectionStatus(_context);
		if(_connected){
			Log.i("NETWORK CONNECTION", WebStuff.getConnectionType(_context));
		}
		
		//Display Beer Data Handler
		_beer = new BeerDisplay(_context);
		
		//Spiiner Handler
		_spinner = new SpinnerDisplay(_context);
		
		//Add view to the layout
		_appLayout.addView(_search);
		_appLayout.addView(_beer);
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
	
	private void getBeerData(String beer){
		String baseURL = "http://api.openbeerdatabase.com/v1/beers.json?callback=?";
		URL finalURL;
		try{
			finalURL = new URL(baseURL);
			beerDataRequest BDR = new beerDataRequest();
			BDR.execute(finalURL);
		} catch (MalformedURLException e){
			Log.e("BAD URL", "MALFORMED URL");
		}
	}
	
	private class beerDataRequest extends AsyncTask<URL, Void, String>{
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
					if(json.getString("name").compareTo("")==00){
						Toast toast = Toast.makeText(_context, "Invalid Beer Name", Toast.LENGTH_LONG);
						toast.show();
					}else{
						Toast toast = Toast.makeText(_context, "Valid Beer Name", Toast.LENGTH_LONG);
						toast.show();
					}
					
				
				
			} catch (JSONException e) {
				Log.e("JSON", "JSON OBJECT EXCEPTION");
			}
		}
		
	}

}
