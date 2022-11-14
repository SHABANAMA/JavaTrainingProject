package com.sms.studentmonitoringapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sms.studentmonitoringapp.entity.User;
import com.sms.studentmonitoringapp.repository.UserRepository;

@Service
public class SmsAuthenticationServiceImpl implements SmsAuthenticationService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUserName(username);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassWord(), new ArrayList<>());
	}	
}
