package twkg.service;

import java.util.List;

import twkg.entity.CoverSong;
import twkg.entity.User;

public interface UserService {
	List<User> getMostPopularUser(int n);
	User getUserById(String userId);
	User verifyUserInfo(String userName, String password);
	String registerAndInsertUser(String userName,String password,String email);
	String changeUserInfo(User currentUser,User tempUser);
	List<User> getUsersByCoverSongs(List<CoverSong> coverSongs);
	int getSearchCount(String searchContent);
	List<User> searchUser(String searchContent, int currentPage, int rowEveryPage);
	String deleteUser(String deleteUserId);
	User getUserByName(String userName);
	User getUserbyEmail(String email);
	boolean insertUser(User user);
	boolean updateUser(User user);
}
