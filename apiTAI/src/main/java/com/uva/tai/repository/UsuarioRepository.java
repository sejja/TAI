package com.uva.tai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.uva.tai.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsUsuarioByEmail(String email);
    Usuario getByEmail(String email);
    
    List<Usuario> findByEnabled(boolean enabled);

    List<Usuario> findByEmail(String email);

}