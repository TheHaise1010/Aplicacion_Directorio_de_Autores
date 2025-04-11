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
        // Se inicializa el autor para el formulario
        this.author = new Author();
    }

    @PostConstruct
    public void init() {
        loadAuthors();
    }

    // Método original que persiste y luego reinicia el objeto
    public void addAuthor() {
        author.setBirthDate(new Date());
        authorModel.createAuthor(author);
        loadAuthors();
        message = "Autor agregado correctamente.";
        // Reinicia el objeto (este método se usa en otros contextos)
        this.author = new Author();
    }

    public void deleteAuthor(Author author) {
        authorModel.deleteAuthor(author);
        loadAuthors(); // refresca la lista de autores
        message = "Autor eliminado correctamente.";
    }


    /**
     * Método para persistir el autor sin reinicializarlo.
     * Se usará en la asociación para que el objeto Author mantenga su ID asignado.
     */
    public void persistAuthor() {
        author.setBirthDate(new Date());
        authorModel.createAuthor(author);
        loadAuthors();
        message = "Autor agregado correctamente.";
        // NO reiniciamos el objeto, para usarlo en la asociación.
    }

    private void loadAuthors() {
        authors = authorModel.getAllAuthors();
    }

    // Getters y Setters
    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
