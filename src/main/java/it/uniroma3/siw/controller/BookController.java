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
		Book book = bookService.getBookById(id);

		//Check the presence of the book
		if(book!=null) {
			model.addAttribute("book", book);
			return "book.html";
		}
		
		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}

	@GetMapping("/administrator/formNewBook")
	public String formNewBook(Model model) {
		model.addAttribute("book", new Book());
		return "formNewBook.html";
	}

	@PostMapping("/book")
	public String saveBook(@ModelAttribute("book") Book book, Model model) {
		this.bookService.save(book);
		return "redirect:/book/"+book.getId();
	}

	@GetMapping("/administrator/formUpdateBook/{id}")
	public String modifyBook(@PathVariable("id") Long id, Model model) {
		Book book = bookService.getBookById(id);

		if(book!=null) {
			
			model.addAttribute("book", book);
			model.addAttribute("id", id);
			return "formUpdateBook.html";
		}
		
		model.addAttribute("errorMessage", "Book not found");
		return "error.html";
	}
	
	@PostMapping("/update/book/{id}")
	public String updateBook(@PathVariable("id") Long id, @ModelAttribute Book updatedBook) {
		Book book = this.bookService.getBookById(id);
		
		if(book!=null) {
				book.setTitle(updatedBook.getTitle());
				book.setYear(updatedBook.getYear());
				
				this.bookService.save(book);
		}
		
		return "redirect:/book/"+id;
	}
	
	@GetMapping("/delete/books")
	public String deleteBooks(Model model) {
		model.addAttribute("books", this.bookService.showBooks());
		return "deleteBooks.html";
	}
	
	@PostMapping("/books-deleted")
	public String deletedBooks(@RequestParam("selectedIds") List<Long> ids) {
		this.bookService.deleteAllById(ids);
		
		return "redirect:/show/books";
	}
}
