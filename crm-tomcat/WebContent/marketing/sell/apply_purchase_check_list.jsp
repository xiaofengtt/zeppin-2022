<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id); // overall_product_id ��ǰ��Ʒ
String product_code= Utility.trimNull(request.getParameter("productid"));
ApplyreachLocal apply = EJBFactory.getApplyreach();
ApplyreachVO vo = new ApplyreachVO();
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String query_contract_bh= Utility.trimNull(request.getParameter("query_contract_bh"));
Integer pre_flag=Utility.parseInt(request.getParameter("pre_flag"),new Integer(0));
Integer check_flag=Utility.parseInt(request.getParameter("check_flag"),new Integer(1));

String viewStr = "CONTRACT_SUB_BH$PRODUCT_NAME$CUST_NAME$CARD_ID$MOBILE$POST_ADDRESS"
	+ "$BANK_NAME$BANK_ACCT$PROV_FLAG_NAME$PROV_LEVEL_NAME$CHANNEL_TYPE_NAME$CHANNEL_NAME$CHANNEL_COOPERTYPE_NAME" 
	+ "$SG_MONEY$QS_DATE$HT_STATUS_NAME$JK_TYPE_NAME";
String tempView = Argument.getMyMenuViewStr(menu_id,input_operatorCode);
if (tempView!=null && !tempView.equals("")){
	viewStr = tempView;
}
Map fieldsMap = Argument.getMenuViewMap(menu_id, "");
if(fieldsMap == null || fieldsMap.isEmpty()){
	fieldsMap = new HashMap();
	viewStr = ""; // ���á���ʾ�趨��ǰ���������ݿ��TFIELDSDIM�м������ʾ�ֶεļ�¼
}

vo.setBook_code(new Integer(1));
vo.setInput_man(input_operatorCode);
vo.setProduct_id(product_id);
vo.setContract_bh(query_contract_bh);
vo.setCheck_flag(check_flag);
vo.setCust_name(cust_name);
String[] totalColumn = {"SG_MONEY"};
IPageList pageList = null;
if(user_id.intValue() == 2)
	pageList = apply.listAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,-1));
else
	pageList = apply.listAll(vo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
sUrl = sUrl +"&productid="+product_code+ "&product_id=" + product_id+"&cust_name="+cust_name+"&card_id="+card_id+"&query_contract_bh="+query_contract_bh+"&pre_flag="+pre_flag+"&check_flag="+check_flag;

String sRetQuery = "page="+sPage+"&pagesize="+sPagesize+"&product_id="+product_id+"&query_contract_bh="+query_contract_bh+"&cust_name="+cust_name;

String options = Argument.getProductListOptions(new Integer(1), product_id,"",input_operatorCode,28);
options = options.replaceAll("\"", "'");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

function showInfo2(serial_no) {
  	location.href = 'apply_purchase_check_info.jsp?serial_no='+serial_no + "&<%=sRetQuery%>";
}

function StartQuery() {
    document.theform.btnQuery.disabled = true;
	disableAllBtn(true);
	location = 'apply_purchase_check_list.jsp?page=1&pagesize=' + document.theform.pagesize.value +'&productid='+document.theform.productid.value+ '&product_id='+ document.theform.product_id.value +'&cust_name='+document.theform.cust_name.value+'&query_contract_bh='+ document.theform.query_contract_bh.value+'&card_id='+document.theform.card_id.value +'&pre_flag='+document.theform.pre_flag.value+'&check_flag='+document.theform.check_flag.value;
}

function refreshPage(){
	StartQuery();
}


function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��");//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}
	}
	nextKeyPress(this);
}

function searchProduct(value){
	prodid=0;
	if (value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��");//����Ĳ�Ʒ��Ų�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function op_check(){
	if(checkedCount(document.theform.serial_no) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ��");//��ѡ��Ҫ��˵ļ�¼
		return false;
	}
	if (sl_check_pass()){
		disableAllBtn(true);
		document.theform.submit();
	}
}

function setView(){
	result = showModalDialog('<%=request.getContextPath()%>/system/basedata/menu_view_set.jsp?v_menu_id=<%=menu_id%>','','dialogWidth:800px;dialogHeight:580px;status:0;help:0');
	if (result) location.reload();
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.product_id.options.length;i++){
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;
		}else{
			document.theform.product_id.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.product_id.focus();
	}else{
		document.theform.product_id.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%> 
<form name="theform" method="POST" action="apply_purchase_check_action.jsp">
<input type="hidden" name = "card_id" value="<%=card_id%>">
<input type="hidden" name = "pre_flag" value="<%=pre_flag%>">
<input type="hidden" name = "check_flag" value="<%=check_flag%>">
<div id="queryCondition" class="qcMain" style="display:none;width:430px;">
<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
  <tr>
   	<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   	<td align="right">
   		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   	</td>
  </tr>
</table>
<table>
	<tr>		
		<td><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
		<td  align="left" >
			<input type="text" onkeydown="javascript:nextKeyPress(this)" name="query_contract_bh" size="15" value="<%=query_contract_bh%>">
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td><!--��Ʒ���-->
		<td>
			<input type="text" name="productid" value="<%=Utility.trimNull(product_code)%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
		</td>
		<td  align="right">��Ʒ���� :</td>
		<td  align="left" >
			<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
		</td>
	</tr>
	<tr>
		<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--��Ʒ����-->
		<td align="left" colspan=3 id="select_id">
			<select size="1" name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
				<%=Argument.getProductListOptions(new Integer(1), product_id,"",input_operatorCode,28)%>
			</select>
		</td>
	</tr>
	<tr>
		<td  align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--�ͻ�����-->
		<td colspan="3">
			<input name="cust_name" value="<%=Utility.trimNull(cust_name)%>" onkeydown="javascript:nextKeyPress(this);" size="55">
		</td>
	</tr>
	<tr>
		<td  align="center" colspan=4>
			<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
		</td><!--ȷ��-->
	</tr>
</table>
</div>

<%
	String[] fieldsArr =Utility.splitString("CONTRACT_SUB_BH$PRODUCT_NAME$CUST_NAME$CARD_ID$POST_ADDRESS"
	+ "$BANK_NAME$BANK_ACCT$PROV_FLAG_NAME$PROV_LEVEL_NAME$CHANNEL_TYPE_NAME$CHANNEL_NAME$CHANNEL_COOPERTYPE_NAME" 
	+ "$SG_MONEY$QS_DATE$HT_STATUS_NAME$JK_TYPE_NAME","$");

	//int totalWidth = 0+100+30; //��ͬ���+���
	
%>
<TABLE height="100%" cellSpacing=0 cellPadding=0  border=0 width="100%">
	<TR>	
		<TD vAlign=top align=left>
		<TABLE cellSpacing=0 cellPadding=4  align=center border=0>
			<TR>
				<TD>
					<table border="0" width="100%" cellspacing="0" cellpadding="0">	
						    <tr>
								<td colspan="6" class="page-title">
									<font color="#215dc6"><b><%=menu_info%></b></font>
								</td>
							</tr>
							<tr>
								<td align=right>
									<div class="btn-wrapper">
									<%if (input_operator.hasFunc(menu_id, 108)){%>
							 		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>Q</u>)
									</button>&nbsp;&nbsp;&nbsp; 
									<button type="button"  class="xpbutton5" accessKey="v" onclick="javascript:setView()"><%=LocalUtilis.language("message.displaySet",clientLocale)%> (<u>V</u>)</button>&nbsp;&nbsp;&nbsp;<!-- ��ʾ�趨 -->
							 		<%}if (input_operator.hasFunc(menu_id, 100)){%>
									<button type="button"  class="xpbutton3" name="btnCheck" title="<%=LocalUtilis.language("message.check",clientLocale)%> " onclick="javascript:op_check();">
									    <%=LocalUtilis.language("message.pass",clientLocale)%> 
									</button> <!--���--><!--���ͨ��-->
									<%}%>
									</div>
									<br/>
							</td></tr>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
				<tr class="trh">
					<td align="center" width="100px">
						<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.serial_no,this);">&nbsp;&nbsp;��ͬ���
					</td><!--��ͬ���-->
				<%
					for(int j=0;j<fieldsArr.length;j++){
						String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
						if ("��ͬ���".equals(setWithName)) continue;

						String setWith = "";
						String other = "";
						
						if ("��Ʒ����".equals(setWithName) || "֤������".equals(setWithName) || "�绰����".equals(setWithName) 
								|| "��������".equals(setWithName)||"�����˺�".equals(setWithName) || "ǩ������".equals(setWithName))
							setWith = "120px";
						else if ("��ַ".equals(setWithName))
							setWith = "180px";	
						else if ("���漶��".equals(setWithName) || "���漶��".equals(setWithName) || "����������ʽ".equals(setWithName))
							setWith = "60px";
						else if ("״̬".equals(setWithName)) 
							setWith = "80px";
						else 
							setWith = "100px";
						
						if ("�깺���".equals(setWithName)) other = "sort='num'";
							
					%>
					<td align="center" width="<%=setWith%>" <%=other%>><%=((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME")%></td>
					<%}%>						
					<td align="center" width="30px"><%=LocalUtilis.language("message.check",clientLocale)%> </td><!--���-->
				</tr>
<%
int iCount = 0;
int iCurrent = 0;
Integer serial_no;
String prov_flag_name = "";
java.util.List list = pageList.getRsList();
String sub_product_name="";
for(int i=0;i < list.size();i++){
	Map map = (Map)list.get(i);
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);
	String contract_bh = Utility.trimNull(map.get("CONTRACT_BH"));
	String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	String product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	//cust_name = Utility.trimNull(map.get("CUST_NAME"));
	Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),null);
	BigDecimal sg_money = Utility.parseDecimal(Utility.trimNull(map.get("SG_MONEY")),null);
	if(sg_money!=null){
		sg_money = sg_money.setScale(2);
	}
	
	//Integer sq_date = Utility.parseInt(Utility.trimNull(map.get("QS_DATE")),null);
	String ht_status_name = Utility.trimNull(map.get("HT_STATUS_NAME"));
	check_flag = Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")),null);
	String check_flag_name = enfo.crm.tools.LocalUtilis.language("message.checked",clientLocale);//�����
	String jk_type_name = Utility.trimNull(map.get("JK_TYPE_NAME"));
	String ht_status = Utility.trimNull(map.get("HT_STATUS"));
	//String bank_name = Utility.trimNull(map.get("BANK_NAME"));
	String bank_acct = Utility.trimNull(map.get("BANK_ACCT"));
	//String prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
	int prov_flag = Utility.parseInt(Utility.trimNull(map.get("PROV_FLAG")),0);
	if(prov_flag==1)
		prov_flag_name = "����";
	else if(prov_flag==2)
		prov_flag_name = "һ��";
	else
		prov_flag_name = "�Ӻ�";
	map.put("PROV_FLAG_NAME", prov_flag_name);

	//String channel_type_name = Utility.trimNull(map.get("CHANNEL_TYPE_NAME"));
	//String channel_name = Utility.trimNull(map.get("CHANNEL_NAME"));
	//String channel_coopertype_name = Utility.trimNull(map.get("CHANNEL_COOPERTYPE_NAME"));
	if(check_flag != null && check_flag.intValue()==1){
		check_flag_name = enfo.crm.tools.LocalUtilis.language("message.unaudited",clientLocale);//δ���		
	}
	sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
	if(sub_product_name!=null&&!"".equals(sub_product_name))
		sub_product_name = "("+sub_product_name+")";		
	%>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td class="tdh" align="center" >
			<table border="0" width="100px" cellspacing="0" cellpadding="0">
				<tr>
					<td width="10%"><input type="checkbox" name="serial_no" value="<%=serial_no%>" class="flatcheckbox"></td>
					<td width="90%" align="left"><%=contract_sub_bh%></td>
				</tr>
			</table>
		</td>
	<%
	for(int j=0;j<fieldsArr.length;j++){		
			String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
			if ("��ͬ���".equals(setWithName)) continue;

			String setWith = "";

			if ("��Ʒ����".equals(setWithName) || "֤������".equals(setWithName) || "�绰����".equals(setWithName) 
					|| "��������".equals(setWithName)||"�����˺�".equals(setWithName) || "ǩ������".equals(setWithName))
				setWith = "120px";
			else if ("��ַ".equals(setWithName))
				setWith = "180px";	
			else if ("���漶��".equals(setWithName) || "���漶��".equals(setWithName) || "����������ʽ".equals(setWithName))
				setWith = "60px";
			else if ("״̬".equals(setWithName)) 
				setWith = "80px";
			else 
				setWith = "100px";
						
			int iType = ((Integer)((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_TYPE")).intValue();
			switch (iType){
				case 1:
					if (fieldsArr[j].equals("PRODUCT_NAME")) {
	%>
						<td align="left" width="<%=setWith%>"><%=product_name%><%=Utility.trimNull(sub_product_name)%></td>
	<%				} else if (fieldsArr[j].equals("HT_STATUS_NAME")) { %>
						<td align="center" width="<%=setWith%>"><%=ht_status_name%><%=check_flag_name%></td>

	<% 				} else if (fieldsArr[j].equals("JK_TYPE_NAME")) { %>
						<td align="center" width="<%=setWith%>"><%=jk_type_name%></td>			
	<% 				} else if (fieldsArr[j].equals("BANK_ACCT")) { %>
						<td align="left" width="<%=setWith%>"><%=Utility.ShowBankAcct(bank_acct)%></td>
	<%   			} else if (fieldsArr[j].equals("SG_MONEY")) { %>
						<td align="left" width="<%=setWith%>"><%=Utility.trimNull(sg_money)%></td>
	<%				} else { %>
						<td align="left" width="<%=setWith%>"><%=Utility.trimNull(map.get(fieldsArr[j]))%></td>
	<%
					}
				break;

				case 2:
	%>
				<td align="right" width="<%=setWith%>"><%=Format.formatMoney(((BigDecimal)map.get(fieldsArr[j])))%></td>
		<%
					break;

					case 3:
		%>
				<td align="center" width="<%=setWith%>"><%=Format.formatDateCn(((Integer)map.get(fieldsArr[j])))%></td>
		<%
					break;

					case 4:
					break;

					default:
					break;
			}
		}
		%>								
				<td align="center" width="30px">
				<%if (input_operator.hasFunc(menu_id, 100)){%>
					<a href="javascript:disableAllBtn(true);showInfo2(<%=serial_no%>);">
	           			<img border="0" src="<%=request.getContextPath()%>/images/check.gif" width="16" height="16">
	           		</a>
				<%}%>
				</td>
			</tr>
<%
iCurrent++;
iCount++;
}
for (; iCurrent < pageList.getPageSize(); iCurrent++){%>
		<tr class="tr<%=(iCurrent % 2)%>">
			<td class="tdh" align="center"></td>
	<%
	 for(int j=0;j<fieldsArr.length;j++){ 
		String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
		if ("��ͬ���".equals(setWithName)) continue;
	%> 			
			<td align="center"></td>    
	<%
	}%>                 
	         <td align="center"></td>                          
	      </tr>           
	<%}%>   								
	
		 <tr class="trbottom">
			<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
		<%
	for(int j=0;j<fieldsArr.length;j++) { 		
		String setWithName = Utility.trimNull(((Map)fieldsMap.get(fieldsArr[j])).get("FIELD_NAME"));
		if ("��ͬ���".equals(setWithName)) continue;
		if ("�깺���".equals(setWithName)) {
	%>
			<td align="right"><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("SG_MONEY"),new BigDecimal(0.00)))%></td>
	<%  } else { %> 
     		<td align="center"></td>    
 	<%
		}
	} %>
	         <td align="center"></td>                     					
		</tr>
	</table>
					
					<table border="0" width="100%" class="page-link">
						<tr valign="top">
							<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>						
						</tr>
					</table>
				</TD>
			</TR>
		</TABLE>
		</TD>
</TABLE>
</form><%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<%apply.remove();
%>
