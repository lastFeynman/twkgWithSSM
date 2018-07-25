package twkg.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import twkg.dao.CoverSongDao;
import twkg.dao.SongDao;
import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.service.CoverSongService;
import twkg.util.ConfigUtil;

@Service
public class CoverSongServiceImpl implements CoverSongService{
	@Autowired
	private CoverSongDao coverSongDao;
	
	@Autowired
	private SongDao songDao;

	@Override
	public String addCoverSongToPlayList(String currentSong, List<CoverSong> coverSongList) {
		int id = 0;
		try {
			id = Integer.parseInt(currentSong);
		}catch (Exception e) {
			System.err.println("CurrentSongId Error");
		}
		CoverSong coverSong = coverSongDao.findCoverSongByCoverSongId(id);
		for(int i=0;i<=coverSongList.size();i++) {
			if(i == coverSongList.size()) {
				coverSongList.add(coverSong);
				break;
			}
			if(coverSongList.get(i).getCoverSongId() == id)
				break;
		}
		StringBuffer playListStr = new StringBuffer("[");
		for(int i=0;coverSongList!=null&&i<coverSongList.size();i++) {
			if(i!=0) {
				playListStr.append(',');
			}
			Song coverSongInfo = songDao.findSongBySongId(coverSongList.get(i).getSongId());
			playListStr.append("{title:'"+coverSongInfo.getSongName()+"',");
			playListStr.append("singer:'"+coverSongInfo.getSingerName()+"',");
			playListStr.append("audio:'"+ConfigUtil.COVER_SONG_PATH+coverSongList.get(i).getCoverSongId()+".mp3',");
			playListStr.append("thumbnail:'"+ConfigUtil.THUMBNAIL_PATH+coverSongInfo.getSongId()+".jpg',");
			playListStr.append("lyric:'"+ConfigUtil.LYRIC_PATH+coverSongInfo.getSongId()+".lrc'}");
		}
		playListStr.append("]");
		return null;
	}

	@Override
	public CoverSong insertCoverSong(String userIdStr, String songIdStr, CommonsMultipartFile audio) {
		int userId,songId;
		try {
			userId = Integer.parseInt(userIdStr);
			songId = Integer.parseInt(songIdStr);
		} catch (Exception e) {
			return null;
		}
		CoverSong coverSong = new CoverSong();
		coverSong.setUserId(userId);
		coverSong.setSongId(songId);
		coverSong.setSingTime(new Timestamp(System.currentTimeMillis()));
		if(coverSongDao.insert(coverSong) == 0)
			return null;
		
		coverSong = coverSongDao.findLastCoverSong(userId, songId);
		
		InputStream is = null;
		FileOutputStream fos = null;
		
		String servPath = CoverSongService.class.getResource("/../..").getPath();
		File recordFile = new File(servPath+ConfigUtil.COVER_SONG_PATH.substring(13)+coverSong.getSongId()+".mp3");
		try {
			if(!recordFile.exists())
				recordFile.createNewFile();
			is = audio.getInputStream();
			fos = new FileOutputStream(recordFile);
			
			byte[] buffer = new byte[1024];
			int len;
			while((len = is.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return coverSong;
	}

	@Override
	public int getSearchCount(String searchContent) {
		return coverSongDao.findCoverSongCountBySongNameAndSingerName(searchContent);
	}

	@Override
	public List<CoverSong> searchCoverSong(String searchContent, int currentPage, int rowEveryPage) {
		return coverSongDao.findCoverSongBySongNameAndSingerName(searchContent, rowEveryPage*(currentPage-1), rowEveryPage);
	}

	@Override
	public String deleteCoverSong(String deleteCoverSongId) {
		int id;
		try {
			id = Integer.parseInt(deleteCoverSongId);
		} catch (Exception e) {
			return "param error";
		}
		
		if (coverSongDao.delete(id) > 0)
			return "succeed";
		else
			return "db error";
	}
	
}
