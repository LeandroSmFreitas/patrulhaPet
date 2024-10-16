package br.com.manager.patrulhapet.web.rest;

import br.com.manager.patrulhapet.domain.enumeration.AnimalCategory;
import br.com.manager.patrulhapet.domain.enumeration.AnimalStatus;
import br.com.manager.patrulhapet.facade.AnimalFacade;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class AnimalResourceTest {

    @Mock
    private AnimalFacade animalFacade;

    @InjectMocks
    private AnimalResource animalResource;

    private MockMvc mockMvc;

    private AnimalDTO animalDTO;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(animalResource).build();

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        animalDTO = new AnimalDTO();
        animalDTO.setId(1L);
        animalDTO.setName("Cachorro");
        animalDTO.setDescription("Cachorro");
        animalDTO.setBirthDate(LocalDate.now());
        animalDTO.setCategory(AnimalCategory.DOG);
        animalDTO.setImageUrl("....");
        animalDTO.setStatus(AnimalStatus.ENABLED);
    }

    @Test
    void createAnimal_ShouldReturnCreatedAnimal() throws Exception {
        when(animalFacade.create(any(AnimalDTO.class))).thenReturn(animalDTO);

        String animalJson = objectMapper.writeValueAsString(animalDTO);

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "")
                        .content(animalJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cachorro"))
                .andExpect(jsonPath("$.status").value("ENABLED"))
                .andDo(print());

        verify(animalFacade, times(1)).create(any(AnimalDTO.class));
    }

    @Test
    void getAll_ShouldReturnListOfAnimals() throws Exception {
        List<AnimalDTO> animalList = Arrays.asList(animalDTO);
        when(animalFacade.getAllAnimals()).thenReturn(animalList);

        mockMvc.perform(get("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Cachorro"))
                .andExpect(jsonPath("$[0].status").value("ENABLED"))
                .andDo(print());

        verify(animalFacade, times(1)).getAllAnimals();
    }

    @Test
    void updateAnimal_ShouldReturnUpdatedAnimal() throws Exception {
        animalDTO.setName("Cachorro atualizado");

        when(animalFacade.updateAnimal(any(AnimalDTO.class))).thenReturn(animalDTO);

        String animalJson = objectMapper.writeValueAsString(animalDTO);

        mockMvc.perform(put("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "")
                        .content(animalJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Cachorro atualizado"))
                .andExpect(jsonPath("$.status").value("ENABLED"))
                .andDo(print());

        verify(animalFacade, times(1)).updateAnimal(any(AnimalDTO.class));
    }

    @Test
    void updateAnimalStatus_ShouldReturnAnimalWithUpdatedStatus() throws Exception {
        animalDTO.setStatus(AnimalStatus.DISABLED);
        when(animalFacade.updateAnimalStatus(1L)).thenReturn(animalDTO);

        mockMvc.perform(patch("/api/animals/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, ""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("DISABLED"))
                .andDo(print());

        verify(animalFacade, times(1)).updateAnimalStatus(1L);
    }

    @Test
    void deleteAnimal_ShouldReturnOkStatus() throws Exception {
        doNothing().when(animalFacade).deleteAnimal(1L);

        mockMvc.perform(delete("/api/animals/1/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, ""))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(animalFacade, times(1)).deleteAnimal(1L);
    }
}
