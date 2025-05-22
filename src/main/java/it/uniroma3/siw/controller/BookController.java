package it.uniroma3.siw.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;

	@GetMapping("/show/books")
	public String showBook(Model model) {
		if(this.bookService.count()>0) {
			model.addAttribute("books", this.bookService.getAllBooks());
			return "books.html";
		}
		
		return "emptyLibrary.html";
	}

	@GetMapping("/book/{id}")
	public String getBookById(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		//Check the presence of the book
		if(book!=null) {
			model.addAttribute("book", book);
			model.addAttribute("authors", this.bookService.getAuthors(id));
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

	@PostMapping("/book")
	public String saveBook(@ModelAttribute("book") Book book, @RequestParam("selectedIds") List<Long> ids, Model model) {
		// Associa gli autori selezionati
		book.setAuthors(this.authorService.getAllAuthorsById(ids));

		// Salva il libro con gli autori
		this.bookService.save(book);
		return "redirect:/book/" + book.getId();
	}


	@GetMapping("/administrator/formUpdateBook/{id}")
	public String modifyBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		if(book!=null) {
			
			model.addAttribute("book", book);
			model.addAttribute("id", id);
			model.addAttribute("authors", this.authorService.showAuthors());
			return "formUpdateBook.html";
		}
		
		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}
	
	@PostMapping("/update/book/{id}")
	public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book updatedBook, @RequestParam("selectedIds") List<Long> ids) {
		Book book = this.bookService.getBookById(id);
		
		if(book!=null) {
				book.setTitle(updatedBook.getTitle());
				book.setYear(updatedBook.getYear());
				
				book.setAuthors(this.authorService.getAllAuthorsById(ids));		
				this.bookService.save(book);
		}
		
		return "redirect:/book/"+id;
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
