<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
try{
String money_source = Utility.trimNull(request.getParameter("money_source"));
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--多选资金来源-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language="javascript">
/*批量反馈*/
function batchAction(){
	if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
        //请选定资金来源
		sl_alert("请选定资金来源！");
		return false;
	}
	else{
		var check_serial_no = document.getElementsByName("check_serial_no");
		var checkedValues_value = "";
		var checkedValues_content = "";
		
		for(var i=0;i<check_serial_no.length;i++){			
			if(check_serial_no[i].checked){
				var check_value = check_serial_no[i].value;
				check_value_array = check_value.split("_");
				checkedValues_value = checkedValues_value + check_value_array[0] + "|";
				
				if(check_value_array[0]=="201299"){
					if(!sl_check(document.getElementById("othersContent"), "其他",30,1)){
						return false;
					}
					else{
						checkedValues_content = checkedValues_content + document.getElementById("othersContent").value  + ",";
					}
				}
				else{
					checkedValues_content = checkedValues_content + check_value_array[1] + ",";
				}				
			}
		}
		
		checkedValues_value = checkedValues_value.substring(0,checkedValues_value.length-1);
		checkedValues_content = checkedValues_content.substring(0,checkedValues_content.length-1);
		
		var v = new Array(); 
		
		v[0] = checkedValues_value;
		v[1] = checkedValues_content;
		
		window.returnValue = v;
		window.close();
	}
}

function clearAction(){
	var v = new Array(); 
	
	v[0] = "";
	v[1] = "";
	
	window.returnValue = v;
	window.close();
}

window.onload = function(){
	var invest_type = document.getElementById("money_source").value;
	var checkedValues_array = invest_type.split("|");	
	var check_serial_no = document.getElementsByName("check_serial_no");
	
	for(var i=0;i<check_serial_no.length;i++){
		for(var j=0;j<checkedValues_array.length;j++){
			var check_serial_no_array = check_serial_no[i].value.split("_");				
			if(check_serial_no_array[0]==checkedValues_array[j]){
				document.getElementsByName("check_serial_no")[i].checked = true;
			}
		}
	} 
}
</script>
</head>
<body class="body body-nox">
<form id="theform" name="theform" method="post">
	<input type="hidden" id="money_source" name="money_source" value="<%=money_source%>" />
	<div align="left" class="page-title">
		<font color="#215dc6"><b>资金来源</b></font><!--资金来源-->
	</div>	
	<div align="right" class="btn-wrapper">
	     <!-- 选中 -->
         <button type="button"  class="xpbutton2" id="btnChoose" onclick="javascript:batchAction();">选中</button>
		 &nbsp;&nbsp;&nbsp;&nbsp;
		 <!-- 清空 -->
         <button type="button"  class="xpbutton2"  id="btnClear" onclick="javascript:clearAction();">清空</button>
	</div>
	<br/>
	<div align="center">
		<table border="0" width="400px" cellspacing="1" cellpadding="2"	class="tablelinecolor">
			<tr class="trtagsort">
				 <td width="*" align="center"> 
				 	<input type="checkbox" name="btnCheckbox" class="selectAllBox" 
				 		onclick="javascript:selectAllBox(document.theform.check_serial_no,this);">
				 		资金来源<!--资金来源-->
				 </td>
		     </tr>
		</table>
		<span id="tableList" style="overflow-y:auto;height:200;">
			<table border="0" width="400px" cellspacing="1" cellpadding="2"	class="tablelinecolor">	
				<%
					//页面用辅助变量
					int iCount = 0;
					java.sql.Connection conn = CrmDBManager.getConnection();
					java.sql.CallableStatement stmt =
						conn.prepareCall(
							"{call SP_QUERY_TDICTPARAM(?)}",
							java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE,
							java.sql.ResultSet.CONCUR_READ_ONLY);
					stmt.setInt(1, 2012);
					java.sql.ResultSet rs = stmt.executeQuery();
					try {
						while (rs.next()){
							iCount++;
				%>
				<tr class="tr<%=iCount%2%>">
					<td width="*" align="center">
						<input class="selectAllBox" type="checkbox" name="check_serial_no" onclick = "javascript:"
							 value="<%= Utility.trimNull(rs.getString("TYPE_VALUE"))%>_<%= Utility.trimNull(rs.getString("TYPE_CONTENT"))%>" class="flatcheckbox"/>
						<%= Utility.trimNull(rs.getString("TYPE_CONTENT"))%>	
						<%if("201299".equals(Utility.trimNull(rs.getString("TYPE_VALUE")))){ %>	
							<input type="text" value="" id="othersContent" style="width:120px"/>
						<%}%>	
					</td><!--资金来源-->
				</tr>			
				<%		
						}
					} 
					finally {
						rs.close();
						stmt.close();
						conn.close();
					}
				%>
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