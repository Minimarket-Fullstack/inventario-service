package cl.day1103.inventario.service;

import cl.day1103.inventario.dto.InventarioRequestDTO;
import cl.day1103.inventario.dto.InventarioResponseDTO;
import cl.day1103.inventario.model.Inventario;
import cl.day1103.inventario.repository.InventarioRepository;
import cl.day1103.inventario.webclient.NotificacionClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final NotificacionClient notificacionClient;

    public List<InventarioResponseDTO> findAll() {
        log.info("[INVENTARIO] Solicitando lista completa de productos en stock.");
        return inventarioRepository.findAll().stream().map(this::convertToResponseDto).collect(Collectors.toList());
    }

    public InventarioResponseDTO save(InventarioRequestDTO dto) {
        log.info("[INVENTARIO] Registrando nuevo producto en inventario. Producto ID: {}", dto.getProductoId());
        Inventario inventario = convertToEntity(dto);
        return convertToResponseDto(inventarioRepository.save(inventario));
    }

    public InventarioResponseDTO actualizarStock(Long productoId, Integer cantidadVendida) {
        log.info("[INVENTARIO] Procesando solicitud de descuento. Producto ID: {}, Cantidad a restar: {}", productoId, cantidadVendida);

        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> {
                    log.error("[INVENTARIO ERROR] No se encontró el producto con ID: {}", productoId);
                    return new RuntimeException("PRODUCTO NO ENCONTRADO");
                });

        if (inventario.getStock() < cantidadVendida) {
            log.warn("[INVENTARIO WARN] Stock insuficiente para Producto ID: {}. Stock actual: {}, Requerido: {}",
                    productoId, inventario.getStock(), cantidadVendida);
            throw new RuntimeException("STOCK INSUFICIENTE");
        }


        inventario.setStock(inventario.getStock() - cantidadVendida);
        log.info("[INVENTARIO SUCCESS] Descuento aplicado. Nuevo stock para Producto ID {}: {}", productoId, inventario.getStock());


        Inventario inventarioGuardado = inventarioRepository.save(inventario);


        if (inventarioGuardado.getStock() <= inventarioGuardado.getStockMinimo()) {
            log.warn("[INVENTARIO -> NOTIFICACIONES] ¡Alerta! El Producto ID {} alcanzó niveles críticos. Stock: {}, Mínimo: {}. Informando al sistema...",
                    productoId, inventarioGuardado.getStock(), inventarioGuardado.getStockMinimo());

            Map<String, Object> datosAlerta = Map.of(
                    "tipo", "STOCK_CRITICO",
                    "mensaje", "Alerta de inventario: El producto con ID " + productoId + " tiene un stock crítico de " + inventarioGuardado.getStock() + " unidades (Mínimo: " + inventarioGuardado.getStockMinimo() + ")."
            );


            notificacionClient.enviarAlerta(datosAlerta);
            log.info("[INVENTARIO -> NOTIFICACIONES] Notificación de stock enviada correctamente.");
        }

        return convertToResponseDto(inventarioGuardado);
    }

    private InventarioResponseDTO convertToResponseDto(Inventario inventario) {
        return new InventarioResponseDTO(
                inventario.getId(),
                inventario.getProductoId(),
                inventario.getStock(),
                inventario.getStockMinimo()
        );
    }

    private Inventario convertToEntity(InventarioRequestDTO dto) {
        Inventario inv = new Inventario();
        inv.setProductoId(dto.getProductoId());
        inv.setStock(dto.getStock());
        inv.setStockMinimo(dto.getStockMinimo());
        return inv;
    }

    public InventarioResponseDTO findById(Long productoId) {
        log.info("[INVENTARIO] Buscando stock del Producto ID: {}", productoId);
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
        return convertToResponseDto(inventario);
    }

    public void deleteByProductoId(Long productoId) {
        log.info("[INVENTARIO] Solicitud para eliminar Producto ID: {}", productoId);
        Inventario inventario = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new RuntimeException("No se puede eliminar: Producto no encontrado"));
        inventarioRepository.delete(inventario);
    }
}