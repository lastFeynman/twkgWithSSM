package twkg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import twkg.entity.CoverSong;

public interface CoverSongDao {
	public int insert(@Param("coverSong") CoverSong coverSong);
	public int delete(@Param("coverSongId") int coverSongId);
	public int update(@Param("coverSong") CoverSong coverSong);
	public List<CoverSong> findCoverSongByUserId(@Param("userId") int userId);
	public List<CoverSong> findCoverSongBySongNameAndSingerName(@Param("searchContent") String searchContent,@Param("startIndex") int startIndex,@Param("n") int n);
	public int findCoverSongCountBySongNameAndSingerName(@Param("searchContent") String searchContent);
	public CoverSong findCoverSongByCoverSongId(@Param("coverSongId") int coverSongId);
	public CoverSong findLastCoverSong(@Param("userId") int userId,@Param("songId") int songId);
}
