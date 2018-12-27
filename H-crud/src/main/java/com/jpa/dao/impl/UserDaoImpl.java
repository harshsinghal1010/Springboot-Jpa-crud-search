package com.jpa.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jpa.dao.UserDao;
import com.jpa.entity.User;
import com.jpa.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private UserRepository userRepository;
	
	 @PersistenceContext
	    private EntityManager em;

	@Override
	public void register(User user) {
		// TODO Auto-generated method stub
		userRepository.save(user);
	
	}

	@Override
	public Long login(String name, String password) {
		// TODO Auto-generated method stub
		Long l = userRepository.login(name, password);
		System.err.println("Dao == login  "+l);
		return l;
	}

	@Override
	public User viewProfile(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElse(null);
	}
	
	
	
	@Override
	public boolean checkEmail(String email) {
		int i = userRepository.checkEmail(email);
		if(i==0)
			return true;
		return false;
	}

	@Override
	public boolean checkUserName(String username) {
		// TODO Auto-generated method stub
		int i = userRepository.checkUserName(username);
		if(i==0)
			return true;
		return false;
	}

	@Override
	public boolean checkMobile(String mobile) {
		// TODO Auto-generated method stub
		int i = userRepository.checkMobile(mobile);
		if(i==0)
			return true;
		return false;
	}

	@Override
	public List<User> allUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}

	@Override
	public List<User> searchUser(String name) {
		// TODO Auto-generated method stub
		return userRepository.searchUser(name);
	}

}
