package twkg.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.service.CoverSongService;
import twkg.service.SongService;

@Controller
public class PlayerController {
	@Autowired
	private SongService songService;
	
	@Autowired
	private CoverSongService coverSongService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/player",method = RequestMethod.GET)
	private String play(@RequestParam(value = "playerType") String playerType, @RequestParam("currentSong") String currentSong, HttpServletRequest request) {
		if(playerType != null && !playerType.equals("song") && !playerType.equals("cover")) {
			request.setAttribute("errMsg", "PlayerType Error");
			return "error";
		}
		HttpSession session = request.getSession();
		String playList;
		int currentIndex = 0;
		if(playerType == null || playerType.equals("song")) {
			List<Song> songList = (List<Song>)session.getAttribute("songList");
			if(songList == null)
				songList = new ArrayList<>();
			playList = songService.addSongToPlayList(currentSong, songList);
			for(int i=0;i<songList.size();i++) {
				if((songList.get(i).getSongId()+"").equals(currentSong))
					currentIndex = i;
			}
			session.setAttribute("songList", songList);
		}else {
			List<CoverSong> coverSongList = (List<CoverSong>)session.getAttribute("coverSongList");
			if(coverSongList == null)
				coverSongList = new ArrayList<>();
			playList = coverSongService.addCoverSongToPlayList(currentSong, coverSongList);
			for(int i=0;i<coverSongList.size();i++) {
				if((coverSongList.get(i).getCoverSongId()+"").equals(currentSong))
					currentIndex = i;
			}
			session.setAttribute("coverSongList", coverSongList);
		}
		request.setAttribute("playList", playList);
		request.setAttribute("currentIndex", currentIndex);
		return "player";
	}
}
