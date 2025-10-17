package co.edu.unbosque.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.entity.Categoria;
import co.edu.unbosque.model.dto.CategoriaDTO;
import co.edu.unbosque.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaRestController {

    private final CategoriaService categoriaService;

    public CategoriaRestController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // 🔹 Listar todas
    @GetMapping
    public List<CategoriaDTO> listarCategorias() {
        return categoriaService.listarCategorias()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // 🔹 Obtener por ID
    @GetMapping("/{id}")
    public CategoriaDTO obtenerCategoria(@PathVariable Long id) {
        return categoriaService.obtenerCategoria(id)
                .map(this::convertirADTO)
                .orElse(null);
    }

    // 🔹 Crear categoría
    @PostMapping
    public CategoriaDTO crearCategoria(@RequestBody CategoriaDTO dto) {
        Categoria categoria = convertirAEntidad(dto);
        Categoria guardada = categoriaService.guardarCategoria(categoria);
        return convertirADTO(guardada);
    }

    // 🔹 Actualizar
    @PutMapping("/{id}")
    public CategoriaDTO actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO dto) {
        Categoria categoria = convertirAEntidad(dto);
        categoria.setId(id);
        Categoria actualizada = categoriaService.guardarCategoria(categoria);
        return convertirADTO(actualizada);
    }

    // 🔹 Eliminar
    @DeleteMapping("/{id}")
    public void eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
    }

    // ============================================================
    // 🔁 CONVERSIÓN DTO ↔ ENTIDAD
    // ============================================================
    private CategoriaDTO convertirADTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(categoria.getId());
        dto.setNombre(categoria.getNombre());
        dto.setDescripcion(categoria.getDescripcion());
        return dto; 
    }

    private Categoria convertirAEntidad(CategoriaDTO dto) {
        Categoria categoria = new Categoria();
        categoria.setId(dto.getId());
        categoria.setNombre(dto.getNombre());
        categoria.setDescripcion(dto.getDescripcion());
        return categoria;
    }
}
