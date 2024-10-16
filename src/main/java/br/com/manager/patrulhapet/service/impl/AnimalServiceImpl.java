package br.com.manager.patrulhapet.service.impl;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import br.com.manager.patrulhapet.exceptions.RestAccessDeniedError;
import br.com.manager.patrulhapet.exceptions.RestNotFound;
import br.com.manager.patrulhapet.repository.AnimalRepository;
import br.com.manager.patrulhapet.service.AnimalService;
import br.com.manager.patrulhapet.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalServiceImpl implements AnimalService {


    private final AnimalRepository repository;
    private final UserService userService;

    public AnimalServiceImpl(AnimalRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public Animal saveAnimal(Animal animal) {
        User user = this.userService.getMe();
        animal.setUser(user);
        return repository.save(animal);
    }

    @Override
    public List<Animal> getAllAnimals() {
        User user = this.userService.getMe();
        return repository.findByUser(user);
    }

    @Override
    public Animal updateAnimal(Animal animalUpdate) {
        Animal existingAnimal = this.getAnimalById(animalUpdate.getId());

        return saveAnimal(animalUpdate);
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
        this.verifyAccess(animal);

        return animal;
    }

    @Override
    public void verifyAccess(Animal animal) {
        User user = this.userService.getMe();

        if (!animal.getUser().equals(user)) {
            throw new RestAccessDeniedError("You do not have permission to update this animal.");
        }
    }

}
