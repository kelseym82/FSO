package com.example.java1wk1;

public enum Weather {
	MELBOURNE("32904", "Sunny", "85"),
	BEVERLYHILLS("90210", "Smoggy", "90"),
	HELL("00666", "On Fire", "666"),
	NEVERLAND("23940", "Cloudy", "40"),
	NARNIA("10293", "Raining", "21");

	private final String zipcode;
	private final String condition;
	private final String temp;
	
	private Weather(String zipcode, String condition, String temp){
		
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
	
	public String setTemp(){
		return temp;
	}
}
