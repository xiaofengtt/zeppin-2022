<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.system.*,enfo.crm.dao.*,enfo.crm.tools.*, enfo.crm.vo.*, enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//���ҳ�洫�ݱ���
Integer q_productId = Utility.parseInt(Utility.trimNull(request.getParameter("q_productId")),new Integer(0));
String q_productCode = Utility.trimNull(request.getParameter("q_productCode"));
String q_product_name = Utility.trimNull(request.getParameter("q_product_name"));
String sub_product_status = Utility.trimNull(request.getParameter("sub_product_status"));
//������ʱ����
input_bookCode = new Integer(1);
//ҳ�渨������
String tempUrl = "";
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
List list = null;
Map map = null;
//url����
tempUrl = tempUrl+"&q_productId="+q_productId;
tempUrl = tempUrl+"&q_productCode="+q_productCode;
tempUrl = tempUrl+"&q_product_name="+q_product_name;
//��ö��󼰽����
ProductLocal product = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

vo.setProduct_id(q_productId);
vo.setProduct_code(q_productCode);
vo.setProduct_name(q_product_name);
vo.setProduct_status(sub_product_status);

IPageList pageList = product.listSubProductForPage(vo,t_sPage,t_sPagesize);
list = pageList.getRsList();

String options = Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0);
options = options.replaceAll("\"", "'");
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/financing.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
	initQueryCondition();
};
/*ˢ��*/
function refreshPage(){
	disableAllBtn(true);
		
	var url = "sub_product_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + '&q_productId=' + document.theform.q_productId.value;
	var url = url + '&q_product_name=' + document.theform.q_product_name.value;
	var url = url + '&q_productCode=' + document.theform.q_productCode.value;
	var url = url + '&sub_product_status=' + document.theform.sub_product_status.value;
	location = url;	
}
/*��ѯ����*/
function StartQuery(){
	refreshPage();
}
/*��ѯ��Ʒ*/
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
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��");//����Ĳ�Ʒ��Ų�����
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
		
		document.theform.q_productCode.focus();					
	}	
}
/*���ò�Ʒ*/
function setProduct(value){
	var prodid=0;
	
	if(event.keyCode == 13 && value != ""){
	    var j = value.length;
    
		for(i=0;i<document.theform.q_productId.options.length;i++){
			if(document.theform.q_productId.options[i].text.substring(0,j)==value){
				document.theform.q_productId.options[i].selected=true;
				prodid = document.theform.q_productId.options[i].value;
				break;
			}	
		}
	
		if (prodid==0){
			sl_alert("<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ��");//����Ĳ�Ʒ��Ų�����
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}
	}
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = "<SELECT size='1' name='q_productId' class='productname' onkeydown='javascript:nextKeyPress(this)' colspan='3'>"+"<%=options%>"+"</SELECT>";
	if(value != ""){
		for(i=0;i<document.theform.q_productId.options.length;i++){
			var j = document.theform.q_productId.options[i].text.indexOf(value);
			if(j>0){
				list.push(document.theform.q_productId.options[i].text);
				list1.push(document.theform.q_productId.options[i].value);
			}
		}	
		if(list.length==0){
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
			document.theform.q_productCode.value="";
			document.theform.q_productId.options[0].selected=true;
		}else{
			document.theform.q_productId.options.length=0;
			for(var i=0;i<list.length;i++){
				document.theform.q_productId.options.add(new Option(list[i],list1[i]));
			}
		}
		document.theform.q_productId.focus();
	}else{
		document.theform.q_productId.options[0].selected=true;
	}
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	<tr>
		<td>��ѯ������</td>
	   	<td align="right">
	   		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
	   	</td>
  	</tr>
</table>

<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
    	<td align="right">��Ʒ���:</td >
        <td align="left">
			<input type="text" name="q_productCode" value="<%=q_productCode%>" onkeydown="javascript:setProduct(this.value);" size="15">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.q_productCode.value);" />
        </td >
        <td align="right">��Ʒ���� :</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
    </tr>
	<tr>
	    <td align="right">��Ʒѡ��:</td >
	    <td align="left" colspan="3" id="select_id">
				<select size="1" colspan="3"name="q_productId"	onkeydown="javascript:nextKeyPress(this)" class="productname">					
					<%=Argument.getProductListOptions(input_bookCode,q_productId,"",input_operatorCode,0)%>
				</select>
	    </td>
	</tr>
	<tr>
		<td align="right">�Ӳ�Ʒ����:</td>
		<td>
				<input name="q_product_name" value="<%=Utility.trimNull(q_product_name)%>" size="25" onkeydown="javascript:nextKeyPress(this)">
		</td>
		<td align="right">�Ӳ�Ʒ״̬:</td>
		<td>
				<select name="sub_product_status" style="width:122">
					<%=Argument.getProductAllStatusOptions(sub_product_status)%>
				</select>
		</td>
	</tr>
	<tr>						
		<td align="right" colspan="3">
			<button type="button"  class="xpbutton3" accessKey=s name="btnQuery"onclick="javascript:StartQuery();">ȷ��(<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
		</td>
	</tr>					
</table>
</div>

<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	
	<div align="right" class="btn-wrapper">
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp; 
	</div>
	<br/>
</div>

<div style="margin-top:5px">
	<table border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
			<tr class="trh">
				<td align="center" height="25">��Ʒ����</td>
                <td align="center">�Ӳ�Ʒ����</td>
				<td align="center" height="25">��Ʒ����</td>
				<td align="center" height="25">Ԥ�ڷ��н��</td>
				<td align="center" height="25">����</td>
				<td align="center" height="25">������</td>
				<td align="center" height="25">���з���</td>
				<td align="center" height="25">ҵ����׼</td>
				<td align="center" height="25">��ʼ����</td>
			</tr>
<%
	Iterator iterator = list.iterator();
	while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();

		BigDecimal exp_rate1 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE1")), new BigDecimal(0.00),2,"100");
		BigDecimal exp_rate2 = Utility.parseDecimal(Utility.trimNull(map.get("EXP_RATE2")), new BigDecimal(0.00),2,"100");
		BigDecimal result_standrad = Utility.parseDecimal(Utility.trimNull(map.get("RESULT_STANDARD")), new BigDecimal(0));
		Integer start_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
		Integer valid_period = Utility.parseInt(Utility.trimNull(map.get("VALID_PERIOD")),new Integer(0));
	    Integer period_unit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
		String product_period = "";
	    if(period_unit.intValue() == 0)
	       product_period = "�޹̶�����";
	    else if(period_unit.intValue() == 1)
	       product_period = valid_period + "��";
	    else if(period_unit.intValue() == 2)
	       product_period = valid_period + "��";
	    else if(period_unit.intValue() == 3)
	       product_period = valid_period + "��";
 %>
		<tr class="tr<%=(iCount%2)%>">
			<td class="tdh" align="left"><%=Utility.trimNull(map.get("PRODUCT_NAME")) %></td>
			<td align="left" ><%=Utility.trimNull(map.get("LIST_NAME")) %></td>
			<td align="center" ><%=Utility.trimNull(map.get("LIST_ID")) %></td>
			<td align="right" ><%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00),2,"1"))%>��Ԫ</td>
			<td align="center" ><%=product_period%></td>
			<td align="right" ><%=Utility.trimZero(exp_rate1)%>%-<%=Utility.trimZero(exp_rate2)%>%</td>
			<td align="right" ><%=Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0)) %></td>
			<td align="right" ><%=Format.formatMoney(result_standrad)%></td>
			<td align="center" ><%=Format.formatDateCn(start_date) %></td>
		</tr>
<% }%>

<%for(int i=0;i<(t_sPagesize-iCount);i++){%>  
       <tr class="tr<%=i%2%>">
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>       
       </tr>           
<%}%>   
	<tr class="trbottom">
		<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>
         <td align="center"></td>     
	</tr>		
	</table>
</div>
<br>
<div><%=pageList.getPageLink(sUrl,clientLocale)%></div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
