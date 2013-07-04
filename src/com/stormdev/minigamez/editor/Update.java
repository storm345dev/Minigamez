package com.stormdev.minigamez.editor;

import java.awt.Toolkit;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.SwingWorker;

public class Update extends SwingWorker<Void,Void> {
	public String downloadUrl = null;;
	public int length = 100;
	VersionRetriever verRet = null;
	public Update(String downloadUrl, VersionRetriever verRet){
		this.downloadUrl = downloadUrl;
		this.verRet = verRet;
		try {
			URL update = new URL(downloadUrl);
			this.length = (update.openConnection().getContentLength()/1024)+1; //In KB
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Void doInBackground() throws Exception {
		System.out.println("Started update...");
		System.out.println("Length: "+this.length);
		URL update = new URL(downloadUrl);
		//URLConnection connection = update.openConnection();
		InputStream inUp = new BufferedInputStream(update.openStream());
		 ByteArrayOutputStream outUp = new ByteArrayOutputStream();
		 byte[] buf = new byte[1024]; //1024 bytes = 1KB
		 int n = 0;
		 int comp = -1; 
		 while (-1!=(n=inUp.read(buf)))
		 {
			 
			 System.out.println("Downloaded "+(comp) + "KB /" + this.length+"KB");
			 comp = comp + 1;
			 if(comp >= 0 ){
				//progress must be in %
				 int percent = (int) ((Double.parseDouble(""+comp)/Double.parseDouble(""+this.length))*100);
				 this.setProgress(percent); 
			 }
			 
		    outUp.write(buf, 0, n);
		 }
		 outUp.close();
		 inUp.close();
		 byte[] responseUp = outUp.toByteArray();
		 String loc = new File("").getAbsolutePath();
		 FileOutputStream fos = new FileOutputStream(new File(loc + File.separator + "minigamez.jar"));
		     fos.write(responseUp);
		     fos.close();
		 System.out.println("Downloaded content!");
		     //done();
		return null;
	}
	public int getLengthOfTask(){
		return this.length;
	}
	public void done() {
	    //Tell progress listener to stop updating progress bar.
	    Toolkit.getDefaultToolkit().beep();
	    this.verRet.progressBar.setValue(this.verRet.progressBar.getMinimum());
	    this.verRet.onComplete();
	    return;
	}



}
