package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // ✅ Cachea la lista completa de categorías
    @Cacheable(value = "categorias")
    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    // ✅ Cachea una categoría específica por su ID
    @Cacheable(value = "categoria", key = "#id")
    public Optional<Categoria> obtenerCategoria(Long id) {
        return categoriaRepository.findById(id);
    }

    // ✅ Cachea las búsquedas por nombre
    @Cacheable(value = "categoriasPorNombre", key = "#nombre")
    public List<Categoria> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // ⚠️ Importante: Guardar o eliminar deben invalidar el caché (no se cachean)
    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
