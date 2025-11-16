package com.ova.microservice.controller;

import com.ova.microservice.model.Ova;
import com.ova.microservice.dto.OvaRequest;
import com.ova.microservice.service.OvaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ovas")
@CrossOrigin(origins = "*") // Para permitir requests desde Postman
public class OvaController {

    @Autowired
    private OvaService ovaService;

    // GET - Obtener todos los OVAs
    @GetMapping
    public ResponseEntity<List<Ova>> getAllOvas() {
        List<Ova> ovas = ovaService.getAllOvas();
        return ResponseEntity.ok(ovas);
    }

    // GET - Obtener OVA por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ova> getOvaById(@PathVariable Long id) {
        Optional<Ova> ova = ovaService.getOvaById(id);
        return ova.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST - Crear nuevo OVA
    @PostMapping
    public ResponseEntity<Ova> createOva(@Valid @RequestBody OvaRequest ovaRequest) {
        Ova newOva = ovaService.createOva(ovaRequest);
        return ResponseEntity.ok(newOva);
    }

    // PUT - Actualizar OVA
    @PutMapping("/{id}")
    public ResponseEntity<Ova> updateOva(@PathVariable Long id,
                                         @Valid @RequestBody OvaRequest ovaRequest) {
        Optional<Ova> updatedOva = ovaService.updateOva(id, ovaRequest);
        return updatedOva.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE - Eliminar OVA
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOva(@PathVariable Long id) {
        boolean deleted = ovaService.deleteOva(id);
        if (deleted) {
            return ResponseEntity.ok().body("OVA eliminado correctamente");
        }
        return ResponseEntity.notFound().build();
    }

    // GET - Buscar OVAs por categoría
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Ova>> getOvasByCategoria(@PathVariable String categoria) {
        List<Ova> ovas = ovaService.getOvasByCategoria(categoria);
        return ResponseEntity.ok(ovas);
    }

    // GET - Buscar OVAs por título
    @GetMapping("/buscar")
    public ResponseEntity<List<Ova>> searchOvasByTitulo(@RequestParam String titulo) {
        List<Ova> ovas = ovaService.searchOvasByTitulo(titulo);
        return ResponseEntity.ok(ovas);
    }

    // Health check
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("OVA Microservice is running!");
    }
}