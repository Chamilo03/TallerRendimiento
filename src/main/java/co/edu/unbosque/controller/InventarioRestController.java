package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
public class InventarioRestController {

    private final InventarioService inventarioService;

    public InventarioRestController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // Listar todo el inventario
    @GetMapping
    public List<Inventario> listarInventario() {
        return inventarioService.listarInventario();
    }

    // Obtener inventario por ID
    @GetMapping("/{id}")
    public Inventario obtenerInventario(@PathVariable Long id) {
        return inventarioService.obtenerInventario(id).orElse(null);
    }

    // Obtener inventario por producto
    @GetMapping("/producto/{productoId}")
    public Inventario obtenerPorProducto(@PathVariable Long productoId) {
        return inventarioService.obtenerPorProducto(productoId).orElse(null);
    }

    // Crear o actualizar inventario
    @PostMapping
    public Inventario guardarInventario(@RequestBody Inventario inventario) {
        return inventarioService.guardarInventario(inventario);
    }

    // Eliminar inventario
    @DeleteMapping("/{id}")
    public void eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
    }

    // Actualizar stock de un producto
    @PutMapping("/producto/{productoId}/stock")
    public Inventario actualizarStock(@PathVariable Long productoId, @RequestParam int cantidad) {
        return inventarioService.actualizarStock(productoId, cantidad);
    }
}
