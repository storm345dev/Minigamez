package com.stormdev.minigamez.editor;

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
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.bukkit.ChatColor;

import com.stormdev.minigamez.utils.GetStringFromUrl;

public class VersionRetriever {

	String yaml = "";
	WindowArea window = null;
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
    	System.out.println("Updating...");
    	try {
			URL update = new URL(downloadUrl);
			 InputStream inUp = new BufferedInputStream(update.openStream());
			 ByteArrayOutputStream outUp = new ByteArrayOutputStream();
			 byte[] buf = new byte[1024];
			 int n = 0;
			 while (-1!=(n=inUp.read(buf)))
			 {
			    outUp.write(buf, 0, n);
			 }
			 outUp.close();
			 inUp.close();
			 byte[] responseUp = outUp.toByteArray();
			 String loc = new File("").getAbsolutePath();
			 FileOutputStream fos = new FileOutputStream(new File(loc + File.separator + "minigamez.jar"));
			     fos.write(responseUp);
			     fos.close();
			     System.out.println("Updated!");
			     JOptionPane.showMessageDialog(null,"Updated successfully!","Updater",
						  JOptionPane.WARNING_MESSAGE);
			     System.exit(1);
					return;
		} catch (Exception e) {
			e.printStackTrace();
			this.window.popUpMsg("Failed to update!", "Updater");
			return;
		}
    }
}
