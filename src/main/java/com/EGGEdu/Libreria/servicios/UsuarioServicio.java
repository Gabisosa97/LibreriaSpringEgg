package com.EGGEdu.Libreria.servicios;

import com.EGGEdu.Libreria.entidades.Usuario;
import com.EGGEdu.Libreria.errores.ErrorServicio;
import com.EGGEdu.Libreria.repositorios.UsuarioRepositorio;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements UserDetailsService {

    @Autowired //la variable la inicializa el servidor de aplicaciones
    private UsuarioRepositorio usuarioRepositorio;

    @Transactional
    public void cargarUsuario(
            Long documento,
            String nombre,
            String apellido,
            String telefono,
            String clave
    ) throws ErrorServicio {
        validar(documento, nombre, apellido, telefono);
        Usuario usuario = new Usuario();
        usuario.setDocumento(documento);
        usuario.setNombre(nombre);
        
        usuario.setClave(clave);
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
        
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);


        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void modificarUsuario(String id, Long documento, String nombre,
            String apellido, String telefono, boolean alta) throws ErrorServicio {
        Usuario usuario = usuarioRepositorio.buscarPorId(id);
        usuario.setDocumento(documento);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setTelefono(telefono);
        usuario.setAlta(alta);

        usuarioRepositorio.save(usuario);
    }

    @Transactional
    public void bajaUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(false);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("NO SE ENCONTRÓ EL LIBRO BUSCADO");
        }
    }

    @Transactional
    public void altaUsuario(String id) throws ErrorServicio {

        Optional<Usuario> respuesta = usuarioRepositorio.findById(id);
        if (respuesta.isPresent()) {
            Usuario usuario = respuesta.get();
            usuario.setAlta(true);
            usuarioRepositorio.save(usuario);
        } else {
            throw new ErrorServicio("NO SE ENCONTRÓ EL LIBRO BUSCADO");
        }
    }

    @Transactional
    public void eliminarUsuario(String id) {
        Usuario usuario = usuarioRepositorio.buscarPorId(id);
        usuarioRepositorio.delete(usuario);
    }

    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepositorio.ListarUsuarios();
        return usuarios;
    }

    public Usuario listarUsuario(String id) {
        Usuario usuario = usuarioRepositorio.buscarPorId(id);
        return usuario;
    }

    public void validar(Long documento, String nombre, String apellido, String telefono) throws ErrorServicio {
        if (documento <= 0 || String.valueOf(documento) == null) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
        if (telefono == null || telefono.isEmpty()) {
            throw new ErrorServicio("El nombre no puede estar vacio");
        }
//        if (alta != true) {
//            throw new ErrorServicio("El nombre no puede estar vacio");
//        }

    }

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
