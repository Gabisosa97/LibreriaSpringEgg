package com.EGGEdu.Libreria.repositorios;

import com.EGGEdu.Libreria.entidades.Autor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepositorio extends JpaRepository<Autor, String> {

    @Query("SELECT c FROM Autor c")
    public List<Autor> ListarAutores();

    @Query("SELECT c FROM Autor c WHERE c.id = :id")
    public Autor buscarPorId(@Param("id") String id);

    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre")
    public Autor buscarPorNombre(@Param("nombre") String nombre);

    @Query("SELECT c FROM Autor c WHERE c.apellido = :apellido")
    public Autor buscarPorApellido(@Param("apellido") String apellido);

    @Query("SELECT c FROM Autor c WHERE c.nombre = :nombre AND c.apellido = :apellido")
    public Autor buscarPorNombreYApellido(@Param("nombre") String nom, @Param("apellido") String ape);

}
