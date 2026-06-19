package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tema_preguntas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemaPregunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "tema_id",
            nullable = false
    )
    private TemaExamen tema;

    @ManyToOne
    @JoinColumn(
            name = "pregunta_id",
            nullable = false
    )
    private Pregunta pregunta;

    @Column(name = "orden_pregunta")
    private Integer ordenPregunta;

    @OneToMany(
            mappedBy = "temaPregunta",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TemaAlternativa> alternativas =
            new ArrayList<>();
}