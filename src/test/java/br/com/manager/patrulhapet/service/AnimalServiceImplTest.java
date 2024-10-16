package br.com.manager.patrulhapet.service;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import br.com.manager.patrulhapet.exceptions.RestAccessDeniedError;
import br.com.manager.patrulhapet.exceptions.RestNotFound;
import br.com.manager.patrulhapet.repository.AnimalRepository;
import br.com.manager.patrulhapet.service.UserService;
import br.com.manager.patrulhapet.service.impl.AnimalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class AnimalServiceImplTest {

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private UserService userService;

    @InjectMocks
    private AnimalServiceImpl animalService;

    private User testUser;
    private Animal testAnimal;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testAnimal = new Animal();
        testAnimal.setUser(testUser);
    }

    @Test
    public void testSaveAnimal() {
        when(userService.getMe()).thenReturn(testUser);
        when(animalRepository.save(any(Animal.class))).thenReturn(testAnimal);

        Animal savedAnimal = animalService.saveAnimal(testAnimal);

        assertNotNull(savedAnimal);
        assertEquals(testUser, savedAnimal.getUser());
        verify(animalRepository).save(testAnimal);
    }

    @Test
    public void testGetAllAnimals() {
        when(userService.getMe()).thenReturn(testUser);
        when(animalRepository.findByUser(testUser)).thenReturn(List.of(testAnimal));

        List<Animal> animals = animalService.getAllAnimals();

        assertNotNull(animals);
        assertEquals(1, animals.size());
        assertEquals(testAnimal, animals.get(0));
    }

    @Test
    public void testUpdateAnimal() {
        testAnimal.setId(1L);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(testAnimal));
        when(userService.getMe()).thenReturn(testUser);
        when(animalRepository.save(testAnimal)).thenReturn(testAnimal);

        Animal updatedAnimal = animalService.updateAnimal(testAnimal);

        assertNotNull(updatedAnimal);
        verify(animalRepository).save(testAnimal);
    }

    @Test
    public void testUpdateAnimalStatusEnabledToDisabled() {
        testAnimal.setId(1L);
        testAnimal.setStatus(AnimalStatus.ENABLED);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(testAnimal));
        when(userService.getMe()).thenReturn(testUser);
        when(animalRepository.save(testAnimal)).thenReturn(testAnimal);

        Animal updatedAnimal = animalService.updateAnimalStatus(1L);

        assertNotNull(updatedAnimal);
        assertEquals(AnimalStatus.DISABLED, updatedAnimal.getStatus());
        verify(animalRepository).save(updatedAnimal);
    }

    @Test
    public void testUpdateAnimalStatusDisabledToEnabled() {
        testAnimal.setId(1L);
        testAnimal.setStatus(AnimalStatus.DISABLED);
        when(animalRepository.findById(1L)).thenReturn(Optional.of(testAnimal));
        when(userService.getMe()).thenReturn(testUser);
        when(animalRepository.save(testAnimal)).thenReturn(testAnimal);

        Animal updatedAnimal = animalService.updateAnimalStatus(1L);

        assertNotNull(updatedAnimal);
        assertEquals(AnimalStatus.ENABLED, updatedAnimal.getStatus());
        verify(animalRepository).save(updatedAnimal);
    }

    @Test
    public void testDeleteAnimal() {
        testAnimal.setId(1L);
        when(animalRepository.findById(testAnimal.getId())).thenReturn(Optional.of(testAnimal)); // Simulando que o animal existe
        when(userService.getMe()).thenReturn(testUser);
        animalService.deleteAnimal(testAnimal.getId()); // Chamando o método delete

        verify(animalRepository).deleteById(testAnimal.getId()); // Verificando se o método deleteById foi chamado com o ID correto
    }

    @Test
    public void testGetAnimalByIdNotFound() {
        when(animalRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RestNotFound.class, () -> animalService.getAnimalById(1L));
    }

    @Test
    public void testVerifyAccessDenied() {
        User differentUser = new User();
        differentUser.setId(2L);
        testAnimal.setUser(differentUser);

        assertThrows(RestAccessDeniedError.class, () -> animalService.verifyAccess(testAnimal));
    }

    @Test
    public void testVerifyAccessGranted() {
        testAnimal.setUser(testUser);
        when(userService.getMe()).thenReturn(testUser);

        assertDoesNotThrow(() -> animalService.verifyAccess(testAnimal));
    }
}