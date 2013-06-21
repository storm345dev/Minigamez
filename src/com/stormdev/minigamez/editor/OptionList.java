package com.stormdev.minigamez.editor;

import java.util.ArrayList;

import javax.swing.JComboBox;

public class OptionList extends JComboBox {

	private static final long serialVersionUID = -6570143230108119046L;
	private WindowArea window = null;
	private ArrayList<String> vals = new ArrayList<String>();
	public OptionList(WindowArea area){
		this.window = area;
		this.vals = new ArrayList<String>();
		draw();
	}
	public void setVals(ArrayList<String> vals){
		this.vals = vals;
		return;
	}
	public ArrayList<String> getVals(){
		return this.vals;
	}
	public void draw(){
		//create
		this.setModel(new JComboBox(this.vals.toArray()).getModel());
	}

}
