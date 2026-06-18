package cl.day1103.inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Respuesta de inventario")
public class InventarioResponseDTO {

    @Schema(
            description = "ID del registro de inventario",
            example = "1"
    )
    private Long id;

    @Schema(
            description = "ID del producto",
            example = "1"
    )
    private Long productoId;

    @Schema(
            description = "Stock disponible",
            example = "50"
    )
    private Integer stock;

    @Schema(
            description = "Stock mínimo permitido",
            example = "10"
    )
    private Integer stockMinimo;
}