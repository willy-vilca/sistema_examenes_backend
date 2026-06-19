package com.sistemaexamenes.service;

public interface FormulaImageService {

    String generarImagenFormula(
            String latex
    );

    void eliminarFormulasTemporales();

}
