package co.edu.unbosque.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "transacciones_inventario")
@Data
public class TransaccionInventario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Producto
    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // Enum de tipo de transacción (VENTA, ENTRADA, DEVOLUCION, AJUSTE)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransaccion tipo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime fecha;

    // Relación con Usuario (puede ser null si se borra)
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
