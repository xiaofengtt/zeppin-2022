<%@ page language="java"  pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld"%>
<%
response.setHeader("expires", "0");
%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>欢迎光临华南师范大学现代远程教育平台</title>
		</title>
		</title>
		<link href="/entity/manager/css/admincss.css" rel="stylesheet"
			type="text/css">
		<link href="/entity/teacher/images/layout.css" rel="stylesheet"
			type="text/css" />
	</head>
	<body>
		<div id="main_content">
			<div class="content_title">
				教师课程列表
			</div>
			<div class="cntent_k">
				<div class="k_cc">
					<table border="0" cellpadding="0" cellspacing="0" width="100%">
						
						<tr class='table_bg1'>

					<td align="center">课程名</td>
					<td align="left">开始授课</td>
                    <td align="left" >实时答疑室</td>
                    <td align="left" >学科论坛</td>

						</tr>
<s:if test="teachingCourses.size()>0">
						<s:iterator value="teachingCourses" status="st" >

						<tr <s:if test="#stuts.odd == false"> class="table_bg2" </s:if> >
							<td align="center">
								<s:property value="peTchCourse.name"/>
							</td>
							<td>
								<a href='about:blank' target='_blank'><div class="study_bt">
<a href='/sso/interaction_InteractioManage.action?course_id=<s:property value="getPeTchCourse().getId()"/>' target='_blank'><div class="study_bt">
										开始授课
									</div>
								</a>
							</td>
							<script>
			var tmp_url_<s:property value="getId()"/> = "http://218.19.140.213/vlog.asp?roomid=<s:property value="getPrTchOpencourse(peTchCourse.id)"/>&op=1&user=" + escape("<s:property value="getPeTeacher().getTrueName()"/>") + "&nick=" + escape("<s:property value="getPeTeacher().getTrueName()"/>") + "&roomname=" + escape("<s:property value="getPeTchCourse().getName()"/>") + "&roomgroup=" + escape("实时多媒体答疑教室");
		</script>

			         <td>
				          <a href='#' onclick='window.open(tmp_url_<s:property value="getId()"/>);'><div class="study_bt">实时答疑室</div></a>
				      </td>
				      
		<td><div class="study_bt" onmouseover="this.className='study_bt_M'" onmouseout="this.className='study_bt'"><a href='/entity/workspaceTeacher/teacher_toCourseforum.action?opencourseid=<s:property value="getPrTchOpencourse(peTchCourse.id)"/>' target="_blank"'>学科论坛</a></div></td>
                      
						</tr>
						
						</s:iterator>
</s:if>
					</table>

				</div>
			</div>
		</div>
		<div class="clear"></div>
	</body>
</html>
