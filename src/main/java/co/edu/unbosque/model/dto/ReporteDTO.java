package co.edu.unbosque.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO {
    private Long productoId;
    private String nombreProducto;
    private Long totalVendidas;
}
