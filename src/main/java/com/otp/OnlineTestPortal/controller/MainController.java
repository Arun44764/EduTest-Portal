package com.otp.OnlineTestPortal.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otp.OnlineTestPortal.Dto.AdminInfoDto;
import com.otp.OnlineTestPortal.Dto.EnquiryDto;
import com.otp.OnlineTestPortal.Dto.StudentInfoDto;
import com.otp.OnlineTestPortal.Repositary.AdminInfoRepo;
import com.otp.OnlineTestPortal.Repositary.EnquiryRepo;
import com.otp.OnlineTestPortal.Repositary.StudentInfoRepo;
import com.otp.OnlineTestPortal.model.AdminInfo;
import com.otp.OnlineTestPortal.model.Enquiry;
import com.otp.OnlineTestPortal.model.StudentInfo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class MainController {

	@Autowired
	StudentInfoRepo stdrepo;

	@Autowired
	EnquiryRepo eqRepo;
	
	@Autowired
	AdminInfoRepo adrepo;


//===INDEX PAGE===================================================================================
	@GetMapping("/")
	public String ShowIndex() {
		return "index";
	}
	
//********************************************************************************************************	
//===Aboutus PAGE===================================================================================
	@GetMapping("/Aboutus")
	public String ShowAboutus() {
		return "Aboutus";
	}
	
	
//********************************************************************************************************	
//===Student Registration PAGE===================================================================================	
	@GetMapping("/Register")
	public String ShowRegistration(Model model) {
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "Registration";
	}

	@PostMapping("/Register")
	public String Registration(@Valid @ModelAttribute StudentInfoDto studentInfoDto, BindingResult result,
			RedirectAttributes redirectAttributes) {
//		if(result.hasErrors())
//		{
//			return "redirect:/Register";
//	}
		try {
			StudentInfo stdinfo = new StudentInfo();
			stdinfo.setEnrollmentno(studentInfoDto.getEnrollmentno());
			stdinfo.setName(studentInfoDto.getName());
			stdinfo.setContactno(studentInfoDto.getContactno());
			stdinfo.setWhatsappno(studentInfoDto.getWhatsappno());
			stdinfo.setEmail(studentInfoDto.getEmail());
			stdinfo.setPassword(studentInfoDto.getPassword());
			stdinfo.setCollegename(studentInfoDto.getCollegename());
			stdinfo.setCourse(studentInfoDto.getCourse());
			stdinfo.setBranch(studentInfoDto.getBranch());
			stdinfo.setYear(studentInfoDto.getYear());
			stdinfo.setHighschoolp(studentInfoDto.getHighschoolp());
			stdinfo.setIntermediatep(studentInfoDto.getIntermediatep());
			stdinfo.setAggregatemarks(studentInfoDto.getAggregatemarks());
			stdinfo.setTrainingmode(studentInfoDto.getTrainingmode());
			stdinfo.setTraininglocation(studentInfoDto.getTraininglocation());
			stdinfo.setStatus("false");
			Date dt = new Date();
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/YYYY");
			String regdate = df.format(dt);
			stdinfo.setRegdate(regdate);

			stdrepo.save(stdinfo);
			redirectAttributes.addFlashAttribute("msg", "Registration is Successfull !");
			return "redirect:/Register";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Something went wrong -" + e.getMessage());
			return "redirect:/Register";

		}

	}
//********************************************************************************************************	
//===STUDENT LOGIN PAGE===================================================================================
	@GetMapping("/studentlogin")
	public String ShowStudentLogin(Model model) 
	{
		StudentInfoDto dto = new StudentInfoDto();
		model.addAttribute("dto", dto);
		return "studentlogin";
	}

	@PostMapping("/studentlogin")
	public String Studentlogin(@ModelAttribute StudentInfoDto dto,HttpSession session, RedirectAttributes redirectAttributes)
	{
		try {
			StudentInfo stdinfo=stdrepo.getById(dto.getEmail());
			if(stdinfo.getPassword().equals(dto.getPassword()))
			{
			if(stdinfo.getStatus().equals("true"))	
			{
				redirectAttributes.addFlashAttribute("msg","Valid user");
				return "redirect:/studentzone/stdhome";

			}
			else {
				redirectAttributes.addFlashAttribute("msg","You can't Login!🚫,wait for admin approval");
				return "redirect:/studentlogin";

			}
			}
			else
			{
				redirectAttributes.addFlashAttribute("msg","Invalid users");
				return "redirect:/studentlogin";

			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg","user does not exist");
			return "redirect:/studentlogin";
		}
	}
	//********************************************************************************************************
//===ADMIN LOGIN PAGE===================================================================================	
	@GetMapping("/Adminlogin")
	public String ShowAdminLogin(Model model) {
		AdminInfoDto dto = new AdminInfoDto();
		model.addAttribute("dto", dto);
		return "Adminlogin";
	}

	
	@PostMapping("/Adminlogin")
	public String AdminLogin(@ModelAttribute AdminInfoDto adminInfoDto, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {

			AdminInfo ad = adrepo.getById(adminInfoDto.getUserid());
			if (ad.getPassword().equals(adminInfoDto.getPassword())) {
				session.setAttribute("admin", ad.getUserid().toString());
				return "redirect:/Adminzone/adhome";
			} else {
				redirectAttributes.addFlashAttribute("msg", "Invalid User");
				return "redirect:/Adminlogin";
			}

		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "User does not exists - " + e.getMessage());
			return "redirect:/Adminlogin";
		}
	}
	//********************************************************************************************************
//===CONTACT USPAGE===================================================================================		
	@GetMapping("/contact")
	public String ShowContactus(Model model) {
		EnquiryDto dto = new EnquiryDto();
		model.addAttribute("dto", dto);
		return "contactus";
	}

	@PostMapping("/contact")
	public String contact(@ModelAttribute EnquiryDto enquiryDto, RedirectAttributes redirectAttributes) {
		try {
			Enquiry eq = new Enquiry();
			eq.setName(enquiryDto.getName());
			eq.setContactno(enquiryDto.getContactno());
			eq.setEmail(enquiryDto.getEmail());
			eq.setCollegename(enquiryDto.getCollegename());
			eq.setAddress(enquiryDto.getName());
			eq.setEnquirytext(enquiryDto.getEnquirytext());
			eq.setEnquirydate(enquiryDto.getEnquirydate());
			eqRepo.save(eq);
			redirectAttributes.addFlashAttribute("msg", "Enquiry Data Submitted Succesfully");

			return "redirect:/contact";
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("msg", "Something Went Wrong -" + e.getMessage());
			return "redirect:/contact";
		}

	}
//********************************************************************************************************
//=== SERVICE  PAGE===================================================================================		
	@GetMapping("/service")
	public String Showservice() {
		return "service";
	}

}
