<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ page import="com.whaty.platform.interaction.forum.*" %>
<%@ include file="../pub/priv.jsp"%>
<%
	String courseId1 = openCourse.getCourse().getId();
	String forumId = request.getParameter("forumId");
	String keyId = "forum_" + courseId + "_" + forumId;
	
	InteractionFactory interactionFactory = InteractionFactory.getInstance();
	InteractionManage interactionManage = interactionFactory.creatInteractionManage(interactionUserPriv);
	ForumList forum = interactionManage.getForumList(forumId);
	
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	ResourceDir dir = resManage.getResourceDirByKeyId(keyId);
	
	if(dir.getName() == null) {			//如果本版块的精华区还没有建立
		if(userType.equalsIgnoreCase("teacher")) {	//如果用户是教师就自动创建该门课程的课程资源目录并赋予访问权限
			ResourceDir courseDir = resManage.getResourceDirByKeyId("forum_" + courseId);
			if(courseDir.getName() == null) {		//如果课程精华区目录还没有建立
				String dirName = openCourse.getCourse().getName();
				String parentDirId = resManage.getResourceDirByKeyId("resource_elite").getId();
				String note = "课程 " + openCourse.getCourse().getName() + "的精华区资源目录";
				String status = "1";
				String isInherit = "0";
				
				resManage.addResourceDir(dirName,parentDirId,note,status,isInherit,"forum_"+courseId);
				courseDir = resManage.getResourceDirByKeyId("forum_"+courseId);
				String dirId = courseDir.getId();
				List userList = new ArrayList();
				userList.add(user.getId());
				List dirList = new ArrayList();
				dirList.add(dirId);
				resManage.setResourceRight(userList, dirList);
			}
			
			String dirName = openCourse.getCourse().getName();
			String parentDirId = courseDir.getId();
			String note = "课程 " + openCourse.getCourse().getName() + "的版块 " + forum.getName() + " 精华区资源目录";
			String status = "1";
			String isInherit = "0";
			
			resManage.addResourceDir(dirName,parentDirId,note,status,isInherit,keyId);
			dir = resManage.getResourceDirByKeyId(keyId);
			String dirId = dir.getId();
			List userList = new ArrayList();
			userList.add(user.getId());
			List dirList = new ArrayList();
			dirList.add(dirId);
			resManage.setResourceRight(userList, dirList);
		} else {
%>
<SCRIPT>
	alert("对不起，本版块还没有建立精华区!");
	window.history.back();
</SCRIPT>
<%
			return;
		}
	}
	
	if(!resManage.hasRight(user.getId(), dir.getId())) {
%>
<SCRIPT>
	alert("对不起，您没有查看 " + <%= openCourse.getCourse().getName() %> + " 的课程精华区资源的权限!");
	window.history.back();
</SCRIPT>
<%
		return;
	} else {
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>生殖健康咨询师培训网</title>
		<link href="./css/css.css" rel="stylesheet" type="text/css">
	</head>
	<body leftmargin="0" topmargin="0">
		<table border="0" cellspacing="0" cellpadding="0" width="100%">			
			<tr>				
				<td valign="top">
					<br>
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td valign="top" height="480">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">

									<tr>
										<td background="images/kzmb_02.gif">
											<img src="images/ggzy_03.gif" width="135" height="29">
										</td>
									</tr>
									<tr>
										<td height="29" class="title" style="padding-left:45px">
											<img src="images/xb.gif">
											&nbsp;您当前的位置是：课程
											<%= openCourse.getCourse().getName() %>
											的课程讨论区精华资源
										</td>
									</tr>
									<tr>
										<td valign="top" style="padding-left:45px">
											<table width="90%" border="1" cellpadding="3" cellspacing="0" bordercolorlight="#01699A" bordercolordark="#FFFFFF">
												<tr>
													<td bgcolor="#E8F6FF" class="listname" style="padding-left:8px">
														 当前资源所属：课程精华区资源
													</td>
												</tr>
												<tr>
													<td>
														<table width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr>
																<td width="30%" valign="bottom">
																	<IFRAME name="dir_frame" src="dir_frame.jsp?forumId=<%=forumId %>" height="250" width="100%" frameborder="0"></IFRAME>
																</td>
																<td width="2%">
																	 &nbsp;
																</td>
																<td width="68%">
																	<IFRAME name="content_frame" src="about:blank" height="250" width="100%" frameborder="0"></IFRAME>
																</td>
															</tr>
														</table>
													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td align="center" valign="middle">
								<iframe marginheight="0" marginwidth="0" width="100%" height="50" name="bottom" frameborder="0" scrolling="no" src="./pub/bottom.jsp"></iframe>
							</td>
						</tr>
					</table>

				</td>
			</tr>
		</table>
	</body>
</html>
<%
	}
%>