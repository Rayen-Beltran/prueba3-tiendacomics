package com.comics.comic.controller.V2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import com.comics.comic.DTO.AutorDTO;
import com.comics.comic.assemblers.AutorAssembler;
import com.comics.comic.model.Autor;
import com.comics.comic.service.AutorService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("AutorControllerV2")
@RequestMapping("/api/v2/autores")
@Slf4j
public class AutorController2 {

    @Autowired
    private AutorService autorService;

    @Autowired
    private AutorAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<AutorDTO>>> todos() {
        List<EntityModel<AutorDTO>> autores = autorService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (autores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                autores,
                linkTo(methodOn(AutorController2.class).todos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AutorDTO>> porId(@PathVariable Integer id) {
        try {
            AutorDTO dto = autorService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<AutorDTO>> registrar(@Valid @RequestBody Autor autor) {
        try {
            Autor savedAutor = autorService.guardarAutor(autor);
            AutorDTO dto = autorService.buscarPorId(savedAutor.getId_autor());
            return ResponseEntity
                    .created(linkTo(methodOn(AutorController2.class).porId(dto.getId_autor())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
