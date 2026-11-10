package com.elloquito.baseloquito.repository;

import com.elloquito.baseloquito.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsernameIgnoreCase(String username);
}
