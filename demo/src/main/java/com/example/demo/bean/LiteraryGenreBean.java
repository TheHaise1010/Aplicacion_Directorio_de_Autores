package com.example.demo.bean;

import com.example.demo.entity.LiteraryGenre;
import com.example.demo.model.LiteraryGenreModel;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class LiteraryGenreBean implements Serializable {

    @Inject
    private LiteraryGenreModel genreModel;

    // Objeto para agregar un nuevo género (opcional)
    private LiteraryGenre genre = new LiteraryGenre();

    // Lista de géneros
    private List<LiteraryGenre> genres;

    // Mensaje
    private String message;

    // Propiedad para la selección en el dropdown (almacena el ID del género)
    private Integer selectedGenreId;

    @PostConstruct
    public void init() {
        loadGenres();
    }

    public void addGenre() {
        genreModel.createGenre(genre);
        loadGenres();
        message = "Género literario agregado correctamente.";
        genre = new LiteraryGenre();
    }

    private void loadGenres() {
        genres = genreModel.getAllGenres();
    }

    // Getters y Setters
    public LiteraryGenre getGenre() {
        return genre;
    }

    public void setGenre(LiteraryGenre genre) {
        this.genre = genre;
    }

    public List<LiteraryGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<LiteraryGenre> genres) {
        this.genres = genres;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getSelectedGenreId() {
        return selectedGenreId;
    }

    public void setSelectedGenreId(Integer selectedGenreId) {
        this.selectedGenreId = selectedGenreId;
    }
}
