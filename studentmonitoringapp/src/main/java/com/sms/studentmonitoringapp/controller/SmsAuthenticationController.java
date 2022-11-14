package com.sms.studentmonitoringapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sms.studentmonitoringapp.dto.UserAuthenticationRequest;
import com.sms.studentmonitoringapp.dto.UserAuthenticationResponse;
import com.sms.studentmonitoringapp.util.JwtUtil;

@RestController
public class SmsAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<UserAuthenticationResponse> generateToken(
			@RequestBody UserAuthenticationRequest userAuthenticationRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				userAuthenticationRequest.getUserName(), userAuthenticationRequest.getPassWord()));
		String jwt = jwtUtil.generateToken(userAuthenticationRequest.getUserName());
		return ResponseEntity.ok(new UserAuthenticationResponse(jwt));
	}

}
