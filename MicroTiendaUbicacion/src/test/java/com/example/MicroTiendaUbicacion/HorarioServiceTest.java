package com.example.MicroTiendaUbicacion;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.MicroTiendaUbicacion.entity.HorarioTienda;
import com.example.MicroTiendaUbicacion.repository.HorarioTiendaRepository;

@SpringBootTest
public class HorarioServiceTest {

    // Inyecta el servicio de Horario para ser probado.
    @Autowired
    private HorarioServiceTest horarioService;

    // Crea un mock del repositorio de Horario para simular su comportamiento.
    @Mock
    private HorarioTiendaRepository horarioRepository;

    @Test
    public void testFindAll() {
        // Define el comportamiento del mock: cuando se llame a findAll(), devuelve una lista con una Carrera.
        when(horarioRepository.findAll()).thenReturn(List.of(new HorarioTienda("1", "Ingeniería")));

        // Llama al método findAll() del servicio.
        List<HorarioTienda> horarios = horarioService.findAll();

        // Verifica que la lista devuelta no sea nula y contenga exactamente una Carrera.
        assertNotNull(horarios);
        assertEquals(1, horarios.size());
    }

    @Test
    public void testFindByCodigo() {
        String codigo = "1";
        HorarioTienda horario = new HorarioTienda(codigo, "Ingeniería");

        // Define el comportamiento del mock: cuando se llame a findById() con "1", devuelve una Carrera opcional.
        when(horarioRepository.findById(codigo)).thenReturn(Optional.of(horario));

        // Llama al método findByCodigo() del servicio.
        HorarioTienda found = horarioService.findByCodigo(codigo);

        // Verifica que la Carrera devuelta no sea nula y que su código coincida con el código esperado.
        assertNotNull(found);
        assertEquals(codigo, found.getCodigo());
    }

    @Test
    public void testSave() {
        HorarioTienda horario = new HorarioTienda("1", "Ingeniería");

        // Define el comportamiento del mock: cuando se llame a save(), devuelve la Carrera proporcionada.
        when(horarioRepository.save(horario)).thenReturn(horario);

        // Llama al método save() del servicio.
        HorarioTienda saved = horarioService.save(horario);

        // Verifica que la Carrera guardada no sea nula y que su nombre coincida con el nombre esperado.
        assertNotNull(saved);
        assertEquals("Ingeniería", saved.getNombre());
    }

    @Test
    public void testDeleteByCodigo() {
        String codigo = "1";

        // Define el comportamiento del mock: cuando se llame a deleteById(), no hace nada.
        doNothing().when(horarioRepository).deleteById(codigo);

        // Llama al método deleteByCodigo() del servicio.
        horarioService.deleteByCodigo(codigo);

        // Verifica que el método deleteById() del repositorio se haya llamado exactamente una vez con el código proporcionado.
        verify(horarioRepository, times(1)).deleteById(codigo);
    }
}