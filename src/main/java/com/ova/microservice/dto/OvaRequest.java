package com.ova.microservice.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class OvaRequest {
    @NotBlank(message = "El título es obligatorio")
    @Size(min = 3, max = 100, message = "El título debe tener entre 3 y 100 caracteres")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, max = 500, message = "La descripción debe tener entre 10 y 500 caracteres")
    private String descripcion;

    @NotBlank(message = "El autor es obligatorio")
    private String autor;

    @NotBlank(message = "La URL es obligatoria")
    @Pattern(regexp = "^(https?|ftp)://.*$", message = "La URL debe ser válida")
    private String url;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;

    private String nivel = "Básico";

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración mínima es 1 minuto")
    @Max(value = 480, message = "La duración máxima es 480 minutos")
    private Integer duracion;

    @DecimalMin(value = "0.0", message = "La calificación mínima es 0")
    @DecimalMax(value = "5.0", message = "La calificación máxima es 5")
    private Double calificacion = 0.0;
}