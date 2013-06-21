package com.stormdev.minigamez.editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JPanel;

public class ObjectiveManager extends JPanel implements ActionListener {
	private static final long serialVersionUID = 4790613626983447410L;
	WindowArea window = null;
    Objective newObjective = null;
	public ObjectiveManager(WindowArea area){
    super(new GridLayout(0,2));
    this.window = area;
    newObjective = new Objective(area, this);
    newObjective.draw();
    draw();
	}
	public void draw(){
		//add current objectives
		
		//create new objectives
		this.add(newObjective);
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == newObjective.save){
			//save it
			String gameevent = newObjective.getEvent();
			String action = newObjective.getAction();
			HashMap<String, Object> vals = newObjective.getValues();
			//TODO Save in readable format!
			//reset objectives bar
		    newObjective = new Objective(window, this);
		    newObjective.draw();
		}
	}
}
