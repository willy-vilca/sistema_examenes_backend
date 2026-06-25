package com.sistemaexamenes.controller;

import com.sistemaexamenes.dto.auth.LoginRequestDTO;
import com.sistemaexamenes.dto.auth.LoginResponseDTO;
import com.sistemaexamenes.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                authService.login(dto, request)
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            HttpServletRequest request
    ) {
        authService.logout(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<LoginResponseDTO> obtenerUsuarioActual() {

        return ResponseEntity.ok(
                authService.obtenerUsuarioActual()
        );

    }

}
