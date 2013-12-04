package com.gyasistory.jsontest.layout;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyasistory.jsontest.lib.Songs;

public class GroupLayout {
	public static TextView resultText;
	static RadioGroup group;
	static RadioButton rButton;

	public static RadioGroup radioGroup(Context context) {
		

	

		// Create Radio Group
		group = new RadioGroup(context);
		
		int i = 1;

		for (Songs song : Songs.values()) {

			// Add group name to Radio Group
			RadioButton rb = new RadioButton(context);
			rb.setText(song.name());
			Log.i("Radio Button", rb.getText().toString());
			rb.setTag(song.name());
			rb.setId(i);
			i++;
			group.addView(rb);
		}

		

		

		

		// return
		return group;

	}

}
