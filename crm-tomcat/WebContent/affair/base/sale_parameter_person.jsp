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
Integer opCode = null;
String opName = "";
String productCode = "";
String productJc = "";
Integer preStartDate = null;
Integer preEndDate = null;
String team_name = "";
Integer team_id = null;
BigDecimal team_money = new BigDecimal(0);//父团队销售配额
BigDecimal quota_money = new BigDecimal(0);//销售配额
Integer quota_qualified_num = new Integer(0);//合格投资人数量配额
Integer team_q_num = new Integer(0);//父团队合格投资人数量配额

if(sub_productId.intValue()==0){
	if(productId.intValue()!=0){
		vo.setProduct_id(productId);
	list = product.load(vo);
	map = (Map)list.get(0);
	productCode = Utility.trimNull(map.get("PRODUCT_CODE"));
	productJc = Utility.trimNull(map.get("PRODUCT_JC"));
	preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
	preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
	}	
}else{
	vo.setProduct_id(productId);
	vo.setSub_product_id(sub_productId);
	list = product.listSubProduct(vo);
	map = (Map)list.get(0);
	preStartDate = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
	preEndDate = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
}
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

//设置团队配额
	function showInfo(productId,sub_productId,team_id,op_code,op_name,quota_money,quota_qualified_num){
		var url='<%=request.getContextPath()%>/affair/base/sale_parameter_person_QuotaUpdate.jsp?productId=' + productId + '&sub_productId='+ sub_productId +'&team_id=' + team_id+'&op_code='+op_code+'&op_name='+op_name;
		url=url+'&quota_money='+quota_money+'&quota_qualified_num='+quota_qualified_num;
		if(showModalDialog(url, '', 'dialogWidth:380px;dialogHeight:350px;status:0;help:0') != null)
		{
		sl_update_ok();
		location="sale_parameter_person.jsp?productId="+productId+"&sub_productId="+sub_productId;
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
<form id="theform" name="theform" method="post" action="sale_parameter_person.jsp">
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
						<%=Argument.getProductListOptions(input_bookCode,productId,"",input_operatorCode,25)%>
					</select>
				</td>
			</tr>
			<tr id="sub_product_flag" style="display:none;">
				<td align="right">子产品名称 :&nbsp;&nbsp;</td>
				<td align="left" id="select_id">
					<select size="1" name="sub_productId" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:selectSubProduct(this.value);">
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
							<td align="center">个人名称</td>
							<td align="center">所在团队</td>
							<td align="center">可分配销售配额</td>
							<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
							<td align="center">已销售配额</td><!--销售配额-->
							<td align="center">可分配数量配额</td>
							<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
							<td align="center">已购投资人数</td><!--合格投资人数量配额-->
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
	
	if(productId.intValue()==0){
		salevo.setProductID(new Integer(-1));
	}else{
		salevo.setProductID(productId);
	}	
	salevo.setSub_product_id(sub_productId);
	salevo.setInput_man(input_operatorCode);
	List  listTTeam = sale_parameter.queryPersonValue(salevo);
	//list = pageList.getRsList();
	for(int i=0;i<listTTeam.size();i++){
		iCount++;
		map = (Map)listTTeam.get(i);
		opCode = Utility.parseInt(Utility.trimNull(map.get("OP_CODE2")),new Integer(0));
		opName = Utility.trimNull(map.get("OP_NAME"));
		team_money = Utility.parseDecimal(Utility.trimNull(map.get("TEAMMONEY")), new BigDecimal(0),2,"1");
		quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
		BigDecimal already_quota_money = Utility.parseDecimal(Utility.trimNull(map.get("ALREADYSALE")), new BigDecimal(0),2,"1");
		team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
		team_name = Utility.trimNull(map.get("TEAM_NAME"));
		team_q_num = Utility.parseInt(Utility.trimNull(map.get("TEAMQNUM")),new Integer(0));
		quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
		Integer already_quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
	 %>
						<tr class="tr0">
							<td align="center"><%=opName %><input type="hidden" name="op_code" value="<%=opCode%>"></td>
							<td align="center"><%=team_name %><input type="hidden" name="team_id" value="<%=team_id%>"></td>
							<td align="center"><%=Format.formatMoney(team_money) %></td>
							<td align="center"><%=Format.formatMoney(quota_money) %></td>
							<td align="center"><%=Format.formatMoney(already_quota_money) %></td>
							<td align="center"><%=team_q_num %></td>
							<td align="center"><%=quota_qualified_num %></td>
							<td align="center"><%=already_quota_qualified_num %></td> 
							<td align="center" width="50px">
								<%if(team_money.intValue()>0){ %>
				               	<a href="#" onclick="javascript:showInfo(<%=productId %>,<%=sub_productId %>,<%=team_id %>,<%=opCode %>,'<%=opName %>','<%=quota_money %>','<%=quota_qualified_num %>');">
					           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
					           	</a>
								<%} %>
							</td>
						</tr>
	<%} %>
					<tr class="trbottom">
						<!--合计--><!--项-->
				        <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
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
