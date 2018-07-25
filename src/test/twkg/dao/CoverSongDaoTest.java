package twkg.dao;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import twkg.entity.CoverSong;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class CoverSongDaoTest {

	@Autowired
	private CoverSongDao coverSongDao;
	
	@Test
	public void testInsert() {
		CoverSong coverSong = new CoverSong();
		coverSong.setUserId(1);
		coverSong.setSongId(1);
		coverSong.setCoverPlayCount(0);
		coverSong.setSingTime(new Timestamp(new Date().getTime()));
		int insert = coverSongDao.insert(coverSong);
		System.out.println(insert);
	}

	@Test
	public void testDelete() {
		int delete = coverSongDao.delete(4);
		System.out.println(delete);
	}

	@Test
	public void testUpdate() {
		CoverSong coverSong = new CoverSong();
		coverSong.setCoverSongId(1);
		coverSong.setUserId(1);
		coverSong.setSongId(1);
		coverSong.setCoverPlayCount(0);
		coverSong.setSingTime(new Timestamp(new Date().getTime()));
		int update = coverSongDao.update(coverSong);
		System.out.println(update);
	}

	@Test
	public void testFindCoverSongByUserId() {
		List<CoverSong> songs = coverSongDao.findCoverSongByUserId(1);
		System.out.println(songs.size());
	}

	@Test
	public void testFindCoverSongBySongNameAndSingerName() {
		List<CoverSong> songs = coverSongDao.findCoverSongBySongNameAndSingerName("a", 0, 20);
		System.out.println(songs.size());
	}

	@Test
	public void testFindCoverSongCountBySongNameAndSingerName() {
		int count = coverSongDao.findCoverSongCountBySongNameAndSingerName("a");
		System.out.println(count);
	}

	@Test
	public void testFindCoverSongByCoverSongId() {
		CoverSong coverSong = coverSongDao.findCoverSongByCoverSongId(1);
		System.out.println(coverSong.getUserId());
	}

	@Test
	public void testFindLastCoverSong() {
		CoverSong coverSong = coverSongDao.findLastCoverSong(1, 1);
		System.out.println(coverSong.getCoverSongId());
	}

}
