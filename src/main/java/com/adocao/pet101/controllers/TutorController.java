package com.adocao.pet101.controllers;

import com.adocao.pet101.dto.TutorDTO;
import com.adocao.pet101.services.TutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "tutor")
@Tag(name = "Tutores", description = "Operações relacionadas aos tutores")
public class TutorController {

    @Autowired
    private TutorService tutorService;

    @Operation(summary = "Listar todos os tutores")
    @GetMapping
    public ResponseEntity<Page<TutorDTO>> findAllPaged(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPrePage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
            ) {

        PageRequest pageRequest = PageRequest.of(
                page,
                linesPrePage,
                Sort.Direction.valueOf(direction.toUpperCase()),
                orderBy
        );

        Page<TutorDTO> tutores = tutorService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(tutores);
    }

    @Operation(summary = "Buscar tutor por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<TutorDTO> findById(@PathVariable Long id) {
        TutorDTO tutorDTO = tutorService.findById(id);

        return ResponseEntity.ok().body(tutorDTO);
    }

    @Operation(summary = "Cadastrar um novo tutor")
    @PostMapping
    public ResponseEntity<TutorDTO> salvarTutor(@Valid @RequestBody TutorDTO tutorDTO) {
        tutorDTO = tutorService.salvarTutor(tutorDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(tutorDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(tutorDTO);
    }

    @Operation(summary = "Atualizar um tutor existente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<TutorDTO> atualizarTutor(@PathVariable Long id,@Valid @RequestBody TutorDTO tutorDTO) {
        tutorDTO = tutorService.atualizarTutor(id, tutorDTO);

        return ResponseEntity.ok().body(tutorDTO);
    }

    @Operation(summary = "Excluir tutor por ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<TutorDTO> deletarTutor(@PathVariable Long id) {
        tutorService.deletarTutor(id);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adotar um ou mais animais")
    @PostMapping(value = "/{id}/adotar")
    public ResponseEntity<TutorDTO> adotarAnimal(@PathVariable Long id, @RequestBody List<Long> idAnimal) {
        TutorDTO tutorDTO = tutorService.adotarAnimal(id, idAnimal);

        return ResponseEntity.ok().body(tutorDTO);
    }
}
