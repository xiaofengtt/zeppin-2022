<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
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
<s:if test="msg!=null&&msg!=''">
alert('<s:property value="msg"/>');
</s:if>
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


<script language="javascript">

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

function sub(){
document.inputgroupright.submit();
}
</script>



<body topmargin="0" rightmargin="0" leftmargin="0" bottommargin="0" scroll="no">

<form name="inputgroupright" method="post" action="managerRangeRight_updateRangeRight.action">
<input type="hidden" name="ssoUser.id" value='<s:property value="ssoUser.id"/>' />
<input type="hidden" name="managerId" value='<s:property value="managerId"/>' />
<input type="hidden" name="managerType" value='<s:property value="managerType"/>' />
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="24" valign="top">
	  <div style="position:relative;padding:4px;font-size:12px;width:100%;background-color:#cfcfcf" onselectstart="return false">
	  
	<div id="menu01" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">站点</div>
	
	<div id="menu02" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">年级</div>
	
	<div id="menu03" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">专业</div>
	<div id="menu04" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)">层次</div>
	
		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:sub();" >提交</a> &nbsp;&nbsp;
			<s:if test="managerType==3"><a href="/sso/admin/peManager.action">返回</a></s:if>
			<s:else><a href="/sso/admin/peSitemanager.action">返回</a></s:else> 	
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
		<s:iterator value="sites">
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><s:property value="name"/> </td>
				
				
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
					<s:if test="checkeSite(id)">
						<input type="checkbox" name="site" value="<s:property value="id"/>" checked="checked"><s:property value="name"/>
					</s:if>
					<s:else>
						<input type="checkbox" name="site" value="<s:property value="id"/>" ><s:property value="name"/>
					</s:else>
				</td>
			</tr>
		</s:iterator>	
		</table>
		
		</div>



		<div id="page02" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在年级范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="gradeAll" onclick="javascript:selectAllTag('grade')"></td>
		</tr>		
		<s:iterator value="grades">
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><s:property value="name"/> </td>
				
				
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
					<s:if test="checkeGrade(id)">
						<input type="checkbox" name="grade" value="<s:property value="id"/>" checked="checked"><s:property value="name"/>
					</s:if>
					<s:else>
						<input type="checkbox" name="grade" value="<s:property value="id"/>" ><s:property value="name"/>
					</s:else>
				</td>
			</tr>
		</s:iterator>	
		</table>
		
</div>



		<div id="page03" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在专业范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="majorAll" onclick="javascript:selectAllTag('major')"></td>
		</tr>		
		<s:iterator value="majors">
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><s:property value="name"/> </td>
				
				
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
					<s:if test="checkeMajor(id)">
						<input type="checkbox" name="major" value="<s:property value="id"/>" checked="checked"><s:property value="name"/>
					</s:if>
					<s:else>
						<input type="checkbox" name="major" value="<s:property value="id"/>" ><s:property value="name"/>
					</s:else>
				</td>
			</tr>
		</s:iterator>	
		</table>
		
</div>




		<div id="page04" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="2" align="center">设置在层次范围的权限
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			全选：<input type="checkbox" name="edutypeAll" onclick="javascript:selectAllTag('edutype')"></td>
		</tr>		
		<s:iterator value="edutypes">
			<tr>
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><s:property value="name"/> </td>
				
				
				<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
					<s:if test="checkeEdutype(id)">
						<input type="checkbox" name="edutype" value="<s:property value="id"/>" checked="checked"><s:property value="name"/>
					</s:if>
					<s:else>
						<input type="checkbox" name="edutype" value="<s:property value="id"/>" ><s:property value="name"/>
					</s:else>
				</td>
			</tr>
		</s:iterator>	
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