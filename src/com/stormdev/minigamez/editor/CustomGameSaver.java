package com.stormdev.minigamez.editor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;

import com.stormdev.minigamez.main.minigamez;
import com.stormdev.minigamez.utils.CustomGame;
import com.stormdev.minigamez.utils.Option;
import com.stormdev.minigamez.utils.OptionType;

public class CustomGameSaver {
	public static Boolean save(WindowArea window){
		CustomGame game = new CustomGame();
		String gname = window.gameName.getText();
		if(gname == null || gname == "" || gname.contains(".") || gname.contains("/") || gname.contains("\\")){
			window.popUpMsg("Invalid game name!", "ERROR");
			System.out.println("Invalid game name... Aborted save");
			return false;
		}
		if(gname.length() < 1){
			window.popUpMsg("Invalid game name!", "ERROR");
			System.out.println("Invalid game name... Aborted save");
			return false;
		}
		if(gname.contains(" ")){
			window.popUpMsg("Game name cannot contain spaces!", "ERROR");
			System.out.println("Game name cannot contain spaces... Aborted save");
			return false;
		}
		game.name = gname;
		Boolean valid = true;
	    System.out.println("Saving change in player count options");
	      String minP = window.minPlayers.getText();
	      int minimum = 2;
	      try {
			minimum = Integer.parseInt(minP);
		} catch (NumberFormatException e1) {
			window.minPlayers.setText("2");
			window.popUpMsg("Minimum amount of players must be a number!", "ERROR");
			valid = false;
		}
	      if(minimum < 1){
	    	  window.minPlayers.setText("2");
				window.popUpMsg("Minimum amount of players must be at least 1!", "ERROR");
				valid = false;
	      }
	    
	    String maxP = window.maxPlayers.getText();
	      int maximum = 2;
	      try {
			maximum = Integer.parseInt(maxP);
		} catch (NumberFormatException e1) {
			window.maxPlayers.setText("4");
			window.popUpMsg("Maximum must be a number!", "ERROR");
			valid = false;
		}
	      if(maximum < 1){
	    	  window.maxPlayers.setText("4");
				window.popUpMsg("Maximum must be at least 1!", "ERROR");
				valid = false;
	      }
	      if(minimum > maximum){
	    	  window.maxPlayers.setText("4");
	    	  window.minPlayers.setText("2");
				window.popUpMsg("Minimum amount of players must be less than the maximum!", "ERROR");
				valid = false;
	      }
	    if(!valid){
	    	window.popUpMsg("Invalid player counts!", "ERROR");
			System.out.println("Invalid players counts... Aborted save");
			return false;
	    }
	    Boolean useTeams = window.useTeams.isSelected();
	    Boolean customTeamNamesInArenas = window.arenaCustomTeams.isSelected();
	    String teamsRaw = window.teams.getText();
	    String[] teams = teamsRaw.split(",");
	    for(String team:teams){
	    	if(team.contains(" ")){
	    		window.popUpMsg("Team named "+team+" shouldn't contain spaces in it's name!", "ERROR");
				System.out.println("Teams cannot contain spaces... Aborted save");
				return false;
	    	}
	    }
	    int teamCount = teams.length;
	    if(useTeams && teamCount < 1){
	    	window.popUpMsg("Invalid teams-Teams are selected, but there are none!", "ERROR");
			System.out.println("Invalid teams... Aborted save");
			return false;
	    }
	    game.setOption("header.name", new Option(gname, OptionType.GAME));
	    game.setOption("playerCount.min", new Option(minimum, OptionType.GAME));
	    game.setOption("playerCount.max", new Option(maximum, OptionType.GAME));
	    game.setOption("teams.use", new Option(useTeams, OptionType.GAME));
	    game.setOption("teams.number", new Option(teamCount, OptionType.GAME));
	    if(customTeamNamesInArenas){
	    game.setOption("teams.teams", new Option(teams, OptionType.ARENA));
	    }
	    else{
	    game.setOption("teams.teams", new Option(teams, OptionType.GAME));
	    }
	    //TODO write to file
	    String loc = new File("").getAbsolutePath();
	    new File(loc + File.separator + "Minigamez" + File.separator + "myGames").mkdirs();
	    String path = loc + File.separator + "Minigamez" + File.separator + "myGames" + File.separator + gname+".minigame"; //TODO find out path to write file
	    System.out.println("Save path: "+path);
	    File toSave = new File(path);
	    if(!(toSave.exists() || toSave.length() > 0)){
	    	try {
				toSave.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path));
			oos.writeObject(game);
			oos.flush();
			oos.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
	    System.out.println("Saved successfully!");
	    window.popUpMsg("Successfully saved to: "+toSave.getAbsolutePath()+"!", "Saved!");
	    return true;
	}

}
