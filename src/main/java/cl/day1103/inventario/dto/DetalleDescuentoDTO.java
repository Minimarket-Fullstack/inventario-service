package cl.day1103.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleDescuentoDTO {
    private Long productoId;
    private Integer cantidad;
}