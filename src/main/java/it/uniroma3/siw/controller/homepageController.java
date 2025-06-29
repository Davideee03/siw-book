package it.uniroma3.siw.controller;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Genre;
import it.uniroma3.siw.model.RandomGenre;
import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class HomepageController {

    private final AuthenticationManager authenticationManager;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ReviewService reviewService;

    HomepageController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

	@Transactional
	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("books", this.bookService.getAllBooks());
		
		//Get the first 5 top rated books
		List<Book> topBooks = this.bookService.getTop15Books();
		Collections.shuffle(topBooks);
		topBooks = topBooks.stream().limit(5).collect(Collectors.toList());
		
		//Get the random top rated book
		Random random = new Random();
		Book randomBook = null;
		if (!topBooks.isEmpty()) {
		    randomBook = topBooks.get(random.nextInt(topBooks.size()));
		}
		
		model.addAttribute("unknownBooks", this.bookService.getUnknownBooks());
		model.addAttribute("suggestedBook", randomBook);
	
		model.addAttribute("topBooks", topBooks);
		model.addAttribute("thrillerBooks", this.bookService.getThrillerBooks());
		model.addAttribute("romanceBooks", this.bookService.getRomanceBooks());
		model.addAttribute("randomAuthor", this.authorService.getRandomAuthor());
		
		
		
		return "homepage.html";
	}
	
	@GetMapping("/errore")
	public String error(Model model) {
		model.addAttribute("errorMessage", "Erroreeeee");
		return "error.html";
	}
}
