package com.example.MicroTiendaUbicacion.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.MicroTiendaUbicacion.entity.Tienda;

@Repository
public interface TiendaRepository extends JpaRepository<Tienda, Integer> {

    // Buscar tiendas activas
    List<Tienda> findByActivaTrue();

    // Buscar por nombre
    List<Tienda> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por comuna
    List<Tienda> findByIdComuna(Integer id_comuna);

    // Buscar por región
    List<Tienda> findByIdRegion(Integer id_region);

    // Buscar tiendas activas de una región
    List<Tienda> findByIdRegionAndActivaTrue(Integer id_region);

}