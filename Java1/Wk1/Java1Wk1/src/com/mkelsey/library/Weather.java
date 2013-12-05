package com.mkelsey.library;

public enum Weather {
	//Locations - this will later get populated to the JSON file via Json.java
	Melbourne("32904", "Sunny", 85),
	Hollywood("90210", "Smoggy", 90),
	Hell("00666", "On Fire", 666),
	Neverland("23940", "Cloudy", 40),
	Narnia("10293", "Raining", 21);

	private final String zipcode;
	private final String condition;
	private final int temp;
	
	private Weather(String zipcode, String condition, int temp){
		
		this.zipcode = zipcode;
		this.condition = condition;
		this.temp = temp;
	}
	
	public String setZip(){
		return zipcode;
	}
	
	public String setCondition(){
		return condition;
	}
	
	public int setTemp(){
		return temp;
	}
}
