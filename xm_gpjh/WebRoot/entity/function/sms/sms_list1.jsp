<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.sms.*,com.whaty.platform.sms.basic.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../teacher/pub/priv.jsp"%>
<%!
	//判断字符串为空的话，赋值为"不详"
	String fixnull(String str)
	{
	    if(str == null || str.equals("") || str.equals("null"))
			str = "不详";
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

<script language="javascript">
function checkForm(form){
	if(form.postalcode.value.length>6){
		alert("邮政编码不应超过6位");
		return false;
	}
	return true;
	
	if(isNull(document.form_xml.name.value))
	{
	}
	else
	{
		alert("输入名称为空!");
		document.form_xml.name.focus();
		document.form_xml.name.select();
		return false;
	}
}
</script>

<script language="JavaScript">
	function listSelect(listId) {
	var form = document.forms[listId + 'form'];
	var check = document.getElementsByName("listMultiAction");
	for (var i = 0 ; i < check.length; i++) {
		if ((check[i].type == 'checkbox') && (check[i].name == 'listMultiAction')) {
			//if (!(check.value == 'DISABLED' || check.disabled)) {
				check[i].checked = form.listSelectAll.checked;
			//}
		}
	}
	return true;
}
</script>
<script  language="javascript">
function cfmdel(link)
{
	if(confirm("您确定要删除本课程吗？"))
		window.navigate(link);
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
<%
	SmsMessage sms = null;
 %>
<%

try
{ 
	SmsManagerPriv smsPriv = teacherOperationManage.getSmsManagerPriv();
	SmsManage smsManage = teacherOperationManage.getSmsManage(smsPriv);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<br>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
			
			<tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <!-- tr>
                <td height="86" valign="top" background="../images/top_01.gif"><img src="../images/tlq.gif" width="217" height="86"></td>
              </tr-->
              <tr>
                <td align="center" valign="top"><table width="750" border="1" cellspacing="0" cellpadding="0" bordercolordark="BDDFF8" bordercolorlight="#FFFFFF">
              <tr>
                      <td bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="50" align="center" valign="bottom"><img src="../images/tlq_04.gif" width="40" height="44"></td>
                            <td width="150" valign="top" class="text3" style="padding-top:25px"><%= openCourse.getCourse().getName() %> 短信列表</td>
                            <td background="../images/wt_03.gif" align=right style="padding-top:25px;padding-right:20px" ><a href="new_sms.jsp"><font size=+1>发送短信</font></a></td>
                          </tr>
                        </table></td>
                    </tr>
                    <form action="sms_delexe.jsp" name="userform">
            <tr> 
          <td valign="top"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0">
                          <tr align="center" bgcolor="#4582B1"> 
                
             <th width='4%' class='select' align='center'>
              </th> 
              	 <!-- td><input type='checkbox' class='checkbox' name='listSelectAll' value='true'  onClick="listSelect('user')"> </td-->
                <td align="center" class="title" width=25%>内容</td>
                <td align="center" class="title" width=30%>发送对象</td>
                <td align="center" class="title" width=10%>创建者</td>
                <td align="center" class="title" width=15%>时间</td>
                <td align="center" class="title" width=10%>状态</td>
              	<td align="center" class="title" width=10%>操作</td>
<%
		String teaId = user.getId()+"|"+teachclass_id;
		int totalItems = smsManage.getSmsMessagesNum(null, null, null, null, null, 
						null, null, null, null, null,teaId);
		//----------分页开始---------------
		String spagesize = (String) session.getAttribute("pagesize");
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

   	       List smsList = smsManage.getSmsMessagesList(pageover,null, null, null, null, null, 
						null, null, null, null, null,teaId);
	       int ii = 0;
	       for(ii =0; ii<smsList.size();ii++){
	       	sms = (SmsMessage) smsList.get(ii);
	       	String status = sms.getStatus();
		             	String statusstr = "未审核";
		             	if(status.equals("1")) {
			             	
			             	statusstr = "已审核";		
			            }
			            if(status.equals("-1")) {
			            	
			            	statusstr = "已驳回";	
			            }
	       	 String targets = fixnull(sms.getTargets());
			 String[] targetsArr = targets.split(",");
			 if(targetsArr.length > 2)
			targets = targetsArr[0] + "," + targetsArr[1] + "...";
			String subContent = limit(sms.getContent(),20);
%>

			<tr>
				<td width='4%' class='select' align='center'>
				<!-- input type='checkbox' class='checkbox' name='listMultiAction' value='<%=sms.getMsgId()%>' id="<%=sms.getMsgId()%>" /--> 
              </td>
                <td align="center" class="td2"><a href="target_info.jsp?id=<%=sms.getMsgId() %>&subContent=<%=subContent%>&targets=<%=targets%>&statusstr=<%=statusstr %>&sender=<%=sms.getSender() %>&time=<%=sms.getTime()%>"><%=subContent%></a></td>
                <td align="center" class="td2"><a href="target_info.jsp?id=<%=sms.getMsgId() %>&subContent=<%=subContent%>&targets=<%=targets%>&statusstr=<%=statusstr %>&sender=<%=sms.getSender() %>&time=<%=sms.getTime()%>"><%=targets%></a></td>
                <td align="center" class="td2"><%=sms.getSender() %></td>
                <td align="center" class="td2"><%=sms.getTime()%></td>
                <td align="center" class="td2"><%=statusstr%></td>   
               <td align="center" class="td2"><a href="javascript:if(confirm('确认删除这条短信么？')) window.location='sms_delexe.jsp?id=<%=sms.getMsgId()%>'">删除</a></td>
            </tr>  
</form>         
<%       
			}  
%>
              <tr>
				<td colspan=11>
				      <%@ include file="../pub/dividepage2.jsp" %>          
				</td>
				
              </tr>
              
            </table></td>
            <tr>
            </tr> 
	  	

</table>
</body>
<%		
	}catch(Exception e){
		out.print(e.getMessage());
		return;
	}
%>
</html>
