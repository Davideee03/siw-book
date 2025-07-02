package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siw.controller.validator.ReviewValidator;
import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.model.BookPhoto;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.BookService;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.ReviewService;
import it.uniroma3.siw.service.UserService;

@Controller
public class ReviewController {

	@Autowired
	private CredentialsService credentialsService;

	@Autowired
	private BookService bookService;

	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReviewValidator reviewValidator;

	@Transactional
	@GetMapping("/user/formNewReview/book/{id}")
	public String formNewReview(@PathVariable("id") Long book_id, Model model) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();

		if (!credentials.getRole().equals(Credentials.DEFAULT_ROLE))
			return "homepage.html";

		Book book = bookService.getBookById(book_id);
		// Controllo se l'utente ha già recensito questo libro
		boolean alreadyReviewed = user.getReviews().stream().anyMatch(r -> r.getBook().getId().equals(book_id));

		if (alreadyReviewed) {
			model.addAttribute("book", book);
			model.addAttribute("authors", bookService.getAuthors(book_id));
			List<Book> booksSameGenre = bookService.findBooksByGenre(book.getGenre());
			booksSameGenre.remove(book);
			for (Book bookSameGenre : booksSameGenre) {
				List<BookPhoto> photos = bookSameGenre.getPhotos();
				if (photos != null) {
					for (BookPhoto photo : photos) {
						if (photo != null && photo.getData() != null) {
							photo.getData();
						}
					}
				}
			}

			model.addAttribute("booksSameGenre", booksSameGenre);
			model.addAttribute("photos", book.getPhotos());

			model.addAttribute("errorMessage", "You have already reviewed this book");
			return "book.html";
		}

		model.addAttribute("book_id", book_id);
		model.addAttribute("review", new Review());
		return "formNewReview.html";
	}

	@PostMapping("/newReview")
	public String newReview(@ModelAttribute("review") Review review, BindingResult bindingResult, @RequestParam("bookId") Long bookId, Model model) {

		this.reviewValidator.validate(review, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("book_id", bookId);
			return "formNewReview.html";
		}
		
		Book book = this.bookService.getBookById(bookId);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		User user = credentials.getUser();

		review.setBook(book);
		review.setUser(user);
		this.reviewService.save(review);

		book.addReview(review);

		return "redirect:/book/" + bookId;
	}
	
	@GetMapping("/removeReview")
	public String removeReview(@RequestParam("reviewId") Long reviewId, @RequestParam("bookId") Long bookId) {
		Review review = this.reviewService.findReviewById(reviewId);
		
		review.getBook().removeReview(review);
		this.reviewService.deleteReview(review);
		
		return "redirect:/book/"+bookId;
	}
}
