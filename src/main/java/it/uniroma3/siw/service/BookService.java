package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.model.Book;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	public List<Book> showBooks(){
		return (List<Book>) bookRepository.findAll();
	}
	
	public Book getBookById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}
	
	public Book save(Book book) {
		return bookRepository.save(book);
	}
	
	public void deleteAllById(List<Long> ids) {
		this.bookRepository.deleteAllById(ids);
	}
	
	public Long count() {
		return this.bookRepository.count();
	}
}
