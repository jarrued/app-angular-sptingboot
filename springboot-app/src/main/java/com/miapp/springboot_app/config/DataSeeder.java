package com.miapp.springboot_app.config;

import com.miapp.springboot_app.model.Usuario;
import com.miapp.springboot_app.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner initData(UsuarioRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (repo.findByEmail("admin@admin.com").isEmpty()) {
                Usuario user = new Usuario();
                user.setNombre("Admin");
                user.setEmail("admin@admin.com");
                user.setPassword(encoder.encode("1234"));
                user.setRol("ROLE_ADMIN");

                repo.save(user);
                System.out.println("✅ Usuario admin creado");
            }
        };
    }
}
