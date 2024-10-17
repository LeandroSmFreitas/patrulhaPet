package br.com.manager.patrulhapet.facade.mapper;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import br.com.manager.patrulhapet.facade.mapper.decorator.AnimalMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(AnimalMapperDecorator.class)
public interface AnimalMapper extends EntityMapper<AnimalDTO, Animal> {

    Animal toEntity(AnimalDTO dto);
};
