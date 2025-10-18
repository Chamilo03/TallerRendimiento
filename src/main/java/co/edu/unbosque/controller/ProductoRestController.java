package co.edu.unbosque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.unbosque.model.dto.ProductoDTO;
import co.edu.unbosque.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoRestController {

    @Autowired
    private ObjectMapper objectMapper;

    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🔹 Listar productos
    @GetMapping
    public List<ProductoDTO> listarProductos() {
        Object cached = productoService.listarProductos(); // ahora devuelve Object
        // Convertir LinkedHashMap a ProductoDTO
        return ((List<Object>) cached).stream()
                .map(o -> objectMapper.convertValue(o, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Buscar productos con filtros
    @GetMapping("/search")
    public List<ProductoDTO> buscarProductos(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        Object cached = productoService.buscarProductos(query, category, minPrice, maxPrice);
        return ((List<Object>) cached).stream()
                .map(o -> objectMapper.convertValue(o, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Detalles de producto
    @GetMapping("/{id}/details")
    public ProductoDTO obtenerDetallesProducto(@PathVariable Long id) {
        Object cached = productoService.obtenerProductoCache(id); // Object
        if (cached == null) return null;
        return objectMapper.convertValue(cached, ProductoDTO.class);
    }

    // 🔹 Crear producto
    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO dto) {
        return productoService.guardarProducto(dto, dto.getCategoriaId());
    }

    // 🔹 Actualizar producto
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        dto.setId(id);
        return productoService.guardarProducto(dto, dto.getCategoriaId());
    }

    // 🔹 Eliminar producto
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }
}
