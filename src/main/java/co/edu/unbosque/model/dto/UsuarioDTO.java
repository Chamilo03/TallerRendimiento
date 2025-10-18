package co.edu.unbosque.model.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO implements Serializable{
    private Long id;
    private String nombre;
    private String correo;
    private RolDTO rol;
}
