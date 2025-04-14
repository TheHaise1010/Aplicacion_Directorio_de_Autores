package com.example.demo.bean;

import com.example.demo.entity.Author;
import com.example.demo.entity.LiteraryGenre;
import com.example.demo.model.AuthorModel;
import com.example.demo.model.LiteraryGenreModel;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped
public class AuthorBean implements Serializable {

    @Inject
    private AuthorModel authorModel;

    @Inject
    private LiteraryGenreModel genreModel;

    // Bean para acceder al género literario seleccionado
    @Inject
    private LiteraryGenreBean literaryGenreBean;

    private Author author = new Author();
    private List<Author> authors;
    private String message;

    @PostConstruct
    public void init() {
        loadAuthors();
    }

    public void addAuthor() {
        StringBuilder errorMessage = new StringBuilder();
        boolean hasErrors = false;

        if (author == null) {
            author = new Author();
            errorMessage.append("No autor seteado<br></br>");
            hasErrors = true;
        }

        if (author.getFirstName() == null || author.getFirstName().trim().isEmpty()) {
            errorMessage.append("El campo Primer nombre está vacío<br></br>");
            hasErrors = true;
        }

        if (author.getLastName() == null || author.getLastName().trim().isEmpty()) {
            errorMessage.append("El campo Apellido está vacío<br></br>");
            hasErrors = true;
        }

        if (author.getBirthDate() == null) {
            errorMessage.append("El campo Fecha de nacimiento está vacío<br></br>");
            hasErrors = true;
        }

        if (author.getPhone() == null || author.getPhone().trim().isEmpty()) {
            errorMessage.append("El campo Teléfono está vacío<br></br>");
            hasErrors = true;
        }

        if (author.getEmail() == null || author.getEmail().trim().isEmpty()) {
            errorMessage.append("El campo Email está vacío<br></br>");
            hasErrors = true;
        }

        // Se asigna el género literario si existe selección
        if (literaryGenreBean.getSelectedGenreId() == null) {
            errorMessage.append("El campo Genero literario está vacío<br></br>");
            hasErrors = true;
        } else {
            LiteraryGenre selectedGenre = genreModel.findGenreById(literaryGenreBean.getSelectedGenreId());
            author.setLiteraryGenre(selectedGenre);
        }

        if (hasErrors) {
            setMessage(errorMessage.toString());
            return;
        }

        if (author.getId() == null) {
            authorModel.createAuthor(author);
            message = "Autor agregado correctamente.";
        } else {
            authorModel.updateAuthor(author);
            message = "Autor actualizado correctamente.";
        }
        loadAuthors();
        // Reiniciar el formulario solo si la operación fue exitosa
        author = new Author();
        literaryGenreBean.setSelectedGenreId(null);
    }

    private void loadAuthors() {
        authors = authorModel.getAllAuthors();
    }

    public void editAuthor(Author a) {
        this.author = a;
        if (a.getLiteraryGenre() != null) {
            literaryGenreBean.setSelectedGenreId(a.getLiteraryGenre().getId());
        } else {
            literaryGenreBean.setSelectedGenreId(null);
        }
    }

    public void deleteAuthor(Author a) {
        authorModel.deleteAuthor(a);
        loadAuthors();
        message = "Autor eliminado correctamente.";
    }

    public void contarAutores() {
        loadAuthors();
    }

    // Getters and Setters
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