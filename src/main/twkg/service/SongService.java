package twkg.service;

import java.util.List;

import twkg.entity.CoverSong;
import twkg.entity.Song;

public interface SongService {
	List<Song> getHotestSong(int n);
	String addSongToPlayList(String currentSong, List<Song> songList);
	Song getSongById(String songId);
	int getSearchCount(String searchContent);
	List<Song> searchSong(String searchContent,int currentPage,int rowEveryPage);
	List<Song> getSongsByCoverSongs(List<CoverSong> coverSongs);
	String deleteSong(String deleteSongId);
	boolean insertSong(Song song);
	boolean updateSong(Song song);
	Song getLastSongBySongName(String songName);
}
