<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeSitemanager"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeSite"/>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<% String path = request.getContextPath() +"/admin/";%>
<link href="<%= path %>css.css" rel="stylesheet" type="text/css">
<title>权限组管理</title>
</head>

<body>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>
<%!
	//判断字符串为空的话，赋值为""
	String fixnull1(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>
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
	List manager= (List)request.getAttribute("list");
%>

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr > 
<% PeSite site = (PeSite)request.getAttribute("site");%>
    <td colspan="10"  align="left" bgcolor="#D4E4EB" class="zhengwen"><div align="center"><font color="red"><%=site.getName() %> 子管理员列表</font> </div></td>
  </tr>
  
  <tr> 
    <!--td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">管理员ID</div></td-->
    <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">登录ID</div></td>
    <td  align="center"  width="10%" bgcolor="#D4E4EB" class="zhengwen">管理员姓名</td>
      <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">移动电话号码</div></td>
       <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">权限组</div></td>
    <td  align="left" width="20%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">所属站点</div></td>
    <!-- 
    <td  align="left"    width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">上次登陆时间</div></td>
    <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">上次登陆IP</div></td>
    <td  align="left"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">登陆次数</div></td>
    -->
    <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">状态</div></td>
    <!-- td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">分站新闻管理权限</div></td-->    
    <td  align="left"   width="20%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">操作</div></td>
    
  </tr>
  
  <%
  if(manager!=null){
     for(int i=0;i<manager.size();i++){
     
       PeSitemanager mana=(PeSitemanager) manager.get(i); 
       
       String status=fixnull1(mana.getEnumConstByFlagIsvalid()!=null ? mana.getEnumConstByFlagIsvalid().getName():"");
     String type=fixnull1(mana.getSsoUser().getEnumConstByUserType()!=null ? mana.getSsoUser().getEnumConstByUserType().getName():"");
 
   	String longId= java.net.URLEncoder.encode(fixnull(mana.getSsoUser().getLoginId()),"UTF-8");
   	String maname= java.net.URLEncoder.encode(fixnull(mana.getName()),"UTF-8");
    String mastatus=status;
    String mobile=mana.getMobilePhone();
	String siteId=java.net.URLEncoder.encode(fixnull(mana.getPeSite().getId()),"UTF-8");
	String id = java.net.URLEncoder.encode(fixnull(mana.getId()),"UTF-8");
	String role = mana.getSsoUser().getPePriRole() != null ? mana.getSsoUser().getPePriRole().getName(): "<font color='red'>未指定权限组</font>";
	
  %>
  <tr> 
    <!--td align="center"  width="10%"  height="21" bgcolor="#D4E4EB" class="zhengwen"><%=mana.getId()%></td-->
     <td align="center"  width="10%"  height="21" bgcolor="#D4E4EB" class="zhengwen"><%=longId%></td>
    <td align="center"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><%=maname%></td>
    <td align="center"  width="10%"" bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(mobile)%></td>
    <td align="center"  width="10%"" bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(role)%></td>
   <td align="center"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(mana.getPeSite().getName())%></td>
    <td  align="left"   width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center"><%=status%></div></td>
    <!-- td  align="left"   width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">
    		<a href="javascript:window.navigate('site_news_managerexe.jsp?admin_id=<%=mana.getId()%>&site_id=<%=mana.getPeSite().getId()%>')">赋权</a>
    		<a href="javascript:window.navigate('site_news_manager_delexe.jsp?admin_id=<%=mana.getId()%>&site_id=<%=mana.getPeSite().getId()%>')">取消</a>
	   	</div></td-->    
    <td  align="left"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center"> 
    	<a href="/sso/admin/siteManagerOper_changeAdminGroup.action?managerId=<%=id%>&type=sub&siteId=<%= siteId%>">设定权限分组</a>
    	<a href="/sso/admin/siteManagerOper_showRangeRight.action?managerId=<%=mana.getId()%>&siteId=<%= siteId%>">设定权限范围</a>
     <br><a href="/sso/admin/siteManagerOper_showEditManager.action?managerId=<%=id%>&type=sub&siteId=<%=siteId %>"> 信息修改</a> &nbsp;&nbsp;<a href="javascript:del('/sso/admin/siteManagerOper_deleteInfo.action?managerId=<%=id%>&type=sub&siteId=<%=siteId %>')">删除</a>
     
     </div></td>
  </tr>
  
<%
}
}

%>


</table>
<br>
<div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="javascript:window.close()">关闭</a></div>
</body>
</html>