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
<%!//???????????????????????????????????????"??????"
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
	if(confirm("?????????????????????????????????"))
		window.navigate(link);
}
</script>
<%

	response.setHeader("Pragma","No-cache");//HTTP 1.1
	response.setHeader("Cache-Control","no-cache");//HTTP 1.0
	response.setHeader("Expires","0");//?????????proxy

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
				alert("??????????????????????????????!");
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
    <td><div class="content_title" id="zlb_title_start">?????????????????????</div id="zlb_title_end"></td>
  </tr>
  <tr> 
    <td valign="top" class="pageBodyBorder_zlb">
<!-- start:???????????? -->

<div class="cntent_k" id="zlb_content_start">   
	<!-- start:??????????????????:?????????????????????????????? -->
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
				&nbsp;???????????????<select name="siteId">
					<option value="">??????</option>
					<option value="0"  <%if(siteIdStr.equals("0")){%>selected<%}%>>??????</option>
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
				&nbsp;?????????????????????<SELECT name="type">
				  <OPTION value="">??????</option>
                  <OPTION value='0' <%if(type.equals("0")){%>selected<%}%>>????????????</OPTION>
                  <OPTION value='1' <%if(type.equals("1")){%>selected<%}%>>????????????</OPTION>
                  <!-- OPTION value='2' <%if(type.equals("2")){%>selected<%}%>>????????????</OPTION-->
                </SELECT>
				<span class="link"><img src='../images/buttons/search.png' alt='Search' width="20" height="20" title='Search'>&nbsp;<a href='javascript:document.search.submit();'>??????</a></span>
				&nbsp;&nbsp;<span class="link" help="???????????????????????????????????????" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/multi_delete.png' alt='??????' width="20" height="20" title='??????'>&nbsp;<a href='#' onclick='javascript:if(confirm("?????????????????????????")){document.userform.action="sms_del.jsp?"+document.getElementById("links").value; document.userform.submit();}'>??????</a></span>
          		&nbsp;&nbsp;<span class="link" help="????????????" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/multi_activate.png' alt='??????' width="20" height="20" title='??????'>&nbsp;<a href='#' onclick='javascript:document.userform.action="sms_checkexe.jsp?types=check"+document.getElementById("links").value;document.userform.submit();'>??????</a></span>
          		&nbsp;&nbsp;<span class="link" help="????????????" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><img src='../images/buttons/multi_minus.png' alt='??????' width="20" height="20" title='??????'>&nbsp;<a href='#' onclick='javascript:document.userform.action="sms_checkexe.jsp?types=reject"+document.getElementById("links").value;document.userform.submit();'>??????</a></span>
          	</div>
			</td>
		  </tr>
		</table>
		</form>
		<!-- end:??????????????????????????????????????????????????? -->
		<!-- start:???????????????????????? -->
		<form name='userform' action='sms_del.jsp' method='post' class='nomargin' onsubmit="">
          <table width='98%'   align="center" cellpadding='0' cellspacing='0' class='list'>
            <tr > 
              <th width='0'  class='select' align='center'><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> 
              </th>
              <th width='20'  style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="????????????">S</a></span></div></th>
              <th width='20' style='white-space: nowrap;'><div style='display:block; width: 20px;'> 
                  <span class="link linkdisabled"><a href='#' title="??????">E</a></span></div></th>
               <th width='5%' style='white-space: nowrap;'> <span class="link">??????</span></th>
              <th width='10%' style='white-space: nowrap;'> <span class="link">?????????</span></th> 
              <th width='15%' style='white-space: nowrap;'> <span class="link">????????????</span></th> 
              <th width='15%' style='white-space: nowrap;'> <span class="link">????????????</span></th>
              <th width='15%' style='white-space: nowrap;'> <span class="link">????????????</span></th>
              <th width='15%' style='white-space: nowrap;'><span class="link">????????????</span> </th>
              <th width='15%'><span class="link">????????????</span> </th>
              <th width='10%'> <span class="link">????????????</span></th>
            </tr>
    		<%
					int totalItems = smsManage.getSmsMessagesNum(null, null, null, "0", null, 
						null, null, null, null, siteIdStr,null,type);
					
					//----------????????????---------------
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
					//----------????????????---------------
					
					if(totalItems<=0){
						out.print("<tr class='oddrowbg'> " + 
			            "<td nowrap colspan='16'>"+
			            "??????????????????????????????</td></tr>");
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
		             		siteName = "??????";
		             	else if(siteId.equals("teacher"))
		             		siteName = "????????????";
		             	else
		             		siteName = basicEntityManage.getSite(siteId).getName();
		            	//????????????
						String className = "oddrowbg1";
						if(i % 2 == 0)
							className = "oddrowbg1";
						else
							className = "oddrowbg2";
			%>
            <tr class='<%=className%>'> 
              <td class='select' style='text-align: center; '> <input type='checkbox' class='checkbox' name='ids' value='<%=sms.getMsgId()%>' id='<%=fixnull(sms.getMsgId())%>' onclick="javascript:enableInput('<%=fixnull(sms.getMsgId())%>')"> 
              </td>
              <td style='text-align: center; '><span class="link" title='????????????' help="????????????????????????????????????" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><a href="sms_info.jsp?id=<%=sms.getMsgId() %>" target=_blank><img src='../images/buttons/csv.png' alt='????????????' width="20" height="20" title='????????????' border=0></a></span> 
              </td>
              <td style='text-align: center; '><span class="link" title='??????' help="?????????????????????" onMouseOver="top.showHelpInfo(this.help)" onMouseOut="top.showHelpInfo()"><a href="sms_edit.jsp?id=<%=sms.getMsgId() %>&pageInt=<%=pageInt %>&tt=0<%=link %>" ><img src='../images/buttons/edit.png' alt='??????' width="20" height="20" title='??????' border=0></a></span> 
              </td>
              <td style='text-align: center; '><%=sms.getMsgId() %></td>
              <td style='text-align: center; '><%=sms.getSender() %></td>
              <td><a href="phone_detail.jsp?id=<%=sms.getMsgId() %>" target=_blank><%=targets %></a></td>
              <td><a href="content_detail.jsp?id=<%=sms.getMsgId() %>" target=_blank><%=subContent%></a></td>
               <td style='text-align: center; '>
               <%
                  String arg = fixnull(sms.getType()).trim();
                  String outs = "????????????";
                  if(arg.equals("0")){
                    outs = "????????????";
                  }else if(arg.equals("1")){
                    outs = "????????????";
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
  <!-- end:???????????????????????? -->
</div>

<!-- ?????????????????? -->
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
