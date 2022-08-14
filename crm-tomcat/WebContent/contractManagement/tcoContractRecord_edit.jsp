<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoContractRecordLocal tcoContractRecordLocal = EJBFactory.getTcoContractRecord();
TcoContractRecordVO tcoContractRecordVO = new TcoContractRecordVO();

boolean bSuccess = false;
Integer mainRecord_id=Utility.parseInt(request.getParameter("mainRecord_id"),new Integer(0));
tcoContractRecordVO.setMainRecord_id(mainRecord_id);
List tcoContractRecordList=tcoContractRecordLocal.queryByList(tcoContractRecordVO);
Map tcoContractRecordMap=(Map)tcoContractRecordList.get(0);
String cust_id=Utility.trimNull(tcoContractRecordMap.get("CUST_ID"));
String cust_name=Utility.trimNull(tcoContractRecordMap.get("CUST_NAME"));
String main_pro_name=Utility.trimNull(tcoContractRecordMap.get("MAIN_PRO_NAME"));
String main_content=Utility.trimNull(tcoContractRecordMap.get("MAIN_CONTENT"));
String record_date=Utility.trimNull(tcoContractRecordMap.get("RECORD_DATE"));
Integer team_id=Utility.parseInt(Utility.trimNull(tcoContractRecordMap.get("TEAM_ID")),new Integer(0));
Integer coProduct_manager=Utility.parseInt(Utility.trimNull(tcoContractRecordMap.get("COPRODUCT_MANAGER")),new Integer(0));

if(request.getMethod().equals("POST")){

	tcoContractRecordVO.setMainRecord_id(Utility.parseInt(request.getParameter("mainRecord_id"),new Integer(0)));
	tcoContractRecordVO.setCust_id(Utility.parseInt(request.getParameter("cust_id"),new Integer(0)));
	tcoContractRecordVO.setMain_pro_name(Utility.trimNull(request.getParameter("main_pro_name")));
	tcoContractRecordVO.setMain_content(Utility.trimNull(request.getParameter("main_content")));
	tcoContractRecordVO.setTeam_id(Utility.parseInt(request.getParameter("team_id"),new Integer(0)));
	tcoContractRecordVO.setCoProduct_manager(Utility.parseInt(request.getParameter("coProduct_manager"),new Integer(0)));
	tcoContractRecordVO.setRecord_date(Utility.parseInt(request.getParameter("record_date"),new Integer(0)));
	tcoContractRecordVO.setInput_man(input_operatorCode);
	tcoContractRecordLocal.modi(tcoContractRecordVO);

	bSuccess = true;

}
%>
<html>
<head>
<title></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">


<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<script language=javascript>

<%if (bSuccess){%>
	alert("修改成功");
	window.location.href="tcocontractrecordset.jsp";
<%}%>


/*客户信息*/
function getMarketingCustomer(prefix,readonly){	
	var url = '<%=request.getContextPath()%>/marketing/customerInfo.jsp?prefix='+ prefix+ '&readonly='+readonly ;	
	var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:720px;status:0;help:0;');	
		
	if (v!=null){

		document.theform.cust_name.value = v[0]
		document.theform.cust_id.value = v[7];
	}	
	
	return (v != null);
}


function validateForm(form)
{
	if(!sl_checkDate(document.theform.record_date_picker,"受理日期"))return false;//受理日期
	syncDatePicker(document.theform.record_date_picker, document.theform.record_date);	
	
	return sl_check_update();
}

function saveAction(){
	if(!sl_checkDate(document.theform.record_date_picker,"受理日期"))return false;//受理日期
	syncDatePicker(document.theform.record_date_picker, document.theform.record_date);	

	document.theform.action="tcoContractRecord_edit.jsp";	
	document.theform.submit();
}
function cancelAction(){
	window.location.href="tcocontractrecordset.jsp";
}

</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post">
<input type="hidden" name="mainRecord_id" value="<%=mainRecord_id%>"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>新增客户维护记录</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="68%" cellspacing="3">
	<tr>
		<td align="right">客户名称: </td><!--客户名称-->
		<td align="left" colspan="3">
			<input type="hidden" id="cust_id" name="cust_id" value="<%=cust_id%>" />
			<input maxlength="100" readonly class='edline' id="cust_name" name="cust_name" value="<%=cust_name%>" size="60" onkeydown="javascript:nextKeyPress(this);" value="">
			<button type="button" class="xpbutton3" accessKey=e name="btnEdit" title="客户选择 " 
		        onclick="javascript:getMarketingCustomer('customer','0');">客户选择 
		    </button>
		</td>
	</tr>	
	<tr>
		<td  align="right">维护项目名称: </td><!--合同编号-->
		<td  align="left"><input type="text" name="main_pro_name" value="<%=main_pro_name%>" size="25" value="" onkeydown="javascript:nextKeyPress(this)"></td>
		<td align="right">受理日期:</td>
		<td>
			<INPUT TYPE="text" NAME="record_date_picker" class=selecttext value="<%=record_date%>" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.record_date_picker,theform.record_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="record_date" id="record_date"   value="<%=record_date%>">
		</td>
	</tr>
	<tr>	
		<td align="right">隶属项目组: </td>
		<td align="left" >
			<select name="team_id" style="width: 145px;">
				<%=Argument.getTeam_Value(team_id) %>
			</select>
		</td>
		<td  align="right">项目经理:</td><!-- 项目经理  -->
		<td>
			<select name="coProduct_manager" style="width: 120px;">
				<%=Argument.getManager_Value(coProduct_manager)%>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">项目维护内容：</td>
		<td colspan="3">
			<textarea rows="" cols="98" name="main_content"><%=main_content %></textarea>
		</td>
	</tr>
</table>
<table border="0" width="60%">
	<tr>
		<td align="right">
		<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="saveAction()"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="cancelAction()"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
<%
tcoContractRecordLocal.remove();
 %>
</html>
