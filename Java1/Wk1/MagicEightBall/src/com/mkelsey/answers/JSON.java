package com.mkelsey.answers;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON {
	
	public static JSONObject buildJSON() {
		
		
			// Create Lucky Day JSONOBject
			JSONObject luckyDayObject = new JSONObject();
		try {		
			// Create Query 
			JSONObject queryObject = new JSONObject();
			
			//Create Answers in Query
			for (Answers answer : Answers.values()){
				//Create Object
				JSONObject newObject = new JSONObject();
				newObject.put("month", answer.setMonth());
				newObject.put("numDays", answer.numDays());
				queryObject.put(answer.name().toString(), newObject);
			}
			// Add Query to LuckyDay
			luckyDayObject.put("query", queryObject);
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return luckyDayObject;

	}
	
		
	
	/*public static String readJSON ( String selected){
		
		String results, month, dayString;
		Integer day;
		JSONObject object = buildJSON();
		
		try {
			month = object.getJSONObject("query").getJSONObject(selected).getString("month");
			 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return results;
	}*/
}
