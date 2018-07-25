package twkg.dao;

import org.apache.ibatis.annotations.Param;

import twkg.entity.Cookie;

public interface CookieDao {
	public int insert(@Param("cookie") Cookie cookie);
	public int delete(@Param("cookieId") int cookieId);
	public int update(@Param("cookie") Cookie cookie);
	public Cookie findCookieByCookieValue(@Param("cookieValue") String cookieValue);
}
