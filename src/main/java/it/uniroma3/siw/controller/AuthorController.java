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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.AuthorPhoto;
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

	@Transactional
	@PostMapping("/author")
	public String newAuthor(@ModelAttribute("author") Author author, @RequestParam("photo") MultipartFile[] photos,
			Model model) {

		this.authorService.save(author);

		// Se c'è una foto allegata, la salviamo
		for (MultipartFile photo : photos) {
			if (!photo.isEmpty()) {
				try {
					AuthorPhoto authorPhoto = new AuthorPhoto();
					authorPhoto.setData(photo.getBytes());
					authorPhoto.setAuthor(author);
					
					this.authorService.save(author); // salva in DB
				} catch (IOException e) {
					e.printStackTrace();
					model.addAttribute("errorMessage", "Errore nel caricamento della foto");
					return "administrator/formNewAuthor"; // o la view di errore
				}
			}
		}

		return "redirect:/author/" + author.getId();
	}

	@Transactional
	@GetMapping("/author/{id}")
	public String getAuthorById(@PathVariable("id") Long id, Model model) {
		Author author = this.authorService.getAuthorById(id);

		if (author != null) {
			model.addAttribute("author", author);
			return "author.html";
		}

		model.addAttribute("errorMessage", "Author not found");
		return "error.html";
	}

	@GetMapping("/administrator/formDeleteAuthors")
	public String deleteAuthors(Model model) {
		model.addAttribute("authors", this.authorService.showAuthors());

		return "formDeleteAuthors.html";
	}

	@PostMapping("/authors-deleted")
	public String authorsDeleted(@RequestParam("selectedIds") List<Long> ids) {
		List<Author> authors = this.authorService.getAllAuthorsById(ids);

		for (Author author : authors) {
			for (Book book : author.getBooks()) {
				book.getAuthors().remove(author);
			}
		}
		this.authorService.deleteAllById(ids);

		return "redirect:/show/authors";
	}

	@GetMapping("/administrator/update/author/{id}")
	public String updateAuthor(@PathVariable("id") Long id, Model model) {
		Author author = this.authorService.getAuthorById(id);

		if (author != null) {
			model.addAttribute("author", author);
			model.addAttribute("id", id);

			return "formUpdateAuthor.html";
		}

		model.addAttribute("errorMessage", "Author not found");
		return "error.html";
	}

	@PostMapping("/author-updated/{id}")
	public String authorUpdated(@PathVariable("id") Long id, @ModelAttribute Author authorUpdated) {
		Author author = this.authorService.getAuthorById(id);

		if (author != null) {
			author.setBirthDate(authorUpdated.getBirthDate());
			author.setDeathDate(authorUpdated.getDeathDate());
			author.setFirstName(authorUpdated.getFirstName());
			author.setLastName(authorUpdated.getLastName());
			author.setNationality(authorUpdated.getNationality());
			this.authorService.save(author);
		}

		return "redirect:/author/" + id;
	}

}
