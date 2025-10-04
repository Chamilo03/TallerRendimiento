package co.edu.unbosque.service;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.entity.TipoTransaccion;
import co.edu.unbosque.repository.TransaccionInventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransaccionInventarioService {

    private final TransaccionInventarioRepository transaccionInventarioRepository;

    public TransaccionInventarioService(TransaccionInventarioRepository transaccionInventarioRepository) {
        this.transaccionInventarioRepository = transaccionInventarioRepository;
    }

    public List<TransaccionInventario> listarTransacciones() {
        return transaccionInventarioRepository.findAll();
    }

    public Optional<TransaccionInventario> obtenerTransaccion(Long id) {
        return transaccionInventarioRepository.findById(id);
    }

    public TransaccionInventario guardarTransaccion(TransaccionInventario transaccion) {
        return transaccionInventarioRepository.save(transaccion);
    }

    public void eliminarTransaccion(Long id) {
        transaccionInventarioRepository.deleteById(id);
    }

    public List<TransaccionInventario> buscarPorProducto(Long productoId) {
        return transaccionInventarioRepository.findByProductoId(productoId);
    }

    public List<TransaccionInventario> buscarPorTipo(TipoTransaccion tipo) {
        return transaccionInventarioRepository.findByTipo(tipo);
    }
}
