package com.example.MicroTiendaUbicacion.service.impl;

import com.example.MicroTiendaUbicacion.dto.RegionDTO.Request;
import com.example.MicroTiendaUbicacion.dto.RegionDTO.Response;

public interface RegionService {

    Response buscarPorId(int id);

    Response actualizar(int id, Request request);

    void eliminar(int id);

    Response crear(Request request);

}
