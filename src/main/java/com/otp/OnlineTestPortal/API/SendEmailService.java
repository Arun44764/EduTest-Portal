package com.otp.OnlineTestPortal.API;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
}
