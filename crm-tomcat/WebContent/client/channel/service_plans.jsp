<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//取得页面查询参数
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")), new Integer(0));
Integer channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")), new Integer(0));
String plan_content = Utility.trimNull(request.getParameter("plan_content"));
Integer plan_man = Utility.parseInt(Utility.trimNull(request.getParameter("plan_man")), new Integer(0));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&serial_no="+serial_no;
tempUrl = tempUrl+"&channel_id="+channel_id;
tempUrl = tempUrl+"&plan_content="+plan_content;
tempUrl = tempUrl+"&plan_man="+plan_man;
sUrl = sUrl + tempUrl;
//辅助变量
input_bookCode = new Integer(1);
//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//获得对象
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();

vo.setSerial_no(serial_no);
vo.setChannel_id(channel_id);
vo.setPlan_contnet(plan_content);
vo.setPlan_man(plan_man);
vo.setInput_man(input_operatorCode);

IPageList pageList = local.queryServicePlanPage(vo,t_sPage,t_sPagesize);
//分页辅助参数
List list = pageList.getRsList();
int iCount = 0;
Map map = null;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title></title><!--代销渠道管理-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}
function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}

/*查询功能*/
function QueryAction(){		
	var _pagesize = document.getElementsByName("pagesize")[0];		
	//url 组合
	var url = "service_plans.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&serial_no=" + document.getElementById("serial_no").getAttribute("value");
	var url = url + "&channel_id=" + document.getElementById("channel_id").getAttribute("value");
	var url = url + "&plan_content=" + document.getElementById("plan_content").getAttribute("value");
	var url = url + "&plan_man=" + document.getElementById("plan_man").getAttribute("value");

	window.location = url;
}
//修改渠道设置
function modiAction(serial_no){
	var url = "service_plan_action.jsp?serial_no="+serial_no;
	var ret = showModalDialog(url,'', 'dialogWidth:700px;dialogHeight:400px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
//新增渠道设置
function appendAction(serial_no){

	var url = "service_plan_action.jsp";
	var ret = showModalDialog(url,'', 'dialogWidth:700px;dialogHeight:400px;status:0;help:0');
	
	if(ret==1){
		window.location.reload();
	}
}
//删除渠道
function delAction(){
	if(checkedCount(document.getElementsByName("q_serial_no")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "service_plan_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;
}
function CancelAction(){
	location = 'channel_list.jsp'
}
/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "activeTr"+serial_no;
	var v_table = "activeTablePro"+serial_no;
	var v_flag = "activeFlag_display"+serial_no;
	var v_div = "activeDiv"+serial_no;
	var v_image = "activeImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}
</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<div id="queryCondition" class="qcMain" style="display:none;width:470px;height:125px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
			<td  align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%>: </td><!-- 渠道名称 -->
			<td  align="left">
				<select size="1" id="channel_id" name="channel_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getChannelName(channel_id)%>
				</select>
			</td>	
			
			<td  align="right">计划维护人: </td><!--计划维护人-->
			<td  align="left">
				<select size="1" id="plan_man" name="plan_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 125px">
					<%=Argument.getManager_Value(plan_man)%>
				</select>
			</td>		
		</tr>
		<tr>
			<td  align="right">计划维护内容: </td><!-- 计划维护内容 -->
			<td width="*" colspan=3>
				<input type="text" name="plan_content" id="plan_content" value="<%= plan_content%>"onkeydown="javascript:nextKeyPress(this)" style="width:320px">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
				<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>
	</table>
</div>

<div>
	<div align="left"  class="page-title ">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right" class="btn-wrapper">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
		<%//if (input_operator.hasFunc(menu_id, 102)) {%>
		<button type="button"  class="xpbutton5" accessKey=u id="appendButton" name="appendButton" title="<%=LocalUtilis.language("message.append",clientLocale)%>" onclick="javascript:appendAction();">
		   <%=LocalUtilis.language("message.append",clientLocale)%><%=LocalUtilis.language("message.maintenance",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%>(<u>U</u>)</button><!--新增维护信息-->
		&nbsp;&nbsp;&nbsp;<%//}%>
		<button type="button"  class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%>" onclick="javascript:delAction();"><%=LocalUtilis.language("message.delete",clientLocale)%>(<u>D</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--删除-->
	</div>
</div>
<br/>
<div align="center">
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="15px">
				<input type="checkbox" name="btnCheckbox" class="selectAllBox"	
					   onclick="javascript:selectAllBox(document.theform.q_serial_no,this);" />
			</td>
			<td align="center" width="*"><%=LocalUtilis.language("class.partnNo",clientLocale)%></td><!-- 渠道编号 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnName",clientLocale)%></td><!-- 渠道名称 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.planMaintain",clientLocale)%><%=LocalUtilis.language("class.time",clientLocale)%></td><!-- 计划维护时间 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.planMaintain",clientLocale)%><%=LocalUtilis.language("class.content2",clientLocale)%></td><!-- 计划维护内容 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.planMaintain",clientLocale)%><%=LocalUtilis.language("class.persons",clientLocale)%></td><!-- 计划维护人员 -->
			<td align="center" width="80"><%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("message.info",clientLocale)%></td><!-- 实际维护信息-->
			<td width="40px" align="center"><%=LocalUtilis.language("message.edit",clientLocale)%></td><!--编辑--> 
		</tr>
		<%
			Iterator iterator = list.iterator();
			String channel_code = "";
			String channel_name = "";
			String plan_time = "";
			String service_time = "";
			String service_content = "";
			Integer service_man = new Integer(0);
			
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
				serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
				channel_id = Utility.parseInt(Utility.trimNull(map.get("CHANNEL_ID")),new Integer(0));
				channel_code =Utility.trimNull(map.get("CHANNEL_CODE"));
				channel_name =Utility.trimNull(map.get("CHANNEL_NAME"));
				plan_time = Utility.trimNull(map.get("PLAN_TIME"));
				plan_content =Utility.trimNull(map.get("PLAN_CONTENT"));
				plan_man = Utility.parseInt(Utility.trimNull(map.get("PLAN_MAN")),new Integer(0));
				service_time = Utility.trimNull(map.get("SERVICE_TIME"));
				service_content =Utility.trimNull(map.get("SERVICE_CONTENT"));
				service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
				
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="15px"><input type="checkbox" name="q_serial_no" value="<%=serial_no%>" class="flatcheckbox"/> </td>
			<td align="center" width="*"><%=channel_code%></td>
			<td align="center" width="*"><%=channel_name%></td>
			<td align="center" width="*"><%=plan_time%></td>
			<td align="left" width="*">&nbsp;&nbsp;<%=plan_content%></td>
			<td align="left" width="*">&nbsp;&nbsp;<%=Argument.getManager_Name(plan_man)%></td>
			<td align="center" width="80px">
	         	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);">
	         		<IMG id="activeImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
	         	</button>
	         	<input type="hidden" id="activeFlag_display<%=serial_no%>" name="activeFlag_display<%=serial_no%>" value="0">
         	</td> 
			<td align="center" width="40px">
				<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction(<%=serial_no%>);">
			</td>
		</tr>
		<tr id="activeTr<%=serial_no%>" style="display: none">
			<td align="center" bgcolor="#FFFFFF" colspan="17" >
				<div id="activeDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
					<table id="activeTablePro<%=serial_no%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">
						<tr style="background:F7F7F7;" >
							<td align="left" width="100px"><%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("class.time",clientLocale)%> ：</td><!--实际维护时间-->
							<td  width="*"><%=service_time %></td>
						</tr>
						<tr style="background:F7F7F7;" >
							<td align="left" width="100px"><%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("class.content2",clientLocale)%>：</td><!--实际维护内容-->
							<td  width="*"><%=service_content%></td>
						</tr>
						<tr style="background:F7F7F7;" >
							<td align="left" width="100px"><%=LocalUtilis.language("class.practicalMaintain",clientLocale)%><%=LocalUtilis.language("class.persons",clientLocale)%>：</td><!--实际维护人员-->
							<td  width="*"><%=Argument.getManager_Name(service_man) %></td>
						</tr>
					</table>
				</div>
			</td>
		</tr>  
		<%
		}%>
		<%for(int i=0;i<(t_sPagesize-iCount);i++){%>       	
	         <tr class="tr<%=(i % 2)%>">
	            <td align="center" width="15px">&nbsp;</td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>      
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>        
	            <td align="center" width="60px"></td>              
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="8" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</TABLE>
</div>
<br>
<div >
	&nbsp;&nbsp;<%=pageList.getPageLink(sUrl)%>
</div>
<div align="right" class="page-link">
	<!--返回-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<%
local.remove();
%>
