package mx.infotec.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.exception.PropertiesException;
import mx.infotec.model.FileModel;
import mx.infotec.model.ResumeModel;
import mx.infotec.service.MergeService;

@RestController
@RequestMapping("/merge")
public class MergeController {
	
	@Autowired
	private MergeService mergeService;
	
	@RequestMapping(value="/single/skip", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> mergeSkip(@RequestBody FileModel fileModel) {
		ResumeModel resume = null;
		try {
			resume = mergeService.mergePropertiesWhitSkipFromFile(fileModel.getFromPropertiesFile(), fileModel.getToPropertiesFile(), fileModel.getSkipPropertiesFile());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(resume);
		} catch (PropertiesException  e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
		}
	}
	
	@RequestMapping(value="/multiple/skip", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> mergeMultiple(@RequestBody FileModel fileModel) {
		List<ResumeModel> resume = null;
		try {
			resume = mergeService.mergeMultipeFilesWhitSkipFromFile(fileModel.getFromPropertiesFile(), fileModel.getToPropertiesFile(), fileModel.getSkipPropertiesFile());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(resume);
		} catch (PropertiesException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getCause());
		}
	}

}
