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
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/show/authors")
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
	
	@GetMapping("/administrator/delete/authors")
	public String deleteAuthors(Model model) {
		model.addAttribute("authors", this.authorService.showAuthors());
		
		return "deleteAuthors.html";
	}
	
	@PostMapping("/authors-deleted")
	public String authorsDeleted(@RequestParam("selectedIds") List<Long> ids, Model model) {
		this.authorService.deleteAllById(ids);
		
		return "redirect:/show/authors";
	}
	
	@GetMapping("/administrator/update/author/{id}")
	public String updateAuthor(@PathVariable("id") Long id, Model model) {
		Optional<Author> author = this.authorService.getAuthorById(id);
		
		if(author.isPresent()) {
			model.addAttribute("author", author.get());
			model.addAttribute("id", author.get().getId());
			
			return "formUpdateAuthor.html";
		}
		
		return "authorNotFound.html";
	}
	
	@PostMapping("/author-updated/{id}")
	public String authorUpdated(@PathVariable("id") Long id, @ModelAttribute Author author, Model model) {
		
		author.setId(id);
		this.authorService.save(author);
		
		model.addAttribute("author", author);
		
		return "redirect:/author/"+id;
	}
	
}
