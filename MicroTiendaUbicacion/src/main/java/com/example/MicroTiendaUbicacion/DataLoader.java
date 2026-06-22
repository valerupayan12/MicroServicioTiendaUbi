package com.example.MicroTiendaUbicacion;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.MicroTiendaUbicacion.entity.HorarioTienda;
import com.example.MicroTiendaUbicacion.entity.Tienda;
import com.example.MicroTiendaUbicacion.repository.HorarioTiendaRepository;
import com.example.MicroTiendaUbicacion.repository.TiendaRepository;


@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private HorarioTiendaRepository horarioRepository;
    @Autowired
    private TiendaRepository tiendaRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        // Generar horarios de tienda
        for (int i = 0; i < 3; i++) {
            HorarioTienda horario = new HorarioTienda();
            horario.setId_horario(i + 1);
            horario.setNombre(faker.book().genre());
            horarioRepository.save(horario);
        }

        // Generar tiendas        for (int i = 0; i < 5; i++) {
        for (int i = 0; i < 5; i++) {
            Tienda tienda = new Tienda();
            ((Object) tienda).setId(i + 1);
            tienda.setNombre(faker.company().name());
            tienda.setDireccion(faker.address().fullAddress());
            tienda.setTelefono(faker.phoneNumber().cellPhone());
            tienda.setIdComuna(faker.number().numberBetween(1, 10));
            tienda.setIdRegion(faker.number().numberBetween(1, 10));
            tienda.setCodigo_postal(faker.address().zipCode());
            tienda.setActiva(faker.bool());
            tiendaRepository.save(tienda);
        }
    }
}