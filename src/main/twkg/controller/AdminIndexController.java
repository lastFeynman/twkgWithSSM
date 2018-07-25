package twkg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminIndexController {
	
	@RequestMapping(value = "/admin/index", method = RequestMethod.GET)
	private String adminIndex() {
		return "admin/adminIndex";
	}
}
