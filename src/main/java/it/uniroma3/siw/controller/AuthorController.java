package it.uniroma3.siw.controller;

import java.io.IOException;
import java.util.List;

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
import it.uniroma3.siw.model.AuthorPhoto;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.AuthorPhotoService;
import it.uniroma3.siw.service.AuthorService;

@Controller
public class AuthorController {

	@Autowired
	private AuthorService authorService;

	@Autowired
	private AuthorPhotoService authorPhotoService;

	@GetMapping("/formNewAuthor")
	public String formNewAuthor(Model model) {
		model.addAttribute("author", new Author());
		return "formNewAuthor.html";
	}

	@GetMapping("/show/authors")
	public String getAllAuthors(Model model) {
		model.addAttribute("authors", this.authorService.getAllAuthors());
		return "authors.html";
	}

	@Transactional
	@PostMapping("/author")
	public String newAuthor(@ModelAttribute("author") Author author, @RequestParam("uploadedImage") MultipartFile photo,
			Model model) {
		authorService.save(author);

		if (!photo.isEmpty()) {
			try {
				AuthorPhoto authorPhoto = new AuthorPhoto();
				authorPhoto.setData(photo.getBytes());
				authorPhoto.setAuthor(author);
				authorPhotoService.save(authorPhoto);
			} catch (IOException e) {
				model.addAttribute("errorMessage", "Error loading photo");
				return "error.html";
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
			model.addAttribute("books", this.authorService.getBooks(author));
			model.addAttribute("photo", author.getAuthorPhoto());
			return "author.html";
		}

		model.addAttribute("errorMessage", "Author not found");
		return "error.html";
	}

	@GetMapping("/administrator/formDeleteAuthors")
	public String deleteAuthors(Model model) {
		model.addAttribute("authors", this.authorService.getAllAuthors());
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

	@GetMapping("/edit/author/{id}")
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

	@PostMapping("/author-edited/{id}")
	public String authorUpdated(@PathVariable("id") Long id, @ModelAttribute Author authorUpdated,
			@RequestParam("uploadedImage") MultipartFile photo, Model model) {

		Author author = this.authorService.getAuthorById(id);

		if (author != null) {
			author.setBirthDate(authorUpdated.getBirthDate());
			author.setDeathDate(authorUpdated.getDeathDate());
			author.setFirstName(authorUpdated.getFirstName());
			author.setLastName(authorUpdated.getLastName());
			author.setNationality(authorUpdated.getNationality());
			this.authorService.save(author);

			if (photo != null && !photo.isEmpty()) {
			    try {
			        AuthorPhoto existingPhoto = authorPhotoService.findByAuthor(author);

			        if (existingPhoto != null) {
			            existingPhoto.setData(photo.getBytes());
			            authorPhotoService.save(existingPhoto); // UPDATE
			        } else {
			            AuthorPhoto newPhoto = new AuthorPhoto();
			            newPhoto.setData(photo.getBytes());
			            newPhoto.setAuthor(author);
			            authorPhotoService.save(newPhoto); // INSERT
			        }
			    } catch (IOException e) {
			        e.printStackTrace();
			        model.addAttribute("errorMessage", "Errore nel caricamento della foto");
			        return "formUpdateAuthor.html";
			    }
			}
		}

		return "redirect:/author/" + id;
	}
}
