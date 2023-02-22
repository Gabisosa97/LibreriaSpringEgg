package com.EGGEdu.Libreria.controladores;

import com.EGGEdu.Libreria.entidades.Autor;
import com.EGGEdu.Libreria.servicios.AutorServicio;
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
@RequestMapping("/autor")
public class AutorControlador {

    @Autowired
    private AutorServicio autorServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Autor> autores = autorServicio.listarAutores();
        modelo.addAttribute("autores", autores);
        return "listaAutores.html";
    }

    @GetMapping("/registro")
    public String formulario() {
        return "form-autor";
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String nombre, @RequestParam String apellido) {
        try {
            autorServicio.cargarAutor(nombre, apellido);
            modelo.put("exito", "Registro exitoso");
            return "form-autor";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "form-autor";
        }

    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            autorServicio.eliminarAutor(id);
            return "redirect:/autor/lista";
        } catch (Exception e) {
            return "listaAutores.html";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Autor autor = autorServicio.listarAutor(id);
        modelo.addAttribute("autor", autor);
        return "modificarAutor";

    }

    @PostMapping("/confirmarModificacion/{id}")
    public String confirmarModificacion(ModelMap modelo, @PathVariable String id, @RequestParam String nombre, @RequestParam String apellido) {
        try {
            autorServicio.modificarAutor(nombre, apellido, id);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/autor/lista";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "form-autor";
        }
    }

}
