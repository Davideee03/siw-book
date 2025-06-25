package it.uniroma3.siw.model;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class AuthorPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY, optional = false)
    private byte[] data;

    @OneToOne
    @JoinColumn(name = "author_id")
    private Author author;

    // Costruttore vuoto richiesto da JPA
    public AuthorPhoto() {
    }

    // Costruttore utile se vuoi istanziare con dati
    public AuthorPhoto(byte[] data, Author author) {
        this.data = data;
        this.author = author;
    }

    // Getter e Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "AuthorPhoto{id=" + id + ", author=" + (author != null ? author.getId() : "null") + "}";
    }
}
