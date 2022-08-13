<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.sms.*,com.whaty.platform.sms.basic.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../teacher/pub/priv.jsp"%>
<%!
   String fixnull(String str){
   		if(str==null || str.equals("null") || str.equals("")){
   			return "";
   		}
   		return str;
   }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>生殖健康咨询师培训网</title>
<link href="../css/css.css" rel="stylesheet" type="text/css">
</head>
<script>
	function strLength(str){

			var re,resultStr;

			re=new RegExp("[^\x00-\xff]","g");

			resultStr=str.replace(re,"aa");

			return resultStr.length;

	}
	
	function textCounter(field, maxlimit) {
		document.sms.remLen.value = strLength(field.value);
		
	}
	function sub(){
		if(document.sms.mobilePhone.value=="")
		{
			alert("请选择发送对象");
			return false;
		}
	}
</script>
<%
	String id = request.getParameter("id");
	String pageInt = request.getParameter("pageInt");
	
	SmsManagerPriv smsPriv = teacherOperationManage.getSmsManagerPriv();
	SmsManage smsManage = teacherOperationManage.getSmsManage(smsPriv);
	
	SmsMessage smsMessage = smsManage.getSmsMessage(id);
		String msgId = smsMessage.getMsgId();
		String targets = smsMessage.getTargets();
		String msgContent = smsMessage.getContent();
		String sender = smsMessage.getSender();
		String time = smsMessage.getTime();
		
		String siteId = smsMessage.getSiteId();

     	
     	String status = smsMessage.getStatus();
     	String checker = "";
     	String statusstr = "未审核";
     	if(status.equals("1")) {
         	statusstr = "已审核";		
        }
        if(status.equals("-1")) {
        	statusstr = "已驳回";	
        }
        String note = smsMessage.getNote();
 %>
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="target_send.jsp" method="POST" name="sms" onsubmit="return sub()">
<input type=hidden value="<%=targets%>" name="mobilePhone">
        <tr>
          <td valign="top" class="bg3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="45" valign="top" background="../images/top_01.gif"></td>
              </tr>
              <tr>
                <td align="center" valign="top">
                  <table width="765" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td height="65" background="../images/table_01.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td align="center" class="text1"><img src="../images/xb3.gif" width="17" height="15"> 
                              <strong>短信内容</strong></td>
							<td width="300">&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td background="../images/table_02.gif" ><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="bg4">
                          <tr>
                            <td class="text2">
                            	<table border="0" align="center" cellpadding="0" cellspacing="0" width=70%>
                            <tr>
							<td class="text1" valign=top>
								状态：<%=statusstr %>
							</td>
						  </tr>
						   <tr>
							<td class="text1" valign=top>
								时间：<%=time %>
							</td>
						  </tr>
						   <tr>
							<td class="text1" valign=top>
								创建者：<%=sender %>
							</td>
						  </tr>
                          <tr>
							<td class="text1" valign=top>
							内容：<textarea class="smallarea" cols="60" name="msgContent" rows="9" onPropertyChange="textCounter(sms.msgContent)"><%=msgContent %></textarea>
							<br>字节数:<input name=remLen value=<%=msgContent.getBytes().length %> readonly type=text size=4 maxlength=3 style="background-color: #eaffe0; border: 0; color: red">
							</td>
						  </tr>
						  <%	if(status.equals("-1")) { %>
						  <tr>
							<td class="text1" valign=top>
								驳回原因：<%=note %>
							</td>
						  </tr>
						  <%	} %>
						  
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><%	if(status.equals("0")) { %><input type=submit value="提交修改"><%} %>&nbsp;<input type=button value="返回" onclick="window.location='sms_list.jsp?pageInt=<%=pageInt %>'"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
</body>
</html>
