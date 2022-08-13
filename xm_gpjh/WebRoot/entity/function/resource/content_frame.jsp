<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*,com.whaty.platform.entity.recruit.*"%>
<%@ page import="com.whaty.platform.resource.basic.*,com.whaty.platform.resource.*"%>
<%@ include file="../pub/priv.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="css/admincss.css" rel="stylesheet" type="text/css">
</head>
<%
	String keyId = "course_" + openCourse.getCourse().getId();
	ResourceFactory resFactory = ResourceFactory.getInstance();
	BasicResourceManage resManage = resFactory.creatBasicResourceManage();
	
	String id = request.getParameter("id");
	List resList = new ArrayList();
	List dirList = new ArrayList();
	if(id == null || id.equals("null"))
		id = resManage.getResourceDirByKeyId(keyId).getId();
	if(id.equals("0")) {
		dirList = resManage.getResourceDirs(null, null, id, null, null, null);
		resList = resManage.getResources(null, null, null, null, null, null, null, id);
	} else {
		ResourceDir dir = resManage.getResourceDir(id);
		resList = dir.getResourceList();
		dirList = dir.getSubDir();
	}
%>
<body leftmargin="0" topmargin="0" class="scllbar">
<!--	<form name='add' action='site_addexe.jsp' method='post' class='nomargin' onsubmit="">-->
        <div class="border">
          <table width="100%" border="0" cellpadding="0" cellspacing="0">
          	
            <tr valign="bottom" class="postFormBox"> 
              <td width="60%" valign="bottom"><span>名称</span></td>
              <td width="10%">类型</td>
              <td width="30%">操作</td>
            </tr>
            <%
            	for(int i=0; i<dirList.size(); i++) {
            		ResourceDir resDir = (ResourceDir)dirList.get(i);
            %>
            <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span><%=resDir.getName()%></span></td>
              <td>[目录]</td>
              <TD>[<span class="link" onclick="javascript:window.open('edit_dir.jsp?id=<%=resDir.getId()%>', 'newwindow', 'height=150, width=300, toolbar=no , menubar=no, scrollbars=no, resizable=no, location=no, status=no')">修改名称</span>][<span class="link" onclick="if(confirm('确定删除吗？')) location.href='dir_del.jsp?id=<%=resDir.getId()%>&dir_id=<%=id%>';">删除</span>]</TD>
            </tr>
            <%
            	}
            	
            	for(int j=0; j<resList.size(); j++) {
            		Resource res = (Resource)resList.get(j);
            %>
             <tr valign="bottom" class="postFormBox"> 
              <td valign="bottom"><span><%=res.getTitle()%></span></td>
              <td>[资源]</td>
              <TD>
              [<span class="link" onclick="location.href='res_info.jsp?id=<%=res.getId()%>&dir_id=<%=id%>';">查看</span>]
              
              <%
              		if(userType.equalsIgnoreCase("teacher")) {
              %>
              [<span class="link" onclick="location.href='res_edit.jsp?id=<%=res.getId() %>&dir_id=<%=id %>';">编辑</span>]
              [<span class="link" onclick="if(confirm('确定删除吗？')) location.href='res_del.jsp?id=<%=res.getId()%>&dir_id=<%=id%>';">删除</span>]
              <%
              		}
              %>
              </TD>
            </tr>
            <%
            	}
            %>
            <tr valign="bottom"> 
              <td valign="bottom">&nbsp;</td>
              <td>&nbsp;</td>
              <td>&nbsp;</td>
            </tr>
            <tr valign="bottom"> 
              <td valign="bottom">&nbsp;</td>
              <td>
              <%
              		if(userType.equalsIgnoreCase("teacher")) {
              %>              
              [<span class="link" onclick="javascript:window.open('res_add.jsp?dir_id=<%=id%>', 'newwindow', 'height=500, width=800, toolbar=no , menubar=no, scrollbars=yes, resizable=no, location=no, status=no')">添加</span>]
              <%
              		}
              %>
              </td>
              <td>&nbsp;</td>
            </tr>
          </table>
      </div>
<!--	</form>-->
</body>
</html>
