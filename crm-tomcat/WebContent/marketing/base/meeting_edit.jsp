<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ page import="enfo.crm.web.DocumentFileToCrmDB"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//���ҳ�洫�ݱ���
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
//������������
boolean bSuccess = false;

//��ö���
MeetingLocal meetingLocal = EJBFactory.getMeeting();
MeetingVO meetingVO = new MeetingVO();
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(serial_no);
attachmentVO.setDf_talbe_id(new Integer(1));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
boolean isAttachmentExist=false;

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
String meeting_type_name ="";
*/
String meeting_type ="";
String attend_man = "";	
String attend_man_code=  "";	
String start_date= "";
Integer meeting_date = 0;
String end_date= "";
String meeting_theme= "";	
String meeting_address= "";	
String remark = "";
String meeting_type_name ="";
String origin_name="";
String save_name="";
//������Ϣ
/*********��Ϊ������һ�������ϴ�������ԭ��request����ȡֵ�ĸ�Ϊ file*******************/
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){	
    file = new DocumentFileToCrmDB(pageContext);
    file.parseRequest();
	
	meeting_type = Utility.trimNull(file.getParameter("meeting_type"));
	attend_man = Utility.trimNull(file.getParameter("attend_man"));	
	attend_man_code=  Utility.trimNull(file.getParameter("attend_man_code"));	
	start_date= Utility.trimNull(file.getParameter("start_date"));
	end_date= Utility.trimNull(file.getParameter("end_date"));
	meeting_theme= Utility.trimNull(file.getParameter("meeting_theme"));	
	meeting_address= Utility.trimNull(file.getParameter("meeting_address"));	
	remark = Utility.trimNull(file.getParameter("remark"));
	meetingVO.setSerial_no(serial_no);
	meetingVO.setMeeting_type(meeting_type);
	meetingVO.setMeeting_date(meeting_date);
	meetingVO.setStart_date(start_date);
	meetingVO.setEnd_date(end_date);
	meetingVO.setAttend_man(attend_man);
	meetingVO.setAttend_man_code(attend_man_code);
	meetingVO.setMeeting_theme(meeting_theme);
	meetingVO.setMeeting_address(meeting_address);
	meetingVO.setRemark(remark);
	meetingVO.setInput_man(input_operatorCode);
	
	meetingLocal.modi(meetingVO);
	int attachment_modify_flag_value=Integer.parseInt(file.getParameter("attachment_modify_flag"));
	save_name=Utility.trimNull(file.getParameter("attachment_save_name"));
	if(attachment_modify_flag_value!=0){//0:�����ޱ仯��1��ԭ���и����������޸ģ���ɾ��ԭ�и�����2��ԭ���޸����������ϴ�������ɾ������
		if(attachment_modify_flag_value==1){
			attachmentVO.setSave_name(save_name); 
			attachmentVO.setInput_man(input_operatorCode);
			attachmentLocal.delete(attachmentVO);
			attachmentLocal.deleteFile(attachmentVO);
		}
		file.uploadAttchment(new Integer(1),"TMEETINGS",serial_no,"",input_operatorCode);
	}
	bSuccess = true;
}
else{
//��������
	List meetingList = null;
	Map meetingMap = null;	
	//����serial_noȡ��meeting�������Ϣ
	if(serial_no.intValue()!= 0){
		meetingVO.setSerial_no(serial_no);
		meetingVO.setInput_man(input_operatorCode);
		
		meetingList = meetingLocal.query(meetingVO);
		meetingMap = (Map)meetingList.get(0);
	}
	
	if(meetingMap!=null){
		meeting_type_name = Utility.trimNull(meetingMap.get("MEETING_TYPE_NAME"));
		meeting_type = Utility.trimNull(meetingMap.get("MEETING_TYPE"));
		attend_man = Utility.trimNull(meetingMap.get("ATTEND_MAN"));
		attend_man_code = Utility.trimNull(meetingMap.get("ATTEND_MAN_CODE"));
		meeting_theme = Utility.trimNull(meetingMap.get("MEETING_THEME"));
		meeting_date = Utility.parseInt(Utility.trimNull(meetingMap.get("MEETING_DATE")),new Integer(0));
		meeting_address = Utility.trimNull(meetingMap.get("MEETING_ADDRESS"));		
		remark = Utility.trimNull(meetingMap.get("REMARK"));
		start_date = Utility.trimNull(meetingMap.get("START_DATE"));
		end_date = Utility.trimNull(meetingMap.get("END_DATE"));
		
		attachmentList=attachmentLocal.load(attachmentVO);
		if(!attachmentList.isEmpty()){
		    isAttachmentExist=true;
		    //attachmentMap=(Map)attachmentList.get(0);
			attachmentMap=(Map)attachmentList.get(attachmentList.size()-1);
			origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
	    	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
		}else{
		    isAttachmentExist=false;
			origin_name=LocalUtilis.language("class.noAttachmentExisted",clientLocale);//�˻����¼û���ϴ�����
		}
	}
}

if(start_date.length()>0){
	start_date = start_date.substring(0,16);
}
if(end_date.length()>0){
	end_date = end_date.substring(0,16);
}
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title><%=LocalUtilis.language("menu.meetingEdit",clientLocale)%> </title>
<!--�޸Ļ���-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar_time.js"></SCRIPT>
<script language="javascript">

	/*��֤����*/
	function validateForm(form){
		if(!sl_check(document.theform.meeting_theme, "<%=LocalUtilis.language("class.meetingTheme",clientLocale)%> ",200,1)){return false;}//��������
		if(!sl_check(document.getElementById('start_date'), "<%=LocalUtilis.language("class.startTime",clientLocale)%> ", 20, 1))return false;//��ʼʱ��
		if(!sl_check(document.getElementById('end_date'), "<%=LocalUtilis.language("class.endTime",clientLocale)%> ", 20, 1))return false;//����ʱ��
		if(document.getElementById('end_date').value<document.getElementById('start_date').value){
			sl_alert("<%=LocalUtilis.language("message.DateError",clientLocale)%> ");//�������ڲ��ܱȿ�ʼ������
			return false;
		}	
		if(!sl_checkChoice(document.theform.meeting_type,"<%=LocalUtilis.language("class.meetingType",clientLocale)%> ")){return false;}//�������
		if(!sl_check(document.theform.meeting_address, "<%=LocalUtilis.language("class.meetingAddress",clientLocale)%> ",50,1)){return false;}//����ص�
		if(!sl_check(document.theform.attend_man, "<%=LocalUtilis.language("class.attendMan",clientLocale)%> ",200,1)){return false;}//������ʿ				
		if(!sl_check(document.theform.remark, "<%=LocalUtilis.language("class.theformRemark",clientLocale)%> ",200,0)){return false;}//�����Ҫ	
		
		return sl_check_update();		
	}

	/*����*/
	function SaveAction(){
		if(document.getElementsByName("theform")[0].onsubmit()){
			document.getElementsByName("theform")[0].submit();
		}	
	}
	
	/*ȡ��*/
	function CancelAction(){
		//window.returnValue=null;
		//window.close();
		location.href = "meeting_list.jsp";
	}
	
	function choose_operator(operatorCodes){
		var url = "<%=request.getContextPath()%>/system/basedata/operator_choose.jsp?operatorCodes="+operatorCodes;
		var ret = showModalDialog(url,'', 'dialogWidth:550px;dialogHeight:450px;status:0;help:0');
		var retStr =ret.split("&");

		document.getElementsByName("attend_man")[0].value=retStr[1];
		document.getElementsByName("attend_man_code")[0].value=retStr[0];
	}
	function ModifyAttachment(){
	    if(window.confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")){ //ȷ��ɾ��������
			document.theform.attachment_modify_flag.value = "1";;
			document.getElementById("file_info").removeAttribute("disabled");  
			document.getElementById("oldAttachment").value="<%=LocalUtilis.language("message.deleteMeetingAttachment",clientLocale)%>";//�������󣬴˻����¼ԭ�и�������ɾ��
			
		}
	}
	/*�鿴��������*/
	function DownloadAttachment(serial_no,table_id){
		var url = "<%=request.getContextPath()%>/tool/download.jsp?serial_no="+serial_no+"&&table_id="+table_id;		
		window.location.href = url;	
	}
</script>
</HEAD>
<BODY class="BODY body-nox">
<form name="theform" method="post" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<!--�޸ĳɹ���־-->
<input type="hidden" id="bSuccess" value="<%=bSuccess%>"/>
<input type="hidden" id="serial_no" name="serial_no" value="<%=serial_no%>"/>

<div align="left" class="page-title">
	<font color="#215dc6"><b><%=menu_info%></b></font>
</div>
<br/>
<div align="left" style="width=100%">
	<table border="0"  cellspacing="3" cellpadding="3" style="width: 100%" class="product-list">
		<tr>
			<td  align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.meetingTheme",clientLocale)%> ��</font></td><!--��������-->
			<td  align="left" colspan="3">
				<input type="text" name="meeting_theme" size="30" value="<%= meeting_theme%>" onkeydown="javascript:nextKeyPress(this)"/>				
			</td>
		</tr>
		<tr>
				<td align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.startTime",clientLocale)%> ��</font></td><!--��ʼʱ��-->
				<td  align="left">
					<input type="text" name="start_date" id="start_date" size="30" onclick="javascript:setDayHM(this);" value="<%= start_date%>"  readOnly/> 				
				</td>	
		</tr>
		<tr>
			<td align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.endTime",clientLocale)%> ��</font></td><!--����ʱ��-->
			<td  align="left">
				<input type="text" name="end_date" id="end_date" size="30" onclick="javascript:setDayHM(this);"  value="<%= end_date%>" readOnly/> 		
			</td>
		</tr>	
		
		<tr>
			<td  align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.meetingType",clientLocale)%> ��</font></td><!--�������-->
			<td  align="left" colspan="3">
				<select name="meeting_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
					<%=Argument.getDictParamOptions(3013,meeting_type)%>
				</select>				
			</td>
		</tr>	
		
		<tr>
			<td  align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.meetingAddress",clientLocale)%> ��</font></td><!--����ص�-->
			<td  align="left" colspan="3">
				<input type="text" name="meeting_address" size="60" value="<%= meeting_address%>" onkeydown="javascript:nextKeyPress(this)"/>				
			</td>
		</tr>
		
		<tr>
			<td  align="right" valign="top"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.attendMan2",clientLocale)%> ��</font></td><!--���������-->
			<td  align="left" colspan="3">
				<textarea rows="3" name="attend_man" onkeydown="javascript:nextKeyPress(this)" cols="80" onclick="javascript:choose_operator('<%=attend_man_code%>');" readOnly><%= attend_man%></textarea>			
				<input type="hidden" name="attend_man_code" value="<%=attend_man_code%>" />
			</td>
		</tr>		
				
		<tr>
			<td  align="right" valign="top"><font size="2" face="΢���ź�">&nbsp;&nbsp;*<%=LocalUtilis.language("class.theformRemark",clientLocale)%> ��</font></td><!--�����Ҫ-->
			<td  align="left" colspan="3">
				<textarea rows="5" name="remark" onkeydown="javascript:nextKeyPress(this)" cols="80"><%= remark%></textarea>
			</td>
		</tr>
		<tr>
			<td  align="right"><font size="2" face="΢���ź�">&nbsp;&nbsp;<%=LocalUtilis.language("class.existedAttachment",clientLocale)%></font></td> <!-- ���и��� �� -->
			<td  align="left" colspan="3">
				<%if(isAttachmentExist){%>
					<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly"/>
					<a href="javascript:DownloadAttachment(<%=serial_no%>,1)">
               			<font size="2" face="΢���ź�"><%=LocalUtilis.language("class.viewAttachments",clientLocale)%></font> <!-- �鿴���� �� -->
               		</a>
               		<input type="hidden" name="attachment_modify_flag" value=0>
               		<input type="hidden" name="attachment_save_name" value=<%=save_name%>>
               		<button type="button"  class="xpbutton3"  id="btnCancel" name="btnCancel" onclick="javascript:ModifyAttachment();"><%=LocalUtilis.language("class.deleteAttachment",clientLocale)%></button><!-- ɾ������ -->
			</td>
		</tr>
		<tr id="reader2" style="display:">
          	        <td align="right"><font size="2" face="΢���ź�"><%=LocalUtilis.language("class.appendAttachment",clientLocale)%></font></td><!-- ��Ӹ���: -->
                    <td colspan="3">       	
            	    <input type="file" name="file_info" size="60" disabled="disabled">&nbsp;<img title="ֻ��ɾ��ԭ�и������������¸���" src="<%=request.getContextPath()%>/images/minihelp.gif"><!-- ѡ�񸽼��ļ� -->
                </td>
        </tr>
				<%}else{%>
					<input id="oldAttachment" type="text" name="file_now" size="60" value="<%= origin_name%>" readonly="readonly">
					<input type="hidden" name="attachment_modify_flag" value=2> 
		 <tr id="reader2" style="display:">
          	        <td align="right"><font size="2" face="΢���ź�"><%=LocalUtilis.language("class.appendAttachment",clientLocale)%></font></td><!-- ��Ӹ���: -->
                    <td colspan="3">       	
            	    <input type="file" name="file_info" size="60">&nbsp;<img title="<%=LocalUtilis.language("message.tSelectAdditionalFiles",clientLocale)%>" src="<%=request.getContextPath()%>/images/minihelp.gif"><!-- ѡ�񸽼��ļ� -->
                </td>
        </tr> 
				<%}%>
	</table>
	
	<br>
	<div align="right" >	
		<button type="button"  class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;&nbsp;&nbsp;<!--����-->
		<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>C</u>)</button>
		<!--����-->
	</div>
</div>

</form>

<% 
   meetingLocal.remove(); 
   attachmentLocal.remove(); 
%>
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