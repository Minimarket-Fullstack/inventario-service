import cl.day1103.inventario.dto.InventarioRequestDTO;
import cl.day1103.inventario.dto.InventarioResponseDTO;
import cl.day1103.inventario.model.Inventario;
import cl.day1103.inventario.repository.InventarioRepository;
import cl.day1103.inventario.service.InventarioService;
import cl.day1103.inventario.webclient.NotificacionClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceMockitoTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private NotificacionClient notificacionClient;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void findAll_deberiaRetornarInventario() {
        Inventario inventario = new Inventario(1L, 10L, 50, 10);
        when(inventarioRepository.findAll()).thenReturn(List.of(inventario));

        List<InventarioResponseDTO> resultado = inventarioService.findAll();

        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getProductoId());
        verify(inventarioRepository).findAll();
    }

    @Test
    void save_deberiaCrearInventario() {
        InventarioRequestDTO dto = new InventarioRequestDTO(10L, 50, 10);
        Inventario guardado = new Inventario(1L, 10L, 50, 10);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(guardado);

        InventarioResponseDTO resultado = inventarioService.save(dto);

        assertEquals(1L, resultado.getId());
        assertEquals(50, resultado.getStock());
        verify(inventarioRepository).save(any(Inventario.class));
    }

    @Test
    void actualizarStock_deberiaDescontarStock() {
        Inventario inventario = new Inventario(1L, 10L, 50, 10);
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(inv -> inv.getArgument(0));

        InventarioResponseDTO resultado = inventarioService.actualizarStock(10L, 5);

        assertEquals(45, resultado.getStock());
        verify(inventarioRepository).findByProductoId(10L);
        verify(inventarioRepository).save(any(Inventario.class));
    }

@Test
    void findById_deberiaRetornarInventarioPorProductoId() {
        Inventario inventario = new Inventario(1L, 10L, 50, 10);
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventario));

        InventarioResponseDTO resultado = inventarioService.findById(10L);

        assertEquals(10L, resultado.getProductoId());
        assertEquals(50, resultado.getStock());
        verify(inventarioRepository).findByProductoId(10L);
    }

    @Test
    void actualizarStock_deberiaLanzarExcepcionSiNoHayStock() {
        Inventario inventario = new Inventario(1L, 10L, 3, 10);
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventario));

        assertThrows(RuntimeException.class, () -> inventarioService.actualizarStock(10L, 5));
        verify(inventarioRepository, never()).save(any(Inventario.class));
    }

    @Test
    void actualizarStock_deberiaEnviarAlertaSiQuedaBajoMinimo() {
        Inventario inventario = new Inventario(1L, 10L, 12, 10);
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventario));
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(inv -> inv.getArgument(0));

        InventarioResponseDTO resultado = inventarioService.actualizarStock(10L, 2);

        assertEquals(10, resultado.getStock());
        verify(notificacionClient).enviarAlerta(anyMap());
    }

    @Test
    void deleteByProductoId_deberiaEliminarInventario() {
        Inventario inventario = new Inventario(1L, 10L, 50, 10);
        when(inventarioRepository.findByProductoId(10L)).thenReturn(Optional.of(inventario));

        inventarioService.deleteByProductoId(10L);

        verify(inventarioRepository).delete(inventario);
    }
}
