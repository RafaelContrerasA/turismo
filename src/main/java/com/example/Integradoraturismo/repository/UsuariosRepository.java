package com.example.Integradoraturismo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Integradoraturismo.models.Usuario;
import java.util.Optional;


@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);

}
