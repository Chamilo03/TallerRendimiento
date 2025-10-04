package co.edu.unbosque.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InventarioDTO {
    private Long id;
    private Long productoId; // solo el id del producto
    private Integer cantidad;
    private LocalDateTime actualizadoEn;
}
