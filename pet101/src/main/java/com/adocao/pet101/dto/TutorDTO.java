package com.adocao.pet101.dto;

import com.adocao.pet101.entities.Tutor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TutorDTO {

    @Schema(description = "ID do tutor", example = "1")
    private Long id;

    @NotBlank(message = "O nome do Tutor é obrigatório")
    @Schema(description = "Nome completo do tutor", example = "Maria Silva", required = true)
    private String nome;

    @NotBlank(message = "O CPF do Tutor é obrigatório")
    @Schema(description = "CPF do tutor", example = "123.456.789-00")
    private String cpf;

    @NotBlank(message = "A idade do Tutor é obrigatória")
    @Schema(description = "Idade do tutor", example = "27")
    private String idade;

    @NotBlank(message = "O endereço do Tutor é obrigatório")
    @Schema(description = "Endereço do tutor", example = "Rua Ator Paulo Gustavo, 350")
    private String endereco;

    @Schema(description = "Lista de IDs dos animais adotados")
    private List<AnimalDTO> animais;

    public TutorDTO() {}

    public TutorDTO(Long id, String nome, String cpf, String idade, String endereco, List<AnimalDTO> animais) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
        this.endereco = endereco;
        this.animais = animais;
    }

    public TutorDTO(Tutor tutor) {
        this.id = tutor.getId();
        this.nome = tutor.getNome();
        this.idade = tutor.getIdade();
        this.endereco = tutor.getEndereco();
        if(tutor.getAnimais() != null) {
            this.animais = tutor.getAnimais().stream()
                    .map(AnimalDTO::new)
                    .collect(Collectors.toList());
        }
    }
}
