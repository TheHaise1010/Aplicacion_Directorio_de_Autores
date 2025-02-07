package com.example.demo.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class AuthorLiteraryGenreId implements Serializable {

    private Long authorId;
    private Long genreId;

    public AuthorLiteraryGenreId() { }

    public AuthorLiteraryGenreId(Long authorId, Long genreId) {
        this.authorId = authorId;
        this.genreId = genreId;
    }

    // Getters y Setters
    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    // equals() y hashCode() son necesarios para una clave compuesta
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorLiteraryGenreId)) return false;
        AuthorLiteraryGenreId that = (AuthorLiteraryGenreId) o;
        return Objects.equals(authorId, that.authorId) &&
                Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, genreId);
    }
}
