package twkg.service.impl;

import java.sql.Date;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twkg.dao.CookieDao;
import twkg.entity.User;
import twkg.service.CookieService;
import twkg.util.ConfigUtil;
import twkg.util.EncryptUtil;

@Service
public class CookieServiceImpl implements CookieService{

	@Autowired
	private CookieDao cookieDao;
	
	@Override
	public Cookie generateAndInsertCookie(User user, String autologin) {
		Cookie cookie = new Cookie(ConfigUtil.COOKIE_NAME,null);
		cookie.setValue(EncryptUtil.encryptCookie(user.getUserId()));
		cookie.setMaxAge(ConfigUtil.COOKIE_EXPIRED*24*60*60);
		cookie.setPath("/twkgWithSSM");
		
		twkg.entity.Cookie twkgCookie = new twkg.entity.Cookie();
		twkgCookie.setUserId(user.getUserId());
		twkgCookie.setCookieValue(cookie.getValue());
		twkgCookie.setCookieTime(new Date(new java.util.Date().getTime()));
		if(autologin==null)
			twkgCookie.setAutoLogin(false);
		else
			twkgCookie.setAutoLogin(true);
		cookieDao.insert(twkgCookie);
		return cookie;
	}

}
