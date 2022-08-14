<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,enfo.crm.cont.*,java.math.BigDecimal,enfo.crm.contractManage.*,enfo.crm.web.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
TcoProductLocal tcoProductLocal = EJBFactory.getTcoProduct();
TcoProductVO tcoProductVO = new TcoProductVO();

boolean bSuccess = false;

if(request.getMethod().equals("POST")){
	tcoProductVO.setCoProduct_name(Utility.trimNull(request.getParameter("coProduct_name")));
	tcoProductVO.setPublish_date(Utility.parseInt(request.getParameter("publish_date"),new Integer(0)));
	tcoProductVO.setTeam_id(Utility.parseInt(request.getParameter("team_id"),new Integer(0)));
	tcoProductVO.setCoProduct_manager(Utility.parseInt(request.getParameter("coProduct_manager"),new Integer(0)));
	tcoProductVO.setCoProduct_type(Utility.trimNull(request.getParameter("coProduct_type")));
	tcoProductVO.setCoProduct_type_name(Utility.trimNull(request.getParameter("coProduct_type_name")));
	tcoProductVO.setSelfMade_type(Utility.trimNull(request.getParameter("selfMade_type")));
	tcoProductVO.setSelfMade_type_name(Utility.trimNull(request.getParameter("selfMade_type_name")));
	tcoProductVO.setCoProduct_price(Utility.parseDecimal(request.getParameter("coProduct_price"),new BigDecimal(0.00)));
	tcoProductVO.setCoProduct_summary(Utility.trimNull(request.getParameter("coProduct_summary")));
	tcoProductVO.setInput_man(input_operatorCode);
	tcoProductLocal.append(tcoProductVO);

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
	window.returnValue = true;
	window.close();
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
	if(!sl_checkDate(document.theform.publish_date_picker,"首次发行日期"))return false;
	syncDatePicker(document.theform.publish_date_picker, document.theform.publish_date);	
	
	return sl_check_update();
}

function saveAction(){
	if(!sl_checkDate(document.theform.publish_date_picker,"首次发行日期"))return false;
	syncDatePicker(document.theform.publish_date_picker, document.theform.publish_date);	
	document.theform.coProduct_type_name.value=document.theform.coProduct_type.text;
	document.theform.selfMade_type_name.value=document.theform.selfMade_type.text;
	var obj=document.theform.coProduct_type;
	var index=obj.selectedIndex;
	var obj1=document.theform.selfMade_type;
	var index1=obj1.selectedIndex;
	document.theform.coProduct_type_name.value=obj.options[index].text;
	document.theform.selfMade_type_name.value=obj1.options[index1].text;
	document.theform.action="tcoProduct_new.jsp";	
	document.theform.submit();
}
function cancelAction(){
	//window.location.href="tcoproductset.jsp";
	window.returnValue=null;
	window.close();
}
/*显示中文钱数*/
function showCnMoney(value){
	temp = value;
	if (trim(value) == "")
		coProduct_price_cn.innerText = "(元)";
	else
		coProduct_price_cn.innerText = "(" + numToChinese(temp) + ")";
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post">
<input type="hidden" name="coProduct_type_name"/>
<input type="hidden" name="selfMade_type_name"/>
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b>新增产品</b></font></td>
	</tr>
	<tr> 
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table border="0" width="98%" cellspacing="3">
	<tr>
		<td align="right">产品名称: </td>
		<td align="left">
			<input type="text" name="coProduct_name" size="25" value="" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td  align="right">首次发行日期: </td>
		<td>
			<INPUT TYPE="text" NAME="publish_date_picker" class=selecttext value="" >
			<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.publish_date_picker,theform.publish_date_picker.value,this);" tabindex="15">
			<INPUT TYPE="hidden" NAME="publish_date" id="publish_date"   value="">
		</td>
	</tr>
	<tr>	
		<td align="right">隶属项目组: </td>
		<td align="left" >
			<select name="team_id" style="width: 145px;">
				<%=Argument.getTeam_Value(null) %>
			</select>
		</td>
		<td  align="right">项目经理:</td><!-- 项目经理  -->
		<td>
			<select name="coProduct_manager" style="width: 145px;">
				<%=Argument.getManager_Value(null)%>
			</select>
		</td>
	</tr>
	<tr>	
		<td align="right">产品类型: </td>
		<td align="left" >
			<select name="coProduct_type" style="width: 145px;">
				<%=Argument.getDictParamOptions(5001,"500101") %>
			</select>
		</td>
		<td  align="right">研发情况:</td>
		<td>
			<select name="selfMade_type" style="width: 145px;">
				<%=Argument.getDictParamOptions(5002,"500201")%>
			</select>
		</td>
	</tr>	
	<tr>	
		<td align="right">建议售价: </td>
		<td align="left" colspan="3">
			<input type="text" name="coProduct_price" size="25" value="" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:sl_checkDecimal(form.coProduct_price,'合同金额',13,3,0);showCnMoney(this.value)">
			<span id="coProduct_price_cn" class="span">&nbsp;(元)</span>
		</td>
	</tr>
	<tr>
		<td align="right">产品功能简介:</td>
		<td colspan="3">
			<textarea rows="" cols="68" name="coProduct_summary"></textarea>
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
tcoProductLocal.remove();
 %>
</html>
