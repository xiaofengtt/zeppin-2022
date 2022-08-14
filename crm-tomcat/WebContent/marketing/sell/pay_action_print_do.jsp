<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<% 
//ҳ�洫�ݲ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
Integer product_id = new Integer(0);
String contract_bh = "";
//��������
//input_bookCode = new Integer(1);//������ʱ����
String product_name = "";
Integer cust_id = new Integer(0);
boolean bSuccess = false;
//��ö���
MoneyDetailLocal moneyDetailLocal = EJBFactory.getMoneyDetail();
MoneyDetailVO md_vo = new MoneyDetailVO();
ProductLocal product = EJBFactory.getProduct();
ProductVO p_vo = new ProductVO();
Map md_map = null;

//��ѯ�ɿ���ϸ��Ϣ
if(serial_no.intValue()>0){
	md_vo = new MoneyDetailVO();
	md_vo.setSerial_no(serial_no);
	List md_list = moneyDetailLocal.load(md_vo);
	if(md_list.size()>0){
			md_map = (Map)md_list.get(0);

			product_id = Utility.parseInt(Utility.trimNull(md_map.get("PRODUCT_ID")),new Integer(0));
			contract_bh = Utility.trimNull(md_map.get("CONTRACT_BH"));
			cust_id = Utility.parseInt(Utility.trimNull(md_map.get("CUST_ID")),new Integer(0));
	}
	String showline = "readonly class='edline' ";	
}
//��ѯ��Ʒ��Ϣ
if(product_id.intValue()>0){
	p_vo = new ProductVO();
	p_vo.setProduct_id(product_id);	
	List product_list = product.load(p_vo);
	
	if(product_list.size()>0){
		Map product_map = (Map)product_list.get(0);
		product_name = Utility.trimNull(product_map.get("PRODUCT_NAME"));
		
	}
}
//��ѯ�ͻ�֤��
CustomerVO cust_vo = new CustomerVO();
CustomerLocal cust = EJBFactory.getCustomer();
cust_vo.setCust_id(cust_id);
List cust_list = cust.listProcAll(cust_vo);
Map cust_map = (Map)cust_list.get(0);
String card_type_name = Utility.trimNull(cust_map.get("CARD_TYPE_NAME"));
String card_id = Utility.trimNull(cust_map.get("CARD_ID"));
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title>�ɿ��ӡ </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/print.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<style media="print">
.noprint { display: none }
</style>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript">
var n = 1;

//ɨ��ʶ����
function FnTcScanOcr(){
    var textPath='D:\\Result.txt';
    try {
        var ocxObj = document.getElementById('TcCardOcr');
        ocxObj.TcScanOcr( 'D:\\BackValue.txt','D:\\ScanSource.jpg','D:\\CardImage.jpg','D:\\HeadImage.jpg',textPath);
        var rt = ocxObj.TcGetBackValue();
        var rm = ocxObj.TcGetBackMessage();
        if( rt == 0 ) {
            var rr = ocxObj.TcGetResultFile();
        } else {
            alert( "ɨ��ʧ�ܣ�ɨ���Ƿ���ֵ:" + rt + ";������Ϣ:" + rm );
        }
    } catch (e) {
        alert("֤��ɨ��ʧ�ܣ�����ɨ����������IE�������ȫ���ã�");
        return false;
    }
    //location = "card_discern.jsp";       
}

</script>
</head>
<BODY class="BODY body-nox" onload="FnTcScanOcr();print();close();">
<%//@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="pay_action_print.jsp" >
<BR/>
<BR/>

<TABLE height="90%" cellSpacing=0 cellPadding=0 width="90%" border=0 align=center>
	<tr><td colspan="4" align=center><input readonly class="edline" name="contract_bh" size="40" value="<%=product_name%>"><font size="5">�����ʽ����мƻ��ɿ�֤���ļ�</font></td>
	</tr>
	<tr><td colspan="1">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��</td>
		<td colspan="3" align="left">��ͬ���:<%=contract_bh %></td>
	</tr>
	<TR>
		<TD vAlign=top align=center width="100%" colspan="2">
		<TABLE height="100%" cellSpacing=1 cellPadding=4 width="100%" border=1 align=center>
			<tr>
				<td colspan="1" width="10%" align=center>ί��������</td>
				<td colspan="1" width="40%" ><%=md_map.get("CUST_NAME") %></td>
				<td colspan="1" width="10%"  align=center>�Ϲ����</td>
				<td colspan="1" width="40%" ><%=md_map.get("TO_MONEY") %></td>
				
			</tr>
			<tr>
				<td colspan="1" align=center>֤������</td>
				<td colspan="1"><%=card_type_name %></td>
				<td colspan="1" align=center>֤������</td>
				<td colspan="1"><%=card_id %></td>
				
			</tr>
			<tr>
				<td colspan="1">
					<TABLE width="100%"><tr><td align=center> ��</td></tr>
							<tr><TD align=center> �� </TD></tr>
							<tr><TD align=center> �� </TD></tr>
							<tr><TD align=center> �� </TD></tr>
							<tr><TD align=center> �� </TD></tr>
							<tr><TD align=center> �� </TD></tr>
							<tr><TD align=center> ӡ </TD></tr>
							<tr><TD align=center> �� </TD></tr>
					</TABLE>
				<td colspan="3" rowspan="10">&nbsp;<OBJECT
				  name=TcCardOcr
				  classid="clsid:6EAFC189-D17E-4E3F-905C-D5A2BC4E055A"
				  codebase="/includes/card/TcIdCard5.ocx"
				  width=0
				  height=0
				  align=middle
				  hspace=0
				  vspace=0
				></OBJECT>
				</td>
			</tr>
			
		</TABLE>
		</TD>
	</TR>
	<tr><TD>�����ˣ�</TD>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>
<%
moneyDetailLocal.remove();
product.remove();
cust.remove();
 %>
