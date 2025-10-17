package co.edu.unbosque.controller;

import java.util.List;
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

import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.model.dto.ProductoDTO;
import co.edu.unbosque.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoRestController {

    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🔹 Listar productos
    @GetMapping
    public List<ProductoDTO> listarProductos() {
        return productoService.listarProductos()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // 🔹 Buscar productos con filtros
    @GetMapping("/search")
    public List<ProductoDTO> buscarProductos(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice) {

        List<Producto> productos = productoService.listarProductos();

        return productos.stream()
                .filter(p -> query == null || p.getNombre().toLowerCase().contains(query.toLowerCase()))
                .filter(p -> category == null ||
                        (p.getCategoria() != null &&
                         p.getCategoria().getNombre().equalsIgnoreCase(category)))
                .filter(p -> minPrice == null || p.getPrecio() >= minPrice)
                .filter(p -> maxPrice == null || p.getPrecio() <= maxPrice)
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // 🔹 Detalles de producto
    @GetMapping("/{id}/details")
    public ProductoDTO obtenerDetallesProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    // 🔹 Crear producto
    @PostMapping
    public ProductoDTO crearProducto(@RequestBody ProductoDTO dto) {
        Producto producto = convertirAEntidad(dto);
        Producto guardado = productoService.guardarProducto(producto, dto.getCategoriaId());
        return convertirADTO(guardado);
    }

    // 🔹 Actualizar producto
    @PutMapping("/{id}")
    public ProductoDTO actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO dto) {
        Producto producto = convertirAEntidad(dto);
        producto.setId(id);
        Producto actualizado = productoService.guardarProducto(producto, dto.getCategoriaId());
        return convertirADTO(actualizado);
    }

    // 🔹 Eliminar producto
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // ============================================================
    // 🔁 CONVERSIÓN DTO ↔ ENTIDAD
    // ============================================================

    private ProductoDTO convertirADTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getId());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setPrecio(producto.getPrecio());
        dto.setCategoriaId(producto.getCategoria() != null ? producto.getCategoria().getId() : null);
        dto.setCreadoEn(producto.getCreadoEn());
        dto.setActualizadoEn(producto.getActualizadoEn());
        return dto;
    }

    private Producto convertirAEntidad(ProductoDTO dto) {
        Producto producto = new Producto();
        producto.setId(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        return producto;
    }
}
