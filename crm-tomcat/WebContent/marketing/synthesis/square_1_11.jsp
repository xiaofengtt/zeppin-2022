<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*"%>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
int _page = Utility.parseInt(sPage, 1);
int pagesize = Utility.parseInt(sPagesize, 8);

DeployLocal deploy = EJBFactory.getDeploy();
//ProductBondCountLocal local = EJBFactory.getProductBondCount();
String bank_id = Utility.trimNull(request.getParameter("bank_id"));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"), new Integer(0));
String sy_type = Utility.trimNull(request.getParameter("sy_type"));
Integer sy_date = Utility.parseInt(request.getParameter("sy_date"), new Integer(0));
String jk_type = Utility.trimNull(request.getParameter("jk_type"));
String prov_level = Utility.trimNull(request.getParameter("prov_level"));
Integer link_man = Utility.parseInt(request.getParameter("link_man"), new Integer(0));

DeployVO vo = new DeployVO();
vo.setBook_code(input_bookCode);
vo.setBank_id(bank_id);
vo.setProduct_id(overall_product_id);
vo.setSy_type(sy_type);
vo.setJk_type(jk_type);
vo.setSy_date(sy_date);
vo.setProv_level(prov_level);
//2005-7-26 
vo.setInput_man(input_operatorCode);
vo.setSub_product_id(sub_product_id);
vo.setLink_man(link_man);
IPageList pageList = deploy.queryOutputList(vo, _page, pagesize);
List list = pageList.getRsList();

sUrl =
	"square_1_11.jsp?pagesize="
		+ pagesize
		+ "&bank_id="
		+ bank_id
		+ "&product_id="
		+ overall_product_id
		+ "&sy_type="
		+ sy_type
		+"&jk_type="
		+jk_type
		+"&sy_date="
		+sy_date+"&prov_level="
		+prov_level
        +"&sub_product_id="
		+sub_product_id
        +"&link_man="
		+link_man;
%>

<HTML>
<HEAD>
<TITLE>收益分配数据导出</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

</HEAD>
<SCRIPT LANGUAGE="javascript" SRC="/includes/queryEx/scripts/queryEx.js"></SCRIPT>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<script type='text/javascript' src='/dwr/interface/utilityService.js'></script>
<script language=javascript>
function searchProduct(value)
{
	prodid=0;
	if (value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}
window.onload = function(){
initQueryCondition();
productChange(<%=overall_product_id%>);
};

function StartQuery() {
    setWaittingFlag(true);
	psize=document.theform.pagesize.value;
	product_id=document.theform.product_id.value;
	sy_type=document.theform.sy_type.value;
	bank_id=document.theform.bank_id.value;
	jk_type = document.theform.jk_type.value;
	if(document.theform.sy_date_picker.value!="") {
		if(!sl_checkDate(document.theform.sy_date_picker,"分配日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker, document.theform.sy_date);
		
	}
	sy_date = document.theform.sy_date.value;
	prov_level = document.theform.prov_level.value;
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
	    sub_product_id = document.theform.sub_product_id.value;
	disableAllBtn(true);
	
	location = 'square_1_11.jsp?pagesize='+psize+'&product_id='+product_id+'&sy_type='+sy_type+'&bank_id='+bank_id+'&jk_type='+jk_type+'&sy_date='+sy_date+'&prov_level='+prov_level+'&sub_product_id='+sub_product_id
				+ '&link_man=' + document.theform.link_man.value;
	return true;
}
function refreshPage()
{
    setWaittingFlag(true);
	StartQuery();
}
function writetxtfile()
{
	product_id=document.theform.product_id.value;
	sy_type=document.theform.sy_type.value;
	bank_id=document.theform.bank_id.value;
	book_code=document.theform.book_code.value;
	outporttype=document.theform.outporttype.value;
	jk_type = document.theform.jk_type.value;
	prov_level = document.theform.prov_level.value;	
	if(document.theform.sy_date_picker.value!="")
	{
		if(!sl_checkDate(document.theform.sy_date_picker,"分配日期"))	return false;
		syncDatePicker(document.theform.sy_date_picker, document.theform.sy_date);
		
	}
	sy_date = document.theform.sy_date.value;
    var sub_product_id = 0;
    if(document.theform.sub_product_id)
    sub_product_id = document.theform.sub_product_id.value;
	
	if(confirm("确认要导出数据吗？")) {
	    setWaittingFlag(false);
		location = 'download.jsp?product_id='+product_id+'&sy_type='+sy_type+'&jk_type='+jk_type+'&bank_id='+bank_id+"&book_code="+book_code+"&outporttype="+outporttype+"&sy_date="+sy_date+'&prov_level='+prov_level+'&sub_product_id='+sub_product_id
					+ '&link_man=' + document.theform.link_man.value
					+ '&input_man=<%=input_operatorCode%>';
	}
}

function setProduct(value) {
	prodid=0;
	if (event.keyCode == 13 && value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('输入的产品编号不存在！');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function productChange(product_id){
	var _prodcut_id = product_id;
	var tr_sub_product_id;
	var sub_product_id;
	
	sub_product_id = document.theform.sub_product_id;
	tr_sub_product_id = document.getElementById("tr_sub_product_id");

	DWRUtil.removeAllOptions(sub_product_id);
	
	utilityService.getProductFlag(product_id,'sub_flag',function(data){
		if(data==1){
				tr_sub_product_id.style.display="";
			}else{
				tr_sub_product_id.style.display="none";
			}
	});
	utilityService.getSubProductJson(product_id,3,function(data){
		var json = eval(data);
		DWRUtil.addOptions(sub_product_id,{"0":"请选择"});
		for(i=0;i<json.length;i++){
			DWRUtil.addOptions(sub_product_id,json[i]);
	  }
	  DWRUtil.setValue('sub_product_id',<%=sub_product_id%>);
  });
}
</script>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform">
<input type="hidden" name="subflag" value="">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" name="outporttype" value="1">

<div id="queryCondition" class="qcMain" style="display:none;width:550px;">
<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
  <tr>
   <td>查询条件：</td>
   <td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
       onclick="javascript:cancelQuery();"></button></td>
  </tr>
</table>

<table>
<tr>
						<td align="right" nowrap>
						分配日期:</td>
						<td  align="left" nowrap>
						<INPUT TYPE="text" NAME="sy_date_picker" class=selecttext 
						<%if(sy_date!=null){%> value="<%=Format.formatDateLine(sy_date)%>"<%}%>>
						<INPUT TYPE="button" value="▼" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.sy_date_picker,theform.sy_date_picker.value,this);" tabindex="13">
						<INPUT TYPE="hidden" NAME="sy_date"   value="">
						</td>
						<td  align="right" nowrap>受益级别:</td>
						<td  align="left" nowrap>
							<SELECT size="1" name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width: 120px">
									<%=Argument.getProvlevelOptions(prov_level)%>
								</SELECT>
						</td>
					</tr>
					<tr>
						<td align="right" nowrap>	
						银行名称:</td>
						<td  align="left"><SELECT onkeydown="javascript:nextKeyPress(this)" size="1" name="bank_id" nowrap>
							<%=Argument.getBankOptions(bank_id)%>
						</SELECT>
						</td>
						<td  align="right" nowrap>类型:</td>
						<td  align="left" nowrap><SELECT style="width: 80" onkeydown="javascript:nextKeyPress(this)" size="1" name="sy_type">
							<%=Argument.getDictParamOptions(1116, sy_type)%>
						</SELECT>
						</td>
						</tr><tr>
						<td  align="right" nowrap>
						付款方式:</td>
						<td  align="left" nowrap><SELECT onkeydown="javascript:nextKeyPress(this)" size="1" name="jk_type">
							<%=Argument.getJkTypeOptions(jk_type)%>
						</SELECT></td>
						<td align="right" nowrap>产品编号:</td >
<td align="left" nowrap>
<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8 size="10">
&nbsp;<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
</td >
					</tr>
					<tr>			

<td align="right" nowrap>产品选择:</td >
<td align="left" colspan=3>
<SELECT name="product_id" class="productname" onchange="javascript:productChange(this.value)" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getProductListOptions(input_bookCode, overall_product_id, "",input_operatorCode,status)%></SELECT>
</td></tr>
		<tr id="tr_sub_product_id" style="display:none">			
			<td align="right">子产品:</td>
			<td align="left" colspan="3" id="td_sub_product_id">
				<SELECT name="sub_product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
						<%=Argument.getSubProductOptions2(overall_product_id, new Integer(0),sub_product_id,0,"")%> 
				</SELECT>
			</td>
		</tr>
		<tr>
			<td align="right">合同销售人员:</td>
			<td colspan="3">
				<select name="link_man" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 120px">
					<%=Argument.getRoledOperatorOptions(input_bookCode, 2,link_man)%>
				</select>
			</td>
		</tr>
<tr>
<td align="center" colspan=4><button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();">确定(<u>O</u>)</button></td>
</tr>
</table>

</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
	<!--	<TD vAlign=top align=left class="flyoutTd" height="100%"><%//@ include file="menu.inc" %>-->
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=center border=0>

			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<%//int  showTabInfo =  showTabInfoTop.intValue();
					//if(showTabInfo != 1){ %>
					<tr>
						<td  colspan="6"><IMG height=28 src="/images/member.gif" width=32 align=absMiddle border=0><b><%=menu_info%></b></td>						
					</tr>
					<%//} %>
					<tr>
						<td align=right> 
							<button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton">查询(<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
							<%if (input_operator.hasFunc(menu_id, 100))
							{%>
							<button type="button"   class="xpbutton4" name="btnOk" title="生成XSL文件并导出" onclick="javascript:writetxtfile();">导出数据</button>&nbsp;&nbsp;&nbsp;
						 	<%}%>
						 	<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button>&nbsp;&nbsp;&nbsp;
						 </td>
					</tr>
					<tr>
						<td colspan="6">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
				</table>
				<table id="table3" border="0" cellspacing="1" cellpadding="2"  class="tablelinecolor" width="100%">
					<tr class="trh">
						<td align="center" >合同编号</td>
						<td align="center" >受益人</td>
						<td align="center" >证件号码</td>
						<td align="center"  sort="num">本金</td>
						<td align="center" >收益类型</td>
						<td align="center" >收益率</td>
						<td align="center" >应付收益</td>
						<td align="center"  sort="num">扣税</td>
						<td align="center" >银行帐号</td>
						<td align="center" >付款银行</td>
						<td align="center" >银行户名</td>
						<td align="center"  sort="num">实付金额</td>						
						<td align="center" >备注</td>	
					</tr>
<%
int iCurrent = 0;
BigDecimal linetotal=new BigDecimal(0);
BigDecimal allsytotal=new BigDecimal(0);
BigDecimal alltaxtotal=new BigDecimal(0);
BigDecimal allinetotal=new BigDecimal(0);

BigDecimal sumall=new BigDecimal(0);
BigDecimal rate=new BigDecimal(0);
for (int i=0; i<list.size(); i++) {
	iCurrent ++;
	Map map = (Map)list.get(i);
	linetotal=new BigDecimal(0);
	if (map.get("SY_MONEY")!=null) {
		linetotal= (BigDecimal)map.get("SY_MONEY");
		allsytotal= allsytotal.add((BigDecimal)map.get("SY_MONEY"));
	}	
	if (map.get("BOND_TAX")!=null) {
		linetotal=linetotal.subtract((BigDecimal)map.get("BOND_TAX"));
		alltaxtotal=alltaxtotal.add((BigDecimal)map.get("BOND_TAX"));
	}
	if (linetotal!=null)
		allinetotal=allinetotal.add(linetotal);	
	if (map.get("BOND_TAX") != null)
		sumall = ((BigDecimal)map.get("SY_MONEY")).add((BigDecimal)map.get("BOND_TAX"));
	else
		sumall = (BigDecimal)map.get("SY_MONEY");
%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="left" ><%=Utility.trimNull(map.get("CONTRACT_SUB_BH"))%></td>
			<td align="left" ><%=Utility.trimNull(map.get("CUST_NAME"))%></td>
			<td align="left" height="18"><%=Utility.trimNull(map.get("CARD_ID"))%></td>
			<td align="right" height="18"><%=Format.formatMoney((BigDecimal)map.get("SY_AMOUNT"))%><%//=deploy.getSy_amount().setScale(2)%></td>
			<td align="left" height="18"><%=Utility.trimNull(map.get("SY_TYPE_NAME"))%></td>
			<td align="left" height="18"><%=Format.formatPercent(map.get("SY_RATE") == null ? 0 : ((BigDecimal)map.get("SY_RATE")).doubleValue(), 8)%></td>
			<td align="right" height="18"><%=Format.formatMoney(sumall)%></td>
			<td align="right" ><%=Format.formatMoneyPrint(map.get("BOND_TAX") == null ? 0 : ((BigDecimal)map.get("BOND_TAX")).doubleValue(), 2)%></td>
			<td align="left" height="18"><%=Format.formatBankNo(Utility.trimNull(map.get("BANK_ACCT")))%></td>
			<td align="left" height="18"><%=Utility.trimNull(map.get("BANK_NAME"))%></td>	
			<td align="left" height="18"><%=Utility.trimNull(map.get("CUST_ACCT_NAME")) %></td>
			<td align="right" height="18"><%=Format.formatMoney((BigDecimal)map.get("SY_MONEY"))%></td>
			<td align="center" height="18"><%=Utility.trimNull(map.get("SUMMARY"))%></td>
		</tr>
<%
}
for (; iCurrent<pageList.getPageSize(); iCurrent++) { %>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="left" ></td>
			<td align="left" height="19"></td>
			<td align="center" height="19">
			<p align="center">
			</td>
			<td align="center" height="19"></td>
			<td align="center" height="19">
			<p align="right">
			</td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			<td align="left" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
		</tr>
		<%}%>

		<tr class="trbottom">
			<td class="tdh" align="center" ><b>合计 <%=pageList.getTotalSize()%> 项</b></td>
			<td align="left" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			<td align="center" height="19"></td>
			
			<td align="right" height="19"><%=Utility.trimNull(Format.formatMoney(allsytotal.add(alltaxtotal)))%></td>
			<td align="right" height="19"><%=Utility.trimNull(Format.formatMoney(alltaxtotal))%></td>
			<td align="left" height="19"></td>
			<td align="left" height="19"></td>
			<td align="left" height="19"></td>
			<td align="right" height="19"><%=Utility.trimNull(Format.formatMoney(allsytotal))%></td>
			<td align="center" height="19"></td>
		</tr>
	</table>
	<br>
	<table border="0" width="100%">
		<tr valign="top">
			<td><%=pageList.getPageLink(sUrl)%></td>
			<td align="right">
	
			</td>
		</tr>
	</table>

	</TD>
</TR>
		</TABLE>
		</TD>
	</TR>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%deploy.remove();
//local.remove();%>