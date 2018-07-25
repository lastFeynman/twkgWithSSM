package twkg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twkg.entity.CoverSong;
import twkg.entity.Song;
import twkg.entity.User;
import twkg.service.CoverSongService;
import twkg.service.SongService;
import twkg.service.UserService;
import twkg.util.ConfigUtil;
import twkg.util.EncryptUtil;

@Controller
public class AdminSearchController {
	private static final int MAX_ROW = 20;

	private static final long MAX_UPLOAD_SIZE = 50*1024*1024;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private CoverSongService coverSongService;

	@RequestMapping(value = "/admin/search", method = {RequestMethod.GET,RequestMethod.POST})
	private String adminSearch(@RequestParam("searchType") String searchType, @RequestParam("searchContent") String searchContent, 
			@RequestParam("currentPage") String currentPage, HttpServletRequest request) {
		if(!searchType.equals("user") && !searchType.equals("song") && !searchType.equals("cover")) {
			request.setAttribute("errMsg", "paramError");
			return "error";
		}
		request.setAttribute("searchType", searchType);
		int page;
		try {
			page = Integer.parseInt(currentPage);
		}catch (Exception e) {
			request.setAttribute("errMsg", "paramError");
			return "error";
		}
		request.setAttribute("currentPage", page);
		
		String maxPageStr = request.getParameter("maxPage");
		int maxPage = 0;
		try {
			maxPage = Integer.parseInt(maxPageStr);
		} catch (Exception e) {
			//do nothing
		}
		int resultCount = 0;
		if(maxPage==0) {
			if(searchType.equals("user")) {
				resultCount = userService.getSearchCount(searchContent);
			}else if(searchType.equals("song")) {
				resultCount = songService.getSearchCount(searchContent);
			}else {
				resultCount = coverSongService.getSearchCount(searchContent);
			}
		}
		if(resultCount%MAX_ROW == 0 && resultCount/MAX_ROW != 0)
			maxPage = resultCount/MAX_ROW;
		else
			maxPage = resultCount/MAX_ROW + 1;
		request.setAttribute("maxPage", maxPage);
		
		List<User> users = null;
		List<Song> songs = null;
		List<CoverSong> coverSongs = null;
		if(searchType.equals("user")) {
			users = userService.searchUser(searchContent, page, MAX_ROW);
			request.setAttribute("users", users);
		}else if(searchType.equals("song")) {
			songs = songService.searchSong(searchContent, page, MAX_ROW);
			request.setAttribute("songs", songs);
		}else {
			coverSongs = coverSongService.searchCoverSong(searchContent, page, MAX_ROW);
			request.setAttribute("coverSongs", coverSongs);
		}
		return "admin/adminSearch";
	}
	
	@RequestMapping(value = "/admin/delete", method = RequestMethod.GET)
	private String delete(HttpServletRequest request) {
		String deleteUserId = request.getParameter("deleteUserId");
		String deleteSongId = request.getParameter("deleteSongId");
		String deleteCoverSongId = request.getParameter("deleteCoverSongId");
		String status;
		if(deleteUserId != null) {
			status = userService.deleteUser(deleteUserId);
		}else if(deleteSongId != null) {
			status = songService.deleteSong(deleteSongId);
		}else {
			status = coverSongService.deleteCoverSong(deleteCoverSongId);
		}
		if(status.equals("succeed"))
			request.setAttribute("statusMsg", "delete succeed");
		else
			request.setAttribute("statusMsg", "delete error");
		return "/admin/status";
	}
	
	@RequestMapping(value = "/admin/editUser", method = RequestMethod.GET)
	private String editUser(HttpServletRequest request) {
		String editUserId = request.getParameter("editUserId");
		if(editUserId == null)
			return "admin/editUser";
		
		User user = userService.getUserById(editUserId);
		request.setAttribute("user", user);
		return "admin/editUser";
	}
	
	@RequestMapping(value = "/admin/editUser.do", method = RequestMethod.POST)
	private String editUserDo(HttpServletRequest request) {
		String editUserIdStr = request.getParameter("editUserId");
		int editUserId = 0;
		if(editUserIdStr!=null) {
			editUserId = Integer.parseInt(editUserIdStr);
		}
		String useru = null;
		String name = null;
		try {
			useru=new String(request.getParameter("usern").getBytes("ISO8859-1"),"UTF-8") ;
			name=new String(request.getParameter("name").getBytes("ISO8859-1"),"UTF-8") ;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String pas=request.getParameter("pas");
		String adm=request.getParameter("isAdmin");
		String sex=request.getParameter("gender");
		String mail=request.getParameter("mail");
		String gender=null;
		if(sex.equals("man"))
			gender="男";
		else if(sex.equals("woman"))
			gender="女";
		boolean isAdmin=false;
		if(adm.equals("is"))
			isAdmin=true;
		
		User us = null;
		if(editUserId == 0)
			us = new User();
		else
			us = userService.getUserById(editUserIdStr);
		
		us.setUserName(useru);
		if(!pas.equals("")&&editUserId==0)
			us.setUserKey("init");
		else if(!pas.equals("")) {
			us.setUserKey(EncryptUtil.encrypt(pas, editUserId));
		}
		
		if(!name.equals(""))
			us.setUserRealName(name);
		
		if(!mail.equals(""))
			us.setUserEmail(mail);
		
		us.setAdmin(isAdmin);
		us.setUserGender(gender);
		
		if(editUserId==0)
			us.setRegisterTime(new Timestamp(new java.util.Date().getTime()));
		
		User existUserName = userService.getUserByName(useru);
		User existUserEmail = userService.getUserbyEmail(mail);
		if((editUserId==0 && (existUserName!=null || existUserEmail!=null)) || 
				(editUserId!=0 && ((existUserName!=null && existUserName.getUserId()!=editUserId) || (existUserEmail!=null && existUserEmail.getUserId()!=editUserId)))) {
			request.setAttribute("statusMsg", "username or email has exist");
			return "admin/status";
		}
		if(editUserId==0) {
			if(userService.insertUser(us)) {
				us = userService.getUserByName(useru);
				us.setUserKey(EncryptUtil.encrypt(pas, us.getUserId()));
				userService.updateUser(us);
				us = userService.getUserByName(useru);
				String servPath = request.getServletContext().getRealPath("/");
				
				File avatarFile = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(13)+us.getUserId()+".jpg");
				File defaultAvatar = new File(servPath+ConfigUtil.USER_AVATAR_PATH.substring(13)+"0.jpg");
				FileInputStream fis = null;
				FileOutputStream fos = null;
				
				try {
					if(!avatarFile.exists()) {
						avatarFile.createNewFile();
					}
					fis = new FileInputStream(defaultAvatar);
					fos = new FileOutputStream(avatarFile);
					
					byte[] buffer = new byte[2048];
					int len;
					while((len=fis.read(buffer))>0) {
						fos.write(buffer, 0, len);
					}
					fos.flush();
				}catch (Exception e) {
				}finally {
					if(fis!=null)
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					if(fos!=null)
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
				}
				request.setAttribute("statusMsg", "add succeed");
			}else {
				request.setAttribute("statusMsg", "add error");
			}
		}else {
			if(userService.updateUser(us)) {
				request.setAttribute("statusMsg", "edit succeed");
			}else {
				request.setAttribute("statusMsg", "edit error");
			}
		}
		return "admin/status";
	}
	
	@RequestMapping(value = "/admin/editSong", method = RequestMethod.GET)
	private String editSong(HttpServletRequest request) {
		String editSongId = request.getParameter("editSongId");
		if(editSongId == null)
			return "admin/editSong";
		
		Song song = songService.getSongById(editSongId);
		request.setAttribute("song", song);
		return "admin/editSong";
	}
	
	@RequestMapping(value = "/admin/editSong.do", method = RequestMethod.POST)
	private String editSongDo(HttpServletRequest request) {
		String editSongIdStr = null;
		String Song = null;
		String Singer = null;
		String ct = null;
		Song so=new Song();
		//ת���������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_UPLOAD_SIZE);
		List<FileItem> fileItems = null;
		Iterator<FileItem> iterator = null;
		Map<String, String> params = new HashMap<>();
		
		try {
			fileItems = upload.parseRequest(request);
			iterator = fileItems.iterator();
			while(iterator.hasNext()) {
				FileItem fileItem = iterator.next();
				if(fileItem.isFormField()) {
					params.put(fileItem.getFieldName(), new String(fileItem.getString().getBytes("ISO8859-1"),"UTF-8"));
				}
			}
		} catch (Exception e) {
			request.setAttribute("statusMsg", "ת�������������");
		}
		
		editSongIdStr = params.get("editSongIdStr");
		Song = params.get("songName");
		Singer = params.get("Singer");
		ct = params.get("Createtime");
		
		int editSongId = 0;
		if(editSongIdStr!=null) {
			try {
				editSongId = Integer.parseInt(editSongIdStr);
			} catch (Exception e) {
				request.setAttribute("errMsg", "param error");
				return "error";
			}
		}
		
		if(editSongId!=0)
			so.setSongId(editSongId);
		so.setSongName(Song);
		so.setSingerName(Singer);		
		if(!ct.equals("请选择创作时间")) {
			SimpleDateFormat sd=new SimpleDateFormat("MM/dd/yyyy");
			Date date=null;
			try {
				date = new Date(sd.parse(ct).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			so.setCreateTime(date);
		}
		
		if(editSongId==0) {
			if(songService.insertSong(so)) {
				request.setAttribute("statusMsg", "add succeed");
			}else {
				request.setAttribute("statusMsg", "add error");
			}
		}else {
			if(songService.updateSong(so)) {
				request.setAttribute("statusMsg", "edit succeed");
			}else {
				request.setAttribute("statusMsg", "edit error");
			}
		}
		
		so = songService.getLastSongBySongName(Song);
		if(so!=null && (request.getAttribute("statusMsg").equals("add succeed") || request.getAttribute("statusMsg").equals("edit succeed"))) {
			File songFile = new File(request.getServletContext().getRealPath("/")+ConfigUtil.SONG_PATH.substring(6)+so.getSongId()+".mp3");
			File thumbnailFile = new File(request.getServletContext().getRealPath("/")+ConfigUtil.THUMBNAIL_PATH.substring(6)+so.getSongId()+".jpg");
			File lyricFile = new File(request.getServletContext().getRealPath("/")+ConfigUtil.LYRIC_PATH.substring(6)+so.getSongId()+".lrc");
			try {
				iterator = fileItems.iterator();
				while(iterator.hasNext()) {
					FileItem fileItem = iterator.next();
					if(!fileItem.isFormField() && fileItem.getSize()>0) {
						if(fileItem.getFieldName().equals("songFile")) {
							fileItem.write(songFile);
						}else if(fileItem.getFieldName().equals("thumbnailFile")) {
							fileItem.write(thumbnailFile);
						}else if(fileItem.getFieldName().equals("lyricFile")) {
							fileItem.write(lyricFile);
						}
					}else if(fileItem.isFormField()){
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("uploadMsg", "upload error");
			}
		}
		return "admin/status";
	}
}
