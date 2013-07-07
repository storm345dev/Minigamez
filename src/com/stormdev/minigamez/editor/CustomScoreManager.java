package com.stormdev.minigamez.editor;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class CustomScoreManager extends JPanel {
	private static final long serialVersionUID = -4520198303034869976L;
	public ArrayList<Score> scores = new ArrayList<Score>();
    WindowArea window = null;
	public CustomScoreManager(WindowArea area){
    	this.window = area;
    }
	public void draw(){
		JPanel current = new JPanel(new GridLayout(0,1));
		for(final Score s:this.scores){
			JPanel pane = new JPanel(new FlowLayout());
			pane.add(new JLabel("|"+s.name + "|Type:"+s.type+"|DefaultValue:"+s.defVal+"|"));
		    JButton remove = new JButton("Remove!");
		    remove.setAction(new Action(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					delScore(s);
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
		    remove.setText("remove!");
		    pane.add(remove);
		    current.add(pane);
		}
		final JTextField name = new JTextField(16);
		final OptionList type = new OptionList(this.window);
		ArrayList<String> types = new ArrayList<String>();
		for(ScoreType v:ScoreType.values()){
			types.add(v.toString().toLowerCase());
		}
		type.setVals(types);
		type.draw();
		type.setSelectedItem("string");
		final JPanel val = new JPanel(new FlowLayout());
		type.setAction(new Action(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//change the value object accordingly
				OptionList type = (OptionList) arg0.getSource();
				String ty = (String) type.getSelectedItem();
				Component value = new JTextField(16);
				if(ty.equalsIgnoreCase("boolean")){
					value = new OptionList(window);
					ArrayList<String> vals = new ArrayList<String>();
					vals.add("true");
					vals.add("false");
					((OptionList) value).setVals(vals);
					((OptionList) value).draw();
					value.setName("value");
				}
				else if(ty.equalsIgnoreCase("string")){
					value = new JTextField(16);
					((JTextComponent) value).setEditable(true);
					value.setName("value");
				}
				else if(ty.equalsIgnoreCase("num")){
					value = new JTextField(16);
					((JTextComponent) value).setEditable(true);
					value.setName("valuet:num");
				}
				else if(ty.equalsIgnoreCase("int")){
					value = new JTextField(16);
					((JTextComponent) value).setEditable(true);
					value.setName("valuet:int");
				}
				val.removeAll();
				val.add(value);
				window.refresh();
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
		JTextField value = new JTextField(16);
		value.setName("value");
		value.setEditable(true);
		val.add(value);
		JPanel newS = new JPanel(new FlowLayout());
		newS.add(new JLabel("Name:"));
		newS.add(name);
		type.draw();
		newS.add(new JLabel("Type:"));
		newS.add(type);
		type.setSelectedItem("string");
		newS.add(new JLabel("Default Value"));
		newS.add(val);
		JButton add = new JButton("Add!");
		add.setName("Add!");
		add.setAction(new Action(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//add it
				ScoreType ty = ScoreType.STRING;
				String t = (String) type.getSelectedItem();
				if(t.equalsIgnoreCase("boolean")){
					ty = ScoreType.BOOLEAN;
				}
				else if(t.equalsIgnoreCase("string")){
					ty = ScoreType.STRING;
				}
				else if(t.equalsIgnoreCase("int")){
					ty = ScoreType.INT;
				}
				else if(t.equalsIgnoreCase("num")){
					ty = ScoreType.NUM;
				}
				Component[] comps = val.getComponents();
				Object value = null;
				for(Component comp:comps){
					if(comp.getName().toLowerCase().contains("value")){
						if(comp instanceof JTextField && comp.getName().toLowerCase().contains("t:num")){
							try {
								value = Double.parseDouble(((JTextField)comp).getText());
							} catch (NumberFormatException e) {
								window.popUpMsg("Score value must be a number", "ERROR");
							    return;
							}
						}
						if(comp instanceof JTextField && comp.getName().toLowerCase().contains("t:int")){
							try {
								value = Integer.parseInt(((JTextField)comp).getText());
							} catch (NumberFormatException e) {
								window.popUpMsg("Score value must be an integer", "ERROR");
							    return;
							}
						}
						if(comp instanceof OptionList){
							String v = ((String)((OptionList)comp).getSelectedItem());
						    if(v.equalsIgnoreCase("true")){
						    	value = true;
						    }
						    else{
						    	value = false;
						    }
						}
						if(comp instanceof JTextField){
							value = ((JTextField)comp).getText();
						}
					}
				}
				Score score = new Score(name.getText(),value,ty);
				addScore(score);
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
		add.setText("Add");
		add.setVisible(true);
		newS.add(add);
		current.add(newS);
		this.removeAll();
		this.add(current);
		this.window.refresh();
		return;
	}
	public ArrayList<Score> getScores(){
		return this.scores;
	}
	public void addScore(Score score){
		if(!this.scores.contains(score)){
			this.scores.add(score);
		}
		return;
	}
	public void delScore(Score score){
		if(this.scores.contains(score)){
			this.scores.remove(score);
		}
		return;
	}
}
