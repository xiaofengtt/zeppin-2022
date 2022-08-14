<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));

ProductAddLocal prod_local = EJBFactory.getProductAdd();//��Ʒ�Զ���Ҫ��
CustomerLocal cust_local = EJBFactory.getCustomer();
ProductAddVO prod_vo = new ProductAddVO();
CustomerVO cust_vo = new CustomerVO();

Map cust_map = new HashMap();
Map prod_map = new HashMap();

cust_vo.setCust_id(cust_id);
List cust_list = cust_local.listCustomerLoad(cust_vo);
if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0);
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY  >
<form name="theform" method="post" action="ent_info_print.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="cust_id" value="<%=cust_id%>"> 


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="1" >
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td align="center" colspan="2"><font size="4"><%=Argument.getCompanyName(user_id)%></font></td>
					</tr>
					<tr>	
						<td align="center" colspan="2"><font size="4"><b>�ϸ�Ͷ�����ʸ�ȷ�ϵǼǱ���Ȼ�ˣ�</b></font></td>
					</tr>
					<tr>	
						<td align="right" style="letter-spacing:40pt;" width="60%"><font size="3">������</font></td>
						<td align="center"><font size="3">���:&nbsp;<%=Utility.trimNull(cust_map.get("HGTZR_BH"))%></font></td>
					</tr>
				</table>

				<table bordercolor="#000000" border ="1" cellSpacing="0" cellPadding="5" width="100%">
					<tr>
						<td class="tdrightbottom" width="1px" rowspan="10" style="BORDER-BOTTOM: 3px solid;"><font size="2"><b>ί������д</b></font></td>
						<td class="tdrightbottom" align="center" width="13%"><b>ί��������</b></td>
						<td class="tdrightbottom" width="10%">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
						<td class="tdrightbottom" align="center" colspan="2" width="5%"><b>�Ա�</b></td>
						<td class="tdrightbottom" width="7%" align="center">&nbsp;<%=Utility.trimNull(cust_map.get("SEX_NAME"))%>&nbsp;</td>
						<td class="tdrightbottom" align="center" colspan="2" width="5%"><b>����</b></td>
						<td class="tdrightbottom" width="15%" >&nbsp;<%=Utility.trimNull(Argument.getDictParamName(9997,Utility.trimNull(cust_map.get("COUNTRY"))))%></td>
						<td class="tdrightbottom" align="center" colspan="2" width="10%"><b>ְҵ</b></td>
						<td class="tdbottom" colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("VOC_TYPE_NAME"))%></td>
					</tr>
					
					<tr>
						<td align="center"  class="tdrightbottom"><b>���֤��/֤���ļ�����</b></td>
						<td class="tdrightbottom" width="15%" colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
						<td align="center"  class="tdrightbottom" colspan="3" width="12%"><b>���֤��/֤���ļ�����</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						<td align="center"  class="tdrightbottom" colspan="2" width="14%"><b>���֤��/֤���ļ���Ч��</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0))))%></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>��ϵ��ַ</b></td>
						<td class="tdbottom" colspan="11">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>&nbsp;</td>
						
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>��������</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
						<td class="tdrightbottom" align="center"   colspan="3"><b>��ϵ�绰</b></td>
			 			<td class="tdbottom"colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("MOBILE"))%></td>
					</tr>
					<tr>
						<td class="tdbottom" colspan="12" style="font-size:14;">
							<input type="checkbox" value="" class="flatcheckbox" disabled>Ͷ����һ�����мƻ�����ͽ����������100��Ԫ&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>��Ȼ��Ͷ����һ�����мƻ�����ͽ�����������100��Ԫ��&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>���˻��ͥ�����ʲ��ܼ������Ϲ�ʱ����100 ��Ԫ�����&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>�������������������ÿ�����볬��20 ��Ԫ����һ��߷���˫���ϼ����������������ÿ�����볬��30 ��Ԫ����ҡ�<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>Ͷ���ڱ���˾���е����мƻ����ڴ����ڼ�Ľ����������100��Ԫ<br>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>����������</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="3" width="12%"><b>�����˹���</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>��ϵ�绰</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull("")%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>���֤��/֤���ļ�����</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="3"><b>���֤��/֤���ļ�����</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>���֤��/֤���ļ���Ч��</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull("")%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>�ʽ���Դ����</b></td>
			 			<td class="tdbottom" colspan="11" style="font-size:13;">&nbsp;
			 		<%
					prod_vo.setBookcode(new Integer(0));
					prod_vo.setTb_flag(new Integer(2));
					prod_vo.setField_caption("�ʽ���Դ");
					List prod_list = prod_local.list(prod_vo);
					if(prod_list != null && prod_list.size() > 0)
					{
						prod_map = (Map)prod_list.get(0);
						String fieldValue = Utility.trimNull(prod_map.get("FIELD_VALUE"));
					%>
							<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("��н����") != -1){%>checked<%}%>>��н����&nbsp;
			 				<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("��Ӫ������") != -1){%>checked<%}%>>��Ӫ������&nbsp;
			 				<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("�Ʋ�������") != -1){%>checked<%}%>>�Ʋ�������&nbsp;
			 				<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("Ͷ������") != -1){%>checked<%}%>>Ͷ������&nbsp;
			 				<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("��������") != -1){%>checked<%}%>>��������&nbsp;
			 				<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("��������") != -1){%>checked<%}%>>��������&nbsp;
					<%}%>			 				
			 			</td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="2" style="BORDER-BOTTOM: 3px solid;"><font size="3"><b>֣������</b></font></td>
						<td class="tdnoall" colspan="11">
							<font size="3"><b>&nbsp; &nbsp; &nbsp; &nbsp; ��ί���˽�������˾�������ʽ�Ϊ��ί���˱��˺Ϸ����еĲƲ��������ڷǷ��㼯�����ʽ�������мƻ������Ρ�
							�����ʽ��������κθ��ˡ����˼�������֯�����ڷ����ϵ��κξ��ס�����Υ��������ŵ��������һ�з��ɺ�����ɱ�ί����ȫ���е���</b></font>
						</td>
					</tr>
					<tr>
						<td class="tdbottom" colspan="4" style="BORDER-BOTTOM: 3px solid;">
							<font size="2"><b>ί���˻������ǩ����</b></font>
						</td>
						<td class="tdbottom" colspan="7" style="letter-spacing:17px;BORDER-BOTTOM: 3px solid;" align="right">
							<font size="2" ><b>��ӡ����   ��   ��   ��</b></font>
						</td>
					</tr>
					<tr>
						<td class="tdright" width="1px" rowspan="2"><font size="2"><b>�ʸ��������д</b></font></td>
						<td class="tdnoall" colspan="12" valign="top" style="font-size:14;">
							<font size="2"><b>�������:<br></b></font>
							<input type="checkbox" value="" class="flatcheckbox" disabled>���������׵�����&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>���и��µĴ��֤��������һ���ң�&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>���й�Ʊ����������֤ȯ֤�����ʽ��˻����֤����ȯ�̻��йܻ������ߣ�&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>Ͷ���������мƻ���������Ƽƻ���֤��&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>Ͷ����ȯ����Ƽƻ�֤��&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>����Ͷ���˻����ʽ�֤��&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>Ͷ���ͱ��ղ�Ʒ֤��&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>���������ʲ�֤��&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>��������˰��˰֤��&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>��������֤��&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>ί�������֤��ԭ������ӡ��&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>���������֤��ԭ������ӡ��&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>ί������Ȩί����ԭ��&nbsp;<br>
						</td>
					</tr>
					<tr>
						<td class="tdnoall" colspan="3">
							<font size="2"><b>������:</b></font>
						</td>
						<td class="tdnoall" colspan="3">
							<font size="2"><b>������:</b></font>
						</td>
						<td class="tdnoall" colspan="6" style="letter-spacing:17px;" align="right">
							<font size="2"><b>�����£�   ��   ��  ��</b></font>
						</td>
					</tr>
					</table>	
					
					<table border="0" width="100%" cellspacing="1" cellpadding="4" id=table99>
					<tr>
						<td>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="��ӡ" onclick="javascript:table99.style.display = 'none';window.print();table99.style.display = '';">��ӡ(<u>P</u>)</button>	 
								&nbsp;&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();">����(<u>B</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</TD>
</TABLE>
</form>
</BODY>
</HTML>
<%
prod_local.remove();
cust_local.remove();
%>