package com.stormdev.minigamez.editor;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

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
    	String downloadUrl = getDownloadUrl();
    	if(downloadUrl == null){
    		this.window.popUpMsg("Unable to retrieve update!", "ERROR");
    		return;
    	}
    	
    }
}
