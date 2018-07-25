package twkg.dao;

import java.sql.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import twkg.entity.Cookie;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class CookieDaoTest {
	
	@Autowired
	private CookieDao cookieDao;
	
	@Test
	public void testInsert() {
		Cookie cookie = new Cookie();
		cookie.setAutoLogin(true);
		cookie.setUserId(1);
		cookie.setCookieValue("123");
		cookie.setCookieTime(new Date(new java.util.Date().getTime()));
		int insert = cookieDao.insert(cookie);
		System.out.println(insert);
	}

	@Test
	public void testDelete() {
		int delete = cookieDao.delete(4);
		System.out.println(delete);
	}

	@Test
	public void testUpdate() {
		Cookie cookie = new Cookie();
		cookie.setCookieId(1);
		cookie.setAutoLogin(true);
		cookie.setUserId(1);
		cookie.setCookieValue("123");
		cookie.setCookieTime(new Date(new java.util.Date().getTime()));
		int update = cookieDao.update(cookie);
		System.out.println(update);
	}

	@Test
	public void testFindCookieByCookieValue() {
		Cookie cookie = cookieDao.findCookieByCookieValue("123");
		System.out.println(cookie.getCookieId());
	}

}
