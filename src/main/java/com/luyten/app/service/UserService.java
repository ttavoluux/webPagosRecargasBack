package com.luyten.app.service;

import java.util.List;
import java.util.Optional;

import com.luyten.app.entity.UserEntity;
import com.luyten.app.request.UserRequest;
import com.luyten.app.response.UserResponse;

public interface UserService {

	public UserResponse create(UserRequest request);
	public UserEntity update(Long id, UserEntity request);
	public String updatePassword(Long id, String password);
	public void delete(UserResponse response);
	public Optional<UserEntity> findById(Long id);
	public UserEntity findByName(String name);
	public List<UserEntity> findByNameAll(String name);
	public boolean find(Long request);
	public boolean activateAccount(String name);
}
