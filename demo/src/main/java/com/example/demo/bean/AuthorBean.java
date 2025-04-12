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
import java.util.List;

@Named
@ViewScoped  // Se utiliza ViewScoped para mantener el estado entre peticiones en la misma vista.
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
     * Se utiliza el valor ingresado por el usuario para la fecha de nacimiento (birthDate)
     * en lugar de asignar la fecha actual.
     * Se asigna al autor el LiteraryGenre correspondiente usando literaryGenreBean.selectedGenreId.
     */
    public void addAuthor() {
        // NOTA: Se elimina author.setBirthDate(new Date());
        // La fecha de nacimiento se toma del formulario (ingresada por el usuario)

        // Asignar el género literario si se ha seleccionado (verificamos que selectedGenreId no sea null)
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
