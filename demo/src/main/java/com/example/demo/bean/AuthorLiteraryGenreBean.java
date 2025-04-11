package com.example.demo.bean;

import com.example.demo.entity.Author;
import com.example.demo.entity.LiteraryGenre;
import com.example.demo.entity.AuthorLiteraryGenre;
import com.example.demo.model.AuthorLiteraryGenreModel;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@RequestScoped
public class AuthorLiteraryGenreBean implements Serializable {

    @Inject
    private AuthorLiteraryGenreModel associationModel;

    // Inyectamos el bean que maneja el autor
    @Inject
    private AuthorBean authorBean;

    // Inyectamos el bean que maneja los géneros literarios (este bean debe tener la propiedad selectedGenre)
    @Inject
    private LiteraryGenreBean literaryGenreBean;

    private String message;
    private List<AuthorLiteraryGenre> associations;

    @PostConstruct
    public void init() {
        loadAssociations();
    }

    /**
     * Método sin parámetros invocado desde la vista.
     * Persiste el autor (si aún no está guardado) y crea la asociación con el género seleccionado.
     */
    public void addAssociation() {
        Author newAuthor = authorBean.getAuthor();
        LiteraryGenre selectedGenre = literaryGenreBean.getSelectedGenre();

        if(newAuthor == null || newAuthor.getFirstName() == null || newAuthor.getFirstName().trim().isEmpty()){
            message = "Debe ingresar los datos del autor.";
            return;
        }
        if(selectedGenre == null){
            message = "Debe seleccionar un género literario.";
            return;
        }

        // Si el autor no está persistido (su id es nulo), lo persistimos primero.
        if(newAuthor.getId() == null) {
            authorBean.persistAuthor();
        }

        // Crear la asociación entre el autor (ya persistido) y el género seleccionado.
        AuthorLiteraryGenre association = new AuthorLiteraryGenre(newAuthor, selectedGenre);
        associationModel.createAssociation(association);
        loadAssociations();
        message = "Asociación creada correctamente.";
    }

    /**
     * Método que elimina la asociación entre un autor y un género literario.
     * Se invoca desde la vista y recibe el objeto de asociación a eliminar.
     */

    public void deleteAssociation(AuthorLiteraryGenre association) {
        // Elimina la asociación en la tabla author_literarygenre
        associationModel.deleteAssociation(association);
        // Elimina el autor; se asume que el autor no está asociado a otros géneros
        authorBean.deleteAuthor(association.getAuthor());
        loadAssociations();
        message = "Asociación y autor eliminados correctamente.";
    }

    private void loadAssociations() {
        associations = associationModel.getAllAssociations();
    }

    // Getters y Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AuthorLiteraryGenre> getAssociations() {
        return associations;
    }

    public void setAssociations(List<AuthorLiteraryGenre> associations) {
        this.associations = associations;
    }
}
