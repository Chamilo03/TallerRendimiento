package co.edu.unbosque.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransaccionInventarioDTO {
    private Long id;
    private Long productoId;
    private String tipo;
    private Integer cantidad;
    private LocalDateTime fecha;
    private Long usuarioId;
}
