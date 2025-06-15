package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.AuthorPhoto;
import it.uniroma3.siw.service.AuthorPhotoService;

@Controller
public class AuthorPhotoController {
	
	@Autowired
	private AuthorPhotoService authorPhotoService;

	@GetMapping("/authorPhoto/{id}")
	public ResponseEntity<byte[]> getPhoto(@PathVariable Long id) {
	    AuthorPhoto photo = authorPhotoService.findById(id);
	    if (photo == null) {
	        return ResponseEntity.notFound().build();
	    }
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_JPEG); // o IMAGE_PNG
	    return new ResponseEntity<>(photo.getData(), headers, HttpStatus.OK);
	}

}
