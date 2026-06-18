package cl.day1103.inventario.controller;

import cl.day1103.inventario.assembler.InventarioModelAssembler;
import cl.day1103.inventario.dto.DetalleDescuentoDTO;
import cl.day1103.inventario.dto.InventarioRequestDTO;
import cl.day1103.inventario.dto.InventarioResponseDTO;
import cl.day1103.inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/inventario")
@RequiredArgsConstructor
@Tag(name = "Inventario V2 - HATEOAS", description = "CRUD de inventario con enlaces HATEOAS")
public class InventarioControllerV2 {

    private final InventarioService inventarioService;
    private final InventarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar inventario con HATEOAS")
    public CollectionModel<EntityModel<InventarioResponseDTO>> listarTodos() {
        var inventarios = inventarioService.findAll()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inventarios,
                linkTo(methodOn(InventarioControllerV2.class).listarTodos()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Buscar inventario por producto ID con HATEOAS")
    public EntityModel<InventarioResponseDTO> buscarPorId(@PathVariable Long id) {
        InventarioResponseDTO inventario = inventarioService.findById(id);
        return assembler.toModel(inventario);
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear registro de inventario con HATEOAS")
    public ResponseEntity<EntityModel<InventarioResponseDTO>> crear(@Valid @RequestBody InventarioRequestDTO dto) {
        InventarioResponseDTO inventario = inventarioService.save(dto);
        return ResponseEntity.status(201).body(assembler.toModel(inventario));
    }

    @PutMapping(value = "/{productoId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar registro de inventario con HATEOAS")
    public EntityModel<InventarioResponseDTO> actualizar(@PathVariable Long productoId,
                                                         @Valid @RequestBody InventarioRequestDTO dto) {
        InventarioResponseDTO inventario = inventarioService.actualizarInventario(productoId, dto);
        return assembler.toModel(inventario);
    }

    @PutMapping(value = "/descontar", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Descontar stock desde una venta")
    public ResponseEntity<Map<String, String>> descontarStock(@RequestBody List<DetalleDescuentoDTO> detalles) {
        for (DetalleDescuentoDTO detalle : detalles) {
            inventarioService.actualizarStock(detalle.getProductoId(), detalle.getCantidad());
        }
        return ResponseEntity.ok(Map.of("mensaje", "Stock descontado correctamente"));
    }

    @DeleteMapping(value = "/{productoId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar registro de inventario")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long productoId) {
        inventarioService.deleteByProductoId(productoId);
        return ResponseEntity.ok(Map.of("mensaje", "Inventario eliminado correctamente"));
    }
}
