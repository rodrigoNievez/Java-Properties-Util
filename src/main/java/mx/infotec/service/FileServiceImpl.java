package mx.infotec.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import mx.infotec.exception.PropertiesException;

@Service
public class FileServiceImpl implements FileService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	

	@Override
	public Properties getPropertiesFromFile(String file) throws PropertiesException {
		File propertiesFile = new File(file);
		Properties properties = null;
		FileInputStream fileInput = null;
		if (propertiesFile.exists()) {
			try {
				fileInput = new FileInputStream(propertiesFile);
				properties = new Properties();
				properties.load(fileInput);
				fileInput.close();
			} catch (FileNotFoundException e) {
				LOGGER.error("Error al leer el archivo, causa: ", e);
				throw new PropertiesException(e);
			} catch (IOException e) {
				LOGGER.error("Error al leer las propiedades, causa: ", e);
				throw new PropertiesException(e);
			}
		}

		return properties;
	}

	@Override
	public boolean writePropertiesFile(Properties properties, String outputFile) throws PropertiesException {
		File file = new File(outputFile);		
		try {
			FileOutputStream fileOutput = new FileOutputStream(file);
			Properties sortedProperties = sortProperties(properties);
			sortedProperties.store(fileOutput, "");
			fileOutput.close();
			return true;
		} catch (FileNotFoundException e) {
			LOGGER.error("Error al leer el archivo, causa: ", e);
			throw new PropertiesException(e);
		} catch (IOException e) {
			LOGGER.error("Error al guardar el archivo de propiedades, causa: ", e);
			throw new PropertiesException(e);
		} 
	}
	
	@Override
	public boolean validateFile(String to) throws PropertiesException {
		return false;
	}
	
	@Override
	public boolean createFile(File file) throws PropertiesException {
		file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			return true;
		} catch (IOException e) {
			LOGGER.error("Error al generar el archivo de propiedades, causa: ", e);
			throw new PropertiesException(e);
		}
	}
	
	private Properties sortProperties(Properties properties) {
		Properties sortedProperties = new Properties() {
			private static final long serialVersionUID = 1L;

			@Override 
			public synchronized Enumeration<Object> keys() {
				return Collections.enumeration(new TreeSet<Object>(super.keySet()));
			}
		};
		sortedProperties.putAll(properties);
		return sortedProperties;
	}

}
