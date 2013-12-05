package com.mkelsey.java1wk1;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.java1wk1.R;
import com.mkelsey.library.Json;
import com.mkelsey.library.UIElements;

public class MainActivity extends Activity {
	
	//Global Variables
	Context _context;
	RadioGroup radioGroup;
	TextView resultText;
	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LinearLayout lLayout = new LinearLayout(this);
		LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lLayout.setLayoutParams(lParams);
		lLayout.setOrientation(LinearLayout.VERTICAL);
		_context = this;

		//Set String Values from Resource
		String buttonText = getResources().getString(R.string.go);
		//Adding the linear layout features from UIElements to this button.		
		LinearLayout llButton = UIElements.button(this, buttonText);
		
		
		//View Variables
		Button button = (Button) llButton.findViewById(2);
				
		
		//This is "Get Weather" Button Handler
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int selectedRadioId = radioGroup.getCheckedRadioButtonId();
				RadioButton selectedRadio = (RadioButton) radioGroup.findViewById(selectedRadioId);
				String selected = selectedRadio.getText().toString();
				resultText.setText(Json.readJSON(selected));
			};
		});
		//This is the Radio Group Handler
		String[] locations = {"Melbourne", "Hollywood", "Hell", "Neverland", "Narnia"};
		radioGroup = UIElements.radioGroupOptions(this, locations);
		
		//This is the Text View Handler
		resultText = UIElements.showResults(this);
		resultText.setText("Select location and push button for current weather");
		
		//Add the UI Elements to the LL
		lLayout.addView(radioGroup);
		lLayout.addView(resultText);
		lLayout.addView(llButton);
		
		setContentView(lLayout);
		
	}

	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
