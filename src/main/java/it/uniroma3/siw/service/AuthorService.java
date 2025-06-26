package it.uniroma3.siw.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.BookPhoto;
import it.uniroma3.siw.repository.AuthorRepository;

@Service
public class AuthorService {

	@Autowired
	private AuthorRepository authorRepository;

	public List<Author> getAllAuthors() {
		return (List<Author>) this.authorRepository.findAll();
	}

	public Author getAuthorById(Long id) {
		return this.authorRepository.findById(id).orElse(null);
	}

	public List<Author> getAllAuthorsById(List<Long> ids) {
		return (List<Author>) this.authorRepository.findAllById(ids);
	}

	public Author save(Author author) {
		return this.authorRepository.save(author);
	}

	public void deleteAllById(List<Long> ids) {
		this.authorRepository.deleteAllById(ids);
	}

	public Author getRandomAuthor() {
		return this.authorRepository.getRandomAuthor();
	}

	public Long count() {
		return this.authorRepository.count();
	}

	@Transactional
	public List<Book> getBooks(Author author) {
		List<Book> books = author.getBooks();
		for (Book book : books) {
			for (BookPhoto photo : book.getPhotos()) {
				photo.getData(); // <-- accede al @Lob
			}
		}
		return books;
	}

	public List<Long> getAuthorIds(List<Author> authors) {
		List<Long> ids = new ArrayList<>();

		for (Author author : authors) {
			ids.add(author.getId());
		}

		return ids;
	}
}
