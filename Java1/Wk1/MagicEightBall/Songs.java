package com.gyasistory.jsontest.lib;

public enum Songs  {
	LoveSong("Lecrae", "The Compilation Album" , "ft. Lecrae, Estella, Ray", "Love Song"),
	Streets("Lecrae", "The Compilation Album", "ft. Lecrae, Bj, Tedashii", "Streets"),
	Riot("Tedashi", "Blacklight", "N/A", "Riot"),
	ShowUp("Flame", "The 6th", "feat Lecrae", "Show Out");
	
	private final String artist;
	private final String album;
	private final String info;
	private final String track;
	
	private Songs(String artist, String album, String info, String track ){
		
		this.artist = artist;
		this.album = album;
		this.info = info;
		this.track = track;
		
	}
	
	public String setArtist(){
		return artist;
	}
	
	public String setAlbum(){
		return album;
	}
	
	public String setInfo(){
		return info;
	}
	
	public String setTrack(){
		return track;
	}

}
