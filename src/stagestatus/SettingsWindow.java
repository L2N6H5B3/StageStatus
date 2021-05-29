package stagestatus;


import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;


/*
 * Author: Luke Bradtke
 * Since: 1.0
 * Version: 1.5
 */


public class SettingsWindow {

	// Initializes settings window
	static JFrame standardSettingWindow;
	
	// Initializes settings panel
	static JPanel standardContentPanel;
	
	// Initializes text labels
	static JLabel commPortLabel;
	
	// Initializes text fields
	static JTextField commPortField;
	
	// Initializes comboBoxes
	static JComboBox<String> multiDirectionCombo;
	
	// Initializes buttons
	static JButton standardSaveButton, standardCancelButton;
	
	// Initializes comboBox options
	static String[] comboOptions = new String[] {"Yes", "No"};
	
	public SettingsWindow() {
	
		// Creates a new JFrame for holding standard JPanel component
		standardSettingWindow = new JFrame("StageStatus Settings");
		
		// Creates a new JPanel for holding standard Label and Field components
		standardContentPanel = new JPanel();
		
		// Creates a new GridLayout with 2 Columns
		standardContentPanel.setLayout(new GridLayout(0, 2));
				
		// Sets border
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		standardContentPanel.setBorder(padding);
		
		// Creates new text fields for each setting
		commPortField = new JTextField("",20);
	    
	    // Creates new text labels for each setting
	    commPortLabel = new JLabel("Communication Port: ");
	    
	    // Creates new comboBoxes for settings
	    multiDirectionCombo = new JComboBox<>(comboOptions);
		
	    // Adds Labels and Fields to the Settings Window Panel
	    standardContentPanel.add(commPortLabel);
	    standardContentPanel.add(commPortField);
	    
	    // Adds Cancel button
	    standardCancelButton = new JButton("Cancel");
	    standardCancelButton.addActionListener((ActionEvent event) -> {
	    	// Removes Settings Frame
	    	standardSettingWindow.dispose();
        });

	    // Adds Save button
	    standardSaveButton = new JButton("Save");
	    standardSaveButton.addActionListener((ActionEvent event) -> {
	    	// Saves each new property to the PropertiesReader
	    	Main.props.setProperty("comm-port", commPortField.getText());
	    	
	    	// Saves all Properties to file
	    	Main.props.saveProperties();
	    	// Removes Settings Frame
	    	standardSettingWindow.dispose();
        });
	    
	    // Adds buttons to Settings Window
	    standardContentPanel.add(standardCancelButton);
	    standardContentPanel.add(standardSaveButton);

	    // Assigns Panels to the Window Frames
	    standardSettingWindow.setContentPane(standardContentPanel);
	    
	    // Finalizes Window Frame items
	    standardSettingWindow.pack();
	}
	
	
	public void showSettings() {
		
		// Refreshes data from PropertiesReader to populate the labels and fields
		commPortField.setText(Main.props.getProperty("comm-port"));
	    
	    // Shows Settings Frame
	    standardSettingWindow.setVisible(true);
	    standardSettingWindow.setAlwaysOnTop(true);
	}
}
