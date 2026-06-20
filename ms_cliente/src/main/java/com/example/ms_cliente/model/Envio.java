package com.example.ms_cliente.model;
    
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "envios")
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEnvio;

    @NotNull
    @Column(nullable = false, length = 10)
    private LocalDate fechaSalida;

    @NotNull(message = "La fecha de entrega es obligatoria")
    @Column(nullable = false, length = 10)
    private LocalDate fechaEntrega;

    //fk id pago
    @OneToOne
    @JoinColumn(name = "id_pago")
    private Pago pago;

    @Size(min = 3, max = 20, message = "El tipo de envio debe tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 20)
    private String tipoEnvio;

    @Size(min = 3, max = 20, message = "La Sucursal debe tener entre 3 y 20 caracteres")
    @Column(nullable = false, length = 20)
    private String sucursal;

}