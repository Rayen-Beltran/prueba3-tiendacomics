package com.example.ms_cliente.DTO;


import java.time.LocalDate;

import lombok.Data;

@Data
public class EnvioDTO {

    private Integer idEnvio;
    private LocalDate fechaSalida;
    private LocalDate fechaEntrega;
    private String tipoEnvio;
    private String sucursal;


}
