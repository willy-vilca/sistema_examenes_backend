package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.auth.LoginRequestDTO;
import com.sistemaexamenes.dto.auth.LoginResponseDTO;
import com.sistemaexamenes.entity.Usuario;
import com.sistemaexamenes.security.CustomUserDetails;
import com.sistemaexamenes.service.AuthService;
import com.sistemaexamenes.service.SesionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final SesionService sesionService;

    @Override
    public LoginResponseDTO login(LoginRequestDTO dto, HttpServletRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getCorreo(),
                        dto.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        HttpSession session = request.getSession(true);

        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        Usuario usuario =
                ((CustomUserDetails) authentication.getPrincipal())
                        .getUsuario();

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol()
        );

    }

    @Override
    public void logout(HttpServletRequest request) {

        HttpSession session = request.getSession(false);

        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

    }

    @Override
    public LoginResponseDTO obtenerUsuarioActual() {

        Usuario usuario = sesionService.obtenerUsuarioActual();

        if (usuario == null) {
            throw new RuntimeException("No existe un usuario autenticado.");
        }

        return new LoginResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol()
        );

    }

}
