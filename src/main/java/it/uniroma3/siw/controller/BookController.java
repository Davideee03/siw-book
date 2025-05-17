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
import it.uniroma3.siw.service.BookService;

@Controller
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping("/show/books")
	public String showBook(Model model) {
		if(this.bookService.count()>0) {
			model.addAttribute("books", this.bookService.showBooks());
			return "books.html";
		}
		
		return "emptyLibrary.html";
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

	@PostMapping("/book")
	public String saveBook(@ModelAttribute("book") Book book, Model model) {
		this.bookService.save(book);
		return "redirect:/book/"+book.getId();
	}

	@GetMapping("/formModifyBook/book/{id}")
	public String modifyBook(@PathVariable("id") Long id, Model model) {
		Optional<Book> book = bookService.getBookById(id);

		//Check the presence of the book
		if(book.isPresent()) {
			
			model.addAttribute("book", book.get());
			model.addAttribute("id", book.get().getId()); //Necessary for not duplicating books
			return "formModifyBook.html";
		}
		return "bookNotFound.html";
	}
	
	@PostMapping("/update/book/{id}")
	public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book updatedBook, Model model) {
		//Set the right id and save it
		updatedBook.setId(id);
		this.bookService.save(updatedBook);
		
		model.addAttribute("book", updatedBook);
		
		return "redirect:/book/"+id;
	}
	
	@GetMapping("/delete/books")
	public String deleteBooks(Model model) {
		model.addAttribute("books", this.bookService.showBooks());
		return "deleteBooks.html";
	}
	
	@PostMapping("/books-deleted")
	public String deletedBooks(@RequestParam("selectedIds") List<Long> ids, Model model) {
		this.bookService.deleteAllById(ids);
		
		return "redirect:/show/books";
	}
}
