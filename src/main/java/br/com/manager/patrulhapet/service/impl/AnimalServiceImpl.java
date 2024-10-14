package br.com.manager.patrulhapet.service.impl;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import br.com.manager.patrulhapet.exceptions.RestNotFound;
import br.com.manager.patrulhapet.repository.AnimalRepository;
import br.com.manager.patrulhapet.service.AnimalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {


    private final AnimalRepository repository;

    public AnimalServiceImpl(AnimalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Animal saveAnimal(Animal animal) {
        return repository.save(animal);
    }

    @Override
    public List<Animal> getAllAnimals() {
        return repository.findAll();
    }

    @Override
    public Animal updateAnimal(Animal animalUpdate) {
        this.getAnimalById(animalUpdate.getId());
        saveAnimal(animalUpdate);
        return animalUpdate;
    }

    @Override
    public Animal updateAnimalStatus(Long id) {
        Animal animal = this.getAnimalById(id);

        if (animal.getStatus() == AnimalStatus.ENABLED) {
            animal.setStatus(AnimalStatus.DISABLED);
        } else {
            animal.setStatus(AnimalStatus.ENABLED);
        }

        return this.updateAnimal(animal);
    }

    @Override
    public void deleteAnimal(Long id) {
        Animal animal = this.getAnimalById(id);

        repository.deleteById(animal.getId());
    }

    @Override
    public Animal getAnimalById(Long id) {
        Animal animal = repository.findById(id).orElse(null);

        if(animal == null) {
            throw new RestNotFound("Animal not found");
        }

        return animal;
    }

}
