package twkg.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import twkg.entity.User;
import twkg.service.UserService;
import twkg.util.ConfigUtil;

@Controller
public class PersonalController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/personal", method = RequestMethod.GET)
	private String personal() {
		return "personal";
	}
	
	@RequestMapping(value = "/changeAvatar", method = RequestMethod.POST)
	private String changeAvatar(@RequestParam("uploadImg") CommonsMultipartFile avatarIn, HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("currentUser");

		String servPath = PersonalController.class.getResource("/../..").getPath();
		File avatar = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(13)+user.getUserId()+".jpg");
		InputStream is = null;
		FileOutputStream fos = null;
		
		try {
			if(!avatar.exists())
				avatar.createNewFile();
			
			is = avatarIn.getInputStream();
			fos = new FileOutputStream(avatar);
			
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
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "personal";
	}
	
	@RequestMapping(value = "change_personal_info", method = RequestMethod.GET)
	private String changePersonalInfo() {
		return "change_personal_info";
	}
	
	@RequestMapping(value = "change_personal_info.do", method = RequestMethod.POST)
	private String changePersonInfoDo(@RequestParam("uname") String userName, @RequestParam("uemail") String userEmail, 
			@RequestParam("ubirth") String userBirth, @RequestParam("ugender") String userGender, 
			@RequestParam("urealname") String userRealName, @RequestParam("ubio") String userBio,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("currentUser");
		User tempUser = new User();
		if(!userName.equals(""))
			tempUser.setUserName(userName);
		if(!userEmail.equals(""))
			tempUser.setUserEmail(userEmail);
		if(!userBirth.equals(""))
			tempUser.setUserBirth(strToDate(userBirth));
		if(!userGender.equals(""))
			tempUser.setUserGender(userGender);
		if(!userRealName.equals(""))
			tempUser.setUserRealName(userRealName);
		if(!userBio.equals(""))
			tempUser.setUserBio(userBio);
		String status = userService.changeUserInfo(user, tempUser);
		if(status.equals("emailerror"))
			return "personal";
		else if(status.equals("nameerror"))
			return "personal";
		else if(status.equals("succeed"))
			return "personal";
		else 
			return "personal";
	}

	private Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	     ParsePosition pos = new ParsePosition(0);
	     Date strtodate = (Date) formatter.parse(strDate, pos);
	     return strtodate;
	}
}
