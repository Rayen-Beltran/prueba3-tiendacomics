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

import com.comics.comic.DTO.EditorialDTO;
import com.comics.comic.assemblers.EditorialAssembler;
import com.comics.comic.model.Editorial;
import com.comics.comic.service.EditorialService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController("EditorialControllerV2")
@RequestMapping("/api/v2/editoriales")
@Slf4j
public class EditorialController2 {

    @Autowired
    private EditorialService editorialService;

    @Autowired
    private EditorialAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<EditorialDTO>>> todos() {
        List<EntityModel<EditorialDTO>> editoriales = editorialService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (editoriales.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                editoriales,
                linkTo(methodOn(EditorialController2.class).todos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EditorialDTO>> porId(@PathVariable Integer id) {
        try {
            EditorialDTO dto = editorialService.buscarPorId(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<EditorialDTO>> registrar(@Valid @RequestBody Editorial editorial) {
        try {
            Editorial savedEditorial = editorialService.guardarEditorial(editorial);
            EditorialDTO dto = editorialService.buscarPorId(savedEditorial.getId_editorial());
            return ResponseEntity
                    .created(linkTo(methodOn(EditorialController2.class).porId(dto.getId_editorial())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
