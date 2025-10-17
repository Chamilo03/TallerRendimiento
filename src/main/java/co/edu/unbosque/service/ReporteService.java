package co.edu.unbosque.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.model.dto.ReporteDTO;
import co.edu.unbosque.repository.TransaccionInventarioRepository;

@Service
public class ReporteService {

    private final TransaccionInventarioRepository transaccionInventarioRepository;

    public ReporteService(TransaccionInventarioRepository transaccionInventarioRepository) {
        this.transaccionInventarioRepository = transaccionInventarioRepository;
    }

    /**
     * Obtiene el top 10 de productos más vendidos en un periodo determinado.
     * Los resultados se almacenan en caché por periodo.
     */
    @Cacheable(value = "topSelling", key = "#period")
    public List<ReporteDTO> obtenerTopSelling(String period) {
        // 🔹 Calcular el rango de fechas según el periodo
        LocalDateTime desde = calcularFechaDesde(period);
        LocalDateTime hasta = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);

        // 🔹 Obtener solo las transacciones de tipo "VENTA" en el rango
        List<TransaccionInventario> ventas = transaccionInventarioRepository.findByTipoAndFechaBetween("VENTA", desde, hasta);

        // 🔹 Agrupar por producto y sumar las cantidades vendidas
        Map<Long, Long> ventasPorProducto = ventas.stream()
                .filter(t -> t.getProducto() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getProducto().getId(),
                        Collectors.summingLong(TransaccionInventario::getCantidad)
                ));

        // 🔹 Ordenar por cantidad descendente, limitar a top 10 y mapear a DTO
        return ventasPorProducto.entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(10)
                .map(entry -> {
                    String nombreProducto = ventas.stream()
                            .filter(t -> t.getProducto() != null && t.getProducto().getId().equals(entry.getKey()))
                            .findFirst()
                            .map(t -> t.getProducto().getNombre())
                            .orElse("Desconocido");

                    return new ReporteDTO(entry.getKey(), nombreProducto, entry.getValue());
                })
                .collect(Collectors.toList());
    }

    /**
     * Calcula la fecha de inicio según el periodo solicitado.
     */
    private LocalDateTime calcularFechaDesde(String period) {
        LocalDate hoy = LocalDate.now();
        return switch (period.toLowerCase()) {
            case "day" -> LocalDateTime.of(hoy.minusDays(1), LocalTime.MIN);
            case "week" -> LocalDateTime.of(hoy.minusWeeks(1), LocalTime.MIN);
            case "month" -> LocalDateTime.of(hoy.minusMonths(1), LocalTime.MIN);
            case "year" -> LocalDateTime.of(hoy.minusYears(1), LocalTime.MIN);
            default -> LocalDateTime.of(hoy.minusMonths(1), LocalTime.MIN);
        };
    }
}
