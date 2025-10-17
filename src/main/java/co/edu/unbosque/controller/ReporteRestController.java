package co.edu.unbosque.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.unbosque.model.dto.ReporteDTO;
import co.edu.unbosque.service.ReporteService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class ReporteRestController {

    private final ReporteService reporteService;

    public ReporteRestController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/top-selling")
    public List<ReporteDTO> obtenerTopSelling(@RequestParam(defaultValue = "month") String period) {
        return reporteService.obtenerTopSelling(period);
    }
}
