package twkg.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.entity.User;
import twkg.service.CoverSongService;
import twkg.service.SongService;
import twkg.service.UserService;

@Controller
public class SearchController {
	private static final int MAX_ROW = 13;
	@Autowired
	private SongService songService;
	
	@Autowired
	private CoverSongService coverSongService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/search", method = {RequestMethod.POST,RequestMethod.GET})
	private String searchPost(@RequestParam() String searchContent, @RequestParam() String searchType, 
			@RequestParam() String currentPage, HttpServletRequest request) {
		if(!searchType.equals("song") && !searchType.equals("cover")) {
			request.setAttribute("errMsg", "param error");
			return "error";
		}
		int page;
		try {
			page = Integer.parseInt(currentPage);
		}catch (Exception e) {
			request.setAttribute("errMsg", "param Error");
			return "error";
		}
		request.setAttribute("currentPage", page);
		
		String maxPageStr = request.getParameter("maxPage");
		int maxPage = 0;
		try {
			maxPage = Integer.parseInt(maxPageStr);
		} catch (Exception e) {
			//do nothing
		}
		int resultCount = 0;
		if(maxPage == 0) {
			if(searchType.equals("song")) {
				resultCount = songService.getSearchCount(searchContent);
			}else {
				resultCount = coverSongService.getSearchCount(searchContent);
			}
		}
		if(resultCount%MAX_ROW == 0 && resultCount/MAX_ROW != 0)
			maxPage = resultCount/MAX_ROW;
		else 
			maxPage = resultCount/MAX_ROW + 1;
		request.setAttribute("maxPage", maxPage);
		
		List<Song> songs = null;
		List<CoverSong> coverSongs = null;
		List<User> users = null;
		if(searchType.equals("song")) {
			songs = songService.searchSong(searchContent, page, MAX_ROW);
			request.setAttribute("songs", songs);
		}else {
			coverSongs = coverSongService.searchCoverSong(searchContent, page, MAX_ROW);
			songs = songService.getSongsByCoverSongs(coverSongs);
			users = userService.getUsersByCoverSongs(coverSongs);
			request.setAttribute("coverSongs", coverSongs);
			request.setAttribute("songs", songs);
			request.setAttribute("users", users);
		}
		
		return "searchResult";
	}
}
