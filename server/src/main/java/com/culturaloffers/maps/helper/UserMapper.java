package com.culturaloffers.maps.helper;

import com.culturaloffers.maps.dto.UserDTO;
import com.culturaloffers.maps.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper implements MapperInterface<User, UserDTO> {

    @Override
    public User toEntity(UserDTO dto) {
        return null;
    }

    @Override
    public List<User> toEntityList(List<UserDTO> dtoList) {
        return null;
    }

    @Override
    public UserDTO toDto(User entity) {
        return new UserDTO(entity.getUsername(), entity.getPassword());
    }

    @Override
    public List<UserDTO> toDtoList(List<User> entityList) {
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }
}
