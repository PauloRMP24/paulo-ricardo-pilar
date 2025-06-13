package com.adocao.pet101.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Table(name="animais")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String especie;
    private String idade;
    private String peso;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Boolean disponivel = true;

    @ManyToOne
    @JoinColumn(name = "tutor_id")
    private Tutor tutor;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id) && Objects.equals(nome, animal.nome) && Objects.equals(especie, animal.especie)
                && Objects.equals(idade, animal.idade) && Objects.equals(peso, animal.peso)
                && Objects.equals(descricao, animal.descricao) && Objects.equals(disponivel, animal.disponivel)
                && Objects.equals(tutor, animal.tutor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, especie, idade, peso, descricao, disponivel, tutor);
    }
}
