<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
CustomerLocal local = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

String scust_id = request.getParameter("cust_id");
String [] cust_items = Utility.splitString(scust_id, ",");

//ҳ�渨������
Integer cust_id = new Integer(0);
List list = new ArrayList();
Map map = new HashMap();

//�ռ�����Ϣ
Integer cust_type = new Integer(0);
String contact_man = "";
String cust_tel = "";
String mobile = "";
String o_tel = "";
String h_tel = "";
String cust_name = "";
String post_address = "";
String post_code = "";
String tel = "";
String gov_prov_regional_name = "";
String gov_regional_name = "";

//�ļ�����Ϣ
String name = Argument.getDictContent("190001");
String telephone = Argument.getDictContent("190002");
String company = Argument.getDictContent("190003");
String address = Argument.getDictContent("190004");
String postcode = Argument.getDictContent("190005");

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
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media="screen">
.show     { display: none }
</style>
<style media="print">
.noprint     { display: none }
.trheight	 { height:50px }
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script language=javascript>
function doPrintSetup(){
//��ӡ����
WB.ExecWB(8,1)
}
function printreturn(){ //����
<% if (Utility.trimNull(request.getParameter("ret")).equals("proto")) {%>
	location = "/prototype/client_cust_info_detail.jsp?cust_id=<%=scust_id%>"
				+"&cust_level_name=<%=request.getParameter("cust_level_name")%>&is_deal_name=<%=request.getParameter("is_deal_name")%>";
<%}else{%>
	location = "client_query_list.jsp"
<%}%>
}
function doPrintPreview(){
//��ӡԤ��

WB.ExecWB(7,1)
}

function setSendMessager(flag,obj)
{
	var data = obj.split("$");
	utilityService.setSenderMessger(flag,data[0],<%=input_operatorCode%>,data[1],{callback: function(data){
		alert(data);
	}});
}

function doPrint(){
		
    if(document.theform.name_of_contents.value==""){
		sl_alert("<%=LocalUtilis.language("message.writeCommodity",clientLocale)%>");//����д�ڼ�Ʒ��
		return false;
	}
	window.open("printclient_ems_do.jsp?flag=2&name_of_contents="+document.theform.name_of_contents.value+"&cust_id="+checkedValue(document.theform.cust_id));
	//location = "printclient_ems_do.jsp?flag=2&name_of_contents="+arra+"&cust_id="+checkedValue(document.theform.cust_id));
}
</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post" action="printclient_ems.jsp">
	<!---�״򷽷�-->
	<table cellspacing=0 cellpadding=0 border=0 width="100%">
		<TR>
			<TD vAlign=top align=left>
				<table cellspacing=0 cellpadding=0 align=center width="100%" border=0>
					<tr>
						<td align="center" height="60px" class="show">
						</td>
					</tr>

<%
Calendar c =  Calendar.getInstance();
c.get(Calendar.YEAR);
if(cust_items != null && cust_items.length > 0)
{

	for(int i=0; i<cust_items.length; i++){
		cust_id = Utility.parseInt(cust_items[i], new Integer(0));

		if(!(cust_id.equals(new Integer(0))))
		{
			vo.setCust_id(cust_id);//ѭ����ÿͻ�ID�������Ϣ
			vo.setInput_man(input_operatorCode);
			list = local.listProcAll(vo);
			Iterator it = list.iterator();
			while(it.hasNext()){
				map = (Map)it.next();
				cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")), new Integer(0));
				if(cust_type.intValue() == 2)
				{
					cust_name = Utility.trimNull(map.get("CUST_NAME"));
					contact_man = Utility.trimNull(map.get("CONTACT_MAN"));
				}
				else{
					cust_name = "";
					contact_man = Utility.trimNull(map.get("CUST_NAME"));
				}
				cust_tel = Utility.trimNull(map.get("CUST_TEL"));
				mobile = Utility.trimNull(map.get("MOBILE"));
				o_tel = Utility.trimNull(map.get("O_TEL"));
				h_tel = Utility.trimNull(map.get("H_TEL"));
				post_address = Utility.trimNull(map.get("POST_ADDRESS"));
				post_code = Utility.trimNull(map.get("POST_CODE"));
				gov_prov_regional_name = Utility.trimNull(map.get("GOV_PROV_REGIONAL_NAME"));
				gov_regional_name = gov_prov_regional_name+" "+Utility.trimNull(map.get("GOV_REGIONAL_NAME"));
				String service_name = Utility.trimNull(map.get("SERVICE_MAN_NAME"));
				if(post_address.equals("")){
					post_address = Utility.trimNull(map.get("POST_ADDRESS2"));
				}
				tel = "";
				if(!cust_tel.equals("")){
					tel = cust_tel;
				}
				if(!h_tel.equals("")){
					tel = h_tel;
				}
				
				if(!o_tel.equals("")){
					tel = o_tel;
				}

				if(!"".equals(mobile)&& mobile!=null){
					tel = mobile+" / "+tel;
				}
			%>
					<TR>
						<TD  align=center height="50px">
							<TABLE border="0" width="100%" cellspacing="0" cellpadding="0">
							<TR>
								<TD class="tdbottom" align="center" colspan="20">
									<FONT size="5" face="����"><B>�����ؿ�ר���ʼ����鵥</B></FONT>
								</TD>
							</TR>
							</TABLE>
							<TABLE border="1"  width="100%" cellspacing="0" cellpadding="1" bordercolor="#000000">
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="1" width="8%" ><FONT size="2.5" face="����"><b>�ռľ�<BR/>ORIGINAL OFFICE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="3" width="12%"><FONT size="2.5" face="����">&nbsp;&nbsp;&nbsp;</FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="10%" ><FONT size="2.5" face="����"><b>�ռ�����<BR/>ACCEPTED DATE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4" width="20%"><FONT size="2.5" face="����">
									&nbsp;<%=c.get(Calendar.YEAR)%>��Y&nbsp;<%=c.get(Calendar.MONTH)+1%>��M&nbsp;<%=c.get(Calendar.DATE)%>��D&nbsp;<%=c.get(Calendar.HOUR_OF_DAY)%>ʱH</FONT></TD>
									<TD class="tdrightbottom" colspan="9" width="50%" ><FONT size="2.5" face="����" color="#b220000"><b>
									<input disabled type="checkbox" name="cust_id" value="<%=cust_id%>" checked="true"><br/>
									</b></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="1" width="8%" ><FONT size="2.5" face="����"><b>�ļ�������<BR/>FORM</b></FONT></TD>
									<TD class="tdrightbottom" colspan="3" width="12%"><FONT size="2.5" face="����">&nbsp;<%=Utility.trimNull(service_name) %></FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="10%"><FONT size="2.5" face="����"><b>�绰<BR/>PHONE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4" width="20%"><FONT size="2.5" face="����">&nbsp;<%=telephone %></FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="8%" ><FONT size="2.5" face="����"><b>�ռ�������<BR/>TO</b></FONT></TD>
									<TD class="tdrightbottom" colspan="3" width="12%"><FONT size="2.5" face="����">&nbsp;<%=contact_man %></FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="10%"><FONT size="2.5" face="����"><b>�绰<BR/>PHONE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4" width="20%"><FONT size="2.5" face="����">&nbsp;<%=tel %></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="9" width="50%"><FONT size="2.5" face="����"><b>��λ���� COMPANY NAME<BR/></b></FONT>
									<FONT size="2.5" face="����">&nbsp;<%//=company %></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="9" width="50%"><FONT size="2.5" face="����"><b>��λ���� COMPANY NAME<BR/></b></FONT>
									<FONT size="2.5" face="����">&nbsp;<%=cust_name %></FONT>
									</TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="9" width="50%"><FONT size="2.5" face="����"><b>��ַ ADDRESS</b><BR/></FONT>
									<FONT size="2.5" face="����">&nbsp;<%//=address %></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="9" width="50%"><FONT size="2.5" face="����"><b>��ַ ADDRESS</b> <%=gov_regional_name%><BR/></FONT>
									<FONT size="2.5" face="����">&nbsp;<%=post_address %></FONT>
									</TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="1" width="8%" ><FONT size="2.5" face=����><b>�û�����<BR/>CUSTOMER CODE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="3" width="12%" ><FONT size="2.5" face=����><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="10%" ><FONT size="2.5" face=����><b>��������<br/>POSTAL CODE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4" align="center" width="2%"><FONT size="2.5" face="����">&nbsp;<%//=postcode %></FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="8%" ><FONT size="2.5" face=����><b>����<BR/>CITY</b></FONT></TD>
									<TD class="tdrightbottom" colspan="3" width="12%" ><FONT size="2.5" face=����><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></FONT></TD>
									<TD class="tdrightbottom" colspan="1" width="10%" ><FONT size="2.5" face=����><b>��������<br/>POSTAL CODE</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4" align="left" width="2%"><FONT size="2.5" face="����">&nbsp;<%=post_code %></FONT></TD>
								</TR>
								<TR class="trheight"  style="word-spacing:30px">
									<TD class="tdrightbottom" colspan="1" align="center" ><FONT size="2.5" face=����><b><%out.print("��");%>�ź�<BR/>LETTER</b></FONT></TD>
									<TD class="tdrightbottom" colspan="3" align="center"><FONT size="2.5" face=����><b><%out.print("��");%>�ļ�����<BR/>DOCUMENT</b></FONT></TD>
									<TD class="tdrightbottom" colspan="1" align="center"><FONT size="2.5" face=����><b><%out.print("��");%>��Ʒ<br/>PARCEL</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4"><FONT size="2.5" face=����><b>��ϵ��Ʒ�����ʵ��д�ڼ����Ƽ�������<BR/>���豣�ۣ����ʵ�걨���۽����ɱ��۷ѡ�</b></FONT></TD>
									<TD class="tdrightbottom" colspan="4" width="20%" ><FONT size="2.5" face=����><b>����&nbsp;&nbsp;&nbsp;&nbsp;ǧ��<br/>POSTAL-CODE&nbsp;&nbsp;&nbsp;&nbsp;KG</b></FONT></TD>
									<TD class="tdrightbottom" colspan="5" width="30%" ><FONT size="2.5" face=����><b>���&nbsp;&nbsp;&nbsp;��&nbsp;��&nbsp;��&nbsp;=&nbsp;����<sup>3</sup></b></FONT></TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom" colspan="5" width="30%"  align="center">
										<FONT size="2.5" face="����"><b>�ڼ�Ʒ��<BR/>NAME OF CONTENTS</b></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="4" width="20%" align="center"><FONT size="2.5" face="����"><b>����<BR/>AMOUNT</b></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="4" width="20%"  align="left">
										<FONT size="2.5" face="����"><b>�ʷ�<BR/>CHARGE</b></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="2" width="10%" align="left"><FONT size="2.5" face="����"><b>���۷�<BR/>INSURANCE FEE</b></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="3" width="20%" align="left"><FONT size="2.5" face="����" >&nbsp;</FONT>
									</TD>
								</TR>
								<TR class="trheight">
									<TD class="tdrightbottom"  colspan="5" align="left" width="30%"><FONT size="2.5" face="����" >&nbsp;

									</FONT></TD>
									<TD class="tdrightbottom" colspan="4" width="20%"><FONT size="2.5" face="����" ></FONT><BR/><BR/><BR/></TD>
									<TD class="tdrightbottom" colspan="4" width="20%"  align="left">
										<FONT size="2.5" face="����"><b>�����ܼ�<BR/>CHARGE</b></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="2" width="20%" align="left"><FONT size="2.5" face="����"><b>�ռ���Աǩ����<BR/>ACCEPTED BY(SIGNATURE)</b></FONT>
									</TD>
									<TD class="tdrightbottom" colspan="3" width="20%" align="left"><FONT size="2.5" face="����" >&nbsp;</FONT>
									</TD>
								</TR>
								<TR class="trheight" style="word-spacing:30px">
									<TD class="tdrightbottom" colspan="9" width="16%" ><FONT size="2.5" face="����"><b>�Ƿ񱣼�&nbsp;
									��<%out.print("��");%>&nbsp;��<%out.print("��");%>&nbsp;&nbsp;
									���۽�&nbsp;��&nbsp;Ǫ&nbsp;��&nbsp;ʰ&nbsp;Ԫ����д��
									</b></FONT></TD>
									<TD class="tdrightbottom" colspan="9" width="50%" align="left" style="word-spacing:30px"><FONT size="2.5" face="����"><b>�ռ���ǩ��������д��������<BR/>RECEIVER'S SIGNATURE<BR/><BR/></b></FONT>
									<FONT size="2.5" face="����" >&nbsp;</FONT>
									<span align="right"><FONT size="2.5" face="����"><b>&nbsp;��Y&nbsp;��M&nbsp;��D&nbsp;ʱH</b></FONT></span>
								</TR>
								<TR>
									<TD class="tdrightbottom" colspan="3" width="20%" align="left"><FONT size="2.5" face="����"><b>������ǩ����<BR/>SENDER'S SIGNATURE</b></FONT>
									<TD class="tdrightbottom" colspan="6" align="left" width="30%"><FONT size="2.5" face="����" >&nbsp;<%=name %></FONT><BR/><BR/><BR/></TD>
									<TD class="tdrightbottom" colspan="3" width="50%" align="left"><FONT size="2.5" face="����"><b>��עREMARK<BR/></b></FONT>
									<TD class="tdrightbottom" colspan="6" align="left" width="30%"><FONT size="2.5" face="����" >&nbsp;</FONT><BR/><BR/><BR/></TD>
								</TR>
							</TABLE>
						</TD>
					</TR>
			<%
			}
		}
	}
}
%>
					<TR class="noprint">
						<TD align="right">
							<table id=printbtn border="0" width="100%">
								<tr>
									<td>
										�ļ�������:<input type="text" name="sender_name"  onkeydown="javascript:nextKeyPress(this)" value="<%=name %>" size = "25" onblur="javascript:setSendMessager(190001,this.value)">&nbsp;&nbsp;
										�ļ��˵绰:<input type="text" name="sender_tel"  onkeydown="javascript:nextKeyPress(this)" value="<%=telephone %>" size = "15" onblur="javascript:setSendMessager(190002,this.value)">&nbsp;&nbsp;
										�ļ��˵�λ:<input type="text" name="sender_comp"  onkeydown="javascript:nextKeyPress(this)" value="<%=company %>" size = "30" onblur="javascript:setSendMessager(190003,this.value)">&nbsp;&nbsp;
										�ļ��˵�ַ:<input type="text" name="sender_address"  onkeydown="javascript:nextKeyPress(this)" value="<%=address %>" size = "37" onblur="javascript:setSendMessager(190004,this.value)">&nbsp;&nbsp;
										�ļ����ʱ�:<input type="text" name="sender_post"  onkeydown="javascript:nextKeyPress(this)" value="<%=postcode %>" size = "6" onblur="javascript:setSendMessager(190005,this.value)">&nbsp;&nbsp;
									</td>
								</tr>
								<tr>
									<td align="right">
										<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
										<!--�״����÷���-->
										<FONT size="2.5" face="����"><b>�ڼ�Ʒ��  NAME OF CONTENTS :</b></FONT>
										<input type="text" name="name_of_contents"  onkeydown="javascript:nextKeyPress(this)" value="" size = "40">&nbsp;&nbsp;&nbsp;
										<%if(user_id.intValue() != 17){ %>
										<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrint();">��ӡ(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
										<%}else{ %>
										<button type="button"  class="xpbutton3" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:window.print();">��ӡ(<u>P</u>)</button>
										&nbsp;&nbsp;&nbsp;
										<button type="button"  class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">ҳ������(<u>A</u>)</button>
										&nbsp;&nbsp;&nbsp;
										<button type="button"  class="xpbutton4" accessKey=c id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(6,1);">��ӡ������(<u>C</u>)</button>
										&nbsp;&nbsp;&nbsp;
										<%} %>
										<!--<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:doPrintPreview()">��ӡԤ��(<u>P</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp; -->
										<button type="button"  class="xpbutton3" accessKey=b id="btnReturn" name="btnReturn" onclick="javascript:printreturn();">����(<u>B</u>)</button>

									</td>
								</tr>
							</table>
						</TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
	</TABLE>
</FORM>
</BODY>
</HTML>
<%
local.remove();
%>