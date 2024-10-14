package br.com.manager.patrulhapet.facade;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import br.com.manager.patrulhapet.facade.mapper.AnimalMapper;
import br.com.manager.patrulhapet.service.AnimalService;
import br.com.manager.patrulhapet.service.impl.AnimalServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimalFacade {

    private final AnimalMapper animalMapper;
    private final AnimalService service;

    public AnimalFacade(AnimalMapper animalMapper, AnimalService animalService) {
        this.animalMapper = animalMapper;
        this.service = animalService;
    }

    @Transactional
    public AnimalDTO create(AnimalDTO animalDTO) {
        Animal animal = animalMapper.toEntity(animalDTO);
        return animalMapper.toDto(service.saveAnimal(animal));
    };

    @Transactional
    public List<AnimalDTO> getAllAnimals() {
        return animalMapper.toDto(service.getAllAnimals());
    };

    @Transactional
    public AnimalDTO updateAnimal(AnimalDTO animalDTO) {
        return animalMapper.toDto(service.updateAnimal(animalMapper.toEntity(animalDTO)));
    };

    @Transactional
    public AnimalDTO updateAnimalStatus(Long id) {
        return animalMapper.toDto(service.updateAnimalStatus(id));
    };

    @Transactional
    public void deleteAnimal(Long animalId) {
        service.deleteAnimal(animalId);
    };
}
