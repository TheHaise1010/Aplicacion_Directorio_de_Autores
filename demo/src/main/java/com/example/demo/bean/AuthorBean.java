package com.example.demo.bean;

import com.example.demo.entity.Author;
import com.example.demo.model.AuthorModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@RequestScoped
public class AuthorBean implements Serializable {

    @Inject
    private AuthorModel authorModel;

    private Author author;
    private List<Author> authors;
    private String message;

    public AuthorBean() {
        this.author = new Author();
    }

    @PostConstruct
    public void init() {
        loadAuthors();
    }

    public void addAuthor() {
        author.setBirthDate(new Date()); // Asignamos una fecha por defecto
        authorModel.createAuthor(author);
        loadAuthors();
        this.message = "✅ Autor agregado correctamente.";
        this.author = new Author(); // Limpiar formulario después de agregar
    }

    private void loadAuthors() {
        authors = authorModel.getAllAuthors();
    }

    // Getters y Setters
    public List<Author> getAuthors() {
        return authors;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }
}
