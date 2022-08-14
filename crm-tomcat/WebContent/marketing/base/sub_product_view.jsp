<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.marketing.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
Integer sProduct_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
//声明辅助变量
boolean bSuccess = false;
input_bookCode = new Integer(1);
String display_text = "class='edline' readonly";
String display_select = "disabled";
//获得对象
ProductLocal productLocal = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

Integer list_id = Utility.parseInt(request.getParameter("list_id"), new Integer(0));
String list_name = Utility.trimNull(request.getParameter("list_name"));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
String prov_level_name = Utility.trimNull(request.getParameter("prov_level_name"));
Integer pre_num = Utility.parseInt(request.getParameter("pre_num"), new Integer(0));
BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_money")),new BigDecimal(0));
BigDecimal min_money = Utility.parseDecimal(Utility.trimNull(request.getParameter("min_money")),new BigDecimal(0));
BigDecimal exp_rate1 = Utility.parseDecimal(Utility.trimNull(request.getParameter("exp_rate1")),new BigDecimal(0));
BigDecimal exp_rate2 = Utility.parseDecimal(Utility.trimNull(request.getParameter("exp_rate2")),new BigDecimal(0));

Integer valid_period = Utility.parseInt(request.getParameter("valid_period"), new Integer(0));
Integer period_unit = Utility.parseInt(request.getParameter("period_unit"), new Integer(3));

BigDecimal result_standard = Utility.parseDecimal(request.getParameter("result_standard"),new BigDecimal(0));
Integer start_date = Utility.parseInt(request.getParameter("start_date"),new Integer(0));
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag"), new Integer(1));
int check_flag = Utility.parseInt(request.getParameter("check_flag"),1);
int flag = 0;

vo.setSub_product_id(sub_product_id);
List rsList = productLocal.listSubProduct(vo);

if(rsList!=null&&rsList.size()>0){
	HashMap rsMap = (HashMap)rsList.get(0);
	list_id = Utility.parseInt(Utility.trimNull(rsMap.get("LIST_ID")), new Integer(0));
	list_name = Utility.trimNull(rsMap.get("LIST_NAME"));
	prov_level = Utility.trimNull(rsMap.get("PROV_LEVEL"));
	prov_level_name = Utility.trimNull(rsMap.get("PROV_LEVEL_NAME"));
	pre_num =  Utility.parseInt(Utility.trimNull(rsMap.get("PRE_NUM")), new Integer(0));
	pre_money = Utility.parseDecimal(Utility.trimNull(rsMap.get("PRE_MONEY")),new BigDecimal(0));
	min_money = Utility.parseDecimal(Utility.trimNull(rsMap.get("MIN_MONEY")),new BigDecimal(0));
	exp_rate1 = Utility.parseDecimal(Utility.trimNull(rsMap.get("EXP_RATE1")),new BigDecimal(0));
	exp_rate1 = exp_rate1.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
	exp_rate2 = Utility.parseDecimal(Utility.trimNull(rsMap.get("EXP_RATE2")),new BigDecimal(0));
	exp_rate2 = exp_rate2.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
	valid_period = Utility.parseInt(Utility.trimNull(rsMap.get("VALID_PERIOD")), new Integer(0));
	period_unit = Utility.parseInt(Utility.trimNull(rsMap.get("PERIOD_UNIT")), new Integer(3));
	result_standard  = Utility.parseDecimal(Utility.trimNull(rsMap.get("RESULT_STANDARD")),new BigDecimal(0));
	start_date = Utility.parseInt(Utility.trimNull(rsMap.get("START_DATE")), new Integer(0));
	prov_flag = Utility.parseInt(Utility.trimNull(rsMap.get("PROV_FLAG")), new Integer(0));
	check_flag = Utility.parseInt(Utility.trimNull(rsMap.get("CHECK_FLAG")),0);
	sProduct_id = Utility.parseInt(Utility.trimNull(rsMap.get("PRODUCT_ID")), new Integer(0));
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>子产品查看</title>
<base target="_self">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
</HEAD>
<BODY class="BODY body-nox" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post">
<input type="hidden" name="sub_product_id" value="<%=sub_product_id%>">
<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=center width="100%">
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=center width="100%">
						<table border="0" width="100%"   cellspacing="0" cellpadding="0" height="74" >
							<tr>
								<td class="page-title"><b><font color="#215dc6">子产品信息</font></b></td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3">
							<%if(check_flag==1){%>
							<tr>
						    	<td align="right">产品编号:</td >
						        <td align="left" colspan="3">
						        	<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="13">&nbsp;
						        	<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
						        </td >
						    </tr>
							<%}%>
							<tr>
							    <td align="right">产品选择:</td >
							    <td align="left" colspan="3" >
							      	<SELECT <%=display_select%> style="width:425px;" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" >
							       		<%=Argument.getProductListOptions(input_bookCode, sProduct_id,"",input_operatorCode,12)%>
							      	</SELECT>
							    </td>
							</tr>
							<tr>
								<td align="right">子产品名称:</td>
								<td colspan="3">
								    <input <%=display_text%> onkeydown="javascript:nextKeyPress(this)"  style="width:425px" name="list_name"  value="<%=list_name%>">
								</td>
							</tr>
							<tr>
								<td align="right">开始日期:</td>
								<td>
									<INPUT TYPE="text" <%=display_text%> NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>">
									<INPUT TYPE="button" <%=display_select%> value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" tabindex="13">
									<INPUT TYPE="hidden" NAME="start_date" value="">
								</td>
								<td align="right">子产品期限:</td>	
							    <td>
							      	<input <%=display_text%> name="valid_period" style="width:50px"  type="text" value="<%=valid_period%>">&nbsp;						      
							      	<select <%=display_select%> size="1"  style="width:63px" name="period_unit" style="WIDTH: 60px" onChange="javascript:changeStyle(this)" onkeydown="javascript:nextKeyPress(this)">
								    	<%=Argument.getPeriodValidUnitOptions(period_unit)%>
							      	</select>
							    </td>
							</tr>
							<tr>
								<td align="right">发行份数:</td>
								<td >
								    <input <%=display_text%> onkeydown="javascript:nextKeyPress(this)"  size="20" name="pre_num" <%if(flag==0) {%> value="1" <%}else{%> value="<%=pre_num%>" <%}%> >份
								</td>
								<td align="right" >预期发行金额:</td>
								<td >
								   	<input <%=display_text%> name="pre_money"  size="20" maxlength="17" <%if(flag==0) {%> value="0" <%}else{%> value="<%=Format.formatMoney0(pre_money)%>" <%}%> onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney(this.value)">
								</td>
							</tr>
							<tr>
								<td colspan=3></td>
								<td align="left"><span id="pre_money_cn" class="span"></span></td>								
							</tr>
							<tr>							
								<td align="right">最低发行金额:</td>
								<td colspan="">
								  	<input <%=display_text%> name="min_money" size="20"  maxlength="17" <%if(flag==0) {%> value="0" <%}else{%> value="<%=Format.formatMoney0(min_money)%>" <%}%> onkeydown="javascript:nextKeyPress(this)" onkeyup="javascript:showCnMoney2(this.value)">
								</td>
								<td align="right">预期收益:</td>
								<td >
								    <input <%=display_text%> name="exp_rate1" style="width:40px" value="<%=Utility.trimZero(exp_rate1)%>" onkeydown="javascript:nextKeyPress(this)">% 到 
									<input <%=display_text%> name="exp_rate2" style="width:40px"  value="<%=Utility.trimZero(exp_rate2)%>" onkeydown="javascript:nextKeyPress(this)">%
								</td>
							</tr>
							<tr>
								<td></td>						
								<td align="left"><span id="min_money_cn" class="span"></span></td>
								<td colspan=2></td>
							</tr>
							<tr>			
								<td align="right">受益标志:</td>
								<td>
									<SELECT name="prov_flag" style="width:120px;">
										<OPTION  value="1"  <%if(prov_flag.intValue()==1)  out.print("selected");%>>优先</OPTION>
										<OPTION  value="2"  <%if(prov_flag.intValue()==2)  out.print("selected");%>>一般</OPTION>
										<OPTION  value="3"  <%if(prov_flag.intValue()==3)  out.print("selected");%>>劣后</OPTION>
									</SELECT>
								</td>
								<td align="right">受益级别:</td>
								<td >
								    <SELECT <%=display_select%> style="width:110px" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									  	<%=Argument.getProvlevelOptions(prov_level)%>
								    </SELECT>
								</td>
							</tr>
							<tr>
								<td align="right">业绩基准:</td>
								<td><input <%=display_text%> name="result_standard" size="20" <%if(flag==0) {%> value="0" <%}else{%> value="<%=Utility.trimZero(result_standard)%>" <%}%> onkeydown="javascript:nextKeyPress(this)"></td>
							</tr>
							
						</table>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<%if(check_flag==1){%>								
								<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) {disableAllBtn(true);document.theform.submit();}">保存(<u>S</u>)</button>
								&nbsp;&nbsp;
								<%}%>	
								<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">关闭(<u>C</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>	
</BODY>
</HTML>