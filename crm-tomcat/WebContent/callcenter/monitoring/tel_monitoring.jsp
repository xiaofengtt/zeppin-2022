<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//设置呼叫中心环境
String callcenter_mode = "";
if(user_id.intValue() == 15){
	callcenter_mode = "_jianxin";
}
 %>
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
<script type="text/javascript" src="/includes/callcenter/callcenter<%=callcenter_mode%>.js"></script>
<script language="vbscript" src="/includes/callcenter/callcenter.vbs"></script>
<script language="javascript">
var j$ = jQuery.noConflict();
function getAgentStatus(){
	var communicationInfo = j$(window.top.document).find('#communication_info').val();
	if(communicationInfo!=null&&communicationInfo!=""){
		refreshPage(communicationInfo);
	}	
}

function refreshPage(communicationInfo){
	var communicationInfoArrays;
	var trClass;
	if(communicationInfo.indexOf("%")){
		communicationInfoArrays = communicationInfo.split('%'); 
	}else{
		communicationInfoArrays = new Array(communicationInfo);
	}
	
	for(i=0;i<communicationInfoArrays.length;i++){
		//alert(statusArrays[i]);
		communicationInfoArray = communicationInfoArrays[i].split(',');
		
		if(j$('#'+communicationInfoArray[0]).size()!=0){
		    if(communicationInfoArray[4]=="空闲"){
		        j$('#'+communicationInfoArray[0]).remove();
		    }else{
		        j$('#'+communicationInfoArray[0]+'_status').html(communicationInfoArray[4]);    
		    }
		}else{
		    if(communicationInfoArray[4]=="空闲"){
		        return false;
		    }else{
		        if(j$('#communication_info tr:last').attr('class')==null||j$('#communication_info tr:last').attr('class')=='tr1')
				    trClass = 'tr0';
    			else
    				trClass = 'tr1';
    			//如果不存在该用户的状态，新插入一行	
    			var trinfo="<tr id="+communicationInfoArray[0]+"><td>"+communicationInfoArray[0]+"</td><td></td><td>"+communicationInfoArray[1]+"</td><td id='"+communicationInfoArray[0]+"_call'>"+communicationInfoArray[2]+"</td><td id='"+communicationInfoArray[0]+"_group'>"+communicationInfoArray[3]+"</td><td id='"+communicationInfoArray[0]+"_status'>"+communicationInfoArray[4]+"</td><td><a id='"+communicationInfoArray[0]+"_listen' href='#'><img border='0'src='/images/msg.gif' align='absmiddle' width='16' height='16'></a></td><td><a id='"+communicationInfoArray[0]+"_plug' href='#'><img border='0'src='/images/switch.gif' align='absmiddle' width='16' height='16'></a></td></tr>";
    			j$('#communication_info').append(trinfo);
    			j$('#'+communicationInfoArray[0]).attr('align','center');
    			j$('#'+communicationInfoArray[0]).attr('class',trClass);
    			j$('#'+communicationInfoArray[0]+'_listen').click(function(){telListen(statusArray[2]);});
    			j$('#'+communicationInfoArray[0]+'_plug').click(function(){telPlug(statusArray[2]);});
		    }
		}
	}
	    j$(window.top.document).find('#communication_info').val('"<%=input_operatorCode %>","<%=input_operatorCode %>",87703600,123,空闲');
}

j$(function(){
    initMyPhone();
	getAgentStatus();
	setInterval("getAgentStatus()",10000);
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
						<td align="center" >监听</td>
						<td align="center" >强插</td>
					</tr>
					<tbody id="communication_info">
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>