package com.sistemaexamenes.dto.auth;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String correo;
    private String password;

}
