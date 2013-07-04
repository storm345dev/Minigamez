package com.stormdev.minigamez.editor;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class CustomGameCreator {
	public static void main(String[] args) {
		System.out.println("Loading CustomGameCreator...");
		System.out.println("Opening window...");
		WindowArea window = new WindowArea();
		window.setIconImage(new ImageIcon(WindowArea.class.getResource("/com/stormdev/minigamez/editor/1.png")).getImage());
		System.out.println("Window open!");
		System.out.println("Loaded!");
		UpdateChecker checker = new UpdateChecker(window);
		checker.execute(); //Check for updates async
        return;
	}

}
