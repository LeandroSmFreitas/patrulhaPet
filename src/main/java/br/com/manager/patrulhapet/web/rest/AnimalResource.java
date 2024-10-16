package br.com.manager.patrulhapet.web.rest;

import br.com.manager.patrulhapet.facade.AnimalFacade;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
@SecurityRequirement(name = "bearerAuth")
public class AnimalResource {


    private final AnimalFacade facade;

    public AnimalResource(AnimalFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/animals")
    @Operation(description = "create animal")
    public ResponseEntity<AnimalDTO> createAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO createdAnimalDTO = facade.create(animalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAnimalDTO);
    };

    @Operation(description = "get all animals")
    @GetMapping("/animals")
    public ResponseEntity<List<AnimalDTO>> getAll() {
        List<AnimalDTO> animals = facade.getAllAnimals();
        return ResponseEntity.ok().body(animals);
    };

    @Operation(description = "update animal")
    @PutMapping("/animals")
    public ResponseEntity<AnimalDTO> updateAnimal(@Valid @RequestBody AnimalDTO animalDTO) {
        AnimalDTO animal = facade.updateAnimal(animalDTO);
        return ResponseEntity.ok().body(animal);
    };

    @Operation(description = "update status of the animal")
    @PatchMapping("/animals/{id}/status")
    public ResponseEntity<AnimalDTO> updateAnimalStatus(@PathVariable("id") Long id) {
        AnimalDTO animal = facade.updateAnimalStatus(id);
        return ResponseEntity.ok().body(animal);
    };

    @Operation(description = "delete animal")
    @DeleteMapping("/animals/{id}/delete")
    public ResponseEntity<Void> deleteAnimal(@PathVariable("id") Long id) {
        facade.deleteAnimal(id);
        return ResponseEntity.ok().build();
    };
}
