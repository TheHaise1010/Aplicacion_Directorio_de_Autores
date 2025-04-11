package com.example.demo.model;

import com.example.demo.entity.AuthorLiteraryGenre;
import com.example.demo.entity.AuthorLiteraryGenreId;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class AuthorLiteraryGenreModel {

    private static final Logger LOGGER = Logger.getLogger(AuthorLiteraryGenreModel.class.getName());

    @PersistenceContext(unitName = "AuthorsPU")
    private EntityManager em;

    public void createAssociation(AuthorLiteraryGenre association) {
        try {
            em.persist(association);
            LOGGER.info("Asociación creada correctamente.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al crear la asociación.", e);
        }
    }

    public void deleteAssociation(AuthorLiteraryGenre association) {
        try {
            // Se obtiene la asociación administrada a partir de su clave compuesta
            AuthorLiteraryGenre managedAssociation = em.find(AuthorLiteraryGenre.class, association.getId());
            if (managedAssociation != null) {
                em.remove(managedAssociation);
            }
        } catch(Exception e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar la asociación.", e);
            throw e; // Propagamos para que se aborte la transacción
        }
    }


    public List<AuthorLiteraryGenre> getAllAssociations() {
        try {
            return em.createQuery("SELECT al FROM AuthorLiteraryGenre al", AuthorLiteraryGenre.class)
                    .getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al obtener las asociaciones.", e);
            return null;
        }
    }

    public AuthorLiteraryGenre findAssociation(AuthorLiteraryGenreId id) {
        try {
            return em.find(AuthorLiteraryGenre.class, id);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error al buscar la asociación con ID " + id, e);
            return null;
        }
    }
}
