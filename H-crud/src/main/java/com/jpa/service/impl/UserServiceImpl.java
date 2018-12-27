package com.jpa.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jpa.dao.UserDao;
import com.jpa.entity.User;
import com.jpa.model.ApiStatus;
import com.jpa.model.EndPoints;
import com.jpa.model.UploadFile;
import com.jpa.service.UserService;

@Service
public class UserServiceImpl implements UserService {


	@Autowired
	private ApiStatus api;

	@Autowired
	private UploadFile uploadFile;

	@Autowired
	private UserDao dao;

	@Override
	public ApiStatus register(User user) {
		// TODO Auto-generated method stub
		if (dao.checkEmail(user.getEmail())) {
			if (dao.checkUserName(user.getUserName())) {
				if (dao.checkMobile(user.getMobile())) {
					dao.register(user);

					return showResponse("Registertion Successfully compete", "Success", null);
				}

				else {

					return showResponse("Mobile Number already Registered", "Error", null);

				}
			} else {

				return showResponse("UserName already Exist", "Error", null);

			}
		} else {

			return showResponse("Email Id already Exist", "Error", null);

		}
	}

	@Override
	public ApiStatus login(String name, String password) {
		// TODO Auto-generated method stub
		Long l = dao.login(name, password);
		System.err.println("Login == " + l);
		if (l != null)
			return showResponse("Login Succesfully ", "success", l);
		return showResponse("Wrong UserName or Password ", "error", 0l);

	}

	@Override
	public User viewProfile(Long id) {
		// TODO Auto-generated method stub
		return dao.viewProfile(id);
	}

	@Override
	public UploadFile upload(MultipartFile uploadfile) throws Exception {
		// TODO Auto-generated method stub
		String fileName = generateUniqueFileName() + uploadfile.getOriginalFilename();
		System.err.println(fileName);

		File cf = new File(EndPoints.UPLOADED_FOLDER + fileName);
		cf.createNewFile();

		FileOutputStream outputStream = new FileOutputStream(cf);
		outputStream.write(uploadfile.getBytes());
		outputStream.close();

		uploadFile.setStatus("success");
		uploadFile.setPath(fileName);
		return uploadFile;
	}

	private String generateUniqueFileName() {
		String filename = "";

		long millis = System.currentTimeMillis();
		String DATE_FORMAT = "yyyyMMdd_HHmmss_SSS";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		String date = sdf.format(new Date());
		System.out.println("date " + date);
		String num = String.valueOf(new Random().nextInt(9999));
		filename = date + "_" + millis + "_" + num;
		return filename;

	}

	private ApiStatus showResponse(String message, String status, Long id) {
		api.setMessage(message);
		api.setStatus(status);
		api.setUserId(id);
		return api;

	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return dao.allUser();
	}

	@Override
	public List<User> search(String name) {
		// TODO Auto-generated method stub
		return dao.searchUser(name);
	}

}
