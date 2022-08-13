<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/WEB-INF/struts-tags.tld" %>
<//%@ page import="com.whaty.platform.entity.BasicRightManage"%>
<//%@ page import="java.io.File,com.whaty.platform.entity.*,com.whaty.platform.entity.right.*,com.whaty.platform.entity.user.Manager" %>
<//%@ page import="com.whaty.platform.config.PlatformConfig" %>
<jsp:directive.page import="com.whaty.platform.sso.bean.PePriCategory"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PePriority"/>
<jsp:directive.page import="com.whaty.platform.sso.bean.PrPriRole"/>
<//%@ include file="./pub/priv.jsp"%>

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
<s:actionmessage theme="bbs0"/>
<body topmargin="0" rightmargin="0" leftmargin="0" bottommargin="0" scroll="no">


<s:form name="inputgroupright" method="post" action="changepriority_changeGroupRight.action">

<input type="hidden" value="<%=request.getParameter("roleId")%>" name="roleId" />
<input type="hidden" value="<%=request.getParameter("roleName")%>" name="roleName" />
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="24" valign="top">
    <font style="font-size:12px"><%=request.getParameter("roleName")%>权限组<br></font>
	  <div style="position:relative;padding:4px;font-size:12px;width:100%;background-color:#cfcfcf" onselectstart="return false">
	  <%
		List officeList=(List)session.getAttribute("list");
			if(officeList!=null&&officeList.size()>0){
 			 for(int i=0;i<officeList.size();i++){
    		  PePriCategory office= (PePriCategory) officeList.get(i);


		%>
			<div id="menu<%=office.getId()%>" class="menu" onMouseOver="setMouseOver(this.id)" onMouseOut="setMouseOut(this.id)" onClick="showPage(this.id)"><%=office.getName()%></div>
		<%
			}
		}
		%>	
		
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:document.inputgroupright.submit();" >提交</a> &nbsp;&nbsp;<a href="role.action">返回</a>	
	  </div>
	 
	</td>
  </tr>
  <tr>
    <td>
    <%
     
        if(officeList!=null&&officeList.size()>0){
                 for(int j=0;j<officeList.size();j++){
                            PePriCategory office= (PePriCategory) officeList.get(j);
                 
                			
        
                    					
  
    %>
    
		<div id="page<%=office.getId()%>" class="page">
		 
		 <table cellPadding=2 cellSpacing=1  border="0" bgcolor=#3F6C61 align=center width="100%">


		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" colspan="3" align="center">设置在该部门的权限 </td>
	
		</tr>		
		<%
			 PePriCategory rightMode = null;
			Set officeModel=office.getPePriCatetories();
    
                    if(officeModel!=null&&officeModel.size()>0){
                    			Iterator it = officeModel.iterator();
                    				while(it.hasNext()){	
                    					 rightMode=(PePriCategory)it.next();
		%>



		<tr>
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%"><%=rightMode.getName()%></td>
			
			
			<td bgcolor="#D4E4EB" class="zhengwen" width="35%">
			<%
			      Set right=rightMode.getPePriorities();
			      if(right!=null&&right.size()>0){
			         Iterator nt = right.iterator();
			         	 while(nt.hasNext()){
			              PePriority mright=(PePriority) nt.next();
			         
			       
			
			
			%>
			<%
				List checkedList = (List)session.getAttribute("priList");
				PePriority checkedPe = null;
				
				
				if(checkedList != null && checkedList.size()>0){
					
					int count = 0;
					for (int mm=0; mm<checkedList.size();mm++){
						checkedPe = ((PrPriRole)checkedList.get(mm)).getPePriority();
						if(mright.getId().equals(checkedPe.getId())){
							count = 1;
							break;
						}else{
							continue;
						}
					}
					
					if(count == 1){
						%>
						<input type="checkbox" name="checkgroup<%=rightMode.getId()%>" value="<%=mright.getId()%>" onclick=unselectall('<%=rightMode.getId()%>') checked="checked"> <%=mright.getName()%><br>
						<%
					}else{
						%>
						<input type="checkbox" name="checkgroup<%=rightMode.getId()%>" value="<%=mright.getId()%>" onclick=unselectall('<%=rightMode.getId()%>') > <%=mright.getName()%><br>
					<%
					}
				}else{
				%>
					<input type="checkbox" name="checkgroup<%=rightMode.getId()%>" value="<%=mright.getId()%>" onclick=unselectall('<%=rightMode.getId()%>') > <%=mright.getName()%><br>
				<%
				
				}
			//out.println(group_id);out.println(mright.getId());
			//out.println(rightMode.getId());
			%>
			<%
			         }
			      
			      }
			
			%>
			
			</td>
			<td bgcolor="#D4E4EB" class="zhengwen">
			<input type="checkbox" name="modelgroup<%=rightMode.getId()%>" onclick=selectall('<%=rightMode.getId()%>')>
        全选
			<input type="hidden" value="<%=office.getId()%>" name="catId" />
			</td>
		</tr>


		
		
	<%
	}
	}
                    
	%>	
	
		</table>
		
</div>

<%
	}
	   }%>

	 

	</td>
  </tr>
</table>
</s:form>
</body>

</html>