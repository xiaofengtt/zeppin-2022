<%@ page language="java" pageEncoding="GBK" import="enfo.crm.system.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
String fieldName = Utility.trimNull(request.getParameter("fieldName"));
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language="javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.3.2.min.js"></SCRIPT>
<script language=javascript>
function selectField(){
    window.returnValue = document.theform.fields.value+"$"+document.theform.fields.options[document.theform.fields.selectedIndex].text;
    window.close();
}
</script>
</HEAD>
<BODY class="BODY">

<form name="theform">
<%@ include file="/includes/waiting.inc"%>
<div>
    <table border="0" width="100%" cellspacing="0" cellpadding="0">
	    <tr><!--�ͻ���Ϣ�����ֶ�ӳ��-->
		    <td><img src="<%=request.getContextPath()%>/images/member.gif" align="absBottom" border=0 width="32" height="28"><b><%=menu_info%>>><%=LocalUtilis.language("message.importFiledMap",clientLocale)%> </b></td>
	    </tr>
	    <tr>
	        <td>
	            <hr/>
	        </td>
	    </tr>
	    <tr>
	        <td>
	            <%=LocalUtilis.language("message.filedToFiled",clientLocale)%> :<!--��ѡ�����ֶζ�Ӧ��ӳ���ֶ�-->
	        </td>
	    </tr>
	    <tr>
	        <td>
	            <br/>
	            <%=fieldName%>
	        </td>
	    </tr>
	    <tr>
	        <td>
	            <select style="width:300" name="fields" id="fields">
	                <option value='cust_name'>�ͻ�����</option><!--�ͻ�����-->
                    <option value='cust_type_name'>�ͻ����</option><!--�ͻ����-->
                    <option value='card_type_name'>֤������</option><!--֤������-->
                    <option value='card_id'>֤������</option><!--֤������-->
                    <option value='post_address'>�ʼĵ�ַ</option><!--�ʼĵ�ַ-->
                    <option value='post_code'>��������</option><!--��������-->
                    <option value='mobile'>�ֻ�1</option><!--�ֻ�-->
                    <option value='cust_tel'>�ֻ�2</option><!--�ֻ�-->
                    <option value='e_mail'>�����ʼ�</option><!--�����ʼ�-->
                    <option value='contact_man'>��ϵ��</option><!--��ϵ��-->
                    <option value='service_man_name'>�ͻ�����</option><!--�ͻ�����-->
                    <option value='touch_type_name'>��ϵ��ʽ</option><!--��ϵ��ʽ-->
                    <option value='cust_source_name'>�ͻ���Դ</option><!--�ͻ���Դ-->
                    <option value='age'>����</option><!--����-->
                    <option value='potenital_money'>Ǳ�ڹ�����</option><!--Ǳ�ڹ�����-->
					<option value='cust_id'>�ͻ�ID</option><!--�ͻ�ID-->
					<option value='birthday'>��������</option><!--��������-->
					<option value='sex_name'>�Ա�</option><!--�Ա�-->
					<option value='voc_type_name'>ְҵ/��ҵ</option><!--ְҵ/��ҵ-->
					<option value='h_tel'>��ͥ�绰</option><!--��ͥ�绰-->
					<option value='o_tel'>��˾�绰</option><!--��˾�绰-->
					<option value='mobile2'>�ֻ�2</option><!--�ֻ�2-->
					<option value='fax'>����</option><!--����-->
					<option value='post_address2'>�ʼĵ�ַ2</option><!--�ʼĵ�ַ2-->
					<option value='post_code2'>��������2</option><!--��������2-->
					<option value='legal_man'>��������</option><!--����-->

					<option value='company_unit'>���ڵ�λ</option><!--���ڵ�λ-->
					<option value='company_depart'>���ڲ���</option><!--���ڲ���-->
					<option value='company_posistion'>ְλ</option><!--ְλ-->
	            </select> 
	        </td>
	    </tr>
	    <tr>
	        <td align="right">
	            <br>
	            <button type="button"  class="xpbutton3" onclick="javascript:selectField();;"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--ȷ��-->
	             <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--ȡ��-->
	        </td>
	    </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>