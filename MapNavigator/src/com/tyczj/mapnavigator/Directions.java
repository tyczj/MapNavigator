package com.tyczj.mapnavigator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public class Directions {
	private String directions;
	private ArrayList<Legs> legs = new ArrayList<Legs>();
	private String totalDuration;
	private String totalDistance;
	private LatLng startLoc;
	private LatLng endLoc;
	private LatLngBounds bounds;
	private String startAddress;
	private String endAddress;
	private ArrayList<LatLng> path = new ArrayList<LatLng>();
	
	public enum DrivingMode{
		DRIVING,MASS_TRANSIT,BYCICLE,WALKING
	}
	
	public enum Avoid{
		TOLLS,HIGHWAYS,NONE
	}
	
	public Directions(String directions){
		this.directions = directions;
		
		if(directions != null){
			parseDirections();
		}
		
		
	}
	
	private void parseDirections(){
		try {
			JSONObject json = new JSONObject(directions);

			
			if(!json.isNull("routes")){
				JSONArray route = json.getJSONArray("routes");
				
				for(int k=0;k<route.length(); k++){
					JSONObject obj3 = route.getJSONObject(k);
					
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
					
					if(!obj3.isNull("duration")){
						JSONArray obj = obj3.getJSONArray("duration");
						
						for(int i=0; i<obj.length();i++){
							JSONObject obj2 = obj.getJSONObject(i);
							totalDuration = obj2.getString("text");
						}
					}
					
					if(!obj3.isNull("distance")){
						JSONArray obj = obj3.getJSONArray("distance");
						
						for(int i=0; i<obj.length();i++){
							JSONObject obj2 = obj.getJSONObject(i);
							totalDistance = obj2.getString("text");
						}
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
//						}
						
						
					}
					if(!obj3.isNull("overview_polyline")){
						JSONObject poly = obj3.getJSONObject("overview_polyline");
						decodePoly(poly.getString("points"));
					}
					
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getDuration(){
		return totalDuration;
	}
	
	private void decodePoly(String encoded) {
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;                 
                shift += 5;             
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;  
                shift += 5;             
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
 
            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            path.add(position);
        }
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
