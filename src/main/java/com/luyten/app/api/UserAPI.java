package com.luyten.app.api;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.Optional;

import org.mapstruct.ValueMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.luyten.app.entity.UserEntity;
import com.luyten.app.repository.UserRepository;
import com.luyten.app.request.UserRequest;
import com.luyten.app.response.UserResponse;
import com.luyten.app.service.MailService;
import com.luyten.app.service.UserService;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;


@CrossOrigin(origins = "http://localhost:3000/")
@Log4j2
@RestController
@RequestMapping("/api/user")
public class UserAPI {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private MailService serviceMail;
	
	@GetMapping("/get/all/{name}")
	public ResponseEntity<?> getByNameAll(@PathVariable String name){
		return new ResponseEntity<>(service.findByNameAll(name), HttpStatus.OK);
	}
	
	
	@GetMapping("/getbyid/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id){
		Optional<UserEntity> entity = service.findById(id);
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	@GetMapping("/getbyname/{name}")
	public ResponseEntity<?> getUser(@PathVariable String name){
		UserEntity entity = service.findByName(name);
		if(entity==null) {
			return new ResponseEntity<>(
					"No se encontro el usuario con el nombre :" + name, HttpStatus.OK);
		}
		return new ResponseEntity<>(entity, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<?> createUser(@RequestBody UserRequest request){
		UserResponse response = service.create(request);
		
		UserRequest request2 = new UserRequest();
		request2.setMail("gustavoaguilas149@gmail.com");
		request2.setName("Guss");
		request2.setLastName("Aguilar");
		serviceMail.sendMailConfimAccount(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/userupdate/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserEntity entity){
		UserEntity E = service.update(id, entity);
		if(E == null) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}else {
			return new ResponseEntity<>("Usuario con id: " + id + " se Actualizo de forma exitosa!", HttpStatus.OK);
		}
		
	}
	
	@PatchMapping("/passwordupdate/{id}/{password}")
	public ResponseEntity<?> updatePasswordUser(@PathVariable Long id, @PathVariable String password){
		return new ResponseEntity<>(service.updatePassword(id, password), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.HEAD, path = "/{id}")
	public ResponseEntity<?> validateUser(@PathVariable Long id){
		
		if(!service.find(id)) {
			return new ResponseEntity<>(false,HttpStatus.NOT_FOUND);
		}else {
			return new ResponseEntity<>(true,HttpStatus.OK);
		}
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteUser(@RequestBody UserResponse response){
		if(service.find(response.getId())) {
			service.delete(response);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		
	}
	
	@PostMapping("/activation")
	public boolean activateAccount(@RequestBody Object name) {
		
		return service.activateAccount(name.toString());
	}
	
}





