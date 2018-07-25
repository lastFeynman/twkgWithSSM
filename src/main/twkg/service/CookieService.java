package twkg.service;

import javax.servlet.http.Cookie;

import twkg.entity.User;

public interface CookieService {
	Cookie generateAndInsertCookie(User user,String autologin);
}
