package com.example.demo.model;

import com.example.demo.entity.Author;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
            em.flush(); // Forzamos para asignar el ID
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear autor", e);
            throw e;
        }
    }

    public void updateAuthor(Author author) {
        try {
            em.merge(author);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar autor", e);
            throw e;
        }
    }

    public void deleteAuthor(Author author) {
        try {
            Author managed = em.find(Author.class, author.getId());
            if (managed != null) {
                em.remove(managed);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar autor", e);
            throw e;
        }
    }

    public List<Author> getAllAuthors() {
        try {
            return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener autores", e);
            throw e;
        }
    }

    public Author findAuthorById(Integer id) {
        try {
            return em.find(Author.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar autor por ID", e);
            throw e;
        }
    }
}
