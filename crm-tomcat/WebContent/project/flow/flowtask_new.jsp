<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,java.util.*,java.sql.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//初始化，主键值
Integer identity_code = Utility.parseInt(request.getParameter("task_id"),new Integer("0"));
String edit_right = Utility.trimNull(request.getParameter("edit_right"));
String node_no = Utility.trimNull(request.getParameter("node_no"));
String table_code = "FLOW_TEMP";
String interfaceType_code = "FlowTask050";
ConfigLocal configLocal = EJBFactory.getConfig();
if( !"010".equals(node_no)&& !"".equals(node_no)){
	
	interfaceType_code = "FlowTask060";
	table_code = "FLOW_TASK";
}
System.out.println(interfaceType_code+"___________"+identity_code);
//通过页面编码查找该表对应的主键字段
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
boolean bSuccess = false;
String freeform_name =ConfigUtil.getPropertyNameById("interface_catalog","freeform_name","interfacetype_code",String.valueOf(interfaceType_code));
String nodeNo =ConfigUtil.getPropertyNameById("flow_task","node_no","task_id",String.valueOf(identity_code));
String objectType="";
if(!"010".equals(node_no)&& !"".equals(node_no)){
	objectType =ConfigUtil.getPropertyNameById("flow_task","object_type","task_id",String.valueOf(identity_code));//非010
}
else{
	objectType =ConfigUtil.getPropertyNameById("flow_temp","object_type","task_id",String.valueOf(identity_code));
}
	

List fieldAllList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"1");//获取页面所有的有效元素集合
List fieldShowList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"2");//获取页面所有显示的有效元素集合
List fieldHideList = ConfigUtil.getFieldShowList(interfaceType_code,identity_code,"3");//获取页面所有隐藏的有效元素集合


if(request.getMethod().equals("POST")){
	if(identity_code.intValue() == 0){//新增

		ConfigUtil.addInfo(table_code,fieldAllList,request);//新增页面数据
		CRMBusinessCheck.updateFlowInfo();
	}else{
	
		ConfigUtil.modInfo(table_code,fieldAllList,request,identityCode,identity_code);//修改页面数据
		if("010".equals(node_no)){
		CRMBusinessCheck.updateFlow(identity_code+"");
		}
	}
	bSuccess = true;
}

List modShowDataList = new ArrayList();//修改页面显示数据
if(identity_code.intValue() != 0){//修改显示取值
	modShowDataList = configLocal.modPageShowData(table_code,identityCode,String.valueOf(identity_code));
}

%>
<html>
<head>
<%if(identity_code.intValue() ==0){%>
<title>新增详情</title>
<%}else{%>
<title>修改详情</title>
<%}%>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>

	self.returnValue="success";
	self.close();
<%}%>
//
window.onload= function(){
	<%if( !"010".equals(node_no)){%>
document.theform.TITLE_NAME.value = '<%=ConfigUtil.getSqlResult("select title_name from flow_temp where object_type='"+objectType+"'")%>';
<%}%>
}
function getChange(returnValue){

	var fieldID=returnValue;
	var selectName =document.getElementsByName(fieldID)[0].options[document.getElementsByName(fieldID)[0].selectedIndex].text;
	var returnValue = checkLogic("CRMBusinessCheck","getFlowValues",selectName);
	s=returnValue.split("@@");
		document.theform.FLOW_NO.value=s[0];
		document.theform.NODE_NAME.value=s[2];
		document.theform.NODE_NO.value=s[3];
		document.theform.NODE_FLAG.value=s[4];
		document.theform.DEPT_ID.value=<%=Utility.trimNull(ConfigUtil.getSqlResult("select dbo.GETORGNAME('"+input_operatorCode+"','OPCODEID') "))%>;
		document.theform.DEPT_NAME.value='<%=Utility.trimNull(ConfigUtil.getSqlResult("select dbo.GETORGNAME('"+input_operatorCode+"','OPCODENAME') "))%>';

}

//---------查询当前阶段表决意见---------
function getOpinion(flagOpinion)
{
	if(flagOpinion=="1") document.theform.USER_OPINION.value="同意";
	else if(flagOpinion=="2") document.theform.USER_OPINION.value="不同意";
	else if(flagOpinion=="3") document.theform.USER_OPINION.value="否决";
	
}
//---------查询当前阶段表决意见---------
function selectNodeOpinion()
{
	//获得相关参数
	var flowNo=trim(document.theform.FLOW_NO.value);
	var nodeNo=trim(document.theform.NODE_NO.value);
	var objectType =trim(document.theform.OBJECT_TYPE.value);
	var returnValue = checkLogic("CRMBusinessCheck","checkBusinessInfo",<%=identity_code%>+"@@"+objectType+"@@work");
	//选择意见界面
	var returnValue=showModalDialog('/project/flow/nodeopinion_query.jsp?flow_no='+flowNo+'&node_no='+nodeNo+'&query_type=nodeopinion&checkFlag='+returnValue, '', 'dialogWidth:400px;dialogHeight:300px;status:0;help:0')
	if(typeof(returnValue)!="undefined" && returnValue!=""){
		s=returnValue.split("@@");
		document.theform.NODE_OPINION.value=s[0];
		document.theform.NODE_OPINION_NAME.value=s[1];
		document.theform.DRIVE_TYPE.value=s[2];
		document.theform.NEXT_NODE.value=s[3];
	}
}

//---------获得流程摘要---------
function getFlowDesc()
{
	//获得相关参数
	var objectNo=trim(document.theform.OBJECT_NO.value);
	var objectType=trim(document.theform.OBJECT_TYPE.value);
	var flowNo=trim(document.theform.FLOW_NO.value);
	var returnValue=actionFlow("FlowUtil","getFlowDesc",objectNo+'@@'+objectType+'@@'+flowNo);
	if(typeof(returnValue)!="undefined" && returnValue!=""){
		returnValue = decodeURIComponent(returnValue);
		document.theform.FLOW_DESC.value=returnValue;
		document.getElementById('genbutton').disabled="true";
	}
}

//---------美化流程摘要显示---------
function beautifyFlowDesc()
{
	//获得相关参数
	var flowDesc=trim(document.theform.FLOW_DESC.value);
	if(typeof(flowDesc)!="undefined" && flowDesc!=""){
		flowDesc=flowDesc.replaceAll("】;","】;\n");
		flowDesc=flowDesc.replaceAll("）;","）;\n");
		alert(flowDesc);
	}
}

//----------提交时必输验证------------
function sucSubmit(){
	if(!submitValidate()){
		return false;	
	}

	//获得相关参数
	var flowNo=trim(document.theform.FLOW_NO.value);
	//获得初始化流程信息
	
			
	if(!(document.theform.BEGIN_TIME ==undefined) && (<%=identity_code.intValue()==0%>)){//新增时赋值
		document.theform.BEGIN_TIME.value='<%=new Timestamp(System.currentTimeMillis())%>';
	}
	if(!(document.theform.INPUT_DATE ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.INPUT_DATE.value='<%=ConfigUtil.getToday("day","yyyy-MM-dd")%>';
	}	
	if(!(document.theform.USER_ID ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.USER_ID.value='<%=input_operatorCode%>';
	}
	if(!(document.theform.USER_NAME ==undefined) && (<%=identity_code.intValue()==0%>)){
		document.theform.USER_NAME.value='<%=input_operatorName%>';
	}	
	

	document.theform.submit();
}

//--------------提交审批功能-----------------//
function referflow()
{   
	//保存信息校验
	if(!submitValidate()){
		return false;	
	}
	//初始化保存信息
	document.theform.submit();
	//获得相关参数
	<%if( "010".equals(node_no)){%>
	var rtValue = '<%=ConfigUtil.getSqlResult("select convert(nvarchar(20),task_id) from flow_task where object_type= '"+objectType+"' and node_no= '"+node_no+"'")%>';
	var identity_code=trim(rtValue);
	<%}else {%>
	var identity_code=trim(document.theform.TASK_ID.value);
	<%}%>
	var object_no=trim(document.theform.OBJECT_NO.value);
	var object_type=trim(document.theform.OBJECT_TYPE.value);
	var flow_no=trim(document.theform.FLOW_NO.value);
	var node_no=trim(document.theform.NODE_NO.value);
	var node_opinion=trim(document.theform.NODE_OPINION.value);
	var drive_type=trim(document.theform.DRIVE_TYPE.value);
	var next_node=trim(document.theform.NEXT_NODE.value);
	
	if(typeof(node_opinion)=="undefined" || node_opinion==""){
		sl_alert("请选择当前阶段表决意见，然后再提交！");
		return;
	}
	if(flow_no=="WorkLogFlow" ){
		
	//初始化流程
		var checkFlag=showModalDialog('/project/flow/checkerror_info.jsp?check_flag=DiscInfoCheck&object_type=WorkLogFlow&object_id=' + object_no, '', 'dialogWidth:560px;dialogHeight:450px;status:0;help:0');
   		if(checkFlag=="1"){
	 		if(confirm("确定要提交吗？"))
			{
			disableAllBtn(true);
			//手工节点选择下一节点操作人员
			var selectUser="";
			if(drive_type=="hand"){
				var url="/project/flow/operateobject_query.jsp";
				var param='query_type=NodeUser&flow_no='+flow_no+'&node_no='+next_node;
				selectUser=showModalDialog(url+'?'+param, '', 'dialogWidth:400px;dialogHeight:450px;status:0;help:0');
				if(typeof(selectUser)=='undefined' || selectUser==''){
					sl_alert("请选择下一节点操作人员！");
					return;
				}
			}else{
				selectUser="0";
			}
			var returnValue=actionFlow("FlowUtil","actionFlow",identity_code+'@@'+object_no+'@@'+object_type+'@@'+flow_no+'@@'+node_no+'@@'+node_opinion+'@@'+selectUser);
			if(returnValue=="success"){
				alert("任务提交成功！")
				location.reload();
			}
		}
	}
	}
	else{
    if(confirm("确定要提交吗？"))
	{
		disableAllBtn(true);
		//手工节点选择下一节点操作人员
		var selectUser="";
		if(drive_type=="hand"){
			var url="/project/flow/operateobject_query.jsp";
			var param='query_type=NodeUser&flow_no='+flow_no+'&node_no='+next_node;
			selectUser=showModalDialog(url+'?'+param, '', 'dialogWidth:400px;dialogHeight:450px;status:0;help:0');
			if(typeof(selectUser)=='undefined' || selectUser==''){
				sl_alert("请选择下一节点操作人员！");
				return;
			}
		}else{
			selectUser="0";
		}		
		var returnValue=actionFlow("FlowUtil","actionFlow",identity_code+'@@'+object_no+'@@'+object_type+'@@'+flow_no+'@@'+node_no+'@@'+node_opinion+'@@'+selectUser);
		if(returnValue=="success"){
			alert("任务提交成功！")
			self.returnValue="success";
			self.close();
		}
	}
	}
}
</script>

<%
	//-----------------------初始化参数-----------------------
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("bSuccess",new Boolean(bSuccess));
%>
<jsp:include page="/system/config/basicdetail_js.jsp"></jsp:include>

<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="<%=freeform_name%>" >
<input type="hidden" name="interfacetype_code" value="<%=interfaceType_code%>" />
<input type="hidden" name="task_id" value="<%=identity_code%>">
<input type="hidden" name="table_code" value="<%=table_code%>"/>
<input type="hidden" name="edit_right" value="<%=edit_right%>"/>
<input type="hidden" name="node_no" value="<%=node_no%>"/>
<%
	request.setAttribute("fieldHideList",fieldHideList);
	request.setAttribute("modShowDataList",modShowDataList);
 %>
<jsp:include page="/system/config/basicdetail_hidden.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>
<SCRIPT>

</SCRIPT>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td class="page-title">
				<font color="#215dc6"><b>流程任务</b></font>
			</td>
		</tr>
</table>

<%
	request.setAttribute("fieldShowList",fieldShowList);
	request.setAttribute("modShowDataList",modShowDataList);
%>
<jsp:include page="/system/config/basicdetail_show.jsp">
	<jsp:param name="identity_code" value="<%=identity_code.toString()%>"/>
</jsp:include>

<table border="0" width="100%" >
	<tr >
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript: sucSubmit();">暂存(<u>S</u>)</button>
		&nbsp;&nbsp;
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">返回(<u>C</u>)</button>
		&nbsp;&nbsp;
		<%if(identity_code.intValue() != 0){ %>
		<button type="button" class="xpbutton3" accessKey=m id="btnSave" name="btnSave" onclick="javascript: referflow();">提交(<u>M</u>)</button>
		&nbsp;&nbsp;
		<%} %>
		</td>
	</tr>
</table>
</form>
</body>
</html>

