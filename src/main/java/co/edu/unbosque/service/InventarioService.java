package co.edu.unbosque.service;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioService(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    public List<Inventario> listarInventario() {
        return inventarioRepository.findAll();
    }

    public Optional<Inventario> obtenerInventario(Long id) {
        return inventarioRepository.findById(id);
    }

    public Optional<Inventario> obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId);
    }

    public Inventario guardarInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

    // Método para actualizar stock de un producto
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
