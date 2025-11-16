package com.ova.microservice.service;

import com.ova.microservice.model.Ova;
import com.ova.microservice.dto.OvaRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OvaService {

    private final List<Ova> ovas = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    // Constructor con datos de ejemplo para probar
    public OvaService() {
        // Agregamos algunos OVAs de ejemplo
        agregarOvaEjemplo("Introducción a Java",
                "Curso básico de programación en Java",
                "Carlos Rodríguez",
                "https://ejemplo.com/java-basico",
                "Programación",
                "Básico",
                120,
                4.5);

        agregarOvaEjemplo("Matemáticas Avanzadas",
                "Álgebra lineal y cálculo diferencial",
                "María González",
                "https://ejemplo.com/matematicas",
                "Matemáticas",
                "Avanzado",
                180,
                4.8);
    }

    // Método helper para agregar OVAs de ejemplo
    private void agregarOvaEjemplo(String titulo, String descripcion, String autor,
                                   String url, String categoria, String nivel,
                                   Integer duracion, Double calificacion) {
        ovas.add(new Ova(counter.getAndIncrement(),
                titulo, descripcion, autor, url, categoria, nivel,
                duracion, calificacion, LocalDateTime.now().minusDays(counter.get()),
                true));
    }

    // Obtener todos los OVAs activos
    public List<Ova> getAllOvas() {
        return filtrarOvasActivos(ovas);
    }

    // Obtener OVA por ID
    public Optional<Ova> getOvaById(Long id) {
        return ovas.stream()
                .filter(ova -> ova.getId().equals(id) && ova.getActivo())
                .findFirst();
    }

    // Crear nuevo OVA
    public Ova createOva(OvaRequest ovaRequest) {
        Ova ova = new Ova();
        ova.setId(counter.getAndIncrement());
        ova.setTitulo(ovaRequest.getTitulo());
        ova.setDescripcion(ovaRequest.getDescripcion());
        ova.setAutor(ovaRequest.getAutor());
        ova.setUrl(ovaRequest.getUrl());
        ova.setCategoria(ovaRequest.getCategoria());
        ova.setNivel(ovaRequest.getNivel());
        ova.setDuracion(ovaRequest.getDuracion());
        ova.setCalificacion(ovaRequest.getCalificacion());
        ova.setFechaCreacion(LocalDateTime.now());
        ova.setActivo(true);

        ovas.add(ova);
        return ova;
    }

    // Actualizar OVA
    public Optional<Ova> updateOva(Long id, OvaRequest ovaRequest) {
        Optional<Ova> existingOva = getOvaById(id);
        if (existingOva.isPresent()) {
            Ova ova = existingOva.get();
            actualizarOvaDesdeRequest(ova, ovaRequest);
            return Optional.of(ova);
        }
        return Optional.empty();
    }

    // Método helper para actualizar OVA desde request
    private void actualizarOvaDesdeRequest(Ova ova, OvaRequest request) {
        ova.setTitulo(request.getTitulo());
        ova.setDescripcion(request.getDescripcion());
        ova.setAutor(request.getAutor());
        ova.setUrl(request.getUrl());
        ova.setCategoria(request.getCategoria());
        ova.setNivel(request.getNivel());
        ova.setDuracion(request.getDuracion());
        ova.setCalificacion(request.getCalificacion());
    }

    // Eliminar OVA (soft delete)
    public boolean deleteOva(Long id) {
        Optional<Ova> ova = getOvaById(id);
        if (ova.isPresent()) {
            ova.get().setActivo(false);
            return true;
        }
        return false;
    }

    // Buscar OVAs por categoría
    public List<Ova> getOvasByCategoria(String categoria) {
        return filtrarOvasActivos(
                ovas.stream()
                        .filter(ova -> ova.getCategoria().equalsIgnoreCase(categoria))
                        .collect(Collectors.toList())
        );
    }

    // Buscar OVAs por título (búsqueda parcial)
    public List<Ova> searchOvasByTitulo(String titulo) {
        return filtrarOvasActivos(
                ovas.stream()
                        .filter(ova -> ova.getTitulo().toLowerCase().contains(titulo.toLowerCase()))
                        .collect(Collectors.toList())
        );
    }

    // Método helper para filtrar OVAs activos
    private List<Ova> filtrarOvasActivos(List<Ova> listaOvas) {
        return listaOvas.stream()
                .filter(Ova::getActivo)
                .collect(Collectors.toList());
    }
}