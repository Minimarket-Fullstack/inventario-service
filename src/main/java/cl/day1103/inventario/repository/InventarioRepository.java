package cl.day1103.inventario.repository;

import cl.day1103.inventario.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {
    // Método clave para buscar stock por el ID del producto
    Optional<Inventario> findByProductoId(Long productoId);
}