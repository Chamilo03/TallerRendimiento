package co.edu.unbosque.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InventarioDTO implements Serializable{
    private Long id;
    private Long productoId; // solo el id del producto
    private Integer cantidad;
    private LocalDateTime actualizadoEn;

    public Long getId() {
        return id;
    }

    public Long getProductoId() { 
        return productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public LocalDateTime getActualizadoEn() {
        return actualizadoEn;
    }

    // ---------- SETTERS ----------

    public void setId(Long id) {
        this.id = id;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public void setActualizadoEn(LocalDateTime actualizadoEn) {
        this.actualizadoEn = actualizadoEn;
    }
}
