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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        // 1. Validaciones para Primer Nombre
        if (author.getFirstName() == null || author.getFirstName().trim().isEmpty()) {
            errorMessage.append("El campo Primer nombre está vacío<br></br>");
            hasErrors = true;
        } else if (author.getFirstName().trim().length() > 15) {
            errorMessage.append("El campo Primer nombre no debe tener más de 15 caracteres<br></br>");
            hasErrors = true;
        } else if (!author.getFirstName().trim().matches("^[a-zA-Z]+$")) {
            errorMessage.append("El campo Primer nombre debe contener solo letras<br></br>");
            hasErrors = true;
        }

        // 1. Validaciones para Apellido
        if (author.getLastName() == null || author.getLastName().trim().isEmpty()) {
            errorMessage.append("El campo Apellido está vacío<br></br>");
            hasErrors = true;
        } else if (author.getLastName().trim().length() > 15) {
            errorMessage.append("El campo Apellido no debe tener más de 15 caracteres<br></br>");
            hasErrors = true;
        } else if (!author.getLastName().trim().matches("^[a-zA-Z]+$")) {
            errorMessage.append("El campo Apellido debe contener solo letras<br></br>");
            hasErrors = true;
        }

        // 2. Validación para Fecha de Nacimiento
        if (author.getBirthDate() == null) {
            errorMessage.append("El campo Fecha de nacimiento está vacío<br></br>");
            hasErrors = true;
        } else {
            LocalDate birthLocalDate = author.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
            if (birthLocalDate.isAfter(eighteenYearsAgo)) {
                errorMessage.append("La fecha de nacimiento debe ser hace 18 años o más<br></br>");
                hasErrors = true;
            }
        }

        // 3. Validación para Teléfono
        if (author.getPhone() != null && !author.getPhone().trim().isEmpty()) {
            if (!author.getPhone().trim().matches("^[0-9]{8}$")) {
                errorMessage.append("El campo Teléfono debe contener exactamente 8 caracteres numéricos<br></br>");
                hasErrors = true;
            }
        } else if (author.getPhone() == null || author.getPhone().trim().isEmpty()) {
            errorMessage.append("El campo Teléfono está vacío<br></br>");
            hasErrors = true;
        }

        // 4. Validación para Email
        if (author.getEmail() != null && !author.getEmail().trim().isEmpty()) {
            if (author.getEmail().trim().length() > 50) {
                errorMessage.append("El campo Email no debe exceder los 50 caracteres<br></br>");
                hasErrors = true;
            } else {
                Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                Matcher matcher = pattern.matcher(author.getEmail().trim());
                if (!matcher.matches()) {
                    errorMessage.append("El campo Email tiene un formato inválido<br></br>");
                    hasErrors = true;
                }
            }
        } else if (author.getEmail() == null || author.getEmail().trim().isEmpty()) {
            errorMessage.append("El campo Email está vacío<br></br>");
            hasErrors = true;
        }

        // Validación para Género Literario
        Integer selectedGenreId = literaryGenreBean.getSelectedGenreId();
        if (selectedGenreId == null) {
            errorMessage.append("El campo Genero literario está vacío<br></br>");
            hasErrors = true;
        }

        if (hasErrors) {
            setMessage(errorMessage.toString());
            return;
        } else {
            LiteraryGenre selectedGenre = null;
            if (selectedGenreId != null) {
                selectedGenre = genreModel.findGenreById(selectedGenreId);
                author.setLiteraryGenre(selectedGenre);
            } else {
                author.setLiteraryGenre(null);
            }

            if (author.getId() == null) {
                authorModel.createAuthor(author);
                message = "Autor agregado correctamente.";
            } else {
                authorModel.updateAuthor(author);
                message = "Autor actualizado correctamente.";
            }
            loadAuthors();
            author = new Author();
            literaryGenreBean.setSelectedGenreId(null);
        }
    }

    private void loadAuthors() {
        authors = authorModel.getAllAuthors();
    }

    public void editAuthor(Author a) {
        this.author = new Author();
        this.author.setId(a.getId());
        this.author.setFirstName(a.getFirstName());
        this.author.setLastName(a.getLastName());
        this.author.setBirthDate(a.getBirthDate());
        this.author.setPhone(a.getPhone());
        this.author.setEmail(a.getEmail());
        this.author.setLiteraryGenre(a.getLiteraryGenre());

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