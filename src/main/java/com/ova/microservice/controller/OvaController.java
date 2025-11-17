package com.ova.microservice.controller;

import com.ova.microservice.model.Ova;
import com.ova.microservice.dto.OvaRequest;
import com.ova.microservice.service.OvaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@Tag(name = "OVA Microservice", description = "API para gestión de Objetos Virtuales de Aprendizaje")
public class OvaController {

    @Autowired
    private OvaService ovaService;

    // ========== ENDPOINTS MÍNIMOS ==========

    @GetMapping("/health")
    @Operation(summary = "Health check del microservicio")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok().body(Map.of(
                "status", "UP",
                "service", "OVA Microservice",
                "version", "1.0.0",
                "message", "Servicio funcionando correctamente"
        ));
    }

    // ========== ENDPOINTS OVA ==========

    @GetMapping("/ovas")
    @Operation(summary = "Obtener todos los OVAs con paginación y filtros")
    public ResponseEntity<Map<String, Object>> getAllOvas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamaño,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String search) {

        Map<String, Object> respuesta = ovaService.getAllOvasPaginados(pagina, tamaño, categoria, search);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/ovas/{id}")
    @Operation(summary = "Obtener OVA por ID")
    public ResponseEntity<Ova> getOvaById(@PathVariable Long id) {
        Ova ova = ovaService.getOvaById(id);
        return ResponseEntity.ok(ova);
    }

    @PostMapping("/ovas")
    @Operation(summary = "Crear nuevo OVA")
    public ResponseEntity<Ova> createOva(@Valid @RequestBody OvaRequest ovaRequest) {
        Ova newOva = ovaService.createOva(ovaRequest);
        return ResponseEntity.ok(newOva);
    }

    @PutMapping("/ovas/{id}")
    @Operation(summary = "Actualizar OVA existente")
    public ResponseEntity<Ova> updateOva(@PathVariable Long id,
                                         @Valid @RequestBody OvaRequest ovaRequest) {
        Ova updatedOva = ovaService.updateOva(id, ovaRequest);
        return ResponseEntity.ok(updatedOva);
    }

    @DeleteMapping("/ovas/{id}")
    @Operation(summary = "Eliminar OVA (soft delete)")
    public ResponseEntity<?> deleteOva(@PathVariable Long id) {
        ovaService.deleteOva(id);
        return ResponseEntity.ok().body("OVA eliminado correctamente");
    }

    @GetMapping("/ovas/categoria/{categoria}")
    @Operation(summary = "Buscar OVAs por categoría")
    public ResponseEntity<List<Ova>> getOvasByCategoria(@PathVariable String categoria) {
        List<Ova> ovas = ovaService.getOvasByCategoria(categoria);
        return ResponseEntity.ok(ovas);
    }

    @GetMapping("/ovas/buscar")
    @Operation(summary = "Buscar OVAs por título")
    public ResponseEntity<List<Ova>> searchOvasByTitulo(@RequestParam String titulo) {
        List<Ova> ovas = ovaService.searchOvasByTitulo(titulo);
        return ResponseEntity.ok(ovas);
    }

    // Mantener el health check original para compatibilidad
    @GetMapping("/ovas/health")
    @Operation(summary = "Health check específico de OVAs")
    public ResponseEntity<String> ovaHealth() {
        return ResponseEntity.ok("OVA Microservice is running!");
    }
}