package com.sistemaexamenes.dto.auth;

import com.sistemaexamenes.entity.enums.Rol;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {

    private Long id;
    private String nombre;
    private String correo;
    private Rol rol;

}
