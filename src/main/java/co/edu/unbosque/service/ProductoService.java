package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Cacheable(value = "productos")
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Cacheable(value = "producto", key = "#id")
    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    @Cacheable(value = "productosPorNombre", key = "#nombre")
    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Cacheable(value = "productosPorCategoria", key = "#categoriaId")
    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    @Cacheable(value = "productosPorRangoPrecio", key = "#min + '-' + #max")
    public List<Producto> buscarPorRangoPrecio(Double min, Double max) {
        return productoRepository.findByPrecioBetween(min, max);
    }

    @CacheEvict(value = {
        "productos", "producto", "productosPorNombre", "productosPorCategoria", "productosPorRangoPrecio"
    }, allEntries = true)
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @CacheEvict(value = {
        "productos", "producto", "productosPorNombre", "productosPorCategoria", "productosPorRangoPrecio"
    }, allEntries = true)
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
