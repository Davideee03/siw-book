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
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookPhotoService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookPhotoService bookPhotoService;

	@Autowired
	private UserService userService;

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/show/books")
	public String showBook(Model model) {
		model.addAttribute("books", this.bookService.getAllBooks());
		model.addAttribute("authors", this.authorService.getAllAuthors());
		model.addAttribute("genres", Genre.values());
		return "books.html";
	}

	@Transactional
	@GetMapping("/book/{id}")
	public String getBookById(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		// Check the presence of the book
		if (book != null) {
			model.addAttribute("book", book);
			model.addAttribute("authors", this.bookService.getAuthors(id));

			List<Book> booksSameGenre = this.bookService.findBooksByGenre(book.getGenre());
			booksSameGenre.remove(book);

			for (Book bookSameGenre : booksSameGenre) {
				List<BookPhoto> photos = bookSameGenre.getPhotos();
				if (photos != null) {
					for (BookPhoto photo : photos) {
						if (photo != null && photo.getData() != null) {
							photo.getData();
						}
					}
				}
			}
			model.addAttribute("booksSameGenre", booksSameGenre);

			model.addAttribute("photos", book.getPhotos());

			return "book.html";
		}

		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}

	@GetMapping("/formNewBook")
	public String formNewBook(Model model) {
		model.addAttribute("book", new Book());
		model.addAttribute("authors", this.authorService.getAllAuthors());
		model.addAttribute("genres", Genre.values());
		return "formNewBook.html";
	}

	@Transactional
	@PostMapping("/book")
	public String saveBook(@ModelAttribute("book") Book book, @RequestParam("photo") MultipartFile[] photos,
			@RequestParam("selectedIds") List<Long> ids, Model model) {

		// Associa gli autori selezionati
		book.setAuthors(this.authorService.getAllAuthorsById(ids));

		// Salva il libro per avere l'ID (serve prima di associare la foto)
		this.bookService.save(book);

		// Se c'è una foto allegata, la salviamo
		for (MultipartFile photo : photos) {
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
		}

		return "redirect:/book/" + book.getId();
	}

	@GetMapping("/administrator/formEditBook/{id}")
	public String modifyBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		if (book != null) {

			model.addAttribute("book", book);
			model.addAttribute("authors", this.authorService.getAllAuthors());
			return "formUpdateBook.html";
		}

		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}

	@GetMapping("/administrator/formDeleteBooks")
	public String deleteBooks(Model model) {
		model.addAttribute("books", this.bookService.getAllBooks());
		return "formDeleteBooks.html";
	}

	@Transactional
	@PostMapping("/books-deleted")
	public String deletedBooks(@RequestParam("selectedIds") List<Long> ids) {

		for (Book book : this.bookService.getBooksByIds(ids)) {
			for (BookPhoto bookPhoto : book.getPhotos()) {
				this.bookPhotoService.deletePhoto(bookPhoto);
			}

			for (Review review : book.getReviews()) {
				this.reviewService.deleteReview(review);
			}
		}

		this.bookService.deleteAllById(ids);

		return "redirect:/show/books";
	}

	@GetMapping("/edit/book/{id}")
	public String editBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		model.addAttribute("book", book);
		model.addAttribute("authors", this.authorService.getAllAuthors());
		model.addAttribute("genres", Genre.values());

		return "formEditBook.html";
	}

	@PostMapping("/book-edited/{id}")
	public String bookEdited(@ModelAttribute("book") Book updatedBook, @PathVariable("id") Long id, Model model) {
		Book book = this.bookService.getBookById(id);

		if (book != null) {
			book.setTitle(updatedBook.getTitle());
			book.setYear(updatedBook.getYear());
			book.setPlot(updatedBook.getPlot());

			// book.setAuthors(this.authorService.getAllAuthorsById(ids));
			this.bookService.save(book);
		}

		return "redirect:/book/" + id;
	}

	@GetMapping("/filterBooks")
	public String filterBooks(
	    @RequestParam(required = false, defaultValue = "") String title,
	    @RequestParam(required = false, defaultValue = "0") int year,
	    @RequestParam(required = false, defaultValue = "") String author,
	    @RequestParam(required = false) Genre genre, // enum direttamente!
	    Model model
	) {
	    List<Book> books = this.bookService.filterBooks(title, year, author, genre);
	    model.addAttribute("books", books);
	    model.addAttribute("genres", Genre.values()); // per il select
	    model.addAttribute("authors", this.authorService.getAllAuthors()); // se hai anche autori
	    return "books.html";
	}
}
