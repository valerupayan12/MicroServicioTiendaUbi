package com.example.MicroTiendaUbicacion.dto;

import java.sql.Time;

import com.example.MicroTiendaUbicacion.model.Tienda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data 
@AllArgsConstructor
@NoArgsConstructor
public class HorarioTiendaDTO {

    @NotBlank(message = "el horario de tienda es obligatorio")
        @Size(min = 3, max = 10, message = "El ID debe tener entre 3 y 10 caracteres")
        private int id_horario_tienda;

    private int id_horario;
    private Tienda tienda;
    private String dia_semana;
    private Time hora_apertura;
    private Time hora_cierre;

}
