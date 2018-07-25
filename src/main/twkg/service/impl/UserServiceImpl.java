package twkg.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twkg.dao.UserDao;
import twkg.entity.CoverSong;
import twkg.entity.User;
import twkg.service.UserService;
import twkg.util.ConfigUtil;
import twkg.util.EncryptUtil;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getMostPopularUser(int n) {
		return userDao.findMostPopularUser(n);
	}

	@Override
	public User getUserById(String userId) {
		int id = 0;
		try {
			id = Integer.parseInt(userId);
		}catch (Exception e) {
			return null;
		}
		User user = userDao.findUserByUserId(id);
		return user;
	}

	@Override
	public User verifyUserInfo(String userName, String password) {
		User user = userDao.findUserByUserName(userName);
		if(user != null && EncryptUtil.verifyPassword(password, user.getUserId(), user.getUserKey()))
			return user;
		else
			return null;
	}

	@Override
	public String registerAndInsertUser(String userName, String password, String email) {
		User user = new User();
		user.setUserName(userName);
		user.setUserKey("initKey");
		user.setUserEmail(email);
		user.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		
		if(userDao.findUserByUserEmail(email) != null)
			return "emailerror";
		else if (userDao.findUserByUserName(userName) != null)
			return "nameerror";
		else if(userDao.insert(user) != 0){
			user = userDao.findUserByUserName(userName);
			user.setUserKey(EncryptUtil.encrypt(password, user.getUserId()));
			if(userDao.update(user) == 0)
				return "dberror";
			
			String servPath = UserService.class.getResource("/../..").getPath();
			File avatar = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(13)+user.getUserId()+".jpg");
			File defaultAvatar = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(13)+"0.jpg");
			FileInputStream fis = null;
			FileOutputStream fos = null;
			
			try {
				if(!avatar.exists())
					avatar.createNewFile();
				
				fis = new FileInputStream(defaultAvatar);
				fos = new FileOutputStream(avatar);
				
				byte[] buffer = new byte[1024];
				int len;
				while((len = fis.read(buffer)) > 0)
					fos.write(buffer, 0, len);
				fos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if(fis != null) {
					try {
						fis.close();
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
			
			return "succeed";
		}else 
			return "dberror";
	}

	@Override
	public String changeUserInfo(User currentUser, User tempUser) {
		User user = userDao.findUserByUserName(tempUser.getUserName());
		if(user != null && user.getUserId() != currentUser.getUserId())
			return "nameerror";
		user = userDao.findUserByUserEmail(tempUser.getUserEmail());
		if(user != null && user.getUserId() != currentUser.getUserId())
			return "emailerror";
		
		currentUser.setUserName(tempUser.getUserName());
		currentUser.setUserEmail(tempUser.getUserEmail());
		currentUser.setUserBirth(tempUser.getUserBirth());
		currentUser.setUserGender(tempUser.getUserGender());
		currentUser.setUserRealName(tempUser.getUserRealName());
		currentUser.setUserBio(tempUser.getUserBio());
		if(userDao.update(currentUser) == 0)
			return "dberror";
		return "secceed";
	}

	@Override
	public List<User> getUsersByCoverSongs(List<CoverSong> coverSongs) {
		List<User> users = new ArrayList<>();
		for(int i=0;i<coverSongs.size();i++) {
			users.add(userDao.findUserByUserId(coverSongs.get(i).getUserId()));
		}
		return users;
	}

	@Override
	public int getSearchCount(String searchContent) {
		return userDao.findUserCountByUserNameAndUserRealName(searchContent);
	}

	@Override
	public List<User> searchUser(String searchContent, int currentPage, int rowEveryPage) {
		return userDao.findUserByUserNameAndUserRealName(searchContent, rowEveryPage*(currentPage-1), rowEveryPage);
	}

	@Override
	public String deleteUser(String deleteUserId) {
		int id;
		try {
			id = Integer.parseInt(deleteUserId);
		} catch (Exception e) {
			return "param error";
		}
		
		if(userDao.delete(id) > 0)
			return "succeed";
		else
			return "db error";
	}

	@Override
	public User getUserByName(String userName) {
		return userDao.findUserByUserName(userName);
	}

	@Override
	public User getUserbyEmail(String email) {
		return userDao.findUserByUserEmail(email);
	}

	@Override
	public boolean insertUser(User user) {
		if(userDao.insert(user) > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean updateUser(User user) {
		if(userDao.update(user) > 0)
			return true;
		else
			return false;
	}

}
