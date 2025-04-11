package com.example.demo.bean;

import com.example.demo.entity.Author;
import com.example.demo.entity.LiteraryGenre;
import com.example.demo.model.AuthorModel;
import com.example.demo.model.LiteraryGenreModel;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped; // se elimina
import javax.faces.view.ViewScoped;           // usar este import
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@ViewScoped // Cambiado a ViewScoped para mantener el estado entre peticiones en la misma vista.
public class AuthorBean implements Serializable {

    @Inject
    private AuthorModel authorModel;

    @Inject
    private LiteraryGenreModel genreModel;

    // Inyectamos el bean de LiteraryGenre para obtener el ID seleccionado
    @Inject
    private LiteraryGenreBean literaryGenreBean;

    private Author author = new Author();
    private List<Author> authors;
    private String message;

    @PostConstruct
    public void init() {
        loadAuthors();
    }

    /**
     * Método para agregar o actualizar un autor.
     * Si el autor ya tiene ID (ya existe), se actualiza; en caso contrario, se crea.
     * Se asigna al autor el LiteraryGenre correspondiente usando literaryGenreBean.selectedGenreId.
     */
    public void addAuthor() {
        author.setBirthDate(new Date());

        // Se asigna el género literario si se seleccionó alguno.
        if (literaryGenreBean.getSelectedGenreId() != null) {
            LiteraryGenre selectedGenre = genreModel.findGenreById(literaryGenreBean.getSelectedGenreId());
            author.setLiteraryGenre(selectedGenre);
        } else {
            author.setLiteraryGenre(null);
        }

        if (author.getId() == null) {
            // Autor nuevo: se crea.
            authorModel.createAuthor(author);
            message = "Autor agregado correctamente.";
        } else {
            // Autor existente: se actualiza.
            authorModel.updateAuthor(author);
            message = "Autor actualizado correctamente.";
        }
        loadAuthors();
        // Reiniciar el formulario
        author = new Author();
        literaryGenreBean.setSelectedGenreId(null);
    }

    /**
     * Método para editar un autor.
     * Al hacer clic en "Editar" se copia el autor seleccionado en el bean para que aparezca en el formulario.
     */
    public void editAuthor(Author a) {
        this.author = a;
        if (a.getLiteraryGenre() != null) {
            literaryGenreBean.setSelectedGenreId(a.getLiteraryGenre().getId());
        } else {
            literaryGenreBean.setSelectedGenreId(null);
        }
    }

    /**
     * Método para borrar un autor.
     */
    public void deleteAuthor(Author a) {
        authorModel.deleteAuthor(a);
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
