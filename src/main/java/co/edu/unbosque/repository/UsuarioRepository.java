package co.edu.unbosque.repository;

import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.entity.RolUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByRol(RolUsuario rol);
    Usuario findByCorreo(String correo);
}
