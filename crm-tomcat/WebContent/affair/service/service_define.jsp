<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//获得对象
ServiceManageLocal serviceManage = EJBFactory.getServiceManage();
ServiceManageVO vo = new ServiceManageVO();

//页面辅助变量
int iCount = 0;
int iCurrent = 0;
List list = serviceManage.query_TServiceDefine(vo);
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
<title><%=LocalUtilis.language("menu.taskDefinition",clientLocale)%> </title><!-- 任务定义 -->
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
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/LeeDialog/dialog.js"></script>
<script language="javascript">
/*修改方法*/
function EditAction(serial_no){				
	var url = "service_define_edit.jsp?serial_no="+serial_no;	
	 //不显 任务管理、任务定义 <%=LocalUtilis.language("message.taskManager'/>>><fmt:message key='menu.taskDefinition",clientLocale)%> 
	dialog("","iframe:"+url,"600px","450px","show","<%=request.getContextPath()%>");
	
	/*if(showModalDialog(url,'', 'dialogWidth:580px;dialogHeight:450px;status:0;help:0')){
		sl_update_ok();
		location.reload();
	}*/
	
	showWaitting(0);
}
</script>
</head>

<body class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>	
<br/>
<div valign="middle" align="center">
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			 <td width="16%" align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!-- 任务类别 -->	
			 <td width="16%" align="center"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td> <!-- 联系方式 -->
			 <td width="16%" align="center"><%=LocalUtilis.language("class.OffsetDays",clientLocale)%> </td> <!-- 天偏移量 -->
			 <td width="16%" align="center"><%=LocalUtilis.language("class.noticeCaption",clientLocale)%> </td><!-- 提示说明文字 -->
	         <td width="16%" align="center"><%=LocalUtilis.language("class.accountManager",clientLocale)%> </td><!-- 客户经理 -->
	         
	         <td width="16%" align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!-- 编辑 --> 
	     </tr>
	     
<%
	//声明参数变量
	Integer serial_no;
	Integer offsetDays = new Integer(0); //天偏移量
	String serviceTypeName;
	Integer executor;	
	String serviceWay;	
	String noticeCaption ="";	 // 提示文字
	Integer offset_flag = new Integer(0);	
	
	Iterator iterator = list.iterator();	
		
	while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		
		serial_no = Utility.parseInt(Utility.trimNull(map.get("Serial_no")),new Integer(0));
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));
		executor = Utility.parseInt(Utility.trimNull(map.get("Executor")),new Integer(0));
		serviceWay = Utility.trimNull(map.get("ServiceWay"));
		offsetDays = Utility.parseInt(map.get("OffsetDays").toString(), new Integer(0));
		noticeCaption = Utility.trimNull(map.get("NoticeCaption"));
		
		offset_flag = offsetDays.intValue()<0?new Integer(1):new Integer(0);
		
		
		
%>	   <tr class="tr<%=iCount%2%>">
		 <td align="center"><%= serviceTypeName%></td>    	
		 <td align="center"><%= Argument.getDictParamName(1109,serviceWay)%></td>  	 
         <!-- 推迟 --><!-- 提前 --><!-- 天 -->
		 <td align="center"><%if(offset_flag.intValue()==0){out.print(LocalUtilis.language("message.putOff",clientLocale));}else{out.print(LocalUtilis.language("message.inAdvance",clientLocale));}%><%=Math.abs(offsetDays.intValue())%><%=LocalUtilis.language("message.days",clientLocale)%> </td>  
		 <td align="center"><%= noticeCaption%></td>  
		 <td align="center"><%= Argument.getManager_Name(executor)%></td>    
		 <td align="center">              
			<%if (input_operator.hasFunc(menu_id, 102)) {%>	
          	<a href="javascript:EditAction(<%=serial_no%>)">
           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" align="absmiddle" width="16" height="16">
           	</a>
			<%} %>
          </td>
        </tr>
<%}%>
	     
	</TABLE>
</div>
<%@ include file="/includes/foot.inc"%>
</body>
</html>