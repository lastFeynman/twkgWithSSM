package twkg.dao;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import twkg.entity.Song;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class SongDaoTest {

	@Autowired
	private SongDao songDao;
	
	@Test
	public void testInsert() {
		Song song = new Song();
		song.setSongName("1");
		song.setSingerName("1");
		song.setCreateTime(new Date(new java.util.Date().getTime()));
		song.setPlayCount(1);
		song.setCoverCount(1);
		int insert = songDao.insert(song);
		System.out.println(insert);
	}

	@Test
	public void testDelete() {
		int delete = songDao.delete(11);
		System.out.println(delete);
	}

	@Test
	public void testUpdate() {
		Song song = new Song();
		song.setSongId(1);
		song.setSongName("1");
		song.setSingerName("1");
		song.setCreateTime(new Date(new java.util.Date().getTime()));
		song.setPlayCount(1);
		song.setCoverCount(1);
		int update = songDao.update(song);
		System.out.println(update);
	}

	@Test
	public void testFindSongBySongNameAndSingerName() {
		List<Song> songs = songDao.findSongBySongNameAndSingerName("a", 0, 20);
		System.out.println(songs.size());
	}

	@Test
	public void testFindSongCountBySongNameAndSingerName() {
		int count = songDao.findSongCountBySongNameAndSingerName("a");
		System.out.println(count);
	}

	@Test
	public void testFindSongBySongId() {
		Song song = songDao.findSongBySongId(1);
		System.out.println(song.getSongName());
	}

	@Test
	public void testFindHotestSongs() {
		List<Song> songs = songDao.findHotestSongs(3);
		System.out.println(songs.get(0).getSongId()+" "+songs.get(1).getSongId()+" "+songs.get(2).getSongId());
	}

	@Test
	public void testFindLastSongBySongName() {
		Song song = songDao.findLastSongBySongName("aaa");
		System.out.println(song.getSongId());
	}

}
