<%@ page contentType="text/html; charset=GBK" import="enfo.crm.callcenter.*,enfo.crm.affair.*,enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.tools.*, enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//初始化参数信息
String interfaceType_code = "StaffLogQuery010",object_type="WorkLog";
String query_type = Utility.trimNull(request.getParameter("QueryType"));
ConfigLocal configLocal = EJBFactory.getConfig();
boolean bSuccess = false;

//获得对象
ServiceTaskLocal serviceTaskLocal = EJBFactory.getServiceTask();
CustomerLocal customerLocal = EJBFactory.getCustomer();
SmsRecordLocal smsRecordLocal = EJBFactory.getSmsRecord();
String phone_num="";
SendSMSVO vo_sms = null;
String[] sendBackInfo = null;
//获得数据
ServiceTaskVO vo_details = new ServiceTaskVO();
CustomerVO vo_cust = new CustomerVO();

//通过页面编码查找该表对应的主键字段和表名
String identityCode = ConfigUtil.getIdentityCode(configLocal,interfaceType_code);
List catalogList =ConfigUtil.getTableName(configLocal,interfaceType_code);
String table_code = (String)catalogList.get(0);
String grid_name =(String)catalogList.get(1);
String freeform_name =(String)catalogList.get(2);
String delStr=identityCode+"DEL";//列表中checkbox的name值

if(request.getMethod().equals("POST")){
	String object_id= request.getParameter("objectid");
	String input_man  = request.getParameter("inputman");
	phone_num= Utility.trimNull(ConfigUtil.getSqlResult("select mobile from toperator where op_code= "+input_man));
	System.out.println(object_id+input_man+phone_num);
	String sms_content =  "日志未提交";
	//Integer sendLevel = Utility.parseInt(Utility.trimNull(request.getParameter("sendLevel")),new Integer(0));
	String putType= enfo.crm.tools.LocalUtilis.language("message.readyToSend", clientLocale);//待发
	String sms_cust_mobile = "";
	Integer sms_define_no = new Integer(0);
	
	Integer smsIndex = new Integer(0);
	
	
		vo_sms = new SendSMSVO();
		
		sms_cust_mobile = phone_num;
		//sms_define_no = Utility.parseInt(object_id,new Integer(0));
		vo_sms.setSmsUser(Utility.trimNull(input_operatorCode));
		vo_sms.setPutType(putType);
		vo_sms.setNewFlag(new Integer(1));
		//vo_sms.setSendLevel(sendLevel);
		vo_sms.setInputOperator(input_operatorCode);
		vo_sms.setSmsContent(sms_content);
		vo_sms.setPhoneNumber(sms_cust_mobile);
		//vo_sms.setSerial_no_detail(sms_define_no);
		//先把短信信息保存在系统表里
		smsIndex = smsRecordLocal.append(vo_sms);
		vo_sms.setUserDefineNo(smsIndex);
		
		try {
			sendBackInfo = SmsClient.sendMessage(vo_sms);	
		}
		catch(Exception e){
			vo_sms = new SendSMSVO();
			
			vo_sms.setSmsIndex(smsIndex);
			vo_sms.setStatus(new Integer(2));
			vo_sms.setStatusName(enfo.crm.tools.LocalUtilis.language("message.failSubmit", clientLocale));//提交失败
			vo_sms.setInputOperator(input_operatorCode);

			smsRecordLocal.modi(vo_sms);			
			throw new Exception(enfo.crm.tools.LocalUtilis.language("message.checkSMSPlatform", clientLocale)+"!");//请检查短信发送平台
		}
		
		vo_sms = new SendSMSVO();
		
		vo_sms.setSmsIndex(smsIndex);
		vo_sms.setStatus(Utility.parseInt(sendBackInfo[0],new Integer(0)));
		vo_sms.setStatusName(sendBackInfo[1]);
		vo_sms.setInputOperator(input_operatorCode);
		smsRecordLocal.modi(vo_sms);

	
	bSuccess = true;
}
//查询时用到的数据集合
List queryFieldList = configLocal.queryShowList(interfaceType_code);
String[] queryFieldColStr =new String[queryFieldList.size()];//字段集
String[] queryFieldNameStr =new String[queryFieldList.size()];//字段名称集
String[] queryValueStr = new String[queryFieldList.size()];//查询字段页面值
String[] queryConditionTypeStr = new String[queryFieldList.size()];//查询类型集
String[] queryConditionValueStr = new String[queryFieldList.size()];//真正查询的类别值
String[] queryInputTypeStr = new String[queryFieldList.size()];//元素框类型集
String[] queryValueTypeStr = new String[queryFieldList.size()];//下拉框数据源类型集
String[] queryValueContentStr =new String[queryFieldList.size()];//数据查询语句集
//查询处理块

ConfigUtil.queryConditionBlock(request,queryFieldList,queryFieldColStr,queryFieldNameStr,queryInputTypeStr,queryValueTypeStr,queryValueContentStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);

List tableFieldList = configLocal.tableFieldShowList(interfaceType_code);//查询需要显示有效的页面表格字段
String[] fieldStrs = new String[tableFieldList.size()];//显示字段编码
String[] fieldNameStrs = new String[tableFieldList.size()];//显示字段名称
ConfigUtil.pageValidColShowBlock(tableFieldList,fieldStrs,fieldNameStrs);//获取字段和字段名称集合

//其他查询字段
List replaceColValue = new ArrayList();//需要替换的值
String sqlContent = ConfigUtil.getOtherQuerySql(interfaceType_code,replaceColValue);
String depart_id =ConfigUtil.getPropertyNameById("toperator","depart_id","op_code",String.valueOf(input_operatorCode));
sqlContent= sqlContent + " and ( exists (select 1 from TOPROLE where ROLE_ID=91 and OP_CODE=" + input_operatorCode +" ))"+ 
	" or INPUT_MAN in ( select MANAGERID from TManagerTeams tt left join  TManagerTeamMembers tts on  tt.TEAM_ID=tts.TEAM_ID where MANAGERID = " + input_operatorCode +" or LEADER =" + input_operatorCode +")";

if(query_type.equals("")) 
	sqlContent =sqlContent + " and 1=2";
IPageList pageList = configLocal.showPageDataOther(interfaceType_code,table_code,queryFieldColStr,queryValueStr,queryConditionValueStr,
										sqlContent,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

sUrl = ConfigUtil.getSUrl(sUrl,queryFieldColStr,queryConditionTypeStr,queryValueStr,queryConditionValueStr);
sPage="QueryType="+query_type;
sUrl=sUrl+"&QueryType="+query_type;
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<BASE TARGET="_self">
</HEAD>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/system/configcommon.js"></SCRIPT>

<script language=javascript>

function showInfo(identity_code)
{
	var tableCode = document.theform.table_code.value;
	var interfaceTypeCode = document.theform.interfacetype_code.value;
	var returnValue = checkLogic("CRMBusinessCheck","getLogType",identity_code);
	if(showModalDialog('<%=freeform_name%>?interfacetype_code='+interfaceTypeCode+'&edit_right=no&logType='+returnValue+'&object_type=<%=object_type%>&object_id='+identity_code+'&table_code='+tableCode, '', 'dialogWidth:900px;dialogHeight:700px;status:0;help:0') != null)
	{
		sl_update_ok();
		document.theform.submit();
	}
}
function sendInfo(identity_code,input_man){
	document.getElementById("objectid").value=identity_code;
	document.getElementById("inputman").value=input_man;
	var returnValue = checkLogic("CRMBusinessCheck","getPhoneNum",input_man);
	if(returnValue==""){
		alert("用户未设置移动电话号码！");
	}else {
		document.theform.submit();
	}
	
}

</script>

<BODY class="BODY" >
<form name="theform" method="POST" action="<%=grid_name%>">
<input name="interfacetype_code" type="hidden" value="<%=interfaceType_code %>" />
<input name="table_code" type="hidden" value="<%=table_code%>" />
<input name="objectid" type="hidden" value="" />
<input name="inputman" type="hidden" value="" />
<%
request.setAttribute("queryFieldColStr",queryFieldColStr);//查询字段集集
request.setAttribute("queryFieldNameStr",queryFieldNameStr);//查询字段名称集;
request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);//查询字段查询类型集
request.setAttribute("queryConditionValueStr",queryConditionValueStr);//查询字段选择的查询类型
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//元素框类型集
request.setAttribute("queryValueTypeStr",queryValueTypeStr);//下拉框数据源类型集
request.setAttribute("queryValueContentStr",queryValueContentStr);//数据查询语句
request.setAttribute("queryValueStr",queryValueStr);//查询字段页面值
request.setAttribute("queryInputTypeStr",queryInputTypeStr);//元素框类型值
 %>
<jsp:include page="/system/config/basiclist_query.jsp" flush="true"></jsp:include>

<%
	request.setAttribute("bSuccess",new Boolean(bSuccess));
	request.setAttribute("queryFieldColStr",queryFieldColStr);
	request.setAttribute("queryConditionTypeStr",queryConditionTypeStr);
	request.setAttribute("queryConditionValueStr",queryConditionValueStr);
	request.setAttribute("queryValueStr",queryValueStr);
%>
<jsp:include page="/system/config/basiclist_js.jsp" flush="true">
	<jsp:param name="grid_name" value="<%=grid_name%>"/>
	<jsp:param name="sPage" value="<%=sPage%>"/>
</jsp:include>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td><IMG src="/images/member.gif" align="absBottom" border="0" width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align="right">
							<button class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>
							&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
					<tr>
						<td colspan="2"><hr noshade color="#808080" size="1"></td>
					</tr>
				</table>
	 	<%
	 	  request.setAttribute("fieldNameStrs",fieldNameStrs); 
	 	  request.setAttribute("pageList",pageList);
	 	  request.setAttribute("fieldStrs",fieldStrs);
	 	  request.setAttribute("tableFieldList",tableFieldList);
	 	%>
		<jsp:include page="/system/config/basiclist_table.jsp" flush="true">
			<jsp:param name="sUrl" value="<%=sUrl%>"/>
		</jsp:include>	
		
		</td>
		</tr>		
	</table>
	</td>
	</tr>	
</table>
</form>
<%@ include file="/includes/foot.inc"%>	
</BODY>

</HTML>
<%configLocal.remove();%>