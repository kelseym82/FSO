package com.mkelsey.magiceightball;

import java.util.Random;

public class EightBall {
	public String[] mAnswers = {
			"It is certain",
			"It is decidedly so",
			"Without a doubt",
			"You may rely on it",
			"As I see it yes",
			"Most Likely",
			"Outlook Good",
			"Yes",
			"Signs point to yes",
			"Reply hazy try again",
			"Ask again later",
			"Better not tell you now",
			"Cannot predict now",
			"Concentrate and ask again",
			"Don't count on it",
			"My reply is no",
			"My Sources say no",
			"Outlook not so good",
			"Very doubtful"};
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
