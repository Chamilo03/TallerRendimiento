package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.repository.InventarioRepository;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Cacheable(value = "inventarioList")
    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    @Cacheable(value = "inventario", key = "#id")
    public Optional<Inventario> obtenerInventario(Long id) {
        return inventarioRepository.findById(id);
    }

    @Cacheable(value = "inventarioPorProducto", key = "#productoId")
    public Optional<Inventario> obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    // Métodos de escritura (no se cachean directamente)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

    // Método para actualizar stock
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
