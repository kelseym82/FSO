package com.mkelsey.magiceightball;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private EightBall mEightBall = new EightBall();
	private TextView mAnswerLabel;
	private Button mGetAnswerButton;
	
	
	@Override
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Declare our View variables and assign them the Views from the layout file
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
        mGetAnswerButton = (Button) findViewById(R.id.button1);
        //mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
        
        mGetAnswerButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				String answer = mEightBall.getAnAnswer();

				
				// Update the label with dynamic answer
				mAnswerLabel.setText(answer);
				
				
			}
		});
    }
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
