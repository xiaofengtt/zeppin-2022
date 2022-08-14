<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>

<%
//获取主键ID 
String[] temp_checks = request.getParameterValues("check_serial_no");
Integer taskSerialNo =Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer t_serial_no;
boolean bSuccess = false;

//获得对象
ServiceTaskLocal local = EJBFactory.getServiceTask();
ServiceTaskVO vo = new ServiceTaskVO();

//逐个删除
if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
		
		if(t_serial_no.intValue()!=0){				
			vo.setSerial_no(t_serial_no);
			vo.setInputMan(input_operatorCode);
			
			local.delete_details(vo);			
		}
	}
}
bSuccess = true;
local.remove();
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.deleteTaskList",clientLocale)%> </title><!-- 明细任务删除 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
    window.onload = function(){		    
		    var v_bSuccess = document.getElementById("bSuccess").value;
		    
		    if(v_bSuccess=="true"){			
					alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
					
					var a = document.createElement("a");
				    a.href = "<%=request.getContextPath()%>/affair/service/service_create_setAction2.jsp?serial_no=<%=taskSerialNo%>";
				    document.body.appendChild(a);
				    a.click();
			}
    }
</SCRIPT>
</head>
<body>
<!--保存标志-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

</body>
</html>