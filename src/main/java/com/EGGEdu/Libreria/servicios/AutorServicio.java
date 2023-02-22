package com.EGGEdu.Libreria.servicios;

import com.EGGEdu.Libreria.entidades.Autor;
import com.EGGEdu.Libreria.errores.ErrorServicio;
import com.EGGEdu.Libreria.repositorios.AutorRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AutorServicio {

    @Autowired //la variable la inicializa el servidor de aplicaciones
    private AutorRepositorio autorRepositorio;

    @Transactional
    public void cargarAutor(String nombre, String apellido) throws ErrorServicio {
        validar(nombre, apellido);
        Autor autor = new Autor();
        autor.setNombre(nombre);
        autor.setApellido(apellido);
        autorRepositorio.save(autor);
    }

    @Transactional
    public void modificarAutor(String nombre, String apellido, String id) throws ErrorServicio {
        
        Optional<Autor> result = autorRepositorio.findById(id);
        
        if(result.isPresent()){
            Autor autor = result.get();
            autor.setNombre(nombre);
            autor.setApellido(apellido);
            autorRepositorio.save(autor);
        } else {
            throw new ErrorServicio("No se encuentra un Autor con ese Id.");
        }
               
    }
    
    @Transactional
    public void eliminarAutor(String id) {
        Autor autor = autorRepositorio.buscarPorId(id);
        autorRepositorio.delete(autor);
    }

    private void validar(String nombre, String apellido) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido no puede estar vacio");
        }
    }

    public List<Autor> listarAutores() {
        List<Autor> autores = autorRepositorio.ListarAutores();
        return autores;
    }

    public Autor listarAutor(String id) {
        Autor autor = autorRepositorio.buscarPorId(id);
        return autor;
    }

    public Autor listarAutorPorNombre(String id) {
        Autor autor = autorRepositorio.buscarPorNombre(id);
        return autor;
    }
    
    public Autor listarAutorPorApellido(String id) {
        Autor autor = autorRepositorio.buscarPorApellido(id);
        return autor;
    }
    
    public Autor listarAutorPorNombreyApellido(String id) {
        Autor autor = autorRepositorio.buscarPorApellido(id);
        return autor;
    }
    
}
