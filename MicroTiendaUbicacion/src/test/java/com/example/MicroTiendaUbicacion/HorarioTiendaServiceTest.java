package com.example.MicroTiendaUbicacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.MicroTiendaUbicacion.dto.HorarioTiendaDTO;
import com.example.MicroTiendaUbicacion.entity.HorarioTienda;
import com.example.MicroTiendaUbicacion.entity.Tienda;
import com.example.MicroTiendaUbicacion.repository.HorarioTiendaRepository;
import com.example.MicroTiendaUbicacion.repository.TiendaRepository;
import com.example.MicroTiendaUbicacion.service.HorarioTiendaService;

@SpringBootTest
public class HorarioTiendaServiceTest {

    @Autowired
    private HorarioTiendaService horarioTiendaService;

    @MockitoBean
    private HorarioTiendaRepository horarioTiendaRepository;

    @MockitoBean
    private TiendaRepository tiendaRepository;

    // LISTAR
    @Test
    public void testListar() {
        Tienda tienda = new Tienda();
        tienda.setId_tienda(1);

        HorarioTienda horario = new HorarioTienda();
        horario.setId_horario(1);
        horario.setTienda(tienda);
        horario.setDia_semana(HorarioTienda.DiaSemana.LUNES);
        horario.setHora_apertura(LocalTime.of(9, 0));
        horario.setHora_cierre(LocalTime.of(18, 0));
        horario.setCerrado(false);

        when(horarioTiendaRepository.findAll()).thenReturn(List.of(horario));

        List<HorarioTiendaDTO.Response> resultado = horarioTiendaService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("LUNES", resultado.get(0).getDia_semana());
    }

    // BUSCAR POR ID (existe)
    @Test
    public void testBuscarPorId() {
        int id = 1;
        Tienda tienda = new Tienda();
        tienda.setId_tienda(1);

        HorarioTienda horario = new HorarioTienda();
        horario.setId_horario(id);
        horario.setTienda(tienda);
        horario.setDia_semana(HorarioTienda.DiaSemana.MARTES);
        horario.setHora_apertura(LocalTime.of(9, 0));
        horario.setHora_cierre(LocalTime.of(18, 0));
        horario.setCerrado(false);

        when(horarioTiendaRepository.findById(id)).thenReturn(Optional.of(horario));

        HorarioTiendaDTO.Response resultado = horarioTiendaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("MARTES", resultado.getDia_semana());
    }

    // BUSCAR POR ID (no existe) -> lanza excepcion
    @Test
    public void testBuscarPorId_noExiste() {
        int id = 99;
        when(horarioTiendaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> horarioTiendaService.buscarPorId(id));
    }

    // GUARDAR
    @Test
    public void testGuardar() {
        Tienda tienda = new Tienda();
        tienda.setId_tienda(1);

        HorarioTiendaDTO.Request request = new HorarioTiendaDTO.Request();
        request.setId_tienda(1);
        request.setDia_semana("miercoles");
        request.setHora_apertura(LocalTime.of(8, 30));
        request.setHora_cierre(LocalTime.of(17, 30));
        request.setCerrado(false);

        when(tiendaRepository.findById(1)).thenReturn(Optional.of(tienda));

        HorarioTienda horarioGuardado = new HorarioTienda();
        horarioGuardado.setId_horario(5);
        horarioGuardado.setTienda(tienda);
        horarioGuardado.setDia_semana(HorarioTienda.DiaSemana.MIERCOLES);
        horarioGuardado.setHora_apertura(LocalTime.of(8, 30));
        horarioGuardado.setHora_cierre(LocalTime.of(17, 30));
        horarioGuardado.setCerrado(false);

        when(horarioTiendaRepository.save(org.mockito.ArgumentMatchers.any(HorarioTienda.class)))
                .thenReturn(horarioGuardado);

        HorarioTiendaDTO.Response resultado = horarioTiendaService.guardar(request);

        assertNotNull(resultado);
        assertEquals("MIERCOLES", resultado.getDia_semana());
        assertEquals(5, resultado.getId_horario());
    }

    // GUARDAR (tienda no existe) -> lanza excepcion
    @Test
    public void testGuardar_tiendaNoExiste() {
        HorarioTiendaDTO.Request request = new HorarioTiendaDTO.Request();
        request.setId_tienda(99);
        request.setDia_semana("lunes");
        request.setHora_apertura(LocalTime.of(8, 30));
        request.setHora_cierre(LocalTime.of(17, 30));
        request.setCerrado(false);

        when(tiendaRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> horarioTiendaService.guardar(request));
    }

    // ACTUALIZAR
    @Test
    public void testActualizar() {
        int id = 1;
        Tienda tienda = new Tienda();
        tienda.setId_tienda(1);

        HorarioTienda horarioExistente = new HorarioTienda();
        horarioExistente.setId_horario(id);
        horarioExistente.setTienda(tienda);
        horarioExistente.setDia_semana(HorarioTienda.DiaSemana.LUNES);
        horarioExistente.setHora_apertura(LocalTime.of(9, 0));
        horarioExistente.setHora_cierre(LocalTime.of(18, 0));
        horarioExistente.setCerrado(false);

        when(horarioTiendaRepository.findById(id)).thenReturn(Optional.of(horarioExistente));
        when(tiendaRepository.findById(1)).thenReturn(Optional.of(tienda));

        HorarioTiendaDTO.Request request = new HorarioTiendaDTO.Request();
        request.setId_tienda(1);
        request.setDia_semana("viernes");
        request.setHora_apertura(LocalTime.of(10, 0));
        request.setHora_cierre(LocalTime.of(20, 0));
        request.setCerrado(true);

        when(horarioTiendaRepository.save(org.mockito.ArgumentMatchers.any(HorarioTienda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        HorarioTiendaDTO.Response resultado = horarioTiendaService.actualizar(id, request);

        assertNotNull(resultado);
        assertEquals("VIERNES", resultado.getDia_semana());
        assertEquals(true, resultado.getCerrado());
    }

    // ELIMINAR
    @Test
    public void testEliminar() {
        int id = 1;

        horarioTiendaService.eliminar(id);

        verify(horarioTiendaRepository).deleteById(id);
    }

    // BUSCAR POR TIENDA
    @Test
    public void testBuscarPorTienda() {
        int idTienda = 1;
        Tienda tienda = new Tienda();
        tienda.setId_tienda(idTienda);

        HorarioTienda horario = new HorarioTienda();
        horario.setId_horario(1);
        horario.setTienda(tienda);
        horario.setDia_semana(HorarioTienda.DiaSemana.JUEVES);
        horario.setHora_apertura(LocalTime.of(9, 0));
        horario.setHora_cierre(LocalTime.of(18, 0));
        horario.setCerrado(false);

        when(horarioTiendaRepository.findByTienda(idTienda)).thenReturn(List.of(horario));

        List<HorarioTiendaDTO.Response> resultado = horarioTiendaService.buscarPorTienda(idTienda);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("JUEVES", resultado.get(0).getDia_semana());
    }
}