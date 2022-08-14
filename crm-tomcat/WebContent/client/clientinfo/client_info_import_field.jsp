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
	    <tr><!--客户信息导入字段映射-->
		    <td><img src="<%=request.getContextPath()%>/images/member.gif" align="absBottom" border=0 width="32" height="28"><b><%=menu_info%>>><%=LocalUtilis.language("message.importFiledMap",clientLocale)%> </b></td>
	    </tr>
	    <tr>
	        <td>
	            <hr/>
	        </td>
	    </tr>
	    <tr>
	        <td>
	            <%=LocalUtilis.language("message.filedToFiled",clientLocale)%> :<!--请选择导入字段对应的映射字段-->
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
	                <option value='cust_name'>客户名称</option><!--客户名称-->
                    <option value='cust_type_name'>客户类别</option><!--客户类别-->
                    <option value='card_type_name'>证件类型</option><!--证件类型-->
                    <option value='card_id'>证件号码</option><!--证件号码-->
                    <option value='post_address'>邮寄地址</option><!--邮寄地址-->
                    <option value='post_code'>邮政编码</option><!--邮政编码-->
                    <option value='mobile'>手机1</option><!--手机-->
                    <option value='cust_tel'>手机2</option><!--手机-->
                    <option value='e_mail'>电子邮件</option><!--电子邮件-->
                    <option value='contact_man'>联系人</option><!--联系人-->
                    <option value='service_man_name'>客户经理</option><!--客户经理-->
                    <option value='touch_type_name'>联系方式</option><!--联系方式-->
                    <option value='cust_source_name'>客户来源</option><!--客户来源-->
                    <option value='age'>年龄</option><!--年龄-->
                    <option value='potenital_money'>潜在购买力</option><!--潜在购买力-->
					<option value='cust_id'>客户ID</option><!--客户ID-->
					<option value='birthday'>出生日期</option><!--出生日期-->
					<option value='sex_name'>性别</option><!--性别-->
					<option value='voc_type_name'>职业/行业</option><!--职业/行业-->
					<option value='h_tel'>家庭电话</option><!--家庭电话-->
					<option value='o_tel'>公司电话</option><!--公司电话-->
					<option value='mobile2'>手机2</option><!--手机2-->
					<option value='fax'>传真</option><!--传真-->
					<option value='post_address2'>邮寄地址2</option><!--邮寄地址2-->
					<option value='post_code2'>邮政编码2</option><!--邮政编码2-->
					<option value='legal_man'>法人姓名</option><!--法人-->

					<option value='company_unit'>所在单位</option><!--所在单位-->
					<option value='company_depart'>所在部门</option><!--所在部门-->
					<option value='company_posistion'>职位</option><!--职位-->
	            </select> 
	        </td>
	    </tr>
	    <tr>
	        <td align="right">
	            <br>
	            <button type="button"  class="xpbutton3" onclick="javascript:selectField();;"><%=LocalUtilis.language("message.ok",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--确认-->
	             <button type="button"  class="xpbutton3" onclick="javascript:window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> </button>&nbsp;&nbsp;&nbsp;<!--取消-->
	        </td>
	    </tr>
    </table>
</div>
<%@ include file="/includes/foot.inc"%>
</form>    
    
</BODY>
</HTML>