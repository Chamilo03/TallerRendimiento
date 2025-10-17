package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    // 🔹 Listar todos los registros de inventario (con caché)
    @Cacheable(value = "inventarioList")
    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    @Cacheable(value = "inventario", key = "#id")
    public Optional<Inventario> obtenerPorId(Long id) {
        return inventarioRepository.findById(id);
    }

    // 🔹 Obtener inventario por producto (con caché)
    @Cacheable(value = "inventarioPorProducto", key = "#productoId")
    public Optional<Inventario> obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    // 🔹 Guardar o actualizar inventario (limpia caché al modificar datos)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public Inventario guardarInventario(Inventario inventario, Long productoId) {
        if (productoId != null) {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            inventario.setProducto(producto);
        }
        return inventarioRepository.save(inventario);
    }

    // 🔹 Eliminar inventario (limpia caché)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

    // 🔹 Actualizar cantidad (stock) de un producto en el inventario (limpia caché)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public Inventario actualizarStock(Long productoId, int cantidad) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findByProductoId(productoId);
        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setCantidad(cantidad);
            return inventarioRepository.save(inventario);
        }
        return null;
    }
}
