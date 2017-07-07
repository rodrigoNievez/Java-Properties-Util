package mx.infotec.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import mx.infotec.model.FileModel;

@RestController
@RequestMapping("/merge")
public class MergeController {
	
	@RequestMapping(value="/multiple", method=RequestMethod.POST, 
			consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> mergeMultiple(@RequestBody FileModel fileModel) {
		return null;
	}

}
