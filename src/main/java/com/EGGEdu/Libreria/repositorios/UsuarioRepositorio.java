package com.EGGEdu.Libreria.repositorios;

import com.EGGEdu.Libreria.entidades.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, String> {
    @Query("SELECT c FROM Usuario c")
    public List<Usuario> ListarUsuarios();

    @Query("SELECT c FROM Usuario c WHERE c.id = :id")
    public Usuario buscarPorId(@Param("id") String id);
    
}
