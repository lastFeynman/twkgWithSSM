<%@page import="twkg.util.ConfigUtil"%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="twkg.dao.SongDao"%>
<%@page import="twkg.dao.UserDao"%>
<%@page import="twkg.entity.User"%>
<%@page import="twkg.entity.Song"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/twkgWithSSM/css/index.css" type="text/css" rel="stylesheet" />
<title>贪玩K歌</title>
</head>
<%
	User currentUser = (User) session.getAttribute("currentUser");
%>
<script type="text/javascript">
	function playSong(songId){
		songPlayer = window.open("/twkgWithSSM/player?playerType=song&currentSong="+songId,"songPlayer");
		songPlayer.focus;
	}
</script>
<body>
	<div class="container" id="container">
		<!-- 头部 -->
		<div class="header">
		
			<a href="index"><img id="logo" style="float: left;" /></a>
			<div id="siteName">贪玩K歌</div>
			<ul id="naviMenu">
				<a href="index"><li>首页</li></a>
				<a href="personal"><li>个人中心</li></a>
			</ul>
			<%
				if (currentUser != null) {
			%>
			<a id="login" style="position: relative; float: left;">欢迎您，</a> <a
				href="personal" id="register"
				style="position: relative; float: left;"><%=currentUser.getUserName()%></a>
				<a href="/twkgWithSSM/login" style="position:relative;float:left;" id="changeUser">切换用户</a>
			<%
				} else {
			%>
			<a href="login" id="login"
				style="position: relative; float: left;">登录</a> <a
				href="register" id="register"
				style="position: relative; float: left;">注册</a>
			<%
				}
			%>
			<!-- 搜索框 -->
			<form action="search" method="post" id="searchForm">
				<input type="hidden" name="currentPage" value="1">
				<input type="hidden" name="searchType" value="song">
				<input type="text" id="searchContent" name="searchContent" /> <img
					src="images/searchIcon.png" id="searchIcon" /> <img
					src="images/searchIconP.png" id="searchIconP" /> <img
					src="images/searchIconM.png" id="searchIconM" />
			</form>
			<%if(currentUser!=null && currentUser.isAdmin()){ %>
		<a href="/twkgWithSSM/admin/index">进入管理员主页</a>
		<%} %>
		</div>

		<!-- 滚动图片 -->
		<div class="scrollPic" id="scrollPic">
			<!-- 前一张 -->
			<div class="lastPic" id="lastPic">
				<div class="cursorIcon">
					<img src="images/leftCursor.png" id="leftCursor" />
				</div>
			</div>
			<!-- 底部导航点 -->
			<div class="naviPoint"
				style="top: 300px; left: 420px; background-color: white;"></div>
			<div class="naviPoint" style="top: 300px; left: 430px;"></div>
			<div class="naviPoint" style="top: 300px; left: 440px;"></div>
			<!--下一张  -->
			<div class="nextPic" id="nextPic">
				<div class="cursorIcon">
					<img src="images/rightCursor.png" id="rightCursor" />
				</div>
			</div>
			<img class="scrollImg" src="images/slideImg1.jpg"
				style="visibility: visible; top: -312px;" /> <img class="scrollImg"
				src="images/slideImg2.jpg" style="visibility: hidden; top: -624px;" />
			<img class="scrollImg" src="images/slideImg3.jpg"
				style="visibility: hidden; top: -936px;" />
		</div>

		<!-- 热门歌曲 -->
		<div class="hotSongContainer" id="hotSongContainer">
			<div
				style="background-color: rgba(54, 169, 206, 1); width: 18px; height: 37px; position: relative; float: left;"></div>
			<div style="font-size: 32px; font-weight: 700;">&emsp;热门歌曲</div>

			<div class="hotSong">
				<a onclick="playSong('${songs.get(0).songId}')">
					<img
					src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(0).songId}.jpg" />
					<div class="hotSongInfo">
						歌曲：${songs.get(0).songName}<br> 歌手：${songs.get(0).singerName}
					</div>
				</a>
			</div>
			<div class="hotSong">
				<a onclick="playSong('${songs.get(1).songId}')">
					<img
					src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(1).songId}.jpg" />
					<div class="hotSongInfo">
						歌曲：${songs.get(1).songName}<br> 歌手：${songs.get(1).singerName}
					</div>
				</a>
			</div>
			<div class="hotSong">
				<a onclick="playSong('${songs.get(2).songId}')">
					<img
					src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(2).songId}.jpg" />
					<div class="hotSongInfo">
						歌曲：${songs.get(2).songName}<br> 歌手：${songs.get(2).singerName}
					</div>
				</a>
			</div>
			<a onclick="playSong('${songs.get(3).songId}')"> <img
				src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(3).songId}.jpg"
				style="width: 259px; height: 259px; position: relative; top: 31px; left: 1px;" />
				<div class="hotSongInfo">
					歌曲：${songs.get(3).songName}<br> 歌手：${songs.get(3).singerName}
				</div>
			</a>

			<div class="hotSong">
				<a onclick="playSong('${songs.get(4).songId}')">
					<img
					src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(4).songId}.jpg" />
					<div class="hotSongInfo">
						歌曲：${songs.get(4).songName}<br> 歌手：${songs.get(4).singerName}
					</div>
				</a>
			</div>
			<div class="hotSong">
				<a onclick="playSong('${songs.get(5).songId}')">
					<img
					src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(5).songId}.jpg" />
					<div class="hotSongInfo">
						歌曲：${songs.get(5).songName}<br> 歌手：${songs.get(5).singerName}
					</div>
				</a>
			</div>
			<div class="hotSong">
				<a onclick="playSong('${songs.get(6).songId}')">
					<img
					src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(6).songId}.jpg" />
					<div class="hotSongInfo">
						歌曲：${songs.get(6).songName}<br> 歌手：${songs.get(6).singerName}
					</div>
				</a>
			</div>

			<a onclick="playSong('${songs.get(7).songId}')"> <img
				src="<%=ConfigUtil.THUMBNAIL_PATH%>${songs.get(7).songId}.jpg"
				style="width: 259px; height: 259px; position: relative; top: 31px; left: 1px;" />
				<div class="hotSongInfo">
					歌曲：${songs.get(7).songName}<br> 歌手：${songs.get(7).singerName}
				</div>
			</a>
		</div>

		<!-- 人气用户-->
		<div class="hotUserContainer" id="hotUserContainer">
			<div
				style="background-color: rgba(54, 169, 206, 1); width: 18px; height: 37px; position: relative; float: left;"></div>
			<div style="font-size: 32px; font-weight: 700;">&emsp;人气用户</div>

			<div
				style="position: relative; width: 549px; height: 579px; float: left;">
				<a href="user?userId=${users.get(0).userId}"> <img
					src="avatars/${users.get(0).userId}.jpg"
					style="position: relative; top: 30px; width: 549px; height: 549px;" />
				</a>
			</div>
			
			<div class="hotUser">
				<a href="user?userId=${users.get(1).userId}"> <img
					src="avatars/${users.get(1).userId}.jpg" />
				</a>
			</div>
			
			<div class="hotUser">
				<a href="user?userId=${users.get(2).userId}"> <img
					src="avatars/${users.get(2).userId}.jpg" />
				</a>
			</div>
			<div class="hotUser">
				<a href="user?userId=${users.get(3).userId}"> <img
					src="avatars/${users.get(3).userId}.jpg" />
				</a>
			</div>
			<div class="hotUser">
				<a href="user?userId=${users.get(4).userId}"> <img
					src="avatars/${users.get(4).userId}.jpg" />
				</a>
			</div>
		
		</div>
		<!-- footer -->
		<div class="footer">
			<div
				style="position: relative; top: 50px; width: 100%; text-align: center;">
				<a href="index">贪玩K歌</a>&emsp;|&emsp;<a href="tos">服务条款</a>&emsp;|&emsp;<a
					href="aboutus">关于我们</a><br> Copyright (C) 2018-9999 All
				rights reserved.
			</div>
		</div>
	</div>
</body>
<script src="/twkgWithSSM/js/index.js"></script>
</html>