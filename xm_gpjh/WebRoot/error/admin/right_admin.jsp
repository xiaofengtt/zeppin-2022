<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,java.net.*"%>
<//%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<//%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*,com.whaty.platform.entity.user.*" %>
<//%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
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
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();
List manager=rightManage.getManagerList();
%>

<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr > 
    <td colspan="4"  align="left" bgcolor="#D4E4EB" class="zhengwen"><div align="left"><font color="red">总站管理员列表</font> &nbsp;&nbsp;<a href="right_site_admin.jsp">分站管理员列表</a>&nbsp;&nbsp;<a href="mobile_batch.jsp">批量导入移动电话号码</a>&nbsp;&nbsp;<a href="admin_excel.jsp">批量导出总站管理员</a></div></td>
  	<td colspan="2" align="right" bgcolor="#D4E4EB" class="zhengwen"><div align="center" bgcolor="#D4E4EB" class="zhengwen"><a href="add_superadmin.jsp">添加总站管理员</a> &nbsp;&nbsp;<!-- <a href="add_admin.jsp">添加分站管理员</a>-->  &nbsp;&nbsp;<a href="right_main.jsp">返回</a></div>
  	</td>
  </tr>
  
  <tr> 
    <!-- td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">管理员ID</div></td-->
    <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">登录ID</div></td>
    <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen">管理员姓名</td>
     <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">管理员类型</div></td>
    <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">权限组</div></td>
    <!-- 
    <td  align="left"    width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">上次登陆时间</div></td>
    <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">上次登陆IP</div></td>
    <td  align="left"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">登陆次数</div></td>
    -->
    <td  align="left"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center">状态</div></td>
    <td  align="left"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">操作</div></td>
    
  </tr>
  
  <%
  if(manager!=null){
     for(int i=0;i<manager.size();i++){
     
       Manager mana=(Manager) manager.get(i); 
       
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
 
   String longId= java.net.URLEncoder.encode(fixnull(mana.getLoginId()),"UTF-8");
   String maname= java.net.URLEncoder.encode(fixnull(mana.getName()),"UTF-8");
   String mastatus=java.net.URLEncoder.encode(fixnull(mana.getStatus()),"UTF-8");
   String mobile=java.net.URLEncoder.encode(fixnull(mana.getMobilePhone()),"UTF-8");
  
  %>
  <tr> 
    <!-- td align="center"  width="5%"  height="21" bgcolor="#D4E4EB" class="zhengwen"><%=mana.getId()%></td-->
    <td align="center"  width="10%"  height="21" bgcolor="#D4E4EB" class="zhengwen"><%=mana.getLoginId()%></td>
    <td align="center"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><%=mana.getName()%></td>
    <td align="center"  width="10%"" bgcolor="#D4E4EB" class="zhengwen"><%=type%></td>
    <td align="center"   width="10%" bgcolor="#D4E4EB" class="zhengwen"><% if(fixnull(mana.getGroup_name()).equals("")) {out.println("<font color='red'>未指定权限组</font>");}else{out.println(fixnull(mana.getGroup_name()));};%></td>
    <!-- 
    <td align="center"   width="10%"  bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(mana.getPlatformInfo().getLastlogin_time())%></td>
    <td align="center"  width="10%"  bgcolor="#D4E4EB" class="zhengwen"><%=fixnull(mana.getPlatformInfo().getLastlogin_ip())%></td>
    <td  align="left"   width="10%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center"><%=mana.getLogin_num()%></div></td>
    -->
    <td  align="left"   width="5%"  bgcolor="#D4E4EB" class="zhengwen"><div align="center"><%=status%></div></td>
    <td  align="left"   width="20%" bgcolor="#D4E4EB" class="zhengwen"><div align="center"><a href="change_admin_group.jsp?id=<%=mana.getId()%>&group_id=<%=mana.getGroup_id()%>&name=<%=URLEncoder.encode(mana.getName(),"UTF-8")%>">设定权限组</a>&nbsp;<a href="change_admin_right.jsp?id=<%=mana.getId()%>&name=<%=URLEncoder.encode(mana.getName(),"UTF-8")%>&group_id=<%=mana.getGroup_id()%>">修改权限</a>
     <br><a href="edit_superadmin.jsp?id=<%=mana.getId() %>&login_id=<%=longId%>&status=<%=mastatus%>"> 信息修改</a> &nbsp;<a href="change_range_right.jsp?id=<%=mana.getId()%>&name=<%=maname%>">设定权限范围</a><br><a href="change_news_right.jsp?id=<%=mana.getId()%>&name=<%=maname%>&type=admin">设定新闻权限</a>&nbsp;<a href="javascript:del('delete_admin.jsp?id=<%=mana.getLoginId()%>')">删除</a></div></td>
  </tr>
  
<%
}
}
%>


</table>
<br>
</body>
</html>