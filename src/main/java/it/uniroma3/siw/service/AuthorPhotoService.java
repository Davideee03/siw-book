package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.AuthorPhoto;
import it.uniroma3.siw.repository.AuthorPhotoRepository;

@Service
public class AuthorPhotoService {

	@Autowired
	private AuthorPhotoRepository authorPhotoRepository;

	public AuthorPhoto save(AuthorPhoto authorPhoto) {
		return authorPhotoRepository.save(authorPhoto);
	}
	
	public AuthorPhoto findById(Long id) {
        return this.authorPhotoRepository.findById(id).orElse(null);
    }

    public List<AuthorPhoto> findAll() {
        return (List<AuthorPhoto>) this.authorPhotoRepository.findAll();
    }
}

