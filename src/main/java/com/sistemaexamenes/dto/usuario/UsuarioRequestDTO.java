package com.sistemaexamenes.dto.usuario;

import com.sistemaexamenes.entity.enums.Rol;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    private String nombre;
    private String correo;
    private String password;
    private Rol rol;

}
