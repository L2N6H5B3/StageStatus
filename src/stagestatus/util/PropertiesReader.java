package stagestatus.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

/*
 * Author: Luke Bradtke
 * Since: 1.0
 * Version: 1.4
 */

public class PropertiesReader {
	
	static Properties props;
	
	public PropertiesReader() {
		File configFile = new File("config.properties");
		 
		try {
		    FileReader reader = new FileReader(configFile);
		    props = new Properties();
		    props.load(reader);
		    reader.close();
		} catch (FileNotFoundException ex) {
		    System.out.println("File Not Found... Please try again later.");
		} catch (IOException ex) {
		    System.out.println("I/O Error... Please try again later.");
		}
		
	}
	
	public void saveProperties() {
		File configFile = new File("config.properties");
		 
		try {
		    FileWriter writer = new FileWriter(configFile);
		    props.store(writer, "StageStatus V1.0 Configuration");
		    writer.close();
		} catch (FileNotFoundException ex) {
			System.out.println("File Not Found... Please try again later.");
		} catch (IOException ex) {
			System.out.println("I/O Error... Please try again later.");
		}
	}
	
	public String getProperty(String propertyID) {
		return props.getProperty(propertyID);
	}
	
	public void setProperty(String propertyID, String property) {
		props.setProperty(propertyID, property);
	}
}