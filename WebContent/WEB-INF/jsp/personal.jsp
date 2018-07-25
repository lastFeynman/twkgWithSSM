<%@page import="twkg.entity.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人中心</title>
<link href="css/index.css" type="text/css" rel="stylesheet" />
</head>
	<body>
		<table border="0"width="100%" height="900px">
			<tr height="10%">
				<td colspan="3" style="background-color: lightgrey;">
					<h1>贪玩K歌&nbsp;&nbsp;&nbsp;<a href="index">首页</a></h1>
			</tr>
			
			
			<tr style="background-image: url(images/背景.png);" height="80%px">
				<td width="20%" ></td>
				
				
				
				<td width="60%"  id="information" style="background-color: #FFFFFF;" valign="top">
					
					<table width="100%"  height="100%"> 
						<tr height="50%" >
							<td width="40%" valign="top">
								<img width="150px"  height="150px" src="avatars/${currentUser.userId}.jpg" />
								
								<img width="50px"  height="50px" src="avatars/${currentUser.userId}.jpg">
								<form method="post" action="/twkgWithSSM/changeAvatar"
											enctype="multipart/form-data">
											选择一个文件：
											<input type="file" name="uploadImg"/> <br/><br/>
											<input type="submit" value="上传" />
								</form >
								
								
								
								
								
							
							</td>
							<td width="60%" valign="top">
								<table width="100%">
									<tr><td width="20%" bgcolor="grey" align="middle">序号</td><td>${currentUser.userId}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">用户名</td><td>${currentUser.userName}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">邮箱</td><td>${currentUser.userEmail}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">生日</td><td>${currentUser.userBirth}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">性别</td><td>${currentUser.userGender}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">真实姓名</td><td>${currentUser.userRealName}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">签名</td><td>${currentUser.userBio}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">热度</td><td>${currentUser.userPopularity}</td></tr>
									<tr><td width="20%" bgcolor="grey" align="middle">注册时间</td><td>${currentUser.registerTime}</td></tr>
									<tr><td><a href="change_personal_info">修改个人信息</a></td></tr>
								</table>
							</td>
						</tr>
						<tr>
							
						</tr>
					</table>
				</td>
				
				
				<td width="20%"></td>
			</tr>
			
			
			<tr>
				<td colspan="3" valign="top">
					<div class="footer">
						<div style="position: relative; top: 10px; width: 100%; text-align: center;">
							<a href="index">贪玩K歌</a>&emsp;|&emsp;<a href="tos">服务条款</a>
							<a href="aboutus">关于我们</a><br> 
							Copyright (C) 2018-9999 All rights reserved.					
						</div>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>