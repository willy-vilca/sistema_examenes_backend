package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tema_alternativas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TemaAlternativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(
            name = "tema_pregunta_id",
            nullable = false
    )
    private TemaPregunta temaPregunta;

    @ManyToOne
    @JoinColumn(
            name = "alternativa_id",
            nullable = false
    )
    private Alternativa alternativa;

    @Column(name = "orden_alternativa")
    private Integer ordenAlternativa;
}