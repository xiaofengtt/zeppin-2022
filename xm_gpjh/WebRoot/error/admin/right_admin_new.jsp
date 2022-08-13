<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*"%>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeManager" />
<//%@ page import="com.whaty.platform.entity.BasicRightManage"%> <//%@
page
import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*,com.whaty.platform.entity.user.*"
%> <//%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<html>
	<head>
		<link href="<%=request.getContextPath()%>/admin/css.css"
			rel="stylesheet" type="text/css">
		<title>权限组管理</title>
	</head>

	<body>
		<%!//判断字符串为空的话，赋值为""
	String fixnull(String str) {
		if (str == null || str.equals("null") || str.equals("null"))
			str = "";
		return str;
	}%>
		<%!//判断字符串为空的话，赋值为""
	String fixnull1(String str) {
		if (str == null || str.equals("null") || str.equals("null"))
			str = "";
		return str;
	}%>
		<script language=javascript>			
		function del(url)
		{
		if(confirm("你确实要删除吗?"))
		{
			window.location=url;
		}
		
		}
</script>

		<%
		List manager = (List) request.getAttribute("managerList");
		%>

		<table cellPadding=2 cellSpacing=1 border="0" bgcolor=#3F6C61
			align=center width="100%">


			<tr>
				<td colspan="5" align="left" bgcolor="#D4E4EB" class="zhengwen">
					<div align="left">
						<font color="red">总站管理员列表</font> &nbsp;&nbsp;
						<a href="/sso/admin/siteManagerOper_showManagerList.action">分站管理员列表</a>&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/admin/mobile_batch.jsp">批量导入移动电话号码</a>&nbsp;&nbsp;
						<a href="/sso/admin/managerOper_export.action" target="_blank">批量导出总站管理员</a>
						<a href="/sso/admin/teacherStudentManagerOper_showTeacherList.action">教师权限组管理</a>
						<a href="/sso/admin/teacherStudentManagerOper_showStudentList.action?type=stu">学生权限组管理</a>
					</div>
				</td>
				<td colspan="1" align="right" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center" bgcolor="#D4E4EB" class="zhengwen">
						<a href="managerOper_showManagerInfo.action">添加总站管理员</a>
						&nbsp;&nbsp;
						<!-- <a href="add_admin.jsp">添加分站管理员</a>-->
						&nbsp;&nbsp;
						<a href="<%=request.getContextPath()%>/admin/right_main.jsp">返回</a>
					</div>
				</td>
			</tr>

			<tr>
				<!-- td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">管理员ID</div></td-->
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						登录ID
					</div>
				</td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					管理员姓名
				</td>
				     <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">管理员类型</div></td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						权限组
					</div>
				</td>
				<!-- 
    <td  align="left"    width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">上次登陆时间</div></td>
    <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">上次登陆IP</div></td>
    <td  align="left"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">登陆次数</div></td>
    -->
				    <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">状态</div></td>
				<td align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						操作
					</div>
				</td>

			</tr>

			<%
					if (manager != null) {
					for (int i = 0; i < manager.size(); i++) {

						PeManager mana = (PeManager) manager.get(i);

						/*   
						 String status=fixnull1(mana.getStatus());
						 if(status.equals("1")){
						 status="有效";
						 }else{
						 status="无效";
						 }
						 
						 String type=fixnull1(mana.getType());
						 
						 if(type.equals("SUPERADMIN")){
						 type="总站管理员";
						 }else{
						 type="分站管理员";
						 }
						 */

						String longId = mana.getSsoUser().getLoginId();
						String maname = mana.getName();
						//  String mastatus=java.net.URLEncoder.encode(fixnull(mana.getStatus()),"UTF-8");
						//  String mobile=java.net.URLEncoder.encode(fixnull(mana.getMobilePhone()),"UTF-8");
			%>
			<tr>
				<!-- td align="center"  width="5%"  height="21" bgcolor="#D4E4EB" class="zhengwen"><%=mana.getId()%></td-->
				<td align="center" width="10%" height="21" bgcolor="#D4E4EB"
					class="zhengwen">
					<%=longId%>
				</td>
				<td align="center" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<%=maname%>
				</td>
				<td align="center" width="10%" " bgcolor="#D4E4EB" class="zhengwen">
					<%=mana.getSsoUser()
											.getEnumConstByUserType() != null ? mana
									.getSsoUser().getEnumConstByUserType()
									.getName()
									: ""%>
				</td>
				<td align="center" width="10%" bgcolor="#D4E4EB" class="zhengwen">
					<%
							if (mana.getSsoUser().getPePriRole() == null) {
							out.println("<font color='red'>未指定权限组</font>");
								} else {
							out.println(fixnull(mana.getSsoUser().getPePriRole()
									.getName()));
								}
								;
					%>
				</td>
				<td align="left" width="5%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						<%=mana.getEnumConstByFlagIsvalid() != null ? mana
									.getEnumConstByFlagIsvalid().getName()
									: ""%>
					</div>
				</td>
				<td align="left" width="20%" bgcolor="#D4E4EB" class="zhengwen">
					<div align="center">
						<a
							href="/sso/admin/managerOper_changeAdminGroup.action?managerId=<%=mana.getId()%>">设定权限组</a>&nbsp;
						<%--    <a href="change_admin_right.jsp?id=<%=mana.getId()%>">修改权限</a>--%>
						<br>
						<a
							href="managerOper_showEditManager.action?managerId=<%=mana.getId()%>">
							信息修改</a> &nbsp;
						<a
							href="managerOper_showRangeRight.action?managerId=<%=mana.getId()%>">设定权限范围</a>
						<br>
						<a
							href="/sso/admin/infoOper_getChangeNewsRight.action?id=<%=mana.getId()%>&name=<%=maname%>&type=admin">设定新闻权限</a>&nbsp;
						<a
							href="javascript:del('managerOper_deleteInfo.action?managerId=<%=mana.getId()%>')">删除</a>
					</div>
				</td>
			</tr>

			<%
				}
				}
			%>


		</table>
		<br>
	</body>
</html>
