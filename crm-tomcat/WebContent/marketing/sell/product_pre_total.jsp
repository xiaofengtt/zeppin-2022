<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer q_pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("q_pre_product_id")),new Integer(0));
PreContractCrmLocal preContract = EJBFactory.getPreContractCrm();
PreContractCrmVO pre_vo = new PreContractCrmVO();
pre_vo.setPre_product_id(q_pre_product_id);
pre_vo.setStatus("110202");
pre_vo.setInput_man(input_operatorCode);
IPageList pre_pageList = preContract.getProductPreList(pre_vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,10));
List list = pre_pageList.getRsList();
Iterator iterator = list.iterator();	

//分页参数
int iCount = 0;

preContract.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("class.productMarketing",clientLocale)%></TITLE><!--在售产品预约情况统计-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
</HEAD>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

function refreshPage(){
	disableAllBtn(true);
	location.search = '?page=1&pagesize=' + document.theform.pagesize.value
		 +'&q_pre_product_id='+document.theform.q_pre_product_id.value;
}

function StartQuery() {
	refreshPage();
}

/*返回*/
function CancelAction(){
	var url = "<%=request.getContextPath()%>/login/main.jsp?display_flag=1";
	<%if(Argument.getSyscontrolValue("USERNEWPORTAL")==1){%>
		url = "<%=request.getContextPath()%>/login/main_3.jsp?display_flag=1";
	<%}%>
	location.href = url;	
}

function showProductInfo(preproduct_id){
	var url = "<%=request.getContextPath()%>/wiki/product_info_view.jsp?preproduct_id="+preproduct_id;
	window.open(url);
}

//查看产品预约统计信息
function showTeamInfo(pre_product_id, pre_product_name, pre_money){
	showModalDialog('<%=request.getContextPath()%>/marketing/sell/product_pre_total_team.jsp?pre_product_id=' + pre_product_id + '&pre_product_name=' + pre_product_name + '&pre_money=' + pre_money, '', 'dialogWidth:900px;dialogHeight:600px;status:0;help:0')
}

//直接预约
function reservAddInfo(pre_product_id){
	location = "<%=request.getContextPath()%>/marketing/sell/direct_reserve_add.jsp?pre_product_id="+pre_product_id;
}
</script>
<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="POST">
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
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
		<td>
			<select size="1" name="q_pre_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
				<%=Argument.getPreProductListOptions(q_pre_product_id,"","110202",input_operatorCode)%>
			</select>		
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
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=LocalUtilis.language("class.productMarketing",clientLocale)%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--查询-->(<u>F</u>)</button>&nbsp;&nbsp;&nbsp; 
	</div>
	<br/>
</div>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc"%>
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>
			<TR>
				<TD>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
					<tr class="trh">
						<td align="center" width="*">产品名称</td>
						<td align="center" width="*">发行规模</td>
						<td align="center" width="*">预约开始时间</td>
						<td align="center" width="*">预约截止时间</td>
						<td align="center" width="*">期限</td>
						<td align="center" width="*">发行方式</td>
						<td align="center" width="*">已预约规模</td>
						<td align="center" width="*">到账规模</td>
						<td align="center" width="*">在途资金规模</td>
						<td align="center" width="*">销售平均成本</td>
						<td align="center" width="*">小额份数</td>
						<td align="center" width="*">剩余预约规模</td>
						<td align="center" width="*">大额预约有效数/大额预约总数</td>
						<td align="center" width="*">操作</td>
					</tr>
<%
for(int i=0;i< list.size();i++){
	Map preMap = (Map)list.get(i);
	Integer preproduct_id = Utility.parseInt(Utility.trimNull(preMap.get("PREPRODUCT_ID")),new Integer(0));
	double pre_max_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_MONEY")),new BigDecimal(0),2,"1").doubleValue();
	double pre_money = Utility.parseDecimal(Utility.trimNull(preMap.get("PRE_FACT_MONEY")),new BigDecimal(0),2,"1").doubleValue();
	double rg_money = Utility.parseDecimal(Utility.trimNull(preMap.get("RG_MONEY")),new BigDecimal(0),2,"1").doubleValue();
	double onway_money = Utility.parseDecimal(Utility.trimNull(preMap.get("ONWAY_MONEY")),new BigDecimal(0),2,"1").doubleValue();
	double sell_cost = Utility.parseDecimal(Utility.trimNull(preMap.get("SELL_COST")),new BigDecimal(0),2,"1").doubleValue();
	double available_money = pre_max_money - pre_money;
	int direct_sale = Utility.parseInt(Utility.trimNull(preMap.get("DIRECT_SALE")),0);
	BigDecimal max_ratio = Utility.parseBigDecimal(Utility.stringToDouble(Utility.trimNull(preMap.get("MAX_RATIO"))), new BigDecimal(0)).setScale(6, BigDecimal.ROUND_HALF_UP);
	BigDecimal stand_ratio = new BigDecimal(0.800000); %>
					<tr class="tr<%=(iCount%2)%>">
						<td align="left" width="*"><a href="#" class="a2" title="查看产品信息" onclick="javascript:showProductInfo('<%=preproduct_id%>');" ><%=Utility.trimNull(preMap.get("PREPRODUCT_NAME"))%></a></td>
						<td align="right" width="*"><%=Format.formatMoney(pre_max_money)%></td>
						<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_START_DATE")),new Integer(0)))%></td>
						<td align="center" width="*"><%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(preMap.get("PRE_END_DATE")),new Integer(0)))%></td>	
						<td align="center" width="*"><%=Utility.trimNull(preMap.get("PERIOD"))%></td>
						<td align="center" width="*"><%if(direct_sale == 1) out.print("代销");else out.print("直销");%></td>
						<td align="right" width="*"><a href="#" class="a2" title="查看产品预约统计信息" onclick="javascript:showTeamInfo('<%=preproduct_id%>','<%=java.net.URLEncoder.encode(Utility.trimNull(preMap.get("PREPRODUCT_NAME")))%>','<%=Utility.trimNull(Format.formatMoney(pre_money))%>');" ><font color="<%=max_ratio.compareTo(stand_ratio) == 1 ? "red" : "" %>"><%=Format.formatMoney(pre_money)%></font></a></td>
						<td align="right" width="*"><%=Format.formatMoney(rg_money)%></td>
						<td align="right" width="*"><%=Format.formatMoney(onway_money)%></td>
						<td align="right" width="*"><%=Format.formatMoney(sell_cost)%></td>
						<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("LESS300_NUMBER")),new Integer(0))%></td>
						<td align="right" width="*"><%=Format.formatMoney(available_money)%></td>
						<td align="center" width="*"><%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_VALID_COUNT")),0)%> / <%=Utility.parseInt(Utility.trimNull(preMap.get("HUGE_COUNT")),0)%></td>
						<td align="center" width="*">
	       					<a href="#" class="a2" onclick="javascript:reservAddInfo('<%=preproduct_id %>');" title="产品预约">预约</a>
						</td>
					</tr>	
<%	
	iCount++;
}
for (int i=0;i < pre_pageList.getBlankSize(); i++){%>
					<tr class="trbottom">
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>	
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%}%>
					<tr class="trbottom">
						<td class="tdh" align="left" colspan="14"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pre_pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b></td>
					</tr>	
			
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pre_pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</td>
		</tr>		
		<tr>
        	<td align=right>             		
        	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button>
			&nbsp;&nbsp;&nbsp;<!--返回-->
        	</td>
        </tr>
	</table>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>