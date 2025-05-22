package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import it.uniroma3.siw.service.BookService;

@Controller
public class homepageController {
	
	@Autowired
	private BookService bookService;

	@GetMapping("/")
	public String homepage(Model model) {
		model.addAttribute("books", this.bookService.getAllBooks());
		return "homepage.html";
	}
}
