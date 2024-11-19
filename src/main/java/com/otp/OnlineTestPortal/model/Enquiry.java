package com.otp.OnlineTestPortal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="enquiries")
public class Enquiry {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false,length=60)
	private String name;
	
	@Column(nullable=false,length=15)
	private String contactno;
	
	@Column(nullable=false,length=100)
	private String email;
	
	@Column(nullable=false,length=200)
	private String collegename;
	
	@Column(nullable=false,length=100)
	private String address;
	
	@Column(nullable=false,length=1000)
	private String enquirytext;
	
	@Column(nullable=false,length=50)
	private String enquirydate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactno() {
		return contactno;
	}

	public void setContactno(String contactno) {
		this.contactno = contactno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCollegename() {
		return collegename;
	}

	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnquirytext() {
		return enquirytext;
	}

	public void setEnquirytext(String enquirytext) {
		this.enquirytext = enquirytext;
	}

	public String getEnquirydate() {
		return enquirydate;
	}

	public void setEnquirydate(String enquirydate) {
		this.enquirydate = enquirydate;
	}

	
}
