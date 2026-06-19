package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "examenes_generados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamenGenerado {

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
            name = "usuario_id",
            nullable = false
    )
    private Usuario usuario;

    private String nombre;

    @Column(name = "cantidad_temas")
    private Integer cantidadTemas;

    @Column(name = "fecha_generacion")
    private LocalDateTime fechaGeneracion;

    @OneToMany(
            mappedBy = "examenGenerado",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TemaExamen> temas =
            new ArrayList<>();

    @OneToMany(
            mappedBy = "examenGenerado",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ExamenCategoria> categorias =
            new ArrayList<>();
}
