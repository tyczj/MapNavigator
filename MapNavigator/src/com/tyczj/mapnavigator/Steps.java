package com.tyczj.mapnavigator;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

public class Steps {
	
	private LatLng start;
	private LatLng end;
	private String travelMode;
	private String duration;
	private String distance;
	private String instructions;
	
	private ArrayList<LatLng> stepLine;
	
	public Steps(JSONObject obj){
		stepLine = new ArrayList<LatLng>();
		parseStep(obj);
	}
	
	public LatLng getStepStartPosition(){
		return start;
	}
	
	public LatLng getEndStepPosition(){
		return end;
	}
	
	public String getStepTravelMode(){
		return travelMode;
	}
	
	public String getStepDuration(){
		return duration;
	}
	
	public String getStepDistance(){
		return distance;
	}
	
	public String getStepInstructions(){
		return instructions;
	}
	
	public ArrayList<LatLng> getSpetLinePoints(){
		return stepLine;
	}

	private void parseStep(JSONObject step){
		try{
			travelMode = step.getString("travel_mode");
			
			if(!step.isNull("start_location")){
				JSONObject pos = step.getJSONObject("start_location");
				start = new LatLng(pos.getDouble("lat"),pos.getDouble("lng"));
			}
			
			if(!step.isNull("end_location")){
				JSONObject pos = step.getJSONObject("end_location");
				end = new LatLng(pos.getDouble("lat"),pos.getDouble("lng"));
			}
			
			if(!step.isNull("duration")){
				JSONObject pos = step.getJSONObject("duration");
				duration = pos.getString("text");
			}
			
			if(!step.isNull("distance")){
				JSONObject pos = step.getJSONObject("distance");
				distance = pos.getString("text");
			}
			
			if(!step.isNull("polyline")){
				JSONObject pos = step.getJSONObject("polyline");
				decodePoly(pos.getString("points"));
//				Log.d("Step count", String.valueOf(stepLine.size()));
			}
			
			instructions = step.getString("html_instructions");
		}catch(JSONException e){
			e.printStackTrace();
		}
		
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
//            Log.d("Lat", String.valueOf(position.latitude));
//            Log.d("Lng", String.valueOf(position.longitude));
            stepLine.add(position);
        }
    }
}
