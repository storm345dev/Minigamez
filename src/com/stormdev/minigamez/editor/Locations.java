package com.stormdev.minigamez.editor;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Locations extends JPanel implements ActionListener {
	private static final long serialVersionUID = -7360871852330458524L;
	private ArrayList<String> locNames = new ArrayList<String>();
	WindowArea window = null;
	JTextField newLocName = new JTextField(16);
	JButton add = new JButton("Add");
	public Locations(WindowArea area){
		this.window = area;
		this.setLayout(new GridLayout(0,2));
		this.add.addActionListener(this);
		setLocation("playerSpawnPoint");
		setLocation("deadPlayers");
		draw();
	}
	public void setLocation(String name){
		Boolean contains = false;
		for(String locName:locNames){
			if(locName.equalsIgnoreCase(name)){
				contains = true;
			}
		}
		if(!contains){
			this.locNames.add(name);
		}
		return;
	}
	public ArrayList<String> getLocations(){
		return this.locNames;
	}
	public void removeLocation(String name){
		Boolean contains = false;
		for(String locName:locNames){
			if(locName.equalsIgnoreCase(name)){
				name = locName;
				contains = true;
			}
		}
		if(contains){
			this.locNames.remove(name);
		}
		return;
	}
	public void draw(){
		this.clear();
		for(String loc:locNames){
			JButton removeIt = new JButton("Remove");
			final String locName = loc;
			removeIt.setAction(new Action(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					removeLocation(locName);
					draw();
				}
				@Override
				public void addPropertyChangeListener(
						PropertyChangeListener arg0) {
				}
				@Override
				public Object getValue(String arg0) {
					return null;
				}
				@Override
				public boolean isEnabled() {
					return true;
				}
				@Override
				public void putValue(String arg0, Object arg1) {
				}
				@Override
				public void removePropertyChangeListener(
						PropertyChangeListener arg0) {
				}
				@Override
				public void setEnabled(boolean arg0) {
				}});
			removeIt.setText("Remove");
		this.add(new JLabel(loc)); this.add(removeIt);
		}
		this.add(newLocName); this.add(add);
		window.refresh();
	}
	public void clear(){
		Component[] comps = this.getComponents().clone();
		for(Component comp:comps){
			this.remove(comp);
		}
		return;
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == add){
			String toAdd = newLocName.getText();
			if(toAdd == null || toAdd == "" || toAdd.length() < 1){
				window.popUpMsg("Please select a location name to add!", "ERROR");
				System.out.println("No Name! Aborted!");
				return;
			}
			if(toAdd.contains(" ")){
				window.popUpMsg("Location name cannot contain spaces!", "ERROR");
				System.out.println("Space in name! Aborted!");
				return;
			}
			setLocation(toAdd);
			newLocName.setText("");
			draw();
			return;
		}
	}
	
	

}
