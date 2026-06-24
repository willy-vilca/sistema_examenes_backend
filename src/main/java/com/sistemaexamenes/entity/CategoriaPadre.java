package com.sistemaexamenes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categorias_padre")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaPadre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
