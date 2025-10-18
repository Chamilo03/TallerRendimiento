package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.dto.TransaccionInventarioDTO;
import co.edu.unbosque.service.TransaccionInventarioService;

@RestController
@RequestMapping("/api/transacciones")
@CrossOrigin(origins = "*")
public class TransaccionInventarioRestController {

    private final TransaccionInventarioService transaccionInventarioService;

    public TransaccionInventarioRestController(TransaccionInventarioService transaccionInventarioService) {
        this.transaccionInventarioService = transaccionInventarioService;
    }

    // 🔹 Listar todas las transacciones
    @GetMapping
    public List<TransaccionInventarioDTO> listarTransacciones() {
        return transaccionInventarioService.listarTransacciones();
    }

    // 🔹 Obtener transacción por ID
    @GetMapping("/{id}")
    public TransaccionInventarioDTO obtenerTransaccion(@PathVariable Long id) {
        return transaccionInventarioService.obtenerTransaccion(id).orElse(null);
    }

    // 🔹 Crear nueva transacción
    @PostMapping
    public TransaccionInventarioDTO crearTransaccion(@RequestBody TransaccionInventarioDTO dto) {
        return transaccionInventarioService.guardarTransaccion(dto);
    }

    // 🔹 Eliminar una transacción
    @DeleteMapping("/{id}")
    public void eliminarTransaccion(@PathVariable Long id) {
        transaccionInventarioService.eliminarTransaccion(id);
    }

    // 🔹 Buscar transacciones por producto
    @GetMapping("/producto/{productoId}")
    public List<TransaccionInventarioDTO> buscarPorProducto(@PathVariable Long productoId) {
        return transaccionInventarioService.buscarPorProducto(productoId);
    }

    // 🔹 Buscar transacciones por tipo
    @GetMapping("/tipo/{tipo}")
    public List<TransaccionInventarioDTO> buscarPorTipo(@PathVariable String tipo) {
        return transaccionInventarioService.buscarPorTipo(tipo);
    }
}
