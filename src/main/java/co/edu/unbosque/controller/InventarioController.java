package co.edu.unbosque.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
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
import co.edu.unbosque.model.dto.InventarioDTO;
import co.edu.unbosque.service.InventarioService;

@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "*")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public List<InventarioDTO> listarInventario() {
        return inventarioService.listarInventario()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public InventarioDTO obtenerPorId(@PathVariable Long id) {
        return inventarioService.obtenerPorId(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @GetMapping("/product/{productoId}")
    public InventarioDTO obtenerPorProducto(@PathVariable Long productoId) {
        return inventarioService.obtenerPorProducto(productoId)
                .map(this::convertirADTO)
                .orElse(null);
    }

    @PostMapping
    public InventarioDTO crearInventario(@RequestBody InventarioDTO dto) {
        Inventario inventario = convertirAEntidad(dto);
        Inventario guardado = inventarioService.guardarInventario(inventario, dto.getProductoId());
        return convertirADTO(guardado);
    }

    @PutMapping("/{id}")
    public InventarioDTO actualizarInventario(@PathVariable Long id, @RequestBody InventarioDTO dto) {
        Inventario inventario = convertirAEntidad(dto);
        inventario.setId(id);
        Inventario actualizado = inventarioService.guardarInventario(inventario, dto.getProductoId());
        return convertirADTO(actualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarInventario(@PathVariable Long id) {
        inventarioService.eliminarInventario(id);
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
