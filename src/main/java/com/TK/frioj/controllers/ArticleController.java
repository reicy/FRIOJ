package com.TK.frioj.controllers;

import java.util.ArrayList;

import org.joda.time.DateTime;
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

import com.TK.frioj.dao.ArticleDao;
import com.TK.frioj.dao.ProblemDao;
import com.TK.frioj.dao.SessionDao;
import com.TK.frioj.dao.SettingsDao;
import com.TK.frioj.dao.SubmissionDao;
import com.TK.frioj.dao.UserDao;
import com.TK.frioj.entities.Article;
import com.TK.frioj.enums.Articles;
import com.TK.frioj.helpers.AuthorizationHelper;

@Controller
public class ArticleController {

	private static final Logger logger = LoggerFactory
			.getLogger(ArticleController.class);

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

	@RequestMapping("/admin/addArticleForm")
	public String createArticleForm(Model model) {

		model.addAttribute("article", new Article());
		return "articleForm";
	}

	/**
	 * updates and creates articles
	 * 
	 * @param articleId
	 * @param name
	 * @param note
	 * @param content
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/updateArticle", method = RequestMethod.POST)
	public String updateArticle(@RequestParam("articleId") int articleId,
			@RequestParam("name") String name,
			@RequestParam("note") String note,
			@RequestParam("content") String content,
			@RequestParam("status") int status,
			@RequestParam(value = "pre", defaultValue = "0") int pre,
			Model model) {

		// article doesn't exist
		if (articleId == -1) {
			System.out.println(status);
			Article article = new Article(0, name, note, content,
					SecurityContextHolder.getContext().getAuthentication()
							.getName(), DateTime.now(),
					Articles.getArticleType(status), pre);
			articleDao.addArticle(article);

		} else {

			Article article = new Article(articleId, name, note, content,
					SecurityContextHolder.getContext().getAuthentication()
							.getName(), DateTime.now(),
					Articles.getArticleType(status), pre);
			articleDao.updateArticle(article);

		}

		return "redirect:/displayAllArticles";
	}

	@RequestMapping("/admin/updateArticleForm/{articleId}")
	public String updateArticleForm(@PathVariable int articleId, Model model) {
		Article article;
		try {
			article = articleDao.getArticle(articleId);
		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/";
		}

		model.addAttribute("article", article);
		return "articleForm";
	}

	@RequestMapping("/admin/deleteArticle/{articleId}")
	public String deleteArticle(@PathVariable int articleId, Model model) {

		articleDao.deleteArticle(articleId);

		return "redirect:/displayAllArticles";
	}

	@RequestMapping("/displayAllArticles")
	public String displayAllArticles(
			@RequestParam(defaultValue = "1") int page, Model model) {

		ArrayList<Article> articleList = new ArrayList<Article>(
				articleDao.getAllArticles());
		ArrayList<Article> temp = new ArrayList<Article>(40);

		int maxArticlesPerPage = settingsDao.getMaxArticlesPerPageCount();
		int maxPageNum = articleList.size() / maxArticlesPerPage;
		if (maxPageNum * maxArticlesPerPage != articleList.size())
			maxPageNum++;
		if (page < 1 || page > maxPageNum) {
			page = 1;
		}

		for (int i = (page - 1) * maxArticlesPerPage; i < Math.min(page
				* maxArticlesPerPage, articleList.size()); i++) {
			temp.add(articleList.get(i));
		}
		articleList = temp;

		model.addAttribute("currentPage", page);
		model.addAttribute("pages", maxPageNum);

		model.addAttribute("articleList", articleList);
		return "displayAllArticles";
	}

	@RequestMapping("/article/{articleId}")
	public String getArticle(@PathVariable("articleId") int articleId,
			Model model) {
		Article article;
		try {
			article = articleDao.getArticle(articleId);
			model.addAttribute("prerequisiteMathed", true);
			if (article.getPre() > 0) {
				if (AuthorizationHelper.isJunior()
						&& !submissionDao.hasUserSolvedProblem(
								article.getPre(), userDao
										.getUserId(AuthorizationHelper
												.getCurrentUserName()))) {
					model.addAttribute("prerequisiteMathed", false);
				}
			}

		} catch (EmptyResultDataAccessException ex) {
			return "redirect:/";
		}

		if (article.getStatus() == Articles.Hidden
				&& AuthorizationHelper.isJuniorOrSenior())
			return "redirect:/displayAllArticles";
		model.addAttribute("article", article);
		return "article";

	}
	

}
