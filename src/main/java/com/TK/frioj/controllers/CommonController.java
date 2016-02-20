package com.TK.frioj.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.TK.frioj.dao.ArticleDao;
import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SessionDao;
import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.ShoutBoxDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.TagsDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.Session;
import com.TK.frioj.entities.Submission;
import com.TK.frioj.entities.User;
import com.TK.frioj.entities.UserRegistrationDTO;
import com.TK.frioj.enums.Languages;
import com.TK.frioj.enums.Roles;
import com.TK.frioj.enums.SubmissionStatus;
import com.TK.frioj.helpers.AuthorizationHelper;
import com.TK.frioj.helpers.HomePageHelper;
import com.TK.frioj.helpers.SessionHelper;
import com.TK.frioj.helpers.StringHelper;
import com.TK.frioj.services.MailService;
import com.TK.frioj.services.ProblemStatisticsService;
import com.TK.frioj.services.SubmissionService;
import com.TK.frioj.services.UserService;
import com.TK.frioj.services.UserStatisticsService;

@Controller
public class CommonController {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	private ProblemDao problemDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private SubmissionDao submissionDao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private ArticleDao articleDao;

	@Autowired
	private ShoutBoxDao shoutBoxDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private ProblemStatisticsService problemStatisticsService;

	@Autowired
	private SettingsDao settingsDao;

	@Autowired
	private MailService mailService;

	@Autowired
	private UserStatisticsService userStatisticsService;

	@Autowired
	private TagsDao tagsDao;

	@Autowired
	private SubmissionService submissionService;

	@RequestMapping("/displayAllSubmissions")
	public String displayAllSubmissions(@RequestParam(defaultValue = "1") int page, Model model) {
		int userId = userDao.getUserId(AuthorizationHelper.getCurrentUserName());
		ArrayList<Submission> submissionList = new ArrayList<Submission>(submissionDao.getAllUserSubmissions(userId));
		ArrayList<Submission> temp = new ArrayList<Submission>(40);

		for (Submission sub : submissionList) {
			if ((sub.getStatus() == SubmissionStatus.WA && StringHelper.isWaDueToDifferentSize(sub))
					|| sub.getStatus() == SubmissionStatus.RTE || sub.getStatus() == SubmissionStatus.CE) {
			} else {
				sub.setErr("");
			}
			if (sub.getErr().startsWith("SYSCALL")) {
				if (sub.getErr().startsWith("SYSCALL -1")) {
					sub.setErr("");
				} else {
					sub.setErr("forbidden syscall, please contact administrator");
				}
			}
			if (sub.getErr().contains("java.security")) {
				sub.setErr("forbidden operation, please contact administrator");
			}
		}

		int maxSubsPerPage = settingsDao.getMaxSubmissionsPerPageCount();
		int maxPageNum = submissionList.size() / maxSubsPerPage;
		if (maxPageNum * maxSubsPerPage != submissionList.size())
			maxPageNum++;
		if (page < 1 || page > maxPageNum) {
			page = 1;
		}

		for (int i = (page - 1) * maxSubsPerPage; i < Math.min(page * maxSubsPerPage, submissionList.size()); i++) {
			temp.add(submissionList.get(i));
		}
		submissionList = temp;

		model.addAttribute("currentPage", page);
		model.addAttribute("pages", maxPageNum);
		model.addAttribute("isJuniorPlusEnabled", settingsDao.isJuniorPlusEnabled());
		model.addAttribute("submissionList", submissionList);
		return "displayAllSubmissions";
	}

	@RequestMapping("/displayAllSessions")
	public String displayAllSessions(Model model) {
		ArrayList<Session> sessionList = null;

		if (AuthorizationHelper.isTeacherOrAdmin()) {
			sessionList = new ArrayList<Session>(sessionDao.getAllSessions());
		} else {
			if (AuthorizationHelper.isJuniorOrSenior()) {
				sessionList = new ArrayList<Session>(
						sessionDao.getAllUserSessions(userDao.getUserId(AuthorizationHelper.getCurrentUserName())));

			} else {
				return "redirect:/";
			}
		}

		model.addAttribute("sessionList", sessionList);
		return "displayAllSessions";
	}

	@RequestMapping("/session/{sessionId}")
	public String session(@PathVariable int sessionId, Model model, Principal principal) {

		if (AuthorizationHelper.isNotLogged())
			return "redirect:/";
		Session session;
		try {
			session = sessionDao.getSession(sessionId);

		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/";
		}

		List<String> sessionMembersNames;

		if (session.getMembers().size() > 0) {
			sessionMembersNames = sessionDao.getSessionMembersLogins(sessionId);
		} else {
			sessionMembersNames = new ArrayList<String>();
		}
		ArrayList<Submission> sesSubs = new ArrayList<Submission>(submissionDao.getAllSessionSubmissions(session));

		int acToAllTAble[][][] = SessionHelper.submissionsToAcToAllTable(sesSubs, session.getMembers(),
				session.getProblems(), settingsDao.getPenalization(), session, sessionMembersNames);
		model.addAttribute("acToAllTable", acToAllTAble);

		model.addAttribute("sessionMembersNames", sessionMembersNames);

		if (AuthorizationHelper.isTeacherOrAdmin()) {
			model.addAttribute("session", session);
			model.addAttribute("sessionId", sessionId);
			return "session";
		}

		List<Integer> userSessionsIds = sessionDao.getAllUserSesionsIds(userDao.getUserId(principal.getName()));
		if (!userSessionsIds.contains(sessionId))
			return "redirect:/";

		model.addAttribute("session", session);
		model.addAttribute("sessionId", sessionId);
		return "session";
	}

	@RequestMapping("/links")
	public String links(Model model) {

		return "links";
	}

	@RequestMapping("/profile")
	public String profile(@RequestParam(value = "name", defaultValue = "") String name, Model model) {

		Roles roleOfViewer = userDao.getUserRole(AuthorizationHelper.getCurrentUserName());

		if (name.equals("")) {
			name = AuthorizationHelper.getCurrentUserName();
			model.addAttribute("displayUpdateButton", true);

		} else {
			model.addAttribute("displayUpdateButton", false);
		}

		if (!name.matches("[a-zA-Z0-9]*"))
			return "redirect:/";
		User user = null;
		String solvedProblems = "";
		try {
			user = userDao.getUser(name);
			solvedProblems = StringHelper.addCommaDelim(new ArrayList<Integer>(
					submissionDao.getAllUserSubmissionNumbersOfStatus(user.getUserId(), SubmissionStatus.AC)));
			if (solvedProblems.equals("-1"))
				solvedProblems = "";
		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/";
		}

		if ((roleOfViewer == Roles.Junior || roleOfViewer == Roles.Senior)
				&& (user.getRole().equals(Roles.Teacher) || user.getRole().equals(Roles.Admin))) {
			return "redirect:/";
		}

		if (!user.getEnabled()) {
			model.addAttribute("role", "banned");
		} else {
			model.addAttribute("role", user.getRole().toString());
		}
		model.addAttribute("userStatistics", userStatisticsService.getUserStatistics(userDao.getUserId(name)));
		model.addAttribute("solvedProblems", solvedProblems);
		model.addAttribute("user", user);

		return "profile";
	}

	@RequestMapping("/updateProfileForm")
	public String updateProfileForm(Model model) {

		model.addAttribute("user", userDao.getUser(AuthorizationHelper.getCurrentUserName()));
		return "updateProfileForm";
	}

	@RequestMapping(value = "/updateProfile", method = RequestMethod.POST)
	public String updateProfile(@RequestParam(defaultValue = "") String userName,
			@RequestParam(defaultValue = "") String email, @RequestParam(defaultValue = "") String info, Model model) {

		User user = userDao.getUser(AuthorizationHelper.getCurrentUserName());
		user.setUserName(userName);
		user.setEmail(email);

		if (info.length() > 1500) {
			user.setInfo(info.substring(0, 1500));
		} else {
			user.setInfo(info);
		}
		userDao.updateUser(user);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/registrationForm")
	public String registrationForm(Model model) {

		model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
		return "registration";
	}

	@RequestMapping(value = "/registration")
	public String registration(@Valid UserRegistrationDTO user,
			BindingResult result, @RequestParam(value = "g-recaptcha-response")String rcResponse, Model model) {
		
		;
		if (result.hasErrors()) {

			model.addAttribute("userRegistrationDTO", user);
			return "registration";
		} else {
			
			String RECAPTCHA_VERIF_URL = "https://www.google.com/recaptcha/api/siteverify";
			MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
	        map.add("secret", "6LeD0hgTAAAAAGrTgKbRDrngurNpQi4zbeQE803r");
	        map.add("response", rcResponse);
	 
	        RestTemplate restTemplate = new RestTemplate();
	 
	        String s = restTemplate.postForObject(RECAPTCHA_VERIF_URL, map, String.class);
	        logger.info(s);
	        if(!s.contains("\"success\": true"))return "registration";
			
			
			if (userDao.existUser(user.getLogin()) == 1) {
				model.addAttribute("message",
						"User already exists, please choose another login.");
			} else {
				
				if (userDao.existEmail(user.getEmail()) >= 1) {
					model.addAttribute("message", "Email already in use, please enter another email.");
				}else{
				User newUser = new User(user.getLogin(), user.getLogin(),
						user.getEmail(), "", passwordEncoder.encode(user
								.getPassword()));
				newUser.setName(user.getName());
				newUser.setSurname(user.getSurname());
				userDao.addUser(newUser);
				model.addAttribute("message", "successful registration");
				model.addAttribute("userRegistrationDTO",
						new UserRegistrationDTO());
				return "registration";
			}
			}
		}

		model.addAttribute("user", new UserRegistrationDTO());
		return "registration";
	}

	@RequestMapping(value = "/rankList")
	public String rankList(@RequestParam(defaultValue = "20") int topN, Model model) {

		if (topN < 1 || topN > 100)
			topN = 20;

		model.addAttribute("rankList", userService.getTopNUsers(topN));
		return "rankList";
	}

	@RequestMapping(value = "/searchProblem")
	public String searchProblem(Model model) {

		return "searchProblem";
	}

	@RequestMapping("/displayAllTutorials")
	public String displayAllTutorials(Model model) {

		model.addAttribute("articleList", articleDao.getAllTutorials());
		return "displayAllArticles";
	}

	@RequestMapping("/contactMeForm")
	public String contactMe(Model model) {
		return "contactMe";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/sendMsg")
	public String sendMsg(@RequestParam(defaultValue = "") String content, Model model) {
		if (!content.equals("")) {
			if (content.length() > 2000) {
				mailService.sendMailToAdmin(content.substring(0, 1999));
			} else {
				mailService.sendMailToAdmin(content);
			}

			String response = "Message sent.";
			model.addAttribute("response", response);
		}

		return "contactMe";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/updatePassword")
	public String updatePassword(@RequestParam(defaultValue = "") String currentPass,
			@RequestParam(defaultValue = "") String newPass1, @RequestParam(defaultValue = "") String newPass2,
			Model model) {

		String response = userService.updatePassword(currentPass, newPass1, newPass2);
		model.addAttribute("response", response);

		return "updatePasswordForm";
	}

	@RequestMapping("/updatePasswordForm")
	public String updatePasswordForm(Model model) {

		return "updatePasswordForm";
	}

	@RequestMapping("/error")
	public String customError(HttpServletRequest request, HttpServletResponse response, Model model) {
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");

		if (throwable != null) {
			throwable.printStackTrace();
		}

		model.addAttribute("response", "error code " + statusCode + " ");
		return "error";
	}

	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("content",
				HomePageHelper.getHomePageContent(settingsDao.getFriojFilesLocation() + "homePage.txt"));
		return "home";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public String showLogin() {
		return "login";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/submit")
	public String submit(@RequestParam(defaultValue = "") String problemId, Model model) {
		model.addAttribute("problemId", problemId);
		return "submit";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/submitProblem")
	public String submitProblem(@RequestParam(value = "problemId", required = false) String id,
			@RequestParam(value = "code", required = false) String sourceCode,
			@RequestParam(value = "lang") Languages lang, Model model) {

		String response = submissionService.runSubmission(id, sourceCode, lang,
				AuthorizationHelper.getCurrentUserName());

		model.addAttribute("response", response);

		return "submit";
	}

}
