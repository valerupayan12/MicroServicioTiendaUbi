package com.example.MicroTiendaUbicacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.jspecify.annotations.Nullable;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MicroTiendaUbicacion.dto.ComunaDTO;
import com.example.MicroTiendaUbicacion.model.Comuna;
import com.example.MicroTiendaUbicacion.repository.ComunaRepository;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComunaServiceimpl implements ConversionService {

    private final ComunaRepository comunaRepository;

    @Transactional(readOnly = true)
    public List<ComunaDTO.Response> listarTodos() {
        return comunaRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ComunaDTO.Response buscarPorId(int id) {
        Comuna c = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con id: " + id));
        return mapToResponse(c);
    }

    @Transactional
    public ComunaDTO.Response crear(ComunaDTO.Request request) {
        Comuna c = new Comuna();
        c.setId(request.getId());
        c.setNombre(request.getNombre());
        return mapToResponse(comunaRepository.save(c));
    }

    @Transactional
    public ComunaDTO.Response actualizar(int id, ComunaDTO.Request request) {
        Comuna c = comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con id: " + id));
        c.setNombre(request.getNombre());
        return mapToResponse(comunaRepository.save(c));
    }

    @Transactional
    public void eliminar1(int id) {
        if (!comunaRepository.existsById(id))
            throw new RuntimeException("Comuna no encontrada con id: " + id);
        comunaRepository.deleteById(id);
    }

    private ComunaDTO.Response mapToResponse(Comuna c) {
        return new ComunaDTO.Response();
    }

    @Override
    public boolean canConvert(@Nullable Class<?> arg0, Class<?> arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canConvert'");
    }

    @Override
    public boolean canConvert(@Nullable TypeDescriptor arg0, TypeDescriptor arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'canConvert'");
    }

    @Override
    public <T> @Nullable T convert(@Nullable Object arg0, Class<T> arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }

    @Override
    public @Nullable Object convert(@Nullable Object arg0, @Nullable TypeDescriptor arg1, TypeDescriptor arg2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'convert'");
    }
}
