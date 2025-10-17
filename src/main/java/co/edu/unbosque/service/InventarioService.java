package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.repository.InventarioRepository;
import co.edu.unbosque.repository.ProductoRepository;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;

    public InventarioService(InventarioRepository inventarioRepository, ProductoRepository productoRepository) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
    }

    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    public Optional<Inventario> obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Inventario guardarInventario(Inventario inventario, Long productoId) {
        if (productoId != null) {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            inventario.setProducto(producto);
        }
        return inventarioRepository.save(inventario);
    }

    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }
}
