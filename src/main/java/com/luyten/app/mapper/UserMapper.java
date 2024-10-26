package com.luyten.app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.luyten.app.entity.UserEntity;
import com.luyten.app.request.UserRequest;
import com.luyten.app.response.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", ignore = true)
	public UserEntity toEntity(UserRequest request);
	
	public UserResponse toResponse(UserEntity entity);
}
