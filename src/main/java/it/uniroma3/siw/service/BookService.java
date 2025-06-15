package it.uniroma3.siw.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.repository.BookRepository;
import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Genre;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
	
	@Transactional(readOnly = true)
	public List<Book> getAllBooks(){
		List<Book> books = (List<Book>) bookRepository.findAll();
        // Forza caricamento degli ID delle foto (senza toccare il blob)
        books.forEach(book -> {
            if (!book.getPhotos().isEmpty()) {
                book.getPhotos().get(0).getId();
            }
        });
		return books;
	}
	
	public Book getBookById(Long id) {
		return this.bookRepository.findById(id).orElse(null);
	}
	
	public Book save(Book book) {
		return this.bookRepository.save(book);
	}
	
	public void deleteAllById(List<Long> ids) {
		this.bookRepository.deleteAllById(ids);
	}
	
	public Long count() {
		return this.bookRepository.count();
	}
	
	public List<Author> getAuthors(Long  id){
		Book book = this.getBookById(id);
		
		return book.getAuthors();
	}
	
	public List<Book> getTop5Books(){
		return this.bookRepository.findTopBooksByAverageMark(PageRequest.of(0, 5));
	}
	
	public List<Book> getMostReviewed(){
		return this.bookRepository.findMostReviewedBooks(PageRequest.of(0, 5));
	}
	
	public List<Book> getThrillerBooks(){
		return this.bookRepository.findThrillerBooks(PageRequest.of(0, 5));
	}
	
	public List<Book> getRomanceBooks(){
		return this.bookRepository.findRomanceBooks(PageRequest.of(0, 5));
	}

	public List<Book> findBooksByGenre(Genre genre) {
		return this.bookRepository.findByGenre(genre);
	}
}
