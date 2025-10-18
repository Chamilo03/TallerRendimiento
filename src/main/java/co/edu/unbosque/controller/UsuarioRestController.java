package co.edu.unbosque.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.dto.UsuarioDTO;
import co.edu.unbosque.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // 🔹 Listar todos los usuarios
    @GetMapping
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    // 🔹 Obtener usuario por ID
    @GetMapping("/{id}")
    public Optional<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id);
    }

    // 🔹 Crear usuario
    @PostMapping
    public UsuarioDTO crearUsuario(@RequestBody UsuarioDTO dto) {
        return usuarioService.guardarUsuario(dto);
    }

    // 🔹 Actualizar usuario
    @PutMapping("/{id}")
    public UsuarioDTO actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        dto.setId(id);
        return usuarioService.guardarUsuario(dto);
    }

    // 🔹 Eliminar usuario
    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }

    // 🔹 Buscar usuarios por rol
    @GetMapping("/rol/{rol}")
    public List<UsuarioDTO> buscarPorRol(@PathVariable String rol) {
        return usuarioService.buscarPorRol(rol);
    }

    // 🔹 Buscar usuario por correo
    @GetMapping("/correo/{correo}")
    public UsuarioDTO buscarPorCorreo(@PathVariable String correo) {
        return usuarioService.buscarPorCorreo(correo);
    }
}
