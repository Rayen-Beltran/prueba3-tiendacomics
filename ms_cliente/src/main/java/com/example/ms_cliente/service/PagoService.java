package com.example.ms_cliente.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ms_cliente.DTO.PagoDTO;
import com.example.ms_cliente.model.Pago;
import com.example.ms_cliente.repository.PagoRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;


    public PagoDTO buscarPorId(Integer id) {
        log.info("Buscando pago por ID: {}", id);
        Pago pago = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Pago no encontrado!"));
        return convertirADTO(pago);
    }

    public String eliminar(Integer id) {
        try {
            Pago pago = pagoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("¡Imposible eliminar! El pago con ID " + id + " no existe."));
            pagoRepository.delete(pago);
            log.info("Pago eliminado: {}", pago.getDescripcion());
            return "El pago '" + pago.getDescripcion() + "' ha sido eliminado exitosamente.";
        } catch (RuntimeException e) {
            log.error("Error al eliminar pago con ID: {}: {}", id, e.getMessage());
            return e.getMessage();
        }
    }

    public Pago guardarPago(Pago pago) {
        log.info("Guardando pago: {}", pago.getDescripcion());
        return pagoRepository.save(pago);
    }

    public Pago actualizarPago(Integer id, Pago pagoActualizado) {
        log.info("Actualizando pago: {}", pagoActualizado.getDescripcion());
        Pago pagoExistente = pagoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("¡Pago no encontrado!"));
        
            if(pagoActualizado.getDescripcion() != null) {
                pagoExistente.setDescripcion(pagoActualizado.getDescripcion());
            }
            if(pagoActualizado.getMonto_total() != null) {
                pagoExistente.setMonto_total(pagoActualizado.getMonto_total());
            }
        
            log.info("Pago actualizado: {}", pagoExistente.getDescripcion());
            return pagoRepository.save(pagoExistente);
    }


    private PagoDTO convertirADTO(Pago pago){
        PagoDTO pagoDTO = new PagoDTO();
        pagoDTO.setId_pago(pago.getId_pago());
        pagoDTO.setDescripcion(pago.getDescripcion());
        pagoDTO.setMonto_total(pago.getMonto_total());
        return pagoDTO;
    }

}
