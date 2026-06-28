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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.comics.comic.DTO.ComicDTO;
import com.comics.comic.assemblers.ComicAssembler;
import com.comics.comic.model.Comic;
import com.comics.comic.service.ComicService;

@RestController("ComicControllerV2")
@RequestMapping("/api/v2/comics")
@Slf4j
public class ComicController2 {

    @Autowired
    private ComicService comicService;

    @Autowired
    private ComicAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<ComicDTO>>> todos() {
        List<EntityModel<ComicDTO>> comics = comicService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        if (comics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(CollectionModel.of(
                comics,
                linkTo(methodOn(ComicController2.class).todos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComicDTO>> porId(@PathVariable Integer id) {
        try {
            ComicDTO dto = comicService.findById(id);
            if (dto == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(assembler.toModel(dto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<ComicDTO>> registrar(@Valid @RequestBody Comic comic) {
        try {
            Comic savedComic = comicService.guardarComic(comic);
            ComicDTO dto = comicService.findById(savedComic.getId());
            return ResponseEntity
                    .created(linkTo(methodOn(ComicController2.class).porId(dto.getId_comic())).toUri())
                    .body(assembler.toModel(dto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
