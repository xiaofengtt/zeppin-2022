<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
Integer input_date = Utility.parseInt(request.getParameter("input_date"), new Integer(0)); // �ʼ�����
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh")).trim();
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));

String[] totalColumn = new String[0];
int pageSize = Utility.parseInt(sPagesize,10);

PostSendLocal local = EJBFactory.getPostSend();
PostSendVO vo = new PostSendVO();

vo.setInput_date(input_date);
vo.setProduct_id(product_id);
vo.setContract_sub_bh(contract_sub_bh);


Map content_map = new HashMap();
IPageList pageList = local.pagelist_query(vo, totalColumn,Utility.parseInt(sPage,1),pageSize);
List list = pageList.getRsList();
for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);

	String post_content = Utility.trimNull((String)map.get("POST_CONTENT"));
	String[] contents = post_content.split("\\|"); //
	String s = "";
	for (int j=0; j<contents.length; j++) {
		if (contents[j].equals("1")) {
			if (j==0)
				s += "��������";
			else if (j<contents.length-1) 
				s += "����������";
			else 
				s += "�ͳ�������";
		} else if (contents[j].equals("2")) {
			if (j==0)
				s += "ȷ�ϵ�";
			else if (j<contents.length-1) 
				s += "��ȷ�ϵ�";
			else 
				s += "��ȷ�ϵ�";
		} else if (contents[j].equals("3")) {
			if (j==0)
				s += "������";
			else if (j<contents.length-1) 
				s += "��������";
			else 
				s += "�͹�����";
		} else if (contents[j].equals("4")) {
			if (j==0)
				s += "��ʱ��Ϣ��¶";
			else if (j<contents.length-1) 
				s += "����ʱ��Ϣ��¶";
			else 
				s += "����ʱ��Ϣ��¶";
		} else if (contents[j].equals("5")) {
			if (j==0)
				s += "��ֹ����";
			else if (j<contents.length-1) 
				s += "����ֹ����";
			else 
				s += "����ֹ����";
		} else if (contents[j].equals("6")) {
			if (j==0)
				s += "����";
			else if (j<contents.length-1) 
				s += "������";
			else 
				s += "������";
		} 
	}
	map.put("POST_CONTENT", s);
}


local.remove();
%>

<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<SCRIPT language="javascript">
<!--�ɹ���־-->

/*��֤����*/
function validateForm(form){		
	var post_no = trim(document.theform.post_no.value);
	var errmsg = '';
	var post_content = "";
	var content = document.getElementsByName('content');
	for (var i=0; i<content.length; i++) {
		if (content[i].checked) 
			if (post_content=="") 
				post_content += content[i].value;
			else 
				post_content += "|" + content[i].value;
	}
	document.theform.post_content.value = post_content;

	if (post_no=='') {
		errmsg += 'δ�����ʼĵ��ţ��������ʼĵ��ţ�\n';
	}	
	if (post_content == "") {
		errmsg += 'δָ���ʼ����ݣ���ѡ������һ���ʼ����ݣ�\n';
	}

	if (errmsg!='') {
		sl_alert(errmsg);
		return false;
	}

	return sl_check_update();	
}



</script>
</HEAD>

<BODY class="BODY">
<form name="theform" method="post" >
<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
<font color="#215dc6"><b><%=menu_info%></b></font>
<hr noshade color="#808080" size="1">

<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		<td width=" " align="center">�ʼ����� </td>
		<td width=" " align="center">��ͬ���</td>
		<td width=" " align="center">�ͻ�����</td>
		<td width=" " align="center">�ʼĵ��� </td>
		<td width=" " align="center">�ʼ����� </td>
		<td width=" " align="center">��ע </td>
			
	</tr>
<%
int iCount = 0;
int iCurrent = 0;
if (list.size()!=0 && list!=null) {
for(int i=0;i<list.size();i++){
	Map	map = (Map)list.get(i);
	
	
%>
	<tr class="tr<%=iCount%2%>">	
		
		<td align="center"><%=Utility.trimNull(map.get("INPUT_DATE"))%></td>
		<td align="center"><%=Utility.trimNull(map.get("CONTRACT_SUB_BH")) %></td>
		<td align="center"><%=Utility.trimNull(map.get("CUST_NAME")) %></td>
		<td align="center"><%=Utility.trimNull(map.get("POST_NO")) %></td>
		<td align="center"><%=Utility.trimNull(map.get("POST_CONTENT"))%></td>
		<td align="center"><%=Utility.trimNull(map.get("SUMMARY")) %> </td>	
	</tr>
<% }}
	
for(int i=0;i<(pageSize-iCount);i++){ %>      	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>  
            <td align="center">&nbsp;</td>  
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- �ϼ� --><!-- �� -->
			<td align="left" class="tdh" colspan="10"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
		</tr> 
</table>
<TABLE>
	<tr>
    	<td>
    		<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
    	</td>
    </tr>
		
</TABLE>
<div align="right">
	<br>
	
	<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.close();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>

</div>
</form>
</BODY>
</HTML>