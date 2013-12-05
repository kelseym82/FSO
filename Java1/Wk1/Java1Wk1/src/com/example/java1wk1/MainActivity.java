package com.example.java1wk1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	//Global Variables
	Context _context;


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		_context = this;

		
		
		
		//View Variables go here
		Button button = (Button) findViewById(R.id.button);
		final TextView resultText = (TextView) findViewById(R.id.resultView);
		
		
		
			
		//This is "Get Weather" Button Handler
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				resultText.setText(Json.readJSON("MELBOURNE"));
			};
		});
		
		
	}

	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
