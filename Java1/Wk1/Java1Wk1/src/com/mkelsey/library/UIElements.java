package com.mkelsey.library;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UIElements {
	public static LinearLayout button(Context context, String buttonText) {
		// Create linear layout and parameters
		LinearLayout ll = new LinearLayout(context);
		LayoutParams lParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		ll.setLayoutParams(lParams);
		
		// Layout Params
		lParams = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1.0f);

		// Button Params
		Button getButton = new Button(context);
		getButton.setText(buttonText);
		getButton.setId(2);
		ll.addView(getButton);

		return ll;
	}
	
	
	//Radio Group Created Here.  To Be Populated via String Array In MainActivity.java
	public static  RadioGroup radioGroupOptions(Context context, String[] location) {
		RadioGroup rGroup = new RadioGroup(context);

		for (int i = 0; i < location.length; i++) {
			RadioButton rButton = new RadioButton(context);
			
			rButton.setText(location[i]);
            if (i==0){
				rButton.setChecked(true);
			}
			rButton.setId(i+1);
			rGroup.addView(rButton);
		}
		

		return rGroup;
	}
	
	
	//TextView Created Here
	public static TextView showResults(Context context) {
		TextView textView = new TextView(context);
		textView.setId(3);

		return textView;
	}
}

