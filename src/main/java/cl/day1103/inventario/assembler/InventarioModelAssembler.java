package cl.day1103.inventario.assembler;

import cl.day1103.inventario.controller.InventarioControllerV2;
import cl.day1103.inventario.dto.InventarioResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<InventarioResponseDTO, EntityModel<InventarioResponseDTO>> {

    @Override
    public EntityModel<InventarioResponseDTO> toModel(InventarioResponseDTO inventario) {

        return EntityModel.of(
                inventario,

                linkTo(methodOn(InventarioControllerV2.class)
                        .buscarPorId(inventario.getProductoId()))
                        .withSelfRel(),

                linkTo(methodOn(InventarioControllerV2.class)
                        .listarTodos())
                        .withRel("inventarios")
        );
    }
}
