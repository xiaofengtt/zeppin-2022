<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> 
<%@ include file="/includes/operator.inc" %>

<%
try{
//清除session
session.removeAttribute("product_sub");
session.removeAttribute("product_stage_item");
session.removeAttribute("product_city");
session.removeAttribute("product_date");
session.removeAttribute("product_rate");
session.removeAttribute("product_sub");


String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
String sQuery = request.getParameter("t1");

Integer depart_id = Utility.parseInt(request.getParameter("depart_id"),new Integer(0)); 
String invest_field = Utility.trimNull(request.getParameter("invest_field")); 
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
vo.setBook_code(input_bookCode);
vo.setInput_man(input_operatorCode);
vo.setDepart_id(depart_id);
vo.setIntrust_flag1(new Integer(2));// 只筛选集合类的产品
vo.setInvest_field(invest_field);
IPageList pageList = null;
String[]totalColumn = {"PRE_MONEY","FACT_MONEY"};
if (sQuery == null)
{
	sQuery = " $ $110202$ $ ";
	pageList = product.query(sQuery,vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}
else
{
	pageList = product.query(sQuery,vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
}
String[] sQueryParse = product.parseQuery(sQuery);
String sUrl = "product_limit.jsp?pagesize=" + sPagesize + "&t1=" + sQuery;

ProductAddLocal local = EJBFactory.getProductAdd();
ProductAddVO aVo = new ProductAddVO();
int rows=0;
aVo.setBookcode(input_bookCode);
List listA = local.list(aVo);
rows=listA.size();
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
initQueryCondition()
};

function refreshPage()
{
	if(!sl_check(document.theform.t4, '<%=LocalUtilis.language("class.productID",clientLocale)%> ', 6, 0))	return false;//产品编号
	document.theform.btnQuery.disabled = true;
	disableAllBtn(true);
	location = 'product_limit.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value +'&depart_id='+document.theform.depart_id.value+'&invest_field='+document.theform.invest_field.value+'&t1=' + document.theform.t1.value + "$" + document.theform.t2.value 
				+ "$" + document.theform.t3.value + "$" + document.theform.t4.value+ "$" + document.theform.product_name.value;
}

function StartQuery()
{
	refreshPage();
}

function setInfo(product_id){
	<%if(Argument.getSyscontrolValue_intrust("TA_FLAG") == 1){%>
	if(showModalDialog('product_set.jsp?product_id=' + product_id , '','dialogWidth:800px;dialogHeight:600px;STATUS=0;help=0;')!=null){
		sl_update_ok();
   	 	location.reload();
   	}
	<%}else{%>
	if(showModalDialog('product_set.jsp?product_id=' + product_id , '','dialogWidth:800px;dialogHeight:350px;STATUS=0;help=0;')!=null){
		sl_update_ok();
   	 	location.reload();
   	}
	<%}%>	
}

function typeChange(invest_field,product_id){
	if(showModalDialog('invest_field_set.jsp?invest_field=' + invest_field + '&product_id=' + product_id , '','dialogWidth:250px;dialogHeight:170px;STATUS=0;help=0;')==1){
		sl_update_ok();
   	 	location.reload();
	}
	showWaitting(0);
}
</script>
</HEAD>
<BODY class="BODY body-nox"><%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="get" action="product_limit.jsp">
<input type="hidden" name="rows" value="<%=rows%>">
<div id="queryCondition" class="qcMain" style="display:none;width:460px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>
<table>
	<tr>
		<td align="right"><select style="display:none" size="1" name="t1" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getIntrustTypeOptions(sQueryParse[0])%>
		</select><%=LocalUtilis.language("class.currency_name",clientLocale)%> : </td><!-- 币种 -->

		<td><select size="1" name="t2" onkeydown="javascript:nextKeyPress(this)" >
			<%=Argument.getCurrencyOptions(sQueryParse[1])%>
		</select></td>
		<td> <%=LocalUtilis.language("class.productStatus",clientLocale)%> : </td><!-- 产品状态 -->
		<td align=left><select size="1" name="t3" onkeydown="javascript:nextKeyPress(this)">
			<%=Argument.getProductAllStatusOptions(sQueryParse[2])%>
		</select></td>
		
		<tr>
			<td align="right"> <%=LocalUtilis.language("class.productID",clientLocale)%> : </td><!-- 产品编号 -->
			<td><input type="text" name="t4" onkeydown="javascript:nextKeyPress(this)" size="15" value=<%=sQueryParse[3]%>>
			</td><td><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!-- 产品名称 -->
			<td><input type="text" name="product_name" onkeydown="javascript:nextKeyPress(this)" size="15" value=<%=sQueryParse[4]%>>
			</td>
		</tr>
		<tr>	
			<td><%=LocalUtilis.language("class.departName",clientLocale)%> :</td><!-- 设计部门 -->
			<td>
				<select size="1" name="depart_id" onkeydown="javascript:nextKeyPress(this)" style="width: 150px">
					<%=Argument.getDepartOptions(depart_id)%>
				</select>
			</td>
			<td>CRM投资领域 :</td>
			<td>
				<select size="1" name="invest_field">
					<%=Argument.getDictParamOptions(1139,invest_field)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan=4>
			<!-- 确定 -->
                        <button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok2",clientLocale)%> (<u>O</u>)</button>
			</td>
	</tr></table>
</div>
<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="2" class="page-title"><b><%=menu_info%></b></td>
					</tr>
					<tr>
						<td align="right">
							<div class="btn-wrapper">
								<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button><!-- 查询 -->
							</div>
							<br/>
						</td>
					</tr>
					
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					<tr class="trh">
                        <!-- 编号 -->
						<td rowspan=2 ><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.selectbox,this);">&nbsp;&nbsp;<%=LocalUtilis.language("class.ID",clientLocale)%> </td>
						<td rowspan=2 ><%=LocalUtilis.language("class.name",clientLocale)%> </td><!-- 名称 -->
						<td colspan=2 ><%=LocalUtilis.language("class.expectedSize",clientLocale)%> </td><!--预期规模-->
						<td colspan=2 ><%=LocalUtilis.language("class.jgMoney",clientLocale)%> </td><!--实际规模-->
						<td rowspan=2 ><%=LocalUtilis.language("class.serviceStatusName2",clientLocale)%> </td><!--状态-->
						<td rowspan=2 >发行设置</td><!-- 设置 -->
						<%if(user_id.intValue()==25 || user_id.intValue()==999){ %>
						<td rowspan=2 >信托投资领域</td>
						<td rowspan=2 >CRM投资领域</td>
						<%} %>
					</tr>
					<tr class="trh">	
						<td ><%=LocalUtilis.language("class.factNum",clientLocale)%> </td><!--份数-->
						<td ><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--金额-->
						<td ><%=LocalUtilis.language("class.factNum",clientLocale)%> </td><!--份数-->
						<td ><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--金额-->
					</tr>
				
<%int iCount = 0;
int iCurrent = 0;
List listP = pageList.getRsList();
for(;iCount<listP.size();iCount++,iCurrent++)
{
	Map map = (Map)listP.get(iCount);
	int check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),0); 
	Integer product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	String product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	String pre_num = Utility.trimNull(map.get("PRE_NUM"));
	BigDecimal pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0.00));
	String fact_num = Utility.trimNull(map.get("FACT_NUM"));
	BigDecimal fact_money = Utility.parseDecimal(Utility.trimNull(map.get("FACT_MONEY")),new BigDecimal(0.00));
	String product_status_name = Utility.trimNull(map.get("PRODUCT_STATUS_NAME"));
	Integer intrust_type2 = Utility.parseInt(Utility.trimNull(map.get("INTRUST_TYPE2")),new Integer(0));
	String intrust_type2_name = Utility.trimNull(map.get("INTRUST_TYPE2_NAME"));
	invest_field = Utility.trimNull(map.get("INVEST_FIELD"));
	String invest_field_name = Utility.trimNull(map.get("INVEST_FIELD_NAME"));
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" width="9%">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td>
								<%if(check_flag==1){%>
								<input type="checkbox" name="selectbox" value="<%=product_id%>" class="flatcheckbox">
								<%}%>
								</td>
								<td width="90%" align="center"><%=Utility.trimNull(product_code)%></td>
							</tr>
						</table>
						</td>
						<td align="left" ><%=Utility.trimNull(product_name)%></td>
						<td align="right" ><%=Utility.trimNull(pre_num)%></td>
						<td align="right" ><%=Utility.trimNull(pre_money)%></td>
						<td align="right" ><%=Utility.trimNull(fact_num)%></td>
						<td align="right" ><%=Utility.trimNull(fact_money)%></td>
						<td align="center" ><%=Utility.trimNull(product_status_name)%></td>
						<td align="center" >
						<button type="button"  class="xpbutton2" onclick="javascript:setInfo('<%=product_id%>');">&gt;&gt;</button>
						</td>
						<%if(user_id.intValue()==25 || user_id.intValue()==999){ %>
						<td align="center"><%=intrust_type2_name %></td>
						<td align="center"><a href="javascript:typeChange('<%=invest_field%>',<%=product_id%>)"><%if("".equalsIgnoreCase(invest_field_name)){out.print("未设置");}else{out.print(invest_field_name);} %></a></td>
						<%} %>
					</tr>
<%
}

for (; iCurrent < pageList.getPageSize(); iCurrent++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
						<td class="tdh" width="9%"></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<%if(user_id.intValue()==25 || user_id.intValue()==999){ %>
						<td align="center" ></td>
						<td align="center" ></td>
						<%} %>
					</tr>
					<%}
%>
					<tr class="trbottom">
						<!--合计--><!-- 项 -->
                        <td class="tdh" align="center"><b><%=LocalUtilis.language("message.total",clientLocale)%><%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<td align="right" ><%=Utility.trimNull(pageList.getTotalValue("PRE_MONEY"))%></td>
						<td align="center" ></td>
						<td align="right" ><%=Utility.trimNull(pageList.getTotalValue("FACT_MONEY"))%></td>
						<td align="center" ></td>
						<td align="center" ></td>
						<%if(user_id.intValue()==25 || user_id.intValue()==999){ %>
						<td align="center" ></td>
						<td align="center" ></td>
						<%} %>
					</tr>
				</table>

				<br>

				<table border="0" width="100%" class="page-link">
					<tr valign="top"> 
						<td><%=pageList.getPageLink(sUrl)%></td>
					
					</tr>
				</table>

				</TD>
			</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%product.remove();
}catch(Exception e){throw e;}%>
