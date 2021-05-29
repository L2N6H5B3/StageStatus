package stagestatus;


import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

/*
 * Author: Luke Bradtke
 * Since: 1.2
 * Version: 1.5
 */

public class WebsocketServer extends WebSocketServer {

    private Set<WebSocket> conns;
    private Hashtable<WebSocket, String> connNames;
    private String serverStatus = "Stopped";

    public WebsocketServer(String port) {
        super(new InetSocketAddress(Integer.parseInt(port)));
        conns = new HashSet<>();
        connNames = new Hashtable<WebSocket, String>();
        serverStatus = "Starting";
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
    	System.out.println("Client Connecting.");
    	conns.add(conn);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        Main.createClient(connNames.get(conn), "SocketDisconnected");
        serverStatus = "Stopped";
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
    	
    	// Splits incoming message into array of Strings
    	String messageData[] = message.split(":");
    	
    	// Get the message request and data
    	String request = messageData[0];
    	String data = messageData[1];
    	
    	// If this is a device request
    	if (request.equals("device")) {
    		// Create a client
    		Main.createClient(data, "SocketDisconnected");
    		// Add the connection and client name
    		connNames.put(conn, data);
    		// Request status from the device
    		for (WebSocket sock : conns) {
    			try {
	              	sock.send("status");
	  			} catch (JSONException e) {
	  				e.printStackTrace();
	  			}
	        }
    		
    	}
    	// If this is a status request
    	else if (request.equals("status")) {
    		// Create a client
    		Main.createClient(connNames.get(conn), data);
    	}
    	
    	
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            conns.remove(conn);
        }
        serverStatus = "Error";
    }

	@Override
	public void onStart() {
		serverStatus = "Running";
	}
	
	public String ServerStatus() {
		return serverStatus;
	}
}