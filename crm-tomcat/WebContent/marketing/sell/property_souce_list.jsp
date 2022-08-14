<%@ page contentType="text/html; charset=GBK"  import="java.util.*,enfo.crm.tools.*" %>
<%@ include file="/includes/operator.inc" %>
<%
try{
Map map = null;
List list = Argument.getDictParamListIntrust(1167, "");
String item [] = Utility.splitString(Utility.trimNull(request.getParameter("property_souce_str")), "^");
Integer check_flag = Utility.parseInt(Utility.trimNull(request.getParameter("check_flag")), new Integer(0));

String property_souce = "";
String other_explain = "";
List exists_list = new ArrayList();

if(item.length > 0)
	property_souce = item[0];
if(item.length > 1)
	other_explain = item[1];

if(!"".equals(property_souce))
	exists_list = Arrays.asList(Utility.splitString(property_souce, "|"));
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>信托财产来源</title>
<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<%if(language.equals("en")){ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_en.js"></SCRIPT>
<%}else{ %>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<%} %>
<script language="JavaScript">
function checkPropertySource(){
	var property_souce = document.getElementsByName("property_souce");
	for(var i=0; i<property_souce.length; i++){
		if(property_souce[i].checked && property_souce[i].value == "116716")
			document.getElementById("other_explan_tr").style.display = "";
		else
			document.getElementById("other_explan_tr").style.display = "none";
	}
}

function save(){
	var property_souce_str = "";
	var property_souce = document.getElementsByName("property_souce");
	for(var i=0; i<property_souce.length; i++){
		if(property_souce[i].checked){
			property_souce_str = property_souce_str + "|" + property_souce[i].value;
			if(property_souce[i].value == "116716"){
				if(!sl_check(document.theform.other_explan, "详细说明"))	return false;
				property_souce_str = property_souce_str + "^" +  document.theform.other_explan.value;
			}
		}
	}

	if(property_souce_str != "" && property_souce_str.length > 1)
		property_souce_str = property_souce_str.substring(1, property_souce_str.length);
	
	window.returnValue = property_souce_str;
	window.close();
}
</script>
</head>
<base target="_self">
<body class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);">
<table border="0" width="100%" cellspacing="0" cellpadding="2">
	<tr>
		<td colspan="2" background="<%=request.getContextPath()%>/images/headerbg.gif"><b><font color="#FFFFFF">信托财产来源</font></b></td>
	</tr>
	<%
	if(list != null && list.size() != 0){
		for(int i=0; i<list.size(); i++){
			map = (Map)list.get(i);
	%>
	<tr>
		<td colspan="2"><input type="checkbox" class="flatcheckbox" name="property_souce" onclick="javascript:checkPropertySource();" onkeydown="javascript:nextKeyPress(this)" <%=exists_list.contains(Utility.trimNull(map.get("TYPE_VALUE"))) ? "checked" : ""%> <%=check_flag.intValue() == 2 ? "disabled='disabled''" : ""%> value="<%=Utility.trimNull(map.get("TYPE_VALUE"))%>"><%=Utility.trimNull(map.get("TYPE_CONTENT"))%></td>
	</tr>
	<%
		}
	}
	%>
	<tr id="other_explan_tr" style="display: <%=exists_list.contains("116716") ? "block" : "none"%>;">
		<td align="right" valign="top">详细说明:</td>
		<td>
			<textarea rows="5" id="other_explan" name="other_explan" cols="55" <%=check_flag.intValue() == 2 ? "readonly class='edline_textarea'" : ""%>><%=other_explain %></textarea>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<table border="0" width="100%">
			<tr>
				<td><font color="red">说明：信托财产来源可多选</font></td>
				<td align="right">
				<%if(check_flag.intValue() != 2){%>
				<button type="button"  class="xpbutton3" accessKey=s  name="btnSave" onclick="javascript:save();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button><!-- 保存 -->
				&nbsp;&nbsp;
				<%}%>
				<!-- 取消 -->
                <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue='';window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</form>
</body>
</html>
<%
}catch(Exception e){
	throw e;
}%>
