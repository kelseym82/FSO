package com.example.java1wk1;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

//This uses the ENUM, and creates the JSON array used in the project

public class Json {
	ArrayList<String> _weatherData;

	public static JSONObject buildJSON(){
		//Create the Objects
		JSONObject weatherObject = new JSONObject();
		try {
			//creates the query object
			JSONObject queryObject = new JSONObject();
			
			//For Loop to run through the enum and add the strings to the json object
			for (Weather weather : Weather.values()){
				//create the data object to store the values
				JSONObject dataObject = new JSONObject();
				//adds the weather data to the data object
				dataObject.put("zipcode", weather.setZip());
				dataObject.put("temp", weather.setTemp());
				dataObject.put("condition", weather.setCondition());				
			}
			// add the query to weather object
			weatherObject.put("query", queryObject);
			
		} catch (JSONException e){
			e.printStackTrace();
		}
		return weatherObject;
	}
	
public static String readJSON(String selected){
	
	String result, zipcode, temp, condition;
	JSONObject object = buildJSON();
	
	try{
		zipcode = object.getJSONObject("query").getJSONObject(selected).getString("zipcode");
		temp = object.getJSONObject("query").getJSONObject(selected).getString("temp");
		condition = object.getJSONObject("query").getJSONObject(selected).getString("condition");
		
		result = 
		
	} catch (JSONException e){
		e.printStackTrace();
		result = e.toString();
	}
	return result;
}


}

