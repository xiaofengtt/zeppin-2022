<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.whaty.platform.config.PlatformConfig" %>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeSite"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeManager"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeGrade"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeMajor"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeEdutype"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PrPriManagerMajor"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PrPriManagerEdutype"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PrPriManagerGrade"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PrPriManagerSite"/>
<jsp:directive.page import="com.whaty.platform.entity.bean.PeSitemanager"/>
<%@ include file="./pub/priv.jsp"%>

<html>
<head>
<link href="<%=request.getContextPath() %>/admin/css.css" rel="stylesheet" type="text/css">
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

//out.println(strmajor);
 PeSitemanager manager = (PeSitemanager)request.getAttribute("peManager");

%>
<body topmargin="0" rightmargin="0" leftmargin="0" bottommargin="0" scroll="no">

<form name="inputgroupright" method="post" action="siteManagerOper_updateRangeRight.action">
<input type="hidden" name="managerId" value="<%=manager.getId()%>">
<input type="hidden" name="siteId" value="<%=request.getParameter("siteId")%>">
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="24" valign="top">
	  <div style="position:relative;padding:4px;font-size:12px;width:100%;background-color:#cfcfcf" onselectstart="return false">
	  
	<div id="menu01" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">站点</div>
	
	<div id="menu02" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">年级</div>
	
	<div id="menu03" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">专业</div>
	<div id="menu04" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">层次</div>
	
		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:document.inputgroupright.submit();" >提交</a> &nbsp;&nbsp;
			<% 
			String str = request.getParameter("siteId");
			System.out.println("str: "+str);
			if(str != null && !str.equals("null") && !str.equals("") ){%>
				<a href="/sso/admin/siteManagerOper_showSubManagerList.action?siteId=<%=request.getParameter("siteId")%>">返回</a>	
				<%
			}else{%>
				<a href="/sso/admin/siteManagerOper_showManagerList.action">返回</a>	
			<%} %>
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
			List siteList=(List)request.getAttribute("sites");
    
                    if(siteList!=null&&siteList.size()>0){
                    				for(int m=0;m<siteList.size();m++){	
                    					
                    					 PeSite site=(PeSite)siteList.get(m);
                    					 
		
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=site.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			<%
				Set set = manager.getSsoUser().getPrPriManagerSites();
				int c = 0;
				if(set != null && !set.isEmpty()){
					Iterator setIt = set.iterator();
					
					while(setIt.hasNext()){
						PrPriManagerSite si = (PrPriManagerSite)setIt.next();
						if(si.getPeSite().getId().equals(site.getId())){
							c = 1;
							break;
						}else{
							continue;
						}
					}
					
						if(c == 1){
							%>
					<input type="checkbox" name="site" value="<%=site.getId()%>" checked="checked"><%=site.getName()%>
				<%
						}else{
							%>
					<input type="checkbox" name="site" value="<%=site.getId()%>" ><%=site.getName()%>
					<%
						}
				
				}else{
				
				%>
				<input type="checkbox" name="site" value="<%=site.getId()%>"><%=site.getName()%><br>
				<%
				}
			
			%>
			
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
			List gradeList= (List)request.getAttribute("grade");
    
                    if(gradeList!=null&&gradeList.size()>0){
                    				for(int n=0;n<gradeList.size();n++){	
                    					
                    					 PeGrade grade=(PeGrade)gradeList.get(n);
                    					 
		
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=grade.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			
			<%
				Set gset = manager.getSsoUser().getPrPriManagerGrades();
				if(gset != null && !gset.isEmpty()){
					Iterator gradeIt = gset.iterator();
					int countGrade = 0;
					while(gradeIt.hasNext()){
						PrPriManagerGrade gr = (PrPriManagerGrade)gradeIt.next();
						if(gr.getPeGrade().getId().equals(grade.getId())){//相同
							countGrade = 1;
							break;
						}else{
							continue;
						}
					}
					
					if(countGrade == 1){
							%>
					<input type="checkbox" name="grade" value="<%=grade.getId()%>" checked="checked" > <%=grade.getName()%><br>
			
				<%
						}else{
							%>
					<input type="checkbox" name="grade" value="<%=grade.getId()%>"  > <%=grade.getName()%><br>
					<%
						}
				
				}else{
				
				%>
				<input type="checkbox" name="grade" value="<%=grade.getId()%>"  > <%=grade.getName()%><br>
				<%
				}
			
			%>
			
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
			List majorList= (List)request.getAttribute("major");
    
                 
					
				  if(majorList!=null&&majorList.size()>0){
                    				for(int n=0;n<majorList.size();n++){	
                    					
                    					 PeMajor major=(PeMajor)majorList.get(n);
			
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=major.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			
			<% 
				Set mset = manager.getSsoUser().getPrPriManagerMajors();
				if(mset !=null && !mset.isEmpty()){
					int maCount = 0;
					Iterator maIt = mset.iterator();
					while(maIt.hasNext()){
						PrPriManagerMajor ma = (PrPriManagerMajor)maIt.next();
						
						if(ma.getPeMajor().getId().equals(major.getId())){
							maCount = 1;
							break;
						}else{
							continue;
						}
					}
					
					if(maCount == 1){
							%>
				<input type="checkbox" name="major" value="<%=major.getId()%>" checked="checked"><%=major.getName()%><br>
				<% 
						}else{
						%>
				<input type="checkbox" name="major" value="<%=major.getId()%>"><%=major.getName()%><br>
				<% 
						}
				
				}else{
				%>
				<input type="checkbox" name="major" value="<%=major.getId()%>"><%=major.getName()%><br>
				<% 
				
				}
			%>
			
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
			List edutypeList= (List)request.getAttribute("edutype");
			
    
                    if(edutypeList!=null){
                    				for(int j=0;j<edutypeList.size();j++){	
                    					
                    					 PeEdutype edutype=(PeEdutype)edutypeList.get(j);
                    					 
		
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=edutype.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			
			<% 
				Set eduset = manager.getSsoUser().getPrPriManagerEdutypes();
				if(eduset !=null && !eduset.isEmpty()){
					
					Iterator eduIt = eduset.iterator();
					int eduCount = 0;
					while(eduIt.hasNext()){
						PrPriManagerEdutype edu = (PrPriManagerEdutype)eduIt.next();
						
						if(edu.getPeEdutype().getId().equals(edutype.getId())){
							eduCount = 1;
							break;
						}else{
							continue;
						}
					}
					if(eduCount == 1){
							%>
				<input type="checkbox" name="edutype" value="<%=edutype.getId()%>" checked="checked"><%=edutype.getName()%><br>
			
				<% 
						}else{
						%>
				<input type="checkbox" name="edutype" value="<%=edutype.getId()%>" ><%=edutype.getName()%><br>
				<% 
						}
				
				}else{
				%>
				<input type="checkbox" name="edutype" value="<%=edutype.getId()%>" ><%=edutype.getName()%><br>
				<% 
				
				}
			%>
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