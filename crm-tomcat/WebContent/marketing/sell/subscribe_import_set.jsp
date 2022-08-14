<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.customer.CustomerLocal"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
String column=Utility.trimNull(request.getParameter("column"));
String[] col = column.split("\\$");	
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.addActivity",clientLocale)%> </title>
<!--新增活动管理-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<script language=javascript>
window.onload = function(){
	var arr = document.getElementsByTagName("INPUT");
	<%for(int i=0;i<col.length;i++){%>
		for(var j=0; j<arr.length; j++){
			if(arr[j].value=="<%=col[i]%>"){
				arr[j].checked=true;
			}
		}
	<%}%>
}

/*保存*/
function SaveAction(){
	var arr = document.getElementsByTagName("INPUT");
	var list = [];
	for (var i=0; i<arr.length; i++){
		if (arr[i].checked){
			list.push(arr[i].value);
		}
	}
	window.returnValue = list;
	window.close();
}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="subscribe_import_set.jsp" >
<div align="left" class="page-title">
	<font color="#215dc6"><b>编辑显示列 </b></font>
</div>

<div align="center">
	<table border="0" width="98%"  cellspacing="0" cellpadding="4" class="table-popup">
		<tr>
			<td  align="left" width="40%"><input type="checkbox" name="contract_sign_date" value="合同签订日期">合同签订日期</td>
			<td  align="left" width="40%"><input type="checkbox" name="pay_date" value="缴款日期">缴款日期</td>
		</tr>		
		<tr>
			<td  align="left"><input type="checkbox" name="contract_end_date" value="合同截止日期">合同截止日期</td>
			<td  align="left"><input type="checkbox" name="contract_deadline" value="合同期限">合同期限</td>				
		</tr>		
		<tr>
			<td  align="left"><input type="checkbox" name="contract_money" value="合同金额">合同金额</td>
			<td  align="left"><input type="checkbox" name="cust_name" value="委托人">委托人</td>	
		</tr>
		
		<tr>
			<td  align="left"><input type="checkbox" name="cust_type" value="委托人类型">委托人类型</td>
			<td  align="left"><input type="checkbox" name="cust_contact_type" value="委托人联系方式">委托人联系方式</td>	
		</tr>	
		<tr>
			<td align="left"><input type="checkbox" name="cust_address" value="委托人地址">委托人地址</td>
			<td  align="left"><input type="checkbox" name="cust_post_code" value="委托人邮编">委托人邮编</td>
		</tr>	
		<tr>
			<td align="left"><input type="checkbox" name="cust_certificate" value="委托人证件名称">委托人证件名称</td>
			<td  align="left"><input type="checkbox" name="cust_certificate_num" value="委托人证件编号">委托人证件编号</td>
		</tr>
		<tr>
			<td align="left"><input type="checkbox" name="cust_legal_representative" value="委托人法人代表">委托人法人代表</td>
			<td align="left"><input type="checkbox" name="cust_line_tel" value="委托人固定电话">委托人固定电话</td>
		</tr>					
		<tr> 
			<td align="left"><input type="checkbox" name="cust_telphone" value="委托人手机">委托人手机</td>
			<td align="left"><input type="checkbox" name="cust_email" value="委托人电子邮件">委托人电子邮件</td>
		</tr>	
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary" value="受益人">受益人</td>
			<td align="left"><input type="checkbox" name="beneficiary_type" value="受益人类型">受益人类型</td>
		</tr>	
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_contact_type" value="受益人联系方式">受益人联系方式</td>
			<td align="left"><input type="checkbox" name="beneficiary_address" value="受益人地址">受益人地址</td>
		</tr>	
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_post_code" value="受益人邮编">受益人邮编</td>
			<td align="left"><input type="checkbox" name="beneficiary_certificate" value="受益人证件名称">受益人证件名称</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_certificate_num" value="受益人证件编号">受益人证件编号</td>
			<td align="left"><input type="checkbox" name="beneficiary_legal_representative" value="受益人法人代表">受益人法人代表</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_line_tel" value="受益人固定电话">受益人固定电话</td>
			<td align="left"><input type="checkbox" name="beneficiary_telphone" value="受益人手机">受益人手机</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="beneficiary_email" value="受益人电子邮件">受益人电子邮件</td>
			<td align="left"><input type="checkbox" name="bank_name" value="开户银行名称">开户银行名称</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="sub_bank_name" value="支行名称">支行名称</td>
			<td align="left"><input type="checkbox" name="bank_acct" value="银行账号">银行账号</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="acct_name" value="开户户名">开户户名</td>
			<td align="left"><input type="checkbox" name="property_category" value="财产类别">财产类别</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="property_name" value="财产名称">财产名称</td>
			<td align="left"><input type="checkbox" name="pay_type" value="缴款方式">缴款方式</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="subscribe_pay_type" value="认购费扣缴方式">认购费扣缴方式</td>
			<td align="left"><input type="checkbox" name="beneficiary_level" value="受益优先级别">受益优先级别</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="income_level" value="收益级别">收益级别</td>
			<td align="left"><input type="checkbox" name="cust_reserve_address" value="委托人备用地址">委托人备用地址</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="cust_reserve_post_code" value="委托人备用邮编">委托人备用邮编</td>
			<td align="left"><input type="checkbox" name="new_contract_no" value="新合同编号">新合同编号</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="product_periods" value="产品期数">产品期数</td>
			<td align="left"><input type="checkbox" name="channel_type" value="渠道类别">渠道类别</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="channel_name" value="渠道名称">渠道名称</td>
			<td align="left"><input type="checkbox" name="channel_reserve" value="渠道备注">渠道备注</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="province" value="省">省</td>
			<td align="left"><input type="checkbox" name="city" value="市">市</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="cust_manager" value="客户经理">客户经理</td>
			<td align="left"><input type="checkbox" name="money_mark" value="保证金标志">保证金标志</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="channel_cooperation_type" value="渠道合作方式">渠道合作方式</td>
			<td align="left"><input type="checkbox" name="comment" value="备注">备注</td>
		</tr>
		<tr> 
			<td align="left"><input type="checkbox" name="money_origin_name" value="资产来源">资产来源</td>
			<td align="left"><input type="checkbox" name="sub_money_origin_name" value="资产来源细分">资产来源细分</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();">确定 </button>
	&nbsp;&nbsp;&nbsp;&nbsp;
</div>
</form>

</BODY>
</HTML>