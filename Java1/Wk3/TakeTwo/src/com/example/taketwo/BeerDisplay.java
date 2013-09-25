package com.example.taketwo;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.TextView;

public class BeerDisplay extends GridLayout {
	
	TextView _beerName;
	TextView _abv;
	TextView _description;
	Context _context;
	
	public BeerDisplay(Context context){
		super(context);
		
		_context = context;
		
		this.setColumnCount(2);
		
		TextView beerNameLabel = new TextView(_context);
		beerNameLabel.setText("Name:");
		_beerName = new TextView(_context);
		TextView abvLabel  = new TextView(_context);
		abvLabel.setText("ABV:");
		_abv = new TextView(_context);
		TextView descriptionLabel = new TextView(_context);
		descriptionLabel.setText("Description:");
		_description = new TextView(_context);
		
		this.addView(beerNameLabel);
		this.addView(_beerName);
		this.addView(abvLabel);
		this.addView(_abv);
		this.addView(descriptionLabel);
		this.addView(_description);
	}
}
