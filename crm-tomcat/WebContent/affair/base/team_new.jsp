<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*" %>
<!-- @ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %> -->
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
boolean bSuccess = false;

TmanagerteamsVO vo = new TmanagerteamsVO();
TmanagerteamsLocal tmanagerteams_Bean = EJBFactory.getTmanagerteams();

String team_no = null;
String team_name = null;
Integer create_date = null;
Integer leader = null;
String description = null;
Integer input_man = null;
Integer team_id = null;
Integer mark_flag = null;
Integer parent_id = null;

//������Ϣ
if(request.getMethod().equals("POST")){
	team_no = Utility.trimNull(request.getParameter("team_no"));
	team_name = Utility.trimNull(request.getParameter("team_name"));
	create_date = Utility.parseInt(Utility.trimNull(request.getParameter("create_date")),null);
	leader = Utility.parseInt(Utility.trimNull(request.getParameter("leader")),null);
	description = Utility.trimNull(request.getParameter("description"));
	input_man = Utility.parseInt(Utility.trimNull(request.getParameter("input_man")),input_operatorCode);
	team_id = Utility.parseInt(Utility.trimNull(request.getParameter("team_id")),null);
	mark_flag = Utility.parseInt(Utility.trimNull(request.getParameter("mark_flag")),new Integer(2));
	parent_id = Utility.parseInt(Utility.trimNull(request.getParameter("parent_id")),new Integer(2));
	
	vo.setTeam_no(team_no);
	vo.setTeam_name(team_name);
	vo.setCreate_date(create_date);
	vo.setLeader(leader);
	vo.setDescription(description);
	vo.setInput_man(input_man);
	vo.setTeam_id(team_id);
	vo.setMark_flag(mark_flag);
	vo.setParent_id(parent_id);
	
	String message = "";	
	try{
		int _team_id = tmanagerteams_Bean.append(vo).intValue();
		if(_team_id != 0){            
            message = enfo.crm.tools.LocalUtilis.language("message.insertMember", clientLocale); //��ȷ��Ҫ¼���Ŷӳ�Ա��
			out.println("<script language=\"javascript\">if(confirm(\""+message+"��\")){window.dialogArguments.newlist_flag = 1;window.dialogArguments.team_id="+_team_id+"}</script>");
			bSuccess = true;
		}else{
            message = enfo.crm.tools.LocalUtilis.language("message.noReturnValue", clientLocale); //��ȷ��Ҫ¼���Ŷӳ�Ա��
			out.println("<script language=\"javascript\">alert(\""+message+"\")</script>");
			bSuccess = false;
		}
	}catch(Exception e){       
        message = enfo.crm.tools.LocalUtilis.language("message.teamIDAlreadyExists", clientLocale); //���Ŷӱ���Ѵ��ڣ�������������
		out.println("<script language=\"javascript\">alert(\""+message+"��\")</script>");
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
		window.returnValue = true;
		window.close();
	<%}%>

	/*��֤����*/
	function validateForm(form){		
		return sl_check_update();	
	}

	/*����*/
	function SaveAction(){
		if(!sl_checkNum(document.getElementsByName("team_no")[0], "<%=LocalUtilis.language("class.teamID",clientLocale)%> ", 7, 1)) return false;//�Ŷӱ��
		
		if(!sl_check(document.getElementsByName("team_name")[0], "<%=LocalUtilis.language("class.teamName",clientLocale)%> ", 128, 1)) return false;// �Ŷ�����
		
		if(!sl_checkDate(document.theform.create_date_picker,"<%=LocalUtilis.language("class.createDate",clientLocale)%> ")){//��������
			return false;
		}else{
			syncDatePicker(document.theform.create_date_picker,document.theform.create_date);			
		}
		if(document.getElementsByName("leader")[0].getAttribute("value") == ""){
            //��ѡ������
			alert("<%=LocalUtilis.language("message.selectLeader",clientLocale)%> ��"); return false;
		}
		if(document.getElementsByName("parent_id")[0].getAttribute("value") == ""){
            //��ѡ������
			alert("��ѡ���Ŷ� ��"); return false;
		}
		if(document.theform.mark_flag.checked)
			document.theform.mark_flag.value = 1;
		else
			document.theform.mark_flag.value = 2;

        //��ע
		if(!sl_check(document.getElementsByName("description")[0], "<%=LocalUtilis.language("class.description",clientLocale)%> ", 512, 0)) return false;
		
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
<form name="theform" method="post" action="<%=request.getContextPath()%>/affair/base/team_new.jsp" onsubmit="javascript:return validateForm(this);">
<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="center">
	<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">
		<tr>
			<td style="width:120px;" align="right">*<%=LocalUtilis.language("class.teamID",clientLocale)%>:</td><!-- �Ŷӱ�� -->
			<td ><input type="text" style="width: 110px;" name="team_no"></td>
			<td style="width:90px;" align="right">*<%=LocalUtilis.language("class.teamName",clientLocale)%>:</td><!-- �Ŷ����� -->
			<td ><input type="text" style="width: 120px;" name="team_name"></td>
		</tr>	
		<tr>
			<td  align="right">*<%=LocalUtilis.language("class.leaderName",clientLocale)%>:</td><!-- ���������� -->
			<td>
				<select name="leader" style="width: 110px;">
					<%=Argument.getManager_Value(null)%>
				</select>
			</td>
			
			<td align="right" style="width:90px;">*<%=LocalUtilis.language("class.createDate",clientLocale)%>:</td><!-- �������� -->
			<td >
				<input type="text" name="create_date_picker" class=selecttext style="width: 99px;" <%if(create_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"<%}else{%> value="<%=Format.formatDateLine(create_date)%>"<%}%>>
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.create_date_picker,theform.create_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="create_date" id="create_date" />			
			</td>

		</tr>
		<tr>
			<td align="right">�Ƿ�ΪӪ���Ŷ�:</td>
			<td >
				<input type="checkbox" name="mark_flag" value="2" class="flatcheckbox">
			</td>
			<td align="right" >*���Ŷ�����:</td>
			<td><select name="parent_id" style="width: 110px;">
					<%=Argument.getTeam1_Value(null)%>
				</select></td>
		</tr>	
		<tr>
			<td align="right"><%=LocalUtilis.language("class.description",clientLocale)%>:</td><!-- ��ע -->
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