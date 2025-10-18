package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.entity.Producto;
import co.edu.unbosque.model.dto.ProductoDTO;
import co.edu.unbosque.repository.CategoriaRepository;
import co.edu.unbosque.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ModelMapper modelMapper;

    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository,
            ModelMapper modelMapper
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.modelMapper = modelMapper;
    }

    // 🔹 Listar todos los productos (devuelve DTOs)
    @Cacheable(value = "productos")
    public List<ProductoDTO> listarProductos() {
        return productoRepository.findAll()
                .stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Obtener producto por ID (devuelve DTO)
    @Cacheable(value = "producto", key = "#id")
    public Optional<ProductoDTO> obtenerProducto(Long id) {
        return productoRepository.findById(id)
                .map(p -> modelMapper.map(p, ProductoDTO.class));
    }

    // 🔹 Buscar productos por nombre (devuelve DTOs)
    @Cacheable(value = "productosPorNombre", key = "#nombre")
    public List<ProductoDTO> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Buscar productos por categoría (devuelve DTOs)
    @Cacheable(value = "productosPorCategoria", key = "#categoriaId")
    public List<ProductoDTO> buscarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoriaId(categoriaId)
                .stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Buscar productos por rango de precio (devuelve DTOs)
    @Cacheable(value = "productosPorRangoPrecio", key = "#min + '-' + #max")
    public List<ProductoDTO> buscarPorRangoPrecio(Double min, Double max) {
        return productoRepository.findByPrecioBetween(min, max)
                .stream()
                .map(p -> modelMapper.map(p, ProductoDTO.class))
                .collect(Collectors.toList());
    }

    // 🔹 Guardar o actualizar producto (limpia caché y devuelve DTO)
    @CacheEvict(value = {
            "productos", "producto", "productosPorNombre",
            "productosPorCategoria", "productosPorRangoPrecio"
    }, allEntries = true)
    public ProductoDTO guardarProducto(ProductoDTO productoDTO, Long categoriaId) {
        Producto producto = modelMapper.map(productoDTO, Producto.class);

        if (categoriaId != null) {
            Categoria categoria = categoriaRepository.findById(categoriaId).orElse(null);
            producto.setCategoria(categoria);
        }

        Producto guardado = productoRepository.save(producto);
        return modelMapper.map(guardado, ProductoDTO.class);
    }

    // 🔹 Eliminar producto y limpiar caché
    @CacheEvict(value = {
            "productos", "producto", "productosPorNombre",
            "productosPorCategoria", "productosPorRangoPrecio"
    }, allEntries = true)
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    @Cacheable(value = "producto", key = "#id")
    public Object obtenerProductoCache(Long id) {
        return obtenerProducto(id); // tu método que devuelve ProductoDTO
    }


    // 🔹 Buscar productos con filtros dinámicos
    public List<ProductoDTO> buscarProductos(String query, String category, Double minPrice, Double maxPrice) {
        return productoRepository.findAll().stream()
            .filter(p -> query == null || p.getNombre().toLowerCase().contains(query.toLowerCase()))
            .filter(p -> category == null ||
                    (p.getCategoria() != null &&
                     p.getCategoria().getNombre().equalsIgnoreCase(category)))
            .filter(p -> minPrice == null || p.getPrecio() >= minPrice)
            .filter(p -> maxPrice == null || p.getPrecio() <= maxPrice)
            .map(p -> modelMapper.map(p, ProductoDTO.class))
            .collect(Collectors.toList());
}

}
