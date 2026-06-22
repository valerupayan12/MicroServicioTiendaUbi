package com.example.MicroTiendaUbicacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.MicroTiendaUbicacion.entity.Tienda;
import com.example.MicroTiendaUbicacion.repository.TiendaRepository;
import com.example.MicroTiendaUbicacion.service.TiendaService;

import antlr.collections.List;

@SpringBootTest
public class TiendaServiceTest {

    @Autowired
    private TiendaService tiendaService;

    @Mock
    private TiendaRepository tiendaRepository;

    @Test
    public void testFindAll() {
        when(tiendaRepository.findAll()).thenReturn(List.of(new Tienda("1", "Tienda A")));

        List<Tienda> tiendas = tiendaService.findAll();
        assertNotNull(tiendas);
        assertEquals(1, tiendas.size());
    }

    @Test
    public void testFindById() {
        Integer id = 1;
        Tienda tienda = new Tienda(id, "Tienda A");
        when(tiendaRepository.findById(id)).thenReturn(Optional.of(tienda));

        Tienda found = tiendaService.findById(id);
        assertNotNull(found);
        assertEquals(id, found.getCodigo());
    }

    @Test
    public void testSave() {
        Tienda tienda = new Tienda("1", "Tienda A");
        when(tiendaRepository.save(tienda)).thenReturn(tienda);

        Tienda saved = tiendaService.save(tienda);
        assertNotNull(saved);
        assertEquals("Tienda A", saved.getNombre());
    }

    @Test
    public void testDeleteById() {
        Integer id = 1;
        doNothing().when(tiendaRepository).deleteById(id);

        tiendaService.deleteById(id);
        verify(tiendaRepository, times(1)).deleteById(id);
    }
}


}
