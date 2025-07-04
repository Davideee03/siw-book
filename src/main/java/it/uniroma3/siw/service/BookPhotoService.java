package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.BookPhoto;
import it.uniroma3.siw.repository.BookPhotoRepository;

@Service
public class BookPhotoService {

	@Autowired
	private BookPhotoRepository bookPhotoRepository;

	public BookPhoto save(BookPhoto bookPhoto) {
		return bookPhotoRepository.save(bookPhoto);
	}
	
	@Transactional(readOnly = true)
	public BookPhoto findById(Long id) {
        return this.bookPhotoRepository.findById(id).orElse(null);
    }

	@Transactional(readOnly = true)
    public List<BookPhoto> findAll() {
        return (List<BookPhoto>) this.bookPhotoRepository.findAll();
    }
	
	public void deletePhoto(BookPhoto bookPhoto) {
		this.bookPhotoRepository.delete(bookPhoto);
	}

	public void deleteById(Long id) {
		this.bookPhotoRepository.deleteById(id);
	}
}

