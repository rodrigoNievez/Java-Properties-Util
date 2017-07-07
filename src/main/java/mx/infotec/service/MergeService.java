package mx.infotec.service;

import java.util.List;
import java.util.Properties;

import mx.infotec.exception.PropertiesException;
import mx.infotec.model.ResumeModel;

public interface MergeService {
	
	ResumeModel mergePropertes(String from, String to) throws PropertiesException;
	
	List<ResumeModel> meregeMultilpeFiles(String from, String container) throws PropertiesException;
	
	ResumeModel mergePropertiesWhitSkip(Properties properties, String to, Properties skip) throws PropertiesException;
	
	ResumeModel mergePropertiesWhitSkipFromFile(String from, String to, String skip) throws PropertiesException;
	
	List<String> mergeMultipeFilesWhitSkipFromFile(String from, String container, String skip) throws PropertiesException;
	
	boolean insertNewProperties(Properties properties,String to) throws PropertiesException;
	
	boolean insertNewPropertiesFromFile(String from, String to) throws PropertiesException;
	
	List<String> insertMultipleProperties(String from, String container) throws PropertiesException;
	
	ResumeModel updateProperties(Properties properties, String to) throws PropertiesException;
	
	ResumeModel updatePropertiesFromFile(String from, String to) throws PropertiesException;
	
	List<String> updateMultipleProperties(String from, String container) throws PropertiesException;

}
