package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.entity.Usuario;
import com.sistemaexamenes.entity.enums.Rol;
import com.sistemaexamenes.security.CustomUserDetails;
import com.sistemaexamenes.service.SesionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SesionServiceImpl implements SesionService {

    @Override
    public Usuario obtenerUsuarioActual() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (!(principal instanceof CustomUserDetails userDetails)) {
            return null;
        }

        return userDetails.getUsuario();

    }

    @Override
    public Long obtenerIdUsuario() {

        Usuario usuario = obtenerUsuarioActual();

        return usuario != null ? usuario.getId() : null;

    }

    @Override
    public boolean esAdministrador() {

        Usuario usuario = obtenerUsuarioActual();

        return usuario != null &&
                usuario.getRol() == Rol.ADMIN;

    }

}
