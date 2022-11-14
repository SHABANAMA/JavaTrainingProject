package com.sms.studentmonitoringapp.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_details")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@NotEmpty(message = "The name must not be empty")
	private String firstName;
	private String lastName;
	
	@NotEmpty(message  = "The email id must not be empty")
	@Email(message = "The email entered is not valid")
	private String email;
	
	@Pattern(regexp="(^$|[0-9]{10})",message="The Phone number entered is not valid")
	private String phone;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private Date dob;
	private String userName;
	private String passWord;
}
