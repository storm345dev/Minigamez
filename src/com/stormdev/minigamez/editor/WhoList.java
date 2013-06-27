package com.stormdev.minigamez.editor;

import java.util.ArrayList;

public class WhoList extends OptionList {
ArrayList<String> who = new ArrayList<String>();
	WindowArea window = null;
public WhoList(WindowArea area){
	super(area);
	this.window = area;
	this.update();
	this.draw();
}
public void update(){
	this.who = new ArrayList<String>();
	this.who.add("allPlayers");
	this.who.add("involvedPlayer");
	this.who.add("randomPlayer");
	ArrayList<String> teams = this.window.getTeams();
	for(String team:teams){
		this.who.add("team::"+team);
	}
	this.who.add("randomTeam");
	this.setVals(this.who);
}

}
