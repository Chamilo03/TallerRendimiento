package co.edu.unbosque.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.service.InventarioService;
import co.edu.unbosque.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final ProductoService productoService;
    // private final InventarioService inventarioService;

    public ProductoRestController(ProductoService productoService, InventarioService inventarioService) {
        this.productoService = productoService;
        // this.inventarioService = inventarioService;
    }

    // Listar todos los productos
    @GetMapping
    public List<Producto> listarProductos() {
        return productoService.listarProductos();
    }

    // Obtener producto por ID
    @GetMapping("/{id}")
    public Producto obtenerProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id).orElse(null);
    }

    // Crear o actualizar producto
    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto) {
        return productoService.guardarProducto(producto);
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
    }

    // Buscar por nombre
    @GetMapping("/buscar/nombre")
    public List<Producto> buscarPorNombre(@RequestParam String nombre) {
        return productoService.buscarPorNombre(nombre);
    }

    // Buscar por categoría
    @GetMapping("/buscar/categoria/{categoriaId}")
    public List<Producto> buscarPorCategoria(@PathVariable Long categoriaId) {
        return productoService.buscarPorCategoria(categoriaId);
    }

    // Buscar por rango de precio
    @GetMapping("/buscar/precio")
    public List<Producto> buscarPorRangoPrecio(@RequestParam Double min, @RequestParam Double max) {
        return productoService.buscarPorRangoPrecio(min, max);
    }

    @GetMapping("/search")
    public List<Producto> buscarProductos(
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
            .toList();
    }

    /**@GetMapping("/{id}/details")
    public Map<String, Object> obtenerDetallesProducto(@PathVariable Long id) {
    Map<String, Object> response = new HashMap<>();

    Producto producto = productoService.obtenerProducto(id).orElse(null);
    if (producto == null) {
        response.put("error", "Producto no encontrado");
        return response;
    }

    response.put("producto", producto);

    inventarioService.obtenerPorProducto(id).ifPresent(
            inv -> response.put("inventario", inv.getCantidad())
    );

    return response;
    }**/
    @GetMapping("/{id}/details")
    public Producto obtenerDetallesProducto(@PathVariable Long id) {
        return productoService.obtenerProducto(id).orElse(null);
    }

}
