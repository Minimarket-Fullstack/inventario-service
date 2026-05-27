package cl.day1103.inventario.controller;

import cl.day1103.inventario.dto.DetalleDescuentoDTO;
import cl.day1103.inventario.dto.InventarioRequestDTO;
import cl.day1103.inventario.dto.InventarioResponseDTO;
import cl.day1103.inventario.service.InventarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PutMapping("/descontar")
    public ResponseEntity<Void> descontarStockFromVenta(@RequestBody List<DetalleDescuentoDTO> detalles) {
        for (DetalleDescuentoDTO detalle : detalles) {
            inventarioService.actualizarStock(detalle.getProductoId(), detalle.getCantidad());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> getAll() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<InventarioResponseDTO> getByProductoId(@PathVariable Long productoId) {
        return ResponseEntity.ok(inventarioService.findById(productoId));
    }

    @PostMapping
    public ResponseEntity<InventarioResponseDTO> create(@Valid @RequestBody InventarioRequestDTO dto) {
        return ResponseEntity.ok(inventarioService.save(dto));
    }

    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> delete(@PathVariable Long productoId) {
        inventarioService.deleteByProductoId(productoId);
        return ResponseEntity.noContent().build();
    }
}