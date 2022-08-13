<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.*,com.whaty.platform.util.*,com.whaty.platform.util.log.*"%>
<%@ page import="com.whaty.platform.entity.activity.*,com.whaty.platform.entity.activity.score.*"%>
<%@ page import="com.whaty.platform.entity.*,com.whaty.platform.entity.basic.*,com.whaty.platform.entity.config.*"%>
<%@ page import="com.whaty.platform.entity.recruit.*,com.whaty.platform.entity.setup.*"%>
<%@ page import="com.whaty.platform.entity.user.*,com.whaty.platform.sms.*,com.whaty.platform.sms.basic.*"%>
<%@ include file="../pub/priv.jsp"%>
<%@ include file="../../teacher/pub/priv.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>

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
<body leftmargin="0" topmargin="0"  background="../images/bg2.gif">
<table width="100%" border="0" cellpadding="0" cellspacing="0">
<form action="target_send.jsp" method="POST" name="sms" onsubmit="return sub()">
	<%
		String subContent = request.getParameter("subContent");
		String id = request.getParameter("id");
		String targets = request.getParameter("targets");
		String statusstr = request.getParameter("statusstr");
		String time = request.getParameter("time");
		String sender = request.getParameter("sender");
	 %>
	 <input type=hidden value="<%=id%>" name="id">
	 <input type=hidden value="<%=statusstr%>" name="statusstr">
	 <input type=hidden value="<%=subContent%>" name="subContent">
	 <input type=hidden value="<%=time%>" name="time">
	 <input type=hidden value="<%=sender%>" name="sender">
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
                              <strong>发送短信</strong></td>
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
								<a href="select_student.jsp" target=_blank>[选择发送对象]</a>
							</td>
							
						  </tr>
						  <tr>
						  	<td>
								<input type="text" length="100" name="mobilePhone" class="selfScale" value="<%= targets%>">
							</td>
						  </tr>
                          <tr>
							<td class="text1" valign=top>
							<textarea class="smallarea" cols="60" name="msgContent" rows="9" onPropertyChange="textCounter(sms.msgContent)"><%=subContent %></textarea>
							<br>字节数:<input name=remLen value=<%=subContent.getBytes().length %> readonly type=text size=4 maxlength=3 style="background-color: #eaffe0; border: 0; color: red">
							</td>
						  </tr>
                            	</table>
							</td>
							</tr>
							
     </table></td>
    </tr>
    
        <tr>
		 <td><img src="../images/table_03.gif" width="765" height="21"></td>
                    </tr>
		<tr>
			<td align=center><input type=submit value="提交">&nbsp;<input type=button value="返回" onclick="window.location='sms_list.jsp'"></td>
		</tr>
                  </table> </td>
              </tr>
            </table></td>
        </tr>
      </table>
      </form>
</body>
</html>
