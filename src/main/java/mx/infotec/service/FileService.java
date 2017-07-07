package mx.infotec.service;

import java.io.File;
import java.util.Properties;

import mx.infotec.exception.PropertiesException;

public interface FileService {
	
	Properties getPropertiesFromFile(String file) throws PropertiesException;
	
	boolean writePropertiesFile(Properties properties, String outputFile) throws PropertiesException;
	
	boolean validateFile(String to) throws PropertiesException;
	
	boolean createFile(File file) throws PropertiesException;

}
