package com.orbital22.wemeet.mapper;

import com.orbital22.wemeet.dto.UserDto;
import com.orbital22.wemeet.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  UserDto userToUserDto(User user);

  User userDtoToUser(UserDto dto);
}
