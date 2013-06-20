package com.stormdev.minigamez.editor;

import java.util.ArrayList;

import javax.swing.JList;

public class LocationsList extends JList {
	private static final long serialVersionUID = -6570143230108119046L;
	private WindowArea window = null;
	private ArrayList<String> locations = new ArrayList<String>();
	public LocationsList(WindowArea area){
		this.window = area;
		this.locations = window.locations.getLocations();
	}
	public void updateLocs(){
		this.locations = window.locations.getLocations();
	}
	
	

}
