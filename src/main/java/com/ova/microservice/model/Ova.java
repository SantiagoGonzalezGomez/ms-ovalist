package com.ova.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ova {
    private Long id;
    private String titulo;
    private String descripcion;
    private String autor;
    private String url;
    private String categoria;
    private String nivel;
    private Integer duracion;
    private Double calificacion;
    private LocalDateTime fechaCreacion;
    private Boolean activo;
}