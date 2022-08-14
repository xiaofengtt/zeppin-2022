<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//��ԤԼ���еõ��ͻ�Id Ȼ���ڿͻ����л�ȡ�ͻ���Ϣ��
Integer scust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));

//+++++++start card save++++++++++
CustomerLocal local = EJBFactory.getCustomer();
// ��ö���
String s=session.getAttribute("card_id")!=null?session.getAttribute("card_id").toString():"";
if(!s.equals("")){
		CustomerVO cust_vo = new CustomerVO();
		cust_vo.setCust_id(scust_id);
		cust_vo.setCard_id(session.getAttribute("card_id").toString());
		cust_vo.setCard_type(session.getAttribute("card_type").toString());
		cust_vo.setCust_name(session.getAttribute("name").toString());
		cust_vo.setNation_name(session.getAttribute("nation").toString());
		cust_vo.setCard_address(session.getAttribute("address").toString());
		cust_vo.setSex(Utility.parseInt(Utility.trimNull(session.getAttribute("sex")), new Integer(1)));
		cust_vo.setBirthday(Utility.parseInt(Utility.trimNull(session.getAttribute("birth")), new Integer(0)));
		cust_vo.setIssued_date(Utility.parseInt(Utility.trimNull(session.getAttribute("issueDate")), new Integer(0)));
		cust_vo.setIssued_org(session.getAttribute("issuePlace").toString());
		cust_vo.setCard_valid_date(Utility.parseInt(Utility.trimNull(session.getAttribute("valid_date")), new Integer(0)));
		cust_vo.setInput_man(input_operatorCode);
		
		java.io.InputStream tt=(java.io.InputStream)session.getAttribute("inputstream");

		java.io.InputStream tt2=(java.io.InputStream)session.getAttribute("inputstream2");
		cust_vo.setInputStream(tt);//��Ƭ1
		cust_vo.setInputStream1(tt2);//��Ƭ2
		//end session����

	    session.removeAttribute("card_id");
		session.removeAttribute("sex" );
		session.removeAttribute("birth");
		session.removeAttribute("address");
		session.removeAttribute("name");
		session.removeAttribute("card_type");
		session.removeAttribute("issueDate");
		session.removeAttribute("period");
		session.removeAttribute("issuePlace");
		session.removeAttribute("nation");
		session.removeAttribute("inputstream1");
		session.removeAttribute("inputstream2");
        local.modify3(cust_vo);
}

//ҳ�����ݲ�ѯ
Map cust_map = new HashMap();
CustomerVO vo = new CustomerVO();
vo.setCust_id(scust_id);
vo.setInput_man(input_operatorCode);
List cust_list=local.listCustomerLoad(vo);
String voc_type = "";
if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0); 
	if(!"".equals(Utility.trimNull(cust_map.get("VOC_TYPE_NAME")))){
		String[] voc =  Utility.splitString(Utility.trimNull(cust_map.get("VOC_TYPE_NAME")),"-"); 
		voc_type =  voc[0];
	}
	Integer card_valid_date = Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ���Ȼ��)</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<style>
 td{font-size:15px;padding:5px;}
 </style>
<script language=javascript>
function printreturn(){
	window.close();	
}
</script>

</head>
<body topmargin="0" leftmargin="8" rightmargin="8">
<form name="theform"  method="post">
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
			<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('ȷ��Ҫ��ӡ��'))	{	document.all.WebBrowser.ExecWB(6,6);}">ֱ�Ӵ�ӡ(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="printreturn()">�ر�</button>
			</td>
		</tr>
</table>
<table style="width:210mm;" border="0" cellspacing="0" cellpadding="0"  align="center">
	<tr>
		<td align="center" colspan="4"><b><font size="+2">���������������޹�˾</font></b></td>
	</tr>
	<tr>
		<td align="center" colspan="4"><b><font size="+2">�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ���Ȼ�ˣ�<font></b></td>
	</tr>
	<tr>
		<td width="33%"></td>
		<td width="34%" align="center"><%=Format.formatDateCn(Utility.getCurrentDate())%></td>
		<td width="23%" align="right">���:</td>	
		<td width="10%"></td>
	</tr>		
</table>
<table style="width:210mm" border="1" cellspacing="0" cellpadding="1" summary="�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ���Ȼ�ˣ�" align="center">
  	<tr >
		<td rowspan="9" width="4px" align="center"><strong>ί������д</strong></td>
		<td width="13%">ί��������</td>
		<td width="10%">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
		<td width="9%">�Ա�</td>
		<td width="6%">&nbsp;<%=Utility.trimNull(cust_map.get("SEX_NAME"))%>&nbsp;</td>
		<td width="7%">����</td>
		<td width="11%">&nbsp;<%=Utility.trimNull(Argument.getDictParamName(9997,Utility.trimNull(cust_map.get("COUNTRY"))))%></td>
		<td width="21%">ְҵ</td>
		<td width="22%">&nbsp;<%=Utility.trimNull(cust_map.get("VOC_TYPE_NAME"))%></td>
  </tr>
  <tr>
    <td>���֤��/֤���ļ�����</td>
    <td>&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
    <td colspan="2">���֤��/֤���ļ�����</td>
    <td colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
    <td>���֤��/֤���ļ���Ч��</td>
    <td>&nbsp;<%if(card_valid_date.intValue() >= 21000101) {%>����<%}else{%><%=Utility.trimNull(cust_map.get("CARD_VALID_DATE"))%><%} %></td>
  </tr>
  <tr>
    <td>��ϵ��ַ</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></td>
    <td>��������</td>
    <td colspan="1">&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
  </tr>
  <tr>
    <td>��ϵ�绰</td>
    <td colspan="5">&nbsp;</td>
    <td>��������</td>
    <td colspan="1">&nbsp;<%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
  </tr>
  <tr>
    <td colspan="8" width="100%">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="2"><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>Ͷ����һ�����мƻ�����ͽ����������100��Ԫ</td>
			</tr>
			<tr>
				<td colspan="2"><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>��Ȼ��Ͷ����һ�����мƻ�����ͽ�����������100��Ԫ��</td>
			</tr>
			<tr>
				<td align="right" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/checkbox.gif"/></td>
				<td>���˻��ͥ�����ʲ��ܼ������Ϲ�ʱ����100 ��Ԫ�����</td>
			</tr>
			<tr>
				<td align="right" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/checkbox.gif"/></td>
				<td>�������������������ÿ�����볬��20 ��Ԫ����һ��߷���˫���ϼ����������������ÿ�����볬��30 ��Ԫ�����</td>
			</tr>
			<tr>
				<td colspan="2"><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>Ͷ���ڱ���˾���е����мƻ����ڴ����ڼ�Ľ����������100��Ԫ</td>
			</tr>
		</table>
     </td>
  </tr>
  <tr>
    <td>����������</td>
    <td>&nbsp;</td>
    <td colspan="2">�����˹���</td>
    <td colspan="2">&nbsp;</td>
    <td>��ϵ�绰</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>���֤��/֤���ļ�����</td>
    <td>&nbsp;</td>
    <td colspan="2">���֤��/֤���ļ�����</td>
    <td colspan="2">&nbsp;</td>
    <td>���֤��/֤���ļ���Ч��</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>�ʽ���Դ����</td>
    <td colspan="7" >
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>��н���� 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>��Ӫ������ 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>�Ʋ������� 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>Ͷ������  
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>�������� 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>��������
	</td>
  </tr>
  <tr>
    <td><div> <strong>֣������</strong><br />
    </div></td>
    <td colspan="7" style="text-indent:2em;">
		<strong>��ί���˽�������˾�������ʽ�Ϊ��ί���˱��˺Ϸ����еĲƲ��������ڷǷ��㼯�����ʽ�������мƻ������Ρ������ʽ��������κθ��ˡ����˼�������֯�����ڷ����ϵ��κξ��ס�����Υ��������ŵ��������һ�з��ɺ�����ɱ�ί����ȫ���е���
  		</strong></br><br>
		<table width="100%">
			<tr>
				<td width="40%"> <strong>ί���˻������ǩ����</strong></td>
				<td width="20%"><strong>��ӡ����</strong></td>
				<td width="10%"><strong>��</strong></td>
				<td width="10%"><strong>��</strong></td>
				<td width="10%"><strong>��</strong></td>
				<td width="*"></td>
			</tr>
		</table></td>
  </tr>
  <tr>
    <td><strong>�ʸ��������д</strong> </td>

   <td colspan="8">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td><strong>������ϣ�</strong> </td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>���������׵�����</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>���и��µĴ��֤��������һ���ң�</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>���й�Ʊ����������֤ȯ֤�����ʽ��˻����֤����ȯ�̻��йܻ������ߣ�</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>Ͷ���������мƻ���������Ƽƻ���֤��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>Ͷ����ȯ����Ƽƻ�֤��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>����Ͷ���˻����ʽ�֤��</td>
			</tr>			
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>Ͷ���ͱ��ղ�Ʒ֤��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>���������ʲ�֤��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>��������˰��˰֤��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>��������֤��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>ί�������֤��ԭ������ӡ��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>���������֤��ԭ������ӡ��</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>ί������Ȩί����ԭ��</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td width="25%"> �����ˣ�</td>
				<td width="25%"> �����ˣ�</td>
				<td width="15%">�����£�</td>
				<td width="8%">��</td>
				<td width="8%">��</td>
				<td width="8%">��</td>
				<td width="*"></td>
			</tr>
		</table>
	</td>
  </tr>  
</table>
<table style="width:210mm" align="center">
	<tr>
		<td valign="top" align="right" style="width:18mm;">ְҵѡ��:</td>
		<td rowspan="3">1A������רҵ������Ա��1B�����һ��ء���Ⱥ��֯������ҵ��λ�ĸ����ˣ�1C��������Ա���й���Ա��1D����ҵ������Ա��1E�������Թ�����Ա��1F��ũ�������Ͷ��ߣ�1G���������������乤���Ͳ��������Ͷ��ߣ�1H���������������Ͷ���</td>
	</tr>
	<tr><td><td></tr>
	<tr><td><td></tr>
</table>
</form>
</body>
</html>
<%
local.remove();
%>