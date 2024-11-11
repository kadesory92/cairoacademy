package com.banatech.ru.cairoacademy.service.user;

import com.banatech.ru.cairoacademy.config.BaseMapper;
import com.banatech.ru.cairoacademy.dto.UserDTO;
import com.banatech.ru.cairoacademy.model.User;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<User, UserDTO> {
    public UserMapper() {
        super();
        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                // Custom mappings for User to UserDTO
                map().setEnabled(source.isEnabled());
            }
        });
    }

    @Override
    public User convertToEntity(UserDTO dto, Object... args) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDTO convertToDto(User entity, Object... args) {
        return modelMapper.map(entity, UserDTO.class);
    }
}
