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
<form action="sms_send.jsp" method="POST" name="sms" onsubmit="return sub()">
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
								<input type="text" length="100" name="mobilePhone" class="selfScale" value="">
							</td>
						  </tr>
                          <tr>
							<td class="text1" valign=top>
							<textarea class="smallarea" cols="60" name="msgContent" rows="9" onPropertyChange="textCounter(sms.msgContent)"></textarea>
							<br>字节数:<input name=remLen value=0 readonly type=text size=4 maxlength=3 style="background-color: #eaffe0; border: 0; color: red">
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
