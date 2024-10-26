package com.luyten.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luyten.app.entity.UserEntity;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
	List<UserEntity> findByName(String name);
}
