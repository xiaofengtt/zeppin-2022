<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,enfo.crm.customer.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;
Integer input_date = Utility.parseInt(request.getParameter("input_date"), new Integer(0)); // 邮寄日期
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String contract_sub_bh = Utility.trimNull(request.getParameter("contract_sub_bh")).trim();
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));;
String post_no = "";
String post_content = "";
String summary = "";


PostSendLocal local = EJBFactory.getPostSend();
PostSendVO vo = new PostSendVO();

vo.setInput_date(input_date);
vo.setProduct_id(product_id);
vo.setContract_sub_bh(contract_sub_bh);

Map map = null;
Map content_map = new HashMap();
List list = local.list_query(vo);
if (list.size()>0) {
	map = (Map)list.get(0);
	post_no = Utility.trimNull(map.get("POST_NO"));
	post_content = Utility.trimNull((String)map.get("POST_CONTENT"));
	summary = Utility.trimNull(map.get("SUMMARY"));
	
	String[] contents = post_content.split("\\|"); //
	for (int i=0; i<contents.length; i++) 
		content_map.put(contents[i], new Boolean(true));
}

//保存信息
if (request.getMethod().equals("POST")){	
	post_no = Utility.trimNull(request.getParameter("post_no")).trim();	
	post_content = Utility.trimNull(request.getParameter("post_content")).trim();
	summary = Utility.trimNull(request.getParameter("summary")).trim();	
	
	
	String[] contents = post_content.split("\\|"); //
	content_map.clear(); //
	for (int i=0; i<contents.length; i++) 
		content_map.put(contents[i], new Boolean(true));

	vo.setPost_no(post_no);
	vo.setPost_content(post_content);
	vo.setSummary(summary);
	vo.setInput_man(input_operatorCode);
	vo.setSerial_no(serial_no);

	try {
		local.modi(vo);
		bSuccess = true;
	} catch (Exception e) {
		String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
		out.println("<script type=\"text/javascript\">alert('"+message+","+e.getMessage()+"');</script>");
	}	
}

local.remove();
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
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
<!--成功标志-->
var bSuccess = <%=bSuccess%>;
window.onload = function() {		
	if (bSuccess){		
		sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ！");//保存成功
		window.location.href = "postsend.jsp";
	}
};

/*验证数据*/
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
		errmsg += '未输入邮寄单号！请输入邮寄单号！\n';
	}	
	if (post_content == "") {
		errmsg += '未指定邮寄内容！请选择至少一项邮寄内容！\n';
	}

	if (errmsg!='') {
		sl_alert(errmsg);
		return false;
	}

	return sl_check_update();	
}

/*保存*/
function SaveAction(){
	if(document.getElementsByName('theform')[0].onsubmit()){
		document.getElementsByName('theform')[0].submit();
	}	
}

/*取消*/
function CancelAction(){
	location="postsend.jsp";
}
</script>
</HEAD>

<BODY class="BODY body-nox">
<form name="theform" method="post" action="postsend_edit.jsp" onsubmit="javascript:return validateForm(this);">
<div class="page-title">
<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<table border="0" width="100%" cellspacing="5" cellpadding="0" class="table-popup">
	<tr>
		<td width="200px" align="right">邮寄日期 ：</td>
		<td align="left">
			<input type="hidden" name="input_date" value="<%=input_date%>"/>
			<input type="text" class="edline" readonly="readonly" size="10"
				value="<%=Format.formatDateLine(input_date)%>"/>	
		</td>
	</tr>

	<tr>	
		<td width="200px" align="right">合同编号 ：</td>
		<td align="left">
			<input type="hidden" name="product_id" value="<%=product_id%>"/>
			<input type="hidden" name="contract_sub_bh" value="<%=contract_sub_bh%>"/>
			<input type="hidden" name="serial_no" value="<%=serial_no%>"/>
			<input type="text" class="edline" readonly="readonly"  size="55"
				value="<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>[<%=contract_sub_bh%>]"/>		
		</td>	
	</tr>

	<tr>
		<td width="200px" align="right">邮寄单号 ：</td>
		<td align="left">
			<input type="text" name="post_no" id="post_no" size="30" value="<%=post_no%>"/>
		</td>		
	</tr>
	
	<tr>	
		<td width="200px" align="right">邮寄内容 ：</td>
		<td align="left">
			<table>
				<tr>
					<td><input type="checkbox" name="content" value="1" class="flatcheckbox"
						<%=content_map.get("1")!=null?"checked=\"checked\"":""%>/>成立公告</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="2" class="flatcheckbox"
						<%=content_map.get("2")!=null?"checked=\"checked\"":""%>/>确认单</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="3" class="flatcheckbox"
						<%=content_map.get("3")!=null?"checked=\"checked\"":""%>/>管理报告</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="4" class="flatcheckbox"
						<%=content_map.get("4")!=null?"checked=\"checked\"":""%>/>临时信息披露</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="5" class="flatcheckbox"
						<%=content_map.get("5")!=null?"checked=\"checked\"":""%>/>终止公告</td></tr>
				<tr>
					<td><input type="checkbox" name="content" value="6" class="flatcheckbox"
						<%=content_map.get("6")!=null?"checked=\"checked\"":""%>/>其他</td></tr>
			</table>
			<input type="hidden" name="post_content" value=""/>
		</td>	
	</tr>

	<tr>
		<td width="200px" align="right">备注 ：</td>
		<td align="left"><textarea rows="3" name="summary"
			onkeydown="javascript:nextKeyPress(this)" cols="62"><%=summary%></textarea></td>
	</tr>
</table>

<div align="right">
	<br>
    <!-- 保存 -->
	<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- 返回 -->
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>