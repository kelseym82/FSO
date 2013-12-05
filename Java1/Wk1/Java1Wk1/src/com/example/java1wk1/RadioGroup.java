package com.example.java1wk1;

import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.TextView;

public class RadioGroup {
	public static TextView resultText;
	static RadioGroup group;
	static RadioButton rButton;

	public static RadioGroup radioGroup(Context context) {
		

	

		// Create Radio Group
		group = new RadioGroup();
		
		int i = 1;

		for (Weather weather : Weather.values()) {

			// Add group name to Radio Group
			RadioButton rb = new RadioButton(context);
			rb.setText(weather.name());
			Log.i("Radio Button", rb.getText().toString());
			rb.setTag(weather.name());
			rb.setId(i);
			i++;
			//group.addView(rb);
		}

		

		

		

		// return
		return group;

	}
}