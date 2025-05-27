package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.AuthorService;
import it.uniroma3.siw.service.BookService;

@Controller
public class HomepageController {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private AuthorService authorService;

	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("topBooks", this.bookService.getTop5Books());
		model.addAttribute("thrillerBooks", this.bookService.getThrillerBooks());
		model.addAttribute("romanceBooks", this.bookService.getRomanceBooks());
		model.addAttribute("randomAuthor", this.authorService.getRandomAuthor());
		return "homepage.html";
	}
}
