package com.stormdev.minigamez.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MinigameCommandExecutor implements CommandExecutor{
	private minigamez plugin;
	public MinigameCommandExecutor(minigamez instance){
		this.plugin = minigamez.plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
			String[] args) {
		//commands
		return false;
	}

}
