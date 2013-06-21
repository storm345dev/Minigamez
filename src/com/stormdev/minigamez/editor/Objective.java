package com.stormdev.minigamez.editor;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Objective extends JPanel implements ActionListener{
	private static final long serialVersionUID = -8722850909010143667L;
	WindowArea window = null;
	GameEventList event = null;
	ObjectiveActions actions = null;
	JPanel values = new JPanel();
	JButton save = new JButton("Save!");
	JLabel when = new JLabel("When:");
	JLabel DO = new JLabel("Do:");
    public Objective(WindowArea area, ObjectiveManager manager){
    	this.window = area;
    	event = new GameEventList(area);
    	actions = new ObjectiveActions(area);
    	event.addActionListener(this);
    	actions.addActionListener(this);
    	event.draw();
    	actions.draw();
    	save.addActionListener(manager);
    }
    public String getEvent(){
    	return (String)this.event.getSelectedItem();
    }
    public String getAction(){
    	return (String)this.actions.getSelectedItem();
    }
    public HashMap<String, Object> getValues(){
		HashMap<String, Object> vals = new HashMap<String, Object>();
		Component[] comps = values.getComponents();
		for(Component comp:comps){
			if(comp.getName().contains("arg")){
				if(comp instanceof JTextField){
					vals.put(comp.getName(), ((JTextField)comp).getText());
					((JTextField) comp).setText("");
				}
				if(comp instanceof OptionList){
					vals.put(comp.getName(), ((OptionList)comp).getSelectedItem());
				}
			}
		}
		return vals;
    }
    public void draw(){
    	clearPanel(values);
    	this.add(when);
    	this.add(event);
    	this.add(DO);
    	this.add(actions);
    	//adjust values tab
    	if(getAction().equalsIgnoreCase("sendMessage")){
    		JTextField toSend = new JTextField(16);
    		toSend.setName("arg0");
    		OptionList to = new OptionList(window);
    		ArrayList<String> recipients = new ArrayList<String>();
    		recipients.add("player");
    		recipients.add("team");
    		to.setVals(recipients);
    		to.draw();
    		values.add(toSend);
    		values.add(new JLabel("To:"));
    		values.add(to);
    	}
    	this.add(values);
    	this.add(save);
    	window.refresh();
    }
    public void clearPanel(JPanel panel){
    	panel.removeAll();
    	return;
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
	    draw();
	}
    
	

}
