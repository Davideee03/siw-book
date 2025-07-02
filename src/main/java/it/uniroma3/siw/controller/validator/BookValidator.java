package it.uniroma3.siw.controller.validator;

import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Book;
import it.uniroma3.siw.service.BookService;

@Component
public class BookValidator implements Validator {

	@Autowired
	private BookService bookService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;

		if (book.getTitle() != null && book.getYear() != 0 && !book.getAuthors().isEmpty()
				&& this.bookService.existsBookTitleYearAuthor(book.getTitle(), book.getYear(), book.getAuthors())) {
			errors.reject("duplicate", "This book already exists");
		}
		
		if(book.getYear()>Year.now().getValue()) {
			errors.reject("invalidYear", "This year is not valid");
		}

	}

}
