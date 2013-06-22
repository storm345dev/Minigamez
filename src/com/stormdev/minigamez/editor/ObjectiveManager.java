package com.stormdev.minigamez.editor;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stormdev.minigamez.utils.GameObjective;

public class ObjectiveManager extends JPanel implements ActionListener {
	private static final long serialVersionUID = 4790613626983447410L;
	WindowArea window = null;
    Objective newObjective = null;
    ArrayList<GameObjective> objectives = new ArrayList<GameObjective>();
	public ObjectiveManager(WindowArea area){
    super(new GridLayout(0,1));
    this.window = area;
    this.newObjective = new Objective(this.window, this);
    this.newObjective.draw();
    this.draw();
	}
	public ArrayList<GameObjective> getObjectives(){
		return this.objectives;
	}
	public void draw(){
		this.removeAll();
		//add current objectives
		final ArrayList<GameObjective> objs = objectives;
		for(GameObjective obj:objs){
			JPanel current = new JPanel(new FlowLayout());
			String toWrite = "When:["+obj.getEvent() + "]Do:["+obj.getActon()+"]Options:[";
			HashMap<String, Object> eventVals = obj.getEventVals();
			for(String key:eventVals.keySet()){
				Object ob = eventVals.get(key);
				toWrite = toWrite + ob.toString() + "|";
			}
			HashMap<String, Object> actionVals = obj.getActionVals();
			for(String key:actionVals.keySet()){
				Object ob = actionVals.get(key);
				toWrite = toWrite + ob.toString() + "|";
			}
			toWrite = toWrite + "]";
			final JLabel display = new JLabel(toWrite);
			JButton remove = new JButton("[Remove]");
			final GameObjective fobj = obj;
			final ObjectiveManager instance = this;
			remove.setAction(new Action(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					//removeTheObjective
					instance.objectives.remove(fobj);
					instance.draw();
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
			current.add(display);
			remove.setVisible(true);
			remove.setText("[Remove]");
			current.add(remove);
			this.add(current);
		}
		//create new objectives
		this.add(this.newObjective);
		this.window.refresh();
	}
	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.newObjective.getSaveButton()){
			//save it
			String gameevent = this.newObjective.getEvent();
			String action = this.newObjective.getAction();
			HashMap<String, Object> actionvals;
			HashMap<String, Object> eventvals;
			try {
				actionvals = this.newObjective.getActionValues();
				eventvals = this.newObjective.getEventValues();
			} catch (Exception e) {
				this.window.refresh();
				return;
			}
			if(actionvals == null || eventvals == null){
				this.window.refresh();
				return;
			}
			//TODO Save in readable format!
			objectives.add(new GameObjective(gameevent, action, eventvals, actionvals));
			//reset objectives bar
			//clear it
			this.remove(this.newObjective);
		    this.newObjective = new Objective(this.window, this);
		    this.newObjective.draw();
		    this.draw();
		    this.window.refresh();
		}
	}
}
