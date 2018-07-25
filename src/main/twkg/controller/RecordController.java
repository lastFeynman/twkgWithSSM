package twkg.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.service.CoverSongService;
import twkg.service.SongService;

@Controller
public class RecordController {
	@Autowired
	private SongService songService;
	
	@Autowired
	private CoverSongService coverSongService;
	
	@RequestMapping(value = "/record", method = RequestMethod.GET)
	private String record(@RequestParam("recordSong") String songIdStr, HttpServletRequest request) {
		Song song = songService.getSongById(songIdStr);
		if(song == null) {
			request.setAttribute("errMsg", "param error");
			return "error";
		}else {
			request.setAttribute("recordSong", song);
			return "record";
		}
	}
	
	@RequestMapping(value = "recordUpload.do", method = RequestMethod.POST)
	private String upload(@RequestParam("userId") String userId, @RequestParam("songId") String songId,
			@RequestParam("audioData") CommonsMultipartFile record, HttpServletRequest request) {
		
		CoverSong coverSong = coverSongService.insertCoverSong(userId, songId, record);
		if(coverSong == null) {
			request.setAttribute("errMsg", "insert error");
			return "error";
		}else {
			return "redirect:index";
		}
	}
}
