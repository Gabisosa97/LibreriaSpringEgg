package com.EGGEdu.Libreria.servicios;

import com.EGGEdu.Libreria.entidades.Editorial;
import com.EGGEdu.Libreria.errores.ErrorServicio;
import com.EGGEdu.Libreria.repositorios.EditorialRepositorio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EditorialServicio {

    @Autowired //la variable la inicializa el servidor de aplicaciones
    private EditorialRepositorio editorialRepositorio;

    @Transactional
    public void cargarEditorial(String nombre) throws ErrorServicio {
        validar(nombre);
        Editorial editorial = new Editorial();
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void modificarEditorial(String nombre, String id) throws ErrorServicio {
        Editorial editorial = editorialRepositorio.buscarPorId(id);
        validar(nombre);
        editorial.setNombre(nombre);
        editorialRepositorio.save(editorial);
    }

    @Transactional
    public void eliminarEditorial(String id) {
        Editorial editorial = editorialRepositorio.buscarPorId(id);
        editorialRepositorio.delete(editorial);
    }

    private void validar(String nombre) throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

    }

    public List<Editorial> listarEditoriales() {
        List<Editorial> editoriales = editorialRepositorio.ListarEditoriales();
        return editoriales;
    }

    public Editorial listarEditorial(String id) {
        Editorial editorial = editorialRepositorio.buscarPorId(id);
        return editorial;
    }
    
    public Editorial listarEditorialPorNombre(String id) {
        Editorial editorial = editorialRepositorio.buscarPorNombre(id);
        return editorial;
    }

}
