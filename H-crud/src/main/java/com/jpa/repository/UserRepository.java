package com.jpa.repository;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;

import com.jpa.entity.User;

public interface UserRepository extends JpaRepository<User	, Long> {
	

	// 1st way
    @Async
    @Query("SELECT Count(*) FROM User u where email = :email") 
    Future<Integer> check(@Param("email") String email);
    
    
    // 2nd way
    
    @Query("SELECT Count(*) FROM User u where email = :email") 
    public Integer checkEmail(@Param("email") String email);
    
    @Query("SELECT Count(*) FROM User u where u.userName = :username")
    public Integer checkUserName(@Param("username") String userName);
    
    @Query("SELECT Count(*) FROM User u where u.mobile = :mobile")
    public Integer checkMobile(@Param("mobile") String mobile);
    
    @Query("SELECT id FROM User u WHERE (u.email=:name OR u.userName=:name) AND u.password=:password")
    public Long login(@Param("name") String name , @Param("password") String password);

    @Query("SELECT u from User u where  lower(concat(u.firstName,' ' ,u.lastName)) like lower(concat('%', :name,'%'))"
    		+ " OR lower(u.email) like lower(concat('%', :name,'%')) "
    		+ "OR lower(u.userName) like lower(concat('%', :name,'%'))")
    public List<User> searchUser(@Param("name")String name); 

}
