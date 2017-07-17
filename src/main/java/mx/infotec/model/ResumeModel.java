package mx.infotec.model;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

public class ResumeModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Properties mergedProperties;
	private Properties addedProperties;
	private Properties updatedProperties;
	private Properties missingProperties;
	private String fileName;
	private boolean isError;
	/**
	 * @return the mergedProperties
	 */
	public Properties getMergedProperties() {
		return mergedProperties;
	}
	/**
	 * @param mergedProperties the mergedProperties to set
	 */
	public void setMergedProperties(Properties mergedProperties) {
		this.mergedProperties = mergedProperties;
	}
	/**
	 * @return the addedProperties
	 */
	public Properties getAddedProperties() {
		return addedProperties;
	}
	/**
	 * @param addedProperties the addedProperties to set
	 */
	public void setAddedProperties(Properties addedProperties) {
		this.addedProperties = addedProperties;
	}
	/**
	 * @return the updatedProperties
	 */
	public Properties getUpdatedProperties() {
		return updatedProperties;
	}
	/**
	 * @param updatedProperties the updatedProperties to set
	 */
	public void setUpdatedProperties(Properties updatedProperties) {
		this.updatedProperties = updatedProperties;
	}
	/**
	 * @return the missingProperties
	 */
	public Properties getMissingProperties() {
		return missingProperties;
	}
	/**
	 * @param missingProperties the missingProperties to set
	 */
	public void setMissingProperties(Properties missingProperties) {
		this.missingProperties = missingProperties;
	}
	/**
	 * @return the files
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param files the files to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the isError
	 */
	public boolean isError() {
		return isError;
	}
	/**
	 * @param isError the isError to set
	 */
	public void setError(boolean isError) {
		this.isError = isError;
	}
	
}
