package co.edu.unbosque.model.dto;

import co.edu.unbosque.entity.RolUsuario;
import lombok.Data;

@Data
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String correo;
    private RolUsuario rol;
}
