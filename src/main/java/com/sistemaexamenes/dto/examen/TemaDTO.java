package com.sistemaexamenes.dto.examen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TemaDTO {

    private Long id;

    private String codigoTema;

    private String rutaPdf;

}
