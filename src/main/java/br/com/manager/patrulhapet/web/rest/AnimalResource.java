package br.com.manager.patrulhapet.web.rest;

import br.com.manager.patrulhapet.facade.AnimalFacade;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class AnimalResource {


    private final AnimalFacade facade;

    public AnimalResource(AnimalFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/animals")
    public ResponseEntity<AnimalDTO> createAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO createdAnimalDTO = facade.create(animalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimalDTO);
    };

    @GetMapping("/animals")
    public ResponseEntity<List<AnimalDTO>> getAll() {
        List<AnimalDTO> animals = facade.getAllAnimals();
        return ResponseEntity.ok().body(animals);
    };

    @PutMapping("/animals")
    public ResponseEntity<AnimalDTO> updateAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO animal = facade.updateAnimal(animalDTO);
        return ResponseEntity.ok().body(animal);
    };

    @PatchMapping("/animals/{id}/status")
    public ResponseEntity<AnimalDTO> updateAnimalStatus(@PathVariable("id") Long id) {
        AnimalDTO animal = facade.updateAnimalStatus(id);
        return ResponseEntity.ok().body(animal);
    };

    @DeleteMapping("/animals/{id}/delete")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("id") Long id) {
        facade.deleteAnimal(id);
        return ResponseEntity.ok().build();
    };
}
