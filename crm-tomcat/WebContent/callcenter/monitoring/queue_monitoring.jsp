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
function getQueueInfo(){
	var queueInfo = j$(window.top.document).find('#queueInfo').val();
	if(queueInfo!=null&&queueInfo!=""){
		refreshPage(queueInfo);
	}	
}

function refreshPage(queueInfo){
	var queueInfoArrays;
	var trClass;
	if(queueInfo.indexOf("%")){
		queueInfoArrays = queueInfo.split('%'); 
	}else{
		queueInfoArrays = new Array(queueInfo);
	}
	
	for(i=0;i<queueInfoArrays.length;i++){
		queueInfoArray = queueInfoArrays[i].split(',');
		
		if(queueInfoArray[0]=='out'){//出队列，删除排队信息
		    if(j$('#'+queueInfoArray[2]).size()!=0){
		        j$('#'+queueInfoArray[2]).remove();
		    }
		}else{//入队列，添加排队信息
		    if(j$('#queue_info tr:last').attr('class')==null||j$('#queue_info tr:last').attr('class')=='tr1')
				trClass = 'tr0';
			else
				trClass = 'tr1';
			//如果不存在该用户的状态，新插入一行	
			var trinfo="<tr id="+queueInfoArray[2]+"><td>"+queueInfoArray[1]+"</td><td id='"+queueInfoArray[2]+"_group'>"+queueInfoArray[2]+"</td><td id='"+queueInfoArray[2]+"_ivrch'>"+queueInfoArray[3]+"</td></tr>";
			j$('#queue_info').append(trinfo);
			j$('#'+queueInfoArray[2]).attr('align','center');
			j$('#'+queueInfoArray[2]).attr('class',trClass);
		}
	}
	j$(window.top.document).find('#queueInfo').val('');
}

j$(function(){
	getQueueInfo();
	setInterval("getQueueInfo()",10000);
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
						<td align="center" >组号</td>
						<td align="center" >电话号码</td>
						<td align="center" >通道号</td>
					</tr>
					<tbody id="queue_info">
					</tbody>
				</table>
			</td>
		</tr>
	</table>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>