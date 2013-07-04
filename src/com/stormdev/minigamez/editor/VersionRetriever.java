package com.stormdev.minigamez.editor;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.ProgressMonitorInputStream;

import org.bukkit.ChatColor;

import com.stormdev.minigamez.utils.GetStringFromUrl;

public class VersionRetriever implements PropertyChangeListener {
	JProgressBar progressBar;
	String yaml = "";
	Update update = null;
	WindowArea window = null;
	JPanel progressPanel = null;
	public VersionRetriever(WindowArea area){
		this.window = area;
		InputStream in = null;
		String yaml = "";
		try {
			in = getClass().getClassLoader().getResource("plugin.yml").openStream();
			java.util.Scanner s = new java.util.Scanner(in).useDelimiter("\\A");
		    yaml = s.hasNext() ? s.next() : "";
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		this.yaml = yaml;	
	}
    public Double getVersion(){
    	if(this.yaml.contains("version:")){
    		int i = this.yaml.indexOf("version:");
    		int e = this.yaml.indexOf(System.getProperty("line.separator"), i);
    		String versionText = this.yaml.substring(i, e);
    		versionText = versionText.trim();
    		Scanner s = new Scanner(versionText);
    		s.useDelimiter("version:\\s*");
    		return Double.parseDouble(s.next());
    	}
    	else{
    	return 1.0;
    	}
    }
    public Double getLatestVersion(){
    	//https://dl.dropboxusercontent.com/u/147363358/minigamez/version.txt
    	//https://dl.dropboxusercontent.com/u/147363358/minigamez/download.txt
    	try {
			return Double.parseDouble(GetStringFromUrl.get("https://dl.dropboxusercontent.com/u/147363358/minigamez/version.txt"));
		} catch (Exception e) {
		}
    	return getVersion();
    }
    public String getDownloadUrl(){
    	try {
			return GetStringFromUrl.get("https://dl.dropboxusercontent.com/u/147363358/minigamez/download.txt");
		} catch (Exception e) {
		}
    	return null;
    }
    public Boolean needUpdate(){
    	Double c = getVersion();
    	Double l = getLatestVersion();
    	if(c<l){
    		return true;
    	}
    	return false;
    }
    public void checkForUpdate(){
    	if(!needUpdate()){
    		return;
    	}
    	
    		if(!this.window.prompt("Detected an update for minigamez! Update now?", "Updater")){
    			return;
    		}
    	
    	String downloadUrl = getDownloadUrl().toLowerCase();
    	if(downloadUrl == null){
    		this.window.popUpMsg("Unable to retrieve update!", "ERROR");
    		return;
    	}
    	Boolean isBukkit = false;
    	if(downloadUrl.startsWith("http://dev.bukkit.org") || downloadUrl.startsWith("https://dev.bukkit.org")){
    		isBukkit = true;
    	}
    	Boolean isJar = false;
    	if(downloadUrl.endsWith(".jar")){
    		isJar = true;
    	}
    	if(isJar && isBukkit){
    		this.window.popUpMsg("Requested file is not approved by bukkit yet!", "Updater");
    			return;
    		
    	}
    	if(isJar && !isBukkit){
    		if(!this.window.prompt("This version is not uploaded to bukkit yet! Do you trust this source: \n"+downloadUrl+"\nProceed?", "Updater")){
    			return;
    		}
    	}
    	if(!isBukkit && !isJar){
    		this.window.popUpMsg("Download is invalid?", "Updater");
			return;
    	}
    	this.window.splitPane.removeAll();
    	this.progressPanel = new JPanel(new GridLayout(0,1));
    	final JLabel loading = new JLabel("");
    	loading.setIcon(new ImageIcon(WindowArea.class.getResource("/com/stormdev/minigamez/editor/loading.gif")));
    	//progressPanel.add(loading);
    	this.window.splitPane.setRightComponent(progressPanel);
    	System.out.println("Updating...");
    	try {
    		this.update = new Update(downloadUrl, this);
    		update.addPropertyChangeListener(this);
    		progressBar = new JProgressBar(0, 100);
    		progressBar.setValue(0);
    		progressBar.setStringPainted(true);
    		JLabel title = new JLabel("Update Progress");
    		Font titleFont = new Font("Title", Font.BOLD, 36);
    		title.setFont(titleFont);
    		JPanel titlePanel = new JPanel(new FlowLayout());
    		titlePanel.add(title, JPanel.CENTER_ALIGNMENT);
    		progressPanel.add(titlePanel);
    		JPanel bar = new JPanel(new FlowLayout());
    		bar.add(progressBar);
    		progressPanel.add(bar);
    		progressBar.setVisible(true);
    		this.window.pane.removeAll();
    		this.window.pane.add(this.window.label);
    		this.window.refresh();
    		update.execute();
    		/*
			     System.out.println("Updated!");
			     JOptionPane.showMessageDialog(null,"Updated successfully!\nPlease reopen minigamez!","Updater",
						  JOptionPane.WARNING_MESSAGE);
			     System.exit(1);
			     */
					return;
		} catch (Exception e) {
			e.printStackTrace();
			this.window.popUpMsg("Failed to update!", "Updater");
			return;
		}
    }
    public void onComplete(){
    	System.out.println("Updated!");
	     JOptionPane.showMessageDialog(null,"Updated successfully!\nPlease reopen minigamez!","Updater",
				  JOptionPane.WARNING_MESSAGE);
	     JButton finish = new JButton("Finish!");
	     finish.setAction(new Action(){
	    	 @Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(1);
				}
				@Override
				public void addPropertyChangeListener(
						PropertyChangeListener arg0) {
				}
				@Override
				public Object getValue(String arg0) {
					return null;
				}
				@Override
				public boolean isEnabled() {
					return true;
				}
				@Override
				public void putValue(String arg0, Object arg1) {
				}
				@Override
				public void removePropertyChangeListener(
						PropertyChangeListener arg0) {
				}
				@Override
				public void setEnabled(boolean arg0) {
			}});
	     finish.setText("Finish!");
	     finish.setVisible(true);
	     JPanel finishPanel = new JPanel(new FlowLayout());
	     finishPanel.add(finish, JPanel.CENTER_ALIGNMENT);
	     this.progressPanel.add(finishPanel);
	     this.window.refresh();
    	return;
    }
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if ("progress" == event.getPropertyName()) {
	        int progress = (int) event.getNewValue();
	        progressBar.setValue(progress);
	        System.out.println(progress);
	        progressBar.setString(progress + "%");
		}
	}
}
