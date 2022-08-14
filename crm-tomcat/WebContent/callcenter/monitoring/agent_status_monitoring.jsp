<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script language="javascript" src="/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>

<script language="javascript">
var j$ = jQuery.noConflict();
function getAgentStatus(){
	var agentStatus = j$(window.top.document).find('#agent_status').val();
	if(agentStatus!=null&&agentStatus!=""){
		refreshPage(agentStatus);
	}	
}

function refreshPage(agentStatus){

	var statusArrays;
	var trClass;
	if(agentStatus.indexOf("%")){
		statusArrays = agentStatus.split('%'); 
	}else{
		statusArrays = new Array(agentStatus);
	}
	for(i=0;i<statusArrays.length-1;i++){
		//alert(statusArrays[i]);
		statusArray = statusArrays[i].split(',');
		if(j$('#'+statusArray[0]).size()!=0){
			//如果已经存在该用户的状态，更新状态即可
			j$('#'+statusArray[0]+'_status').html(statusArray[4]);
		} else{
			if(j$('#agent_status tr:last').attr('class')==null||j$('#agent_status tr:last').attr('class')=='tr1')
				trClass = 'tr0';
			else
				trClass = 'tr1';
			//如果不存在该用户的状态，新插入一行	
			var trinfo="<tr id="+statusArray[0]+"><td>"+statusArray[0]+"</td><td></td><td>"+statusArray[1]+"</td><td id='"+statusArray[0]+"_call'>"+statusArray[2]+"</td><td id='"+statusArray[0]+"_group'>"+statusArray[3]+"</td><td id='"+statusArray[0]+"_status'>"+statusArray[4]+"</td></tr>";
			j$('#agent_status').append(trinfo);
			j$('#'+statusArray[0]).attr('align','center');
			j$('#'+statusArray[0]).attr('class',trClass);
		}
		j$(window.top.document).find('#agent_status').val('');
	}
}

j$(function(){
	getAgentStatus();
	setInterval("getAgentStatus()",100);
});

</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform">
	<table cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
		<tr>
			<td>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td class="page-title">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>	
					</tr>
				</table>
				<br/>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" >工号</td>
						<td align="center" >用户名</td>
						<td align="center" >分机号</td>
						<td align="center" >主叫号</td>
						<td align="center" >组号</td>
						<td align="center" >坐席状态</td>
					</tr>
					<tbody id="agent_status">
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>