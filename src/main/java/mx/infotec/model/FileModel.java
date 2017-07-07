package mx.infotec.model;

import java.io.Serializable;

public class FileModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String fromPropertiesFile;
	private String toPropertiesFile;
	private String skipPropertiesFile;
	private boolean whitSkip;
	
	public String getFromPropertiesFile() {
		return fromPropertiesFile;
	}
	
	public void setFromPropertiesFile(String fromPropertiesFile) {
		this.fromPropertiesFile = fromPropertiesFile;
	}
	
	public String getToPropertiesFile() {
		return toPropertiesFile;
	}
	
	public void setToPropertiesFile(String toPropertiesFile) {
		this.toPropertiesFile = toPropertiesFile;
	}
	
	public String getSkipPropertiesFile() {
		return skipPropertiesFile;
	}
	
	public void setSkipPropertiesFile(String skipPropertiesFile) {
		this.skipPropertiesFile = skipPropertiesFile;
	}
	
	public boolean isWhitSkip() {
		return whitSkip;
	}
	
	public void setWhitSkip(boolean whitSkip) {
		this.whitSkip = whitSkip;
	}
	
	

}
