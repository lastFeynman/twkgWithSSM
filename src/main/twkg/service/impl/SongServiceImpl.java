package twkg.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twkg.dao.SongDao;
import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.service.SongService;
import twkg.util.ConfigUtil;

@Service
public class SongServiceImpl implements SongService{
	@Autowired
	private SongDao songDao;

	@Override
	public List<Song> getHotestSong(int n) {
		return songDao.findHotestSongs(n);
	}

	@Override
	public String addSongToPlayList(String currentSong, List<Song> songList) {
		int id = 0;
		try {
			id = Integer.parseInt(currentSong);
		}catch (Exception e) {
			System.err.println("currentSongId Error");
		}
		
		Song song = songDao.findSongBySongId(id);
		for(int i=0;i<=songList.size();i++) {
			if(i==songList.size()) {
				songList.add(song);
				break;
			}
			if(songList.get(i).getSongId() == id)
				break;
		}
		StringBuffer playListStr = new StringBuffer("[");
		for(int i=0;i<songList.size();i++) {
			if(i!=0) {
				playListStr.append(',');
			}
			playListStr.append("{title:'"+songList.get(i).getSongName()+"',");
			playListStr.append("singer:'"+songList.get(i).getSingerName()+"',");
			playListStr.append("audio:'"+ConfigUtil.SONG_PATH+songList.get(i).getSongId()+".mp3',");
			playListStr.append("thumbnail:'"+ConfigUtil.THUMBNAIL_PATH+songList.get(i).getSongId()+".jpg',");
			playListStr.append("lyric:'"+ConfigUtil.LYRIC_PATH+songList.get(i).getSongId()+".lrc'}");
		}
		playListStr.append("]");
		return new String(playListStr);
	}

	@Override
	public Song getSongById(String songId) {
		int id;
		try {
			id = Integer.parseInt(songId);
		} catch (Exception e) {
			return null;
		}
		Song song = songDao.findSongBySongId(id);
		return song;
	}

	@Override
	public int getSearchCount(String searchContent) {
		return songDao.findSongCountBySongNameAndSingerName(searchContent);
	}

	@Override
	public List<Song> searchSong(String searchContent, int currentPage, int rowEveryPage) {
		return songDao.findSongBySongNameAndSingerName(searchContent, rowEveryPage*(currentPage-1), rowEveryPage);
	}

	@Override
	public List<Song> getSongsByCoverSongs(List<CoverSong> coverSongs) {
		List<Song> songs = new ArrayList<>();
		for(int i=0;i<coverSongs.size();i++) {
			songs.add(songDao.findSongBySongId(coverSongs.get(i).getSongId()));
		}
		return songs;
	}

	@Override
	public String deleteSong(String deleteSongId) {
		int id;
		try {
			id = Integer.parseInt(deleteSongId);
		} catch (Exception e) {
			return "param error";
		}
		
		if(songDao.delete(id) > 0)
			return "succeed";
		else 
			return "db error";
		
	}

	@Override
	public boolean insertSong(Song song) {
		if(songDao.insert(song) > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateSong(Song song) {
		if(songDao.update(song) > 0)
			return true;
		else
			return false;
	}

	@Override
	public Song getLastSongBySongName(String songName) {
		return songDao.findLastSongBySongName(songName);
	}
}
