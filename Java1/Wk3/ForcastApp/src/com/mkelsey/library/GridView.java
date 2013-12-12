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
	TextView _high;
	TextView _low;
	TextView _condition;
	TextView _dayLabel;
	TextView _lowLabel;
	TextView _highLabel;
	TextView _conditionLabel;
	
	
	
	public GridView(Context context){
		super(context);
		
		_context = context;
		//Sets the number of columns
		this.setColumnCount(4);
		
		//Sets TextViews to pull from the string resource file
		_dayLabel = new TextView(_context);
		_dayLabel.setText(getResources().getString(R.string.day_label));
		
		_lowLabel = new TextView(_context);
		_lowLabel.setText(getResources().getString(R.string.low_temp_label));
		
		_highLabel = new TextView(_context);
		_highLabel.setText(getResources().getString(R.string.high_temp_label));
		
		_conditionLabel = new TextView(_context);
		_conditionLabel.setText(getResources().getString(R.string.condition_label));
		
		_day = new TextView(_context);
		_low = new TextView(_context);
		_high = new TextView(_context);
		_condition = new TextView(_context);
		
		//Adds the TextViews to the View
		//Labels
		this.addView(_dayLabel);
		this.addView(_lowLabel);
		this.addView(_highLabel);
		this.addView(_conditionLabel);
		//Actual Forecast
		this.addView(_day);
		this.addView(_low);
		this.addView(_high);
		this.addView(_condition);
		
	}
	
	//Populates the grid view
	public void setForecastInfo(String dayText, String lowText, String highText, String conditionText){
		_day.setText(dayText);
		_low.setText(lowText);
		_high.setText(highText);
		_condition.setText(conditionText);
	}
	
	
}
