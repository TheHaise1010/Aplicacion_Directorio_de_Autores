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

    // Para agregar un nuevo género
    private LiteraryGenre genre;

    // Lista de géneros cargados desde la base de datos
    private List<LiteraryGenre> genres;

    // Mensaje de confirmación u otro feedback
    private String message;

    // Propiedad para enlazar la selección en el dropdown (aunque por ahora solo se use para mostrar)
    private LiteraryGenre selectedGenre;

    public LiteraryGenreBean() {
        // Inicializamos la entidad para el formulario de nuevo género
        this.genre = new LiteraryGenre();
    }

    @PostConstruct
    public void init() {
        loadGenres();
    }

    /**
     * Agrega un nuevo género literario a la base de datos.
     */
    public void addGenre() {
        genreModel.createGenre(genre);
        loadGenres();
        message = "Género literario agregado correctamente.";
        // Reiniciamos la entidad para limpiar el formulario
        genre = new LiteraryGenre();
    }

    /**
     * Carga la lista de géneros literarios.
     */
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
