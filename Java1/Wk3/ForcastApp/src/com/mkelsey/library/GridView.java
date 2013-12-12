//Michael Kelsey
//Full Sail University
//Java 1 - Class 1312

package com.mkelsey.library;

import com.example.forcastapp.R;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class GridView extends GridLayout {
	//Variables
	Context _context;
	TextView _day;
	TextView _location;
	TextView _temp;
	TextView _condition;
	TextView _dayLabel;
	TextView _tempLabel;
	TextView _locationLabel;
	TextView _conditionLabel;
	
	
	
	public GridView(Context context){
		super(context);
		
		_context = context;
		//Sets the number of columns
		this.setColumnCount(2);
		
		//Sets TextViews to pull from the string resource file
		_dayLabel = new TextView(_context);
		_dayLabel.setText(getResources().getString(R.string.day_label));
		_day = new TextView(_context);
		
		_tempLabel = new TextView(_context);
		_tempLabel.setText(getResources().getString(R.string.temp_label));
		_temp = new TextView(_context);
		
		_locationLabel = new TextView(_context);
		_locationLabel.setText(getResources().getString(R.string.location_label));
		_location = new TextView(_context);
		
		_conditionLabel = new TextView(_context);
		_conditionLabel.setText(getResources().getString(R.string.condition_label));
		_condition = new TextView(_context);
		
		
		//Adds the TextViews to the View
		
		this.addView(_dayLabel);
		this.addView(_day);
		this.addView(_tempLabel);
		this.addView(_temp);
		this.addView(_locationLabel);
		this.addView(_location);
		this.addView(_conditionLabel);
		this.addView(_condition);
		
		
	}
	
	//Populates the grid view
	public void setForecastInfo(String dayText, String tempText, String locationText, String conditionText){
		_day.setText(dayText);
		_temp.setText(tempText);
		_location.setText(locationText);
		_condition.setText(conditionText);
	}
	
	
	
}
