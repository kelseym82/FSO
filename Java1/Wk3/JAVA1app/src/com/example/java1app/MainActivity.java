package com.example.java1app;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mkelsey.lib.BasicLayout;
import com.mkelsey.lib.NetworkClass;

public class MainActivity extends Activity {
	
	Context _context;
	String[] beerName;
	Resources res;
	TextView results;
	Spinner viewSpinner;
	TextView jsonView;
	TextView connectedView;
	String beerNameString;
	String abv;
	String cal;
	String breweryName;
	Boolean connected = false;
	ArrayList<String> beerNameListArray = new ArrayList<String>();
	ArrayList<String> abvListArray = new ArrayList<String>();
	ArrayList<String> calListArray = new ArrayList<String>();
	ArrayList<String> breweryNameListArray = new ArrayList<String>();
	
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		_context = this;
		res = getResources();
		
		// setting the linear layout
		LinearLayout ll = new LinearLayout(this);
		LinearLayout ml = BasicLayout.layoutWithButton(this, "Show Info");
		ll.setOrientation(LinearLayout.VERTICAL);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
		ll.setLayoutParams(lp);
		
		// Creating button from BasicLayout class
		Button mb = (Button) ml.findViewById(1);
		mb.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				/*Get selected team info.*/
				int pos = viewSpinner.getSelectedItemPosition();
				String tName = beerNameListArray.get(pos).toString();
				String tAbv = abvListArray.get(pos).toString();
				String tCal = calListArray.get(pos).toString();  
				String tBrewery = breweryNameListArray.get(pos).toString();

				jsonView.setText("Name: " + tName + "\r\n" + "ABV: " + tAbv + "\r\n" +"Calories: "+ tCal + "\r\n" + "Brewery: " + tBrewery);
			}

		});
	

	//connectedView = new TextView(_context);
	
	// Detecting network connection
			connected = NetworkClass.getConnectionStatus(_context);
			if(connected){
				
				Log.i("NETWORK CONNECTION", NetworkClass.getConnectionType(_context));
				//connectedView.setText("Network Connection: " + NetworkClass.getConnectionType(_context)+"\n");
			}
			else{
					//connectedView.setText(""+NetworkClass.getConnectionType(_context)+"\n");
			}

			
			// calling the getSongInfo function 
			getBeerData();
	
	// song length display
	/*int songNum = res.getStringArray(R.array.songArray).length;*/
	//TextView tv = new TextView(context);
	//tv.setText("Check out one of the "+songNum+" songs on the album");*/
	
	//spinner adapter
	ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(_context, android.R.layout.simple_spinner_item, beerName);
	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	
	//creating the spinner
	viewSpinner = new Spinner(_context);
	viewSpinner.setAdapter(spinnerAdapter);
	lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	viewSpinner.setLayoutParams(lp);
	
	//spinner onClick function
	viewSpinner.setOnItemSelectedListener(new OnItemSelectedListener(){

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Toast.makeText(_context, "You selected " + beerName[position], Toast.LENGTH_LONG).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	});

	
	// setting different layout parts to the main layout
	ll.addView(ml);
	//ll.addView(tv);
	ll.addView(viewSpinner);
	ll.addView(connectedView);
	ll.addView(jsonView);
	
	
	// setting the content view
	setContentView(ll);
	
	
	
	
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main, menu);
	return true;
}

//get URL
private void getBeerData(){
	String baseURL = "https://dl.dropboxusercontent.com/u/74966280/Dev/JAVA1/beers.json";
	URL finalURL;
	try{
		finalURL = new URL(baseURL);
		dataRequest sr = new dataRequest();
		sr.execute(finalURL);
	} catch (MalformedURLException e){
		Log.e("BAD URL", "MALFORMED URL");
		finalURL = null;
	}
}

//get data from URL
private class dataRequest extends AsyncTask<URL, Void, String>{
	@Override
	protected String doInBackground(URL... urls){
		String response = "";
		for(URL url: urls){
			response = NetworkClass.getURLStringResponse(url);
		}
		return response;
	}
	//get data and add to arrays.
	@Override
	protected void onPostExecute(String result){
		try {
			JSONArray jsonArray = new JSONArray(result);

			int n = jsonArray.length();
			for(int i = 0;i<n; i++){
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				beerNameString = jsonObject.getString("name");
				abv= jsonObject.getString("abv");
				cal = jsonObject.getString("calories");
				breweryName= jsonObject.getString("brewery");
				beerNameListArray.add(beerNameString);
				abvListArray.add(abv);
				calListArray.add(cal);  
				breweryNameListArray.add(breweryName);
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}	
}

}
