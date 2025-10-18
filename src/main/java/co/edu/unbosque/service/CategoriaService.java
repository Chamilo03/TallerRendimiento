package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.model.dto.CategoriaDTO;
import co.edu.unbosque.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // ✅ Cachea la lista completa pero ya en formato DTO
    @Cacheable(value = "categorias")
    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ✅ Cachea una categoría específica por su ID en formato DTO
    @Cacheable(value = "categoria", key = "#id")
    public Optional<CategoriaDTO> obtenerCategoria(Long id) {
        return categoriaRepository.findById(id)
                .map(this::convertirADTO);
    }

    // ✅ Cachea las búsquedas por nombre también como DTO
    @Cacheable(value = "categoriasPorNombre", key = "#nombre")
    public List<CategoriaDTO> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ⚠️ Guardar o eliminar deben invalidar el caché (no se cachean)
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    // ============================================================
    // 🔁 Conversión ENTIDAD ↔ DTO
    // ============================================================
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto;
    }
}
