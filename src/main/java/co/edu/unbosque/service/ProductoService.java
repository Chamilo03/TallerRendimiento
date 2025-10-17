package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

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

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> obtenerProducto(Long id) {
        return productoRepository.findById(id);
    }

    public Producto guardarProducto(Producto producto, Long categoriaId) {
        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
            producto.setCategoria(categoria);
        }
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public List<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Producto> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId);
    }

    public List<Producto> buscarPorRangoPrecio(Double min, Double max) {
        return productoRepository.findByPrecioBetween(min, max);
    }
}
