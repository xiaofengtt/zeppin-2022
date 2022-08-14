<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//获得页面传递变量
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer close_flag = Utility.parseInt(request.getParameter("close_flag"), new Integer(0));

//声明辅助变量
int iCount = 0;
List rsList = null;
List rsList_details = null;
Map rsMap = null;
Map rsMap_details = null;
Integer showFlag2 = Utility.parseInt(Utility.trimNull(request.getParameter("showFlag2")),new Integer(0));//按钮显示

//获得对象
ServiceTaskLocal serviceTask = EJBFactory.getServiceTask();
ServiceTaskVO vo = null;
ServiceTaskVO vo_details = null;

//声明显示字段
Integer managerID =new Integer(0);
Integer serviceType = Utility.parseInt(request.getParameter("serviceType"), new Integer(0));
String managerName = "";
String serviceTypeName = "";
String start_date = "";
String end_date =  "";
String serviceTitle ="";
String serviceWay = "";
String serviceWayName = "";
String serviceRemark =  "";
Integer product_id =new Integer(0);
Integer ques_id = new Integer(0);
String productName = "";
String quesTitle = "";

//编辑时显示 任务信息
if(serial_no.intValue()>0){
	vo = new ServiceTaskVO();
	vo_details = new ServiceTaskVO();
	
	vo.setSerial_no(serial_no);
	vo.setServiceType(serviceType);
	vo.setInputMan(input_operatorCode);
	
	rsList = serviceTask.query(vo);
	
	if(rsList.size()>0){
		rsMap = (Map)rsList.get(0);
	}
	
	if(rsMap!=null){
		start_date =  Utility.trimNull(rsMap.get("StartDateTime"));	
		end_date = Utility.trimNull(rsMap.get("EndDateTime"));
		serviceTitle = Utility.trimNull(rsMap.get("ServiceTitle"));	
		managerID = Utility.parseInt(Utility.trimNull(rsMap.get("ManagerID")), new Integer(0));
		managerName = Argument.getOpNameByOpCode(managerID);
		serviceWay = Utility.trimNull(rsMap.get("ServiceWay"));
		serviceWayName= Argument.getDictParamName(1109,serviceWay);
		serviceType = Utility.parseInt(rsMap.get("ServiceType").toString(), new Integer(0));
		serviceTypeName = Utility.trimNull(rsMap.get("ServiceTypeName"));
		serviceRemark = Utility.trimNull(rsMap.get("ServiceRemark"));
		ques_id = Utility.parseInt(Utility.trimNull(rsMap.get("QUES_ID")), new Integer(0));
		product_id = Utility.parseInt(Utility.trimNull(rsMap.get("PRODUCT_ID")),new Integer(0));
		productName = Argument.getProductName(product_id);
		quesTitle = Utility.trimNull(rsMap.get("QUES_TITLE"));
	}
	
	vo_details.setTaskSerialNO(serial_no);
	vo_details.setInputMan(input_operatorCode);
	rsList_details = serviceTask.query_details(vo_details);
}

if(start_date.length()>0){
	start_date = start_date.substring(0,16);
}

if(end_date.length()>0){
	end_date = end_date.substring(0,16);
}
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.addTask",clientLocale)%> 1</title><!-- 任务创建设置 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>

<script language=javascript>
function show(parm){
	//document.getElementById("showFlag").value = parm;

   for(i=1;i<4;i++) {  
	     if(i!= parm){	     	
	      	eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
	      	if(document.getElementById("r"+i)!=null)
			 eval("document.getElementById('r" + i + "').style.display = 'none'");
		 }
		 else{
		   	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");		   
		    if(document.getElementById("r"+i)!=null)
			  	eval("document.getElementById('r" + i + "').style.display = ''");
		 } 
	}
}

//初始化页面
window.onload = function(){
	show(1);
	var showFlag2 = document.getElementById("showFlag2").value;
	
	if(showFlag2==1){
		document.getElementById("btnPre").style.display="none";
		document.getElementById("btnSure").style.display="none";
	}
}

function previorsAction(){
	var a = document.createElement("a");
	var v_serial_no= document.getElementById("serial_no").value;
	
    a.href = "<%=request.getContextPath()%>/affair/service/service_create_setAction2.jsp?serial_no="+v_serial_no;
    document.body.appendChild(a);
    a.click();
}
/*取消*/
function CancelAction(){
	var v_close_flag = document.getElementById("close_flag").value;
	if(v_close_flag==0){
		window.returnValue=null;
		window.close();
		window.parent.location.reload();
	}
	else if(v_close_flag==1){
		window.parent.location.reload();
		window.parent.document.getElementById("closeImg").click();	
	}

}
</script>

</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post"  onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="serial_no"  id="serial_no" value="<%=serial_no%>"/>
<input type="hidden" name="showFlag2" id="showFlag2" value="<%= showFlag2%>" />
<input type="hidden" name="close_flag" id="close_flag" value="<%= close_flag%>" />
<div align="left" class="page-title">
    <!-- 任务管理 --><!-- 任务创建 --><!-- 任务信息查看 -->
	<font color="#215dc6"><b><%=LocalUtilis.language("message.taskManager",clientLocale)%>>><%=LocalUtilis.language("message.taskAdd",clientLocale)%>>><%=LocalUtilis.language("message.queryTask",clientLocale)%></b></font>
</div>
<br/>
<div style="margin-left:40px;margin-top:10px;">
	<font  size="2" face="宋体">*<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ：</font><!-- 任务标题 -->
	<input type="text" name="service_title" style="width:120px"  readonly class='edline'  value="<%=serviceTitle%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
	<font  size="2" face="宋体">*<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ：</font><!-- 任务类别 -->
	<input type="text" name="serviceTypeName" style="width:120px"  readonly class='edline'  value="<%=serviceTypeName%>" onkeydown="javascript:nextKeyPress(this)"/>
	&nbsp;&nbsp;
</div>

<div align="left"  style="margin-left:10px">
	<TABLE cellSpacing=0 cellPadding=0 width="97%" border="0" class="edline">
				<TBODY>
					<TR>							
						<TD vAlign=top>&nbsp;</TD>					
						<!-- 基本信息 -->
                        <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
						<!-- 扩展信息 -->
                        <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.extendedInformation",clientLocale)%> </TD>	
						<!-- 明细信息 -->
                        <TD id="d3" style="background-repeat: no-repeat" onclick=show(3) vAlign=top width=80 height=20 background='/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.detailInformation",clientLocale)%> </TD>						
					</TR>
			</TBODY>
	</TABLE>
</div>

<div  id="r1" align="left"  style="display:none; height:165px; margin-left:10px;margin-top:10px;">
	 <table  id="table1" cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
 			<tr style="background:F7F7F7;">
		 			<!-- 任务信息主要内容 -->
                    <td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.mainInfo",clientLocale)%> </b></font></td>
		 	</tr>
		 	
		 	 <tr style="background:F7F7F7;" >	
				 <td width="30%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTitle",clientLocale)%> ：</font></td><!-- 任务标题 -->
				 <td width="*" ><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceTitle%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> ：</font></td><!-- 任务类别 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceTypeName%></font>  </td>
			</tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.accountManager",clientLocale)%> ：</font></td><!-- 客户经理 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= managerName%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font></td><!-- 联系方式 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceWayName%></font>  </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.startDate",clientLocale)%> ：</font></td><!-- 开始日期 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= start_date%></font>  </td>
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.endDate",clientLocale)%> ：</font></td><!-- 结束日期 -->
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= end_date%></font>  </td>
			 </tr>
		 	
		 	 <tr style="background:F7F7F7;">	
				 <td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceRemark",clientLocale)%> ：</font></td><!-- 任务详细描述 -->
				 <td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= serviceRemark%></font>  </td>
			 </tr>
 		</table>
</div>

<div  id="r2" align="left"  style="display:none; height:165px; margin-left:10px;margin-top:10px;">
	<table border="0" width="98%" cellSpacing="1" cellPadding="2" bgcolor="#CCCCCC">
					<tr style="background:F7F7F7;">
						<td  width="150px"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.questionaireName2",clientLocale)%> ：</font></td><!-- 问卷名称 -->
						<td  width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= quesTitle%></font>  </td>
						
						<td width="150px"><font size="2" face="微软雅黑">&nbsp;&nbsp; <%=LocalUtilis.language("class.productName",clientLocale)%> ：</font></td><!-- 产品名称 -->
						<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= productName%></font>  </td>
					</tr>	
	</table>
</div>

<div  id="r3" align="left"  style="display:none; height:165px; margin-left:10px;margin-top:10px;">
	<table cellSpacing="1" cellPadding="2" width="800px"  bgcolor="#CCCCCC">
		<tr style="background:6699FF;">
			 <td width="*" align="center"><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!-- 客户名称 -->
			 <td width="20%" align="center"><%=LocalUtilis.language("class.feedback",clientLocale)%> </td><!-- 反馈 -->
			 <td width="20%" align="center"><%=LocalUtilis.language("class.serviceStatusName",clientLocale)%> </td><!--  状态 -->			 
		</tr>  
	</table>	
	<span id="tableList" style="overflow-y:auto;height:145;">
		<table border="0" width="800px" cellspacing="1" cellpadding="2" bgcolor="#CCCCCC">
<%
	//声明字段
	String cust_name = "" ;	
	String feedback ="";
	String serviceStatusName_details = "";
	Integer needFeedback = new Integer(1);
	Integer serial_no_detail = new Integer(0);
	int status_flag_details = 0;

	if(rsList_details!=null&&rsList_details.size()>0){
		 Iterator iterator = rsList_details.iterator();
						
		while(iterator!=null&&iterator.hasNext()){		
			rsMap_details = (Map)iterator.next();
			iCount++;

			if(rsMap_details!=null){
					serial_no_detail = Utility.parseInt(Utility.trimNull(rsMap_details.get("Serial_no")),null);
					cust_name = Utility.trimNull(rsMap_details.get("CUST_NAME"));	
					needFeedback = Utility.parseInt(Utility.trimNull(rsMap_details.get("NeedFeedback")),new Integer(0));
					status_flag_details = Utility.parseInt(Utility.trimNull(rsMap_details.get("Status")),0);
					serviceStatusName_details = Argument.getService_status_name(status_flag_details);
			}
			
			if(needFeedback.intValue()==1){
				 feedback = LocalUtilis.language("message.need",clientLocale);//需要
			}else if(needFeedback.intValue()==2){
				feedback = LocalUtilis.language("message.no",clientLocale);//不需要 
                
			}
	%>
			<tr class="tr1">
				 <td align="center" width="*" ><%= cust_name%> </td>         
				 <td align="center" width="20%" ><%= feedback%></td>  
				 <td align="center" width="20%" ><%= serviceStatusName_details%></td>        
			</tr>
<%}
}%>
		<%if(iCount==0){%>
			<tr class="tr1"><td align="center" colspan="4"><%=LocalUtilis.language("message.noDetails",clientLocale)%> </td></tr><!-- 没有添加明细任务信息 -->
		<%}%>
		</table>
	</span>
</div>

<div align="right" style="margin-top:10px;">		
	<button type="button" class="xpbutton3" accessKey=s id="btnPre" name="btnPre" title="<%=LocalUtilis.language("message.up",clientLocale)%> " onclick="javascript: previorsAction();"><%=LocalUtilis.language("message.up",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;		
	<button type="button" class="xpbutton3" accessKey=n id="btnSure" name="btnSure" title="<%=LocalUtilis.language("message.ok",clientLocale)%> " onclick="javascript:  CancelAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>
	&nbsp;&nbsp;&nbsp;	
</div>	
<% serviceTask.remove();%>
</form>
</BODY>
</HTML>