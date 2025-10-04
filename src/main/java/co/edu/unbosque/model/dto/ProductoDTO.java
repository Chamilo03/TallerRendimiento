package co.edu.unbosque.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long categoriaId; // referencia por id
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
}
