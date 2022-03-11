package dataProviders;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {
	
	private Properties properties;
	String path = System.getProperty("user.dir");
	private final String propertyFilePath= path + "\\src\\test\\java\\config\\Configuration.properties";

	
	public ConfigFileReader(){
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		}		
	}
	
	public String getBaseURI(){
		String url = properties.getProperty("url");
		if(url!= null) return url;
		else throw new RuntimeException("baseURI not specified in the Configuration.properties file.");		
	}
	
	public String getEmployeeEndpoint() {		
		String employeeEndpoint = properties.getProperty("empEndpoint");
		if(employeeEndpoint!= null) return employeeEndpoint;
		else throw new RuntimeException("employee Endpoint not specified in the Configuration.properties file.");		
	}
	
	public String getSingleEmployeeEndpoint() {		
		String singleemployeeEndpoint = properties.getProperty("singleEmpEndpoint");
		if(singleemployeeEndpoint!= null) return singleemployeeEndpoint;
		else throw new RuntimeException("singleEmpEndpoint not specified in the Configuration.properties file.");		
	}
}
