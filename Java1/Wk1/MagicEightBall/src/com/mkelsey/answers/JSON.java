package com.mkelsey.answers;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON {
	
	public static JSONObject buildJSON() {
		
		
			// Create Lucky Day JSONOBject
			JSONObject horoscopeObject = new JSONObject();
		try {		
			// Create Query 
			JSONObject queryObject = new JSONObject();
			
			//Create Answers in Query
			for (Horoscope answer : Horoscope.values()){
				//Create Object
				JSONObject newObject = new JSONObject();
				//Adds Horoscope Text and Lucky Numbers
				newObject.put("horoscopeText", answer.setHoroscope());
				newObject.put("luckyNumber", answer.setLuckyNumber());
				queryObject.put(answer.name().toString(), newObject);
			}
			// Add Query to LuckyDay
			horoscopeObject.put("query", queryObject);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return horoscopeObject;

	}
	
		
	
	public static String readJSON ( String selected){
		
		String result, horoscopeText, luckyNumber;
		JSONObject object = buildJSON();
		
		try {
			horoscopeText = object.getJSONObject("query").getJSONObject(selected).getString("horoscopeText");
			luckyNumber = object.getJSONObject("query").getJSONObject(selected).getString("luckyNumber");
			result = "Daily Horoscope: " + horoscopeText + "\r\n"
					+ "Lucky Number: " + luckyNumber;
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();
		}
		
		return result;
	}
}
