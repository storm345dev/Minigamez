package com.stormdev.minigamez.editor;

import java.util.ArrayList;

public class ObjectiveActions extends OptionList {
	private static final long serialVersionUID = 1090516548573439068L;
	public ArrayList<String> vals = new ArrayList<String>();
	private WindowArea window = null;
	public ObjectiveActions(WindowArea area){
		super(area);
		this.window = area;
		calculate();
	}
	public void calculate(){
		vals.add("sendMessage");
		vals.add("teleportTo");
		vals.add("setRespawnLoc");
		vals.add("broadcastMessage");
		vals.add("endGame");
		vals.add("heal");
		vals.add("kill");
		this.setVals(vals);
	}

}

