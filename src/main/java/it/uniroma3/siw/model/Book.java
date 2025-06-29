package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int year;

	@Column(columnDefinition = "TEXT")
	private String plot;

	@Enumerated(EnumType.STRING)
	private Genre genre;

	@ManyToMany
	@JoinTable(name = "book_authors", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
	private List<Author> authors = new ArrayList<>();

	@OneToMany(mappedBy = "book")
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "book")
	private List<BookPhoto> photos = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public List<Review> getReviews() {
		return this.reviews;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public List<BookPhoto> getPhotos() {
		return this.photos;
	}

	public void addPhoto(BookPhoto bookPhoto) {
		this.photos.add(bookPhoto);
	}

	public void addReview(Review review) {
		this.reviews.add(review);
	}
}
