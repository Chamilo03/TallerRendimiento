package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.repository.TransaccionInventarioRepository;

@Service
public class TransaccionInventarioService {

    private final TransaccionInventarioRepository transaccionInventarioRepository;

    public TransaccionInventarioService(TransaccionInventarioRepository transaccionInventarioRepository) {
        this.transaccionInventarioRepository = transaccionInventarioRepository;
    }

    @Cacheable("transacciones")
    public List<TransaccionInventario> listarTransacciones() {
        return transaccionInventarioRepository.findAll();
    }

    @Cacheable(value = "transaccion", key = "#id")
    public Optional<TransaccionInventario> obtenerTransaccion(Long id) {
        return transaccionInventarioRepository.findById(id);
    }

    @CacheEvict(value = { "transacciones", "transaccion", "transaccionesPorProducto", "transaccionesPorTipo" }, allEntries = true)
    public TransaccionInventario guardarTransaccion(TransaccionInventario transaccion) {
        return transaccionInventarioRepository.save(transaccion);
    }

    @CacheEvict(value = { "transacciones", "transaccion", "transaccionesPorProducto", "transaccionesPorTipo" }, allEntries = true)
    public void eliminarTransaccion(Long id) {
        transaccionInventarioRepository.deleteById(id);
    }

    @Cacheable(value = "transaccionesPorProducto", key = "#productoId")
    public List<TransaccionInventario> buscarPorProducto(Long productoId) {
        return transaccionInventarioRepository.findByProductoId(productoId);
    }

    @Cacheable(value = "transaccionesPorTipo", key = "#tipo")
    public List<TransaccionInventario> buscarPorTipo(String tipo) {
        return transaccionInventarioRepository.findByTipo(tipo);
    }
}
