package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;
	
	@GetMapping("/books")
	public String showBook(Model model) {
		model.addAttribute("books", this.bookService.showBooks());
		return "books.html";
	}
	
	@GetMapping("/book/{id}")
	public String getBookById(@PathVariable("id") Long id, Model model) {
		Optional<Book> book = bookService.getBookById(id);
		
		//Check the presence of the book
		if(book.isPresent()) {
			model.addAttribute("book", book.get());
			return "book.html";
		}
		return "bookNotFound.html";
	}
	
	@GetMapping("/formNewBook")
	public String formNewBook(Model model) {
		model.addAttribute("book", new Book());
		return "formNewBook.html";
	}
	
	@PostMapping("/books")
	public String newBook(@ModelAttribute("book") Book book, Model model) {
		this.bookService.save(book);
		return "redirect:/book/"+book.getId();
	}
}
