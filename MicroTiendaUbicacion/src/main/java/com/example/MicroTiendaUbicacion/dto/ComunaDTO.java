package com.example.MicroTiendaUbicacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComunaDTO {
    private int  id;
    private String nombre;
    public class Response {
    }
    public class Request {

        public String getNombre() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getNombre'");
        }

        public Object getId() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'getId'");
        }
    }

}
