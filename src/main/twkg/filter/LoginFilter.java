package twkg.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import twkg.dao.CookieDao;
import twkg.dao.UserDao;
import twkg.entity.User;
import twkg.util.ConfigUtil;

/**
 * LoginFilter
 */
public class LoginFilter implements Filter {

	private CookieDao cookieDao;
	
	private UserDao userDao;
	
	private FilterConfig config = null;
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		config = null;
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		String uri = req.getRequestURI();
		String contextPath = req.getContextPath();
		uri = uri.substring(contextPath.length()+1);
		//static resource
		if(uri.endsWith("js")||uri.endsWith("css")||uri.endsWith("png")||uri.endsWith("jpg")||uri.endsWith("mp3")||uri.endsWith("lrc")) {
			chain.doFilter(request, response);
			return;
		}
		//nologin
		String[] noLogin = config.getInitParameter("noLogin").split(";");
		boolean isNoLogin = false;
		for(int i=0;i<noLogin.length;i++) {
			if(noLogin[i].equals(uri)) {
				isNoLogin=true;
				break;
			}
		}
		if(!isNoLogin) {
			HttpSession session = req.getSession();
			if(session.getAttribute("currentUser") == null) {
				//get Cookie
				Cookie[] cookies = req.getCookies();
				int cookieIndex;
				for(cookieIndex=0;cookies!=null&&cookieIndex<cookies.length;cookieIndex++) {
					if(cookies[cookieIndex].getName().equals(ConfigUtil.COOKIE_NAME)) {
						break;
					}
				}
				if(cookies==null || cookieIndex == cookies.length) {
					req.getServletContext().getRequestDispatcher("/login").forward(req, res);
					return;
				}
				
				twkg.entity.Cookie cookie = null;
				
				if((cookie = cookieDao.findCookieByCookieValue(cookies[cookieIndex].getValue())) == null || !cookie.isAutoLogin()) {
					req.getServletContext().getRequestDispatcher("/login").forward(req, res);
					return;
				}
				
				User currentUser = null;
				if((currentUser = userDao.findUserByUserId(cookie.getUserId())) == null) {
					req.getServletContext().getRequestDispatcher("/login").forward(req, res);
					return;
				}
				session.setAttribute("currentUser", currentUser);
				if(uri.equals("login")) {
					req.getServletContext().getRequestDispatcher("/index").forward(req, res);
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
		ServletContext sc = config.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		cookieDao = (CookieDao)ctx.getBean("cookieDao");
		userDao = (UserDao)ctx.getBean("userDao");
	}

}
