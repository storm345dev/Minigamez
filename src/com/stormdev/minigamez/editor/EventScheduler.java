package com.stormdev.minigamez.editor;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EventScheduler extends JPanel {
	private static final long serialVersionUID = -7802601186441528252L;
	public ArrayList<GameEvent> events = new ArrayList<GameEvent>();
	public EventScheduler(){
		super(new GridLayout(0,2));
	}
	
	public void registerEvent(double wait, double delay, String event, Boolean repeat){
		GameEvent gEvent = new GameEvent(wait, delay, event, repeat);
		if(!events.contains(gEvent)){
			events.add(gEvent);
		}
		return;
	}
	public void unRegisterEvent(GameEvent event){
		if(this.events.contains(event)){
			this.events.remove(event);
		}
		return;
	}
	public ArrayList<GameEvent> getEvents(){
		return this.events;
	}
	public void draw(final WindowArea window){
		this.removeAll();
		for(final GameEvent event:this.events){
			JPanel panel = new JPanel(new FlowLayout());
			String eInfo = "[Wait "
		+ event.wait + " ticks|Then every " 
	    + event.delay + "ticks|FireEvent "
	    + event.event + "|Repeat: "
	    + event.repeat + "]";
			final JLabel evt = new JLabel(eInfo);
			JButton remove = new JButton();
			remove.setAction(new Action(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
                events.remove(event);
                draw(window);
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
					return false;
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
			remove.setText("Remove");
			remove.setEnabled(true);
			panel.add(evt);
			panel.add(remove);
			this.add(panel);
			this.add(new JLabel(" "));
		}
		JPanel newEvt = new JPanel(new FlowLayout());
		final JTextField wait = new JTextField(3);
		final JTextField delay = new JTextField(4);
		ArrayList<String> cEvnts = window.customEvents.getEvts();
		if(!cEvnts.contains("Nothing")){
		cEvnts.add("Nothing");
		}
		final OptionList evt = new OptionList(window);
		evt.setVals(cEvnts);
		evt.draw();
		evt.setSelectedItem("Nothing");
		final JCheckBox repeat = new JCheckBox();
		JButton add = new JButton("Add!");
		add.setAction(new Action(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				double tWait = 0;
				double tDelay = 20;
				try {
					tWait = Double.parseDouble(wait.getText());
					tDelay = Double.parseDouble(delay.getText());
				} catch (NumberFormatException e) {
					window.popUpMsg("Wait and delay must be a number!", "ERROR");
					return;
				}
				if(tWait < 0 || tDelay < 0){
					window.popUpMsg("Wait and delay must more than or equal to 0!", "ERROR");
					return;
				}
            GameEvent event = new GameEvent(tWait, tDelay, ((String)evt.getSelectedItem()), repeat.isSelected());
			events.add(event);
			draw(window);
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
				return false;
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
		add.setText("Add!");
		add.setEnabled(true);
		newEvt.add(new JLabel("Wait "));
		newEvt.add(wait);
		newEvt.add(new JLabel(" ticks, then every "));
		newEvt.add(delay);
		newEvt.add(new JLabel(" ticks, fireEvent "));
		newEvt.add(evt);
		newEvt.add(new JLabel(" Repeat? "));
		newEvt.add(repeat);
		newEvt.add(add);
		this.add(newEvt);
		window.refresh();
	}
	
}
