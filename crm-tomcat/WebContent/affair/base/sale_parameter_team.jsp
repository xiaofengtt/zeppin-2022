<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer sub_productId = Utility.parseInt(request.getParameter("sub_productId"),new Integer(0));

ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
List list = null;
Map map = null;
String productCode = "";
String productJc = "";
BigDecimal preMoney = new BigDecimal(0);
Integer preNum = new Integer(0);
BigDecimal minMoney = new BigDecimal(0);
Integer preStartDate = null;
Integer preEndDate = null;
String team_name = "";
Integer team_id = null;
BigDecimal team_money = new BigDecimal(0);//父团队销售配额
BigDecimal quota_money = new BigDecimal(0);//销售配额
BigDecimal quota_money_total = new BigDecimal(0);//总销售配额
Integer quota_qualified_num = new Integer(0);//合格投资人数量配额
int	quota_qualified_num_total = 0;//总合格投资人数量配额
//Integer team_q_num = new Integer(0);//父团队合格投资人数量配额

if(sub_productId.intValue()==0){
	if(productId.intValue()!=0){
		vo.setProduct_id(productId);
	list = product.load(vo);
	map = (Map)list.get(0);
	productCode = Utility.trimNull(map.get("PRODUCT_CODE"));
	productJc = Utility.trimNull(map.get("PRODUCT_JC"));
	preMoney = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0),2,"1");
	preNum = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
	minMoney = Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")), new BigDecimal(0),2,"1");
	preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
	preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
	}
}else{
	vo.setProduct_id(productId);
	vo.setSub_product_id(sub_productId);
	list = product.listSubProduct(vo);
	map = (Map)list.get(0);
	preMoney = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0),2,"1");
	preNum = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
	minMoney = Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")), new BigDecimal(0),2,"1");
	preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
	preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
}
sUrl=request.getContextPath()+"/affair/base/sale_parameter_team.jsp?productId="+productId.toString()+"&sub_productId="+sub_productId.toString();
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv="Content-Style-Type" content="text/css">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" /> 
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>

<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script language=javascript>

window.onload=function(){
	if(<%=sub_productId.intValue()%>!=0){
		document.getElementById("sub_product_flag").style.display="";
	}
};
//查看团队成员
	function showTeamInfo(team_name){
		var _event = window.event.srcElement;
		var url = "<%=request.getContextPath()%>/marketing/base/team_member.jsp?team_id="+arguments[0];
		if(showModalDialog(url,'', 'dialogWidth:360px;dialogHeight:350px;status:0;help:0')){
			sl_update_ok();
			location='<%=sUrl%>';
		}	
	}

//设置团队配额
	function showInfo(productId,team_id,team_name,sub_productId,quota_money,quota_qualified_num){
		if(showModalDialog('<%=request.getContextPath()%>/marketing/base/sale_parameter_team_QuotaUpdate.jsp?productId=' + productId + '&sub_productId='+ sub_productId +'&team_id='
							 + team_id+'&team_name='+team_name +'&quota_money='+quota_money+'&quota_qualified_num='+quota_qualified_num, '', 'dialogWidth:380px;dialogHeight:350px;status:0;help:0') != null)
		{
		sl_update_ok();
		location='<%=sUrl%>';
		}
	}

function selectProduct(product_id){
	if(product_id>0){
		utilityService.getSubProductFlag(product_id,function(data){
				var arrayL = data.split("$");
				var sub_product_flag = arrayL[0];
				product_id = DWRUtil.getValue("productId");				
				if(sub_product_flag==1){
					document.getElementById("sub_product_flag").style.display = "";
					utilityService.getSubProductOptionS(product_id,0,function(data1){
						$("select_id").innerHTML = "<select size='1' name='sub_productId' onkeydown='javascript:nextKeyPress(this)' class='productname' onchange='javascript:selectSubProduct(this.value)';>"+
						data1+"</select>&nbsp;&nbsp;";
					});
				}else{
					document.getElementById("sub_productId").value = 0 ;
					document.getElementById("theform").submit();
				}	
			}
		);
	}else{
		sl_alert("产品不存在,请重新选择!");
		return false;
	}
}

function selectSubProduct(sub_product_id){
	if(sub_product_id>0){
		document.getElementById("theform").submit();
	}
}


</script>
<TITLE></TITLE>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="post" action="sale_parameter_team.jsp">
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
		<hr noshade color="#808080" size="1">
	</div>
	<div>
		<table  border="0" width="100%" cellspacing="4" cellpadding="2" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
			<tr>
				<td align="right">产品名称 :&nbsp;&nbsp;</td>
				<td align="left">
					<select size="1" name="productId" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProduct(this.value);" class="productname">
						<option>请选择 </option><!--请选择-->
						<%=Argument.getProductListOptions(input_bookCode,productId,"",input_operatorCode,25)%>
					</select>
				</td>
			</tr>
			<tr id="sub_product_flag" style="display:none;">
				<td align="right">子产品名称 :&nbsp;&nbsp;</td>
				<td align="left" id="select_id">
					<select size="1" id="sub_productId" name="sub_productId" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectSubProduct(this.value);">
						<%=Argument.getSubProductOptions(productId, new Integer(0),sub_productId)%>			
					</select>
				</td>
			</tr>
			<%if(sub_productId.intValue()==0){ %>
			<tr>
				<td  align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :&nbsp;&nbsp;</td><!--产品编号-->
				<td align="left" >
					<input type="text" id="productcode" name="productcode" readonly="readonly" class="edline" value="<%=productCode %>" onkeydown="javascript:setProduct(this.value);" size="30">
				</td>
				<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品简称-->
				<td  align="left">
					<input type="text" id="productJC" name="productJC" readonly="readonly" class="edline" value="<%=productJc %>" size="30">
				</td>
			</tr>
			<%} %>
			<tr>
				<td align="right" width="120px">预发行份数 :&nbsp;&nbsp;</td>
				<td>
					<input type="text" name="preNum" readonly="readonly" class="edline" value="<%=preNum %>">
				</td>
			</tr>
			<tr>
				<td align="right" width="120px">预发行金额 :&nbsp;&nbsp;</td>
				<td>
					<input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=preMoney %>">
				</td>
				<td align="right" width="120px">最低发行金额 :&nbsp;&nbsp;</td>
				<td>
					<input type="text" name="minMoney" readonly="readonly" class="edline" value="<%=minMoney %>">
				</td>
			</tr>
			<tr>
				<td align="right" width="120px"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> :&nbsp;&nbsp;</td><!--推介起始日期-->
				<td>				
					<INPUT TYPE="text" NAME="preStartDate" readonly="readonly" class="edline" value="<%=Format.formatDateLine(preStartDate) %>">
				</td>			
	
				<td align="right" width="120px"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> :&nbsp;&nbsp;</td><!--推介终止日期-->
				<td>					
					<INPUT TYPE="text" NAME="preEndDate" readonly="readonly" class="edline" value="<%=Format.formatDateLine(preEndDate) %>">
				</td>					
			</tr>
		</table>
<br/>
			<div >
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class=trtagsort>
							<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--团队名称-->
							<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
							<td align="center">已销售配额</td><!--销售配额-->
							<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
							<td align="center">已购投资人数</td><!--合格投资人数量配额-->
							<td align="center"><%=LocalUtilis.language("class.TeamMember",clientLocale)%> </td><!--团队成员-->
							<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
						</tr>
<%//查看团队销售信息

	SaleParameterVO salevo = new SaleParameterVO();
	SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();
	int iCount = 0;
	int iCurrent = 0;
	int t_sPage = Utility.parseInt(sPage,1);
	int t_sPagesize = Utility.parseInt(sPagesize,12);
	String[] totalColumn = new String[0];
	Iterator iterator = null; 
		
	//salevo.setTeamID(team_id);
	//salevo.setProductID(productId);
	List  listTTeam = sale_parameter.queryTTeamValue(productId);

	for(int i=0;i<listTTeam.size();i++){
		iCount++;
		map = (Map)listTTeam.get(i);
		quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
		quota_money_total = quota_money_total.add(quota_money);
		BigDecimal already_quota_money = Utility.parseDecimal(Utility.trimNull(map.get("ALREADYSALE")), new BigDecimal(0),2,"1");
		team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
		team_name = Utility.trimNull(map.get("TEAM_NAME"));
		quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
		quota_qualified_num_total = quota_qualified_num_total + quota_qualified_num.intValue() ;
		Integer already_quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
	 %>
						<tr class="tr0">
							<td align="center"><%=team_name %><input type="hidden" name="team_id" value="<%=team_id%>"></td>
							<td align="center"><%=Format.formatMoney(quota_money) %></td>
							<td align="center"><%=Format.formatMoney(already_quota_money) %></td>
							<td align="center"><%=quota_qualified_num %></td>
							<td align="center"><%=already_quota_qualified_num %></td>
				            <td align="center">
				            	<button type="button" class="xpbutton2" name="btnEdit" onclick="javascript:showTeamInfo('<%=Utility.trimNull(map.get("TEAM_ID"))%>');">&gt;&gt;</button>
				            </td>  
							<td align="center" width="50px">
								<%if (input_operator.hasFunc(menu_id, 102)) {%>
				               	<a href="#" onclick="javascript:showInfo(<%=productId %>,<%=team_id %>,'<%=team_name %>',<%=sub_productId %>,<%=quota_money %>,<%=quota_qualified_num %>);">
					           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
					           	</a>
								<%} %>
							</td>
						</tr>
	<%} %>

					<tr class="trbottom">
						<!--合计--><!--项-->
				        <td class="tdh" align="left">&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center"><%=Format.formatMoney(quota_money_total) %></td>
						<td align="center"></td>
						<td align="center"><%=quota_qualified_num_total %></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
				</table>
			</div>
	<br>
	</div>
</form>
<p>
<%
sale_parameter.remove();
product.remove();
%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
