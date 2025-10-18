package co.edu.unbosque.model.dto;

import java.time.LocalDateTime;
import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransaccionInventarioDTO implements Serializable{
    private Long id;
    private Long productoId;
    private String tipo;
    private Integer cantidad;
    private LocalDateTime fecha;
    private Long usuarioId;
}
