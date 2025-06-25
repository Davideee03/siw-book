package it.uniroma3.siw.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private LocalDate birthDate;
    private LocalDate deathDate;

    private String nationality;

    @ManyToMany(mappedBy = "authors")
    private List<Book> books = new ArrayList<>();
    
    @OneToOne(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AuthorPhoto authorPhoto;

    // Getter & Setter per ID
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter & Setter per nome
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter & Setter per date
    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate;
    }

    // Getter & Setter per nazionalità
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    // Relazione con Book
    public List<Book> getBooks() {
        return this.books;
    }

    public AuthorPhoto getAuthorPhoto() {
        return this.authorPhoto;
    }

    public void setAuthorPhoto(AuthorPhoto authorPhoto) {
        this.authorPhoto = authorPhoto;
    }

    public void setPhoto(AuthorPhoto authorPhoto) {
    	this.authorPhoto = authorPhoto;
    }
}
