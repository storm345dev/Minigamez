package com.stormdev.minigamez.editor;

import javax.swing.SwingWorker;

public class UpdateChecker extends SwingWorker<Void,Void> {
    WindowArea window = null;
    public UpdateChecker(WindowArea area){
    	this.window = area;
    }
	@Override
	protected Void doInBackground() throws Exception {
		VersionRetriever verRet = new VersionRetriever(this.window);
		verRet.checkForUpdate();
		return null;
	}



}
