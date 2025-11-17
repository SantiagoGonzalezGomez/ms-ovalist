package com.ova.microservice.controller;

import com.ova.microservice.model.Ova;
import com.ova.microservice.dto.OvaRequest;
import com.ova.microservice.service.OvaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ovas")
@CrossOrigin(origins = "*")
public class OvaController {

    @Autowired
    private OvaService ovaService;

    // GET - Obtener todos los OVAs con paginación y filtros
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllOvas(
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "10") int tamaño,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String search) {

        Map<String, Object> respuesta = ovaService.getAllOvasPaginados(pagina, tamaño, categoria, search);
        return ResponseEntity.ok(respuesta);
    }

    // GET - Obtener OVA por ID
    @GetMapping("/{id}")
    public ResponseEntity<Ova> getOvaById(@PathVariable Long id) {
        Ova ova = ovaService.getOvaById(id);
        return ResponseEntity.ok(ova);
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
        Ova updatedOva = ovaService.updateOva(id, ovaRequest);
        return ResponseEntity.ok(updatedOva);
    }

    // DELETE - Eliminar OVA
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOva(@PathVariable Long id) {
        ovaService.deleteOva(id);
        return ResponseEntity.ok().body("OVA eliminado correctamente");
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