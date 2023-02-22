package com.EGGEdu.Libreria.servicios;

import com.EGGEdu.Libreria.entidades.Usuario;
import com.EGGEdu.Libreria.entidades.Libro;
import com.EGGEdu.Libreria.entidades.Prestamo;
import com.EGGEdu.Libreria.errores.ErrorServicio;
import com.EGGEdu.Libreria.repositorios.UsuarioRepositorio;
import com.EGGEdu.Libreria.repositorios.LibroRepositorio;
import com.EGGEdu.Libreria.repositorios.PrestamoRepositorio;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrestamoServicio {

    @Autowired //la variable la inicializa el servidor de aplicaciones
    private PrestamoRepositorio prestamoRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private LibroRepositorio libroRepositorio;
    
    @Transactional
    public void cargarPrestamo(Date fechaPrestamo, Date fechaDevolucion, String idLibro, String idUsuario) throws ErrorServicio {
        validar(fechaPrestamo, fechaDevolucion, idLibro, idUsuario);
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        prestamo.setAlta(true);
        
        Usuario usuario = usuarioRepositorio.getOne(idUsuario);
        Libro libro = libroRepositorio.getOne(idLibro);

        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);

        prestamoRepositorio.save(prestamo);
    }

    @Transactional
    public void modificarPrestamo(String id, Date fechaPrestamo, Date fechaDevolucion,
            String idLibro, String idUsuario) throws ErrorServicio {
        Prestamo prestamo = prestamoRepositorio.buscarPorId(id);
        prestamo.setFechaPrestamo(fechaPrestamo);
        prestamo.setFechaDevolucion(fechaDevolucion);
        
        Usuario usuario = usuarioRepositorio.getOne(idUsuario);
        Libro libro = libroRepositorio.getOne(idLibro);
        
        prestamo.setLibro(libro);
        prestamo.setUsuario(usuario);
        
        prestamoRepositorio.save(prestamo);
    }

    @Transactional
    public void bajaPrestamo(String id) throws ErrorServicio {

        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            prestamo.setAlta(false);
            prestamoRepositorio.save(prestamo);
        } else {
            throw new ErrorServicio("NO SE ENCONTRÓ EL PRESTAMO BUSCADO");
        }
    }

    @Transactional
    public void altaPrestamo(String id) throws ErrorServicio {

        Optional<Prestamo> respuesta = prestamoRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Prestamo prestamo = respuesta.get();
            prestamo.setAlta(true);
            prestamoRepositorio.save(prestamo);
        } else {
            throw new ErrorServicio("NO SE ENCONTRÓ EL PRESTAMO BUSCADO");
        }
    }
    
    @Transactional
    public void eliminarPrestamo(String id) {
        Prestamo prestamo = prestamoRepositorio.getOne(id);
        prestamoRepositorio.delete(prestamo);
    }

    public List<Prestamo> listarPrestamos() {
        List<Prestamo> prestamos = prestamoRepositorio.ListarPrestamo();
        return prestamos;
    }

    public Prestamo listarPrestamo(String id) {
        Prestamo prestamo = prestamoRepositorio.buscarPorId(id);
        return prestamo;
    }

    public void validar(Date fechaPrestamo, Date fechaDevolucion, String idLibro, String idUsuario) throws ErrorServicio {

        if (fechaPrestamo == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (fechaDevolucion == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (idUsuario == null || String.valueOf(idUsuario).isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (idLibro == null || String.valueOf(idLibro).isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }

    }
}
