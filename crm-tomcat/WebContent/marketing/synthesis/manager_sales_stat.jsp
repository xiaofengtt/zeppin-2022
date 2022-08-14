<%@ page language="java" pageEncoding="GBK"%>
<%@ page import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
ContractLocal contract = EJBFactory.getContract(); 
Integer productId = Utility.parseInt(request.getParameter("productId"), new Integer(0));
Integer startDate = Utility.parseInt(request.getParameter("startDate"), new Integer(0));
Integer endDate = Utility.parseInt(request.getParameter("endDate"), new Integer(0));
List list = contract.statManagerSales(productId, startDate, endDate, new Integer(0), input_operatorCode);

String options = Argument.getProductListOptions(new Integer(1), productId,"",input_operatorCode, 0);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
	initQueryCondition();
};	

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.productId.options.length;i++)
		{
			if(document.theform.productId.options[i].text.substring(0,j)==value)
			{
				document.theform.productId.options[i].selected=true;
				prodid = document.theform.productId.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.productId.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value) {
	prodid=0;
	if (value != "") {
        j = value.length;
		for(i=0;i<document.theform.productId.options.length;i++) {
			if(document.theform.productId.options[i].text.substring(0,j)==value) {
				document.theform.productId.options[i].selected=true;
				prodid = document.theform.productId.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.productId.options[0].selected=true;	
		} 
		document.theform.productId.focus();					
	}	
}

function StartQuery() {
	syncDatePicker(document.theform.startDatePicker,document.theform.startDate);
	syncDatePicker(document.theform.endDatePicker,document.theform.endDate);
	document.theform.submit();
}

function showStatistics(servMan, productId) {
	showModalDialog("<%=request.getContextPath()%>/marketing/synthesis/servman_statistics.jsp?servMan="+servMan+"&productId="+productId, 
					'','dialogWidth:800px;dialogHeight:600px;status:0;help:0');
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='productId' style='width: 320px;' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.productId.options.length;i++){
			var j = document.theform.productId.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.productId.options[i].text);
				list1.push(document.theform.productId.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
			document.theform.productid.value="";
			document.theform.productId.options[0].selected=true;
		}else{
			document.theform.productId.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.productId.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.productId.focus();
	}else{
		document.theform.productId.options[0].selected=true;
	}
}
</script>
</HEAD>

<BODY>
<%@ include file="/includes/waiting.inc"%> 
<form name="theform">

<!--查询功能模块-->
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td align="left"><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
   <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
		<tr>
			<td  align="right" style="width: 90px;">合同签署日期 从 : </td>
			<td  align="left">
				<input type="text" name="startDatePicker" class=selecttext style="width: 99px;" value="<%=Format.formatDateLine(startDate)%>">
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.startDatePicker,theform.startDatePicker.value,this);" tabindex="13">
				<input TYPE="hidden" name="startDate" id="startDate" />	
			</td>						
		</tr>
		<tr>
			<td  align="right" style="width: 90px;">到 : </td>
			<td  align="left">
				<input type="text" name="endDatePicker" class=selecttext style="width: 99px;" value="<%=Format.formatDateLine(endDate)%>">
				<input TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.endDatePicker,theform.endDatePicker.value,this);" tabindex="13">
				<input TYPE="hidden" name="endDate" id="endDate" />	
			</td>						
		</tr>
		<tr>	
			<td  align="right">产品编号 : </td>
			<td>
				<input type="text" maxlength="16" name="productid" value="" 
					onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10"> &nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
			</td>		
			<td align="right">产品名称 :</td>
			<td>
				<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
			</td>	
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> : </td><!-- 产品选择 -->
			<td align="left" colspan=3 id="select_id">
				<SELECT name="productId" style="width: 320px;" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductListOptions(new Integer(1), productId,"",input_operatorCode, 0)%><%-- 0全部；11推介及正常期产品--%>
				</SELECT>
			</td>	
		</tr>
	<tr>
		<td align="center" colspan="4">		
			<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
			<!--确认-->	
		</td>
	</tr>
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 border=0 width="75%">
	<TR>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD height="100%">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan=2><IMG src="/images/member.gif" border=0 width="32" height="28"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align="left"></td>
						<td align="right">
							<button type="button"  class="xpbutton3" accessKey="q" id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<hr noshade color="#808080"  size="1">
						</td>
					</tr>
				</table>

				<%--table border="2" cellspacing="0" width="480px" align="center" style="border-collapse:collapse"--%>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" align="center" class="tablelinecolor" width="480px">
					<tr class="trh" style="ie2:expression(onclick=function(){});ie3:expression(onmouseover=function(){})">
						<TD align="center" width="150px">部门</TD>
						<TD align="center" width="150px">客户经理</TD>
						<TD align="center" width="180px">销售金额</TD>	
					</tr>
				<%
				Map m = new HashMap(); // 部门名称－>部门人数

				int nDeptEmps = 0;
				for (int i=0; i<list.size(); i++) {
					Map map = (Map)list.get(i);
					String deptName = Utility.trimNull(map.get("DEPART_NAME"));
					String servManName = Utility.trimNull(map.get("SERVICEMAN_NAME"));

					if ("小计".equals(servManName)) {
						m.put(deptName, new Integer(nDeptEmps));
						nDeptEmps = 0;
					} else {
						nDeptEmps ++;
					}
				}

				boolean bNextDept = true;
				for (int i=0; i<list.size(); i++) {
					Map map = (Map)list.get(i);					
					String deptName = Utility.trimNull(map.get("DEPART_NAME"));
					String servManName = Utility.trimNull(map.get("SERVICEMAN_NAME"));
					Integer servMan = (Integer)map.get("SERVICEMAN");
					BigDecimal rgMoney = (BigDecimal)map.get("RG_MONEY");
				%>
					<% // 注意if、else if的次序，当servManName是"合计"的时候
					if ("小计".equals(servManName) || "合计".equals(servManName)) {%>
					<tr class="tr0">
						<td align="center">&nbsp;</td>
					<%
					} else if (bNextDept) {%>
					<tr class="tr1">
						<td align="center" rowspan="<%=m.get(deptName)%>"><b><%=deptName%></b></td>
 					<% 
					} else {%>
					<tr class="tr1">
					<%
					} %>
						<td align="center">
							<%=("小计".equals(servManName) || "合计".equals(servManName))?
								"":"<a href='javascript:showStatistics("+servMan+","+productId+")'>"%>
							<%=servManName%>
							<%=("小计".equals(servManName) || "合计".equals(servManName))?
								"":"</a>"%>
						</td>
						
						<td align="right"><%=Format.formatMoney2(rgMoney)%></td>
					</tr>
				<%
					if ("小计".equals(servManName)) 
						bNextDept = true;
					else 
						bNextDept = false;
				}%>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</TD>
</TR>
</TABLE>
</form>
<script language="javascript">
showWaitting(0);
//document.body.onbeforeunload = function (){
//									showWaitting(1);
//								};
</script>
<%--@ include file="/includes/foot.inc"--%>
</BODY>
</HTML>
<%
contract.remove();
%>
