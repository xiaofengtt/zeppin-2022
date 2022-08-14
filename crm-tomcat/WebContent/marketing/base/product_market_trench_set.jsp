<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量
try{
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String productCode = Utility.trimNull(request.getParameter("productcode"));
String productName = Utility.trimNull(request.getParameter("productName"));
String risk_level = Utility.trimNull(request.getParameter("risk_level"));
Integer intrust_flag1 = Utility.parseInt(request.getParameter("intrust_flag1"),new Integer(0));
Integer team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),new Integer(0));

Integer r_channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
String r_channel_name = Utility.trimNull(request.getParameter("r_channel_name"));
BigDecimal fareRate = Utility.parseDecimal(Utility.trimNull(request.getParameter("fareRate")), new BigDecimal(0),4,"0.01");
Integer preStartDate = Utility.parseInt(request.getParameter("preStartDate"),new Integer(0));
Integer preEndDate = Utility.parseInt(request.getParameter("preEndDate"),new Integer(0));
BigDecimal pre_max_rate = Utility.parseDecimal(Utility.trimNull(request.getParameter("pre_max_rate")), new BigDecimal(0),4,"0.01");
String pre_code = Utility.trimNull(request.getParameter("pre_code"));


//信息显示辅助变量
Integer qualified_flag = new Integer(1);
Integer qualified_num = new Integer(0);
Integer asfund_flag = new Integer(1);
Integer gain_flag = new Integer(1) ;
Integer open_gain_flag = new Integer(2);
BigDecimal qualified_money = new BigDecimal(0);
Integer is_bank_consign = new Integer(2); //是否银行代销 1是、2否 默认否
BigDecimal jg_min_subamount = new BigDecimal(0); 
BigDecimal gr_min_subamount = new BigDecimal(0); 
BigDecimal jg_min_bidsamount = new BigDecimal(0); 
BigDecimal gr_min_bidsamount = new BigDecimal(0); 
BigDecimal jg_min_appbidsamount = new BigDecimal(0); 
BigDecimal gr_min_appbidsamount = new BigDecimal(0); 
BigDecimal min_redeem_vol = new BigDecimal(0); 
Integer large_redeem_flag = new Integer(2);
Integer large_redeem_condition = new Integer(1);
BigDecimal large_redeem_percent = new BigDecimal(0); 
Integer large_redeem_deal = new Integer(1);
Integer coerce_redeem_flag = new Integer(2);
Integer sub_flag = new Integer(0);//子产品标志 0无1有
String styleStr = "none" ;
if(qualified_flag.intValue()==1){
	styleStr = "" ;
}
String productJC = "";
BigDecimal preMoney = new BigDecimal(0);
Integer preNum = new Integer(0);
BigDecimal minMoney = new BigDecimal(0);
Integer periodUnit = new Integer(0);
String team_name = "";
BigDecimal quota_money = new BigDecimal(0);//销售配额
Integer quota_qualified_num = new Integer(0);//合格投资人数量配额
String valid_period = "";


//获得对象
ProductVO vo = new ProductVO();
ProductLocal product = EJBFactory.getProduct();

//页面变量
List listProduct = null;
Map map = null;
List listIssue = null;
Map map_issue = null;
List list_crm_product = null;
Map map_issue_crm = null;
boolean bSuccess = false ;
String error_mess = "";
List markList = null;
Map markMap = null;

if(!request.getMethod().equals("POST")){

	//productId不等于0，则查询查询单条产品信息
	if(productId.intValue()!= 0){
		//产品信息
		vo.setProduct_id(productId);
		listProduct = product.load(vo);
		if(listProduct.size() >0){
			map = (Map)listProduct.get(0);
		}
		
		if(intrust_flag1.intValue() == 2){//是否为集合产品
			Utility.debug(vo.getProduct_id());
			listIssue = product.queryProductLimit(vo);
			if(listIssue.size() >0){
				map_issue = (Map)listIssue.get(0);
			}
		}
		
		//风险等级
		vo.setBook_code(new Integer(1));
		vo.setStart_date(new Integer(0));
		vo.setEnd_date(new Integer(0));
		vo.setProduct_status("");
		vo.setCheck_flag(new Integer(0));
		vo.setOpen_flag(new Integer(0));
		vo.setIntrust_flag1(new Integer(0));
		vo.setProduct_id(productId);
		vo.setProduct_name("");
		vo.setProduct_code("");
		vo.setSale_status(new Integer(0));
		vo.setInput_man(input_operatorCode);
		list_crm_product = product.listCrmProduct(vo);
		if(list_crm_product.size()>0){
			map_issue_crm = (Map)list_crm_product.get(0); 
		}
		
		//产品销售渠道信息
		markList = product.queryMarketTrench(vo);
	}
	//产品信息
	if(map!= null){
		productCode = Utility.trimNull(map.get("PRODUCT_CODE"));
		productName = Utility.trimNull(map.get("PRODUCT_NAME"));
		productJC = Utility.trimNull(map.get("PRODUCT_JC"));
		preMoney = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0),2,"1");
		preNum = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
		minMoney = Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")), new BigDecimal(0),2,"1");
		preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
		preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
		periodUnit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
		intrust_flag1= Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG1")),new Integer(0));
		valid_period = Utility.trimNull(map.get("VALID_PERIOD"));
		pre_code =  Utility.trimNull(map.get("PRE_CODE"));	
		pre_max_rate = Utility.parseDecimal(Utility.trimNull(map_issue_crm.get("PRE_MAX_RATE")), new BigDecimal(0),4,"100");
		pre_max_rate = pre_max_rate.setScale(2,BigDecimal.ROUND_HALF_UP);
		sub_flag = Utility.parseBit(Utility.trimNull(map.get("SUB_FLAG")));
	}
}
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.saleParameterSet",clientLocale)%></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 

<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>

<script language="javascript">
	
function newInfo()
{	
	if(showModalDialog('product_market_trench_add.jsp?productId=<%=productId%>&sub_flag=<%=sub_flag%>&productName=<%=productName%>&sub_product_id=<%=sub_product_id%>', '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

function showInfo(serial_no)
{	
	if(showModalDialog('product_market_trench_modi.jsp?serial_no='+serial_no+'&productId=<%=productId%>&sub_flag=<%=sub_flag%>&productName=<%=productName%>', '', 'dialogWidth:400px;dialogHeight:400px;status:0;help:0') != null)
	{
		sl_update_ok();
		location.reload();
	}
}

//返回
function CancelAction(){
	window.location.href="sale_parameter.jsp?";
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="product_market_trench_remove.jsp">
<input type="hidden" name="sub_product_id" value="<%=sub_product_id%>">
<input type="hidden" name="productId" value="<%=productId%>">
<input type="hidden" name="intrust_flag1" value="<%=intrust_flag1%>">
<input type="hidden" name="sub_flag" value="<%=sub_flag%>">
<input type="hidden" name="createChild" value="">
<input type="hidden" id="channel_id" name="channel_id" value="<%=r_channel_id%>"/>

	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%>>>销售渠道设置</b></font>
	</div>
	<div class="direct-panel">
	<FIELDSET><LEGEND>产品信息</LEGEND>
		<table  border="0" width="100%" cellspacing="4" cellpadding="2">
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--产品编号-->
				<td align="left" >
					<input type="text" name="productcode" readonly="readonly" class="edline" value="<%=productCode%>" onkeydown="javascript:setProduct(this.value);" size="30">
				</td>
			</tr>
			<tr>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品名称-->
				<td  align="left">
					<input type="text" name="productName" readonly="readonly" class="edline" value="<%=productName%>" onkeydown="javascript:setProduct(this.value);" size="60">
				</td>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品简称-->
				<td  align="left">
					<input type="text" name="productJC" readonly="readonly" class="edline" value="<%=productJC%>" onkeydown="javascript:setProduct(this.value);" size="40">
				</td>
			</tr>
			<tr>				
				<td align="right" width="120px"><%=LocalUtilis.language("class.preMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--预期发行金额-->
				<td><input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(preMoney) %>" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" size="30"> </td>							
				<td align="right" width="120px"><%=LocalUtilis.language("class.preNum",clientLocale)%> :&nbsp;&nbsp;</td><!--预期发行份数-->
				<td><input type="text" name="preNum" readonly="readonly" class="edline" value="<%=preNum%>" size="40"></td>			
			</tr>
			<tr>				
				<td align="right" width="120px"><%=LocalUtilis.language("class.minMoney",clientLocale)%> :&nbsp;&nbsp;</td><!--最低发行金额-->
				<td><input type="text" name="minMoney" readonly="readonly" class="edline" value="<%=Format.formatMoney(minMoney) %>" onkeyup="javascript:showCnMoney(this.value,rg_money_cn)" size="30"></td>			
			</tr>
		</table>
	</FIELDSET>
	<br/>
		<FIELDSET><LEGEND>销售渠道信息</LEGEND>
			<div style="width: 100%; text-align: right; height: 30px;">
				<button type="button"  class="xpbutton3"   accessKey=n id="btnNew" name="btnNew" title="<%=LocalUtilis.language("message.newRecord",clientLocale)%> " onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button> <!--新建记录-->
				&nbsp;&nbsp;&nbsp;<!--新建-->
				<button type="button"  class="xpbutton3" accessKey=d id="btnDelete" name="btnDelete" title="<%=LocalUtilis.language("message.delRecordsSelect",clientLocale)%> " onclick="javascript:if(confirmRemove(document.theform.serial_no)) document.theform.submit();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
				&nbsp;&nbsp;&nbsp;<!--删除-->
				<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
				&nbsp;&nbsp;&nbsp;
			</div>
			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class=trtagsort>
						<%if(sub_flag.intValue() == 1){ %>
						<td align="center"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);">子产品名称</td>
						<td align="center" width="10%">渠道类型</td>					
						<%}else{ %>
						<td align="center" width="10%"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.serial_no);">渠道类型</td>
						<%} %>
						<td align="center" width="10%">渠道名称</td>
						<td align="center" width="10%">渠道销售费率(%)</td>
						<td align="center" width="50%">备注</td>
						<td align="center" width="10%">修改</td><!--编辑-->
					</tr>
					<%
					if(markList != null && markList.size() != 0){
						for(int i=0; i<markList.size(); i++){
							markMap = (Map)markList.get(i);
					%>
					<tr class="tr<%=(i % 2)%>">
						<%if(sub_flag.intValue() == 1){ %>
						<td align="center"><input type="checkbox" name="serial_no" value="<%=Utility.parseInt(Utility.trimNull(markMap.get("SERIAL_NO")),new Integer(0)) %>" class="flatcheckbox"><%=Utility.trimNull(markMap.get("LIST_NAME")) %></td>
						<td align="center" width="10%"><%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %></td>					
						<%}else{ %>
						<td align="left">
							<input type="checkbox" name="serial_no" value="<%=Utility.parseInt(Utility.trimNull(markMap.get("SERIAL_NO")),new Integer(0)) %>" class="flatcheckbox">
							<%=Utility.trimNull(markMap.get("CHANNEL_TYPE_NAME")) %>
						</td>
						<%} %>
						<td align="left"><%=Utility.trimNull(markMap.get("CHANNEL_NAME")) %></td>
						<td align="right"><%=Utility.trimNull(Utility.parseDecimal(Utility.trimNull(markMap.get("CHANNEL_FARE_RATE")), new BigDecimal(0),4,"100")) %></td>
						<td align="left"><%=Utility.trimNull(markMap.get("SUMMARY")) %></td>
						<td align="center">
			               	<a href="#" onclick="javascript:showInfo(<%=Utility.parseInt(Utility.trimNull(markMap.get("SERIAL_NO")),new Integer(0)) %>);">
				           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
				           	</a>
						</td>
					</tr>
					<%
						}
					}%>
				</table>
			</div>
		</FIELDSET>
	<br>
	</div>
</form>
<p>
<%
product.remove();
}catch(Exception e){throw e ;}
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
