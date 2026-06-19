package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "examen_categorias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamenCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "examen_generado_id",
            nullable = false
    )
    private ExamenGenerado examenGenerado;

    @ManyToOne
    @JoinColumn(
            name = "categoria_id",
            nullable = false
    )
    private Categoria categoria;

    @Column(name = "cantidad_preguntas")
    private Integer cantidadPreguntas;
}