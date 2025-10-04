package co.edu.unbosque.model.dto;

import lombok.Data;

@Data
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Long categoriaPadreId; // solo el id, no el objeto completo
}