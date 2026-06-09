package com.example.MicroTiendaUbicacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MicroTiendaUbicacion.dto.TiendaDTO;
import com.example.MicroTiendaUbicacion.model.Tienda;
import com.example.MicroTiendaUbicacion.repository.TiendaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TiendaServiceimpl  implements TokenService {

    private final TiendaRepository tiendaRepository;

    @Transactional(readOnly = true)
    public List<TiendaDTO.Response> listarTodos() {
        return tiendaRepository.findAll().stream().max(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TiendaDTO.Response buscarPorId(int id) {
        Tienda t = tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con id: " + id));
        return mapToResponse(t);
    }

    @Transactional
    public TiendaDTO.Response crear(TiendaDTO.Request request) {
        Tienda t = new Tienda();
        t.setId(request.getId());
        t.setNombre(request.getNombre());
        return mapToResponse(tiendaRepository.save(t));
    }

    @Transactional
    public TiendaDTO.Response actualizar(int id, TiendaDTO.Request request) {
        Tienda t = tiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada con id: " + id));
        t.setId(request.getId());
        t.setNombre(request.getNombre());
        return mapToResponse(tiendaRepository.save(t));
    }

    @Transactional
    public void eliminar1(int id) {
        if (!tiendaRepository.existsById(id))
            throw new RuntimeException("Tienda no encontrada con id: " + id);
        tiendaRepository.deleteById(id);
    }

    private TiendaDTO.Response mapToResponse(Tienda t) {
        return new TiendaDTO.Response(
                t.getId(), t.getNombre());
    }
}