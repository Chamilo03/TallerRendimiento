package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Inventario;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.model.dto.InventarioDTO;
import co.edu.unbosque.repository.InventarioRepository;
import co.edu.unbosque.repository.ProductoRepository;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoRepository productoRepository;
    private final ModelMapper modelMapper;

    public InventarioService(
            InventarioRepository inventarioRepository,
            ProductoRepository productoRepository,
            ModelMapper modelMapper
    ) {
        this.inventarioRepository = inventarioRepository;
        this.productoRepository = productoRepository;
        this.modelMapper = modelMapper;
    }

    // 🔹 Listar todos los registros de inventario (devuelve DTOs con caché)
    @Cacheable(value = "inventarioList")
    public List<InventarioDTO> listarInventario() {
        return inventarioRepository.findAll()
                .stream()
                .map(inv -> modelMapper.map(inv, InventarioDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Obtener inventario por ID (devuelve DTO con caché)
    @Cacheable(value = "inventario", key = "#id")
    public Optional<InventarioDTO> obtenerPorId(Long id) {
        return inventarioRepository.findById(id)
                .map(inv -> modelMapper.map(inv, InventarioDTO.class));
    }

    // 🔹 Obtener inventario por producto (devuelve DTO con caché)
    @Cacheable(value = "inventarioPorProducto", key = "#productoId")
    public Optional<InventarioDTO> obtenerPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId)
                .map(inv -> modelMapper.map(inv, InventarioDTO.class));
    }

    // 🔹 Guardar o actualizar inventario (limpia caché)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public InventarioDTO guardarInventario(InventarioDTO inventarioDTO, Long productoId) {
        Inventario inventario = modelMapper.map(inventarioDTO, Inventario.class);

        if (productoId != null) {
            Producto producto = productoRepository.findById(productoId).orElse(null);
            inventario.setProducto(producto);
        }

        Inventario guardado = inventarioRepository.save(inventario);
        return modelMapper.map(guardado, InventarioDTO.class);
    }

    // 🔹 Eliminar inventario (limpia caché)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public void eliminarInventario(Long id) {
        inventarioRepository.deleteById(id);
    }

    // Método que obtiene inventario con bajo stock (por ejemplo, cantidad <= 5)
    public List<InventarioDTO> obtenerLowStock() {
        return inventarioRepository.findAll()
            .stream()
            .filter(inv -> inv.getCantidad() <= 5) // ajusta el criterio según tu lógica
            .map(inv -> modelMapper.map(inv, InventarioDTO.class))
            .collect(Collectors.toList());
    }

    @Cacheable(value = "lowStock")
    public Object obtenerLowStockCache() {
        return obtenerLowStock(); // ahora llama al método real
    }

    // 🔹 Actualizar stock (limpia caché y devuelve DTO)
    @CacheEvict(value = {"inventarioList", "inventario", "inventarioPorProducto"}, allEntries = true)
    public InventarioDTO actualizarStock(Long productoId, int cantidad) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findByProductoId(productoId);
        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            inventario.setCantidad(cantidad);
            Inventario actualizado = inventarioRepository.save(inventario);
            return modelMapper.map(actualizado, InventarioDTO.class);
        }
        return null;
    }
}
