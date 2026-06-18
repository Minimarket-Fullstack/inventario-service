package cl.day1103.inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de descuento de stock por venta")
public class DetalleDescuentoDTO {

    @Schema(
            description = "ID del producto",
            example = "1"
    )
    private Long productoId;

    @Schema(
            description = "Cantidad a descontar",
            example = "2"
    )
    private Integer cantidad;
}