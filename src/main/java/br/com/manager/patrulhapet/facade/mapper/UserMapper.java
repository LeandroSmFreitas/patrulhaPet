package br.com.manager.patrulhapet.facade.mapper;

import br.com.manager.patrulhapet.domain.Animal;
import br.com.manager.patrulhapet.domain.User;
import br.com.manager.patrulhapet.facade.dto.animal.AnimalDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserDTO;
import br.com.manager.patrulhapet.facade.dto.user.UserRegisterDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends EntityMapper<UserDTO, User>{
    User toEntity(UserRegisterDTO dto);
}
