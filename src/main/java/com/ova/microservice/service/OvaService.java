package com.ova.microservice.service;

import com.ova.microservice.model.Ova;
import com.ova.microservice.dto.OvaRequest;
import com.ova.microservice.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class OvaService {

    private final List<Ova> ovas = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);

    public OvaService() {
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

    private void agregarOvaEjemplo(String titulo, String descripcion, String autor,
                                   String url, String categoria, String nivel,
                                   Integer duracion, Double calificacion) {
        ovas.add(new Ova(counter.getAndIncrement(),
                titulo, descripcion, autor, url, categoria, nivel,
                duracion, calificacion, LocalDateTime.now().minusDays(counter.get()),
                true));
    }

    // Obtener todos los OVAs con paginación y filtros
    public Map<String, Object> getAllOvasPaginados(int pagina, int tamaño, String categoria, String search) {

        List<Ova> ovasFiltradas = ovas.stream()
                .filter(Ova::getActivo)
                .filter(ova -> categoria == null || ova.getCategoria().equalsIgnoreCase(categoria))
                .filter(ova -> search == null ||
                        ova.getTitulo().toLowerCase().contains(search.toLowerCase()) ||
                        ova.getDescripcion().toLowerCase().contains(search.toLowerCase()) ||
                        ova.getAutor().toLowerCase().contains(search.toLowerCase()))
                .collect(Collectors.toList());

        int inicio = pagina * tamaño;
        int fin = Math.min(inicio + tamaño, ovasFiltradas.size());

        if (inicio > ovasFiltradas.size()) {
            inicio = 0;
            fin = Math.min(tamaño, ovasFiltradas.size());
        }

        List<Ova> ovasPagina = ovasFiltradas.subList(inicio, fin);

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("ovas", ovasPagina);
        respuesta.put("paginaActual", pagina);
        respuesta.put("totalPaginas", (int) Math.ceil((double) ovasFiltradas.size() / tamaño));
        respuesta.put("totalOvas", ovasFiltradas.size());
        respuesta.put("tamañoPagina", tamaño);

        return respuesta;
    }

    // Obtener OVA por ID - ahora lanza excepción
    public Ova getOvaById(Long id) {
        return ovas.stream()
                .filter(ova -> ova.getId().equals(id) && ova.getActivo())
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("OVA no encontrado con id: " + id));
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

    // Actualizar OVA - ahora lanza excepción
    public Ova updateOva(Long id, OvaRequest ovaRequest) {
        Ova existingOva = getOvaById(id);
        actualizarOvaDesdeRequest(existingOva, ovaRequest);
        return existingOva;
    }

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

    // Eliminar OVA - ahora lanza excepción
    public void deleteOva(Long id) {
        Ova ova = getOvaById(id);
        ova.setActivo(false);
    }

    // Buscar OVAs por categoría
    public List<Ova> getOvasByCategoria(String categoria) {
        return ovas.stream()
                .filter(ova -> ova.getCategoria().equalsIgnoreCase(categoria) && ova.getActivo())
                .collect(Collectors.toList());
    }

    // Buscar OVAs por título (búsqueda parcial)
    public List<Ova> searchOvasByTitulo(String titulo) {
        return ovas.stream()
                .filter(ova -> ova.getTitulo().toLowerCase().contains(titulo.toLowerCase()) && ova.getActivo())
                .collect(Collectors.toList());
    }
}