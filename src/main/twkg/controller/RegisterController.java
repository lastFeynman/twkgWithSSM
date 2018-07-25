package twkg.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twkg.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	private String register() {
		return "register";
	}
	
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	private String registerDo(@RequestParam("username") String userName, @RequestParam("password") String password, 
			@RequestParam("email") String email, HttpServletRequest request) {
		try {
			userName = new String(userName.getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String status = userService.registerAndInsertUser(userName, password, email);
		if(status.equals("succeed"))
			return "redirect:login?error=3";
		else if(status.equals("emailerror"))
			return "redirect:register?error=1";
		else if(status.equals("nameerror"))
			return "redirect:register?error=2";
		else
			return "redirect:register";
	}
}
