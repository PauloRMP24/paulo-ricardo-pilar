package com.adocao.pet101.dto;

import com.adocao.pet101.entities.Animal;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AnimalDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    @Schema(description = "ID do animal", example = "5")
    private Long id;

    @NotBlank(message = "O nome do animal é obrigatório")
    @Schema(description = "Nome do animal", example = "Rex", required = true)
    private String nome;

    @Schema(description = "Idade do animal", example = "5", required = true)
    private String idade;

    @NotBlank(message = "A espécie do animal é obrigatória")
    @Schema(description = "Espécie do animal", example = "cachorro", required = true)
    private String especie;

    @NotBlank(message = "O peso do animal é obrigatório")
    @Schema(description = "Peso do animal", example = "10kg")
    private String peso;

    @Schema(description = "Descrição da situação do animal", example = "Resgatado da rua com várias feridas")
    private String descricao;

    @NotNull(message = "A disponibilidade do animal é obrigatória")
    @Schema(description = "Disponibilidade do animal para adoção", example = "true")
    private Boolean disponivel;

    @Schema(description = "ID do tutor responsável (se adotado)", example = "1")
    private Long tutorId;

    public AnimalDTO() {
    }

    public AnimalDTO(Long id, String nome, String idade, String especie, String peso, String descricao,
                     Boolean disponivel, Long tutorId) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.peso = peso;
        this.descricao = descricao;
        this.disponivel = disponivel;
        this.tutorId = tutorId;
    }

    public AnimalDTO(Animal animal) {
        this.id = animal.getId();
        this.nome = animal.getNome();
        this.idade = animal.getIdade();
        this.especie = animal.getEspecie();
        this.peso = animal.getPeso();
        this.descricao = animal.getDescricao();
        this.disponivel = animal.getDisponivel();
        this.tutorId = animal.getTutor() != null ? animal.getTutor().getId() : null;
    }

}
