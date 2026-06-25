package com.example.MicroTiendaUbicacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.example.MicroTiendaUbicacion.dto.TiendaDTO;
import com.example.MicroTiendaUbicacion.entity.Tienda;
import com.example.MicroTiendaUbicacion.repository.TiendaRepository;
import com.example.MicroTiendaUbicacion.service.TiendaService;

@SpringBootTest
public class TiendaServiceTest {

    @Autowired
    private TiendaService tiendaService;

    @MockitoBean
    private TiendaRepository tiendaRepository;

    // LISTAR
    @Test
    public void testListar() {
        Tienda tienda = new Tienda();
        tienda.setId_tienda(1);
        tienda.setNombre("Tienda A");

        when(tiendaRepository.findAll()).thenReturn(List.of(tienda));

        List<TiendaDTO.Response> resultado = tiendaService.listar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Tienda A", resultado.get(0).getNombre());
    }

    // BUSCAR POR ID (existe)
    @Test
    public void testBuscarPorId() {
        int id = 1;
        Tienda tienda = new Tienda();
        tienda.setId_tienda(id);
        tienda.setNombre("Tienda A");

        when(tiendaRepository.findById(id)).thenReturn(Optional.of(tienda));

        TiendaDTO.Response resultado = tiendaService.buscarPorId(id);

        assertNotNull(resultado);
        assertEquals("Tienda A", resultado.getNombre());
    }

    // BUSCAR POR ID (no existe) -> lanza excepcion
    @Test
    public void testBuscarPorId_noExiste() {
        int id = 99;
        when(tiendaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> tiendaService.buscarPorId(id));
    }

    // GUARDAR
    @Test
    public void testGuardar() {
        TiendaDTO.Request request = new TiendaDTO.Request();
        request.setNombre("Tienda Nueva");
        request.setDireccion("Av. Siempre Viva 123");
        request.setTelefono("123456789");
        request.setId_comuna(1);
        request.setId_region(1);
        request.setCodigo_postal("1234567");
        request.setActiva(true);

        Tienda tiendaGuardada = new Tienda();
        tiendaGuardada.setId_tienda(10);
        tiendaGuardada.setNombre("Tienda Nueva");
        tiendaGuardada.setDireccion("Av. Siempre Viva 123");
        tiendaGuardada.setTelefono("123456789");
        tiendaGuardada.setIdComuna(1);
        tiendaGuardada.setIdRegion(1);
        tiendaGuardada.setCodigo_postal("1234567");
        tiendaGuardada.setActiva(true);

        when(tiendaRepository.save(org.mockito.ArgumentMatchers.any(Tienda.class)))
                .thenReturn(tiendaGuardada);

        TiendaDTO.Response resultado = tiendaService.guardar(request);

        assertNotNull(resultado);
        assertEquals(10, resultado.getId_tienda());
        assertEquals("Tienda Nueva", resultado.getNombre());
    }

    // ACTUALIZAR (existe)
    @Test
    public void testActualizar() {
        int id = 1;
        Tienda tiendaExistente = new Tienda();
        tiendaExistente.setId_tienda(id);
        tiendaExistente.setNombre("Tienda Vieja");

        when(tiendaRepository.findById(id)).thenReturn(Optional.of(tiendaExistente));
        when(tiendaRepository.save(org.mockito.ArgumentMatchers.any(Tienda.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        TiendaDTO.Request request = new TiendaDTO.Request();
        request.setNombre("Tienda Actualizada");
        request.setDireccion("Nueva Direccion 456");
        request.setTelefono("987654321");
        request.setId_comuna(2);
        request.setId_region(2);
        request.setCodigo_postal("7654321");
        request.setActiva(false);

        TiendaDTO.Response resultado = tiendaService.actualizar(id, request);

        assertNotNull(resultado);
        assertEquals("Tienda Actualizada", resultado.getNombre());
        assertEquals(false, resultado.getActiva());
    }

    // ACTUALIZAR (no existe) -> lanza excepcion
    @Test
    public void testActualizar_noExiste() {
        int id = 99;
        when(tiendaRepository.findById(id)).thenReturn(Optional.empty());

        TiendaDTO.Request request = new TiendaDTO.Request();
        request.setNombre("Tienda Actualizada");

        assertThrows(RuntimeException.class, () -> tiendaService.actualizar(id, request));
    }

    // ELIMINAR
    @Test
    public void testEliminar() {
        int id = 1;

        tiendaService.eliminar(id);

        verify(tiendaRepository).deleteById(id);
    }
}