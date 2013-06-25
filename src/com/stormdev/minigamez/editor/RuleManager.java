package com.stormdev.minigamez.editor;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

public class RuleManager extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1838513925752664927L;
	WindowArea window = null;
	HashMap<String, Object> ruleVals = new HashMap<String, Object>();
	GameRuleList rules = null;
	JButton add = new JButton("Set!");
	String rname = null;
	JPanel cont = null;
	
    public RuleManager(WindowArea area){
    	super(new GridLayout(0,1));
    	this.window = area;
    	this.rules = new GameRuleList(this.window);
    	this.rules.addActionListener(this);
    	this.rules.calculate();
    	ArrayList<String> rs = this.rules.getRules();
    	for(String r:rs){
    		Object val = this.rules.getDefault(r);
    		this.ruleVals.put(r, val);
    	}
    	this.add.setText("Set!");
    	this.add.addActionListener(this);
    	this.rules.draw();
    	this.draw();
    }
    public void draw(){
    	this.removeAll();
    	JPanel ruleList = new JPanel(new GridLayout(0,1));
    	for(String key:this.ruleVals.keySet()){
    		JPanel rval = new JPanel(new FlowLayout(FlowLayout.LEADING));
    		rval.add(new JLabel("["+key+"]"));
    		rval.add(new JLabel(this.ruleVals.get(key).toString()));
    		ruleList.add(rval);
    	}
    	this.add(ruleList);
    	JPanel container = new JPanel(new FlowLayout());
    	container.add(rules);
    	String rule = (String) this.rules.getSelectedItem();
    	String type = this.rules.getType(rule);
    	if(type.equalsIgnoreCase("bool")){
    		JCheckBox val = new JCheckBox();
    		if(ruleVals.containsKey(rule)){
    			val.setSelected((Boolean)ruleVals.get(rule));
    		}
    		val.setName("val");
    		container.add(val);
    	}
    	else if(type.equalsIgnoreCase("str")){
    		JTextField val = new JTextField();
    		if(ruleVals.containsKey(rule)){
    			val.setText((String)ruleVals.get(rule));
    		}
    		val.setName("val");
    		container.add(val);
    	}
    	else if(type.equalsIgnoreCase("int")){
    		JTextField val = new JTextField();
    		if(ruleVals.containsKey(rule)){
    			val.setText((String)ruleVals.get(rule));
    		}
    		val.setName("val");
    		container.add(val);
    	}
    	else if(type.equalsIgnoreCase("num")){
    		JTextField val = new JTextField();
    		if(ruleVals.containsKey(rule)){
    			val.setText((String)ruleVals.get(rule));
    		}
    		val.setName("val");
    		container.add(val);
    	}
    	container.add(add);
    	this.add(container);
    	this.cont = container;
    	this.rname = rule;
    	this.window.refresh();
    	return;
    }
    
	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		if(source == this.rules){
			this.draw();
		}
		if(source == this.add){
			String type = this.rules.getType(this.rname);
			Component[] comps = this.cont.getComponents().clone();
			Component val = null;
			for(Component comp:comps){
				if(comp.getName() == null || comp.getName().length() < 1){
				}
				else if(comp.getName().equalsIgnoreCase("val")){
					val = comp;
				}
			}
			if(type.equalsIgnoreCase("int")){
				String raw = ((JTextField) val).getText();
				int i = 0;
				try {
					i = Integer.parseInt(raw);
				} catch (NumberFormatException e) {
				this.window.popUpMsg(this.rname + "'s value must be an integer!", "ERROR");
				System.out.println("Incorrect val");
				return;
				}
				this.ruleVals.put(this.rname, i);
			}
			else if(type.equalsIgnoreCase("num")){
				String raw = ((JTextField) val).getText();
				Double n = 0.0;
				try {
					n = Double.parseDouble(raw);
				} catch (NumberFormatException e) {
				this.window.popUpMsg(this.rname + "'s value must be a number!", "ERROR");
				System.out.println("Incorrect val");
				return;
				}
				this.ruleVals.put(this.rname, n);
			}
			else if(type.equalsIgnoreCase("str")){
				String str = ((JTextField) val).getText();
				this.ruleVals.put(this.rname, str);
			}
			else if(type.equalsIgnoreCase("bool")){
				Boolean b = ((JCheckBox) val).isSelected();
				this.ruleVals.put(this.rname, b);
			}
			this.window.refresh();
			this.draw();
		}
	}
    
    
	

}
