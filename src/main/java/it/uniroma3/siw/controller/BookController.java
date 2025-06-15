package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.BookPhoto;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookPhotoService;
import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookPhotoService bookPhotoService;

	@GetMapping("/show/books")
	public String showBook(Model model) {
		if (this.bookService.count() > 0) {
			model.addAttribute("books", this.bookService.getAllBooks());
			return "books.html";
		}

		return "emptyLibrary.html";
	}

	@Transactional
	@GetMapping("/book/{id}")
	public String getBookById(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		// Check the presence of the book
		if (book != null) {
			System.out.println(book.getPhotos().size());
			model.addAttribute("book", book);
			model.addAttribute("booksSameGenre", this.bookService.findBooksByGenre(book.getGenre()));
			model.addAttribute("photos", book.getPhotos());
			return "book.html";
		}

		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}

	@GetMapping("/administrator/formNewBook")
	public String formNewBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", this.authorService.showAuthors());
		return "formNewBook.html";
	}

	@Transactional
	@PostMapping("/book")
	public String saveBook(@ModelAttribute("book") Book book, @RequestParam("photo") MultipartFile photo,
			@RequestParam("selectedIds") List<Long> ids, Model model) {

		// Associa gli autori selezionati
		book.setAuthors(this.authorService.getAllAuthorsById(ids));

		// Salva il libro per avere l'ID (serve prima di associare la foto)
		this.bookService.save(book);

		// Se c'è una foto allegata, la salviamo
		if (!photo.isEmpty()) {
			try {
				BookPhoto bookPhoto = new BookPhoto();
				bookPhoto.setData(photo.getBytes());
				bookPhoto.setBook(book);
				this.bookPhotoService.save(bookPhoto); // salva in DB
			} catch (IOException e) {
				e.printStackTrace();
				model.addAttribute("errorMessage", "Errore nel caricamento della foto");
				return "book/form"; // o la view di errore
			}
		}

		return "redirect:/book/" + book.getId();
	}

	@GetMapping("/administrator/formUpdateBook/{id}")
	public String modifyBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		if (book != null) {

			model.addAttribute("book", book);
			model.addAttribute("id", id);
			model.addAttribute("authors", this.authorService.showAuthors());
			return "formUpdateBook.html";
		}

		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}

	@PostMapping("/update/book/{id}")
	public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book updatedBook,
			@RequestParam("selectedIds") List<Long> ids) {
		Book book = this.bookService.getBookById(id);

		if (book != null) {
			book.setTitle(updatedBook.getTitle());
			book.setYear(updatedBook.getYear());

			book.setAuthors(this.authorService.getAllAuthorsById(ids));
			this.bookService.save(book);
		}

		return "redirect:/book/" + id;
	}

	@GetMapping("/administrator/formDeleteBooks")
	public String deleteBooks(Model model) {
		model.addAttribute("books", this.bookService.getAllBooks());
		return "formDeleteBooks.html";
	}

	@PostMapping("/books-deleted")
	public String deletedBooks(@RequestParam("selectedIds") List<Long> ids) {
		this.bookService.deleteAllById(ids);

		return "redirect:/show/books";
	}
}
