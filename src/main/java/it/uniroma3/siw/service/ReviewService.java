package it.uniroma3.siw.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.model.Review;
import it.uniroma3.siw.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;

	public Review save(Review review) {
		return this.reviewRepository.save(review);
	}

	public void deleteAllReviews() {
		reviewRepository.deleteAll();
	}
	

	public void deleteReview(Review review) {
		this.reviewRepository.delete(review);
		
	}
}
