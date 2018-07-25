package twkg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import twkg.entity.Song;

public interface SongDao {
	public int insert(@Param("song") Song song);
	public int delete(@Param("songId") int songId);
	public int update(@Param("song") Song song);
	public List<Song> findSongBySongNameAndSingerName(@Param("searchContent") String searchContent,@Param("startIndex") int startIndex,@Param("n") int n);
	public int findSongCountBySongNameAndSingerName(@Param("searchContent") String searchContent);
	public Song findSongBySongId(@Param("songId") int songId);
	public List<Song> findHotestSongs(@Param("n") int n);
	public Song findLastSongBySongName(@Param("songName") String songName);
}
