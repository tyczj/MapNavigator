package com.tyczj.mapnavigator;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Legs {
	
	private ArrayList<Steps> steps;
	
	public Legs(JSONObject leg){
		steps = new ArrayList<Steps>();
		parseSteps(leg);
	}
	
	public ArrayList<Steps> getSteps(){
		return steps;
	}
	
	private void parseSteps(JSONObject leg){
		try{
			if(!leg.isNull("steps")){
				JSONArray step = leg.getJSONArray("steps");
				
				for(int i=0; i<step.length();i++){
					JSONObject obj = step.getJSONObject(i);
					Log.d("Step", String.valueOf(i));
					steps.add(new Steps(obj));
				}
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
	}

}
