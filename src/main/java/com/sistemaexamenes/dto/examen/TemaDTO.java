package com.sistemaexamenes.dto.examen;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TemaDTO {

    private Long id;

    private String codigoTema;

    private String rutaPdf;
}
