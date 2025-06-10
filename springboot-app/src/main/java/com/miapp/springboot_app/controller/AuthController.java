package com.miapp.springboot_app.controller;

import com.miapp.springboot_app.security.JwtUtil;
import com.miapp.springboot_app.security.CustomUserDetailsService;
import com.miapp.springboot_app.dto.*;
import com.miapp.springboot_app.model.Usuario;
import com.miapp.springboot_app.repository.UsuarioRepository;

import org.springframework.security.core.AuthenticationException;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private CustomUserDetailsService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }

        UserDetails user = userService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(user);

        return ResponseEntity.ok(new AuthResponse(token, user.getAuthorities().iterator().next().getAuthority()));
    }

    @Autowired
    private UsuarioRepository repo;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        // 1) Validar email duplicado
        if (repo.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("message", "Email ya registrado"));
        }

        // 2) Mapear DTO a entidad y guardar
        Usuario u = new Usuario();
        u.setNombre(req.getNombre());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setRol("ROLE_USER");
        repo.save(u);

        // 3) Responder éxito
        return ResponseEntity
                .ok(Map.of("message", "Usuario registrado correctamente"));
    }
}
