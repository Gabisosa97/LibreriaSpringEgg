package com.EGGEdu.Libreria.servicios;

import com.EGGEdu.Libreria.entidades.Autor;
import com.EGGEdu.Libreria.entidades.Editorial;
import com.EGGEdu.Libreria.entidades.Libro;
import com.EGGEdu.Libreria.errores.ErrorServicio;
import com.EGGEdu.Libreria.repositorios.AutorRepositorio;
import com.EGGEdu.Libreria.repositorios.EditorialRepositorio;
import com.EGGEdu.Libreria.repositorios.LibroRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroServicio {

    @Autowired
    private LibroRepositorio libroRepositorio;
    @Autowired
    private AutorRepositorio autorRepositorio;
    @Autowired
    private EditorialRepositorio editorialRepositorio;

    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @Transactional
    public void cargarLibro(
            Long isbn,
            String titulo,
            int anio,
            int ejemplares,
            int ejemplaresPrestados,
            String nombreAutor,
            String apellidoAutor,
            String nombreEditorial,
            String foto
    ) throws ErrorServicio {
        validar(isbn, titulo, anio, ejemplares, ejemplaresPrestados,
                nombreAutor, apellidoAutor, nombreEditorial, foto);
        Libro libro = new Libro();
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setFoto(foto);

        //AUTOR///////////////////////////////
        Optional<Autor> respuestaAutor = autorRepositorio.findById(autorServicio.listarAutorPorApellido(apellidoAutor).getId());

        if (respuestaAutor.isEmpty()) {
            Autor autor = respuestaAutor.get();
            libro.setAutor(autor);
        } else {
            Autor autor = new Autor();
            autor.setNombre(nombreAutor);
            autor.setApellido(apellidoAutor);
            libro.setAutor(autor);
        }

//        Autor autor = new Autor();
//        autor.setNombre(nombreAutor);
//        autor.setApellido(apellidoAutor);
//        libro.setAutor(autor);
        //////////////////////////////////////
        //EDITORIAL///////////////////////////
        Optional<Editorial> respuestaEditorial = editorialRepositorio.findById(editorialServicio.listarEditorialPorNombre(nombreEditorial).getId());

        if (respuestaEditorial.isEmpty()) {
            Editorial editorial = respuestaEditorial.get();
            libro.setEditorial(editorial);
        } else {
            Editorial editorial = new Editorial();
            editorial.setNombre(nombreEditorial);
            libro.setEditorial(editorial);
        }
//        Editorial editorial = new Editorial();
//        editorial.setNombre(nombreEditorial);
//        libro.setEditorial(editorial);
        //////////////////////////////////////

        libroRepositorio.save(libro);
    }

    @Transactional
    public void modificarLibro(Long isbn, String titulo,
            int anio, int ejemplares, int ejemplaresPrestados, String id,
            String foto) throws ErrorServicio {
        Libro libro = libroRepositorio.buscarPorId(id);
        libro.setIsbn(isbn);
        libro.setTitulo(titulo);
        libro.setAnio(anio);
        libro.setEjemplares(ejemplares);
        libro.setEjemplaresPrestados(ejemplaresPrestados);
        libro.setFoto(foto);
        libroRepositorio.save(libro);
    }

    @Transactional
    public void bajaLibro(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(false);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("NO SE ENCONTRÓ EL LIBRO BUSCADO");
        }
    }

    @Transactional
    public void altaLibro(String id) throws ErrorServicio {

        Optional<Libro> respuesta = libroRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Libro libro = respuesta.get();
            libro.setAlta(true);
            libroRepositorio.save(libro);
        } else {
            throw new ErrorServicio("NO SE ENCONTRÓ EL LIBRO BUSCADO");
        }
    }

    @Transactional
    public void eliminarLibro(String id
    ) {
        Libro libro = libroRepositorio.buscarPorId(id);
        libroRepositorio.delete(libro);
    }

    public List<Libro> listarLibros() {
        List<Libro> libros = libroRepositorio.ListarLibros();
        return libros;
    }

    public Libro listarLibro(String id) {
        Libro libro = libroRepositorio.buscarPorId(id);
        return libro;
    }

    public void validar(Long isbn, String titulo, Integer anio, Integer ejemplares, Integer ejemplaresPrestados, String nombreAutor, String apellidoAutor, String nombreEditorial, String foto) throws ErrorServicio {
        if (isbn <= 0 || String.valueOf(isbn) == null || String.valueOf(isbn).isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (anio <= 0 || String.valueOf(anio) == null || String.valueOf(anio).isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (ejemplares <= 0 || String.valueOf(ejemplares) == null || String.valueOf(ejemplares).isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (ejemplaresPrestados <= 0 || String.valueOf(ejemplaresPrestados) == null || String.valueOf(ejemplaresPrestados).isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (titulo == null || titulo.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (nombreAutor == null || nombreAutor.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (apellidoAutor == null || apellidoAutor.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (nombreEditorial == null || nombreEditorial.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (foto == null || foto.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

    }

}
