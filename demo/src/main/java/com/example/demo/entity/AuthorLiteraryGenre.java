package com.example.demo.entity;

import javax.persistence.*;

@Entity
@Table(name = "author_literarygenre")
public class AuthorLiteraryGenre {

    @EmbeddedId
    private AuthorLiteraryGenreId id;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "author_id")
    private Author author;

    @ManyToOne
    @MapsId("genreId")
    @JoinColumn(name = "genre_id")
    private LiteraryGenre literaryGenre;

    public AuthorLiteraryGenre() { }

    // Constructor que crea la asociación y establece la clave compuesta
    public AuthorLiteraryGenre(Author author, LiteraryGenre literaryGenre) {
        this.author = author;
        this.literaryGenre = literaryGenre;
        this.id = new AuthorLiteraryGenreId(author.getId(), literaryGenre.getId());
    }

    // Getters y Setters
    public AuthorLiteraryGenreId getId() {
        return id;
    }

    public void setId(AuthorLiteraryGenreId id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public LiteraryGenre getLiteraryGenre() {
        return literaryGenre;
    }

    public void setLiteraryGenre(LiteraryGenre literaryGenre) {
        this.literaryGenre = literaryGenre;
    }
}
