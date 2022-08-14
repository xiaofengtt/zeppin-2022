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
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
		
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.taskSelection",clientLocale)%> </title><!-- 任务选择 -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
	var oState = {}
	
	oState = window.dialogArguments;
	
	
	window.onload = function(){
		try{
			if(oState.show == "false"){
				document.getElementsByName("btnOk")[0].style.display = "none";
              	//基本管理 任务查看 
				document.getElementById("headTitle").innerText="<%=LocalUtilis.language("message.basicManagement",clientLocale)%>\>><%=LocalUtilis.language("message.taskView",clientLocale)%> ";
				var _btnCheckbox = document.getElementsByName("btnCheckbox");
				for(var i=0;i != _btnCheckbox.length; i++){
					_btnCheckbox[i].disabled=true;
				}
			}
			if(oState.flag == "edit"){
				var _btnCheckbox = document.getElementsByName("btnCheckbox");
				for(var i=0;i != _btnCheckbox.length; i++){
					if( (parseInt(_btnCheckbox[i].getAttribute("value")) & parseInt(oState.service_value)) != 0){
							_btnCheckbox[i].checked = true;	
					}
				}
			}	
		}catch(e){

		}
	}

	function onOk(){
		var valu_arr = new Array();
		var _btnCheckbox = document.getElementsByName("btnCheckbox");
		for(var i=0;i != _btnCheckbox.length; i++){
			if(_btnCheckbox[i].checked){
				valu_arr.push(_btnCheckbox[i].getAttribute("value"));			
			}
		}
		if(valu_arr.length !=0 ){
			var valu = 0
			for(var j=0; j!=valu_arr.length;j++){
				valu += (0+ valu_arr[j]/1);
			}
			oState.service_value = valu;
			window.returnValue = true;
			window.close();
		}else{
			alert("<%=LocalUtilis.language("message.selectServiceType",clientLocale)%> ！");//对不起，请选择服务类别
		}
	}
</script>
</head>

<body class="body body-nox">

<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
	<tr>
		<td align="left" class="page-title">
            <!-- 基本管理 --><!-- 任务设置 -->
			<b><%=LocalUtilis.language("message.basicManagement",clientLocale)%>>><%=LocalUtilis.language("message.setTask",clientLocale)%> </b>
		</td>
	</tr>
	<tr>
		<td align="right" >
		<div class="btn-wrapper">
            <!-- 确定 -->
			<button type="button" class="xpbutton3" accessKey=y  name="btnOk" title="<%=LocalUtilis.language("message.confirm",clientLocale)%> " onclick="onOk()"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>Y</u>)</button>&emsp;
			<!-- 关闭 -->
            <button type="button" class="xpbutton3" accessKey=n  name="btnOk" title="<%=LocalUtilis.language("message.close",clientLocale)%>" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<u>N</u>)</button>
		</div>
		<br/>
		</td>
	</tr>
</table>
<div style="height:186px;  margin: 0 0 0 0; overflow-y: auto; overflow-x: no;">
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
		<tr class="trh">
			 <td width="40%" align="center"><%=LocalUtilis.language("menu.taskSelection",clientLocale)%> </td><!-- 任务选择 -->
			 <td width="*" align="center"><%=LocalUtilis.language("class.serviceTypeName",clientLocale)%> </td><!-- 任务类别 -->
	    </tr>
	     
<%
	//声明参数变量
	Integer serviceType;
	String serviceTypeName;
	Iterator iterator = list.iterator();	
		
	while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		serviceType = Utility.parseInt(Utility.trimNull(map.get("ServiceType")),new Integer(0));
		serviceTypeName = Utility.trimNull(map.get("ServiceTypeName"));		
%>	   
		<tr class="tr<%=iCount%2%>">
			<td align="center">
				<input type="checkbox" name="btnCheckbox" class="flatcheckbox" value="<%=serviceType%>" onclick="">
			</td> 
		 	<td align="center"><%=serviceTypeName%></td>    	
       	</tr>
<%}%>
	</TABLE>
</div>

</body>
</html>