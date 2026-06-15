package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "procesos_admision")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProcesoAdmision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fecha_inicio;

    @Column(name = "fecha_fin")
    private LocalDate fecha_fin;

    private String estado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fecha_creacion;
}
