package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.repository.CategoriaRepository;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> obtenerCategoria(Long id) {
        return categoriaRepository.findById(id);
    }

    public Categoria guardarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public void eliminarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }
}
