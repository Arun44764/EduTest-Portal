package com.otp.OnlineTestPortal.Dto;

import jakarta.persistence.Column;

public class EnquiryDto {

	
	private int id;
	private String name;
	private String contactno;
	private String email;
	private String collegename;
	private String address;
	private String enquirytext;
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
