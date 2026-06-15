package com.example.ms_cliente.DTO;


import lombok.Data;

@Data
public class ClienteDTO {
    public Integer id_cliente;
    public String nombre;
    public String apellido;
    public Integer rut;
    public String correo;
    public String direccion;
    public Integer telefono;
    public String Dv;


}