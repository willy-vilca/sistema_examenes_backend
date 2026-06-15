package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alternativas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Alternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "pregunta_id",
            nullable = false
    )
    private Pregunta pregunta;

    @Column(
            name = "contenido_html",
            columnDefinition = "TEXT"
    )
    private String contenidoHtml;

    @Column(name = "es_correcta")
    private Boolean esCorrecta;
}