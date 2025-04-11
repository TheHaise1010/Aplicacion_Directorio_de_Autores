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
            em.flush(); // Forzamos el flush para asignar el ID
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear género literario", e);
            throw e;
        }
    }

    public void updateGenre(LiteraryGenre genre) {
        try {
            em.merge(genre);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar género literario", e);
            throw e;
        }
    }

    public void deleteGenre(LiteraryGenre genre) {
        try {
            LiteraryGenre managed = em.find(LiteraryGenre.class, genre.getId());
            if (managed != null) {
                em.remove(managed);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar género literario", e);
            throw e;
        }
    }

    public List<LiteraryGenre> getAllGenres() {
        try {
            return em.createQuery("SELECT g FROM LiteraryGenre g", LiteraryGenre.class).getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener géneros", e);
            throw e;
        }
    }

    public LiteraryGenre findGenreById(Integer id) {
        try {
            return em.find(LiteraryGenre.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar género por ID", e);
            throw e;
        }
    }
}
