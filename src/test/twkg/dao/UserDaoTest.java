package twkg.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import twkg.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public class UserDaoTest {

	@Autowired
	private UserDao userDao;
	
	@Test
	public void testInsert() {
		User user = new User();
		user.setAdmin(false);
		user.setUserName("qqq");
		user.setUserKey("11");
		user.setRegisterTime(new Timestamp(new Date().getTime()));
		user.setUserBio("qw");
		user.setUserBirth(new java.sql.Date(new Date().getTime()));
		user.setUserEmail("123");
		user.setUserGender("男");
		user.setUserRealName("qq");
		user.setUserPopularity(1);
		int insert = userDao.insert(user);
		System.out.println(insert);
	}

	@Test
	public void testDelete() {
		int delete = userDao.delete(8);
		System.out.println(delete);
	}

	@Test
	public void testUpdate() {
		User user = new User();
		user.setAdmin(false);
		user.setUserName("qqq");
		user.setUserKey("11");
		user.setRegisterTime(new Timestamp(new Date().getTime()));
		user.setUserBio("qw");
		user.setUserBirth(new java.sql.Date(new Date().getTime()));
		user.setUserEmail("123");
		user.setUserGender("男");
		user.setUserRealName("qq");
		user.setUserPopularity(1);
		user.setUserId(6);
		int update = userDao.update(user);
		System.out.println(update);
	}

	@Test
	public void testFindUserByUserId() {
		User user = userDao.findUserByUserId(1);
		System.out.println(user.getUserName());
	}

	@Test
	public void testFindMostPopularUser() {
		List<User> users = userDao.findMostPopularUser(3);
		System.out.println(users.get(0).getUserName()+" "+users.get(1).getUserName()+" "+users.get(2).getUserName()+" ");
	}

	@Test
	public void testFindUserByUserNameAndUserRealName() {
		List<User> users = userDao.findUserByUserNameAndUserRealName("u", 0, 20);
		System.out.println(users.size());
	}

	@Test
	public void testFindUserCountByUserNameAndUserRealName() {
		int count = userDao.findUserCountByUserNameAndUserRealName("u");
		System.out.println(count);
	}

	@Test
	public void testFindUserByUserName() {
		User user = userDao.findUserByUserName("usera");
		System.out.println(user.getUserEmail());
	}

	@Test
	public void testFindUserByUserEmail() {
		User user = userDao.findUserByUserEmail("123@qq.qq");
		System.out.println(user.getUserName());
	}

}
