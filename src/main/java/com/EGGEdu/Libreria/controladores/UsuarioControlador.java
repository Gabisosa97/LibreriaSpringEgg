package com.EGGEdu.Libreria.controladores;

import com.EGGEdu.Libreria.entidades.Usuario;
import com.EGGEdu.Libreria.servicios.UsuarioServicio;
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
@RequestMapping("/usuario")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/lista")
    public String lista(ModelMap modelo) {
        List<Usuario> usuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", usuarios);
        return "listaUsuarios.html";
    }

    @GetMapping("/registro")
    public String formulario() {
        return "form-usuario";
    }

    @GetMapping("/Usuario_Alta/{id}")
    public String altaUsuario(@PathVariable String id) {

        try {
            usuarioServicio.altaUsuario(id);
            return "redirect:/usuario/lista";
        } catch (Exception e) {
            return "listaUsuarios.html";
        }
    }

    @GetMapping("/Usuario_Baja/{id}")
    public String bajaUsuario(@PathVariable String id) {

        try {
            usuarioServicio.bajaUsuario(id);
            return "redirect:/usuario/lista";
        } catch (Exception e) {
            return "listaUsuarios.html";
        }
    }

    @PostMapping("/registro")
    public String guardar(ModelMap modelo, @RequestParam String clave, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono) {
        try {
            usuarioServicio.cargarUsuario(documento, nombre, apellido, telefono, clave);
            modelo.put("exito", "Registro exitoso");
            return "form-usuario";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "form-usuario";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable String id) {
        try {
            usuarioServicio.eliminarUsuario(id);
            return "redirect:/usuario/lista";
        } catch (Exception e) {
            return "listaUsuarios";
        }
    }

    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id) {
        Usuario usuario = usuarioServicio.listarUsuario(id);
        modelo.addAttribute("usuario", usuario);
        return "modificarUsuario";

    }

    @PostMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, @PathVariable String id, @RequestParam Long documento, @RequestParam String nombre, @RequestParam String apellido, @RequestParam String telefono, @RequestParam boolean alta) {
        try {
            usuarioServicio.modificarUsuario(id, documento, nombre, apellido, telefono, alta);
            modelo.put("exito", "Registro exitoso");
            return "redirect:/usuario/lista";
        } catch (Exception e) {
            modelo.put("error", "Falto algun dato");
            return "modificarUsuario";
        }
    }
}
