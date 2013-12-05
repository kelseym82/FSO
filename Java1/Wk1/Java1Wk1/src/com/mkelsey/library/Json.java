package com.mkelsey.library;

import org.json.JSONException;
import org.json.JSONObject;


//This uses the ENUM, and creates the JSON array used in the project

public class Json {
	
	public static JSONObject buildJSON(){
		//Create the Objects
		JSONObject weatherObject = new JSONObject();
		try {
			//creates the query object
			JSONObject dataObject = new JSONObject();
			
			//For Loop to run through the enum and add the strings to the json object
			for (Weather weather : Weather.values()){
				//create the data object to store the values
				JSONObject currentWeatherObject = new JSONObject();
				//adds the weather data to the data object
				currentWeatherObject.put("zipcode", weather.setZip());
				currentWeatherObject.put("condition", weather.setCondition());
				currentWeatherObject.put("temp", weather.setTemp());
				
				dataObject.put(weather.name().toString(), currentWeatherObject);
			}
			// add the query to weather object
			weatherObject.put("data", dataObject);
			
		} catch (JSONException e){
			e.printStackTrace();
		}
		
		return weatherObject;
	}
	
public static String readJSON(String selected){
	
	String result, zipcode, temp, condition;
	JSONObject object = buildJSON();
	
	try{
		zipcode = object.getJSONObject("data").getJSONObject(selected).getString("zipcode");
		condition = object.getJSONObject("data").getJSONObject(selected).getString("condition");
		temp = object.getJSONObject("data").getJSONObject(selected).getString("temp");
		
		result = "Zip Code: " + zipcode + "\r\n"
				+ "Condition: " + condition + "\r\n"
				+ "Temperature: " + temp + " Degrees";
		
	} catch (JSONException e){
		e.printStackTrace();
		result = e.toString();
	}
	return result;
}


}

