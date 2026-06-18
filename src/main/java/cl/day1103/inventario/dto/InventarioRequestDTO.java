package cl.day1103.inventario.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Datos necesarios para registrar o actualizar inventario")
public class InventarioRequestDTO {

    @Schema(
            description = "ID del producto asociado",
            example = "1"
    )
    @NotNull(message = "El ID del producto es obligatorio")
    private Long productoId;

    @Schema(
            description = "Cantidad disponible en stock",
            example = "50"
    )
    @NotNull(message = "El stock no puede ser nulo")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;

    @Schema(
            description = "Stock mínimo permitido",
            example = "10"
    )
    @NotNull(message = "El stock mínimo es obligatorio")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo")
    private Integer stockMinimo;
}