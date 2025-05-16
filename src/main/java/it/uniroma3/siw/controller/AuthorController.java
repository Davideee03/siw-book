package it.uniroma3.siw.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;

@Controller
public class AuthorController {

	@Autowired
	private AuthorService authorService;
	
	
	@GetMapping("/administrator/formNewAuthor")
	public String formNewAuthor(Model model) {
		model.addAttribute("author", new Author());
		return "formNewAuthor.html";
	}
	
	@GetMapping("/authors")
	public String getAllAuthors(Model model) {
		model.addAttribute("authors", this.authorService.showAuthors());
		return "authors.html";
	}
	
	@PostMapping("/author")
	public String newAuthor(@ModelAttribute("author") Author author, Model model) {
		this.authorService.save(author);
		return "redirect:/author/" + author.getId();
	}
	
	@GetMapping("/author/{id}")
	public String getAuthorById(@PathVariable("id") Long id, Model model) {
		Optional<Author> author = this.authorService.getAuthorById(id);
		
		if(author.isPresent()) {
			model.addAttribute("author", author.get());
			return "author.html";
		}
		
		return "authorNotFound.html";
	}
	
}
