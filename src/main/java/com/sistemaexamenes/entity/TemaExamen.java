package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "temas_examen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemaExamen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "examen_generado_id",
            nullable = false
    )
    private ExamenGenerado examenGenerado;

    @Column(name = "codigo_tema")
    private String codigoTema;

    @Column(name = "ruta_pdf")
    private String rutaPdf;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @OneToMany(
            mappedBy = "tema",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TemaPregunta> preguntas =
            new ArrayList<>();
}
