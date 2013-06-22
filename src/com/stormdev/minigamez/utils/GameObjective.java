package com.stormdev.minigamez.utils;

import java.io.Serializable;
import java.util.HashMap;



public class GameObjective implements Serializable {
	private static final long serialVersionUID = -3855923751889621847L;
	public String gameEvent,gameAction = null;
	  HashMap<String,Object> eventVals,actionVals = new HashMap<String, Object>();
      public GameObjective(String gameEvent, String gameAction, HashMap<String, Object> eventVals, HashMap<String, Object> actionVals){
    	  this.gameEvent = gameEvent;
    	  this.gameAction = gameAction;
    	  this.eventVals = eventVals;
    	  this.actionVals = actionVals;
      }
      public String getEvent(){
    	  return this.gameEvent;
      }
      public String getActon(){
    	  return this.gameAction;
      }
      public HashMap<String, Object> getEventVals(){
    	  return this.eventVals;
      }
      public HashMap<String, Object> getActionVals(){
    	  return this.actionVals;
      }
      public void setEvent(String gameEvent){
    	  this.gameEvent = gameEvent;
    	  return;
      }
      public void setActon(String gameAction){
    	  this.gameAction = gameAction;
    	  return;
      }
      public void setEventVals(HashMap<String, Object> eventVals){
    	  this.eventVals = eventVals;
    	  return;
      }
      public void setActionVals(HashMap<String, Object> actionVals){
    	  this.actionVals = actionVals;
    	  return;
      }

}
