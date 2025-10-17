package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Cacheable("usuarios")
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @Cacheable(value = "usuario", key = "#id")
    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    @CacheEvict(value = {"usuarios", "usuario", "usuariosPorRol", "usuarioPorCorreo"}, allEntries = true)
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @CacheEvict(value = {"usuarios", "usuario", "usuariosPorRol", "usuarioPorCorreo"}, allEntries = true)
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Cacheable(value = "usuariosPorRol", key = "#rol")
    public List<Usuario> buscarPorRol(String rol) {
        return usuarioRepository.findByRol_Nombre(rol);
    }

    @Cacheable(value = "usuarioPorCorreo", key = "#correo")
    public Usuario buscarPorCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }
}
