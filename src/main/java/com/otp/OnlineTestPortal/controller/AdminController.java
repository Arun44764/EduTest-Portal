package com.otp.OnlineTestPortal.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.KeyStore.Entry.Attribute;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.otp.OnlineTestPortal.API.SendEmailService;
import com.otp.OnlineTestPortal.Dto.QuestionBankDto;
import com.otp.OnlineTestPortal.Dto.TestInfoDto;
import com.otp.OnlineTestPortal.Repositary.AdminInfoRepo;
import com.otp.OnlineTestPortal.Repositary.EnquiryRepo;
import com.otp.OnlineTestPortal.Repositary.QuestionBankRepo;
import com.otp.OnlineTestPortal.Repositary.ResponseRepo;
import com.otp.OnlineTestPortal.Repositary.StudentInfoRepo;
import com.otp.OnlineTestPortal.Repositary.TestInfoRepo;
import com.otp.OnlineTestPortal.Repositary.TestResultRepo;
import com.otp.OnlineTestPortal.model.AdminInfo;
import com.otp.OnlineTestPortal.model.Enquiry;
import com.otp.OnlineTestPortal.model.QuestionBank;
import com.otp.OnlineTestPortal.model.Response;
import com.otp.OnlineTestPortal.model.StudentInfo;
import com.otp.OnlineTestPortal.model.TestInfo;

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

	@Autowired
	QuestionBankRepo qbrepo;
	
	@Autowired
	TestInfoRepo tstrepo;
	
	@Autowired
	 ResponseRepo resrepo;
	@Autowired
	TestResultRepo resultrepo;

//=======OPEN ADMIN DASHBOARD PAGE START CODE===========================================================================//

	@GetMapping("/adhome")
	public String ShowAdmindashboard(HttpSession session, RedirectAttributes redirectAttributes,Model model, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("admin") != null) {
			AdminInfo ad= adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("ad", ad);
			model.addAttribute("profilepic", ad.getProfilepic());
			model.addAttribute("adname", ad.getName());
			// Student Count
			
			long stdcount=stdRepo.count();
			model.addAttribute("stdcount", stdcount);
			
			//feedback
			List<Response> fList=resrepo.findResponseByResponsetype("feedback");
			long fcount=fList.size();
			model.addAttribute("fcount", fcount);
			//complaint
			List<Response> clList=resrepo.findResponseByResponsetype("complaint");
			long ccount=clList.size();
			model.addAttribute("ccount", ccount);

			long qcount=qbrepo.count();
			model.addAttribute("qcount", qcount);
			
			long rcount=resultrepo.count();
			model.addAttribute("rcount", rcount);
			
			long eqcount=erepo.count();
			model.addAttribute("eqcount", eqcount);
			
			return "Adminzone/AdminDashboard";
		}

		else

		{
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}
	
	
	
	@GetMapping("/profile")
	public String ShowProfile(HttpSession session,Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("admin") != null)
		{
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("ad", ad);
			model.addAttribute("profilepic", ad.getProfilepic());
			model.addAttribute("adname", ad.getName());
				return "adminzone/profile";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}
	@PostMapping("/adhome")
	public String UploadPic(@RequestParam MultipartFile profilepic, HttpSession session, RedirectAttributes attributes)
			throws IOException {
		try {
			// Get the original filename
			String StorageFileName = profilepic.getOriginalFilename();

			// Directory to store the uploaded file (make sure to set the absolute path or
			// relative path as needed)
			String UploadDir = "public/profiles/";

			// Ensure the directory exists
			Path UploadPath = Paths.get(UploadDir);
			if (!Files.exists(UploadPath)) {
				Files.createDirectories(UploadPath);
			}

			// Upload the file
			try (InputStream inputStream = profilepic.getInputStream()) {
				Files.copy(inputStream, Paths.get(UploadDir + StorageFileName), StandardCopyOption.REPLACE_EXISTING);
			}

			// Save the uploaded profile picture path in the student info
			AdminInfo adinfo = adrepo.findById(session.getAttribute("admin").toString()).get();
			adinfo.setProfilepic(StorageFileName);
			adrepo.save(adinfo);

			// Flash message for successful upload
			attributes.addFlashAttribute("msg", "Profile Pic uploaded successfully");

			// Redirect to the same page to reflect the changes
			return "redirect:/Adminzone/adhome";
		} catch (Exception e) {
			// In case of an error, show the error message
			attributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/Adminzone/adhome";
		}
	}
	
	
	
	
	
	

//=========Schedule Test Working Button and function	
	
	@GetMapping("/scheduletest")
	public String ShowSchdeuleTest(HttpSession session, RedirectAttributes redirectAttributes, Model model) {

		if (session.getAttribute("admin") != null) {

			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			
			TestInfoDto dto = new TestInfoDto();
			model.addAttribute("dto",dto);
			return "Adminzone/scheduletest";
		}

		else

		{
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}
	
	
	
	
	
	
	
	
	@PostMapping("/scheduletest")
	public String ScheduleTest(@ModelAttribute TestInfoDto dto,RedirectAttributes attributes) {
		 try {
			TestInfo tstinfo = new TestInfo();
			tstinfo.setTestname(dto.getTestname());
			tstinfo.setCourse(dto.getCourse());
			tstinfo.setBranch(dto.getBranch());
			tstinfo.setYear(dto.getYear());
			tstinfo.setNumberofquestion(dto.getNumberofquestion());
			tstinfo.setStarttime(dto.getStarttime());
			tstinfo.setActive(false);
			tstrepo.save(tstinfo);
			
			
			
//========Send EMail message to student test schedule==================================
			
			List<StudentInfo> stdlist = stdRepo.findAll();
			for(StudentInfo student:stdlist)
			{
				if (student.getCourse().equals(tstinfo.getCourse()) 
						&& student.getBranch().equals(tstinfo.getBranch())
						&& student.getYear().equals(tstinfo.getYear())) 
						
				{
				emailService.SendTestId(student.getName(), tstinfo.getId(), dto.getTestname(), dto.getStarttime().toString(), student.getEmail());
					
				}
			}
				
			
			attributes.addFlashAttribute("msg", "Test Has Been Schedule Successfully!");
			return "redirect:/Adminzone/scheduletest";
			
		} catch (Exception e) {
			attributes.addFlashAttribute("msg",e.getMessage());
			return "redirect:/Adminzone/scheduletest";
		}
		
	}

//====Pending Student DELETE button KE LIYE==========================================================================//

	@GetMapping("delete")
	public String Delete(@RequestParam String email) {

		StudentInfo dt = stdRepo.findById(email).get();
		stdRepo.delete(dt);
		return "redirect:/Adminzone/newstudent";
	}

//=====Verified student DELETE button=====================================================================================//

	@GetMapping("delete1")
	public String Delete1(@RequestParam String email) {

		StudentInfo dt = stdRepo.findById(email).get();
		stdRepo.delete(dt);
		return "redirect:/Adminzone/verifiedstd";
	}

//=====Pending Student Ke liye============================================================================================//

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

//=====Check pending status verified and then send message on your email using an API==========================================//

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
	public String ShowFeedback(HttpSession session, Model model, HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("admin") != null) {
			List <Response> flList=resrepo.findResponseByResponsetype("feedback");
			model.addAttribute("flList",flList);
			return"Adminzone/feedback";
	} 
		else 
		{
		return "redirect:/admin";
		}
		}

	@GetMapping("deletefed")
	public String DeleteFeedback(@RequestParam int resid)
	{

	Response dtc=resrepo.findById(resid).get();
	resrepo.delete(dtc);
	return "redirect:/Adminzone/feedback";
	}
	

//=======Complaint============================================================================================================//
	@GetMapping("/complaint")
	public String ShowComplaint(HttpSession session, HttpServletResponse response,Model model)
	{
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("admin") != null) {
			
			List <Response> clList=resrepo.findResponseByResponsetype("complaint");
			model.addAttribute("clList",clList);
			return"Adminzone/complaint";
		}
		else {
			return "redirect:/admin";
		}
		}

	@GetMapping("deletecom")
	public String DeleteComplaint(@RequestParam int resid)
	{

	Response dtf=resrepo.findById(resid).get();
	resrepo.delete(dtf);
	return "redirect:/Adminzone/complaint";
	}

//=======View enquiries===========================================================================================================//

	@GetMapping("/viewenquiries")
	public String ShowEnquiries(HttpSession session, HttpServletResponse response,
			RedirectAttributes redirectAttributes, Model model) {
		if (session.getAttribute("admin") != null) {
			List<Enquiry> eliList = erepo.findAll();
			model.addAttribute("eliList", eliList);
			return "Adminzone/viewenquiries";

		} else {
			redirectAttributes.addFlashAttribute("msg", "Your session has been ended.");
			return "redirect:/admin";

		}
	}

//=======Add Question=============================================================================================================//	

	@GetMapping("/addquestion")
	public String Showaddquestion(HttpSession session, Model model, RedirectAttributes redirectAttributes,HttpServletResponse response) 
	{
response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");
		if (session.getAttribute("admin") != null) {
			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());
			QuestionBankDto dto = new QuestionBankDto();
			model.addAttribute("dto", dto);
			return "Adminzone/addquestion";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

	@PostMapping("/addquestion")
	public String AddQuestion(@ModelAttribute QuestionBankDto dto, RedirectAttributes attributes) {
		try {
			QuestionBank qb = new QuestionBank();
			qb.setQuestion(dto.getQuestion());
			qb.setA(dto.getA());
			qb.setB(dto.getB());
			qb.setC(dto.getC());
			qb.setD(dto.getD());
			qb.setCorrect(dto.getCorrect());
			qb.setYear(dto.getYear());
			qbrepo.save(qb);
			attributes.addFlashAttribute("msg", "Question Added Successfully!");
			return "redirect:/Adminzone/addquestion";

		} catch (Exception e) {
			attributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/Adminzone/addquestion";
		}
	}

//=======Uplaod CSV File Question PAGE START CODE===========================================================================//

	@GetMapping("/uploadquestion")
	public String ShowUploadQuestion(HttpSession session, RedirectAttributes redirectAttributes, Model model) {

		if (session.getAttribute("admin") != null) {

			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
			model.addAttribute("adname", ad.getName());

			return "Adminzone/uploadquestion";
		}

		else

		{
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/Adminlogin";
		}
	}

	@PostMapping("/uploadquestion")
	public String UploadCSVQuestion(@RequestParam("csvfile") MultipartFile file, RedirectAttributes attributes) {
		if (file.isEmpty()) {
			attributes.addFlashAttribute("msg", "File is Empty");
			return "redirect:/Adminzone/uploadquestion";
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
			String line;
			List<QuestionBank> qblist = new ArrayList<>();
			reader.readLine(); // Remove Header line in CSV file
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(","); // Data comma(,) use krke rakhe jate han csv file me
				if (data.length == 7) {
					QuestionBank qb = new QuestionBank();
					qb.setQuestion(data[0]);
					qb.setA(data[1]);
					qb.setB(data[2]);
					qb.setC(data[3]);
					qb.setD(data[4]);
					qb.setCorrect(data[5]);
					qb.setYear(data[6]);
					qblist.add(qb);
				}

			}
            qbrepo.saveAll(qblist);
			attributes.addFlashAttribute("msg", "CSV File Uploaded Successfully");
			return "redirect:/Adminzone/uploadquestion";

		} catch (Exception e) {
			attributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/Adminzone/uploadquestion";
		}
	}
	
	
	
	@GetMapping("/managequestion")
	public String ManageQuestionBank(Model model,HttpSession session, HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("admin") != null) {
		List<QuestionBank> qList =qbrepo.findAll();
		model.addAttribute("qList",qList);
		return"Adminzone/managequestion";
		}
		else
		{
			return "redirect:/admin";
		
		}
		}
	
	
	
	
	

//=======Change Password karne ke liye=================================================================================================//

//	@GetMapping("/changepassword")
//	public String Showchangepassword(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
//
//		if (session.getAttribute("admin") != null) {
//			AdminInfo ad = adrepo.getById(session.getAttribute("admin").toString());
//			model.addAttribute("adname", ad.getName());
//			return "Adminzone/changepassword";
//		} else {
//			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
//			return "redirect:/Adminlogin";
//		}
//	}
//
//	@PostMapping("/changepassword")
//	public String changePassword(HttpSession session, HttpServletRequest request, RedirectAttributes attributes) {
//		try {
//			AdminInfo adInfo = adrepo.findById(session.getAttribute("admin").toString()).get();
//			String oldpass = request.getParameter("oldpass");
//			String newpass = request.getParameter("newpass");
//			String confirmpass = request.getParameter("confirmpass");
//
//			if (newpass.equals(confirmpass)) {
//				if (oldpass.equals(adInfo.getPassword())) {
//					adInfo.setPassword(confirmpass);
//					adrepo.save(adInfo);
//					return "redirect:/Adminzone/adlogout";
//				} else {
//					attributes.addFlashAttribute("msg", "Invalid Old Password");
//				}
//			}
//
//			else {
//				attributes.addFlashAttribute("msg", "New Password and Confirm Password Not Match");
//			}
//			return "redirect:/Adminzone/changepassword";
//		} catch (Exception e) {
//			attributes.addFlashAttribute("msg", "Something went wrong" + e.getMessage());
//			return "redirect:/Adminzone/changepassword";
//		}
//
//	}

//=======Manage Result============================================================================================================//
	@GetMapping("/manageresult")
	public String ShowResult( HttpServletResponse response)
	{
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		return"adminzone/manageresult";
		}
	@GetMapping("/changepassword")
	public String Showchangepasswords(HttpSession session,Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("admin") != null)
		{
				return "Adminzone/changepassword";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/admin";
		}
	}


//=======Logout AdminInfo Profile=========================================================================================================//

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
