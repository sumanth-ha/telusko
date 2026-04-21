package com.sumanth.Entity;


import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
	private LocalDate dob;
	private String age;
	private String email;
	private String mobile;
	private String Address;
	private String Creationdate;

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCreationdate() {
		return Creationdate;
	}

	public void setCreationdate(String creationdate) {
		Creationdate = creationdate;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Customer(Integer id, String name, LocalDate dob, String age, String email, String mobile, String address,
			String creationdate) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.age = age;
		this.email = email;
		this.mobile = mobile;
		Address = address;
		Creationdate = creationdate;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", dob=" + dob + ", age=" + age + ", email=" + email
				+ ", mobile=" + mobile + ", Address=" + Address + ", Creationdate=" + Creationdate + "]";
	}


}
