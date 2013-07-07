package com.stormdev.minigamez.editor;

import java.io.Serializable;

public class Score implements Serializable {
	private static final long serialVersionUID = -1416891832335821294L;
	String name = "";
    Object defVal = "";
    ScoreType type = ScoreType.STRING;
	public Score(String name, Object defVal, ScoreType type){
		this.name = name;
		this.defVal = defVal;
		this.type = type;
	}

}
