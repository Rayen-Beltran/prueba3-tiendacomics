package com.example.ms_cliente.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pago")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pago;

   /*  @ManyToOne
    @JoinColumn(name = "id_comic")
    private Comic comic; */

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

   /*  @ManyToOne
    @JoinColumn(name = "id_empleado")
    private Empleado empleado;*/


    private String descripcion;

    @NotNull(message = "El Monto total de su compra debe ser obligatorio")
    @Min(value = 1000, message = "El monto total debe tener un min. de 1000 pesos")
    @Column(nullable = false)
    private Integer Monto_total;

   /*  @ManyToOne
    @JoinColumn(name = "id_tienda")
    private Tienda tienda;*/
    
}
