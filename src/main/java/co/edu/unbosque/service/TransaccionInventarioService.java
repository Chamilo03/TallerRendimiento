package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.model.dto.TransaccionInventarioDTO;
import co.edu.unbosque.repository.TransaccionInventarioRepository;

@Service
public class TransaccionInventarioService {

    private final TransaccionInventarioRepository transaccionInventarioRepository;
    private final ModelMapper modelMapper;

    public TransaccionInventarioService(TransaccionInventarioRepository transaccionInventarioRepository, ModelMapper modelMapper) {
        this.transaccionInventarioRepository = transaccionInventarioRepository;
        this.modelMapper = modelMapper;
    }

    // 🔹 Listar todas las transacciones (con caché)
    @Cacheable("transacciones")
    public List<TransaccionInventarioDTO> listarTransacciones() {
        return transaccionInventarioRepository.findAll().stream()
                .map(t -> modelMapper.map(t, TransaccionInventarioDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Buscar una transacción por ID
    @Cacheable(value = "transaccion", key = "#id")
    public Optional<TransaccionInventarioDTO> obtenerTransaccion(Long id) {
        return transaccionInventarioRepository.findById(id)
                .map(t -> modelMapper.map(t, TransaccionInventarioDTO.class));
    }

    // 🔹 Guardar o actualizar (limpia cache globalmente)
    @CacheEvict(value = { 
        "transacciones", "transaccion", "transaccionesPorProducto", "transaccionesPorTipo" 
    }, allEntries = true)
    public TransaccionInventarioDTO guardarTransaccion(TransaccionInventarioDTO dto) {
        TransaccionInventario entity = modelMapper.map(dto, TransaccionInventario.class);
        TransaccionInventario saved = transaccionInventarioRepository.save(entity);
        return modelMapper.map(saved, TransaccionInventarioDTO.class);
    }

    // 🔹 Eliminar una transacción (limpia cache)
    @CacheEvict(value = { 
        "transacciones", "transaccion", "transaccionesPorProducto", "transaccionesPorTipo" 
    }, allEntries = true)
    public void eliminarTransaccion(Long id) {
        transaccionInventarioRepository.deleteById(id);
    }

    // 🔹 Buscar por producto (cacheado)
    @Cacheable(value = "transaccionesPorProducto", key = "#productoId")
    public List<TransaccionInventarioDTO> buscarPorProducto(Long productoId) {
        return transaccionInventarioRepository.findByProductoId(productoId).stream()
                .map(t -> modelMapper.map(t, TransaccionInventarioDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Buscar por tipo (cacheado)
    @Cacheable(value = "transaccionesPorTipo", key = "#tipo")
    public List<TransaccionInventarioDTO> buscarPorTipo(String tipo) {
        return transaccionInventarioRepository.findByTipo(tipo).stream()
                .map(t -> modelMapper.map(t, TransaccionInventarioDTO.class))
                .collect(Collectors.toList());
    }
}
