package br.com.manager.patrulhapet.service;


import br.com.manager.patrulhapet.domain.Animal;

import java.util.List;

public interface AnimalService {

    public Animal saveAnimal(Animal animal);

    public List<Animal> getAllAnimals();

    public Animal updateAnimal(Animal animal);

    public Animal updateAnimalStatus(Long id);

    public void deleteAnimal(Long id);

    public Animal getAnimalById(Long id);

    public void verifyAccess(Animal animal);
}
