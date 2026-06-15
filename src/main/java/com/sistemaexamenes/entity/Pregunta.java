package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "preguntas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "proceso_id",
            nullable = false
    )
    private ProcesoAdmision proceso;

    @ManyToOne
    @JoinColumn(
            name = "categoria_id",
            nullable = false
    )
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(
            name = "usuario_id",
            nullable = false
    )
    private Usuario usuario;

    @Column(
            name = "contenido_html",
            columnDefinition = "TEXT"
    )
    private String contenidoHtml;

    private Boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @OneToMany(
            mappedBy = "pregunta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Alternativa> alternativas =
            new ArrayList<>();
}