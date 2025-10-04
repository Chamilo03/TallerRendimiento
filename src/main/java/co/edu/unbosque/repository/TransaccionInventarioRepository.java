package co.edu.unbosque.repository;

import co.edu.unbosque.entity.TransaccionInventario;
import co.edu.unbosque.entity.TipoTransaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionInventarioRepository extends JpaRepository<TransaccionInventario, Long> {
    List<TransaccionInventario> findByProductoId(Long productoId);
    List<TransaccionInventario> findByTipo(TipoTransaccion tipo);
}
