package com.example.demo.converter;

import com.example.demo.entity.LiteraryGenre;
import com.example.demo.bean.LiteraryGenreBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(forClass = LiteraryGenre.class)
public class LiteraryGenreConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value == null || value.trim().isEmpty()){
            return null;
        }
        try {
            Long id = Long.valueOf(value);
            // Obtenemos el bean que contiene la lista de géneros para buscar el objeto que coincide con el id.
            LiteraryGenreBean bean = fc.getApplication()
                    .evaluateExpressionGet(fc, "#{literaryGenreBean}", LiteraryGenreBean.class);
            if(bean != null && bean.getGenres() != null){
                for (LiteraryGenre genre : bean.getGenres()) {
                    if(genre.getId().equals(id)){
                        return genre;
                    }
                }
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException("Error de conversión: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object == null){
            return "";
        }
        if(object instanceof LiteraryGenre){
            LiteraryGenre genre = (LiteraryGenre) object;
            return (genre.getId() != null) ? genre.getId().toString() : "";
        } else {
            throw new IllegalArgumentException("El objeto " + object + " no es de tipo LiteraryGenre");
        }
    }
}
