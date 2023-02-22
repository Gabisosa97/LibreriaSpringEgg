package com.EGGEdu.Libreria.controladores;

import com.EGGEdu.Libreria.entidades.Autor;
import com.EGGEdu.Libreria.entidades.Editorial;
import com.EGGEdu.Libreria.entidades.Libro;
import com.EGGEdu.Libreria.servicios.AutorServicio;
import com.EGGEdu.Libreria.servicios.EditorialServicio;
import com.EGGEdu.Libreria.servicios.LibroServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/libro")
public class LibroControlador {

    @Autowired
    private LibroServicio libroServicio;
    @Autowired
    private AutorServicio autorServicio;
    @Autowired
    private EditorialServicio editorialServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "listaLibros.html";
    }

    @GetMapping("/listaSimple")
    public String listaSimple(ModelMap modelo) {
        List<Libro> libros = libroServicio.listarLibros();
        modelo.addAttribute("libros", libros);
        return "listaLibrosSimple.html";
    }

    @GetMapping("/Libro_Alta/{id}")
    public String altaLibro(@PathVariable String id) {

        try {
            libroServicio.altaLibro(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "listaLibros.html";
        }
    }

    @GetMapping("/Libro_Baja/{id}")
    public String bajaLibro(@PathVariable String id) {

        try {
            libroServicio.bajaLibro(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "listaLibros.html";
        }
    }

    @GetMapping("/registro")
    public String formulario(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        List<Editorial> editoriales = editorialServicio.listarEditoriales();
        modelo.put("autores", autores);
        modelo.put("editoriales", editoriales);
        return "form-libro";
    }

    @PostMapping("/registro")
    public String guardar(
            ModelMap modelo,
            @RequestParam Long isbn,
            @RequestParam String titulo,
            @RequestParam int anio,
            @RequestParam int ejemplares,
            @RequestParam int ejemplaresPrestados,
            @RequestParam String nombreAutor,
            @RequestParam String apellidoAutor,
            @RequestParam String nombreEditorial,
            @RequestParam String foto
    ) {
        try {
            libroServicio.cargarLibro(isbn, titulo, anio, ejemplares,
                    ejemplaresPrestados, nombreAutor, apellidoAutor, nombreEditorial, foto);
            modelo.put("exito", "Registro exitoso");
            return "form-libro";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "form-libro";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            libroServicio.eliminarLibro(id);
            return "redirect:/libro/lista";
        } catch (Exception e) {
            return "listaLibros";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Libro libro = libroServicio.listarLibro(id);
        modelo.addAttribute("libro", libro);
        return "modificarLibro";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Long isbn, @RequestParam String titulo, @RequestParam int anio, @RequestParam int ejemplares, @RequestParam int ejemplaresPrestados, @RequestParam String foto) {
        try {
            libroServicio.modificarLibro(isbn, titulo, anio, ejemplares, ejemplaresPrestados, id, foto);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/libro/lista";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "modificarLibro";
        }
    }

}
