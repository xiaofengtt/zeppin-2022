<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.basic.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.entity.test.*"%>
<%@ page import="com.whaty.platform.Exception.*,com.whaty.platform.sms.*,com.whaty.platform.sms.basic.*"%>
<jsp:directive.page import="com.whaty.platform.database.oracle.standard.entity.user.OracleManagerPriv"/>
<%@ include file="../pub/priv.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>tableType_1</title>
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
function enableInput(objID)
{
	var tempObj,inputObj;
		if(document.getElementById(objID))
		{
			tempObj = document.getElementById(objID);
			inputObj = document.getElementById(objID+'note');
			if(tempObj.checked)
			{
				inputObj.disabled = false;
			}
			else
			{
				inputObj.disabled = true;
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

	private String limit(String str, int len) {
		if (str == null) {
			return "";
		}
		str = str.trim();
		StringBuffer r = new StringBuffer();
		int l = str.length();
		float count = 0;
		for (int i = 0; i < l; ++i) {
			char c = str.charAt(i);
			if (c > 255 || c < 0) {
				++count;
				r.append(c);
			} else {
				count += 0.5;
				r.append(c);
			}
			if (count >= len) {
				if (i < l - 1) {
					r.append("");
				}
				break;
			}
		}
		return r.toString();
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

	response.setHeader("Pragma","No-cache");//HTTP 1.1
	response.setHeader("Cache-Control","no-cache");//HTTP 1.0
	response.setHeader("Expires","0");//防止被proxy

	String siteIdStr = request.getParameter("siteId");
	String type = fixnull(request.getParameter("type"));
	if(siteIdStr == null)
		siteIdStr = "";
    try {
    	PlatformFactory platformfactory = PlatformFactory.getInstance();
  //  	ManagerPriv includePriv = new OracleManagerPriv("504");
		BasicEntityManage basicEntityManage = platformfactory.creatBasicEntityManage(includePriv);
		SmsManagerPriv smsPriv = basicEntityManage.getSmsManagerPriv(includePriv.getSso_id());
		if(smsPriv.getSms !=  1){
			%>
			<script  language="javascript">
				alert("您没有浏览短信的权限!");
				window.history.back(-1);
			</script>
			
			<%
			return;
		}
		SmsManage smsManage = basicEntityManage.getSmsManage(smsPriv);
%>

<body leftmargin="0" topmargin="0" class="scllbar">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td><div class="content_title" id="zlb_title_start">待审核短信列表</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb">
<!-- start:内容区域 -->

<div class="cntent_k" id="zlb_content_start">   
	<!-- start:内容区－头部:项目数量、搜索、按钮 -->
	<table width='100%' border="0" cellpadding="0" cellspacing='0'>
    <tr>
			
			<td class='misc' style='white-space: nowrap;'>
				<div> 
                </div>
			</td>
		</tr></table>
		<form name=search action="sms_check.jsp" method=post>
  		<table width='100%' border="0" cellpadding="0" cellspacing='0' style='margin-bottom: 5px'>		  
		  <tr> 
			<td style='white-space: nowrap;'>
			<div style="padding-left:6px"> 
				&nbsp;请选择站点<select name="siteId">
					<option value="">全部</option>
					<option value="0"  <%if(siteIdStr.equals("0")){%>selected<%}%>>总站</option>
					<% 
						List siteList = basicEntityManage.getSites();
						for(int i=0; i<siteList.size(); i++) {
							Site site = (Site)siteList.get(i);
					%>
					<option value="<%=site.getId() %>" <%if(siteIdStr.equals(site.getId())){%>selected<%}%>><%=site.getName() %></option>
					<% 
						}
					%>
				</select>
				&nbsp;请选择短信类型<SELECT name="type">
				  <OPTION value="">全部</option>
                  <OPTION value='0' <%if(type.equals("0")){%>selected<%}%>>普通短信</OPTION>
                  <OPTION value='1' <%if(type.equals("1")){%>selected<%}%>>定时短信</OPTION>
                  <!-- OPTION value='2' <%if(type.equals("2")){%>selected<%}%>>系统短信</OPTION-->
                </SELECT>
				<span class="link"><img src='../images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.search.submit();'>查找</a></span>
				&nbsp;&nbsp;<span class="link" help="将选中的短信从数据库中删掉" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/multi_delete.png' alt='删除' width="20" height="20" title='删除'>&nbsp;<a href='#' onclick='javascript:if(confirm("要批量删除短信吗?")){document.userform.action="sms_del.jsp?"+document.getElementById("links").value; document.userform.submit();}'>删除</a></span>
          		&nbsp;&nbsp;<span class="link" help="审核短信" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/multi_activate.png' alt='审核' width="20" height="20" title='审核'>&nbsp;<a href='#' onclick='javascript:document.userform.action="sms_checkexe.jsp?types=check"+document.getElementById("links").value;document.userform.submit();'>审核</a></span>
          		&nbsp;&nbsp;<span class="link" help="驳回短信" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/multi_minus.png' alt='驳回' width="20" height="20" title='驳回'>&nbsp;<a href='#' onclick='javascript:document.userform.action="sms_checkexe.jsp?types=reject"+document.getElementById("links").value;document.userform.submit();'>驳回</a></span>
          	</div>
			</td>
		  </tr>
		</table>
		</form>
		<!-- end:内容区－头部：项目数量、搜索、按钮 -->
		<!-- start:内容区－列表区域 -->
		<form name='userform' action='sms_del.jsp' method='post' class='nomargin' onsubmit="">
          <table width='98%'   align="center" cellpadding='0' cellspacing='0' class='list'>
            <tr > 
              <th width='0'  class='select' align='center'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> 
              </th>
              <th width='20'  style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="详细信息">S</a></span></div></th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="编辑">E</a></span></div></th>
               <th width='5%' style='white-space: nowrap;'> <span class="link">编号</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">发送人</span></th> 
              <th width='15%' style='white-space: nowrap;'> <span class="link">发送对象</span></th> 
              <th width='15%' style='white-space: nowrap;'> <span class="link">短信内容</span></th>
              <th width='15%' style='white-space: nowrap;'> <span class="link">短信类型</span></th>
              <th width='15%' style='white-space: nowrap;'><span class="link">定时时间</span> </th>
              <th width='15%'><span class="link">发送站点</span> </th>
              <th width='10%'> <span class="link">驳回原因</span></th>
            </tr>
    		<%
					int totalItems = smsManage.getSmsMessagesNum(null, null, null, "0", null, 
						null, null, null, null, siteIdStr,null,type);
					
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
					String link = "&siteId=" +siteIdStr+"&type=" +type;  
					//----------分页结束---------------
					
					if(totalItems<=0){
						out.print("<tr class='oddrowbg'> " + 
			            "<td nowrap colspan='16'>"+
			            "没有符合条件的信息！</td></tr>");
					}else{
					List smsList = smsManage.getSmsMessagesList(pageover,null, null, null, "0", null, 
						null, null, null, null, siteIdStr,null,type);
					
		            for(int i=0;i<smsList.size();i++) {            
		            	SmsMessage sms = (SmsMessage) smsList.get(i);
		             	String status = sms.getStatus();
		             	
		             	String targets = sms.getTargets();
		             	String[] targetsArr = {""};
		             	if(targets != null && targets.indexOf("'")>=0){
		             	 targetsArr= targets.split("'");
		             	}
						if(targets != null && targetsArr.length==1 && targetsArr[0].equals("") && targets.indexOf(",")>=0){
		             	 targetsArr= targets.split(",");
		             	}
			            
			            if(targetsArr.length > 2)
			            	targets = targetsArr[0] + "," + targetsArr[1] + "...";
			            String subContent = limit(sms.getContent(),10);
		             	String siteId = sms.getSiteId();
		             	String siteName = "";
		             	if(siteId.equals("0"))
		             		siteName = "总站";
		             	else if(siteId.equals("teacher"))
		             		siteName = "教师发送";
		             	else
		             		siteName = basicEntityManage.getSite(siteId).getName();
		            	//差色显示
						String className = "oddrowbg1";
						if(i % 2 == 0)
							className = "oddrowbg1";
						else
							className = "oddrowbg2";
			%>
            <tr class='<%=className%>'> 
              <td class='select' style='text-align: center; '> <input type='checkbox' class='checkbox' name='ids' value='<%=sms.getMsgId()%>' id='<%=fixnull(sms.getMsgId())%>' onclick="javascript:enableInput('<%=fixnull(sms.getMsgId())%>')"> 
              </td>
              <td style='text-align: center; '><span class="link" title='详细信息' help="显示这条短信的详细信息。" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><a href="sms_info.jsp?id=<%=sms.getMsgId() %>" target=_blank><img src='../images/buttons/csv.png' alt='详细信息' width="20" height="20" title='详细信息' border=0></a></span> 
              </td>
              <td style='text-align: center; '><span class="link" title='编辑' help="修改这条短信。" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><a href="sms_edit.jsp?id=<%=sms.getMsgId() %>&pageInt=<%=pageInt %>&tt=0<%=link %>" ><img src='../images/buttons/edit.png' alt='编辑' width="20" height="20" title='编辑' border=0></a></span> 
              </td>
              <td style='text-align: center; '><%=sms.getMsgId() %></td>
              <td style='text-align: center; '><%=sms.getSender() %></td>
              <td><a href="phone_detail.jsp?id=<%=sms.getMsgId() %>" target=_blank><%=targets %></a></td>
              <td><a href="content_detail.jsp?id=<%=sms.getMsgId() %>" target=_blank><%=subContent%></a></td>
               <td style='text-align: center; '>
               <%
                  String arg = fixnull(sms.getType()).trim();
                  String outs = "普通短信";
                  if(arg.equals("0")){
                    outs = "普通短信";
                  }else if(arg.equals("1")){
                    outs = "定时短信";
                  }
                  out.print(outs);
                %>
               </td>
              <td style='text-align: center; '><%=fixnull( arg.equals("0")?"":sms.getSetTime()) %></td>
              <td style='text-align: center; '><%=siteName %></td>
              <td style='text-align: center; '><input type=text name=rejectNote id="<%=fixnull(sms.getMsgId() )%>note" size=6 disabled></td>
            </tr>
            
            <%}}%>
             
            <input type="hidden" name="pageInt" value="<%=pageInt %>">
			<input type="hidden" name="tt" value="0">
			<input type="hidden" name="links" id="links" value="<%=link %>">
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
