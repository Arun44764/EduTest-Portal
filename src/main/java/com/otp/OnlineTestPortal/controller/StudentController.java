
package com.otp.OnlineTestPortal.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

import com.google.gson.Gson;
import com.otp.OnlineTestPortal.Dto.ResponseDto;
import com.otp.OnlineTestPortal.Repositary.QuestionBankRepo;
import com.otp.OnlineTestPortal.Repositary.ResponseRepo;
import com.otp.OnlineTestPortal.Repositary.StudentInfoRepo;
import com.otp.OnlineTestPortal.Repositary.TestInfoRepo;
import com.otp.OnlineTestPortal.Repositary.TestResultRepo;
import com.otp.OnlineTestPortal.model.QuestionBank;
import com.otp.OnlineTestPortal.model.Response;
import com.otp.OnlineTestPortal.model.StudentInfo;
import com.otp.OnlineTestPortal.model.TestInfo;
import com.otp.OnlineTestPortal.model.TestResult;

import jakarta.mail.Session;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller

@RequestMapping("/studentzone")
public class StudentController {

	@Autowired
	StudentInfoRepo stdrepo;

	@Autowired
	private TestInfoRepo testrepo;
	
	@Autowired
	private QuestionBankRepo qbrepo;
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Autowired
	ResponseRepo resrepo;
	
	@Autowired
	TestResultRepo resultrepo;

	

	@GetMapping("/stdhome")
	public String ShowStudentDashboard(HttpSession session, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");
		if (session.getAttribute("student") != null) {
			StudentInfo st = stdrepo.getById(session.getAttribute("student").toString());
			model.addAttribute("st", st);
			model.addAttribute("profilepic", st.getProfilepic());
			model.addAttribute("stdname", st.getName());
			model.addAttribute("stdbranch", st.getBranch());
			model.addAttribute("stdemail", st.getEmail());
			model.addAttribute("stdphone", st.getContactno());
			model.addAttribute("stdmode", st.getTrainingmode());
			return "studentzone/studentdashboard";
		}

		else {
			redirectAttributes.addFlashAttribute("msg", "Your session has been Ended");
			return "redirect:/studentlogin";
		}
	}

	@PostMapping("/stdhome")
	public String UploadPic(@RequestParam MultipartFile profilepic, HttpSession session, RedirectAttributes attributes)
			throws IOException {
		try {
			String storageFileName = profilepic.getOriginalFilename();
			String UploadDir = "public/profiles/";
			Path UploadPath = Paths.get(UploadDir);
			if (!Files.exists(UploadPath)) {
				Files.createDirectories(UploadPath);
			}

			try (InputStream inputStream = profilepic.getInputStream()) {
				Files.copy(inputStream, Paths.get(UploadDir + storageFileName), StandardCopyOption.REPLACE_EXISTING);
			}
			StudentInfo stdinfo = stdrepo.findById(session.getAttribute("student").toString()).get();
			stdinfo.setProfilepic(storageFileName);
			stdrepo.save(stdinfo);
			attributes.addFlashAttribute("msg", "Profile Pic Uploaded Successfully");
			return "redirect:/studentzone/stdhome";

		} catch (Exception e) {
			attributes.addFlashAttribute("msg", e.getMessage());
			return "redirect:/studentzone/stdhome";

		}
	}

	@GetMapping("/givetest")
	public String GiveTest(HttpSession session, RedirectAttributes attributes, Model model,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("student") != null) {
       StudentInfo stdinfo=stdrepo.getById(session.getAttribute("student").toString());
       model.addAttribute("stdinfo", stdinfo);
			return "studentzone/givetest";
		} else {
			attributes.addFlashAttribute("msg", "Sesssion has been expired");
			return "redirect:/studentlogin";
		}
	}
	private long tstid;

	@GetMapping("/testover")
	public String TestOver(HttpSession session, @RequestParam("t") int t, @RequestParam("s") int s,Model model, RedirectAttributes attributes)
	{
		if(session.getAttribute("student")!=null)
		{
		TestInfo testInfo=testrepo.getById(tstid);
		StudentInfo stdinfo=stdrepo.getById(session.getAttribute("student").toString());
		TestResult result= new TestResult();
		result.setEmail(stdinfo.getEmail());
		result.setName(stdinfo.getName());
		result.setContactno(stdinfo.getContactno());
		result.setCollegename(stdinfo.getCollegename());
		result.setCourse(stdinfo.getCourse());
		result.setBranch(stdinfo.getBranch());
		result.setYear(stdinfo.getYear());
		result.setStatus("true");
		result.setTotalmarks(t);
		result.setGetmarks(s);
		result.setTestname(testInfo.getTestname());
		resultrepo.save(result);
		model.addAttribute("stdinfo", stdinfo);
		return "studentzone/testover";
		

		}
		else {
			attributes.addFlashAttribute("msg", "Session has been expired");
			return "redirect:/studentlogin";
		}
	}

//==============Test Start Code==========/
	@GetMapping("/starttest")
	public String startTest(@RequestParam long testid, HttpSession session, RedirectAttributes attributes,
			Model model) {
		if (session.getAttribute("student") != null) {
			StudentInfo stdinfo = stdrepo.getById(session.getAttribute("student").toString());
			model.addAttribute("stdinfo", stdinfo);
			try {
				TestInfo tstinfo = testrepo.getById(testid);
				tstid = testid;
				if (tstinfo.isActive()) {
					if (stdinfo.getCourse().equals(tstinfo.getCourse())
							&& stdinfo.getBranch().equals(tstinfo.getBranch())
							&& stdinfo.getYear().equals(tstinfo.getYear()))
					{
					 String year=stdinfo.getYear();
					 int numberOfQuestion = tstinfo.getNumberofquestion();
					 
					 List<QuestionBank> qblist = qbrepo.findQuestionbyYear(year,numberOfQuestion,entityManager);
					 Gson gson = new Gson(); //gson is a class to convert json file
					 String json = gson.toJson(qblist);
					 model.addAttribute("json", json);
					 model.addAttribute("testname", tstinfo.getTestname());
					 model.addAttribute("tt", (double)qblist.size()/2); // time ko half krne ke liye total number of question divide by 2
					 model.addAttribute("tq", qblist.size());
					 return "studentzone/starttest";

					} else {
						attributes.addFlashAttribute("msg", "This test is not schduled for you.");
						return "redirect:/studentzone/givetest";

					}
				}

				else {
					attributes.addFlashAttribute("msg", "There is no test is active with this id.");
					return "redirect:/studentzone/givetest";

				}
			} catch (Exception e) {
				attributes.addFlashAttribute("msg", "There is no test found with this id.");
				return "redirect:/studentzone/givetest";

			}
		} else {
			attributes.addFlashAttribute("msg", "Sesssion has been expired");
			return "redirect:/studentlogin";
		}
	}

	@GetMapping("/collegevideo")
	public String Showcollegevideo() {
		return "studentzone/collegevideo";
	}

	@GetMapping("/studentresult")
	public String ShowResult(HttpServletResponse response, HttpSession session,Model model) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");
		if (session.getAttribute("student") != null) {
			StudentInfo stdInfo = stdrepo.getById(session.getAttribute("student").toString());
            model.addAttribute("stdInfo", stdInfo);
            TestResult result= resultrepo.findResultByEmail(stdInfo.getEmail());
            model.addAttribute("result", result);
		return "studentzone/seeresult";
	}
		else {
			return "redirect:/studentlogin";
		}
	}

	@GetMapping("/giveresponse")
	public String GiveResponse(HttpSession session, Model model,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("student") != null) {

//		StudentInfo st = stdRepo.getById(session.getAttribute("student").toString());
//		model.addAttribute("stdname", st.getName());

		ResponseDto dto = new ResponseDto();
		model.addAttribute("dto", dto);
		return "studentzone/giveresponse";
	}
		else
		{
			return "redirect:/studentlogin";

			}
		}

	@PostMapping("/giveresponse")
	public String ShowResponse(HttpSession session, Model model, @Valid @ModelAttribute ResponseDto dto,
			RedirectAttributes attributes) {
		try {
			Response res = new Response();
			res.setName(dto.getName());
			res.setEnrollmentno(dto.getEnrollmentno());
			res.setEmail(dto.getEmail());
			res.setContactno(dto.getContactno());
			res.setResponsetype(dto.getResponsetype());
			res.setSubject(dto.getSubject());
			res.setMessage(dto.getMessage());
			resrepo.save(res);
			attributes.addFlashAttribute("msg", "Successfully");
			return "redirect:/studentzone/giveresponse";
		} catch (Exception e) {
			attributes.addFlashAttribute("msg", "Wrong" + e.getMessage());

			return "redirect:/studentlogin";
		}
	}


	@GetMapping("/changepassword")
	public String Showchangepasswords(HttpSession session, Model model, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache no-store,must-revalidate");

		if (session.getAttribute("student") != null) {
			return "studentzone/changepassword";
		} else {
			redirectAttributes.addFlashAttribute("msg", "Your Session has been Ended.");
			return "redirect:/studentlogin";
		}
	}

	@PostMapping("/changepassword")
	public String changePassword(HttpSession session, HttpServletRequest request, RedirectAttributes attributes) {
		if (session.getAttribute("student") != null) {
			StudentInfo st = stdrepo.getById(session.getAttribute("studentlogin").toString());
			String oldpass = request.getParameter("oldpass");
			String newpass = request.getParameter("newpass");
			String confirmpass = request.getParameter("confirmpass");
			if (!newpass.equals(confirmpass)) {
				attributes.addFlashAttribute("msg", "New password and confirm password are not matched...");
				return "redirect:/studentzone/changepassword";
			}
			if (!oldpass.equals(st.getPassword())) {
				attributes.addFlashAttribute("msg", "Old password is not matched...");
				return "redirect:/studentzone/changepassword";
			}
			st.setPassword(newpass);
			stdrepo.save(st);
			return "redirect:/studentzone/stdlogout";
		} else {
			return "redirect:/studentlogin";
		}
	}

	@GetMapping("/stdlogout")
	public String Logout(HttpSession session, RedirectAttributes attributes) {
		if (session.getAttribute("student") != null) {

			session.invalidate();
			attributes.addFlashAttribute("msg", "Successfully Logout");
			return "redirect:/studentlogin";
		} else {

			attributes.addFlashAttribute("Msg", "Session Expired");
			return "redirect:/studentlogin";
		}
	}
}