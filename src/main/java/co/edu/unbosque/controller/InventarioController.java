package co.edu.unbosque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unbosque.model.dto.InventarioDTO;
import co.edu.unbosque.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    @Autowired
    private ObjectMapper objectMapper;

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // 🔹 Listar inventario (usa DTOs directamente)
    @GetMapping
    public List<InventarioDTO> listarInventario() {
        Object cached = inventarioService.listarInventario(); // devuelve Object
        return ((List<Object>) cached).stream()
                .map(o -> objectMapper.convertValue(o, InventarioDTO.class))
                .collect(Collectors.toList());
    }
    // 🔹 Obtener por ID
    @GetMapping("/{id}")
    public InventarioDTO obtenerPorId(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id).orElse(null);
    }

    @GetMapping("/product/{id}")
    public InventarioDTO obtenerPorProducto(@PathVariable Long id) {
        Object cached = inventarioService.obtenerPorProducto(id); // devuelve Object
        if (cached == null) return null;
        return objectMapper.convertValue(cached, InventarioDTO.class);
    }

    // 🔹 Crear inventario
    @PostMapping
    public InventarioDTO crearInventario(@RequestBody InventarioDTO dto) {
        return inventarioService.guardarInventario(dto, dto.getProductoId());
    }

    // 🔹 Actualizar inventario
    @PutMapping("/{id}")
    public InventarioDTO actualizarInventario(@PathVariable Long id, @RequestBody InventarioDTO dto) {
        dto.setId(id);
        return inventarioService.guardarInventario(dto, dto.getProductoId());
    }

    // 🔹 Eliminar inventario
    @DeleteMapping("/{id}")
    public void eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
    }

    @GetMapping("/low-stock")
    public ResponseEntity<List<InventarioDTO>> obtenerLowStock() {
    Object cached = inventarioService.obtenerLowStockCache();

    List<InventarioDTO> lowStock = ((List<Object>) cached).stream()
        .map(o -> objectMapper.convertValue(o, InventarioDTO.class))
        .collect(Collectors.toList());

    return ResponseEntity.ok(lowStock);
}
}
