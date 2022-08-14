<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
SalesResultForStatisticLocal local = EJBFactory.getSalesResultForStatistic();
SalesResultForStatisticVO vo = new SalesResultForStatisticVO();
AttachmentToCrmLocal attach = EJBFactory.getAttachmentToCrm();
AttachmentVO atta_vo = new AttachmentVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer pre_product_type = Utility.parseInt(request.getParameter("pre_product_type"),new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal rg_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("rg_money")),new BigDecimal(0));
BigDecimal dz_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("dz_money")),new BigDecimal(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
String dz_time = Utility.trimNull(request.getParameter("dz_date"));
Integer is_onway = Utility.parseInt(request.getParameter("is_onway"), new Integer(0));
boolean bSuccess = false;
Map map = new HashMap();
Map atta_map = new HashMap();

if(request.getMethod().equals("POST")){
	DocumentFileToCrmDB file = new DocumentFileToCrmDB(pageContext);
	file.parseRequest();

	serial_no = Utility.parseInt(file.getParameter("serial_no"),new Integer(0));
	dz_time = Utility.trimNull(file.getParameter("dz_date"));
	dz_money = Utility.parseDecimal(Utility.trimNull(file.getParameter("dz_money")),new BigDecimal(0));
	jk_type = Utility.trimNull(file.getParameter("jk_type"));
	is_onway = Utility.parseInt(file.getParameter("is_onway"), new Integer(0));
	
	String rg_cust_name = Utility.trimNull(file.getParameter("rg_cust_name"));
	String rg_product_name = Utility.trimNull(file.getParameter("rg_product_name"));
	Integer rg_cust_type = Utility.parseInt(file.getParameter("rg_cust_type"), new Integer(0));
	Integer rg_product_type = Utility.parseInt(file.getParameter("rg_product_type"), new Integer(0));
	String rg_product_type_name = Argument.getSalecChangesResultProductTypeName(rg_product_type+"");
	
	Integer rg_service_man = Utility.parseInt(file.getParameter("rg_service_man"),new Integer(0));

	vo.setSerial_no(serial_no);
	vo.setDz_time(dz_time);
	vo.setDz_money(dz_money);
	vo.setJk_type(jk_type);
	vo.setInput_man(input_operatorCode);
	vo.setIs_onway(is_onway);
	
	vo.setCust_name(rg_cust_name);
	vo.setRg_product_name(rg_product_name);
	vo.setRg_cust_type(rg_cust_type);
	vo.setPre_product_type(rg_product_type);
	vo.setPre_product_type_name(rg_product_type_name);
	vo.setRg_service_man(rg_service_man);
	
	local.modiSalesResultForStatistic(vo);

	file.uploadAttchment_crm(new Integer(1102),"TSALESRESULTFORSTATISTIC",serial_no,"",input_operatorCode); // 1102-TSALESRESULTFORSTATISTIC

	bSuccess = true;

}else{
	if (serial_no.intValue() > 0){
		vo.setSerial_no(serial_no);
		vo.setPre_product_type(pre_product_type);
		List list = local.querySalesResultForStatistic(vo);
		if (list.size() > 0) map = (Map)list.get(0);

		atta_vo.setDf_talbe_id(new Integer(3));
		atta_vo.setDf_serial_no(serial_no);
		atta_vo.setInput_man(input_operatorCode);
		List atta_list = attach.load(atta_vo);
		if (atta_list.size()>0) atta_map = (Map)atta_list.get(0);
	}
}
//本次最多能换金额

local.remove();
attach.remove();
%>
<html>
<head>
<title>到账处理</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<base target="_self"/>
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function validateForm(form){
	if(!sl_check(form.rg_product_name, "产品名称 ", 100, 1))	return false;//产品名称
	if(!sl_checkChoice(document.theform.rg_product_type,"产品类型 ")) return false;//产品类型
	if(!sl_check(form.rg_cust_name, "<%=LocalUtilis.language("class.customerName",clientLocale)%> ", 100, 1))	return false;//客户名称
	if(!sl_checkChoice(document.theform.is_deal_picker,"<%=LocalUtilis.language("class.custType",clientLocale)%> ")) return false;//客户类型
	if (!sl_checkChoice(form.rg_service_man, "客户经理")) return false;
	if (! sl_check(document.getElementById('dz_date'), "到账时间", 23, 1)) return false;
	if (!sl_checkDecimal(document.theform.dz_money, "到账金额", 13, 3, 1)) return false;
	if(document.theform.dz_money.value <= 0){
		sl_alert("到账金额不能小于等于零！");
		return false;
	}

	if (!sl_checkChoice(form.jk_type, "缴款方式")) return false;
	return sl_check_update();
}

function _checked(obj) {
	document.getElementById("onway_attach").style.display = obj.checked?"":"none";
}

function confirmRemoveAttach(serial_no){
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>？'))	{
		var updatehref = 'attach_crm_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		document.getElementById("divattach").style.display = "none";
		document.theform.onway_attach.style.display = "";		
	}
}
var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' class='xpbutton3' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='移除'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}
/*查看附件方法*/
function DownloadAttachment(attachmentId){
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}

//删除数据库中已有附件的方法
function deleteDbAttachment(attachmentId,save_name){
    if (confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //确认删除附件吗
		var url = "sales_changes_result_attachment.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name
			+"&serial_no=<%=serial_no%>&pre_product_type=<%=pre_product_type%>";
		document.getElementById("go-link").href = url;
		document.getElementById("go-link").click();	
    }
}
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<a id="go-link"></a>
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="pre_money" value="<%=pre_money%>">
<input type=hidden name="rg_money" value="<%=rg_money%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>到账处理</b></font></td>
	</tr>
	<br/>
</table>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right"><font color="red">*</font>产品名称:</td>
		<td align="left">
			<input type="text" name="rg_product_name" class="edline" size="30" value="<%=Utility.trimNull(map.get("RG_PRODUCT_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>产品分类:</td>
		<td align="left">
			<select name="rg_product_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getSalecChangesResultProductTypeOptions(Utility.trimNull(map.get("PRE_PRODUCT_TYPE"))) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>客户名称:</td>
		<td align="left">
			<input type="text" name="rg_cust_name" class="edline" size="30" value="<%=Utility.trimNull(map.get("RG_CUST_NAME")) %>">
		</td>
	</tr>
	<tr>
		<td align="right" width="20%"><font color="red">*</font>客户类型:</td>
		<td width="25%">
			<select name="rg_cust_type" id="is_deal_picker" onkeydown="javascript:nextKeyPress(this)" style="width:160px">	
				<option value="0">请选择</option>
				<option value="1" <%=Utility.parseInt(Utility.trimNull(map.get("RG_CUST_TYPE")), 0) == 1 ? "selected" : ""%>>个人</option>
				<option value="2" <%=Utility.parseInt(Utility.trimNull(map.get("RG_CUST_TYPE")), 0) == 2 ? "selected" : ""%>>机构</option>
			</select>
			<input readonly="readonly" class="edline" name="input_value2" size="27" style="display: none;" id="customerType">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>客户经理:</td>
		<td align="left">
			<select name="rg_service_man" style="width: 147px">
				<%=Argument.getManager_Value(Utility.parseInt(Utility.trimNull(map.get("RG_SERVICE_MAN")),null)) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>到账时间:</td>
		<td align="left">
			<input type="text" name="dz_date" id="dz_date" size="30" onclick="javascript:setday(this);" readOnly value="<%=Utility.trimNull(map.get("DZ_TIME"))%>"/> 	
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>到账金额:</td>
		<td align="left">
			<input type="text" name="dz_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,dz_money_cn)" value="<%=Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1") %>">
			<br><span id="dz_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>缴款方式:</td>
		<td align="left">
			<select name="jk_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1114, Utility.trimNull(map.get("JK_TYPE"))) %>
			</select>
		</td>
	</tr>
<%
AttachmentToCrmLocal attachmentLocal = EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO = new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(1102)); // 
attachmentVO.setInput_man(input_operatorCode);
List attachmentList =attachmentLocal.load(attachmentVO);
for (int i=0; i<attachmentList.size(); i++) {
	Map attachmentMap = (Map)attachmentList.get(i);
	String attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
	String origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
   	String save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME")); %>
	<tr>
		<td align="right">附件<%=i+1%>：</td>
		<td align="left">
			<a href="javascript:DownloadAttachment(<%=attachmentId%>);" style="width:200px;text-align:right"><%=origin_name%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" id="btn_DelDbAttachment" name="btn_DelDbAttachment" onclick="javascript:deleteDbAttachment('<%=attachmentId%>','<%=save_name%>');">删除附件</button>
		</td>
	</tr>
<%} 
attachmentLocal.remove();%>	
	<tr>
		<td align="right" valign="top"><input type="button" class="xpbutton6" onclick="addline()" value="添加更多附件" style="line-height:15px;margin-top:5px;"/></td>	
		<td>
			<div>
            	<div style="float:left">
	            	<table id="test" style="width:190px;" ><tr ><td><input type="file" name="file_info" size="45">&nbsp;</td></tr></table>
            	</div>
            <div>
		</td>
	</tr>
</table>

<table border="0" width="100%">
	<tr>
		<td align="center">
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--保存-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
		&nbsp;&nbsp;<!--取消-->
		</td>
	</tr>
</table>
</form>
</body>
</html>