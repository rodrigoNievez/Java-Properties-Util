package mx.infotec.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.infotec.exception.PropertiesException;
import mx.infotec.model.ResumeModel;

@Service
public class MergeServiceImpl implements MergeService {
	
	private Logger LOG = LoggerFactory.getLogger(MergeServiceImpl.class);
	
	@Autowired
	private FileService fileService;
	
	
	@Override
	public ResumeModel mergePropertes(String from, String to) throws PropertiesException {
		Properties fromProperties = null;
		Properties toProperties = null;
		Properties back = null;
		ResumeModel resume = null;
		try {
			resume = new ResumeModel();
			resume.setUpdatedProperties(new Properties());
			resume.setMergedProperties(new Properties());
			
			fromProperties = fileService.getPropertiesFromFile(from);
			toProperties = fileService.getPropertiesFromFile(to);
			back = toProperties;
			
			for (Entry<Object, Object> element : fromProperties.entrySet()) {
				String key = String.valueOf(element.getKey());
				String value = String.valueOf(element.getValue());
				if (toProperties.containsKey(key)) {
					toProperties.setProperty(key, value);
					resume.getUpdatedProperties().put(key, value);
				} else {
					toProperties.put(key, value);
					resume.getMergedProperties().put(key, value);
				}
				
			}
			
			fileService.writePropertiesFile(toProperties, to);
			back = null;
			return resume;
			
		} catch (Exception e) {
			fileService.writePropertiesFile(back, to);
			LOG.error("Error al hacer el merge de los archivos, causa: {} ", e);
			throw new PropertiesException(e);
		}
		
	}

	@Override
	public List<ResumeModel> meregeMultilpeFiles(String from, String container) throws PropertiesException {
		List<ResumeModel> resume = null;
		File fileContainer;
		try {
			fileContainer = new File(container);
			Scanner fileScanner = new Scanner(fileContainer);
			resume = new ArrayList<>();
			
			while (fileScanner.hasNextLine()) {
				String to = fileScanner.nextLine();
				resume.add(mergePropertes(from, to));
			}
			fileScanner.close();
			return resume;
		} catch(Exception e) {
			LOG.error("Error al hacer el merge de los archivos, causa: {} ", e);
			throw new PropertiesException(e);
		}
		
	}
	
	@Override
	public ResumeModel mergePropertiesWhitSkip(Properties properties, String to, Properties skip) throws PropertiesException {
		ResumeModel resume = new ResumeModel();
		resume.setAddedProperties(new Properties());
		resume.setUpdatedProperties(new Properties());
		Properties back = null;
		try {
			Properties toProperties = fileService.getPropertiesFromFile(to);
			back = toProperties;
			for (Entry<Object, Object> item : properties.entrySet()) {
				String key = String.valueOf(item.getKey());
				String value = String.valueOf(item.getValue());
				if (!skip.contains(key)) {
					continue;
				} else {
					
					if (toProperties.containsKey(key)) {
						toProperties.setProperty(key, value);
						resume.getUpdatedProperties().setProperty(key, value);
					} else {
						toProperties.put(key, value);
						resume.getAddedProperties().setProperty(key, value);
					}
				}
			}
			fileService.writePropertiesFile(toProperties, to);
		} catch (Exception e) {
			LOG.error("Error al hacer el merge con los archivos, causa: ", e);
			if (back != null) {
				restoreBackup(back, to);
			}
			throw new PropertiesException(e);
		}
		back = null;
		return resume;
	}
	
	@Override
	public ResumeModel mergePropertiesWhitSkipFromFile(String from, String to, String skip) throws PropertiesException {
		try {
			Properties fromProperties = fileService.getPropertiesFromFile(from);
			Properties skipProperties = fileService.getPropertiesFromFile(skip);
			return mergePropertiesWhitSkip(fromProperties, to, skipProperties);
		} catch (Exception e) {
			LOG.error("Error al hacer un merge desde los archivos, causa: ",e);
			throw new PropertiesException(e);
		}
	}
	
	@Override
	public List<String> mergeMultipeFilesWhitSkipFromFile(String from, String container, String skip) throws PropertiesException {
		File fileContainer = null;
		Scanner fileScanner = null;
		List<String> resume = new ArrayList<>();
		try {
			File file = new File(container);
			fileScanner = new Scanner(file);
			while (fileScanner.hasNext()) {
				String currentFile = fileScanner.nextLine();
				fileContainer = new File(currentFile);
				mergePropertiesWhitSkipFromFile(from, currentFile, skip);
				resume.add(fileContainer.getName());
			}
			fileScanner.close();
		} catch (Exception e) {
			LOG.error("Error al insertar las nuevas propiedades en multiples archivos, causa: {} ", e);
		}
		return resume;
	}

	@Override
	public boolean insertNewProperties(Properties properties, String to) throws PropertiesException {
		Properties toProperties = null;
		Properties back = null;
		try {
			toProperties = fileService.getPropertiesFromFile(to);
			back = toProperties;
			toProperties.putAll(properties);
			return true;
		} catch (Exception e) {
			LOG.error("Error al insertar las nuevas propiedades, causa: {} ", e);
			if (back != null) {
				restoreBackup(back, to);
			}
			return false;
		}
		
	}

	@Override
	public boolean insertNewPropertiesFromFile(String from, String to) throws PropertiesException {
		Properties fromProperties = null;
		try {
			fromProperties = fileService.getPropertiesFromFile(from);
			return insertNewProperties(fromProperties, to);
			
		} catch (Exception e) {
			LOG.error("Error al insertar las nuevas propiedades desde archivo, causa: {} ", e);
			throw new PropertiesException(e);
		}
	}
	
	@Override
	public List<String> insertMultipleProperties(String from, String container) throws PropertiesException {
		File fileContainer = null;
		Scanner fileScanner = null;
		List<String> resume = new ArrayList<>();
		try {
			fileScanner = new Scanner(container);
			while (fileScanner.hasNext()) {
				String currentFile = fileScanner.nextLine();
				fileContainer = new File(currentFile);
				if (insertNewPropertiesFromFile(from, currentFile)) {
					resume.add(fileContainer.getName());
				}
			}
			fileScanner.close();
		} catch (Exception e) {
			LOG.error("Error al insertar las nuevas propiedades en multiples archivos, causa: {} ", e);
		}
		return resume;
	}

	@Override
	public ResumeModel updateProperties(Properties properties, String to) throws PropertiesException {
		ResumeModel resume = null;
		Properties backup = null;
		try {
			Properties toProperties = fileService.getPropertiesFromFile(to);
			backup = toProperties;
			
			resume = new ResumeModel();
			resume.setUpdatedProperties(new Properties());
			resume.setMissingProperties(new Properties());
			
			for (Entry<Object, Object> item : properties.entrySet()) {
				String key = String.valueOf(item.getKey());
				String value = String.valueOf(item.getValue());
				if (toProperties.contains(value)) {
					toProperties.setProperty(key, value);
					resume.getUpdatedProperties().put(key, value);
				} else {
					resume.getMissingProperties().put(key, value);
				}
			}
			fileService.writePropertiesFile(toProperties, to);
			return resume;
		} catch (Exception e) {
			LOG.error("Error al actualizar propiedades en archivo, causa: {} ", e);
			if (backup != null) {
				restoreBackup(backup, to);
			}
			throw new PropertiesException(e);
		}

	}

	@Override
	public ResumeModel updatePropertiesFromFile(String from, String to) throws PropertiesException {
		
		try {
			Properties fromProperties = fileService.getPropertiesFromFile(from);
			return updateProperties(fromProperties, to);
		} catch (Exception e) {
			LOG.error("Error al actualizar propiedades en archivo, causa: {} ", e);
			throw new PropertiesException(e);
		}
	}
	
	@Override
	public List<String> updateMultipleProperties(String from, String container) throws PropertiesException {
		
		Scanner fileScanner = null;
		List<String> resume = new ArrayList<>();
		try {
			fileScanner = new Scanner(container);
			
			while (fileScanner.hasNext()) {
				String currentFile = fileScanner.nextLine();
				File fileContainer = new File(currentFile);
				updatePropertiesFromFile(from, currentFile);
				resume.add(fileContainer.getName());
				
			}
			fileScanner.close();
		} catch (Exception e) {
			LOG.error("Error al insertar las nuevas propiedades en multiples archivos, causa: {} ", e);
		}
		return resume;
	}
	
	private boolean restoreBackup(Properties properties, String to) {
		try {
			fileService.writePropertiesFile(properties, to);
			return true;
		} catch (PropertiesException e) {
			LOG.error("Error al restaurar el archivo de propiedades, causa: {}",e);
			return false;
		}
	}

}
