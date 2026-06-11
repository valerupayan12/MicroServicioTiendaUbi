package com.example.MicroTiendaUbicacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroTiendaUbicacion.entity.HorarioTienda;

@Repository
public interface HorarioTiendaRepository extends JpaRepository<HorarioTienda, Integer> {

    // Buscar horarios por tienda
    List<HorarioTienda> findByTiendaIdTienda(Integer id_tienda);

    // Buscar horarios abiertos
    List<HorarioTienda> findByCerradoFalse();

}