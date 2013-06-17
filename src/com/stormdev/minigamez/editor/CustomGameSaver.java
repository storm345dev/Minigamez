package com.stormdev.minigamez.editor;

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
	    	  window.maxPlayers.setText("2");
				window.popUpMsg("Maximum must be at least 1!", "ERROR");
				valid = false;
	      }
	    if(!valid){
	    	window.popUpMsg("Invalid player counts!", "ERROR");
			System.out.println("Invalid players counts... Aborted save");
			return false;
	    }
	    game.setOption("header.name", new Option(gname, OptionType.GAME));
	    game.setOption("playerCount.min", new Option(minimum, OptionType.GAME));
	    game.setOption("playerCount.max", new Option(maximum, OptionType.GAME));
	    //TODO write to file
	    return true;
	}

}
