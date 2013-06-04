package com.stormdev.minigamez.utils;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import org.bukkit.Location;
import com.stormdev.minigamez.main.minigamez;

public class Lobbies implements Serializable {
	private static final long serialVersionUID = 8650311534439769069L;
	public HashMap<String,SerializableLocation> locs = new HashMap<String, SerializableLocation>();
	File saveFile = new File(minigamez.plugin.pluginFolder + File.separator + "GameLobbieLocations.bin");
	public Lobbies(){
		if(!(saveFile.exists() || saveFile.length() > 0)){
			try {
				saveFile.mkdir();
				saveFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			this.locs = minigamez.loadHashMapSerializableLocation(saveFile.getAbsolutePath());
		} catch (Exception e) {
			try {
				saveFile.mkdir();
				saveFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	public void save(){
		minigamez.saveHashMapSloc(this.locs, saveFile.getAbsolutePath());
	}
	public void setLobby(String game, Location loc){
		SerializableLocation newLoc = new SerializableLocation(loc);
		this.locs.put(game, newLoc);
		this.save();
		return;
	}
	public Location getLobby(String game){
		SerializableLocation loc = null;
		if(this.locs.containsKey(game)){
			loc = this.locs.get(game);
		}
		if(loc == null){
			return null;
		}
		return loc.getLocation(minigamez.plugin.getServer());
	}
	

}
