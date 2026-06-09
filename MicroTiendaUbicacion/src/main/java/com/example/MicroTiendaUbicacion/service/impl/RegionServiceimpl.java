package com.example.MicroTiendaUbicacion.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.MicroTiendaUbicacion.dto.RegionDTO;
import com.example.MicroTiendaUbicacion.dto.RegionDTO.Response;
import com.example.MicroTiendaUbicacion.repository.RegionRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.spi.RegisterableService;
import javax.swing.plaf.synth.Region;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegionServiceimpl  implements RegionService {

    private final RegionRepository regionRepository;

    @Transactional(readOnly = true)
    public List<RegionDTO.Response> listarTodos() {
        return regionRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    

    @Transactional
    public RegionDTO.Response crear(RegionDTO.Request request) {
        com.example.MicroTiendaUbicacion.model.Region r = new com.example.MicroTiendaUbicacion.model.Region();
        r.setId(request.getId());
        r.setNombre(request.getNombre());
        return mapToResponse(regionRepository.save(r));
    }

    private Response mapToResponse(com.example.MicroTiendaUbicacion.model.Region save) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mapToResponse'");
    }

    @Transactional
    public RegionDTO.Response actualizar(int id, RegionDTO.Request request) {
        com.example.MicroTiendaUbicacion.model.Region r = regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + id));
        r.setId(request.getId());
        r.setNombre(request.getNombre());
        return mapToResponse(regionRepository.save(r));
    }

    @Transactional
    public void eliminar(int id) {
        if (!regionRepository.existsById(id))
            throw new RuntimeException("Región no encontrada con id: " + id);
        regionRepository.deleteById(id);
    }

    private RegionDTO.Response mapToResponse(Region r) {
        return new RegionDTO.Response();
    }
}