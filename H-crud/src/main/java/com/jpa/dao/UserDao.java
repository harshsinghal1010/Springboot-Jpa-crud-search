package com.jpa.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jpa.entity.User;


public interface UserDao {

	public void register(User user);
	public Long login(String name , String password);
	public User viewProfile(Long id);
	public boolean checkEmail(String email);
	public boolean checkUserName(String username);
	public boolean checkMobile(String mobile);
	public List<User> allUser();
	public List<User> searchUser(String name);
}
