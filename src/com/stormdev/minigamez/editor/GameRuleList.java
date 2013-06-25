package com.stormdev.minigamez.editor;

import java.util.ArrayList;
import java.util.HashMap;

public class GameRuleList extends OptionList {
	private static final long serialVersionUID = 1090516548573439068L;
	public HashMap<String, String> vals = new HashMap<String, String>();
	public HashMap<String, Object> defaults = new HashMap<String, Object>();
	private WindowArea window = null;
	public GameRuleList(WindowArea area){
		super(area);
		this.window = area;
		calculate();
	}
	public void calculate(){
		this.vals.put("keepInventoryOnDeath", "bool");
		this.defaults.put("keepInventoryOnDeath", true);
		this.vals.put("differentInventoryDuringMinigame", "bool");
		this.defaults.put("differentInventoryDuringMinigame", true);
		this.vals.put("allowCommands", "bool");
		this.defaults.put("allowCommands", false);
		ArrayList<String> rules = new ArrayList<String>();
		rules.addAll(this.vals.keySet());
		this.setVals(rules);
	}
	public Object getDefault(String rule){
		if(this.defaults.containsKey(rule)){
			return this.defaults.get(rule);
		}
		else{
			return null;
		}
	}
	public ArrayList<String> getRules(){
		ArrayList<String> rules = new ArrayList<String>();
		rules.addAll(this.vals.keySet());
		return rules;
	}
	public String getType(String rule){
		if(this.vals.containsKey(rule)){
			return this.vals.get(rule);
		}
		else{
			return null;
		}
	}

}

