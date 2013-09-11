package com.mkelsey.magiceightball;

import java.util.Random;

public class EightBall {
	public String[] mAnswers = {
			"It is certain",
			"It is so",
			"All signs say YES",
			"The stars are not aligned",
			"My reply is no",
			"It is doubtful",
			"Better not tell you now",
			"Concentrate and ask again",
			"Unable to answer now",
			"It is hard to say"};
	// Methods (abilities: things the object can do)
	public String getAnAnswer() {

			
		String answer = "";
			
			//Randomly select on of three answer: Yes, No, or Maybe
			Random randomGenerator = new Random();//Construct a new Random number generator
			int randomNumber = randomGenerator.nextInt(mAnswers.length);
			
			answer = mAnswers[randomNumber];
			
			return answer;
	}
}
