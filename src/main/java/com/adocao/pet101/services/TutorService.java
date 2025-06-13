package com.adocao.pet101.services;

import com.adocao.pet101.dto.TutorDTO;
import com.adocao.pet101.entities.Animal;
import com.adocao.pet101.entities.Tutor;
import com.adocao.pet101.repositories.AnimalRepository;
import com.adocao.pet101.repositories.TutorRepository;
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

import java.util.List;
import java.util.Optional;

@Service
public class TutorService {

    @Autowired
    private TutorRepository tutorRepository;

    @Autowired
    private AnimalRepository animalRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(TutorService.class);

    @Transactional(readOnly = true)
    public Page<TutorDTO> findAllPaged(PageRequest pageRequest) {
        Page<Tutor> tutores = tutorRepository.findAll(pageRequest);

        return tutores.map(x -> new TutorDTO(x));
    }

    @Transactional(readOnly = true)
    public TutorDTO findById(Long id) {
        Optional<Tutor> obj = tutorRepository.findById(id);
        Tutor tutor = obj.orElseThrow(() -> new ResourceNotFoundException("Recurso não encontrado"));

        return new TutorDTO(tutor);
    }

    @Transactional
    public TutorDTO salvarTutor(TutorDTO tutorDTO) {
        Tutor tutor = new Tutor();

        tutor.setNome(tutorDTO.getNome());
        tutor.setCpf(tutorDTO.getCpf());
        tutor.setIdade(tutorDTO.getIdade());
        tutor.setEndereco(tutorDTO.getEndereco());
        tutor = tutorRepository.save(tutor);

        return new TutorDTO(tutor);
    }

    @Transactional
    public TutorDTO atualizarTutor(Long id, TutorDTO tutorDTO){
        Tutor tutor = tutorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id não encontrado: "+ id));

        tutor.setNome(tutorDTO.getNome());
        tutor.setCpf(tutorDTO.getCpf());
        tutor.setIdade(tutorDTO.getIdade());
        tutor.setEndereco(tutorDTO.getEndereco());

        return new TutorDTO(tutor);
    }

    public void deletarTutor(Long id) {
        LOGGER.info("Entrou no método deletarTutor com o id: {}", id);

        if(!tutorRepository.existsById(id)) {
            LOGGER.warn("Tutor com id {} não existe!", id);
            throw new ResourceNotFoundException("Id não encontrado: "+id);
        }

        try {
            tutorRepository.deleteById(id);
            LOGGER.info("Animal com id {} deletada com sucesso!", id);
        }catch (DataIntegrityViolationException e) {
            LOGGER.error("Erro de integridade ao excluir tutor com id {} : {}", id, e.getMessage());
            throw new DatabaseException("Não foi possível excluir o tutor. Ele pode estar vinculado a outros registros.");
        }
    }

    @Transactional
    public TutorDTO adotarAnimal(Long idTutor, List<Long> idAnimal) {
        Tutor tutor = tutorRepository.findById(idTutor)
                .orElseThrow(() -> new ResourceNotFoundException("Tutor não encontrado com id"));

        List<Animal> animaisParaAdotar = animalRepository.findAllById(idAnimal);

        for (Animal animal : animaisParaAdotar) {
            if (Boolean.FALSE.equals(animal.getDisponivel())) {
                throw new IllegalStateException("O animal com id " +animal.getId() + "não está disponível para adoção.");
            }
            animal.setTutor(tutor);
            animal.setDisponivel(false); //atualiza status
        }

        animalRepository.saveAll(animaisParaAdotar);

        return new TutorDTO(tutor);
    }
}
