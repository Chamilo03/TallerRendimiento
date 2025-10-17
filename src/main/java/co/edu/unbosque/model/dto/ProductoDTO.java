package co.edu.unbosque.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long categoriaId;
    private LocalDateTime creadoEn;
    private LocalDateTime actualizadoEn;
    private String comentario;
    private Double calificacion;
}
