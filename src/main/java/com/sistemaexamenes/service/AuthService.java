package com.sistemaexamenes.service;

import com.sistemaexamenes.dto.auth.LoginRequestDTO;
import com.sistemaexamenes.dto.auth.LoginResponseDTO;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginResponseDTO login(LoginRequestDTO dto, HttpServletRequest request);

    void logout(HttpServletRequest request);

    LoginResponseDTO obtenerUsuarioActual();
}