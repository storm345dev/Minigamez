package com.stormdev.minigamez.editor;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class Objective extends JPanel implements ActionListener{
	private static final long serialVersionUID = -8722850909010143667L;
	WindowArea window = null;
	GameEventList event = null;
	ObjectiveActions actions = null;
	JPanel values = new JPanel(new GridLayout(1,0));
    public Objective(WindowArea area){
    	this.window = area;
    	event = new GameEventList(area);
    	actions = new ObjectiveActions(area);
    	event.addActionListener(this);
    	actions.addActionListener(this);
    	event.draw();
    	actions.draw();
    }
    public String getEvent(){
    	return (String)this.event.getSelectedItem();
    }
    public String getAction(){
    	return (String)this.actions.getSelectedItem();
    }
    public void draw(){
    	this.add(new JLabel("When:"));
    	this.add(event);
    	this.add(new JLabel("Do:"));
    	this.add(actions);
    	this.add(new JLabel("Options:"));
    	this.add(values);
    	
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    window.popUpMsg("success", "test");
	}
    
	

}
