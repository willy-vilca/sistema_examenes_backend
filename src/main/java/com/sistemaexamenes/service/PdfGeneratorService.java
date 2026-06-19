package com.sistemaexamenes.service;

import com.sistemaexamenes.entity.TemaExamen;

public interface PdfGeneratorService {

    String generarPdfTema(TemaExamen tema);
}
