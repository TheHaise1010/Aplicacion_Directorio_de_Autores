package com.example.demo.model;

import com.example.demo.entity.LiteraryGenre;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class LiteraryGenreModel {

    private static final Logger LOGGER = Logger.getLogger(LiteraryGenreModel.class.getName());

    @PersistenceContext(unitName = "AuthorsPU")
    private EntityManager em;

    public void createGenre(LiteraryGenre genre) {
        try {
            em.persist(genre);
            LOGGER.info("Género literario guardado correctamente.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al guardar el género literario.", e);
        }
    }

    public List<LiteraryGenre> getAllGenres() {
        try {
            return em.createQuery("SELECT g FROM LiteraryGenre g", LiteraryGenre.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener la lista de géneros literarios.", e);
            return null;
        }
    }

    public LiteraryGenre findGenreById(Long id) {
        try {
            return em.find(LiteraryGenre.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar el género literario con ID " + id, e);
            return null;
        }
    }
}
