<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*,com.whaty.platform.entity.user.Manager,com.whaty.platform.entity.basic.*" %>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="css.css" rel="stylesheet" type="text/css">
<title>权限管理</title>

</head>
<style>
div.menu{
	cursor:hand;
	position:relative;
	float:left;
	padding-top:4px;
	padding-bottom:2px;
	padding-left:12px;
	padding-right:12px;
	background-color:#F2F2EE;
	border:1px solid #7C93B1;
	white-space: nowrap;
}

div.menuDown{
	cursor:hand;
	position:relative;
	float:left;
	color:#FFFFFF;
	padding-top:4px;
	padding-bottom:2px;
	padding-left:12px;
	padding-right:12px;
	background-color:#92B5CF;
	border:1px solid #ffffff;
	white-space: nowrap;
}
div.page{
	position:relative;
	top:0px;
	width:100%;
	height:100%;
	padding:28 8 8 8;
	font-size:12px;
	background-color:#f6f6f6;
	overflow:auto;
	display:none;
}
</style>
<script>
var prePageID = "null";
function setMouseOver(id)
{
	if(getByID(id))		getByID(id).className="menuDown";
}
function setMouseOut(id)
{
	if(getByID(id) && id.substring(4,id.length)!=prePageID.substring(4,prePageID.length))
	{
		getByID(id).className="menu";
	}
}
function setOut(id)
{
	if(getByID(id))
	{
		getByID(id).className="menu";
	}
}
function showPage(mID)
{
	var curPageID;
	curPageID = mID.substring(4,mID.length);
	if(getByID("page"+curPageID)&&getByID("page"+curPageID).style.display!="block")
	{
		getByID("page"+curPageID).style.display="block";
		if(prePageID && mID!=prePageID)
		{
			hidePage(prePageID);
			setOut("menu"+prePageID.substring(4,prePageID.length));
		}
		setMouseOver(mID);
		prePageID = "page"+curPageID;
	}
}
function hidePage(id)
{
	if(getByID(id))	getByID(id).style.display="none";
}
function getByID(id)
{
	if(document.getElementById(id))		return document.getElementById(id);
	else return false;
}
</script>


<script language=javascript>

function unselectall(index)
{
	temp = "document.inputgroupright.modelgroup" + index;
	modelgroup = eval(temp);
	temp = "document.inputgroupright.checkgroup" + index;
	checkgroup = eval(temp);

    if(modelgroup.checked)
    {
		modelgroup.checked = modelgroup.checked & 0;
    }
    else
    {
    	var checkall = 1;
		var i = 0;
	
	    while (i < checkgroup.length)
	    {
			if(!checkgroup[i].checked)
			{
				checkall = 0;
				break;
			}
			i++;
	    }
	    
	    if(checkall != 0)
	    	modelgroup.checked = modelgroup.checked | 1;
    }
}
function selectall(index)
{
	temp = "document.inputgroupright.modelgroup" + index;
	modelgroup = eval(temp);
	temp = "document.inputgroupright.checkgroup" + index;
	checkgroup = eval(temp);

	if(checkgroup == null)
	{
		return;
	}

	if(1 < checkgroup.length)
	{
		var i = 0;
	    while (i < checkgroup.length)
	    {
			checkgroup[i].checked = modelgroup.checked;
			i++;
	    }
    }
    else
    	checkgroup.checked = modelgroup.checked;
}


</script>

<%!
	//判断字符串为空的话，赋值为""
	String fixnull(String str)
	{
	    if(str == null || str.equals("null") || str.equals("null"))
			str = "";
			return str;
	}
%>

<%
PlatformFactory platformfactory = PlatformFactory.getInstance();
BasicRightManage rightManage = platformfactory.creatBasicRightManage();


String id=java.net.URLDecoder.decode(fixnull(request.getParameter("id")),"UTF-8");
String name=java.net.URLDecoder.decode(fixnull(request.getParameter("name")),"UTF-8");

/*RightGroup rightgroup=rightManage.getRightGroup(group_id);
String strsite=fixnull(rightgroup.getSite());
String strgrade=fixnull(rightgroup.getGrade());
String strmajor=fixnull(rightgroup.getMajor());
String stredutype=fixnull(rightgroup.getEduType());*/

Manager manager=rightManage.getAdmin(id);
String strsite=fixnull(manager.getSite());
String strgrade=fixnull(manager.getGrade());
String strmajor=fixnull(manager.getMajor());
String stredutype=fixnull(manager.getEduType());

//out.println(strmajor);


%>
<body topmargin="0" rightmargin="0" leftmargin="0" bottommargin="0" scroll="no">

<form name="inputgroupright" method="post" action="change_range_rightexe.jsp">
<input type="hidden" name="id" value="<%=id%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="24" valign="top">
	  <div style="position:relative;padding:4px;font-size:12px;width:100%;background-color:#cfcfcf" onselectstart="return false">
	  
	<div id="menu01" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">站点</div>
	
	<div id="menu02" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">年级</div>
	
	<div id="menu03" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">专业</div>
	<div id="menu04" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">层次</div>
	
		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:document.inputgroupright.submit();" >提交</a> &nbsp;&nbsp;<a href="right_admin.jsp">返回</a>	
	  </div>

	</td>
  </tr>
  <tr>
    <td>
   
    
		<div id="page01" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在站点范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="siteAll" onclick="javascript:selectAllTag('site')"></td>
		</tr>		
		<%
			List siteList=rightManage.getSiteList();
    
                    if(siteList!=null&&siteList.size()>0){
                    				for(int m=0;m<siteList.size();m++){	
                    					
                    					 Site site=(Site)siteList.get(m);
                    					 
		
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=site.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			<%//=rightgroup.getSite().indexOf(site.getId())%>
			<input type="checkbox" name="site" value="<%=site.getId()%>" <% if(!strsite.equals("")) {if(strsite.indexOf(site.getId())>=0||strsite.equals(site.getId())){%> checked<%}}%>> (<%=site.getId()%>)<%=site.getName()%><br>
			
			
			
			
			</td>
			
			
		
		</tr>


		<%
			  
			         }
			      
			      }
			
			%>
		
		
	
	
	

		</table>
		
</div>



		<div id="page02" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在年级范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="gradeAll" onclick="javascript:selectAllTag('grade')"></td>
		</tr>		
		<%
			List gradeList=rightManage.getGradeList();
    
                    if(gradeList!=null&&gradeList.size()>0){
                    				for(int n=0;n<gradeList.size();n++){	
                    					
                    					 Grade grade=(Grade)gradeList.get(n);
                    					 
		
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=grade.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			
			<input type="checkbox" name="grade" value="<%=grade.getId()%>"  <% if(!strgrade.equals("")){ if(strgrade.indexOf(grade.getId())>=0){%> checked<%}}%>>(<%=grade.getId()%>) <%=grade.getName()%><br>
			
			
			
			
			</td>
			
			
		</tr>


		
	<%
			  
			         }
			      
			      }
			
			%>	
		
	
	
	

		</table>
		
</div>



		<div id="page03" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在专业范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="majorAll" onclick="javascript:selectAllTag('major')"></td>
		</tr>		
		<%
			List majorList=rightManage.getMajorList();
    
                 
					
				  if(majorList!=null&&majorList.size()>0){
                    				for(int n=0;n<majorList.size();n++){	
                    					
                    					 Major major=(Major)majorList.get(n);
			
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=major.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			
			<input type="checkbox" name="major" value="<%=major.getId()%>" <% if(!strmajor.equals("")){if((""+strmajor).indexOf(major.getId())>=0){%> checked<%}}%>> (<%=major.getId()%>)<%=major.getName()%><br>
			
			
			
			</td>
			
			
			
		</tr>

<%
		}	  
			         }
			      
			      
			
			%>
			
		
		
		
	
	
	

		</table>
		
</div>




		<div id="page04" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在层次范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="edutypeAll" onclick="javascript:selectAllTag('edutype')"></td>
		</tr>		
		<%
			List edutypeList=rightManage.getEduTypeList();
    
                    if(edutypeList!=null){
                    				for(int j=0;j<edutypeList.size();j++){	
                    					
                    					 EduType edutype=(EduType)edutypeList.get(j);
                    					 
		
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=edutype.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			
			<input type="checkbox" name="edutype" value="<%=edutype.getId()%>"  <% if(!stredutype.equals("")){if(stredutype.indexOf(edutype.getId())>=0){%> checked<%}}%>>(<%=edutype.getId()%>) <%=edutype.getName()%><br>
			
			
			
			
			</td>
			
			
			
		</tr>


		
		<%
			  
			         }
			      
			      }
			
			%>
		
	
	
	

		</table>
		
</div>



	</td>
  </tr>
</table>


</form>
</body>
<script>
function selectAllTag(tag)
{
	var tagAll = document.getElementsByName(tag+"All");
	var tags = document.getElementsByName(tag);
	for(i=0;i<tags.length;i++)
	{
		if(tagAll[0].checked)
		{
			if(!tags[i].checked)
				tags[i].checked=true;
		}
		else
		{
			if(tags[i].checked)
				tags[i].checked=false;
		}
	}
}
</script>
</html>