package co.edu.unbosque.controller;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Listar todas las categorías
    @GetMapping
    public List<Categoria> listarCategorias() {
        return categoriaService.listarCategorias();
    }

    // Obtener una categoría por ID
    @GetMapping("/{id}")
    public Categoria obtenerCategoria(@PathVariable Long id) {
        return categoriaService.obtenerCategoria(id).orElse(null);
    }

    // Crear o actualizar categoría
    @PostMapping
    public Categoria crearCategoria(@RequestBody Categoria categoria) {
        return categoriaService.guardarCategoria(categoria);
    }

    // Eliminar categoría
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
    }

    // Buscar por nombre
    @GetMapping("/buscar")
    public List<Categoria> buscarPorNombre(@RequestParam String nombre) {
        return categoriaService.buscarPorNombre(nombre);
    }
}
