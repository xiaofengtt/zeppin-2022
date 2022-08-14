<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TmanagerteammembersVO vo = new TmanagerteammembersVO();
TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();

Integer serial_no = null;
Integer team_id = null;
Integer managerid = null;
String description = null;
Integer input_man = null;

//������Ϣ
if(request.getMethod().equals("POST")){
	team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),null);
	managerid = Utility.parseInt(Utility.trimNull(request.getParameter("managerid")),null); // �Ŷӳ�Ա = ���� ֻ�����Ǽ���ͬ
	description = Utility.trimNull(request.getParameter("description"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);

	vo.setTeam_id(team_id);
	vo.setManagerid(managerid);
	vo.setDescription(description);
	vo.setInput_man(input_man);

	try{
		tmanagerteams_Bean.append(vo);
		bSuccess = true;
	}catch(Exception e){        
        String message = enfo.crm.tools.LocalUtilis.language("message.memberAlreadyExists", clientLocale);//���Ŷ����Ѵ��ڸó�Ա��������ѡ��
		out.println("<script language=\"javascript\">alert(\""+message+"��"+""+"\");window.returnValue=null;window.close();</script>");
		bSuccess = false;
	}
}
%>
<HTML>
<HEAD>
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("index.menu.addTeam",clientLocale)%> </title><!-- �����Ŷ� -->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

	<% if(bSuccess){ %>
		window.dialogArguments.newlist_flag = 1;
		window.returnValue = true;
		window.close();
	<%}%>

	/*��֤����*/
	function validateForm(form){
        //��ѡ��ͻ�����
		if(theform.managerid.value == ""){alert("<%=LocalUtilis.language("message.selectAccountManager",clientLocale)%> ��");return false;}		
		return sl_check_update();	
	}

	/*����*/
	function SaveAction(){
		if(document.getElementsByName('theform')[0].onsubmit()){
			document.getElementsByName('theform')[0].submit();
		}	
	}
	
	/*ȡ��*/
	function CancelAction(){
		window.returnValue=null;
		window.close();
	}

</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/team_number_new.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<input type="hidden" name="team_id" value="<%=Utility.trimNull(request.getParameter("team_id"))%>">
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0">	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.memberName",clientLocale)%> ��</td><!-- ��Ա���� -->
			<td>
				<select name="managerid" style="width: 110px;">
					<%=Argument.getManager_Value(null)%>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.memberDescription",clientLocale)%> ��</td><!-- ���� -->
			<td colspan="3">
				<textarea rows="3" name="description" onkeydown="javascript:nextKeyPress(this)" cols="62"></textarea>			
			</td>
		</tr>
	</table>
</div>

<div align="right">
	<br>
    <!-- ���� -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp;
    <!-- ���� -->
	<button type="button" class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>