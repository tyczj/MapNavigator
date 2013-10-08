package com.tyczj.mapnavigator;

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
	
	public Steps(JSONObject obj){
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
			
			instructions = step.getString("html_instructions");
		}catch(JSONException e){
			e.printStackTrace();
		}
		
	}
}
