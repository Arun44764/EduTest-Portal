
package com.otp.OnlineTestPortal.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("studentzone/")
public class StudentController {
@GetMapping("/stdhome")
	public String ShowStudentDashboard()
	{
		return "studentzone/studentdashboard";
	}
@GetMapping("/givetest")
public String Showgivetest()
{
	return "studentzone/givetest";
}

@GetMapping("/collegevideo")
public String Showcollegevideo()
{
	return "studentzone/collegevideo";
}

@GetMapping("/studentresult")
public String Showseeresult()
{
	return "studentzone/seeresult";
}

@GetMapping("/response")
public String Showgiveresponse()
{
	return "studentzone/giveresponse";
}




@GetMapping("/changepassword")
public String Showchangepassword()
{
	return "studentzone/changepassword";
}
	


@GetMapping("/stdlogout")
public String Logout(HttpSession session,RedirectAttributes attributes) 
{
	if(session.getAttribute("student")!=null)
	{
		
		session.invalidate();
		attributes.addFlashAttribute("msg","Successfully Logout");
		return "redirect:/studentlogin";
	}
	else {
		
		attributes.addFlashAttribute("Msg","Session Expired");
		return "redirect:/studentlogin";
	}
}
}