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

@Entity
public class BookPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.EAGER, optional = false)
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    // Costruttore vuoto richiesto da JPA
    public BookPhoto() {
    }

    // Costruttore utile se vuoi istanziare con dati
    public BookPhoto(byte[] data, Book book) {
        this.data = data;
        this.book = book;
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

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BookPhoto{id=" + id + ", book=" + (book != null ? book.getId() : "null") + "}";
    }
}
