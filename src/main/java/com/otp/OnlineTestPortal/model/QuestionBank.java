package com.otp.OnlineTestPortal.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="questionbank")
public class QuestionBank {
	
	@Id
	private int id;

	@Column(nullable=false,length=100)
	private String Year;
	
	@Column(nullable=false,length=2000)
	private String question;
	
	@Column(nullable=false,length=500)
	private String a;
	
	@Column(nullable=false,length=500)
	private String b;
	
	@Column(nullable=false,length=500)
	private String c;
	
	@Column(nullable=false,length=500)
	private String d;
	
	@Column(nullable=false,length=10)
	private String correct;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getC() {
		return c;
	}
	
	

	public String getYear() {
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getCorrect() {
		return correct;
	}

	public void setCorrect(String correct) {
		this.correct = correct;
	}
	
	
	


}
