<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//取得页面查询参数
Integer actionFlag = Utility.parseInt(Utility.trimNull(request.getParameter("actionFlag")),new Integer(0));//动作标记：1.发送;2.查询发送结果
Integer begin_plan_date = Utility.parseInt(Utility.trimNull(request.getParameter("begin_plan_date")),null);
Integer end_plan_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_plan_date")),null);
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
Integer way_type = Utility.parseInt(Utility.trimNull(request.getParameter("way_type")),new Integer(1));
String mobiles = Utility.trimNull(request.getParameter("mobiles"));
String sms_content = Utility.trimNull(request.getParameter("sms_content"));
//url设置
String tempUrl = "";
sUrl = sUrl + tempUrl;

//页面用辅助变量
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];


//获得对象
SmsRecordLocal smsRecordLocal = EJBFactory.getSmsRecord();
SendSMSVO vo_sms = null;

vo_sms = new SendSMSVO();
vo_sms.setBegin_plan_time(begin_plan_date);
vo_sms.setEnd_plan_time(end_plan_date);
vo_sms.setWay_type(way_type);
vo_sms.setMobiles(mobiles);
vo_sms.setCust_name(cust_name);
vo_sms.setContent_templet(sms_content);
vo_sms.setStatus(new Integer(0));
vo_sms.setCheck_flag(new Integer(1));
vo_sms.setCheck_man(input_operatorCode);
IPageList pageList = smsRecordLocal.queryMessageList(vo_sms,totalColumn,t_sPage,t_sPagesize);

//分页辅助参数
List list = pageList.getRsList();
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
<title><%=LocalUtilis.language("menu.querySMS",clientLocale)%> </title><!--短信信息查询-->

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-ui-1.7.2.custom.min.js"></script>
<script language=javascript>
	var j$ = jQuery.noConflict();
</script>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
	initQueryCondition();
	<%}%>
}

 
/*查询方法*/
function QueryAction(){	
	var url = "send_info_check.jsp?page=<%=sPage%>&pagesize="+ document.theform.pagesize.value;	
	var url = url + "&begin_plan_date=" + document.getElementById("begin_plan_date").value
				  + "&end_plan_date=" + document.getElementById("end_plan_date").value
				  + "&cust_name=" + document.getElementById("cust_name").value
 				  + "&mobiles=" + document.getElementById("mobiles").value
				  + "&sms_content=" + document.getElementById("sms_content").value
				  + "&way_type=" + document.getElementById("way_type").value;
	window.location.href = url;
}

/*分页*/
function refreshPage()
{
	 QueryAction();
}

function checkAction(flag){
	if(checkedCount(document.getElementsByName('serial_no'))== 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordToUpdate",clientLocale)%> ！");//请选定要更新的记录
		return false;
	}
	if(sl_confirm("发送所选的短信")){
		document.getElementsByName('theform')[0].action="send_info_check_action.jsp";
		document.getElementById('actionFlag').value = flag;
		document.getElementsByName('theform')[0].submit();
	}
}
function viewAction(serial_no){				
	var url = "<%=request.getContextPath()%>/affair/message/send_tolist_info.jsp?serial_no=" + serial_no;		
	dialog("","iframe:"+url,"850px","400px","show","<%=request.getContextPath()%>"); 
	showWaitting(0);
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post">
<input type="hidden" name="actionFlag" id="actionFlag" value="<%= actionFlag%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:510px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--开始日期-->
			<td align="left">
				<input type="text" name="begin_plan_date_picker" class=selecttext <%if(begin_plan_date==null){%> value=""<%}else{%> value="<%=Format.formatDateLine(begin_plan_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.begin_plan_date_picker,theform.begin_plan_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="begin_plan_date" id="begin_plan_date" />	
			</td>	
			<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--结束日期-->
			<td align="left">
				<input type="text" name="end_plan_date_picker" class=selecttext <%if(end_plan_date==null){%> value=""<%}else{%> value="<%=Format.formatDateLine(end_plan_date)%>"<%}%>>
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.end_plan_date_picker,theform.end_plan_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="end_plan_date" id="end_plan_date" />	
			</td>
		</tr>
		<tr>
			<td align="right">短信邮件: </td><!-- 客户名称 -->
			<td align="left" >
				<select name="way_type" id="way_type" style="width:60px;">
					<option value="1" <%if(way_type.intValue() ==1){%> selected <%}%> >短信</option>
					<option value="2" <%if(way_type.intValue() ==2){%> selected <%}%> >邮件</option>
				</select>
			</td> 
			
			<td align="right">接收号码 :  </td>
			<td align="left" >
				<input type="text" id="mobiles" name="mobiles" value="<%=mobiles%>"/>
			</td>					
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> : </td><!-- 客户名称 -->
			<td align="left" >
				<input type="text" id="cust_name" name="cust_name" value="<%=cust_name %>"/>
			</td>				
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.content2",clientLocale)%> : </td><!--信息内容-->
			<td align="left" colspan="3">
				<input TYPE="text" name="sms_content" id="sms_content" size="40" value="<%=sms_content%>"/>	
			</td>	
		</tr>
		<tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button><!--确定-->
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>		
	</table>
</div>


<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>		
	<%if(Argument.getSyscontrolValue("DT_SMS")==1){%>
	<div align="right" class="btn-wrapper">
		<!--查询-->
        <button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
        <!--审核-->
		<button type="button" class="xpbutton3" title="<%=LocalUtilis.language("message.pass",clientLocale)%>" onclick="javascript:checkAction(1);"><%=LocalUtilis.language("message.pass",clientLocale)%></button>
		&nbsp;&nbsp;&nbsp;
		<button type="button" class="xpbutton3" title="<%=LocalUtilis.language("message.notPass2",clientLocale)%>" onclick="javascript:checkAction(2);"><%=LocalUtilis.language("message.notPass2",clientLocale)%></button>
		<%}%>
		
	</div>
	<%}%>
	<br/>
</div>

<div>
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			<td width="7%" align="center">
				 <input type="checkbox" name="checkBox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);" /><%=LocalUtilis.language("class.serialNumber",clientLocale)%> <!--序号-->
			</td>	
			<td align="center">短信/邮件 </td>
	        <td align="center">发送方式</td>
			<td align="center">信息类型</td>
	        <td width="10%" align="center">发送时间</td>
			<td width="*" align="center">发送内容</td>
	        <td width="10%" align="center">发送结果 </td>
			<td align="center">审核状态</td>
			<td align="center"><%=LocalUtilis.language("class.reviewer",clientLocale)%> </td> <!--审核人-->
			<td align="center">详细信息</td>
	     </tr>
<% 

Iterator iterator = list.iterator();
while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();
	
	Integer serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
	Integer way_type2 = Utility.parseInt(Utility.trimNull(map.get("WAY_TYPE")),new Integer(0));	
	Integer send_type = Utility.parseInt(Utility.trimNull(map.get("SEND_TYPE")),new Integer(0));
	String msg_type_name =  Utility.trimNull(map.get("MSG_TYPE_NAME"));	
	Integer check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),new Integer(0));
	Integer check_man = Utility.parseInt(Utility.trimNull(map.get("CHECK_MAN")),new Integer(0));
	String plam_time = Utility.trimNull(map.get("PLAN_TIME"));
	if(plam_time.length()>0){
		plam_time = plam_time.substring(0,16);
	}
%>
	<tr class="tr<%=iCount%2%>">
		<td align="center">				
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%">
						<input type="checkbox" name="serial_no" value="<%= serial_no%>$<%= way_type2%>" class="flatcheckbox"/>&nbsp;&nbsp;
					</td>
					<td width="90%" align="center"><%= serial_no%>&nbsp;&nbsp;</td>
				</tr>
			</table>
		</td>
		<td align="center"><%= way_type2.intValue()==2?"邮件":"短信"%></td> 
		<td align="center"><%= send_type.intValue()==2?"定时发送":"即时发送"%></td>
		<td align="center"><%= msg_type_name%></td>
		<td align="center"><%= plam_time%></td> 
		<td align="center"><%= Utility.trimNull(map.get("CONTENT_TEMPLET"))%></td> 
		<td align="center"><%= Utility.trimNull(map.get("SEND_RESULT"))%></td> 
		<td align="center"><%if(check_flag.intValue() ==1)out.print("待审核");else if(check_flag.intValue() ==2) out.print("已审核");else out.print("");%></td> 
		<td align="center"><%= Argument.getOpNameByOpCode(Utility.parseInt(Utility.trimNull(map.get("CHECK_MAN")),new Integer(0)))%></td> 
		<td align="center" style="width:10px">       	
			<button type="button" class="xpbutton2" name="btnEdit" onclick="javascript:viewAction(<%=serial_no%>);">&gt;&gt;</button>	
     	</td>
	</tr>
<%}%>

		<%for(int i=0;i<(8-iCount);i++){%>  
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>       
			<td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>   
			<td align="center">&nbsp;</td>     
			<td align="center">&nbsp;</td>  
			<td align="center">&nbsp;</td> 
         </tr>           
		<%}%>           
		<tr class="tr1">
            <!--合计--><!--项-->
			<td align="center" class="tdh"><b><%=LocalUtilis.language("message.total",clientLocale)%> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
		</tr>
	</TABLE>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>

</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
<% smsRecordLocal.remove();%>