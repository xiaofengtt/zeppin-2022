<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%

Integer q_productId = Utility.parseInt(request.getParameter("q_productId"),new Integer(0));

Integer q_managerID = Utility.parseInt(Utility.trimNull(request.getParameter("q_managerID")),new Integer(0));

Integer q_cust_type = 0;
Integer q_check_flag = 0;
String query_contract_bh = "";
String q_cust_name = ""; 
String q_card_id = ""; 
String min_rg_money = ""; 
String max_rg_money = ""; 
Integer q_pre_flag = 0; 
Integer q_channel_id = 0;
String q_product_name  = "";
String q_productCode = ""; 
Integer q_group_id  = 0;
Integer q_class_detail_id = 0;
//sUrl = sUrl + tempUrl;

//获得对象及结果集
ContractLocal contract = EJBFactory.getContract();
ContractVO vo = new ContractVO();

vo.setBook_code(input_bookCode);

vo.setManagerID(q_managerID);

//IPageList pageList = contract.(vo,totalColumn,t_sPage,t_sPagesize);
//list = pageList.getRsList();
IPageList pageList = new JdbcPageList();
//list = pageList.getRsList();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.recoveryContract",clientLocale)%> </TITLE><!--资产合同审核恢复-->
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
/*启动加载*/
window.onload = function(){
	initQueryCondition();
	var q_cust_type = document.getElementById("q_cust_type").value;
	var tdId = "td"+q_cust_type;
	document.getElementById(tdId).bgColor ="#99FFFF" ;
}

function getSQuery(){
	var sQuery = document.theform.q_productId.value ;
	var sQuery = sQuery + "$" +document.theform.q_productCode.value;
	var sQuery = sQuery + "$" +document.theform.q_cust_name.value;
	var sQuery = sQuery + "$" +document.theform.query_contract_bh.value;
	var sQuery = sQuery + "$" +document.theform.q_pre_flag.value;
	var sQuery = sQuery + "$" +document.theform.pagesize.value;
	var sQuery = sQuery + "$" +document.theform.q_check_flag.value;
	var sQuery = sQuery + "$" +document.theform.q_product_name.value;
	var sQuery = sQuery + "$" +document.theform.q_managerID.value;

	return sQuery;
}

/*查询功能*/
function StartQuery(){
	refreshPage();
}

/*新增*/
function newInfo(){
	disableAllBtn(true);
	var sQuery = getSQuery();

	if(sl_vb_comfirm('<%=LocalUtilis.language("message.systemVailidation",clientLocale)%>：\n\n<%=LocalUtilis.language("message.appointement",clientLocale)%> ？')){//系统确认//您是否已经预约
		location = 'subscribe_add1.jsp?page=1&sQuery=' + sQuery;
	}
	else{
		location = 'subscribe_add2.jsp?page=1&sQuery=' + sQuery;
	}
}

/*查看受益人*/
function showBenifiter(contract_id){
	disableAllBtn(true);
	var sQuery = getSQuery();
	location = 'benifiter_list.jsp?from_flag_benifitor=1&contract_id='+contract_id+'&page=1&sQuery=' + sQuery;
}


/*修改2*/
function editAction(serialno, checkflag,ht_status,bank_id,pre_flag){
	disableAllBtn(true);
	var sQuery = getSQuery();

	if(pre_flag==1){
		location = 'contract_aftercheck_edit1.jsp?serial_no='+serialno+'&bank_id='+bank_id+'&check_flag='+checkflag+'&ht_status='+ht_status+'&page=1&sQuery=' + sQuery;
	}
	else{
		location = 'contract_aftercheck_edit2.jsp?serial_no='+serialno+'&check_flag='+checkflag+'&bank_id='+bank_id+'&page=1&sQuery=' + sQuery;
	}

}

/*删除功能*/
function delInfo(){
	if(confirmRemove(document.theform.serial_no)){
		disableAllBtn(true);
		document.theform.action="subscribe_del.jsp"
		document.theform.submit();
	}
}

/*刷新*/
function refreshPage(){
	disableAllBtn(true);
	var url = "contract_aftercheck_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&q_pre_flag=' + document.theform.q_pre_flag.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_card_id=' + document.theform.q_card_id.value;
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;
	var url = url + '&max_rg_money=' +  document.theform.max_rg_money.value;
	var url = url + '&min_rg_money=' +  document.theform.min_rg_money.value;
	var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
	var url = url + '&q_group_id=' +document.theform.q_group_id.value;
	var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	var url = url + '&q_channel_id=' +document.theform.q_channel_id.value;
	var url = url + '&q_product_name='+document.theform.q_product_name.value;
	var url = url + '&q_managerID=' +document.theform.q_managerID.value;
	
	location = url;
}

/*设置产品*/
function setProduct(value){
	var prodid=0;

	if(event.keyCode == 13 && value != ""){
        var j = value.length;

		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				StartQuery();
				break;
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}

	nextKeyPress(this);
}

/*查询产品*/
function searchProduct(value){
	var prodid=0;
	if(value!=""){
        var j = value.length;

		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}
		}

		if (prodid==0){
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}

		document.theform.q_productCode.focus();
	}
}

//打印
function print(serial_no){
	disableAllBtn(true);
	<%if(user_id.intValue()==1){%>
  		location = 'subscribe_print2.jsp?serial_no='+serial_no;
  	<%}
	else{%>
  		location = 'subscribe_print.jsp?serial_no='+serial_no;
  	<%}%>
}

/*导出*/
function writefile(){
	var url = "subscribe_outExcel.jsp?bookcode=<%=input_bookCode%>&input_man=<%=input_operatorCode%>&page=1&pagesize="+ document.theform.pagesize.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&q_pre_flag=' + document.theform.q_pre_flag.value;
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_check_flag=' + document.theform.q_check_flag.value;
	var url = url + '&q_cust_name=' + document.theform.q_cust_name.value;
	var url = url + '&q_card_id=' + document.theform.q_card_id.value;
	var url = url + '&query_contract_bh=' + document.theform.query_contract_bh.value;
	var url = url + '&max_rg_money=' +  document.theform.max_rg_money.value;
	var url = url + '&min_rg_money=' +  document.theform.min_rg_money.value;
	var url = url + '&q_cust_type=' +document.theform.q_cust_type.value;
	var url = url + '&q_group_id=' +document.theform.q_group_id.value;
	var url = url + '&q_class_detail_id=' +document.theform.q_class_detail_id.value;
	var url = url + '&q_channel_id=' +document.theform.q_channel_id.value;
	var url = url + '&q_product_name' +document.theform.q_product_name.value;
	var url = url + '&q_managerID' +document.theform.q_managerID.value;
	location = url;

	if(sl_confirm("<%=LocalUtilis.language("message.exportData",clientLocale)%> ")){//导出数据
		location = url;
	}

	showWaitting(0);
}

function changeCustType(flag){
	document.getElementById("q_cust_type").value=flag;
	refreshPage();
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if(result!=null)
	location.reload();
}
</script>
</HEAD>
<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>	
<form name="theform">
<input type="hidden" name="book_code" value="<%=input_bookCode%>">
<input type="hidden" id="q_cust_type" name="q_cust_type" value="<%=q_cust_type%>" />

<div id="queryCondition" class="qcMain" style="display:none;width:600px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   <td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   <td align="right">
  	 <button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"   onclick="javascript:cancelQuery();"></button>
   </td>
  </tr>
</table>

<table width="99%">
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.checkFlag",clientLocale)%> :</td><!--审核标志-->
		<td  align="left" >
			<select size="1" name="q_check_flag" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<OPTION value="2" <%if(q_check_flag.intValue()==2)out.print("selected") ;%>>正常已审核</OPTION>
				<OPTION value="9" <%if(q_check_flag.intValue()==9)out.print("selected") ;%>>修改待审核</OPTION>
			</select>
		</td>
		<td  align="right">	<%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--合同编号-->
		<td  align="left" >
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="15" value="<%=query_contract_bh%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
		<td><input   name="q_cust_name" value="<%=q_cust_name%>" onkeydown="javascript:nextKeyPress(this);" size="15"/></td>
		<td  align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
		<td  align="left">
			<input type="text" name="q_card_id" value="<%=q_card_id%>" onkeydown="javascript:nextKeyPress(this);"  size="23">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.regMoney",clientLocale)%> :</td><!--登记额度-->
		<!--从-->
        <td colspan="3">
        		<%=LocalUtilis.language("message.start",clientLocale)%>
        		<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_rg_money" size="20"
				value="<%=Utility.trimNull(min_rg_money)%>" />
                <%=LocalUtilis.language("message.end",clientLocale)%> <!-- 到 -->
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_rg_money" size="20"
				value="<%=Utility.trimNull(max_rg_money)%>" />
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.applyFlag",clientLocale)%> :</td><!--认购方式-->
		<td align="left">
			<select size="1" name="q_pre_flag" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 110px">
				<%=Argument.getPreFlagOptions(q_pre_flag)%>
			</select>
		</td>
		<td align="right">渠道来源:</td><!--渠道来源-->
		<td > 
			<select size="1" name="q_channel_id" onkeydown="javascript:nextKeyPress(this)" style="WIDTH: 250px">
				<%=Argument.getChannelOptions(input_bookCode,"","",q_channel_id)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.productName",clientLocale)%>:</td>
		<td >
			<input type="text" name="q_product_name" size="25" value="<%=Utility.trimNull(q_product_name)%>"/>
		</td>
		<td  align="right">客户经理:</td>
		<td  align="left">
			<select size="1" name="q_managerID" onkeydown="javascript:onkeyQuery(this);" style="WIDTH: 100px">
				<%=Argument.getManager_Value(q_managerID)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="center" colspan=4>
		<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--确认-->
(<u>O</u>)</button>
		</td>
	</tr>
</table>
</div>

<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TR>
		<TD vAlign=top align=left width="100%">
		<TABLE cellSpacing=0 cellPadding=4 width="100%" align=left border=0>
			<TR>
				<TD>
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="6">
							<IMG src="<%=request.getContextPath()%>/images/member.gif" align=absBottom border=0 width="32" height="28">
							<font color="#215dc6"><b><%=menu_info%></b></font>
						</td>
					</tr>
				    <tr>
				    	<td align=right>
				     		<!--查询-->
                            <button type="button"  class="xpbutton3" accessKey=f id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>F</u>)</button>&nbsp;&nbsp;&nbsp;
								
				    	</td>
				    </tr>
					<tr>
						<td colspan="6">
						<hr noshade color="#808080" size="1">
						</td>
					</tr>
					<tr>
						<td colspan="6">
							<table cellSpacing="1" cellPadding="2"  bgcolor="#CCCCCC">
								<tr style="background:F7F7F7;">
									<td width="50px" align="center" id="td0" <%if (q_cust_type.intValue()==0) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(0);" class="a2"><%=LocalUtilis.language("message.all",clientLocale)%> </a></font></td>
									<!--全部-->
									<td width="50px" align="center" id="td1" <%if (q_cust_type.intValue()==1) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(1);" class="a2">个人</a></font></td>
									<!--个人-->
									<td width="50px" align="center" id="td2" <%if (q_cust_type.intValue()==2) out.print(" class='active'"); %>><font size="2" face="微软雅黑"><a href="#" onclick="javascript:changeCustType(2);" class="a2">机构</a></font></td>
									<!--机构-->
									<td>
										<select size="1" colspan="3"name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:StartQuery();">
											<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
										</select>
									</td>
									<td>
										<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
										<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
									</td>

								<!--产品-->
								<td align="center" id="td4" style="display:none">
									<select name="q_group_id" id="q_group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
										<%=Argument.getCustGroupOption2(new Integer(0),q_group_id)%>
								</select>
							</td>
							<!--客户群组-->
							<td align="center" id="td5" style="display:none">
										<select name="q_class_detail_id" id="q_class_detail_id" onkeydown="javascript:nextKeyPress(this)" style="width: 120px;" onchange="javascript:StartQuery();">
											<option>请选择关注度</option>
											<%=Argument.getCustClassOption(new Integer(30),q_class_detail_id)%>
										</select>
							</td>
										<!--关注度-->
								</tr>
						</table>
						</td>
					</tr>
				</table>

				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class="trh">
					<!--合同编号-->
                    <td align="center" ><%=LocalUtilis.language("class.contractID",clientLocale)%> </td>
					<td align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--产品名称-->
					<td align="center" ><%=LocalUtilis.language("class.customerName",clientLocale)%> </td><!--客户名称-->
					<td align="center" ><%=LocalUtilis.language("class.customerCardID",clientLocale)%> </td><!--证件号码-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.rg_money",clientLocale)%> </td><!--认购金额-->
					<td align="center" ><%=LocalUtilis.language("class.dzDate",clientLocale)%> </td><!--缴款日期-->
					<td align="center" ><%=LocalUtilis.language("class.paymentBank",clientLocale)%> </td><!-- 缴款银行 -->
					<td align="center" ><%=LocalUtilis.language("class.bankAcct3",clientLocale)%> </td><!--银行帐号-->
					<td align="center" width="70px;">受益级别</td><!--受益级别-->
					<td align="center" width="70px;">收益级别</td><!--收益级别-->
					<td align="center" width="35px;"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
				</tr>
<%
int iCurrent = 0;
List rsList = null;
Map rowMap = null;
rsList = pageList.getRsList();

String sub_product_name = "";    
String prov_flag_name = "";
String ht_status = "";
Integer pre_flag;
Integer check_flag;
String bank_id;

for(int i=0;i<rsList.size();i++){
	rowMap = (Map)rsList.get(i);

	sub_product_name = Utility.trimNull(rowMap.get("SUB_PRODUCT_NAME"));
	String prov_level_name = Utility.trimNull(rowMap.get("PROV_LEVEL_NAME"));
	int prov_flag = Utility.parseInt(Utility.trimNull(rowMap.get("PROV_FLAG")),new Integer(0)).intValue();
	Integer serial_no = Utility.parseInt(Utility.trimNull(rowMap.get("SERIAL_NO")),new Integer(0));
	ht_status = Utility.trimNull(rowMap.get("HT_STATUS"));
	pre_flag = Utility.parseInt(Utility.trimNull(rowMap.get("PRE_FLAG")),new Integer(0));
	check_flag = Utility.parseInt(Utility.trimNull(rowMap.get("CHECK_FLAG")),new Integer(0));
	bank_id  = Utility.trimNull(rowMap.get("BANK_ID"));

	if(prov_flag==1)
		prov_flag_name = "优先";
	else if(prov_flag==2)
		prov_flag_name = "一般";
	else if(prov_flag==3)
		prov_flag_name = "劣后";
		
	if(sub_product_name!=null&&!"".equals(sub_product_name))
		sub_product_name = "("+sub_product_name+")";
%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" ><%=rowMap.get("CONTRACT_SUB_BH")%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("PRODUCT_NAME"))%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("CUST_NAME"))%></td>
					<td align="center" ><%=Utility.trimNull(rowMap.get("CARD_ID"))%></td>
					<td align="right" ><%=Utility.trimNull(Format.formatMoney((BigDecimal)rowMap.get("RG_MONEY")))%></td>
					<td align="center" ><%=Format.formatDateCn((Integer)rowMap.get("JK_DATE"))%></td>
					<td align="left" ><%=Utility.trimNull(rowMap.get("BANK_NAME"))%><%=Utility.trimNull(rowMap.get("BANK_SUB_NAME"))%></td>
					<td align="center" ><%=Utility.trimNull(rowMap.get("BANK_ACCT"))%></td>
					<td align="left" ><%=prov_flag_name %></td>   
					<td align="left" ><%=prov_level_name %></td>  
					<td align="center">
						  <a href="javascript:editAction(<%=serial_no%>,<%=check_flag%>,<%=ht_status%>,<%=bank_id%>,<%=pre_flag%>);">
			           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
			           	 </a>
					</td>

				</tr>
<%iCurrent++;
}
for (int i=0; i < pageList.getBlankSize(); i++)
{
%>
					<tr class="tr<%=(iCurrent % 2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>
<%iCurrent++;}
%>
					<tr class="trbottom">
                    <!--合计--><!--项-->
					<td class="tdh" align=left" colspan="11" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>

		
				</tr>
				</table>
				<BR>
				<table border="0" width="100%" cellspacing="1">
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
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%contract.remove();%>
