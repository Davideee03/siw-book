package it.uniroma3.siw.controller.validator;

import org.springframework.validation.Validator;

import java.time.Period;
import java.time.Year;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import it.uniroma3.siw.model.Author;
import it.uniroma3.siw.service.AuthorService;

@Component
public class AuthorValidator implements Validator {

	@Autowired
	private AuthorService authorService;

	@Override
	public void validate(Object o, Errors errors) {
		Author author = (Author) o;

		if (author.getFirstName() != null && author.getLastName() != null && author.getBirthDate() != null) {
			String fullName = author.getFirstName() + " " + author.getLastName();

			if (this.authorService.existsByNameAndDate(fullName, author.getBirthDate())) {
				errors.reject("duplicate", "This author already exists");
			}

			if (author.getBirthDate() != null && author.getDeathDate() != null) {

			    boolean birthInFuture = author.getBirthDate().getYear() >= Year.now().getValue();
			    boolean deathInFuture = author.getDeathDate().getYear() >= Year.now().getValue();
			    boolean invertedDates = author.getBirthDate().isAfter(author.getDeathDate());
			    boolean tooOld = Period.between(author.getBirthDate(), author.getDeathDate()).getYears() > 120;

			    if (birthInFuture || deathInFuture || invertedDates || tooOld) {
			        errors.reject("invalidYear", "Birth or death date not valid");
			    }
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return Author.class.equals(aClass);
	}

}
