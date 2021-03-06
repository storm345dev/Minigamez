package com.stormdev.minigamez.utils;

import java.io.Serializable;
import java.util.HashMap;

public class CustomGame implements Serializable {
	private static final long serialVersionUID = 5522536924802473540L;
	private HashMap<String, Option> options = new HashMap<String, Option>();
	private int playerLimit = 0;
	public String name;
	public void setPlayerLimit(int limit){
		this.playerLimit = limit;
		return;
	}
	public int getPlayerLimit(){
		return this.playerLimit;
	}
	public HashMap<String, Option> getOptions(){
		return this.options;
	}
	public Option getOption(String key){
		if(!this.options.containsKey(key)){
			return null;
		}
		return this.options.get(key);
	}
	public Boolean validate(){
		for(Option opt:this.options.values()){
			if(opt.type == OptionType.INVALID){
				return false;
			}
		}
		return true;
	}
	public Boolean setOption(String key, Option val){
		this.options.put(key, val);
		return true;
	}
}

