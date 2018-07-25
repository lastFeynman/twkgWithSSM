package twkg.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twkg.entity.User;
import twkg.service.CookieService;
import twkg.service.UserService;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private CookieService cookieService;
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	private String loginDo(@RequestParam("username") String userName, @RequestParam("password") String password, 
			HttpServletRequest request, HttpServletResponse response) {
		User user = null;
		try {
			user = userService.verifyUserInfo(new String(userName.getBytes("ISO8859-1"), "UTF-8"), password);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(user == null) {
			return "redirect:login?error=1";
		}
		HttpSession session = request.getSession();
		session.setAttribute("currentUser", user);
		Cookie cookie = cookieService.generateAndInsertCookie(user,request.getParameter("autologin"));
		response.addCookie(cookie);
		
		if(user.isAdmin())
			return "redirect:admin/adminIndex";
		else
			return "redirect:index";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String login() {
		return "login";
	}
}
