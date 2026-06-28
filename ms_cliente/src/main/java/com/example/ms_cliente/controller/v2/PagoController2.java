package com.example.ms_cliente.controller.v2;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ms_cliente.DTO.PagoDTO;
import com.example.ms_cliente.assemblers.PagoModelAssembler;
import com.example.ms_cliente.model.Pago;
import com.example.ms_cliente.service.PagoService;

import jakarta.validation.Valid;

@RestController("pagoControllerV2")
@RequestMapping("/api/v2/pago")
public class PagoController2 {

    @Autowired private PagoService pagoService;
    @Autowired private PagoModelAssembler assembler;

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoDTO>> porId(@PathVariable Integer id) {
        try {
            PagoDTO dto = pagoService.buscarPorId(id);
            if (dto == null) return ResponseEntity.notFound().build();
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/guardar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<PagoDTO>> registrar(@Valid @RequestBody Pago pago) {
        try {
            Pago guardado = pagoService.guardarPago(pago);
            PagoDTO dto = pagoService.buscarPorId(guardado.getId_pago());
            return ResponseEntity
                .created(linkTo(methodOn(PagoController2.class).porId(dto.getId_pago())).toUri())
                .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}