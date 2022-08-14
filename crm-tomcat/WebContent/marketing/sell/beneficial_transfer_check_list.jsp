<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));//��ͬ���
String product_code=request.getParameter("productid");
Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);//��Ʒ���
String[] totalColumn = {"TO_AMOUNT"};
String Url = sUrl  + "productid="+product_code+ "&contract_bh=" + contract_bh+"&product_id="+product_id;

BenChangeLocal local = EJBFactory.getBenChanage();
boolean bsuccess=false;

if (request.getMethod().equals("POST")){
	BenChangeVO vo = new BenChangeVO();
	vo.setChange_date(Utility.parseInt(request.getParameter("jk_date"), new Integer(0)));
	vo.setInput_man(input_operatorCode);

	String[] serials = request.getParameterValues("selectbox");
 	if (serials != null){
		boolean choosed = false;
		
		for (int i = 0; i < serials.length; i++){
			String serial_no = Utility.trimNull(serials[i]);
			if (serial_no.length() > 0){
				vo.setSerial_no(new Integer(Utility.parseInt(serial_no, 0)));				
				local.check(vo);
			}
		}	
		
		bsuccess=true;
	 }
}

BenChangeVO vo = new BenChangeVO();
vo.setBook_code(new Integer(1));
vo.setProduct_id(product_id);
vo.setContract_bh(contract_bh);
vo.setInput_man(input_operatorCode);
vo.setCheck_flag(new Integer(1));

IPageList pageList = local.listAll(vo,totalColumn, Utility.parseInt(sPage, 1), Utility.parseInt(sPagesize, 8));
List list = pageList.getRsList();
Iterator it = list.iterator();

String options = Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status).replaceAll("\"", "'");

local.remove();
%>
<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("menu.beneficialTransferCheckList",clientLocale)%> </TITLE><!--����Ȩת�����-->
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
<%if(bsuccess){%>
	sl_alert("<%=LocalUtilis.language("message.checkOK2",clientLocale)%> !");//���ͨ���ɹ�
<%}%>

window.onload = function(){
		initQueryCondition();
	};

function refreshPage(){
  disableAllBtn(true);
  
  if(document.theform.product_id.value==null || document.theform.product_id.value.length==0){
    location.search = "?page=<%=sPage%>&pagesize=" + document.theform.pagesize.value
    		 +'&product_id='+document.theform.product_id.value+"&contract_bh="+document.theform.query_contract_bh.value;             
  }
  else{
    location.search = "?page=<%=sPage%>&pagesize=" + document.theform.pagesize.value 
    		+'&product_id='+document.theform.product_id.value+"&contract_bh="+document.theform.query_contract_bh.value;   
  }
}

function StartQuery(){
	refreshPage();
}

function showInfo(serial_no,product_id,contract_bh){
	location = "beneficial_transfer_info_check.jsp?serial_no="+serial_no+"&product_id="+product_id+"&contract_bh="+contract_bh+ "&first=true";
}

function op_check(){
	if(checkedCount(document.theform.selectbox) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectCheckRecord",clientLocale)%> ��");//��ѡ��Ҫ��˵ļ�¼
		return false;
	}
	
	if (confirm("<%=LocalUtilis.language("message.auditBy",clientLocale)%> ��")){//ȷ�����ͨ����
		disableAllBtn(true);
		document.theform.action="beneficial_transfer_check_list.jsp";
		document.theform.submit();
	}
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != ""){
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
	}
	nextKeyPress(this);
}
 
function searchProduct(value){
	prodid=0;
	if (value != ""){
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++){
			if(document.theform.product_id.options[i].text.substring(0,j)==value){
				document.theform.product_id.focus();
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

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT name='product_id' class='productname' onkeydown='javascript:nextKeyPress(this)'>"+"<%=options%>"+"</SELECT>";
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
<BODY class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="POST" action="contract_exchange_check_pz.jsp">
<div id="queryCondition" class="qcMain" style="display:none;width:420px;">	
	<table id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> :</td><!--��ѯ����-->
			<td align="right">
				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"	
				onclick="javascript:cancelQuery();"></button>
			</td>
		</tr>
	</table>
	<table border="0" width="100%" cellspacing="2" cellpadding="2">					
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :</td ><!--��Ʒ���-->
			<td align="left" >
				<input type="text" maxlength="16" name="productid" value="" maxlength=8 size="10"
				onkeydown="javascript:setProduct(this.value);nextKeyPress(this);">&nbsp;
				<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" 
				onclick="javascript:searchProduct(document.theform.productid.value);" />
			</td >
			<td  align="right">��Ʒ���� :</td>
			<td>
				<input type="text" name="product_name" value="" onkeydown="javascript:nextKeyPress(this);" size="15">&nbsp;
				<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" />		
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%> :</td ><!--��Ʒѡ��-->
			<td align="left"  colspan="3" id="select_id">
				<SELECT name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
				</SELECT>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</td><!--��ͬ���-->
			<td align="left"  colspan="3">
				<input type="text" onkeydown="javascript:nextKeyPress(this)"name="query_contract_bh" size="15" value="<%=contract_bh%>">
				&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=o name="btnQuery" onclick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--ȷ��-->								
			</td>
		</tr>					
	</table>				
</div>

<div align="left">
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		&nbsp;&nbsp;&nbsp;	
		<%//if (input_operator.hasFunc(menu_id, 103)){%>
		<!-- <button type="button"  class="xpbutton3"  name="btnNew" title="<%=LocalUtilis.language("message.check",clientLocale)%> " onclick="javascript:op_check();"><%=LocalUtilis.language("message.check",clientLocale)%> 
		</button> --><!--���-->
		&nbsp;&nbsp;&nbsp;<%//}%>
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>	<!--��ѯ-->										
	</div>
	<br/>
</div>

<div>
<!--����-->
<table border="0" width="100%" cellpadding="0" cellspacing="0" >
	<tr>
		<td>
			<table width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
				<tr class="trh">
					<td align="center" >
						<input type="checkbox" name="btnCheckbox" class="selectAllBox"
						 onclick="javascript:selectAllBox(document.theform.selectbox,this);"><%=LocalUtilis.language("class.contractID",clientLocale)%> <!--��ͬ���-->
					</td>
					<td align="center" ><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.fromCustName2",clientLocale)%> </td><!--ת�÷�-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.toCustName2",clientLocale)%> </td><!--���÷�-->
					<td align="center" ><%=LocalUtilis.language("class.jkType",clientLocale)%> </td><!--���ʽ-->
					<td align="center"  sort="num"><%=LocalUtilis.language("class.money",clientLocale)%> </td><!--���-->
					<td align="center"  ><%=LocalUtilis.language("class.changeDate5",clientLocale)%> </td><!--��Ч����-->
					<td align="center" ><%=LocalUtilis.language("class.customerSummary",clientLocale)%> </td><!--��ע-->
					<td align="center" ><%=LocalUtilis.language("message.check",clientLocale)%> </td><!--���-->
				</tr>
				<%
				int iCount = 0;
				int iCurrent = 0;
				
				while(it.hasNext()){
					Map map = (Map)it.next();	%>
				<tr class="tr<%=(iCurrent%2)%>">
					<td class="tdh" align="left" >
						<table border="0" width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td width="10%">
									<!--��˱�־Ϊ��Ϊ��ˡ�ʱ��ʾ-->
								<%if(Utility.parseInt(Utility.trimNull(map.get("CHECK_FLAG")), 0) == 1){%>
									<input type="checkbox" name="selectbox" class="flatcheckbox" value="<%=map.get("SERIAL_NO")%>">
								<%}else{%>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<%}%>
								</td>
								<td width="90%"><%=map.get("CONTRACT_SUB_BH")%></td>
							</tr>
						</table>
					</td>
					<td align="left"><%=Utility.trimNull(Argument.getProductName(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")), new Integer(0))))%></td>
					<td align="left"><%=Utility.trimNull(map.get("FROM_CUST_NAME"))%></td>
					<td align="left"><%=Utility.trimNull(map.get("TO_CUST_NAME"))%></td>
					<td align="center"><%=Utility.trimNull(Argument.getDictParamName_intrust(1114,Utility.trimNull(map.get("JK_TYPE"))))%></td>
					<td align="right" ><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("TO_AMOUNT")),new BigDecimal(0))))%></td>
					<td align="right" ><%=Utility.trimNull(Format.formatDateCn(Utility.parseInt(Utility.trimNull(map.get("CHANGE_DATE")),new Integer(0))))%></td>
					<td align="center" ><%=Utility.trimNull(map.get("SUMMARY"))%></td>
					<td align="center" >
					<%if (input_operator.hasFunc(menu_id, 103)){%>
						<button type="button"  class="xpbutton2" onclick="javascript:showInfo('<%=Utility.trimNull(map.get("SERIAL_NO"))%>','<%=Utility.trimNull(map.get("PRODUCT_ID"))%>');">&gt;&gt;</button>
					<%}%>
					</td>
				</tr>	
				<%
					iCount ++;
					iCurrent ++;
				}
				for(; iCurrent < pageList.getPageSize(); iCurrent++){	%>
				<tr class="tr<%=(iCurrent%2)%>">
					<td class="tdh" align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>	
				<%}	%>
				<tr class="trbottom">
					<td class="tdh" align="left" colspan="1" ><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="right" ><%=Format.formatMoney(Utility.parseBigDecimal(pageList.getTotalValue("TO_AMOUNT"),new BigDecimal(0.00)))%></td>
					<td align="center" ></td>
					<td align="center" ></td>
					<td align="center" ></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
		<td>
			<table width="100%" border="0" cellpadding="0" cellspacing="0" class="page-link">
				<tr>
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>