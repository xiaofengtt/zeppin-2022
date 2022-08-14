<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.marketing.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.DocumentFileToCrmDB"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//������������
boolean bSuccess = false;
//�����¼���� hesl 2011.4.20
Integer serial_no =  new Integer(0);
//��ö���
MeetingLocal meetingLocal = EJBFactory.getMeeting();
MeetingVO vo = new MeetingVO();

//�����ֶ�
/**
String meeting_type = Utility.trimNull(request.getParameter("meeting_type"));
String attend_man = Utility.trimNull(request.getParameter("attend_man"));	
String attend_man_code=  Utility.trimNull(request.getParameter("attend_man_code"));	
String start_date= Utility.trimNull(request.getParameter("start_date"));
String end_date= Utility.trimNull(request.getParameter("end_date"));
String meeting_theme= Utility.trimNull(request.getParameter("meeting_theme"));	
String meeting_address= Utility.trimNull(request.getParameter("meeting_address"));	
String remark = Utility.trimNull(request.getParameter("remark"));
*/
/*********��Ϊ������һ�������ϴ�������ԭ��request����ȡֵ�ĸ�Ϊ file*******************/
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){	
	file = new DocumentFileToCrmDB(pageContext);
    file.parseRequest();
	String meeting_type = Utility.trimNull(file.getParameter("meeting_type"));
	String attend_man = Utility.trimNull(file.getParameter("attend_man"));	
	String attend_man_code=  Utility.trimNull(file.getParameter("attend_man_code"));	
	String start_date= Utility.trimNull(file.getParameter("start_date"));
	String end_date= Utility.trimNull(file.getParameter("end_date"));
	String meeting_theme= Utility.trimNull(file.getParameter("meeting_theme"));	
	String meeting_address= Utility.trimNull(file.getParameter("meeting_address"));	
	String remark = Utility.trimNull(file.getParameter("remark"));
	//vo.setMeeting_date(meeting_date);
	vo.setMeeting_type(meeting_type);
	vo.setStart_date(start_date);
	vo.setEnd_date(end_date);
	vo.setAttend_man(attend_man);
	vo.setAttend_man_code(attend_man_code);
	vo.setMeeting_theme(meeting_theme);
	vo.setMeeting_address(meeting_address);
	vo.setRemark(remark);
	vo.setInput_man(input_operatorCode);
	serial_no=meetingLocal.append(vo);
	file.uploadAttchment(new Integer(1),"TMEETINGS",serial_no,"",input_operatorCode);
	bSuccess = true;
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=11" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.meetingAdd",clientLocale)%> </title>
<!--��������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/newDate.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.6.2.min.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script language=javascript>

	/*��֤����*/
	function validateForm(form){		
		if(!sl_check(document.theform.meeting_theme, "<%=LocalUtilis.language("class.meetingTheme",clientLocale)%> ",200,1)){return false;}//��������
		if(!sl_check(document.getElementById('start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//��ʼʱ��
		if(!sl_check(document.getElementById('end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//����ʱ��
		if(document.getElementById('end_date').value<document.getElementById('start_date').value){
			sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//�������ڲ��ܱȿ�ʼ������
			return false;
		}	
		if(!sl_checkChoice(document.getElementById('meeting_type'),"<%=LocalUtilis.language("class.meetingType",clientLocale)%> ")){return false;}//�������
		if(!sl_check(document.theform.meeting_address, "<%=LocalUtilis.language("class.meetingAddress",clientLocale)%> ",50,1)){return false;}//����ص�	
		if(!sl_check(document.theform.attend_man, "<%=LocalUtilis.language("class.attendMan",clientLocale)%> ",200,1)){return false;}	//������ʿ
		if(!sl_check(document.theform.remark, "<%=LocalUtilis.language("class.theformRemark",clientLocale)%> ",200,0)){return false;}//�����Ҫ	
		
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
		location.href = "meeting_list.jsp";
	}
	
	function choose_operator(){
		var url = "<%=request.getContextPath()%>/system/basedata/operator_choose.jsp";
		var ret = showModalDialog(url,'', 'dialogWidth:550px;dialogHeight:450px;status:0;help:0');
		var retStr =ret.split("&");
		
		document.getElementsByName("attend_man")[0].value=retStr[1];
		document.getElementsByName("attend_man_code")[0].value=retStr[0];
	}

</script>
</HEAD>
<BODY class="BODY body-nox" id="body">
<form name="theform" method="post" action="meeting_add.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<!--�洢�����¼���� hesl 2011.4.20-->
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>
<!--�����ɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>

<div align="left" style="width=100%">
	<table border="0"  cellspacing="3" cellpadding="3" style="width: 100%" class="product-list">
		<tr>
			<td align="right" ><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.meetingTheme",clientLocale)%> ��</font></td><!--��������-->
			<td  align="left">
				<input type="text" name="meeting_theme" size="30" value="" onkeydown="javascript:nextKeyPress(this)"/>				
			</td>
		</tr>
		<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/laydate-v1.1/laydate/laydate.js"></SCRIPT>
		<tr>
				<td align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.startTime",clientLocale)%> ��</font></td><!--��ʼʱ��-->
				<td  align="left">
					<input type="text" name="start_date" id="start_date" onclick="laydate({elem:'#start_date',format:'YYYY-MM-DD hh:mm:ss',istime:true});$('#laydate_box').css({'left':'455px','top':'100px'});" size="30" readOnly/> 				
				</td>	
		</tr>
		<tr>
			<td align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.endTime",clientLocale)%> ��</font></td><!--����ʱ��-->
			<td  align="left">
				<input type="text" name="end_date" id="end_date" size="30"  onclick="laydate({elem:'#end_date',format:'YYYY-MM-DD hh:mm:ss',istime:true});$('#laydate_box').css({'left':'455px','top':'100px'});" readOnly/> 		
			</td>
		</tr>	
		<script language=javascript>
			$("#body").addClass("laydate_body");
		</script>
		<tr>
			<td align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.meetingType",clientLocale)%> ��</font></td><!--�������-->
			<td  align="left">
				<select name="meeting_type"  id="meeting_type" onkeydown="javascript:nextKeyPress(this)" style="width:175px">	
					<%=Argument.getDictParamOptions(3013,"")%>
				</select>				
			</td>
		</tr>	
		
		<tr>
			<td align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.meetingAddress",clientLocale)%> ��</font></td><!--����ص�-->
			<td  align="left">
				<input type="text" name="meeting_address" size="79" value="" onkeydown="javascript:nextKeyPress(this)"/>				
			</td>
		</tr>
		
		<tr>
			<td align="right" valign="top"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.attendMan2",clientLocale)%> ��</font></td><!--���������-->
			<td  align="left">				
				<textarea rows="3" name="attend_man" onkeydown="javascript:nextKeyPress(this)" cols="80" onclick="javascript:choose_operator();" readOnly></textarea>			
				<input type="hidden" name="attend_man_code" value="" />
			</td>			
		</tr>		
				
		<tr>
			<td align="right" valign="top"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.theformRemark",clientLocale)%> ��</font></td><!--�����Ҫ-->
			<td  align="left">
				<textarea rows="5" name="remark" onkeydown="javascript:nextKeyPress(this)" cols="80"></textarea>			
			</td>
		</tr>
		<tr id="reader2" style="display:">
          	        <td align="right"><font size="2" face="΢���ź�"><%=LocalUtilis.language("class.appendAttachment",clientLocale)%></font></td><!-- ��Ӹ���: --> 
                    <td colspan="3">          	
            	    <input type="file" name="file_info" size="60">&nbsp;<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%>" src="<%=request.getContextPath()%>/images/minihelp.gif"><!-- ѡ�񸽼��ļ� -->
                </td>
        </tr>	
		
	</table>
	<br>
	<div align="right" >	
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;
		<!--����-->
		<button type="button"   class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
		<!--����-->
	</div>
</div>

</form>
<% meetingLocal.remove(); %>

<script language=javascript>
	window.onload = function(){
		var v_bSuccess = document.getElementById("bSuccess").value;
		
		if(v_bSuccess=="true"){		
			sl_alert("<%=LocalUtilis.language("message.saveOk",clientLocale)%> ��");//����ɹ�
			window.location.href="meeting_list.jsp"
		}
	}
	
	
</script>
</BODY>
</HTML>