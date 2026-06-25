package com.example.MicroTiendaUbicacion;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.MicroTiendaUbicacion.entity.HorarioTienda;
import com.example.MicroTiendaUbicacion.entity.Tienda;
import com.example.MicroTiendaUbicacion.repository.HorarioTiendaRepository;
import com.example.MicroTiendaUbicacion.repository.TiendaRepository;

import net.datafaker.Faker; // Si usas com.github.javafaker.Faker, ajusta este import

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

        // Generar tiendas primero (HorarioTienda depende de Tienda)
        for (int i = 0; i < 5; i++) {
            Tienda tienda = new Tienda();
            // No asignar id_tienda manualmente: es @GeneratedValue(IDENTITY)
            tienda.setNombre(faker.company().name());
            tienda.setDireccion(faker.address().fullAddress());
            tienda.setTelefono(faker.phoneNumber().cellPhone());
            tienda.setIdComuna(faker.number().numberBetween(1, 10));
            tienda.setIdRegion(faker.number().numberBetween(1, 10));
            tienda.setCodigo_postal(faker.address().zipCode());
            tienda.setActiva(faker.bool().bool());
            tiendaRepository.save(tienda);
        }

        List<Tienda> tiendas = tiendaRepository.findAll();
        HorarioTienda.DiaSemana[] dias = HorarioTienda.DiaSemana.values();

        // Generar horarios de tienda
        for (int i = 0; i < 10; i++) {
            HorarioTienda horario = new HorarioTienda();
            // No asignar id_horario manualmente: es @GeneratedValue(IDENTITY)
            horario.setTienda(tiendas.get(random.nextInt(tiendas.size())));
            horario.setDia_semana(dias[random.nextInt(dias.length)]);
            horario.setHora_apertura(LocalTime.of(9, 0));
            horario.setHora_cierre(LocalTime.of(18, 0));
            horario.setCerrado(false);
            horarioRepository.save(horario);
        }
    }
}