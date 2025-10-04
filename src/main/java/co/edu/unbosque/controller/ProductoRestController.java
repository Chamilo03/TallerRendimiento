package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoRestController {

    private final ProductoService productoService;

    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
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
}
