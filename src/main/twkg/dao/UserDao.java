package twkg.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import twkg.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-dao.xml"})
public interface UserDao {
	public int insert(@Param("user") User user);
	public int delete(@Param("userId") int userId);
	public int update(@Param("user") User user);
	public User findUserByUserId(@Param("userId") int userId);
	public List<User> findMostPopularUser(@Param("n") int n);
	public List<User> findUserByUserNameAndUserRealName(@Param("searchContent") String searchContent,@Param("startIndex") int startIndex,@Param("n") int n);
	public int findUserCountByUserNameAndUserRealName(@Param("searchContent") String searchContent);
	public User findUserByUserName(@Param("userName") String userName);
	public User findUserByUserEmail(@Param("userEmail") String userEmail);
}
