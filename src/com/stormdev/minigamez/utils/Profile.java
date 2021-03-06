package com.stormdev.minigamez.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import com.google.common.collect.Ordering;
import com.stormdev.minigamez.main.minigamez;

public class Profile {
	private static minigamez plugin = minigamez.plugin;
	private String name = null;
	private File profileFile = null;
	private YamlConfiguration editor = new YamlConfiguration();
	public Profile(String name){
		this.name = name;
		OfflinePlayer[] players = Bukkit.getOfflinePlayers();
		for(int i=0;i<players.length;i++){
			if(players[i].getName().equalsIgnoreCase(name)){
				name = players[i].getName();
				this.name = name;
			}
		}
		String filename = name+".yml";
		File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "profiles" + File.separator + filename);
		if(file.length()<1 || !file.exists()){
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
			}
		}
		profileFile = file;
		try {
			editor.load(profileFile);
		} catch (Exception e) {
		}
	}
	public String getName(){
		return this.name;
	}
	public static List<Profile> getProfiles(){
		List<Profile> profiles = new ArrayList<Profile>();
		OfflinePlayer[] players = minigamez.plugin.getServer().getOfflinePlayers();
		for(OfflinePlayer player:players){
			String name = player.getName();
			Profile pProfile = new Profile(name);
            profiles.add(pProfile);
		}
	    return profiles;
	}
	public static Objective calculateLeaderboard(){
		SortedMap<String, Integer> stats = getStats();
		Map<String, Integer> vals = new HashMap<String, Integer>();
		vals.putAll(stats);
		Set<String> gamers = stats.keySet();
		Object gamersarray[] = gamers.toArray();
		int displayed = 0;
		for(int i=0;i<gamersarray.length && displayed < 5;i++){
			String gamer = (String) gamersarray[i];
			if(minigamez.plugin.getServer().getOfflinePlayer(gamer) == null){
			}
			else{
				if(vals.get(gamer) != null){
			minigamez.plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("gamers").getScore(plugin.getServer().getOfflinePlayer(gamer)).setScore(vals.get(gamer));
			displayed++;
				}
			}
		}
		minigamez.plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("gamers").setDisplaySlot(DisplaySlot.SIDEBAR);
		return minigamez.plugin.getServer().getScoreboardManager().getMainScoreboard().getObjective("gamers");
	}
	public static SortedMap<String, Integer> getStats(){
		Map<String, Integer> rawstats = new HashMap<String, Integer>();
		List<Profile> profiles = getProfiles();
		for(Profile profile:profiles){
	          String pname = profile.getName();
	          profile.getRewardPoints();
	          rawstats.put(pname, profile.getRewardPoints());
		}
		ValueComparator com = new ValueComparator(rawstats);
		SortedMap<String, Integer> stats = new TreeMap<String, Integer>(com);
		stats.putAll(rawstats);
		return stats;
	}
public static File getProfile(String name){
	OfflinePlayer[] players = Bukkit.getOfflinePlayers();
	for(int i=0;i<players.length;i++){
		if(players[i].getName().equalsIgnoreCase(name)){
			name = players[i].getName();
		}
	}
	String filename = name+".yml";
	File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + "profiles" + File.separator + filename);
	if(file.length()<1 || !file.exists()){
		try {
			file.createNewFile();
		} catch (IOException e) {
		}
	}
	return file;
}
public File getProfile(){
	return this.profileFile;
}
public YamlConfiguration getEditor(){
	return this.editor;
}
public void load(){
	try {
		editor.load(profileFile);
	} catch (Exception e) {
	}
}
public void save(){
	try {
		editor.save(profileFile);
	} catch (IOException e) {
		try {
			profileFile.createNewFile();
		} catch (IOException e2) {
		}
		try {
			editor.save(profileFile);
		} catch (IOException e1) {
		}
	}
}
public void setOnline(Boolean online){
	editor.set("profile.online", online);
	this.save();
	this.load();
}
public Boolean getOnline(){
	Boolean online = false;
	if(editor.contains("profile.online")){
		online = editor.getBoolean("profile.online");
	}
	return online;
}
public void setOnlineTime(){
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm");
	   Date date = new Date();
	   String time = dateFormat.format(date);
	   editor.set("profile.last", time);
	   this.save();
	   this.load();
	return;
}
public String getOnlineTime(){
	String time = "Unknown";
	if(editor.contains("profile.last")){
		time = editor.getString("profile.last");
	}
	return time;
}
public int getRewardPoints(){
	int points = 0;
	if(editor.contains("profile.rewardPoints")){
		points = editor.getInt("profile.rewardPoints");
	}
	return points;
}
public void addRewardPoint(int amount){
	int current = 0;
	if(editor.contains("profile.rewardPoints")){
		current = editor.getInt("profile.rewardPoints");
	}
	int newOne = current;
	newOne += amount;
	if(current < 1 && newOne <0){
		newOne = 0;
	}
	if(newOne<0){
		newOne = 0;
	}
	editor.set("profile.rewardPoints", newOne);
	this.save();
	this.load();
	return;
}
}
