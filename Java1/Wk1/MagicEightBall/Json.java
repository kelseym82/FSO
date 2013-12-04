package com.gyasistory.jsontest;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.gyasistory.jsontest.lib.Songs;

public class Json {
	ArrayList<String> _songTags;

	/*
	 * This class is to create the JSON Object that will be used to create the
	 * data is used in the application.
	 * 
	 * @return <code>songsObject</code>
	 */
	public static JSONObject buildJSON() {

		// create Songs JSONObject
		JSONObject songsObject = new JSONObject();
		try {
			// create query JSONObject
			JSONObject queryObject = new JSONObject();

			// Create Song object in query
			for (Songs song : Songs.values()) {

				// Create SongTrack Object
				JSONObject tracksObject = new JSONObject();

				// add Tracks to object
				tracksObject.put("artist", song.setArtist());
				tracksObject.put("album", song.setAlbum());
				tracksObject.put("info", song.setInfo());
				tracksObject.put("track name", song.setTrack());
				queryObject.put(song.name().toString(), tracksObject);
			}
			// Add Query to songs
			songsObject.put("query", queryObject);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return songsObject;

	}

	public static String readJSON(String selected) {
		
		String result, artist, album, info, track;
		JSONObject object = buildJSON();
		
		try {
			artist = object.getJSONObject("query").getJSONObject(selected).getString("artist");
			album = object.getJSONObject("query").getJSONObject(selected).getString("album");
			info = object.getJSONObject("query").getJSONObject(selected).getString("info");
			track = object.getJSONObject("query").getJSONObject(selected).getString("track name");
			
			result = "Artist: " + artist + "\r\n"
					+ "Album: " +  album + "\r\n"
					+ "Information: " + info + "\r\n"
					+ "Track Name: " + track + "\r\n";
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = e.toString();
		}
			

		return result;

	}

}
