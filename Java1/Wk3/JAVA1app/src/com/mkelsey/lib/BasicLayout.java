package com.mkelsey.lib;

import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class BasicLayout {
	
	// creating the linear layout
	public static LinearLayout layoutWithButton(Context context, String buttonTxt){
		
		// setting the linear layout
		LinearLayout ll = new LinearLayout(context);
		LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(lp);
		
		//Main button created
		Button button = new Button(context);
		button.setText(buttonTxt);
		button.setId(1);
		ll.addView(button);
		
		return ll;
	}	
}