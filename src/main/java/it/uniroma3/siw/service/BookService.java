package it.uniroma3.siw.service;

import java.util.ArrayList;
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
import it.uniroma3.siw.model.User;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Transactional(readOnly = true)
	public List<Book> getAllBooks() {
		List<Book> books = (List<Book>) bookRepository.findAll();
		// Forza caricamento degli ID delle foto (senza toccare il blob)
		books.forEach(book -> {
			if (!book.getPhotos().isEmpty()) {
				book.getPhotos().get(0).getId();
			}

			this.getAuthors(book.getId());
		});
		return books;
	}

	@Transactional(readOnly = true)
	public Book getBookById(Long id) {
		Book book = bookRepository.findById(id).orElse(null);
		if (book != null) {
			book.getPhotos().size();

			book.getReviews().forEach(r -> {
				User user = r.getUser();
				if (user.getUserPhoto() != null) {
					user.getUserPhoto().getData();
				}
			});
		}
		return book;
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

	@Transactional(readOnly = true)
	public List<Author> getAuthors(Long id) {
		Book book = this.getBookById(id);

		for (Author author : book.getAuthors()) {
			if (author.getAuthorPhoto() != null) {
				author.getAuthorPhoto(); // Inizializza il lazy loading
			}
		}

		return book.getAuthors();
	}

	public List<Book> getTop15Books() {
		return this.bookRepository.findTopBooksByAverageMark(PageRequest.of(0, 15));
	}

	public List<Book> getMostReviewed() {
		return this.bookRepository.findMostReviewedBooks(PageRequest.of(0, 5));
	}

	public List<Book> getThrillerBooks() {
		return this.bookRepository.findThrillerBooks(PageRequest.of(0, 5));
	}

	public List<Book> getRomanceBooks() {
		return this.bookRepository.findRomanceBooks(PageRequest.of(0, 5));
	}

	@Transactional(readOnly = true)
	public List<Book> findBooksByGenre(Genre genre) {
		if (genre == null) {
			List<Book> books = new ArrayList<>();
			return books;
		}
		return this.bookRepository.findByGenre(genre);
	}

	public List<Book> getBooksByIds(List<Long> ids) {
		return (List<Book>) this.bookRepository.findAllById(ids);
	}

	public List<Book> getUnknownBooks() {
		return this.bookRepository.getUnknownBooks();
	}

	@Transactional
	public List<Book> getBooksByTitle(String title) {
		List<Book> books = (List<Book>) this.bookRepository.getBooksByTitle(title);
		// Forza caricamento degli ID delle foto (senza toccare il blob)
		books.forEach(book -> {
			if (!book.getPhotos().isEmpty()) {
				book.getPhotos().get(0).getId();
			}

			this.getAuthors(book.getId());
		});
		return books;
	}

	@Transactional
	public List<Book> filterBooks(String title, int year, String author, Genre genre) {
		List<Book> books = this.bookRepository.filterBooks(title, year, author, genre);

		books.forEach(book -> {
			if (!book.getPhotos().isEmpty()) {
				book.getPhotos().get(0).getId();
			}

			this.getAuthors(book.getId());
		});

		return books;
	}

	public boolean existsBookTitleYearAuthor(String title, int year, List<Author> authors) {
		return this.bookRepository.existsBookTitleYearAuthor(title, year, authors, authors.size());
		
	}
}
