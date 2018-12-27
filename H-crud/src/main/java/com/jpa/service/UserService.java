package com.jpa.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jpa.entity.User;
import com.jpa.model.ApiStatus;
import com.jpa.model.UploadFile;

public interface UserService {

	public ApiStatus register(User user);
	public ApiStatus login(String name , String password);
	public User viewProfile(Long id);
	public UploadFile upload(MultipartFile file) throws Exception; 
	public List<User> getAllUser();
	public List<User> search(String name);
 }
