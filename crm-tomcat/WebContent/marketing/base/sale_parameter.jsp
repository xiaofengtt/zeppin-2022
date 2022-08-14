<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//���ҳ�洫�ݱ���
Integer productId = Utility.parseInt(Utility.trimNull(request.getParameter("productId")),new Integer(0));
String productCode = Utility.trimNull(request.getParameter("productCode"));
String product_name = Utility.trimNull(request.getParameter("q_productName"));
Integer start_date=Utility.parseInt(request.getParameter("start_date"),new Integer(0));
Integer end_date=Utility.parseInt(request.getParameter("end_date"),new Integer(0));
String product_status=Utility.trimNull(request.getParameter("product_status"));
Integer check_flag = Utility.parseInt(request.getParameter("check_flag"),new Integer(0));
Integer open_flag = Utility.parseInt(request.getParameter("open_flag"),new Integer(0));
Integer intrust_flag1 = Utility.parseInt(request.getParameter("intrust_flag1"),new Integer(2));
Integer sale_status = Utility.parseInt(request.getParameter("sale_status"),new Integer(0));
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String sub_product_name = null;
Integer pre_status = Utility.parseInt(request.getParameter("pre_status"),new Integer(0));
Integer pre = Utility.parseInt(request.getParameter("pre"),new Integer(0));

//ҳ�渨������
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
int iCount = 0;
int iCurrent = 0;
String[] totalColumn = new String[0];

//��ö��󼰽����
ProductLocal sale_parameter = EJBFactory.getProduct();
ProductVO vo = new ProductVO();
vo.setBook_code(new Integer(1));
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setProduct_status(product_status);
vo.setCheck_flag(check_flag);
vo.setOpen_flag(open_flag);
vo.setIntrust_flag1(intrust_flag1);
vo.setProduct_id(productId);
vo.setProduct_name(product_name);
vo.setProduct_code(productCode);
vo.setSale_status(sale_status);
vo.setInput_man(input_operatorCode);
vo.setSub_product_id(sub_product_id);
vo.setPreStatus(pre_status);
if(pre.intValue()==1){
	sale_parameter.modiProductPre(vo);
	vo.setProduct_id(new Integer(0));
	vo.setSub_product_id(new Integer(0));
}
IPageList pageList = sale_parameter.listPageCrmSubProduct(vo,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));
List list = pageList.getRsList();

sUrl += "&productId="+productId
				+"&productCode="+productCode
				+"&product_name="+product_name
				+"&product_status="+product_status
				+"&check_flag="+check_flag
				+"&open_flag="+open_flag
				+"&start_date="+start_date
				+"&end_date="+end_date;
%>

<HTML>
<HEAD>
<TITLE><%=LocalUtilis.language("message.saleParameterSet",clientLocale)%></TITLE><!--���۲�������-->
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
window.onload = function(){
		initQueryCondition();
	};

function StartQuery(){
	refreshPage();
}
	/*ˢ��*/
function refreshPage(){
	syncDatePicker(document.theform.start_date_picker, document.theform.start_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	var url ="sale_parameter.jsp?page=1&pagesize=" + document.theform.pagesize.value
					+"&productCode="+document.theform.productCode.value
					+"&q_productName="+document.theform.q_productName.value
					+"&product_status="+document.theform.product_status.value
					+"&check_flag="+document.theform.check_flag.value
					+"&open_flag="+document.theform.open_flag.value
					+"&start_date="+document.theform.start_date.value
					+"&end_date="+document.theform.end_date.value
					+"&sale_status="+document.theform.sale_status.value
					+"&intrust_flag1="+document.theform.intrust_flag1.value;						
	location.href = url;
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

/*�༭*/
function showInfo(productId,intrust_flag1,sub_product_id,sub_product_name){
	location.href="sale_parameter_set.jsp?productId="+productId+"&sub_product_id="+sub_product_id+"&sub_product_name="+sub_product_name+"&intrust_flag1="+intrust_flag1;
}

/*������������*/
function showInfo1(productId,intrust_flag1,sub_product_id){
	location.href="product_market_trench_set.jsp?productId="+productId+"&sub_product_id="+sub_product_id+"&intrust_flag1="+intrust_flag1;
}

/*ģ������*/
function exportInfo(){
	if(checkedCount(document.theform.product_idv) == 0){
		sl_alert("��ѡ����Ҫ���صĲ�Ʒ��");
		return false;
	}
	if(sl_confirm("����ѡ��Ĳ�Ʒ"))	{
	  	setWaittingFlag(false);
	 	document.theform.action = "sale_parameter_export.jsp";
		document.theform.submit();
	}
}

//�Ƿ��ԤԼ
function makeAnAppointment(product_id,sub_product_id,pre_status){
	if (pre_status==1)
		pre_status=2;
	else
		pre_status=1;
	
	var url="sale_parameter.jsp?page=1&pagesize="+document.theform.pagesize.value+"&productId="+product_id+"&sub_product_id="+sub_product_id+"&pre_status="+pre_status+"&pre=1";
	location=url;
}

//�鿴��Ʒ��Ϣ
function showProduct(product_id){	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}
</script>
</HEAD>
<BODY class="BODY body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform">
<div id="queryCondition" class="qcMain" style="display:none;width:550px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="99%" cellspacing="0" cellpadding="2">
		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
			<td align="right"><button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose"
	       		onclick="javascript:cancelQuery();"></button></td>
		</tr>
	</table>
	<table width="99%" cellspacing="0" cellpadding="2" border="0">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productID2",clientLocale)%> :</td><!--��Ʒ���-->
			<td>
				<input type="text" style="width:120" name="productCode" value="<%=productCode%>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.openFlag",clientLocale)%> :</td><!--���з�ʽ-->	
			<td>
				<select name="open_flag" style="width:122">
					<%=Argument.getOpenFlag(open_flag)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productName4",clientLocale)%> :</td><!--��Ʒ����-->
			<td colspan="3">
				<input type="text" name="q_productName" size="66" value="<%=product_name%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> :</td><!--��ʼ����-->
			<td>
				<input TYPE="text" style="width:120" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="start_date" value="">	</td>
			<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--��������-->
			<td>
				<input TYPE="text" style="width:120" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="end_date" value="">	</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productStatus",clientLocale)%> :</td><!--��Ʒ״̬-->
			<td>				
				<select name="product_status" style="width:122">
					<%=Argument.getProductAllStatusOptions(product_status)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.product",clientLocale)%><%=LocalUtilis.language("class.checkFlag2",clientLocale)%> :</td><!--��Ʒ���״̬-->
			<td>				
				<select name="check_flag" style="width:122">
					<%=Argument.getCheckFlagOptions(check_flag)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.collectionLogo",clientLocale)%> :</td><!-- ���ϱ�־ -->
			<td>				
				<select name="intrust_flag1" style="width:122">
					<OPTION value="1" <%if(intrust_flag1.intValue()==1)out.print("selected"); %>>��һ</OPTION>
					<OPTION value="2" <%if(intrust_flag1.intValue()==2)out.print("selected"); %>>����</OPTION>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.saleParam",clientLocale)%><%=LocalUtilis.language("class.checkFlag2",clientLocale)%> :</td><!--���۲������״̬-->
			<td>				
				<select name="sale_status" style="width:122">
					<%=Argument.getSaleCheckFlagOptions(sale_status)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan=6><button type="button"  class="xpbutton3" name="btnQuery" accessKey=o onClick="javascript:StartQuery();"><%=LocalUtilis.language("message.ok",clientLocale)%> <!--ȷ��-->(<u>O</u>)</button></td>
		</tr>
  </table>	
</div>

<div style="margin-top:5px">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan=4 class="page-title">
			<font color="#215dc6"><b><%=LocalUtilis.language("message.productManagement",clientLocale)%> <!--��Ʒ����--></b></font>
		</td><!--��������>>��Ʒ����-->						
	</tr>
	<tr>
		<td align=right >
		<div class="btn-wrapper">
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton"><%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton5" id="exportButton" name="exportButton" onclick="javascript:exportInfo();" title="��������ģ������">��������ģ������</button>
		</div>
		</td>				
	</tr>
	<tr><td>&nbsp;&nbsp;&nbsp; </td></tr>
</table>
<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
	<tr class=trtagsort>
		<td align="center"><input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAll(document.theform.product_idv);"><%=LocalUtilis.language("class.productID",clientLocale)%> </td><!--��Ʒ����-->
		<td align="center"><%=LocalUtilis.language("class.productName",clientLocale)%> </td><!--��Ʒ����-->
		<td align="center"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> </td><!--�ƽ鿪ʼʱ��-->	
		<td align="center"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> </td><!--�ƽ����ʱ��-->
		<td align="center"><%=LocalUtilis.language("class.preNum",clientLocale)%> </td><!--Ԥ�ڷ��зݶ�-->			
		<td align="center"><%=LocalUtilis.language("class.preMoney",clientLocale)%> </td><!--Ԥ�ڷ��н��-->			
		<td align="center"><%=LocalUtilis.language("class.minMoney",clientLocale)%> </td><!--��ͷ��н��-->		
		<td align="center"><%=LocalUtilis.language("class.periodUnit",clientLocale)%> </td><!--��Ʒ����-->
		<td align="center">���۲������״̬</td><!--���۲������״̬-->
		<td align="center">����<%=LocalUtilis.language("message.set",clientLocale)%> </td><!--����-->
		<td align="center">��������<%=LocalUtilis.language("message.set",clientLocale)%> </td><!--����-->
		<td align="center">�Ƿ��ԤԼ</td>
	</tr>
<%
BigDecimal pre_money = new BigDecimal(0);
BigDecimal min_money = new BigDecimal(0);
Integer pre_start_date;
Integer pre_end_date;
Integer pre_num;
String product_code = "";
Integer periodUnit ;
for(int i=0;i<list.size();i++){
	Map map = (Map)list.get(i);
	productId = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")), new BigDecimal(0.00));
	min_money = Utility.parseDecimal(Utility.trimNull(map.get("MIN_MONEY")), new BigDecimal(0.00));
	pre_start_date = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
	pre_end_date = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0));
    pre_num = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),new Integer(0));
    product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
    product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
    periodUnit = Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0));
    sale_status = Utility.parseInt(Utility.trimNull(map.get("SALE_STATUS")),new Integer(0));
    //intrust_flag1 = Utility.parseInt(Utility.trimNull(map.get("INTRUST_FLAG1")),new Integer(0));//�Ӳ�Ʒ����û�е�һ/���ϱ�ʶ��INTRUST_FLAG1���ò�Ʒ��ֵ
	sub_product_id = Utility.parseInt(Utility.trimNull(map.get("SUB_PRODUCT_ID")),new Integer(0));
	pre_status = Utility.parseInt(Utility.trimNull(map.get("PRE_STATUS")),new Integer(0));
	if(sub_product_id.intValue()!=0)
		sub_product_name = product_name;
	else
		sub_product_name = "1";	
 %>
	<tr class="tr<%=(iCurrent % 2)%>">
		<td align="center"> <input type="checkbox" name="product_idv" value="<%=productId%>" class="flatcheckbox"><%=product_code %></td>
		<td align="left"><a href="#" onclick="javascript:showProduct('<%=productId%>');"><%=product_name %></a></td>
		<td align="center"><%=Format.formatDateCn(pre_start_date) %></td>
		<td align="center"><%=Format.formatDateCn(pre_end_date) %></td>
		<td align="right"><%=pre_num%></td>
		<td align="right"><%=Format.formatMoney(pre_money) %></td>
		<td align="right"><%=Format.formatMoney(min_money) %></td>
		<td align="left"><%if(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0))!=null)
		        {if(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)).intValue()!=0){%>
		    <%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("VALID_PERIOD")),new Integer(0)))%>
			<%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)))%>
			<%}else{%><%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(map.get("PERIOD_UNIT")),new Integer(0)))%><%}}%>
		</td>
		<td align="center"><%if(sale_status.intValue()==1) out.print("δ���"); 
				else if(sale_status.intValue()==2) out.print("�����"); else out.print("δ����");%></td>
		<td align="center" width="50px">
			<%if (input_operator.hasFunc(menu_id, 102)) {%>
               	<a href="#"  onclick="javascript:showInfo(<%=productId %>,<%=intrust_flag1 %>,<%=sub_product_id %>,'<%=sub_product_name%>');">
	           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
	           	</a>
			<%}%>
		</td>
		<td align="center" width="50px">
			<%if (input_operator.hasFunc(menu_id, 102)) {%>
               	<a href="#" onclick="javascript:showInfo1(<%=productId %>,<%=intrust_flag1 %>,<%=sub_product_id %>);">
	           		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
	           	</a>
			<%}%>
		</td>
		<td align="center">
			<%if(pre_status.intValue()!=0){ %>
			<a href="#" onclick="javascript:makeAnAppointment(<%=productId %>,<%=sub_product_id %>,<%=pre_status %>)"><%if(pre_status.intValue()==1){out.print("��ԤԼ");}
																					else if(pre_status.intValue()==2){out.print("����ԤԼ");}%></a>
			<%}else{%>���۲���δ����<%}%>
		</td>
	</tr>
<%	iCurrent++;
	iCount++;
}

for(; iCurrent < pageList.getPageSize(); iCurrent++){%>  
	<tr class="tr<%=(iCurrent % 2)%>">
		<td align="center"></td>
        <td align="center"></td>
        <td align="center"></td>
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
		<!--�ϼ�--><!--��-->
        <td class="tr<%=(iCurrent % 2)%>" align="left" colspan="12"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
	</tr>		
</table>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>
<%@ include file="/includes/foot.inc"%>
</HTML>