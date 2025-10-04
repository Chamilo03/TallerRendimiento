package co.edu.unbosque.model.dto;

import co.edu.unbosque.entity.TipoTransaccion;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransaccionInventarioDTO {
    private Long id;
    private Long productoId;
    private TipoTransaccion tipo;
    private Integer cantidad;
    private LocalDateTime fecha;
    private Long usuarioId;
}
