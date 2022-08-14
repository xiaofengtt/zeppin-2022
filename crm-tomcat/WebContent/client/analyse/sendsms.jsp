<%@ page language="java" pageEncoding="GBK" import="java.util.*,enfo.crm.web.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
	String target_sms = "";
	String target_cust_id = "";

	String[] cust_id_item = request.getParameterValues("cust_id");

	if(cust_id_item != null){   
		for(int i = 0;i < cust_id_item.length; i++){  	

			String[] cust_ids = Utility.splitString(cust_id_item[i], ",");	
			for(int j=0; j < cust_ids.length; j++){
				String temp_modile = Utility.trimNull(Argument.getCustomerModile(Utility.parseInt(cust_ids[j], new Integer(0)),input_operatorCode));
				if(!"".equals(temp_modile)){
					target_sms += "," + temp_modile; 
					target_cust_id += "," + cust_ids[j];
				}	
			}
		}
		if(target_sms.length() > 0){
			target_sms = target_sms.substring(1);
			target_cust_id = target_cust_id.substring(1);
		}
	}
		
%>


<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<title><%=LocalUtilis.language("menu.sendMail2",clientLocale)%> </title><!--撰写邮件-->
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/ccService.js'></script>	
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>

<script language=javascript>
	var okFlag = false;
	var arr;
	DWREngine.setAsync(false); //同步

	function sendAction(){
		disableAllBtn(true); //

		var cust_ids = document.getElementById("to_sms_cust_id").value.split(',');
		var cust_modiles = document.getElementById("to_sms").value.split(',');
		var content = document.getElementById("content").value;
		
		if(cust_ids.length >0 && cust_modiles.length > 0){
			var i;
			for(i=0; i < cust_ids.length; i++){				
				ccService.sendSmsWithSigln(cust_modiles[i],content,"-2","<%=input_operatorCode%>",
					{callback: function(data){
									arr = data.split("|");
									var retVal = parseInt(arr[0],10);
									okFlag = (retVal==1 || retVal==3);
								}
					});
				if (! okFlag) {
					alert("提交发向"+cust_modiles[i]+"的短信时发生错误。"+arr[1]);
					break;
				}				
				
			}
			if (i==cust_ids.length) {
				if (i==1) {
					alert(arr[1]);
				} else {
					alert("短信已全部成功提交！");
				}
				onClose();
			}
		}
		disableAllBtn(false);
	}
	
	function onClose(){   
		window.returnValue = false;
		window.close();		
	}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" MARGINWIDTH="0" MARGINHEIGHT="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="sendmail.jsp" onsubmit="javascript:return validateForm(this);" >

<input type="hidden" name="to_sms_cust_id" value="<%=target_cust_id %>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<!--撰写邮件-->
                                <td><img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=LocalUtilis.language("message.sendSMS",clientLocale)%> </b></font></td>
							</tr>
							<tr>
								<td>
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3">
							<tr>
								<td width="10%">
								<p align="right"><%=LocalUtilis.language("class.custMobile",clientLocale)%> </p><!--手机-->
								</td>
								<!--多收件人以逗号分开-->
                                <td width="90%"><input id="to_sms" name="to_sms" size="100" onkeydown="javascript:nextKeyPress(this)" value="<%=target_sms%>" readonly="readonly" class="edline">&nbsp;(<%=LocalUtilis.language("message.multRecipients",clientLocale)%> )</td>
							</tr>
							<tr>
								<td align="right" width="10%"><%=LocalUtilis.language("class.smsContent",clientLocale)%> :</td><!--内容-->
								<td width="90%"><textarea rows="20" id="content" name="content" cols="120"><%//=content%></textarea></td>
							</tr>
						</table>
						<br>
						<table border="0" width="100%">
							<tr>
								<td align="right">	
														
								<!--发送-->
                                <button type="button"  class="xpbutton3" accessKey=s onclick="javascript:sendAction();"><%=LocalUtilis.language("message.send",clientLocale)%> (<u>S</u>)</button>
								&nbsp;&nbsp;
								<!--取消-->
                                <button type="button"  class="xpbutton3" accessKey=c onclick="javascript:onClose();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>