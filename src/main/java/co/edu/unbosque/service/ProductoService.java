package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.repository.CategoriaRepository;
import co.edu.unbosque.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository, CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // 🔹 Listar todos los productos
    @Cacheable(value = "productos")
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // 🔹 Obtener producto por ID
    @Cacheable(value = "producto", key = "#id")
    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    // 🔹 Buscar productos por nombre
    @Cacheable(value = "productosPorNombre", key = "#nombre")
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // 🔹 Buscar productos por categoría
    @Cacheable(value = "productosPorCategoria", key = "#categoriaId")
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    // 🔹 Buscar productos por rango de precio
    @Cacheable(value = "productosPorRangoPrecio", key = "#min + '-' + #max")
    public List<Producto> buscarPorRangoPrecio(Double min, Double max) {
        return productoRepository.findByPrecioBetween(min, max);
    }

    // 🔹 Guardar o actualizar producto (vinculando categoría si existe)
    @CacheEvict(value = {
        "productos", "producto", "productosPorNombre", 
        "productosPorCategoria", "productosPorRangoPrecio"
    }, allEntries = true)
    public Producto guardarProducto(Producto producto, Long categoriaId) {
        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
            producto.setCategoria(categoria);
        }
        return productoRepository.save(producto);
    }

    // 🔹 Eliminar producto y limpiar caché
    @CacheEvict(value = {
        "productos", "producto", "productosPorNombre", 
        "productosPorCategoria", "productosPorRangoPrecio"
    }, allEntries = true)
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
