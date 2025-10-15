package co.edu.unbosque.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.service.InventarioService;

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

    @GetMapping("/low-stock")
    public List<Map<String, Object>> obtenerLowStock(@RequestParam(defaultValue = "5") int limite) {
    return inventarioService.listarInventario().stream()
            .filter(i -> i.getCantidad() <= limite)
            .map(i -> {
                Map<String, Object> data = new HashMap<>();
                data.put("productoId", i.getProducto().getId());
                data.put("nombre", i.getProducto().getNombre());
                data.put("cantidad", i.getCantidad());
                return data;
            })
            .toList();
    }


}
