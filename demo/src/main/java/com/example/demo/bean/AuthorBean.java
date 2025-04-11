package com.example.demo.bean;

import com.example.demo.entity.Author;
import com.example.demo.entity.LiteraryGenre;
import com.example.demo.model.AuthorModel;
import com.example.demo.model.LiteraryGenreModel;
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

    @Inject
    private LiteraryGenreModel genreModel;

    // Inyectamos el bean de LiteraryGenre para obtener selectedGenreId
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
        // Establecer la fecha de nacimiento (ejemplo: la fecha actual)
        author.setBirthDate(new Date());

        // Si se seleccionó un género, buscar el objeto LiteraryGenre correspondiente
        if (literaryGenreBean.getSelectedGenreId() != null) {
            LiteraryGenre selectedGenre = genreModel.findGenreById(literaryGenreBean.getSelectedGenreId());
            author.setLiteraryGenre(selectedGenre);
        } else {
            // En caso de que no se haya seleccionado género, se puede asignar null o manejarlo según la lógica
            author.setLiteraryGenre(null);
        }

        // Persistir el autor y forzar el flush para que se asigne el ID
        authorModel.createAuthor(author);
        loadAuthors();
        message = "Autor agregado correctamente.";
        // Reiniciar el formulario
        author = new Author();
    }

    public void updateAuthor() {
        authorModel.updateAuthor(author);
        loadAuthors();
        message = "Autor actualizado correctamente.";
    }

    public void deleteAuthor(Author author) {
        authorModel.deleteAuthor(author);
        loadAuthors();
        message = "Autor eliminado correctamente.";
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
