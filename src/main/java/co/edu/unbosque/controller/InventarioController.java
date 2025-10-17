package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.model.dto.InventarioDTO;
import co.edu.unbosque.service.InventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // 🔹 Listar todo el inventario
    @GetMapping
    public List<InventarioDTO> listarInventario() {
        return inventarioService.listarInventario()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // 🔹 Obtener inventario por ID
    @GetMapping("/{id}")
    public InventarioDTO obtenerPorId(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    // 🔹 Obtener inventario por producto
    @GetMapping("/product/{productoId}")
    public InventarioDTO obtenerPorProducto(@PathVariable Long productoId) {
        return inventarioService.obtenerPorProducto(productoId)
                .map(this::convertirADTO)
                .orElse(null);
    }

    // 🔹 Crear inventario
    @PostMapping
    public InventarioDTO crearInventario(@RequestBody InventarioDTO dto) {
        Inventario inventario = convertirAEntidad(dto);
        Inventario guardado = inventarioService.guardarInventario(inventario, dto.getProductoId());
        return convertirADTO(guardado);
    }

    // 🔹 Actualizar inventario
    @PutMapping("/{id}")
    public InventarioDTO actualizarInventario(@PathVariable Long id, @RequestBody InventarioDTO dto) {
        Inventario inventario = convertirAEntidad(dto);
        inventario.setId(id);
        Inventario actualizado = inventarioService.guardarInventario(inventario, dto.getProductoId());
        return convertirADTO(actualizado);
    }

    // 🔹 Eliminar inventario
    @DeleteMapping("/{id}")
    public void eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
    }

    // 🔹 Productos con bajo stock
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

    // ============================================================
    // 🔁 CONVERSIÓN DTO ↔ ENTIDAD
    // ============================================================
    private InventarioDTO convertirADTO(Inventario inventario) {
        InventarioDTO dto = new InventarioDTO();
        dto.setId(inventario.getId());
        dto.setCantidad(inventario.getCantidad());
        dto.setProductoId(inventario.getProducto() != null ? inventario.getProducto().getId() : null);
        return dto;
    }

    private Inventario convertirAEntidad(InventarioDTO dto) {
        Inventario inventario = new Inventario();
        inventario.setId(dto.getId());
        inventario.setCantidad(dto.getCantidad());
        return inventario;
    }
}
