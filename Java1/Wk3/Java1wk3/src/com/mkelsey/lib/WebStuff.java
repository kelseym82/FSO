package com.mkelsey.lib;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class WebStuff {
	
	static Boolean _conn = false;
	static String _connectionType = "Unavailable";
	
	public static String getConnectionType(Context context){
		netInfo(context);
		return _connectionType;
	}
	
	public static Boolean getConnectionStatus(Context context){
		netInfo(context);
		return _conn;
	}
	
	private static void netInfo(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		if(ni != null){
			if(ni.isConnected()){
				_connectionType = ni.getTypeName();
				_conn = true;
			}
		}
	}
}
