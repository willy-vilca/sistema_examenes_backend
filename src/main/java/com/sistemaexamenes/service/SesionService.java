package com.sistemaexamenes.service;

import com.sistemaexamenes.entity.Usuario;

public interface SesionService {

    Usuario obtenerUsuarioActual();

    Long obtenerIdUsuario();

    boolean esAdministrador();

}
