package co.edu.unbosque.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.Rol;
import co.edu.unbosque.entity.Usuario;
import co.edu.unbosque.model.dto.RolDTO;
import co.edu.unbosque.model.dto.UsuarioDTO;
import co.edu.unbosque.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ------------------- CONVERSIONES -------------------

    private RolDTO convertirRolADTO(Rol rol) {
        if (rol == null) return null;
        RolDTO dto = new RolDTO();
        dto.setId(rol.getId());
        dto.setNombre(rol.getNombre());
        return dto;
    }

    private Rol convertirRolAEntidad(RolDTO dto) {
        if (dto == null) return null;
        Rol rol = new Rol();
        rol.setId(dto.getId());
        rol.setNombre(dto.getNombre());
        return rol;
    }

    private UsuarioDTO convertirADTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setCorreo(usuario.getCorreo());
        dto.setRol(convertirRolADTO(usuario.getRol()));
        return dto;
    }

    private Usuario convertirAEntidad(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setNombre(dto.getNombre());
        usuario.setCorreo(dto.getCorreo());
        usuario.setRol(convertirRolAEntidad(dto.getRol()));
        return usuario;
    }

    // ------------------- SERVICIOS -------------------

    @Cacheable("usuarios")
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "usuario", key = "#id")
    public Optional<UsuarioDTO> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id)
                .map(this::convertirADTO);
    }

    @CacheEvict(value = {"usuarios", "usuario", "usuariosPorRol", "usuarioPorCorreo"}, allEntries = true)
    public UsuarioDTO guardarUsuario(UsuarioDTO dto) {
        Usuario usuario = convertirAEntidad(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return convertirADTO(guardado);
    }

    @CacheEvict(value = {"usuarios", "usuario", "usuariosPorRol", "usuarioPorCorreo"}, allEntries = true)
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Cacheable(value = "usuariosPorRol", key = "#rol")
    public List<UsuarioDTO> buscarPorRol(String rol) {
        return usuarioRepository.findByRol_Nombre(rol)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "usuarioPorCorreo", key = "#correo")
    public UsuarioDTO buscarPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo);
        return usuario != null ? convertirADTO(usuario) : null;
    }
}
