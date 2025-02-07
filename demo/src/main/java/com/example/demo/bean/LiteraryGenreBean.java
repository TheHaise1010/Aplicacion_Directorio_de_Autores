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

    private LiteraryGenre genre;
    private List<LiteraryGenre> genres;
    private String message;

    // Propiedad para enlazar la selección del dropdown
    private LiteraryGenre selectedGenre;

    public LiteraryGenreBean() {
        this.genre = new LiteraryGenre();
    }

    @PostConstruct
    public void init() {
        loadGenres();
    }

    public void addGenre() {
        genreModel.createGenre(genre);
        loadGenres();
        message = "Género literario agregado correctamente.";
        genre = new LiteraryGenre(); // Limpiar formulario
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

    public LiteraryGenre getSelectedGenre() {
        return selectedGenre;
    }

    public void setSelectedGenre(LiteraryGenre selectedGenre) {
        this.selectedGenre = selectedGenre;
    }
}
