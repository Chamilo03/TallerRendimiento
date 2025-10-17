package co.edu.unbosque.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unbosque.entity.TransaccionInventario;

@Repository
public interface TransaccionInventarioRepository extends JpaRepository<TransaccionInventario, Long> {
    List<TransaccionInventario> findByProductoId(Long productoId);
    List<TransaccionInventario> findByTipo(String tipo);
    List<TransaccionInventario> findByTipoAndFechaBetween(String tipo, LocalDateTime desde, LocalDateTime hasta);
}
