package com.jpa.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jpa.entity.User;
import com.jpa.model.ApiStatus;
import com.jpa.model.EndPoints;
import com.jpa.model.UploadFile;
import com.jpa.service.UserService;

@RestController
@RequestMapping(EndPoints.USER)
public class UserController {

	@Autowired
	private ApiStatus api;

	@Autowired
	private UserService service;

	@PostMapping(path = EndPoints.UPLOAD, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	private ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile uploadfile) {

		System.err.println("hello");
		UploadFile up;
		try {
			up = service.upload(uploadfile);
			return ResponseEntity.status(HttpStatus.OK).body(up);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return showResponse("error", "File not upload", null);
		}

	}

	@PostMapping(EndPoints.REGISTER)
	private ResponseEntity<?> register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return showResponse("error", bindingResult.getAllErrors().get(0).getDefaultMessage(), null);
		}

		return ResponseEntity.status(HttpStatus.OK).body(service.register(user));
	}

	@PostMapping(EndPoints.LOGIN)
	private ResponseEntity<?> login(@RequestParam("username") String name,
			@RequestParam("password") String password) {

		return ResponseEntity.status(HttpStatus.OK).body(service.login(name, password));
	}

	
	
	@GetMapping(EndPoints.USER_PROFILE)
	private ResponseEntity<?> viewProfile(@RequestParam("user_id") Long id) {
		
		User user = service.viewProfile(id);
		if(user==null)
			showResponse("User Not FOund", "error", null);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}

	
	
	
	@GetMapping(EndPoints.ALL_USER)
	private ResponseEntity<?> allProfile() {
		
		List<User> user = service.getAllUser();
		if(user.isEmpty())
			return showResponse("User Not FOund", "error", null);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	
	
	@GetMapping(EndPoints.SEARCH_USER)
	private ResponseEntity<?> searchUser(@Param("name")String name) {
		
		List<User> user = service.search(name);
		if(user.isEmpty())
			return showResponse("User Not FOund", "error", null);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private ResponseEntity<ApiStatus> showResponse(String message, String status, Long id) {
		api.setMessage(message);
		api.setStatus(status);
		api.setUserId(id);
		return ResponseEntity.status(HttpStatus.OK).body(api);

	}

	@ExceptionHandler({ Exception.class })

	private ResponseEntity<ApiStatus> showException(Exception ex) {
		api.setMessage(ex.getMessage());
		api.setStatus("error");
		api.setUserId(null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(api);

	}

}
