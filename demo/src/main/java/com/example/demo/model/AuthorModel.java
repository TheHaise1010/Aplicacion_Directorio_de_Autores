package com.example.demo.model;

import com.example.demo.entity.Author;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class AuthorModel {

    private static final Logger LOGGER = Logger.getLogger(AuthorModel.class.getName());

    @PersistenceContext(unitName = "AuthorsPU")
    private EntityManager em;

    public void createAuthor(Author author) {
        try {
            em.persist(author);
            LOGGER.info("✅ Autor guardado correctamente en la base de datos.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Error al guardar el autor.", e);
        }
    }

    public List<Author> getAllAuthors() {
        try {
            return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Error al obtener la lista de autores.", e);
            return null;
        }
    }

    public Author findAuthorById(Long id) {
        try {
            return em.find(Author.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ Error al buscar el autor con ID " + id, e);
            return null;
        }
    }
}
