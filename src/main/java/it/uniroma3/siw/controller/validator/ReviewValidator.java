package it.uniroma3.siw.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siw.model.Review;

@Component
public class ReviewValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Review.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Review review = (Review) target;

		if (review.getMark() > 5 || review.getMark() < 0)
			errors.reject("invalidMark", "The review mark must be between 0 and 5.");

	}

}
