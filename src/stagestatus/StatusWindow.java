package stagestatus;


import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


/*
 * Author: Luke Bradtke
 * Since: 1.0
 * Version: 1.0
 */


public class StatusWindow {

	// Initializes Status Window
	static JFrame statusWindow;
	
	// Initializes Status Panel
	static JPanel contentPanel;
	
	// Initializes Status Label
	static JLabel empty;

	// Initializes ImageIcons
	static ImageIcon connected, disconnected;
	
	// Initializes client Hashtable
	static Hashtable<JLabel, JLabel> clients;
	
	public StatusWindow() {
	
		// Create a new JFrame for holding standard JPanel component
		statusWindow = new JFrame("Stage Connections");
		statusWindow.setMinimumSize(new Dimension(200, 0));
		
		// Create a new JPanel for holding standard Label and Field components
		contentPanel = new JPanel();
		contentPanel.setLayout(new GridLayout(0, 1));

		// Set border
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		contentPanel.setBorder(padding);
		
		// Set Status Label
		empty = new JLabel("No connections yet...");
		
		// Set Image Icons
		connected = createImageIcon("img/connected.png");
		disconnected = createImageIcon("img/disconnected.png");
		
		// Create the client hashtable object
		clients = new Hashtable<JLabel, JLabel>();

		// Assign Label to Panel
		contentPanel.add(empty);
		
	    // Assign Panel to Window Frame
	    statusWindow.setContentPane(contentPanel);
	    
	    // Finalize Window Frame items
	    statusWindow.pack();
	}
	
	
	public void showStatus() {

	    // Shows Status Frame
	    statusWindow.setVisible(true);
	}
	
	public boolean clientExists(String device) {
		boolean foundClient = false;
		
		// Iterate through each current client
        for(JLabel key: clients.keySet()){
        	// If the JLabel Text is the same as the IP
        	if (device.equals(key.getText())) {
        		// Report client exists
				foundClient = true;
			}
        }
		return foundClient;
	}
	
	public void addClient(String device, int status) {
		
		// Create a new JPanel for the client
		JPanel client = new JPanel();
		client.setLayout(new GridLayout(0, 2));
		
		// Create a new JLabel for the client name
		JLabel clientName = new JLabel(device);
		clientName.setFont(clientName.getFont().deriveFont(clientName.getFont().getStyle() | Font.BOLD));
		
	    JLabel clientStatus;
	    // Connected to Server & Connected to ProPresenter
		if (status == 1) {
			clientStatus = new JLabel("Connected", connected, JLabel.HORIZONTAL);
			clientStatus.setHorizontalTextPosition(SwingConstants.LEFT);
		} 
		// Connected to Server & Not Connected to ProPresenter
		else if (status == 2) {
			clientStatus = new JLabel("Disconnected", disconnected, JLabel.HORIZONTAL);
			clientStatus.setHorizontalTextPosition(SwingConstants.LEFT);
		}
		// Not Connected to Server
		else {
			clientStatus = new JLabel("Unknown", disconnected, JLabel.HORIZONTAL);
			clientStatus.setHorizontalTextPosition(SwingConstants.LEFT);
		}
		
		// Add Client Name to Client Panel
		client.add(clientName);
		// Add Client Status to Client Panel
	    client.add(clientStatus);
	    
	    // Add the Client to the Hashtable
	    clients.put(clientName, clientStatus);
	    
	    // Remove the Empty Label
	    contentPanel.remove(empty);
	    
	    // If there is more than 1 client
	    if (clients.size() > 1) {
	    	// Add a Separator
		    contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
	    }
	    
	    // Add Client to the Status Window Panel
	    contentPanel.add(client);
			    
		// Finalizes Window Frame items
	    statusWindow.pack();
	}
	
	public void updateClient(String device, int status) {
		
		// Iterate through each current client
		clients.forEach((ipLabel, statusLabel) -> {
			if (device.equals(ipLabel.getText())) {
				JLabel clientStatus = statusLabel;
				
				// Connected to Server & Connected to ProPresenter
				if (status == 1) {
					clientStatus.setText("Connected");
					clientStatus.setIcon(connected);
				} 
				// Connected to Server & Not Connected to ProPresenter
				else if (status == 2) {
					clientStatus.setText("Disconnected");
					clientStatus.setIcon(disconnected);
				}
				// Not Connected to Server
				else {
					clientStatus.setText("Unknown");
					clientStatus.setIcon(disconnected);
				}
			}
        });

		// Finalizes Window Frame items
	    statusWindow.pack();
	}
	
	public boolean allConnected() {
		boolean allConnected = true;
		
		// Iterate through each current client
        for(JLabel key: clients.keySet()){
        	// If the client is disconnected
        	if (clients.get(key).getText().equals("Disconnected")) {
        		// Report client disconnected
        		allConnected = false;
			}
        }
		return allConnected;
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String path) {
	    java.net.URL imgURL = getClass().getResource(path);
	    if (imgURL != null) {
	        return new ImageIcon(imgURL);
	    } else {
	        System.err.println("Couldn't find file: " + path);
	        return null;
	    }
	}
	
}
