package com.TK.frioj.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.TK.frioj.dao.ArticleDao;
import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SessionDao;
import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.Problem;
import com.TK.frioj.entities.Session;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.entities.User;
import com.TK.frioj.enums.ProblemStatus;
import com.TK.frioj.enums.Roles;
import com.TK.frioj.helpers.AuthorizationHelper;
import com.TK.frioj.systemServices.SystemHelper;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = LoggerFactory
			.getLogger(AdminController.class);

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private SubmissionDao submissionDao;

	@Autowired
	private SettingsDao settingsDao;
	
	@Autowired
	private SystemHelper systemHelper;

	
	@RequestMapping(method = RequestMethod.GET, value = "/displaySessionSubmissions")
	public String displaySessionSubmissions(@RequestParam(value = "sessionId", defaultValue="-1")int sessionId,Model model){
		
		List<Submission> subs;
		Session session = sessionDao.getSession(sessionId);
		if(session == null){
			subs = new LinkedList<>();
		}else{
			subs = submissionDao.getAllSessionSubmissions(session);
			for (Submission submission : subs) {
				//TODO change dao to support this opperation
				submission.setUserName(userDao.getUserName(submission.getUserId()));
			}
		}
		
		model.addAttribute("submissions", subs);
				
		return "displaySessionSubmissions";
		
	}
	
	
	
	
	@RequestMapping("/addProblemForm")
	public String addProblemForm(Model model) {

		model.addAttribute("PageTitle", "addProblem");
		return "addProblemForm";
	}
	
	
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/addProblemInOutForm")
	public String addProblemInOutForm(Model model){
		
		return "inoutUpLoad";
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/addProblemInOut")
	public String addProblemInOut(
			@RequestParam(value = "problemId", defaultValue = "-1") int problemId,
			@RequestParam(value = "inFile", required = false) MultipartFile inFile,
			@RequestParam(value = "outFile", required = false) MultipartFile outFile, Model model){
		
		if(problemId < 0){
			model.addAttribute("msg","Problem id not entered.");
			return "inoutUpLoad";
		}
		
		byte[] file = null;

		if (!inFile.isEmpty()) {
			try {
				file = inFile.getBytes();
				
				systemHelper.createFile(problemId+".in", settingsDao.getInLocation(), file);

			} catch (Exception e) {
				file = null;
			}
		}
		
		if (!outFile.isEmpty()) {
			try {
				file = outFile.getBytes();
				systemHelper.createFile(problemId+".out", settingsDao.getOutLocation(), file);

			} catch (Exception e) {
				file = null;
			}
		}
		
		
		
		return "redirect:addProblemInOutForm";
	}
			

	@RequestMapping(method = RequestMethod.POST, value = "/addProblem")
	public String addProblem(
			@RequestParam(value = "pdf", required = false) MultipartFile file,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "note", defaultValue = "") String note,
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "input", defaultValue = "") String input,
			@RequestParam(value = "output", defaultValue = "") String output,
			@RequestParam(value = "timeLimit", defaultValue = "0") int timeLimit,
			Model model) {

		int setterId = userDao.getUserId(AuthorizationHelper
				.getCurrentUserName());
		Problem problem;
		byte[] pdf = null;

		if (!file.isEmpty()) {
			try {
				pdf = file.getBytes();

			} catch (Exception e) {
				pdf = null;
			}
		} else {
		}
		problem = new Problem(setterId, name, note, text, input, output,
				timeLimit, pdf);

		problemDao.addProblem(problem);

		return "redirect:addProblemForm";
	}

	@RequestMapping("/updateProblemForm")
	public String updateProblemForm(@RequestParam("problemId") int problemId,
			Model model) {
		Problem problem;

		try {
			problem = problemDao.getProblem(problemId);
		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/allProblems";
		}

		model.addAttribute("problem", problem);
		model.addAttribute("problemId", problem.getProblemId());
		model.addAttribute("stav", "" + problem.getStatus());
		model.addAttribute("PageTitle", "update problem form");
		return "updateProblemForm";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updateProblem")
	public String updateProblem(
			@RequestParam(value = "pdf", required = false) MultipartFile file,
			@RequestParam(value = "problemId") int problemId,
			@RequestParam(value = "newPdf", defaultValue = "false") boolean replacePdf,
			@RequestParam(value = "status", defaultValue = "") int status,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "note", defaultValue = "") String note,
			@RequestParam(value = "text", defaultValue = "") String text,
			@RequestParam(value = "input", defaultValue = "") String input,
			@RequestParam(value = "output", defaultValue = "") String output,
			@RequestParam(value = "timeLimit", defaultValue = "0") int timeLimit,
			@RequestParam(value = "memoryLimit", defaultValue = "0") int memoryLimit,
			@RequestParam(value = "maxInputFileSize", defaultValue = "0") int maxInputFileSize,
			Model model) {

		/*try {
			name = (new String(name.getBytes("iso-8859-1"), "UTF-8"));
			text = (new String(text.getBytes("iso-8859-1"), "UTF-8"));
			input = (new String(input.getBytes("iso-8859-1"), "UTF-8"));
			output = (new String(output.getBytes("iso-8859-1"), "UTF-8"));
		} catch (UnsupportedEncodingException e1) {

			e1.printStackTrace();
		}*/

		int setterId = userDao.getUserId(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		Problem problem;
		byte[] pdf = null;

		problem = new Problem(setterId, name, note, text, input, output,
				timeLimit, pdf);
		problem.setProblemId(problemId);
		problem.setMemoryLimit(memoryLimit);
		problem.setMaxInputFileSize(maxInputFileSize);
		problem.setStatus(ProblemStatus.getProblemStatus(status));

		if (replacePdf) {
			if (!file.isEmpty()) {
				try {
					pdf = file.getBytes();

				} catch (Exception e) {
					pdf = null;
				}
			} else {
			}
			problem.setPdf(pdf);
			problemDao.updateProblem(problem);
		} else {
			problemDao.updateProblemWithoutPdf(problem);
		}

		return "redirect:/allProblems";
	}

	@RequestMapping("/addSessionForm")
	public String addSessionForm(Model model) {

		return "addSessionForm";
	}

	@RequestMapping("/addSession")
	public String addSession(@RequestParam("name") String name, Model model) {

		sessionDao.addSession(new Session(0, name, DateTime.now(), DateTime
				.now().plusHours(4)));

		return "redirect:/displayAllSessions";
	}

	@RequestMapping("/updateSessionForm/{sessionId}")
	public String updateSessionForm(@PathVariable int sessionId, Model model) {
		Session session;
		try {
			session = sessionDao.getSession(sessionId);
		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/";
		}

		model.addAttribute("session", session);
		model.addAttribute("sessionId", sessionId);
		return "updateSessionForm";
	}

	@RequestMapping("/deleteSession/{sessionId}")
	public String deleteSession(@PathVariable int sessionId, Model model) {
		Session session;
		try {
			session = sessionDao.getSession(sessionId);
			if (session != null)
				sessionDao.removeSession(sessionId);
		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/";
		}
		return "redirect:/displayAllSessions";
	}

	@RequestMapping("/getSession/{sessionId}")
	public @ResponseBody String getSession(@PathVariable int sessionId,
			Model model) {
		Session session = null;
		try {
			session = sessionDao.getSession(sessionId);
		} catch (EmptyResultDataAccessException ex) {

		}

		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		String json = "";
		JSONArray memNamesList = new JSONArray();
		for (Integer id : session.getMembers()) {
			memNamesList.put(userDao.getUserName(id));
		}

		JSONArray problemIdAndNamePair;
		JSONArray problemNamesAndIdsList = new JSONArray();
		for (Integer id : session.getProblems()) {
			problemIdAndNamePair = new JSONArray();
			problemIdAndNamePair.put(id);
			problemIdAndNamePair.put(problemDao.getProblem(id).getName());
			problemNamesAndIdsList.put(problemIdAndNamePair);
		}

		JSONObject obj = new JSONObject();
		try {
			json = ow.writeValueAsString(session);
			obj = new JSONObject(json);
			obj.put("userNames", memNamesList);
			obj.put("problemNames", problemNamesAndIdsList);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj.toString();
	}

	@RequestMapping("/getAllUserNames")
	public @ResponseBody String getAllUserNames(Model model) {
		String json = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(userDao.getAllJuniorAndSeniorUsers());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping("/getAllProblemNames")
	public @ResponseBody String getAllProblemNames(Model model) {
		String json = "";
		ObjectWriter ow = new ObjectMapper().writer()
				.withDefaultPrettyPrinter();
		try {
			json = ow.writeValueAsString(problemDao.getAllProblemNamesAndIds());

		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	@RequestMapping(value = "/updateSession", method = RequestMethod.POST)
	public String updateSession(@RequestParam("sessionId") int sessionId,
			@RequestParam("name") String name,
			@RequestParam("start") String start,
			@RequestParam("end") String end,
			@RequestParam Map<String, String> allRequestParams, Model model) {

		
		
		Session session = new Session(sessionId, name, DateTime.parse(start),
				DateTime.parse(end));

		for (String key : allRequestParams.keySet()) {
			if (key.startsWith("pS"))
				session.addProblem(Integer.parseInt(allRequestParams.get(key)));
			if (key.startsWith("uS"))
				session.addMember(userDao.getUserId(allRequestParams.get(key)));
		}

		sessionDao.updateSession(session);

		return "redirect:/displayAllSessions";
	}

	@RequestMapping("/updateUserRolesAndStatusesForm")
	public String updateUserRolesAndStatusesForm(Model model) {

		model.addAttribute("userList",
				userDao.getAllUsersRolesAndStatusesExcept0());
		return "updateUserRolesAndStatusesForm";
	}

	@RequestMapping("/updateUserRolesAndStatuses")
	public String updateUserRolesAndStatuses(
			@RequestParam Map<String, String> map, Model model) {

		List<User> userList = userDao.getAllUsersRolesAndStatusesExcept0();

		Integer val;
		Roles role;
		for (User user : userList) {
			try {
				val = Integer.parseInt(map.get("" + user.getUserId()));
			} catch (Exception ex) {
				val = null;
			}

			if (val == null)
				continue;

			if (val == 0 && user.getEnabled())
				userDao.changeUserStatus(user.getUserId(), false);

			if (val != 0 && !user.getEnabled())
				userDao.changeUserStatus(user.getUserId(), true);
			role = Roles.getRole(val);

			if (val != 0 && !role.equals(user.getRole()))
				userDao.changeUserRole(user.getUserId(), role);
		}

		return "redirect:/admin/updateUserRolesAndStatusesForm";
	}

	@RequestMapping("/submission")
	public String submission(
			@RequestParam(defaultValue = "") String submissionId, Model model) {
		Submission sub = null;
		if (!submissionId.equals("")) {
			try {
				int subId = Integer.parseInt(submissionId);
				sub = submissionDao.getSubmission(subId);
			} catch (NumberFormatException ex) {

			} catch (EmptyResultDataAccessException e) {

			}
		}

		model.addAttribute("submission", sub);
		model.addAttribute("senderName",
				(sub == null) ? "" : userDao.getUserName(sub.getUserId()));
		return "submission";
	}

}
