package com.example.ms_cliente.DTO;

import lombok.Data;

@Data
public class PagoDTO {
    private Integer id_pago;
    private String descripcion;
    private Integer Monto_total;
}
