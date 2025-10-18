package co.edu.unbosque.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteDTO implements Serializable{
    private Long productoId;
    private String nombreProducto;
    private Long totalVendidas;
}
