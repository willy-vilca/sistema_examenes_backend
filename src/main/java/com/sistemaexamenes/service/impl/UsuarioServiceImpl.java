package com.sistemaexamenes.service.impl;

import com.sistemaexamenes.dto.usuario.UsuarioRequestDTO;
import com.sistemaexamenes.dto.usuario.UsuarioResponseDTO;
import com.sistemaexamenes.entity.Usuario;
import com.sistemaexamenes.repository.UsuarioRepository;
import com.sistemaexamenes.service.SesionService;
import com.sistemaexamenes.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final SesionService sesionService;

    @Override
    public List<UsuarioResponseDTO> listar() {

        return usuarioRepository.findAllByOrderByNombreAsc()
                .stream()
                .map(this::convertirDTO)
                .toList();

    }

    @Override
    public UsuarioResponseDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));
        return convertirDTO(usuario);
    }

    @Override
    public UsuarioResponseDTO crear(UsuarioRequestDTO dto) {

        usuarioRepository.findByCorreo(dto.getCorreo())
                .ifPresent(u -> {
                    throw new RuntimeException("Ya existe un usuario con ese correo.");
                });

        Usuario usuario = new Usuario();

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol(dto.getRol());
        usuario.setActivo(true);

        usuario = usuarioRepository.save(usuario);

        return convertirDTO(usuario);

    }

    @Override
    public UsuarioResponseDTO actualizar(Long id, UsuarioRequestDTO dto) {

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        Usuario existente = usuarioRepository.findByCorreo(dto.getCorreo()).orElse(null);
        if (existente != null && !existente.getId().equals(id)) {
            throw new RuntimeException("Ya existe un usuario con ese correo.");
        }

        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(dto.getRol());

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        usuario = usuarioRepository.save(usuario);

        return convertirDTO(usuario);

    }

    @Override
    public void cambiarEstado(Long id) {

        Long usuarioActualId = sesionService.obtenerIdUsuario();

        if (usuarioActualId.equals(id)) {
            throw new RuntimeException("No puede desactivar su propia cuenta.");
        }

        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        usuario.setActivo(!usuario.getActivo());

        usuarioRepository.save(usuario);

    }

    private UsuarioResponseDTO convertirDTO(Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getActivo()
        );

    }
}
