package co.edu.unbosque.model.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoriaDTO implements Serializable{
    private Long id;
    private String nombre;
    private String descripcion;

    public void setNombre(String nombre) {
    this.nombre = nombre;
}

public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
}

}

