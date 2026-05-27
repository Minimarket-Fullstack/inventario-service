package cl.day1103.inventario.config;

import cl.day1103.inventario.model.Inventario;
import cl.day1103.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        if (inventarioRepository.count() > 0) {
            log.info(">>> DataInitializer: La BD ya tiene datos, se omite la carga inicial");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando productos de prueba en el Inventario...");




        inventarioRepository.save(new Inventario(null, 1L, 100, 10));
        inventarioRepository.save(new Inventario(null, 2L, 50, 5));
        inventarioRepository.save(new Inventario(null, 3L, 80, 15));


        //Al limite
        inventarioRepository.save(new Inventario(null, 4L, 11, 10));

        //Pasado del limite
        inventarioRepository.save(new Inventario(null, 10L, 3, 10));

        log.info(">>> DataInitializer: Carga finalizada. {} productos insertados correctamente en el inventario.", inventarioRepository.count());
    }
}