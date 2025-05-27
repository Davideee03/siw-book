package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.AuthorRepository;
import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.model.Author;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;
	
	public List<Author> showAuthors(){
		return (List<Author>) this.authorRepository.findAll();
	}
	
	public Author getAuthorById(Long id){
		return this.authorRepository.findById(id).orElse(null);
	}
	
	public List<Author> getAllAuthorsById(List<Long> ids){
		return (List<Author>) this.authorRepository.findAllById(ids);
	}
	
	public Author save(Author author) {
		return this.authorRepository.save(author);
	}
	
	public void deleteAllById(List<Long> ids) {
		this.authorRepository.deleteAllById(ids);
	}
	
	public Author getRandomAuthor(){
		return this.authorRepository.getRandomAuthor();
	}
}
