package com.stormdev.minigamez.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.text.Format;

import javax.swing.JSplitPane;

public class WindowArea extends JFrame implements ActionListener,ChangeListener {
	JPanel pane = new JPanel();
	//int numberOfOptions = 1; //TODO ALWAYS update this
	JPanel optionsPane = new JPanel(new GridLayout());
	JPanel optionSettingsPane = new JPanel();
	JLabel spacer = new JLabel(" ");
	  JLabel result = new JLabel();
	  JButton saveButton = new JButton();
	  JButton loadButton = new JButton();
	  JLabel gameNameLabel = new JLabel();
	  JTextField gameName = new JTextField(16);
	  private final JSeparator separator = new JSeparator();
	  private final JLabel label = new JLabel("");
	  private final JSplitPane splitPane = new JSplitPane();
	  private final JScrollPane scrollPane = new JScrollPane(optionsPane);
	  private JScrollPane scrollPane_1 = new JScrollPane(optionSettingsPane);
	  private final JButton btnPlayerCount = new JButton("Player count");
	  private final JButton savePlayerCount = new JButton("Validate!");
	  private JPanel optionsPlayerCount = new JPanel(new GridLayout(0,2));
	  JTextField minPlayers = new JTextField(16);
	  JTextField maxPlayers = new JTextField(16);
	  WindowArea() // the frame constructor method
	  {
	    super("Minigame Creator"); 
	    
	    optionsPane.add(btnPlayerCount);setBounds(100,100,1000,600);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Container con = this.getContentPane(); // inherit main frame
	    con.add(pane, BorderLayout.NORTH); // add the panel to frame
	    gameName.setEditable(true);
	    gameName.setVisible(true);
	    gameNameLabel.setText("Game Name:");
	    pane.setVisible(true);
	    label.setIcon(new ImageIcon(WindowArea.class.getResource("/com/stormdev/minigamez/editor/minigamez-logo.png")));
	    
	    pane.add(label);
	    pane.add(gameNameLabel);
	    pane.add(gameName);
	    //Top of editor
	    saveButton.addActionListener(this);
	    saveButton.setText("Create!");
	    loadButton.addActionListener(this);
	    loadButton.setText("Load!");
	    savePlayerCount.addActionListener(this);
	    savePlayerCount.setText("Save!");
	    pane.add(saveButton);
	    pane.add(loadButton);
	    pane.add(result);
	    
	    getContentPane().add(separator, BorderLayout.WEST);
	    
	    getContentPane().add(splitPane, BorderLayout.CENTER);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    splitPane.setLeftComponent(scrollPane);
	    scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    splitPane.setRightComponent(scrollPane_1);
	    splitPane.setDividerLocation(200);
	    setVisible(true); // display this frame
	    Font title = new Font("Title", Font.BOLD, 36);
	    //start player count options page
	    btnPlayerCount.addActionListener(this);
	    JLabel playerCountOptionsTitle = new JLabel("Player Count:");
	    playerCountOptionsTitle.setFont(title);
	    minPlayers.setText("2");
	    minPlayers.addActionListener(this);
	    maxPlayers.setText("4");
	    maxPlayers.addActionListener(this);
	    optionsPlayerCount.add(playerCountOptionsTitle); optionsPlayerCount.add(spacer); //optionsPlayerCount.add(spacer);  optionsPlayerCount.add(spacer); 
	    optionsPlayerCount.add(new JLabel("Minimum players:")); optionsPlayerCount.add(minPlayers); //optionsPlayerCount.add(spacer);
	    optionsPlayerCount.add(new JLabel("Maximum players:")); optionsPlayerCount.add(maxPlayers); //optionsPlayerCount.add(spacer);
	    optionsPlayerCount.add(savePlayerCount); //optionsPlayerCount.add(spacer);
	    //end player count options page
	  }
	  
	  public void actionPerformed(ActionEvent event)
	  {
	    Object source = event.getSource();
	    if (source == saveButton)
	    {
	    	//TODO save script
	    	Boolean success = this.save();
	    	if(success){
	    		result.setText("Saved!");
	    	}
	    	else{
	    		result.setText("Invalid!");
	    	}
	      return;
	    }
	    if (source == loadButton)
	    {
	    	//TODO load script
	      result.setText("Loaded!");
	      return;
	    }
	    if (source == btnPlayerCount)
	    {
	    	System.out.println("Option: Player Count");
	    	setPanelComponent(optionSettingsPane, optionsPlayerCount);
	      return;
	    }
	    if (source == savePlayerCount)
	    {
	    	Boolean valid = true;
	    System.out.println("Saving change in player count options");
	      String minP = minPlayers.getText();
	      int minimum = 2;
	      try {
			minimum = Integer.parseInt(minP);
		} catch (NumberFormatException e1) {
			minPlayers.setText("2");
			popUpMsg("Minimum must be a number!", "ERROR");
			valid = false;
		}
	      if(minimum < 1){
	    	  minPlayers.setText("2");
				popUpMsg("Minimum must be at least 1!", "ERROR");
				valid = false;
	      }
	    
	    String maxP = maxPlayers.getText();
	      int maximum = 2;
	      try {
			maximum = Integer.parseInt(maxP);
		} catch (NumberFormatException e1) {
			maxPlayers.setText("4");
			popUpMsg("Maximum must be a number!", "ERROR");
			valid = false;
		}
	      if(maximum < 1){
	    	  maxPlayers.setText("2");
				popUpMsg("Maximum must be at least 1!", "ERROR");
				valid = false;
	      }
	    if(!valid){
	    	return;
	    }
	    //save it
	    //this.save();
	    }
      }
	  public void popUpMsg(String msg, String title){
		  JOptionPane.showMessageDialog(null,msg,title,
			      JOptionPane.PLAIN_MESSAGE); setVisible(true);  // show something
	      return;
	  }
	  public void setPanelComponent(JPanel pane, Component replacement){
		  Component[] comps = pane.getComponents().clone();
		  for(Component c:comps){
			  pane.remove(c);
		  }
		  pane.add(replacement);
		  replacement.setVisible(true);
		  Rectangle size = getBounds();
		  pack();
		  setBounds(size);
		  return;
	  }

	@Override
	public void stateChanged(ChangeEvent event) {
		Object source = event.getSource();
	}
	public Boolean save(){
		return CustomGameSaver.save(this);
	}

	

}
