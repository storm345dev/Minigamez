package com.stormdev.minigamez.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
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
import java.util.ArrayList;

import javax.swing.JSplitPane;

public class WindowArea extends JFrame implements ActionListener,ChangeListener {
	JPanel pane = new JPanel();
	JPanel optionsPane = new JPanel(new GridLayout(0,1));
	JPanel optionSettingsPane = new JPanel();
	JLabel spacer = new JLabel(" ");
	JLabel spacer1 = new JLabel(" ");
	JLabel spacer2 = new JLabel(" ");
	JLabel spacer3 = new JLabel(" ");
	JLabel spacer4 = new JLabel(" ");
	JLabel spacer5 = new JLabel(" ");
	JLabel spacer6 = new JLabel(" ");
	JLabel spacer7 = new JLabel(" ");
	JLabel spacer8 = new JLabel(" ");
	JLabel spacer9 = new JLabel(" ");
	Locations locations = new Locations(this);
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
	  private final JButton btnPlayerCount = new JButton("Player settings");
	  private final JButton btnTeamSettings = new JButton("Team settings");
	  private final JButton btnLocationSettings = new JButton("Locations");
	  private final JButton btnObjectives = new JButton("Objectives");
	  private final JButton btnRules = new JButton("Game Rules");
	  private final JButton btnPoints = new JButton("Points");
	  private final JButton btnCustomEvents = new JButton("Custom Events");
	  private JPanel optionsPlayerCount = new JPanel(new GridLayout(0,2));
	  JTextField minPlayers = new JTextField(16);
	  JTextField maxPlayers = new JTextField(16);
	  private JPanel optionsTeamSettings = new JPanel(new GridLayout(0,2));
	  private JPanel optionsLocationSettings = new JPanel(new GridLayout(0,2));
	  private JPanel optionsObjectives = new JPanel(new GridLayout(0,2));
	  private JPanel optionsRules = new JPanel(new GridLayout(0,2));
	  private JPanel optionsPoints = new JPanel(new GridLayout(0,2));
	  private JPanel optionsCustomEvents = new JPanel(new GridLayout(0,2));
	  JCheckBox useTeams = new JCheckBox("Use teams", true);
	  JCheckBox arenaCustomTeams = new JCheckBox("Allow arena's to customise team names (but not amount)", false);
	  JCheckBox usePoints = new JCheckBox("Use Points", true);
	  JCheckBox allowNegativePoints = new JCheckBox("Allow negative points", true);
	  JRadioButton playerPoints = new JRadioButton("Per player", true);
	  JRadioButton teamPoints = new JRadioButton("Per team", false);
	  JTextField teams = new JTextField(16);
	  RuleManager rules = null;
	  public ObjectiveManager objectives = new ObjectiveManager(this);
	  public CustomEvents customEvents = null;
	  WindowArea() // the frame constructor method
	  {
	    super("Minigame Editor"); 
	    this.setIconImage(new ImageIcon(WindowArea.class.getResource("/com/stormdev/minigamez/editor/1.png")).getImage());
	    optionsPane.add(btnPlayerCount);
	    optionsPane.add(btnTeamSettings);
	    optionsPane.add(btnLocationSettings);
	    optionsPane.add(btnCustomEvents);
	    optionsPane.add(btnObjectives);
	    optionsPane.add(btnRules);
	    optionsPane.add(btnPoints);
	    setBounds(100,100,1000,600);
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
	    btnPlayerCount.addActionListener(this);
	    btnLocationSettings.addActionListener(this);
	    btnObjectives.addActionListener(this);
	    btnRules.addActionListener(this);
	    btnPoints.addActionListener(this);
	    btnCustomEvents.addActionListener(this);
	  //start player options page
	    JLabel playerCountOptionsTitle = new JLabel("Player Settings:");
	    playerCountOptionsTitle.setFont(title);
	    minPlayers.setText("2");
	    minPlayers.addActionListener(this);
	    maxPlayers.setText("4");
	    maxPlayers.addActionListener(this);
	    optionsPlayerCount.add(playerCountOptionsTitle); optionsPlayerCount.add(spacer); //optionsPlayerCount.add(spacer);  optionsPlayerCount.add(spacer); 
	    optionsPlayerCount.add(new JLabel("Minimum players:")); optionsPlayerCount.add(minPlayers); //optionsPlayerCount.add(spacer);
	    optionsPlayerCount.add(new JLabel("Maximum players:")); optionsPlayerCount.add(maxPlayers); //optionsPlayerCount.add(spacer);
//optionsPlayerCount.add(spacer);
	    //end player options page
	  //start teams page
	    btnTeamSettings.addActionListener(this);
	    JLabel teamSettingsTitle = new JLabel("Team Settings:");
	    teamSettingsTitle.setFont(title);
	    optionsTeamSettings.add(teamSettingsTitle); optionsTeamSettings.add(spacer1); //optionsPlayerCount.add(spacer);  optionsPlayerCount.add(spacer); 
	    optionsTeamSettings.add(useTeams); optionsTeamSettings.add(spacer2);
	    teams.setText("Blue,Red");
	    optionsTeamSettings.add(new JLabel("Team names(separated by ','):")); optionsTeamSettings.add(teams); //optionsPlayerCount.add(spacer);
	    optionsTeamSettings.add(arenaCustomTeams);
//optionsPlayerCount.add(spacer);
	    //end teams page
	    //start locations settings page
	    JLabel locationOptionsTitle = new JLabel("Location settings:");
	    locationOptionsTitle.setFont(title);
	    optionsLocationSettings.add(locationOptionsTitle); optionsLocationSettings.add(spacer3);
	    optionsLocationSettings.add(locations);
	    //TODO
	    //end locations settings page
	    //start objectives settings page
	    JLabel ObjectivesTitle = new JLabel("Objectives:");
	    ObjectivesTitle.setFont(title);
	    optionsObjectives.add(ObjectivesTitle); optionsObjectives.add(new JLabel(" "));
	    objectives = new ObjectiveManager(this);
	    optionsObjectives.add(objectives);
	    
	    //end objectives settings page
	  //start rules settings page
	    JLabel rulesTitle = new JLabel("Game Rules:");
	    rulesTitle.setFont(title);
	    this.rules = new RuleManager(this);
	    optionsRules.add(rulesTitle); optionsRules.add(new JLabel(" "));
	    optionsRules.add(this.rules);
	    this.rules.draw();
	    //end rules settings page
	  //start pts settings page
	    JLabel ptsTitle = new JLabel("Points:");
	    ptsTitle.setFont(title);
        optionsPoints.add(ptsTitle); optionsPoints.add(new JLabel(" "));
        optionsPoints.add(usePoints); optionsPoints.add(allowNegativePoints);
        ButtonGroup scoreByWho = new ButtonGroup();
        scoreByWho.add(playerPoints); scoreByWho.add(teamPoints);
        JPanel radioSel = new JPanel(new FlowLayout());
        radioSel.add(playerPoints);
        radioSel.add(teamPoints);
        optionsPoints.add(new JLabel("Calculate scores:"));optionsPoints.add(radioSel);
	    //end pts settings page
      //start evt settings page
	    JLabel evtTitle = new JLabel("Custom Events:");
	    evtTitle.setFont(title);
	    this.customEvents = new CustomEvents(this);
        optionsCustomEvents.add(evtTitle); optionsCustomEvents.add(new JLabel(" "));
        optionsCustomEvents.add(this.customEvents);
	    //end evt settings page
	  }
	  public ArrayList<String> getTeams(){
		  String teamsRaw = this.teams.getText();
		    String[] teams = teamsRaw.split(",");
		    ArrayList<String> toReturn = new ArrayList<String>();
		    for(String team:teams){
		    	toReturn.add(team);
		    }
		    return toReturn;
	  }
	  public void actionPerformed(ActionEvent event)
	  {
	    Object source = event.getSource();
	    if (source == saveButton)
	    {
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
	    if (source == btnTeamSettings)
	    {
	    	System.out.println("Option: Team Settings");
	    	setPanelComponent(optionSettingsPane, optionsTeamSettings);
	      return;
	    }
	    if (source == btnLocationSettings)
	    {
	    	System.out.println("Option: Location Settings");
	    	setPanelComponent(optionSettingsPane, optionsLocationSettings);
	    	locations.draw();
	      return;
	    }
	    if (source == btnObjectives)
	    {
	    	System.out.println("Option: Objectives");
	    	setPanelComponent(optionSettingsPane, optionsObjectives);
	    	objectives.draw();
	      return;
	    }
	    if (source == btnRules)
	    {
	    	System.out.println("Option: Game Rules");
	    	setPanelComponent(optionSettingsPane, optionsRules);
	      return;
	    }
	    if (source == btnPoints)
	    {
	    	System.out.println("Option: Game Points");
	    	setPanelComponent(optionSettingsPane, optionsPoints);
	      return;
	    }
	    if (source == btnCustomEvents)
	    {
	    	System.out.println("Option: Game Custom Events");
	    	setPanelComponent(optionSettingsPane, optionsCustomEvents);
	      return;
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
    public void refresh(){
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
