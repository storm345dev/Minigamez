package com.stormdev.minigamez.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.stormdev.minigamez.utils.Arena;
import com.stormdev.minigamez.utils.Arenas;
import com.stormdev.minigamez.utils.GameScheduler;
import com.stormdev.minigamez.utils.ListStore;
import com.stormdev.minigamez.utils.Lobbies;
import com.stormdev.minigamez.utils.LobbyManager;
import com.stormdev.minigamez.utils.MinigameMethods;
import com.stormdev.minigamez.utils.Profile;
import com.stormdev.minigamez.utils.SerializableLocation;

public class minigamez extends JavaPlugin {
	public SerializableLocation invalidLoc;
	public String pluginFolder;
	public HashMap<String, Arena> arenas = new HashMap<String, Arena>();
	//Main class
	private void copy(InputStream in, File file) {
	    try {
	        OutputStream out = new FileOutputStream(file);
	        byte[] buf = new byte[1024];
	        int len;
	        while((len=in.read(buf))>0){
	            out.write(buf,0,len);
	        }
	        out.close();
	        in.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> loadHashMapString(String path)
	{
		try
		{
			System.out.println("Loading information!");
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
	        Object result = ois.readObject();
	        ois.close();
			return (HashMap<String, String>) result;
		}
		catch(Exception e)
		{
			System.out.println("Information failed to load error:");
			e.printStackTrace();
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, SerializableLocation> loadHashMapSerializableLocation(String path)
	{
		try
		{
			System.out.println("Loading information!");
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
	        Object result = ois.readObject();
	        ois.close();
			return (HashMap<String, SerializableLocation>) result;
		}
		catch(Exception e)
		{
			System.out.println("Information failed to load error:");
			e.printStackTrace();
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public static HashMap<String, Arena> loadHashMapArena(String path)
	{
		try
		{
			System.out.println("Loading information!");
	        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path));
	        Object result = ois.readObject();
	        ois.close();
			return (HashMap<String, Arena>) result;
		}
		catch(Exception e)
		{
			System.out.println("Information failed to load error:");
			e.printStackTrace();
			return null;
		}
	}
	public static void saveHashMap(HashMap<String, String> map, String path)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(map);
			oos.flush();
			oos.close();
			//Handle I/O exceptions
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void saveHashMapArena(HashMap<String, Arena> map, String path)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(map);
			oos.flush();
			oos.close();
			//Handle I/O exceptions
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void saveHashMapSloc(HashMap<String, SerializableLocation> map, String path)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(map);
			oos.flush();
			oos.close();
			//Handle I/O exceptions
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public static Plugin bukkit;
	public static minigamez plugin;
	public static FileConfiguration config;
	public static PluginDescriptionFile pluginYaml;
    public Arenas minigamesArenas = null;
    public GameScheduler gameScheduler = null;
    public MinigameMethods mgMethods = null;
    public Lobbies mgLobbies = null;
    public LobbyManager mgLobbyManager = null;
    public Boolean isUcarsInstalled = false;
    public Scoreboard leaderboards = null;
public void onEnable(){
	//Now on github!
	plugin = this;
	bukkit = this;
	getServer().getPluginManager().registerEvents(new MinigameListener(this), this); //TODO this shud be working??
	pluginYaml = plugin.getDescription();
	//START HERE
	invalidLoc = new SerializableLocation(new Location(getServer().getWorlds().get(0), 1, 1, 1));
	PluginDescriptionFile pldesc = plugin.getDescription();
    Map<String, Map<String, Object>> commands = pldesc.getCommands();
    Set<String> keys = commands.keySet();
    for(String k : keys){
    	try {
			getCommand(k).setExecutor(new MinigameCommandExecutor(this));
		} catch (Exception e) {
			getLogger().log(Level.SEVERE, "Error registering command " + k.toString());
			e.printStackTrace();
		}
    }
	minigamesArenas = new Arenas(this);
	config = getConfig();
	try{
		config.load(this.getDataFolder().getAbsolutePath() + File.separator + "config.yml");
		//here is where we write to the config
		if(!config.contains("settings.showProfilesAndLeaderboards.enable")){
			config.set("settings.showProfilesAndLeaderboards.enable", true);
		}
	}
	catch (Exception e){
		//error
	}
	saveConfig();
	this.gameScheduler = new GameScheduler();
	this.mgMethods = new MinigameMethods();
	String pathArenas = this.getDataFolder().getAbsolutePath() + File.separator + "arenas.bin";
	File fileArenas = new File(pathArenas);
	if(fileArenas.exists() && fileArenas.length() > 1){ // check if file exists before loading to avoid errors!
		arenas = loadHashMapArena(pathArenas);
	}
	else{
		getLogger().info("Created a new arenas.bin!");
		arenas = new HashMap<String, Arena>();
		saveHashMapArena(arenas, this.getDataFolder().getAbsolutePath() + File.separator + "arenas.bin");
	}
	pluginFolder = this.getDataFolder().getAbsolutePath();
	(new File(pluginFolder)).mkdirs();
	this.mgLobbies = new Lobbies();
	File lobbyFile = new File(getDataFolder().getAbsolutePath()+File.separator+"mgLobbies.lobbylist");
	if(!lobbyFile.exists() || lobbyFile.length() < 1){
		try {
			lobbyFile.createNewFile();
		} catch (Exception e) {
			getLogger().info("Failed to create lobbyList file!");
		}
	}
	this.mgLobbyManager = new LobbyManager(lobbyFile);
	this.mgLobbyManager.load();
	Set<Team> teams = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeams();
	for(Team team:teams){
		if(team.getName().startsWith("red") || team.getName().startsWith("blue")){
			team.unregister();
		}
	}
	if(config.getBoolean("settings.showProfilesAndLeaderboards.enable")){
    this.leaderboards = getServer().getScoreboardManager().getMainScoreboard();
    this.leaderboards.clearSlot(DisplaySlot.SIDEBAR);
    if(this.leaderboards.getObjective("gamers") == null){
    	this.leaderboards.registerNewObjective("gamers", "dummy");
    }
    this.leaderboards.getObjective("gamers").setDisplayName(ChatColor.GOLD+"Top gamers:");
    this.leaderboards.getObjective("gamers").setDisplaySlot(DisplaySlot.SIDEBAR);
    Profile.calculateLeaderboard();
	}
	getLogger().info("Minigamez is enabled");	//Tell the console it is enabled
}
public void onDisable(){
	this.mgLobbyManager.save();
	Set<Team> teams = plugin.getServer().getScoreboardManager().getMainScoreboard().getTeams();
	for(Team team:teams){
		if(team.getName().startsWith("red") || team.getName().startsWith("blue")){
			team.unregister();
		}
	}
	getLogger().info("Minigamez is disabled");
}
}
