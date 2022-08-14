<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
try{
	ContractLocal contract = EJBFactory.getContract();
	String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
	String cust_name = Utility.trimNull(request.getParameter("cust_name"));
	String product_name = Utility.trimNull(request.getParameter("product_name"));
	int dealFlag = Utility.parseInt(request.getParameter("dealFlag"),1);

	// IPageList pageList = contract.queryOldTemp(product_name,query_contract_bh,cust_name,2, _page, pagesize);
	List list = contract.queryOldTemp(product_name,contract_bh,cust_name,dealFlag, 1, -1).getRsList();
	
	//contract.getOldTempNext();	
	Map map = (Map)list.get(0);
	Integer str_qs_date = Utility.parseInt((String)map.get("��ͬǩ������"),new Integer(0));
	Integer str_jk_date = Utility.parseInt((String)map.get("�ɿ�����"),new Integer(0));

	int bzjFlag = Argument.getSyscontrolValue_intrust("BZJCLFS");//Argument.getSyscontrolValue("BZJCLFS");//��֤�𿪹� 1��,2û��
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
<style media="print">
.noprint { display: none }
</style>

<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>

</script>
<BODY class="body body-nox">
<FORM>
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD><b><%=Utility.trimNull(map.get("��Ŀ����"))%></b></TD>
		</TR>
		<TR>
			<TD width="100%">
				<FIELDSET >
					<LEGEND>��ͬ��Ϣ</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">��ͬ���:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("��ͬ���"))%>"></TD>
							<TD align="right">��ͬǩ������:</TD>	
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_qs_date)%>"></TD>						
						</TR>
						<TR>
							<TD align="right">�ɿ�����:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_jk_date)%>"></TD>
							<TD align="right">��ֹ����:</TD>
							<TD><input class="edline" readonly  type="text" size="25" 
									value="<%=Format.formatDateCn(Utility.parseInt((String)map.get("��ͬ��ֹ����"), new Integer(0)))%>"/></TD>
						</TR>
						<TR>	
							<TD align="right">��ͬ����:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("��ͬ����"))%>"></TD>
							<TD align="right">��ͬ���:</TD>
							<TD><input class="edline"  type="text" size="25" 
									value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("��ͬ���")).trim(), new BigDecimal(0)))%>"></TD>
						</TR>
						<%if(bzjFlag == 1) {%>
						<TR>	
							<TD align="right">�Ƿ�Ϊ��֤��:</TD>
							<TD colspan="3"><%=Utility.trimNull(map.get("��֤���־")) %>
								��:<input type="radio" name="bzj_flag" class="flatcheckbox" <%=Utility.parseInt((String)map.get("��֤���־"),new Integer(2)).equals(new Integer(1)) ? "checked" : "disabled"%>>&nbsp;&nbsp;
								��:<input type="radio" name="bzj_flag" class="flatcheckbox" <%=Utility.parseInt((String)map.get("��֤���־"),new Integer(2)).equals(new Integer(2)) ? "checked" : "disabled"%>>
							</TD>
						</TR>
						<%}%>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND>ί������Ϣ</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">ί����:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί����"))%>"></TD>
							<TD align="right">ί��������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί��������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">��ϵ��ʽ:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί������ϵ��ʽ"))%>"></TD>
							<TD align="right">��ַ:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί���˵�ַ"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">��������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί�����ʱ�"))%>"></TD>
							<TD align="right">֤������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί����֤������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">֤�����:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί����֤�����"))%>"></TD>
							<TD align="right">���˴���:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί���˷��˴���"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�̶��绰:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί���˹̶��绰"))%>"></TD>
							<TD align="right">�ֻ�:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί�����ֻ�"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�����ʼ�:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ί���˵����ʼ�"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>
						
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND>��������Ϣ</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("������"))%>"></TD>
							<TD align="right">����������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("����������"))%>"></TD>
						</TR>
						<TR>	
							<TD align="right">�������ȼ���:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("���漶��"))%>"></TD>
							<TD align="right">���漶��:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�������ȼ���"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">��ϵ��ʽ:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("��������ϵ��ʽ"))%>"></TD>
							<TD align="right">��ַ:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�����˵�ַ"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">��������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�������ʱ�"))%>"></TD>
							<TD align="right">֤������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("������֤������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">֤������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("������֤�����"))%>"></TD>
							<TD align="right">���˴���:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�����˷��˴���"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�̶��绰:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�����˹̶��绰"))%>"></TD>
							<TD align="right">�ֻ�:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�������ֻ�"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�����ʼ�:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�����˵����ʼ�"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND>������Ϣ</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">�������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("�������"))%>"></TD>
							<TD align="right">��������:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("��������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">������ע:</TD>
							<TD colspan="3"><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("������ע"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">ʡ:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("ʡ"))%>"></TD>
							<TD align="right">��:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("��"))%>"></TD>
						</TR>				
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND>������Ϣ</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">��������:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("������������"))%>"></TD>
							<TD align="right">֧������:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("֧������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�����˺�:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Format.formatBankNo(Utility.trimNull(map.get("�����˺�")))%>"></TD>
							<TD align="right">��������:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("��������"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�Ʋ����:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("�Ʋ����"))%>"></TD>
							<TD align="right">�Ʋ�����:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("�Ʋ�����"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">�ɿʽ:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("�ɿʽ"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>						
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD align="right"><button type="button"  class="xpbutton3" accessKey=b onclick="javascript:disableAllBtn(true);history.back();">����(<u>B</u>)</button></TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
</BODY>
</HTML>
<%
}catch(Exception e){
	throw e;
}
%>