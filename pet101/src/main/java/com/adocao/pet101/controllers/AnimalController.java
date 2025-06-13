package com.adocao.pet101.controllers;

import com.adocao.pet101.dto.AnimalDTO;
import com.adocao.pet101.services.AnimalService;
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

@RestController
@RequestMapping(value = "animal")
@Tag(name = "Animais", description = "Operações relacionadas aos animais")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Operation(summary = "Listar todos os animais")
    @GetMapping
    public ResponseEntity<Page<AnimalDTO>> findAllPaged(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
            ) {

        PageRequest pageRequest = PageRequest.of(
                page,
                linesPerPage,
                Sort.Direction.valueOf(direction.toUpperCase()),
                orderBy
        );

        Page<AnimalDTO> animais = animalService.findAllPaged(pageRequest);

        return ResponseEntity.ok().body(animais);
    }

    @Operation(summary = "Buscar animal por ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<AnimalDTO> findById(@PathVariable Long id) {
        AnimalDTO animalDTO = animalService.findById(id);

        return ResponseEntity.ok().body(animalDTO);
    }

    @Operation(summary = "Cadastrar um novo animal")
    @PostMapping
    public ResponseEntity<AnimalDTO> salvarAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        animalDTO = animalService.salvarAnimal(animalDTO);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(animalDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(animalDTO);
    }

    @Operation(summary = "Atualizar informações de um animal existente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<AnimalDTO> atualizarAnimal(@PathVariable Long id, @RequestBody AnimalDTO animalDTO) {
        animalDTO = animalService.atualizarAnimal(id, animalDTO);

        return ResponseEntity.ok().body(animalDTO);
    }

    @Operation(summary = "Deletar um animal por ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<AnimalDTO> deletarAnimal(@PathVariable Long id) {
        animalService.deletarAnimal(id);

        return ResponseEntity.noContent().build();
    }
}
