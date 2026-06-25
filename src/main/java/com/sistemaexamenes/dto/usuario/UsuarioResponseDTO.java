package com.sistemaexamenes.dto.usuario;

import com.sistemaexamenes.entity.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private Rol rol;
    private Boolean activo;

}
