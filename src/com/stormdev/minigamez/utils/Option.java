package com.stormdev.minigamez.utils;

import java.io.Serializable;

public class Option implements Serializable {
	private static final long serialVersionUID = 8287691824216531461L;
public Option(Object val, OptionType type){
	this.val = val;
	this.type = type;
}
	public Object val = null;
public OptionType type = OptionType.INVALID;
public Object getValue(){
	return this.val;
}
public OptionType getType(){
	return this.type;
}
public void setValue(Object value){
	this.val = value;
}
public void setType(OptionType type){
	this.type = type;
}
}
