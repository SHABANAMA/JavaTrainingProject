package com.sms.studentmonitoringapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sms.studentmonitoringapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	public User findByUserName(String username);
}
