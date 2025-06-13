package com.adocao.pet101.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="tutores")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tutor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;
    private String idade;

    @Column(columnDefinition = "TEXT")
    private String endereco;

    @OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Animal> animais;
}
