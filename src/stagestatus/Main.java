package stagestatus;

import java.io.IOException;
import java.text.ParseException;

import stagestatus.tray.TrayItem;
import stagestatus.util.PropertiesReader;

/*
 * Author: Luke Bradtke
 * Since: 1.0
 * Version: 1.0
 */

public class Main {

	public static PropertiesReader props;
	public static SettingsWindow settings;
	public static StatusWindow status;
	public static TrayItem tray;
	public static WebsocketServer wss;
	
	public static void main(String[] args) throws ParseException, IOException {
		// Hide the Java Icon in the Mac Dock
		System.setProperty("apple.awt.UIElement", "true");
		// Creates a new PropertyReader for storing and retrieving settings
		props = new PropertiesReader();
		// Creates a new SettingsWindow for editing settings
		settings = new SettingsWindow();
		// Creates a new StatusWindow for viewing connections
		status = new StatusWindow();
		// Creates a new TrayItem for access to About, Settings, and Program Control
		tray = new TrayItem();
		// Creates a new WebSocketServer
		wss = new WebsocketServer(props.getProperty("comm-port"));
		// Start the WebSocketServer
		wss.start();
		
	}
	
	
	public static void createClient(String device, String data) {
		if(!status.clientExists(device)) {
			switch(data) {
				case "StageConnected":
					status.addClient(device, 1);
					break;
				case "StageDisconnected":
					status.addClient(device, 2);
					break;
				case "SocketDisconnected":
					status.addClient(device, 3);
					break;
			}
		} else {
			switch(data) {
				case "StageConnected":
					status.updateClient(device, 1);
					break;
				case "StageDisconnected":
					status.updateClient(device, 2);
					break;
				case "SocketDisconnected":
					status.updateClient(device, 3);
					break;
			}
		}
		if (status.allConnected()) {
			tray.setTrayIconStatus(1);
		} else {
			tray.setTrayIconStatus(2);
		}
	}
}