//Michael Kelsey
//Full Sail University
//Java 1 - Class 1312

package com.mkelsey.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

@SuppressLint("ViewConstructor")
public class SearchBar extends LinearLayout {

		//Create variables
		//search field
		EditText _searchField;

		//lp
		LayoutParams _lp;

		//search button
		Button _searchButton;

 		//starts a new search form.
		public SearchBar(Context context, String hint, String buttonText) {
		super(context);
    	//Set layout params
    	_lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    	this.setLayoutParams(_lp);

    	//Declare variables
    	_searchField = new EditText(context);
    	_lp = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);
    	_searchField.setLayoutParams(_lp);
    	_searchField.setHint(hint);
    	//number only keyboard
    	_searchField.setRawInputType(InputType.TYPE_CLASS_NUMBER);

    	_searchButton = new Button(context);
    	_searchButton.setText(buttonText);

    	//adding elements to view
    	this.addView(_searchField);
    	this.addView(_searchButton);
	}

	//Gets the field.
	public EditText getField() {
		return _searchField;
	}

	//Gets the button.
	public Button getButton() {
		return _searchButton;
	}
}
