package com.uva.microservicio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.uva.microservicio.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    boolean existsUsuarioByEmail(String email);
    Usuario getByEmail(String email);
}