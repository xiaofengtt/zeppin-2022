<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.web.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
PreMoneyDetailLocal local = EJBFactory.getPreMoneyDetail();
PreMoneyDetailVO vo = new PreMoneyDetailVO();
AttachmentToCrmLocal attach = EJBFactory.getAttachmentToCrm();
AttachmentVO atta_vo = new AttachmentVO();

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
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

	vo.setSerial_no(serial_no);
	vo.setDz_time(dz_time);
	vo.setDz_money(dz_money);
	vo.setJk_type(jk_type);
	vo.setInput_man(input_operatorCode);
	vo.setIs_onway(is_onway);
	local.modiPreMoneyDetail(vo);

	file.uploadAttchment(new Integer(3), "TPREMONEYDETAIL", serial_no, "", input_operatorCode);

	bSuccess = true;

}else{
	if (serial_no.intValue() > 0){
		vo.setSerial_no(serial_no);
		List list = local.queryPreMoneyDetail(vo);
		if (list.size() > 0) map = (Map)list.get(0);

		atta_vo.setDf_talbe_id(new Integer(3));
		atta_vo.setDf_serial_no(serial_no);
		atta_vo.setInput_man(input_operatorCode);
		List atta_list = attach.load(atta_vo);
		if (atta_list.size()>0) atta_map = (Map)atta_list.get(0);
	}
}
//本次最多能换金额
String yh_money_cn = Utility.trimNull(Format.formatMoney(pre_money.subtract(rg_money.subtract(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")))));
BigDecimal yh_money = pre_money.subtract(rg_money.subtract(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")));

local.remove();
attach.remove();
%>
<html>
<head>
<title>到账处理</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
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
	if (! sl_check(document.getElementById('dz_date'), "到账时间", 23, 1)) return false;
	if (!sl_checkDecimal(document.theform.dz_money, "到账金额", 13, 3, 1)) return false;
	if(document.theform.dz_money.value <= 0){
		sl_alert("到账金额不能小于等于零！");
		return false;
	}

	if(parseFloat(form.dz_money.value) > parseFloat(form.yh_money.value)){
		sl_alert("本次到账金额不能大于最大金额："+form.yh_money_cn.value+"？");
		return false;
	}
	if (!sl_checkChoice(form.jk_type, "交款方式")) return false;
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
</script>
</head>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="#" onsubmit="javascript:return validateForm(this);" enctype="multipart/form-data">
<input type=hidden name="serial_no" value="<%=serial_no%>">
<input type=hidden name="cust_name" value="<%=cust_name%>">
<input type=hidden name="product_name" value="<%=product_name%>">
<input type=hidden name="pre_money" value="<%=pre_money%>">
<input type=hidden name="rg_money" value="<%=rg_money%>">
<input type=hidden name="yh_money_cn" value="<%=yh_money_cn%>">
<input type=hidden name="yh_money" value="<%=yh_money%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td class="page-title"><font color="#215dc6"><b>到账处理</b></font></td>
	</tr>
	<br/>
</table>
<table border="0" width="100%" cellspacing="3" class="product-list">
	<tr>
		<td align="right">产品名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(product_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">客户名称:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(cust_name) %>">
		</td>
	</tr>
	<tr>
		<td align="right">预约金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(pre_money)) %>">
		</td>
	</tr>
	<tr>
		<td align="right">已到账金额:</td>
		<td align="left">
			<input type="text" readonly="readonly" class="edline" size="30" value="<%=Utility.trimNull(Format.formatMoney(rg_money.subtract(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")))) %>">
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
			<input type="text" name="dz_money" size="30" onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value,dz_money_cn)" value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONEY")), new BigDecimal(0.00),2,"1")) %>">
			<br><span id="dz_money_cn" class="span"></span>
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>交款方式:</td>
		<td align="left">
			<select name="jk_type" style="width: 175px;" onkeydown="javascript:nextKeyPress(this)">
				<%=Argument.getDictParamOptions(1114, Utility.trimNull(map.get("JK_TYPE"))) %>
			</select>
		</td>
	</tr>
	<tr>
		<td align="right">在途资金:</td>
		<td align="left">
			<input type="checkbox" name="is_onway" value="1" <%=Utility.parseInt((Integer)map.get("ONWAY_FLAG"), new Integer(0)).intValue()==1?"checked":""%> 
				onclick="javascript:_checked(this);" />是
		</td>
	</tr>	

	<tr id="onway_attach" style="display:<%=Utility.parseInt((Integer)map.get("ONWAY_FLAG"), new Integer(0)).intValue()==1?"":"none"%>">
		<td align="right">相关附件:</td>
		<td align="left">
			<div id="divattach" style="display:<%=atta_map.isEmpty()?"none":""%>">
        		<a title="查看附件" target="_blank" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(atta_map.get("SAVE_NAME")),"\\","/")%>&name=<%=Utility.trimNull(atta_map.get("ORIGIN_NAME"))%>">
					<%=Utility.trimNull(atta_map.get("ORIGIN_NAME"))%></a>
	        	&nbsp;&nbsp;&nbsp;&nbsp;
	        	<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
	        		onclick="javascript:confirmRemoveAttach('<%=Utility.trimNull(atta_map.get("ATTACHMENTID"))%>$<%=Utility.replaceAll(Utility.trimNull(atta_map.get("SAVE_NAME")),"\\","/")%>');">删除</button>
	        </div>
			<input type="file" name="onway_attach" style="display:<%=!atta_map.isEmpty()?"none":""%>"/>
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