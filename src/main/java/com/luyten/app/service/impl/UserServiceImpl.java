package com.luyten.app.service.impl;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luyten.app.entity.UserEntity;
import com.luyten.app.mapper.UserMapper;
import com.luyten.app.repository.UserRepository;
import com.luyten.app.request.UserRequest;
import com.luyten.app.response.UserResponse;
import com.luyten.app.service.UserService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserMapper mapper;
	
	@Override
	public UserResponse create(UserRequest request) {
		UserEntity entity = repository.save(mapper.toEntity(request));
		log.info("Usuario creado de forma exitosa!!");
		log.info(entity);
		return mapper.toResponse(entity);
	}

	@Override
	public UserEntity update(Long id, UserEntity request) {
		Optional<UserEntity> findUser = findById(id);
		if(findUser.isEmpty()) {
			return null;
		}else {
			UserEntity entity = findUser.get();
			entity.setName(request.getName());
			entity.setLastName(request.getLastName());
			entity.setAge(request.getAge());
			entity.setCountry(request.getCountry());
			entity.setCity(request.getCity());
			entity.setPassword(request.getPassword());
			entity.setMail(request.getMail());
			
			repository.save(entity);
			return entity;
		}
	}

	@Override
	public void delete(UserResponse response) {
			log.info("Deleted : {}", response);
			repository.deleteById(response.getId());
		
	}

	@Override
	public Optional<UserEntity> findById(Long id) {
		Optional<UserEntity> entity = repository.findById(id);
		if(entity.isEmpty()) {
			log.info("No se encontro el usuario con el Id: {}", id);
		}
		return entity;
	}

	@Override
	public boolean find(Long id) {
		if(findById(id).isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public UserEntity findByName(String name) {
		List<UserEntity> entity = repository.findByName(name);
		if(!entity.isEmpty()) {
			return entity.get(0);
		}else {
			log.info("No se encontro el usuario con el nombre : {}", name);
			return null;
		}
		
	}

	@Override
	public List<UserEntity> findByNameAll(String name) {
		 List<UserEntity> entity = repository.findByName(name);
		return entity;
	}

	@Override
	public String updatePassword(Long id, String password) {
		Optional<UserEntity> findUser = findById(id);
		if(findUser.isEmpty()) {
			return "No se encontro el usuario con id: " + id;
		}else {
			UserEntity entity = findUser.get();

			entity.setPassword(password);
			
			repository.save(entity);
			return "Password actualiza con exito!! "
					+ "\nId: " + id
					+ "\nNew Password: " + password;
		}
	}

	@Override
	public boolean activateAccount(String name) {
		UserEntity user = findByName(name);
		if(user == null) {
			return false;
		}else {
			user.setActive(true);
			repository.save(user);
			return true;
		}
		
	}

	

}
