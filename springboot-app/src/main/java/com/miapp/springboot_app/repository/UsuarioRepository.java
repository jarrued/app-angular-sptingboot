package com.miapp.springboot_app.repository;

import com.miapp.springboot_app.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
