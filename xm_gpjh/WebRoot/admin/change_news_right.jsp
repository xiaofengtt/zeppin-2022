<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<jsp:directive.page import="com.whaty.platform.info.NewsType"/>
<jsp:directive.page import="com.whaty.platform.info.bean.InfoNewsType"/>
<%@ include file="./pub/priv.jsp"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<%! 
  String fixnull(String str){
     if(str==null||str.equals("null"))
       return "";
     else return str;
  }
%>
<script language="javascript">
	function doComit() {
	/*
		var check = window.document.getElementsByName("listMultiAction");
		c = 0;
		for(i =0 ;i<check.length;i++){
			if(check[i].checked == 'true'){
				c = 1;
				break;
			}
		}
		if(c == 0){
			alert("请至少选择一项");
			return false;
		}
		*/
		if(confirm("您确定要为这位管理员更改这些新闻类型的权限吗？")){
		    document.forms["user-form"].action = "/sso/admin/infoOper_updateInfoUserRight.action";
		    document.forms["user-form"].submit();
		}
	}
</script>

<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + '-form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'listMultiAction')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}
</script>
<html>
<head>
<link href="<%=request.getContextPath() %>/admin/css.css" rel="stylesheet" type="text/css">
<title>新闻权限管理</title>
</head>

<body>

<%
try{
	String id = request.getParameter("id");
	System.out.println(id);
	String name = request.getParameter("name");
	String type = request.getParameter("type");
	if(type == null || type.equals("null"))
		type = "";

	String parent_id = request.getParameter("parent_id");
	String parent_name = "无父类型";
	String up_id = "";//父类型的父类型id
	
	 
	if(parent_id == null || parent_id.equals("") || parent_id.equals("null"))
		parent_id = "0";
	InfoNewsType parent = (InfoNewsType)request.getAttribute("newstype");
	parent_name = parent.getName();	
	if(!parent_id.equals("0")&& parent.getInfoNewsType() != null)
		up_id = parent.getInfoNewsType().getId();
					
	List right_types = (List)request.getAttribute("list");
	

%>
<form name='user-form' action='' method='post' class='nomargin'
	onsubmit="">
	<input type="hidden" name="id" value="<%=id%>">
	<input type="hidden" name="name" value="<%=name%>">
	<input type="hidden" name="type" value="<%=type%>">
	
<table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


<tr > 
    <td colspan="10"  align="left" bgcolor="#D4E4EB" class="zhengwen"><div align="left"><font color="red">新闻类型列表</font> &nbsp;&nbsp;
    
										父类型： <%=parent_name%>
										<%if(!"0".equals(parent_id)){ %>
										<a href="/sso/admin/infoOper_getChangeNewsRight.action?parent_id=<%=up_id%>&id=<%=id%>&name=<%=name%>&type=<%=type%>">返回上层类型</a>
										<%} %>
										<a href="/sso/admin/infoOper_showNewsTypeAdd.action?parent_id=<%=parent_id%>&id=<%=id%>&name=<%=name%>&type=<%=type%>">添加新类型</a>

    
    </div></td>
  </tr>
  
  <tr> 
    <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">类型名称(点击进入子类型)</div></td>
    <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center">活动状态</div></td>
    <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen">类型介绍</td>
     <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">类型标签</div></td>
    <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">										
    	<input type='checkbox' class='checkbox' name='listSelectAll'
											value='true' onClick="listSelect('user')"></div></td>
  </tr>
  
  <%
		List newsTypeList = (List)request.getAttribute("newtypeList");
		
		for (int i = 0; i < newsTypeList.size(); i++) {
			InfoNewsType newsType = (InfoNewsType) newsTypeList.get(i);
			String selected = "";
			if(right_types != null && !right_types.isEmpty() && right_types.contains(newsType.getId()))
				selected = "checked";
				
  
  %>
			 <tr> 
			  <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center"><a href="/sso/admin/infoOper_getChangeNewsRight.action?parent_id=<%=newsType.getId()%>&id=<%=id%>&name=<%=name%>&type=<%=type%>"><%=newsType.getName()%></a></div></td>
			  <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" ><div align="center"><%if("1".equals(newsType.getStatus())) out.print("活动");else out.print("不活动");%></div></td>
			  <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen"><%=newsType.getNote()%></td>
			   <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center"><%=fixnull(newsType.getTag())%></div></td>
			  <td  align="left" width="10%" bgcolor="#D4E4EB" class="zhengwen"><div align="center">
			  							<a href="/sso/admin/infoOper_showEdit.action?newsTypeId=<%=newsType.getId()%>&parent_id=<%=newsType.getInfoNewsType()!= null? newsType.getInfoNewsType().getId() : "0"%>&id=<%=id%>&name=<%=name%>&type=<%=type%>">修改</a>&nbsp;&nbsp;
										<input type='checkbox' class='checkbox' name='listMultiAction'
											value='<%=newsType.getId()%>' <%=selected%>>
										<input type="hidden" name="page_news_type_ids" value="<%=newsType.getId()%>">											
											</div></td>
			</tr>
  
<%
		}
%>
			 <tr> 
			  <td  align="left"  width="10%" bgcolor="#D4E4EB" class="zhengwen" colspan="9">
			  		<div align="center">
			  			<input type="button" name="bt" value="提交" OnClick="javascript:doComit()">
<%
		String return_link = "/sso/admin/managerOper_showManagerList.action";
		if("site_admin".equals(type))
			return_link = "/sso/admin/siteManagerOper_showManagerList.action";
			
%>			  			
			  			<input type="button" name="return" value="返回管理员列表" 
			  			 Onclick="javascript:window.navigate('<%=return_link%>')">
					</div>
			  </td>
			</tr>
<%
}catch(Exception e){
	out.print(e);
}
%>


</table>
</form>
</body>
</html>