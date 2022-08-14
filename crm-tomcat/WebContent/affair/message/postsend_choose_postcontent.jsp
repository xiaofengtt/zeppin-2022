<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
String post_content = Utility.trimNull(request.getParameter("post_content"));
Map content_map = new HashMap();

String[] contents = post_content.split("\\|"); 
	for (int i=0; i<contents.length; i++) 
		content_map.put(contents[i], new Boolean(true));
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
/*批量反馈*/
function batchAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
		sl_alert("请选定邮寄内容！");
		return false;
	}
	else{
		var check_serial_no = document.getElementsByName("check_serial_no");
		var v = new Array(); 
		var j = 0; 
		for(var i=0;i<check_serial_no.length;i++){			
			if(check_serial_no[i].checked){
				v[j] = check_serial_no[i].value.replace(/(^\s*)|(\s*$)/g, "");
				j++; 			
			}
		}

		window.returnValue = v;
		window.close();
	}
}

function clearAction(){
	var v = new Array(); 
	
	window.returnValue = v;
	window.close();
}

</script>
</head>
<body class="body">
<form id="theform" name="theform" method="post">
	<input type="hidden" id="post_content" name="money_source" value="<%=post_content%>" />
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b>邮寄内容</b></font>
	</div>	
	<div align="right">
	     <!-- 选中 -->
         <button type="button" class="xpbutton2" id="btnChoose" onclick="javascript:batchAction();">选中</button>
		 &nbsp;&nbsp;&nbsp;&nbsp;
		 <!-- 清空 -->
         <button type="button" class="xpbutton2"  id="btnClear" onclick="javascript:clearAction();">清空</button>
		 &nbsp;&nbsp;&nbsp;&nbsp;
	</div>
	<hr noshade color="#808080" size="1" width="400px"">
	
	<div align="center">
		<table border="0" width="400px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trtagsort">
				 <td width="*" align="center"> 
				 	<input type="checkbox" name="btnCheckbox" class="selectAllBox" 
				 		onclick="javascript:selectAllBox(document.theform.check_serial_no,this);">
				 		邮寄内容
				 </td>
		     </tr>
		</table>
		<span id="tableList" style="overflow-y:auto;height:200;">
			<table border="0" width="400px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
				<tr class="tr0">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" value="1"
						<%=content_map.get("1")!=null?"checked=\"checked\"":""%>>成立公告												
					</td>
				</tr>			
				<tr class="tr0">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" value="2"
						<%=content_map.get("2")!=null?"checked=\"checked\"":""%>>确认单												
					</td>
				</tr>
				<tr class="tr0">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" value="3"
						<%=content_map.get("3")!=null?"checked=\"checked\"":""%>>管理报告												
					</td>
				</tr>
				<tr class="tr0">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" value="4"
						<%=content_map.get("4")!=null?"checked=\"checked\"":""%>>临时信息披露												
					</td>
				</tr>
				<tr class="tr0">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" value="5"
						<%=content_map.get("5")!=null?"checked=\"checked\"":""%>>终止公告												
					</td>
				</tr>
				<tr class="tr0">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" value="6"
						<%=content_map.get("6")!=null?"checked=\"checked\"":""%>>其他												
					</td>
				</tr>
			</table>
		</span>
	</div>
</form>
</body>
<%
}
catch(Exception e){
	throw e;
}
 %>