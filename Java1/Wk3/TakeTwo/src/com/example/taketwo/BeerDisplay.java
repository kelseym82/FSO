package com.example.taketwo;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class BeerDisplay extends GridLayout {
	
	TextView _locationName;
	TextView _tempText;
	TextView _condition;
	Context _context;
	
	public BeerDisplay(Context context){
		super(context);
		
		_context = context;
		
		this.setColumnCount(2);
		
		TextView locationLabel = new TextView(_context);
		locationLabel.setText("Location:");
		_locationName = new TextView(_context);
		TextView tempLabel  = new TextView(_context);
		tempLabel.setText("Temp:");
		_tempText = new TextView(_context);
		TextView conditionLabel = new TextView(_context);
		conditionLabel.setText("Condition:");
		_condition = new TextView(_context);
		
		this.addView(locationLabel);
		this.addView(_locationName);
		this.addView(tempLabel);
		this.addView(_tempText);
		this.addView(conditionLabel);
		this.addView(_condition);
	}
}
