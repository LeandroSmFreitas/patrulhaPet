package br.com.manager.patrulhapet.facade.mapper;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnimalMapper extends EntityMapper<AnimalDTO, Animal> {

    Animal toEntity(AnimalDTO dto);
};
