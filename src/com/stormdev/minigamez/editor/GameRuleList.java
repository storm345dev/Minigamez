package com.stormdev.minigamez.editor;

import java.util.ArrayList;

public class GameRuleList extends OptionList {
	private static final long serialVersionUID = 1090516548573439068L;
	public ArrayList<String> vals = new ArrayList<String>();
	private WindowArea window = null;
	public GameRuleList(WindowArea area){
		super(area);
		this.window = area;
		calculate();
	}
	public void calculate(){
		vals.add("keepInventoryOnDeath");
		vals.add("differentInventoryDuringMinigame");
		vals.add("allowCommands");
		this.setVals(vals);
	}

}

