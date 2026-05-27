package cl.day1103.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioResponseDTO {
    private Long id;
    private Long productoId;
    private Integer stock;
    private Integer stockMinimo;
}