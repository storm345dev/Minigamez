package com.stormdev.minigamez.editor;

import java.io.Serializable;

public class GameEvent implements Serializable {
	private static final long serialVersionUID = -6396798212734574850L;
	public double wait = 0;
	public double delay = 20;
	public String event = "Nothing";
	Boolean repeat = false;
	public GameEvent(double wait, double delay, String event, Boolean repeat){
		this.wait = wait;
		this.delay = delay;
		this.event = event;
		this.repeat = repeat;
	}

}
