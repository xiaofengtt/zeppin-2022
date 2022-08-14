<%@ page contentType="text/html; charset=GBK"  import="java.math.*,java.util.*,enfo.crm.tools.*,enfo.crm.vo.*,enfo.crm.intrust.*" %>
<%@ include file="/includes/operator.inc" %>
<%
//��ҳ��Ϊ���Ż���ָ�����Ľ�������ҳ��
Integer contract_serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));
//String template_code = Utility.trimNull(request.getParameter("template_code"),"");
Integer template_id = Utility.parseInt(Utility.trimNull(request.getParameter("template_id")),new Integer(0));
String create_date = "";

//��ȡģ��
String temp_content = Utility.trimNull(Argument.getPrintTemplateById(template_id));

%>

<HTML>
<HEAD>

<TITLE>��ͬ��ӡ</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<base target="_self">

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery1.4.js"></script>
<STYLE type="text/css">
	.tb_border{
		border: 1px solid #000000;
	}
	
	@media print {
	  .notprint{
	  	visibility:hidden;
	  }
	}
</STYLE>
<script language="javascript">

function choosePrintTemp(){
	document.theform.submit();
}

</script>
</HEAD>
<BODY>
<form name="theform" action="contract_print.jsp" method="POST">
<INPUT type="hidden" name="serial_no" id="serial_no" value="<%=contract_serial_no%>">

<TABLE cellSpacing="2" cellPadding="0" align="center" width="100%" border="0" summary='���ֱ��'>
<tr>
<td colspan="2"></td>
</tr>
<tr>
<td valign="top" width="90%">
	<TABLE cellSpacing="1" cellPadding="0" width="100%" border="0" class="notprint">
		<TR>
			<TD align="right" width="50%">
				<button type="button"  onclick="window.print()" style="margin-left: 7px;CURSOR: hand">
					<img src="<%=request.getContextPath()%>/images/print.gif" align="middle" style="height:26px; width:26px;"/><span style="vertical-align: middle;">��ӡ</span>	
				</button>
			</TD>
		</TR>
		<TR>
			<TD height="1" bgcolor="#eeeeee" align="right" width="100%" colspan="4"></TD>
		</TR>
	</TABLE>
<%
ContractLocal contract = EJBFactory.getContract();
ContractVO cont_vo = new ContractVO();
Map map_contract = new HashMap();
if(!"".equals(temp_content)){
	//��ȡ����
	if(contract_serial_no.intValue()>0){
		List rsList_contract = null;
		//CustomerVO cust_vo = new CustomerVO();
		cont_vo.setSerial_no(contract_serial_no);
		rsList_contract = contract.load(cont_vo);
		
		if(rsList_contract.size()>0){
			map_contract = (Map)rsList_contract.get(0);
		}
		
	}
	
	StringBuffer total_content = new StringBuffer();
	//��������¸�ֵ
	//noticMap.put("CONTRACT_SUB_BH",Format.formatMoney(fk_total_money));
	map_contract.put("QS_DATE",Format.formatDateCn(Utility.parseInt(Utility.trimNull(map_contract.get("QS_DATE")),0)));
	map_contract.put("RG_MONEY_CN",Utility.numToChinese(Utility.trimNull(map_contract.get("RG_MONEY"))));
	
	String total_elment = "";
	String[] temp_s = temp_content.split("\\_\\$");//����Ҫ�滻�Ĳ��ַ���

	if(!"".equals(temp_content)){
		for(int j=0;j<temp_s.length;j++){
			total_elment = temp_s[j].substring(temp_s[j].lastIndexOf("$_")+2);//�ҳ���Ҫ���滻���ֶ����ƣ�ע��ȥ����ʶ
			temp_s[j] = temp_s[j].replaceAll("\\$\\_"+total_elment,Utility.trimNull(map_contract.get(total_elment)));
		}
	
		for(int n=0;n<temp_s.length;n++){
			total_content.append(temp_s[n]);
		}
	}
%>
<table cellSpacing="1" cellPadding="1" width="100%"  border="0">
<tr>
<td><div contentEditable=true><%=total_content.toString()%></div></td>
</tr>
</table>


<%
	contract.remove();
}
%>
</td>
</tr>
</TABLE>
</form>
</BODY>
</HTML>
