package com.EGGEdu.Libreria.controladores;

import com.EGGEdu.Libreria.entidades.Usuario;
import com.EGGEdu.Libreria.entidades.Libro;
import com.EGGEdu.Libreria.entidades.Prestamo;
import com.EGGEdu.Libreria.errores.ErrorServicio;
import com.EGGEdu.Libreria.servicios.UsuarioServicio;
import com.EGGEdu.Libreria.servicios.LibroServicio;
import com.EGGEdu.Libreria.servicios.PrestamoServicio;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping("/prestamo")
public class PrestamoControlador {

//    @InitBinder
//    public void initBinder(WebDataBinder binder, WebRequest request) {
//        //convert the date Note that the conversion here should always be in the same format as the string passed in, e.g. 2015-9-9 should be yyyy-MM-dd
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor is a custom date editor
//    }

    @Autowired
    private PrestamoServicio prestamoServicio;
    @Autowired
    private UsuarioServicio usuarioServicio;
    @Autowired
    private LibroServicio libroServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Prestamo> prestamos = prestamoServicio.listarPrestamos();
        modelo.addAttribute("prestamos", prestamos);
        return "listaPrestamos.html";
    }

    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        List<Libro> libros = libroServicio.listarLibros();
        modelo.put("usuarios", usuarios);
        modelo.put("libros", libros);
        return "form-prestamo";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String fechaPrestamo,
            @RequestParam String fechaDevolucion, @RequestParam String idLibro,
            @RequestParam String idUsuario) throws ParseException {
        Date fecha1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaPrestamo);
        Date fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDevolucion);
        try {
            prestamoServicio.cargarPrestamo(fecha1, fecha2, idLibro, idUsuario);
            modelo.put("exito", "Registro exitoso");
            return "form-prestamo";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "form-prestamo";
        }
    }

    @GetMapping("/Prestamo_Alta/{id}")
    public String altaPrestamo(@PathVariable String id) {
        try {
            prestamoServicio.altaPrestamo(id);
            return "redirect:/prestamo/lista";
        } catch (Exception e) {
            return "listaPrestamos.html";
        }
    }

    @GetMapping("/Prestamo_Baja/{id}")
    public String bajaPrestamo(@PathVariable String id) {
        try {
            prestamoServicio.bajaPrestamo(id);
            return "redirect:/prestamo/lista";
        } catch (Exception e) {
            return "listaPrestamos.html";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            prestamoServicio.eliminarPrestamo(id);
            return "redirect:/prestamo/lista";
        } catch (Exception e) {
            return "listaPrestamos";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Prestamo prestamo = prestamoServicio.listarPrestamo(id);
        modelo.addAttribute("prestamo", prestamo);
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        List<Libro> libros = libroServicio.listarLibros();
        modelo.put("usuarios", usuarios);
        modelo.put("libros", libros);
        return "modificarPrestamo";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @RequestParam String id,
            @RequestParam String fechaPrestamo, @RequestParam String fechaDevolucion,
            @RequestParam String idLibro, @RequestParam String idUsuario) throws ParseException {
        Date fecha1 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaPrestamo);
        Date fecha2 = new SimpleDateFormat("yyyy-MM-dd").parse(fechaDevolucion);

        try {
            prestamoServicio.modificarPrestamo(id, fecha1, fecha2, idLibro, idUsuario);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/prestamo/lista";
        } catch (ErrorServicio e) {
            modelo.put("error", "Falto algun dato");
            return "modificarPrestamo";
        }
    }
}
