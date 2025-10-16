package co.edu.unbosque.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.repository.TransaccionInventarioRepository;

@Service
public class ReporteService {

    private final TransaccionInventarioRepository transaccionInventarioRepository;

    public ReporteService(TransaccionInventarioRepository transaccionInventarioRepository) {
        this.transaccionInventarioRepository = transaccionInventarioRepository;
    }

    public List<Map<String, Object>> obtenerTopSelling(String period) {
        LocalDateTime desde = switch (period.toLowerCase()) {
            case "day" -> LocalDateTime.now().minusDays(1);
            case "week" -> LocalDateTime.now().minusWeeks(1);
            default -> LocalDateTime.now().minusMonths(1);
        };

        List<TransaccionInventario> ventas = transaccionInventarioRepository.findAll();

        return ventas.stream()
            .filter(t -> "VENTA".equalsIgnoreCase(t.getTipo()))
            .filter(t -> t.getFecha().isAfter(desde))
            .collect(Collectors.groupingBy(
                t -> t.getProducto().getNombre(),
                Collectors.summingInt(TransaccionInventario::getCantidad)
            ))
            .entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(10)
            .map(e -> {
                Map<String, Object> data = new HashMap<>();
                data.put("producto", e.getKey());
                data.put("cantidadVendida", e.getValue());
                return data;
            })
            .collect(Collectors.toList());
    }
}
