package com.example.MicroTiendaUbicacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MicroTiendaUbicacion.dto.HorarioTiendaDTO;
import com.example.MicroTiendaUbicacion.model.HorarioTienda;
import com.example.MicroTiendaUbicacion.repository.HorarioTiendaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HorarioTiendaServiceimpl implements HorarioTiendaService {

    private final HorarioTiendaRepository horarioTiendaRepository;

    @Transactional(readOnly = true)
    public List<HorarioTiendaDTO.Response> listarTodos() {
        return horarioTiendaRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public HorarioTiendaDTO.Response buscarPorId(int id) {
        HorarioTienda h = horarioTiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de tienda no encontrado con id: " + id));
        return mapToResponse(h);
    }

    @Transactional
    public HorarioTiendaDTO.Response crear(HorarioTiendaDTO.Request request) {
        HorarioTienda h = new HorarioTienda();
        h.setId(request.getId());
        h.setHora_apertura(request.getHora_apertura());
        h.setHora_cierre(request.getHora_cierre());
        return mapToResponse(horarioTiendaRepository.save(h));
    }

    @Transactional
    public HorarioTiendaDTO.Response actualizar(int id, HorarioTiendaDTO.Request request) {
        HorarioTienda h = horarioTiendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Horario de tienda no encontrado con id: " + id));
        h.setHora_apertura(request.getHora_apertura());
        h.setHora_cierre(request.getHora_cierre());
        return mapToResponse(horarioTiendaRepository.save(h));
    }

    @Transactional
    public void eliminar(int id) {
        if (!horarioTiendaRepository.existsById(id))
            throw new RuntimeException("Horario de tienda no encontrado con id: " + id);
        horarioTiendaRepository.deleteById(id);
    }

    private HorarioTiendaDTO.Response mapToResponse(HorarioTienda h) {
        return new HorarioTiendaDTO.Response();
    }
}