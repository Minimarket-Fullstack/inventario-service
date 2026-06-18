package cl.day1103.inventario.config;

import cl.day1103.inventario.model.Inventario;
import cl.day1103.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) {

        if (inventarioRepository.count() > 0) {
            System.out.println("Inventario ya tiene datos. No se cargan datos iniciales.");
            return;
        }

        inventarioRepository.saveAll(List.of(
                new Inventario(null, 1L, 100, 10),
                new Inventario(null, 2L, 50, 5),
                new Inventario(null, 3L, 80, 15),
                new Inventario(null, 4L, 11, 10),
                new Inventario(null, 5L, 3, 10)
        ));

        System.out.println("Datos iniciales de inventario cargados correctamente.");
    }
}