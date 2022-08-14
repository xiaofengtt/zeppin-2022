<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>

<%
//��ò���
Integer define_id = Utility.parseInt(request.getParameter("define_id"), new Integer(0));//�ּ�ID
int level_id = Utility.parseInt(request.getParameter("level_id"), 0);//�ڵ���
Integer detail_id = Utility.parseInt(request.getParameter("detail_id"), new Integer(0));//�ּ���ϸID
Integer canmodi = Utility.parseInt(request.getParameter("canmodi"), new Integer(0));//�Ƿ��ɾ��
String flag = Utility.trimNull(request.getParameter("flag"));
String define_name = Utility.trimNull(request.getParameter("define_name"));
Integer default_value = Utility.parseInt(request.getParameter("default_value"), null);
Integer parent_value = Utility.parseInt(request.getParameter("parent_value"), null);
String summary = Utility.trimNull(request.getParameter("summary"));
Integer min_value = Utility.parseInt(request.getParameter("min_value"),null);
Integer max_value = Utility.parseInt(request.getParameter("max_value"),null);

int table_flag = Utility.parseInt(request.getParameter("table_flag"),1);

CustClassDefineLocal define_local = EJBFactory.getCustClassDefine();
CustClassDefineVO define_vo = new CustClassDefineVO();
CustClassDetailLocal detail_local = EJBFactory.getCustClassDetail();
CustClassDetailVO detail_vo = new CustClassDetailVO();

List list = new ArrayList();
Map map = new HashMap();
boolean bSuccess = false;


//��ñ༭��Ϣ
if(flag == "edit" || "edit".equals(flag))
{
	if(table_flag==2)//(level_id != 0 && (level_id % 2 != 0))
	{
		if(define_id != null && !(define_id.equals(new Integer(0))))
		{
			//���Ҫ�޸ĵķּ�
			define_vo.setClass_define_id(define_id);
			define_vo.setInput_man(input_operatorCode);
			list = define_local.query(define_vo);
			if(list.size() > 0)
				map = (Map)list.get(0);
		}
	}else{
		if(detail_id != null && !(detail_id.equals(new Integer(0))))
		{
			//���Ҫ�޸ĵķּ�֮��ϸ
			detail_vo.setClass_detail_id(detail_id);
			detail_vo.setInput_man(input_operatorCode);
			list = detail_local.query(detail_vo);
			if(list.size() > 0)
				map = (Map)list.get(0);
		}
	}
}

//����ͻ��ּ���Ϣ
if(request.getMethod().equals("POST"))
{
	if(table_flag==2)//(level_id != 0 && (level_id % 2 != 0))
	{
		define_vo.setClass_define_id(define_id);
		define_vo.setClass_define_name(define_name);
		define_vo.setDefault_value(default_value);
		define_vo.setParent_value(parent_value);
		define_vo.setSummary(summary);
		define_vo.setInput_man(input_operatorCode);
		define_local.modify(define_vo);
	}else{
		
		detail_vo.setClass_detail_id(detail_id);
		detail_vo.setClass_detail_name(define_name);
		detail_vo.setClass_minvalue(min_value);
		detail_vo.setClass_maxvalue(max_value);
		detail_vo.setInput_man(input_operatorCode);
		detail_local.modify(detail_vo);
	}
	bSuccess = true;
}
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK HREF="<%=request.getContextPath()%>/includes/default.css" TYPE="text/css" REL="stylesheet">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css
	rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/includes/financing.js"></script>
<script language="javascript">
<%if (bSuccess)
{
%>
	window.returnValue = 1;
	window.close();
<%}%>

//����֤
function validateForm(form)
{	
	if(!sl_check(document.getElementsByName("define_name")[0], "<%=LocalUtilis.language("message.ClassificationName",clientLocale)%> ", 60, 1))		return false; //nvarchar��60��//��������
	if(sl_confirm("<%=LocalUtilis.language("message.saveUpdate",clientLocale)%> "))//�����޸�
	{
		document.theform.flag.value = "";
		document.theform.submit();
	}	
}
</script>
</HEAD>
<BODY leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0"
	onkeydown="javascript:chachEsc(window.event.keyCode)" class="BODY">
<form name="theform" method="post" action="client_class_define_edit.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="define_id" value="<%=define_id%>">
<input type="hidden" name="flag" value="<%=flag%>">
<input type="hidden" name="level_id" value="<%=level_id%>">
<input type="hidden" name="detail_id" value="<%=detail_id%>">
<input type="hidden" name="table_flag" value="<%=table_flag %>"> 
<!--�������-->

<input type="hidden" name="default_value" value="<%=Utility.trimNull(map.get("DEFAULT_VALUE"))%>">
<input type="hidden" name="parent_value" value="<%=Utility.trimNull(map.get("PARENT_VALUE"))%>">


<div style="height: 10px;"></div>
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<font color="#215dc6"><b><%=LocalUtilis.language("menu.CustomerClassification",clientLocale)%> </b></font><!--�ͻ�����-->
	<hr noshade color="#808080" size="1">
</div>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">
		<tr>
			<td align="right">*<%=LocalUtilis.language("message.ClassificationName",clientLocale)%> ��</td><!--��������-->
			<td>
				<%if(table_flag==2){%>
				<input type="text" name="define_name" size="50" value="<%=Utility.trimNull(map.get("CLASSDEFINE_NAME"))%>" <%if(canmodi.intValue() == 2) {out.print("readonly class='edline'");}%> onkeydown="javascript:nextKeyPress(this)">
				<%}else{%>
				<input type="text" name="define_name" size="30" value="<%=Utility.trimNull(map.get("CLASSDETAIL_NAME"))%>" <%if(canmodi.intValue() == 2) {out.print("readonly class='edline'");}%> onkeydown="javascript:nextKeyPress(this)">
				<%}%>
			</td>
		</tr>
		<tr <%out.print("style='display: none;'");%>>
			<td align="right"><%=LocalUtilis.language("class.min",clientLocale)%> ��</td><!--��Сֵ-->
			<td><input type="text" name="min_value" size="30" value="<%=Utility.trimNull(map.get("CLASS_MINVALUE"))%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr <%out.print("style='display: none;'");%>>
			<td align="right"><%=LocalUtilis.language("class.max",clientLocale)%> ��</td><!--���ֵ-->
			<td><input type="text" name="max_value" size="30" value="<%=Utility.trimNull(map.get("CLASS_MAXVALUE"))%>" onkeydown="javascript:nextKeyPress(this)"></td>
		</tr>
		<tr <%out.print("style='display: none;'");%>>
			<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%> ��</td><!--��ע-->
			<td>
				<textarea rows="3" name="summary" onkeydown="javascript:nextKeyPress(this)" cols="50"><%=Utility.trimNull(map.get("SUMMARY"))%></textarea>
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
	<!--����-->
    <button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){ document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
	<!--ȡ��-->
    <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>
