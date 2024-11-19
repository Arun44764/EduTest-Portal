package com.otp.OnlineTestPortal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otp.OnlineTestPortal.API.SendEmailService;
import com.otp.OnlineTestPortal.Repositary.AdminInfoRepo;
import com.otp.OnlineTestPortal.Repositary.EnquiryRepo;
import com.otp.OnlineTestPortal.Repositary.StudentInfoRepo;
import com.otp.OnlineTestPortal.model.AdminInfo;
import com.otp.OnlineTestPortal.model.Enquiry;
import com.otp.OnlineTestPortal.model.StudentInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("Adminzone/")
public class AdminController

{
	@Autowired
	StudentInfoRepo stdRepo;

	@Autowired
	private SendEmailService emailService;

	@Autowired
	AdminInfoRepo adrepo;

	@Autowired
	EnquiryRepo erepo;

//=======OPEN DASHBOARD PAGE START CODE===========================================================================//

	@GetMapping("/adhome")
	public String ShowAdminDashboard(HttpSession session, RedirectAttributes redirectAttributes, Model model) {

		if (session.getAttribute("admin") != null) {

			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());

			return "Adminzone/AdminDashboard";
		}

		else

		{
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

//====Pending Student DELETE button KE LIYE==========================================================================//

	@GetMapping("delete")
	public String Delete(@RequestParam String email) {

		StudentInfo dt = stdRepo.findById(email).get();
		stdRepo.delete(dt);
		return "redirect:/Adminzone/newstudent";
	}

//=====verifiedstudent DELETE button=====================================================================================//

	@GetMapping("delete1")
	public String Delete1(@RequestParam String email) {

		StudentInfo dt = stdRepo.findById(email).get();
		stdRepo.delete(dt);
		return "redirect:/Adminzone/verifiedstd";
	}

//=====Pending Student ke liye============================================================================================//

	@GetMapping("/pendingstudent")
	public String Showpendingstudent(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			List<StudentInfo> stdlist = stdRepo.findAllByStatus("false");

			model.addAttribute("stdlist", stdlist);
			return "adminzone/pendingstudent";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}

	}
//=========VIEW MORE OPTION KE LIYE======================================================================================//

	@GetMapping("/viewmore")
	public String ShowViewMore(@RequestParam String email, HttpSession session, RedirectAttributes redirectAttributes,
			Model model) {
		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			StudentInfo stdInfo = stdRepo.getById(email);
			model.addAttribute("stdInfo", stdInfo);
			return "adminzone/viewmore";

		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

//=====Check pending status verified and then send message on your email using an api==========================================//

	@GetMapping("/checkstatus")
	public String CheckStatus(@RequestParam String email, HttpSession session, RedirectAttributes redirectAttributes,
			Model model) {

		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			StudentInfo stdinfo = stdRepo.findById(email).get();

			if (stdinfo.getStatus().equals("false")) {
				stdinfo.setStatus("true");
				stdRepo.save(stdinfo);

				String name = stdinfo.getName();
				String pass = stdinfo.getPassword();
				emailService.ConfirmUserMail(name, pass, email);
				return "redirect:/Adminzone/pendingstudent";
			} else {
				stdinfo.setStatus("false");
				stdRepo.save(stdinfo);
				return "redirect:/Adminzone/verifiedstd";
			}

		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

//========VerifiedStudent========================================================================================================//

	@GetMapping("/verifiedstd")
	public String ShowVerifiedStudents(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			List<StudentInfo> stdlist = stdRepo.findAllByStatus("true");
			model.addAttribute("stdlist", stdlist);
			return "Adminzone/verifiedstudent";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

//=======Feedback============================================================================================================//
	@GetMapping("/feedback")
	public String ShowFeedback() {
		return "adminzone/feedback";
	}

//=======Complaint============================================================================================================//
	@GetMapping("/complaint")
	public String ShowComplaints() {
		return "adminzone/complaint";
	}

//=======View Enquiries===========================================================================================================//

	@GetMapping("/viewenquiries")
	public String ShowEnquiries(HttpSession session, HttpServletResponse response,
			RedirectAttributes redirectAttributes, Model model) {
		if (session.getAttribute("admin") != null) {
			List<Enquiry> eliList = erepo.findAll();
			model.addAttribute("eliList", eliList);
			return "adminzone/viewenquiries";

		} else {
			redirectAttributes.addFlashAttribute("msg", "Your session has been ended.");
			return "redirect:/admin";

		}
	}
	

//=======Add Question ============================================================================================================//	

	@GetMapping("/addquestion")
	public String Showaddquestion(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			return "Adminzone/addquestion";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

//=======Change Password karne ke liye=================================================================================================//

	@GetMapping("/changepassword")
	public String Showchangepassword(HttpSession session, Model model, RedirectAttributes redirectAttributes) {

		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			return "Adminzone/changepassword";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

	@PostMapping("/changepassword")
	public String changePassword(HttpSession session, HttpServletRequest request, RedirectAttributes attributes) {
		try {
			AdminInfo adInfo = adrepo.findById(session.getAttribute("admin").toString()).get();
			String oldpass = request.getParameter("oldpass");
			String newpass = request.getParameter("newpass");
			String confirmpass = request.getParameter("confirmpass");

			if (newpass.equals(confirmpass)) {
				if (oldpass.equals(adInfo.getPassword())) {
					adInfo.setPassword(confirmpass);
					adrepo.save(adInfo);
					return "redirect:/Adminzone/adlogout";
				} else {
					attributes.addFlashAttribute("msg", "Invalid Old Password");
				}
			}

			else {
				attributes.addFlashAttribute("msg", "New Password and Confirm Password Not Match");
			}
			return "redirect:/Adminzone/changepassword";
		} catch (Exception e) {
			attributes.addFlashAttribute("msg", "Something went wrong" + e.getMessage());
			return "redirect:/Adminzone/changepassword";
		}

	}

//=======Manage Result============================================================================================================//

	@GetMapping("/manageresult")
	public String ShowResult() {
		return "adminzone/manageresult";
	}

//=======Logout Admin Profile=========================================================================================================//

	@GetMapping("/adlogout")
	public String Logout(HttpSession session, RedirectAttributes attributes) {
		if (session.getAttribute("admin") != null) {

			session.invalidate();
			attributes.addFlashAttribute("msg", "Successfully Logout");
			return "redirect:/Adminlogin";
		} else {

			attributes.addFlashAttribute("Msg", "Session Expired");
			return "redirect:/Adminlogin";
		}
	}

}
