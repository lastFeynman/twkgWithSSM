package twkg.service;

import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import twkg.entity.CoverSong;

public interface CoverSongService {
	String addCoverSongToPlayList(String currentSong, List<CoverSong> coverSongList);
	CoverSong insertCoverSong(String userIdStr, String songIdStr, CommonsMultipartFile audio);
	int getSearchCount(String searchContent);
	List<CoverSong> searchCoverSong(String searchContent, int currentPage, int rowEveryPage);
	String deleteCoverSong(String deleteCoverSongId);
}
