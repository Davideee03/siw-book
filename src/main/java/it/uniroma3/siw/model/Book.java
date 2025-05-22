package it.uniroma3.siw.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Book {

	@Id
	  @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int year;
	private String plot;
	
	@ManyToMany
	@JoinTable(
	    name = "book_authors",
	    joinColumns = @JoinColumn(name = "book_id"),
	    inverseJoinColumns = @JoinColumn(name = "author_id")
	)
	private List<Author> authors = new ArrayList<>();

	
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

}
