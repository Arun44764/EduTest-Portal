package com.otp.OnlineTestPortal.Dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class StudentInfoDto {

	@NotBlank(message = "enrollment Number Can not be null")
 private String enrollmentno;
	
	@NotBlank(message = "name  Can not be null")
	private String name;
	
	@NotBlank(message = "contact Number Can not be null")
	private String contactno;
	
	@NotBlank(message = "whatsappno Can not be null")
	private String whatsappno;
	
	@NotBlank(message = "email address Can not be null")
	private String email;
	
	@NotBlank(message = "password Can not be null")
	@Size(min = 4, message="Password Should be atleast 4 digits")
	@Size(max=15, message = "Password can not be more than 15 digits")
	private String password;
	
	
	@NotBlank(message = "Please select college name")
	private String collegename;
	
	
	@NotBlank(message = "Please select course")
	private String course;
	
	
	@NotBlank(message = "Please select branch")
	private String branch;
	
	@NotBlank(message = "Please select year")
	private String year;
	
	@NotBlank(message = "Please provide highschool marks percentage")
	private String highschoolp;
	
	@NotBlank(message = "Please provide intermediate marks percentage")
	private String intermediatep;
	
	@NotBlank(message = "Please provide aggregatemarks marks percentage")
	private String aggregatemarks;
	
	@NotBlank(message = "Please select training mode")
	private String trainingmode;
	
	@NotBlank(message = "Please select training location")
	private String traininglocation;
	
	private String status;
	
	
	private MultipartFile profilepic;
	
	private String regdate;

	public String getEnrollmentno() {
		return enrollmentno;
	}

	public void setEnrollmentno(String enrollmentno) {
		this.enrollmentno = enrollmentno;
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

	public String getWhatsappno() {
		return whatsappno;
	}

	public void setWhatsappno(String whatsappno) {
		this.whatsappno = whatsappno;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCollegename() {
		return collegename;
	}

	public void setCollegename(String collegename) {
		this.collegename = collegename;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHighschoolp() {
		return highschoolp;
	}

	public void setHighschoolp(String highschoolp) {
		this.highschoolp = highschoolp;
	}

	public String getIntermediatep() {
		return intermediatep;
	}

	public void setIntermediatep(String intermediatep) {
		this.intermediatep = intermediatep;
	}

	public String getAggregatemarks() {
		return aggregatemarks;
	}

	public void setAggregatemarks(String aggregatemarks) {
		this.aggregatemarks = aggregatemarks;
	}

	public String getTrainingmode() {
		return trainingmode;
	}

	public void setTrainingmode(String trainingmode) {
		this.trainingmode = trainingmode;
	}

	public String getTraininglocation() {
		return traininglocation;
	}

	public void setTraininglocation(String traininglocation) {
		this.traininglocation = traininglocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public MultipartFile getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(MultipartFile profilepic) {
		this.profilepic = profilepic;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	
	

}
