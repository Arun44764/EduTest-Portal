package com.otp.OnlineTestPortal.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;

@Service
public class SendEmailService {

	@Autowired
	private JavaMailSender MailSender;
	

	
	public void ConfirmUserMail(String name,String pass,String mailTo)
	{
		String subject="Login Status On BBDITM LUCKNOW EDUTEST PORTAL";
		String message="Thank You Dear, "+name+"\nYou Have Successfully Registered "
				+ "To EDUTEST PORTAL.\nYour Registration Has been Approved by "
				+ "Admin\nNow you have login to given Id and password\n\nUser ID"
				+ " = "+mailTo+"\nPassword="+pass+"\n\nThank You \nEDUTEST PORTAL";
	    SimpleMailMessage msg=new SimpleMailMessage();
	    msg.setTo(mailTo);
	    msg.setSubject(subject);
	    msg.setText(message);
	    MailSender.send(msg);
	}
	
	
	public void SendTestId(String name,long testid,
			String testname,String starttime,String mailTo)
	{
		String subject="Test Details From BBDITM LUCKNOW EDUTEST PORTAL";
		String message = "Hello  Dear, " + name
				+ "\n BBDITM LUcknow EDUTEST portal has been scheduled a test for you \n Please login on Softpro Online Test portal by using your credentials \n You can access test through the test id \n \n Test ID ="
				+ testid + "\n Test Name =" + testname + "\n Test Start Date and Time =" +starttime + " \n\n Thank you";
		SimpleMailMessage msg = new SimpleMailMessage();

		msg.setTo(mailTo);
		msg.setSubject(subject);
		msg.setText(message);
		MailSender.send(msg);
		
	}
}

