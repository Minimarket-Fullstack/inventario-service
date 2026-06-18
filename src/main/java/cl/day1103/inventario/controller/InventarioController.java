package cl.day1103.inventario.controller;

import cl.day1103.inventario.dto.DetalleDescuentoDTO;
import cl.day1103.inventario.dto.InventarioRequestDTO;
import cl.day1103.inventario.dto.InventarioResponseDTO;
import cl.day1103.inventario.service.InventarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Inventario",
        description = "Operaciones relacionadas con la gestión de inventario"
)
@RestController
@RequestMapping({"/api/v1/inventario", "/api/inventario"})
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @Operation(summary = "Descontar stock de productos desde una venta")
    @PutMapping("/descontar")
    public ResponseEntity<Void> descontarStockFromVenta(
            @RequestBody List<DetalleDescuentoDTO> detalles) {

        for (DetalleDescuentoDTO detalle : detalles) {
            inventarioService.actualizarStock(
                    detalle.getProductoId(),
                    detalle.getCantidad()
            );
        }

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Listar todo el inventario")
    @GetMapping
    public ResponseEntity<List<InventarioResponseDTO>> getAll() {
        return ResponseEntity.ok(inventarioService.findAll());
    }

    @Operation(summary = "Buscar inventario por ID de producto")
    @GetMapping("/{productoId}")
    public ResponseEntity<InventarioResponseDTO> getByProductoId(
            @PathVariable Long productoId) {

        return ResponseEntity.ok(
                inventarioService.findById(productoId)
        );
    }

    @Operation(summary = "Crear un registro de inventario")
    @PostMapping
    public ResponseEntity<InventarioResponseDTO> create(
            @Valid @RequestBody InventarioRequestDTO dto) {

        return ResponseEntity.ok(
                inventarioService.save(dto)
        );
    }

    @Operation(summary = "Eliminar un registro de inventario")
    @DeleteMapping("/{productoId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long productoId) {

        inventarioService.deleteByProductoId(productoId);
        return ResponseEntity.noContent().build();
    }
}