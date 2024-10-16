package br.com.manager.patrulhapet.facade;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.domain.enumeration.AnimalCategory;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import br.com.manager.patrulhapet.facade.mapper.AnimalMapper;
import br.com.manager.patrulhapet.service.AnimalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AnimalFacadeTest {

    @Mock
    private AnimalMapper animalMapper;

    @Mock
    private AnimalService animalService;

    @InjectMocks
    private AnimalFacade animalFacade;

    private AnimalDTO animalDTO;
    private Animal animal;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        animalDTO = new AnimalDTO();
        animalDTO.setId(1L);
        animalDTO.setName("Dog");
        animalDTO.setImageUrl("....");
        animalDTO.setDescription("....");
        animalDTO.setCategory(AnimalCategory.DOG);
        animalDTO.setBirthDate(LocalDate.now());
        animalDTO.setStatus(AnimalStatus.ENABLED);

        animal = new Animal();
        animal.setId(1L);
        animal.setName("Dog");
        animal.setImageUrl("....");
        animal.setDescription("....");
        animal.setCategory(AnimalCategory.DOG);
        animal.setBirthDate(LocalDate.now());
        animal.setStatus(AnimalStatus.ENABLED);
    }

    @Test
    void create_ShouldReturnAnimalDTO() {
        when(animalMapper.toEntity(animalDTO)).thenReturn(animal);
        when(animalService.saveAnimal(animal)).thenReturn(animal);
        when(animalMapper.toDto(animal)).thenReturn(animalDTO);

        AnimalDTO response = animalFacade.create(animalDTO);

        assertEquals(animalDTO.getId(), response.getId());
        assertEquals(animalDTO.getName(), response.getName());
        assertEquals(animalDTO.getBirthDate(), response.getBirthDate());
        assertEquals(animalDTO.getCategory(), response.getCategory());
        assertEquals(animalDTO.getDescription(), response.getDescription());
        verify(animalMapper, times(1)).toEntity(animalDTO);
        verify(animalService, times(1)).saveAnimal(animal);
        verify(animalMapper, times(1)).toDto(animal);
    }

    @Test
    void getAllAnimals_ShouldReturnListOfAnimalDTOs() {
        List<Animal> animals = Collections.singletonList(animal);
        List<AnimalDTO> animalDTOs = Collections.singletonList(animalDTO);

        when(animalService.getAllAnimals()).thenReturn(animals);
        when(animalMapper.toDto(animals)).thenReturn(animalDTOs);

        List<AnimalDTO> response = animalFacade.getAllAnimals();

        assertEquals(1, response.size());
        assertEquals(animalDTO.getId(), response.get(0).getId());
        assertEquals(animalDTO.getName(), response.get(0).getName());
        assertEquals(animalDTO.getBirthDate(), response.get(0).getBirthDate());
        assertEquals(animalDTO.getCategory(), response.get(0).getCategory());
        assertEquals(animalDTO.getDescription(), response.get(0).getDescription());
        verify(animalService, times(1)).getAllAnimals();
        verify(animalMapper, times(1)).toDto(animals);
    }

    @Test
    void updateAnimal_ShouldReturnAnimalDTO() {
        when(animalMapper.toEntity(animalDTO)).thenReturn(animal);
        when(animalService.updateAnimal(animal)).thenReturn(animal);
        when(animalMapper.toDto(animal)).thenReturn(animalDTO);

        AnimalDTO response = animalFacade.updateAnimal(animalDTO);

        assertEquals(animalDTO.getId(), response.getId());
        assertEquals(animalDTO.getName(), response.getName());
        assertEquals(animalDTO.getBirthDate(), response.getBirthDate());
        assertEquals(animalDTO.getCategory(), response.getCategory());
        assertEquals(animalDTO.getDescription(), response.getDescription());
        verify(animalMapper, times(1)).toEntity(animalDTO);
        verify(animalService, times(1)).updateAnimal(animal);
        verify(animalMapper, times(1)).toDto(animal);
    }

    @Test
    void updateAnimalStatus_ShouldReturnStatusDISABLED() {
        animal.setStatus(AnimalStatus.DISABLED);
        when(animalService.updateAnimalStatus(animalDTO.getId())).thenReturn(animal);
        animalDTO.setStatus(AnimalStatus.DISABLED);
        when(animalMapper.toDto(animal)).thenReturn(animalDTO);

        AnimalDTO response = animalFacade.updateAnimalStatus(animalDTO.getId());

        assertEquals(animalDTO.getId(), response.getId());
        assertEquals(animalDTO.getName(), response.getName());
        assertEquals(animalDTO.getStatus(), AnimalStatus.DISABLED);
        verify(animalService, times(1)).updateAnimalStatus(animalDTO.getId());
        verify(animalMapper, times(1)).toDto(animal);
    }

    @Test
    void updateAnimalStatus_ShouldReturnStatusENABLED() {
        animal.setStatus(AnimalStatus.ENABLED);
        when(animalService.updateAnimalStatus(animalDTO.getId())).thenReturn(animal);
        animalDTO.setStatus(AnimalStatus.ENABLED);
        when(animalMapper.toDto(animal)).thenReturn(animalDTO);

        AnimalDTO response = animalFacade.updateAnimalStatus(animalDTO.getId());

        assertEquals(animalDTO.getId(), response.getId());
        assertEquals(animalDTO.getName(), response.getName());
        assertEquals(animalDTO.getStatus(), AnimalStatus.ENABLED);
        verify(animalService, times(1)).updateAnimalStatus(animalDTO.getId());
        verify(animalMapper, times(1)).toDto(animal);
    }

    @Test
    void deleteAnimal_ShouldInvokeServiceDelete() {
        Long animalId = animalDTO.getId();

        animalFacade.deleteAnimal(animalId);

        verify(animalService, times(1)).deleteAnimal(animalId);
    }
}