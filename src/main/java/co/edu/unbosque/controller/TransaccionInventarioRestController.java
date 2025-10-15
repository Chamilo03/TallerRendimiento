package co.edu.unbosque.controller;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.service.TransaccionInventarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionInventarioRestController {

    private final TransaccionInventarioService transaccionInventarioService;

    public TransaccionInventarioRestController(TransaccionInventarioService transaccionInventarioService) {
        this.transaccionInventarioService = transaccionInventarioService;
    }

    // Listar todas las transacciones
    @GetMapping
    public List<TransaccionInventario> listarTransacciones() {
        return transaccionInventarioService.listarTransacciones();
    }

    // Obtener transacción por ID
    @GetMapping("/{id}")
    public TransaccionInventario obtenerTransaccion(@PathVariable Long id) {
        return transaccionInventarioService.obtenerTransaccion(id).orElse(null);
    }

    // Crear una nueva transacción
    @PostMapping
    public TransaccionInventario crearTransaccion(@RequestBody TransaccionInventario transaccion) {
        return transaccionInventarioService.guardarTransaccion(transaccion);
    }

    // Eliminar una transacción
    @DeleteMapping("/{id}")
    public void eliminarTransaccion(@PathVariable Long id) {
        transaccionInventarioService.eliminarTransaccion(id);
    }

    // Buscar transacciones por producto
    @GetMapping("/producto/{productoId}")
    public List<TransaccionInventario> buscarPorProducto(@PathVariable Long productoId) {
        return transaccionInventarioService.buscarPorProducto(productoId);
    }

    // Buscar transacciones por tipo
    @GetMapping("/tipo/{tipo}")
    public List<TransaccionInventario> buscarPorTipo(@PathVariable String tipo) {
        return transaccionInventarioService.buscarPorTipo(tipo);
    }
}
