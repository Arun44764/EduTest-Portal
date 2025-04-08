package com.otp.OnlineTestPortal.Dto;



public class ResponseDto {

	private int resid;
	private String name;
	private String enrollmentno;
	private String email;
	private String contactno;
	private String responsetype;
	private String subject;
	private String message;
	public int getResid() {
		return resid;
	}
	public void setResid(int resid) {
		this.resid = resid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEnrollmentno() {
		return enrollmentno;
	}
	public void setEnrollmentno(String enrollmentno) {
		this.enrollmentno = enrollmentno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContactno() {
		return contactno;
	}
	public void setContactno(String contactno) {
		this.contactno = contactno;
	}
	public String getResponsetype() {
		return responsetype;
	}
	public void setResponsetype(String responsetype) {
		this.responsetype = responsetype;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
