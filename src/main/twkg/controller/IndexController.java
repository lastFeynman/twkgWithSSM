package twkg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import twkg.entity.Song;
import twkg.entity.User;
import twkg.service.SongService;
import twkg.service.UserService;

@Controller
public class IndexController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SongService songService;
	
	@RequestMapping(value = {"/index","/"}, method = RequestMethod.GET)
	private String index(HttpServletRequest request) {
		List<User> users = userService.getMostPopularUser(5);
		List<Song> songs = songService.getHotestSong(8);
		request.setAttribute("songs", songs);
		request.setAttribute("users", users);
		
		return "index";
	}
}
