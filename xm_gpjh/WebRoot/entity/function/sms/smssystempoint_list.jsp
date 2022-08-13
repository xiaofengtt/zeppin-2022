<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.Exception.*,com.whaty.platform.sms.*,com.whaty.platform.sms.basic.*"%>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
<link href="../css/admincss.css" rel="stylesheet" type="text/css">
<script language=javascript src="../js/filter.js"></script>
<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + 'form'];
	for (var i = 0 ; i < form.elements.length; i++) {
		if ((form.elements[i].type == 'checkbox') && (form.elements[i].name == 'ids')) {
			if (!(form.elements[i].value == 'DISABLED' || form.elements[i].disabled)) {
				form.elements[i].checked = form.listSelectAll.checked;
			}
		}
	}
	return true;
}

function doselect(objID)
{
	var tempObj;
		if(document.getElementById(objID))
		{
			tempObj = document.getElementById(objID);
			if(tempObj.checked)
			{
				tempObj.checked=false;
			}
			else
			{
				tempObj.checked=true;
			}
		}
}
</script>
</head>
<%!//判断字符串为空的话，赋值为"不详"
	String fixnull(String str) {
		if (str == null || str.equals("null"))
			str = "";
		return str;

	}
	%>
	<script  language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除该短信吗？"))
		window.navigate(link);
}
</script>
<%
    try {
    	PlatformFactory platformfactory = PlatformFactory.getInstance();
    	PlatformManage platformManage = platformfactory.createPlatformManage();
		BasicEntityManage basicEntityManage = platformfactory.creatBasicEntityManage(includePriv);
		
		SmsManagerPriv smsPriv = basicEntityManage.getSmsManagerPriv(includePriv.getSso_id());
		SmsManage smsManage = basicEntityManage.getSmsManage(smsPriv);
%>

<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">系统短信点列表</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb">
<!-- start:内容区域 -->
<div class="cntent_k" id="zlb_content_start">   
	<!-- <span class="text" ><a href="smssystempoint_add.jsp" title="添加短信点" class="button">添加短信点</a>  -->
	<hr />
	<!-- start:内容区－头部:项目数量、搜索、按钮 -->
	<table width='100%' border="0" cellpadding="0" cellspacing='0'>
    <tr>
			
			<td class='misc' style='white-space: nowrap;'>
				<div> 
                </div>
			</td>
		</tr></table>
		<!-- end:内容区－头部：项目数量、搜索、按钮 -->
		<!-- start:内容区－列表区域 -->
		<form name='userform' action='sms_del.jsp' method='post' class='nomargin' onsubmit="">
          <table width='98%' align="center" cellpadding='1' cellspacing='0' class='list'>
            <tr> 
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="详细信息">S</a></span></div></th>
               <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="编辑">E</a></span></div></th>
               <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="状态设置">A</a></span></div></th>
               <th width='5%' style='white-space: nowrap;'> <span class="link">编号</span></th>
              <th width='15%' style='white-space: nowrap;'> <span class="link">名称</span></th> 
              <th width='80%' style='white-space: nowrap;'> <span class="link">短信内容</span> 
              </th>
            </tr>
    		<%
					int totalItems = smsManage.getSmsSystemPointsNum(null);
					
					//----------分页开始---------------
					String spagesize = (String) session.getValue("pagesize");
					String spageInt = request.getParameter("pageInt");
					Page pageover = new Page();
					pageover.setTotalnum(totalItems);
					pageover.setPageSize(spagesize);
					pageover.setPageInt(spageInt);
					int pageNext = pageover.getPageNext();
					int pageLast = pageover.getPagePre();
					int maxPage = pageover.getMaxPage();
					int pageInt = pageover.getPageInt();
					int pagesize = pageover.getPageSize();
					String link = "";  
					//----------分页结束---------------
				
					List smsList = smsManage.getSmsSystemPoints(pageover,null);
					
		            for(int i=0;i<smsList.size();i++) {            
		            	SmsSystemPoint sms = (SmsSystemPoint) smsList.get(i);
		            	String status = sms.getStatus();
		            	//差色显示
						String className = "oddrowbg1";
						if(i % 2 != 0)
							className = "oddrowbg2";
			%>
            <tr class='<%=className%>'> 
              <td style='text-align: center; '><span class="link" title='详细信息' help="显示这条短信的详细信息。" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()" ><a href="smssystempoint_info.jsp?id=<%=sms.getId() %>" target=_blank><img src='../images/buttons/csv.png' alt='详细信息' width="20" height="20" title='详细信息' border=0></a></span> 
              </td>
              <td style='text-align: center; '><span class="link" title='编辑' help="修改这个系统短信点。" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><a href="smssystempoint_edit.jsp?id=<%=sms.getId() %>&pageInt=<%=pageInt %>" ><img src='../images/buttons/edit.png' alt='编辑' width="20" height="20" title='编辑' border=0></a></span>
              </td>
              <td style='text-align: center; '><span class="link" title='状态设置' help="修改这个系统短信点的状态。" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()">
              	<%if(status.equals("0")) {%><a href="smssystempoint_changestatus.jsp?id=<%=sms.getId() %>&pageInt=<%=pageInt %>&status=1" ><img src='../images/buttons/inactive.png' alt='激活' width="20" height="20" title='激活' border=0></a>
              	<%}else{%><a href="smssystempoint_changestatus.jsp?id=<%=sms.getId() %>&pageInt=<%=pageInt %>&status=0" ><img src='../images/buttons/active.png' alt='停用' width="20" height="20" title='停用' border=0></a>
              	<%}%>
              	</span>
              </td>
              <td style='text-align: center; '><%=sms.getId() %></td>
              <td style='text-align: center; '><%=sms.getName() %></td>
              <td style='text-align: center; '><%=fixnull(sms.getContent()) %></td>
            </tr>            
            <%
            	}
            %>
            
          </table>
</form>          
  <!-- end:内容区－列表区域 -->
</div>

<!-- 内容区域结束 -->
	</td>
  </tr>
  <tr> 
    <td align="center" id="pageBottomBorder_zlb"><div class="content_bottom"> <table width="98%" height="100%" border="0" cellpadding="0" cellspacing="0"><tr>
          <td valign="middle"> 
           
       <%@ include file="../pub/dividepage.jsp"%>
   
          </td>
        </tr>

      </table></td>
  </tr>
</table>
</body>
<%
	} catch (Exception e) {
%>
<script>
	alert("<%=e.getMessage() %>");
	window.history.back();
</script>
<%
	}
%>
</html>
