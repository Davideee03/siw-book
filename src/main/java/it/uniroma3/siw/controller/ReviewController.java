package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private BookService bookService;
	
	@Autowired
	private ReviewService reviewService;

	@GetMapping("/user/formNewReview/book/{id}")
	public String formNewReview(@PathVariable("id") Long book_id, Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			model.addAttribute("book_id", book_id);
			model.addAttribute("review", new Review());
			return "formNewReview.html";
		}
		return "homepage.html";
	}

	@PostMapping("/newReview")
	public String newReview(@ModelAttribute("review") Review review, @RequestParam("bookId") Long bookId, Model model) {
	    Book book = this.bookService.getBookById(bookId);
	    review.setBook(book);
	    this.reviewService.save(review);

	    book.addReview(review); 

	    model.addAttribute("book", book);
	    return "redirect:/book/" + bookId;
	}
}
