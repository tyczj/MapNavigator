package com.tyczj.mapnavigator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Route {
	
	private ArrayList<LatLng> path = new ArrayList<LatLng>();
	private ArrayList<Legs> legs = new ArrayList<Legs>();
	private String totalDuration;
	private String totalDistance;
	private LatLng startLoc;
	private LatLng endLoc;
	private LatLngBounds bounds;
	private String startAddress;
	private String endAddress;
	
	public Route(JSONObject route){
		parseRoute(route);
	}
	
	private void parseRoute(JSONObject obj3){
		try{
			if(!obj3.isNull("legs")){
				JSONArray leg = obj3.getJSONArray("legs");
				
				for(int i=0; i<leg.length();i++){
					JSONObject obj = leg.getJSONObject(i);
					if(i == 0){
						if(!obj.isNull("start_address")){
							startAddress = obj.getString("start_address");
						}
						
						if(!obj.isNull("end_address")){
							endAddress = obj.getString("end_address");
						}

					}
					
					legs.add(new Legs(obj));
				}
			}
			
			if(!obj3.isNull("distance")){
				JSONObject obj = obj3.getJSONObject("distance");
				totalDistance = obj.getString("text");
			}
			
			if(!obj3.isNull("duration")){
				JSONObject obj = obj3.getJSONObject("duration");
				totalDuration = obj.getString("text");
			}
			
			if(!obj3.isNull("start_location")){
				JSONArray pos = obj3.getJSONArray("start_location");
				
				for(int i=0; i<pos.length();i++){
					JSONObject obj = pos.getJSONObject(i);
					startLoc = new LatLng(obj.getDouble("lat"),obj.getDouble("lng"));
				}
			}
			
			if(!obj3.isNull("end_location")){
				JSONObject pos = obj3.getJSONObject("end_location");
				endLoc= new LatLng(pos.getDouble("lat"),pos.getDouble("lng"));
				
			}
			
			if(!obj3.isNull("bounds")){
				JSONObject pos = obj3.getJSONObject("bounds");

					LatLng southWest = null;
					LatLng northEast = null;
					if(!pos.isNull("southwest")){
						JSONObject obj2 = pos.getJSONObject("southwest");
						southWest = new LatLng(obj2.getDouble("lat"),obj2.getDouble("lng"));
					}
					
					if(!pos.isNull("northeast")){
						JSONObject obj2 = pos.getJSONObject("northeast");
						northEast = new LatLng(obj2.getDouble("lat"),obj2.getDouble("lng"));
					}
					
					if(southWest != null && northEast != null){
						this.bounds = new LatLngBounds(southWest,northEast);
					}
				
			}
			
			
			for(Legs leg : legs){
				for(Steps step : leg.getSteps()){
					path.addAll(step.getSpetLinePoints());
				}	
			}
			
		}catch(JSONException e){
			e.printStackTrace();
		}
		
	}
	
	public String getDuration(){
		return totalDuration;
	}
	
	public ArrayList<LatLng> getPath(){
		return path;
	}
	
	public String getDistance(){
		return totalDistance;
	}
	
	public String getStartAddress(){
		return startAddress;
	}
	
	public String getEndAddress(){
		return endAddress;
	}
	
	public ArrayList<Legs> getLegs(){
		return legs;
	}
	
	public LatLngBounds getMapBounds(){
		return bounds;
	}
	
	public LatLng getStartLocation(){
		return startLoc;
	}
	
	public LatLng getEndLocation(){
		return endLoc;
	}

}
