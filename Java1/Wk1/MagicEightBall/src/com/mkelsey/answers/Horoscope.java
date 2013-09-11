package com.mkelsey.answers;

public enum Horoscope {
	
	AERIS("Today's a good day to work toward the goals you've set", "7"),
	TAURUS("You may want to watch the clock today", "13"),
	GEMINI("Do not trust anyone today", "50"),
	CANCER("Today you will find true love", "3"),
	LEO("Today someone you love will hurt you", "457"),
	VIRGO("Stop and smell the roses", "1337"),
	LIBRA("Snap into a slim jim", "8"),
	SCORPIO("You are looking for love in all the wrong places", "0"),
	SAGITTARIUS("Stay inside today", "1"),
	CAPRICORN("Yo! JOE!", "99"),
	AQUARIUS("Hell have no fury like a woman scorn", "88"),
	PISCES("If you're not first, you're last", "777");
	
	private final String horoscopeText;
	private final String luckyNumber;
	
	private Horoscope (String horoscopeText, String luckyNumber){
		this.horoscopeText = horoscopeText;
		this.luckyNumber = luckyNumber;
	}
	
	public String setHoroscope(){
		return horoscopeText;
	}
	
	public String setLuckyNumber(){
		return luckyNumber;
	}
}
