package com.adocao.pet101.services;

import com.adocao.pet101.dto.AnimalDTO;
import com.adocao.pet101.entities.Animal;
import com.adocao.pet101.repositories.AnimalRepository;
import com.adocao.pet101.services.exceptions.DatabaseException;
import com.adocao.pet101.services.exceptions.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AnimalService {

    @Autowired
    private AnimalRepository animalRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimalService.class);

    @Transactional(readOnly = true)
    public Page<AnimalDTO> findAllPaged(PageRequest pageRequest) {
        Page<Animal> animais = animalRepository.findAll(pageRequest);

        return animais.map(x -> new AnimalDTO(x));
    }

    @Transactional(readOnly = true)
    public AnimalDTO findById(Long id) {
        Optional<Animal> obj = animalRepository.findById(id);
        Animal animal = obj.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado."));

        return new AnimalDTO(animal);
    }

    @Transactional
    public AnimalDTO salvarAnimal(AnimalDTO animalDTO) {
        Animal animal = new Animal();

        animal.setNome(animalDTO.getNome());
        animal.setIdade(animalDTO.getIdade());
        animal.setPeso(animalDTO.getPeso());
        animal.setEspecie(animalDTO.getEspecie());
        animal.setDescricao(animalDTO.getDescricao());
        animal.setDisponivel(animalDTO.getDisponivel());
        animal.setTutor(animal.getTutor());
        animal = animalRepository.save(animal);

        return new AnimalDTO(animal);
    }

    @Transactional
    public AnimalDTO atualizarAnimal(Long id, AnimalDTO animalDTO) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: "+ id));

        animal.setNome(animalDTO.getNome());
        animal.setIdade(animalDTO.getIdade());
        animal.setPeso(animalDTO.getPeso());
        animal.setEspecie(animalDTO.getEspecie());
        animal.setDescricao(animalDTO.getDescricao());
        animal.setDisponivel(animalDTO.getDisponivel());
        animal.setTutor(animal.getTutor());

        return new AnimalDTO(animal);
    }


    public void deletarAnimal(Long id) {
        LOGGER.info("Entrou no método deletarAnimal com o id: {}", id);

        if(!animalRepository.existsById(id)) {
            LOGGER.warn("Animal com id {} não existe!", id);
            throw new ResourceNotFoundException("Id não encontrado: "+id);
        }

        try {
            animalRepository.deleteById(id);
            LOGGER.info("Animal com id {} deletada com sucesso!", id);
        }catch (DataIntegrityViolationException e) {
            LOGGER.error("Erro de integridade ao excluir animal com id {} : {}", id, e.getMessage());
            throw new DatabaseException("Não foi possível excluir o animal. Ele pode estar vinculado a outros registros.");
        }
    }
}
