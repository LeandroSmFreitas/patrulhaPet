package br.com.manager.patrulhapet.facade.mapper.decorator;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import br.com.manager.patrulhapet.facade.mapper.AnimalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public abstract class AnimalMapperDecorator implements AnimalMapper {

    @Override
    public AnimalDTO toDto(Animal entity) {
        return AnimalDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .imageUrl(entity.getImageUrl())
                .category(entity.getCategory())
                .age(this.calculateAge(entity.getBirthDate()))
                .build();
    }

    @Override
    public List<AnimalDTO> toDto(List<Animal> entities) {
        return entities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AnimalDTO convertToDto(Animal entity) {
        LocalDate birthDate = entity.getBirthDate();

        return AnimalDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birthDate(entity.getBirthDate())
                .description(entity.getDescription())
                .status(entity.getStatus())
                .imageUrl(entity.getImageUrl())
                .category(entity.getCategory())
                .age(calculateAge(birthDate))
                .build();
    }

    private int calculateAge(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
}
