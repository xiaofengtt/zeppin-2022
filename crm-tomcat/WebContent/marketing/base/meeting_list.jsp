<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//ȡ��ҳ���ѯ����
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
String q_meeting_type = Utility.trimNull(request.getParameter("q_meeting_type"));
String q_meeting_theme = Utility.trimNull(request.getParameter("q_meeting_theme"));
String q_meeting_address = Utility.trimNull(request.getParameter("q_meeting_address"));
Integer q_start_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_start_date")),new Integer(0));
Integer q_end_date = Utility.parseInt(Utility.trimNull(request.getParameter("q_end_date")),new Integer(0));

//url����
String sUrl = "meeting_list.jsp?&sPage="+sPage;
String tempUrl = "";
tempUrl = tempUrl+"&q_meeting_type="+q_meeting_type;
tempUrl = tempUrl+"&q_meeting_theme="+q_meeting_theme;
tempUrl = tempUrl+"&q_meeting_address="+q_meeting_address;
tempUrl = tempUrl+"&q_start_date="+q_start_date;
tempUrl = tempUrl+"&q_end_date="+q_end_date;
sUrl = sUrl + tempUrl;

//ҳ���ø�������
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

//��ö���
MeetingLocal meetingLocal = EJBFactory.getMeeting();
MeetingVO vo = new MeetingVO();

//��ý����
vo.setMeeting_type(q_meeting_type);
vo.setMeeting_theme(q_meeting_theme);
vo.setMeeting_address(q_meeting_address);
vo.setQ_end_date(q_start_date);
vo.setQ_end_date(q_end_date);
vo.setInput_man(input_operatorCode);

IPageList pageList = meetingLocal.pageList_query(vo,totalColumn,t_sPage,t_sPagesize);

//��ҳ��������
int iCount = 0;
int iCurrent = 0;
List list = pageList.getRsList();
Map map = null;
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.meetingList",clientLocale)%> </title>
<!--�������-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/calendar.js" LANGUAGE="javascript" ></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>

<script language="javascript">
	/*��������*/	
	window.onload = function(){
		initQueryCondition();
	}
	
	/*��������*/
	function AppendAction(){		
		var url = "meeting_add.jsp";		
		window.location.href = url;	
		//if(showModalDialog(url,'', 'dialogWidth:580px;dialogHeight:350px;status:0;help:0')){
			//sl_update_ok();
		//	location.reload();
	//	}		
	}
	
	/*�޸ķ���*/
	function ModiAction(serial_no,isAttachmentExist){
		var url = "meeting_edit.jsp?serial_no="+serial_no+"&&f="+isAttachmentExist;		
		window.location.href = url;	
		/*
		if(showModalDialog(url,'', 'dialogWidth:580px;dialogHeight:350px;status:0;help:0')){
			sl_update_ok();
			location.reload();
		}*/
	}
	
	/*ɾ������*/
	function DelAction(){
		if(checkedCount(document.getElementsByName("check_serial_no")) == 0){
			sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ��");//��ѡ��Ҫɾ���ļ�¼
			return false;
		}
		 
		if(sl_check_remove()){			
			var url = "meeting_del.jsp";
			document.getElementsByName("theform")[0].setAttribute("action",url);
			document.getElementsByName("theform")[0].submit();	
			return true;		
		}
		
		return false;			
	}
	
	function refreshPage(){
		disableAllBtn(true);
		var _pagesize = document.getElementsByName("pagesize")[0];
		window.location = 'meeting_list.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value")+'<%=tempUrl%>';
	}
	
	function validate(){	
		if(document.theform.q_start_date_picker.value!=""&&(!sl_checkDate(document.theform.q_start_date_picker,"<%=LocalUtilis.language("class.startDate",clientLocale)%> "))){//��ʼ����			
			return false;
		}
		else{
			syncDatePicker(document.theform.q_start_date_picker,document.theform.q_start_date);
		}
		
		if(document.theform.q_end_date_picker.value!=""&&(!sl_checkDate(document.theform.q_end_date_picker,"<%=LocalUtilis.language("class.endDate",clientLocale)%> "))){//��������
			return false;
		}
		else{
			syncDatePicker(document.theform.q_end_date_picker,document.theform.q_end_date);
		}

		return true;	
	}
	
	/*��ѯ����*/
	function QueryAction(){		
		if(validate()){
			var _pagesize = document.getElementsByName("pagesize")[0];
			//url ���
			var url = 'meeting_list.jsp?page=<%=sPage%>&pagesize=' + _pagesize[_pagesize.selectedIndex].getAttribute("value");
			var url = url + "&q_meeting_type=" + document.getElementById("q_meeting_type").getAttribute("value");
			var url = url +"&q_meeting_theme="+document.getElementById("q_meeting_theme").getAttribute("value");
			var url = url +"&q_meeting_address="+document.getElementById("q_meeting_address").getAttribute("value");
			var url = url +"&q_start_date="+document.getElementById("q_start_date").getAttribute("value");
			var url = url +"&q_end_date="+ document.getElementById("q_end_date").getAttribute("value");		
			disableAllBtn(true);
			window.location = url;
		}
	}
	
		
/*�鿴��ϸ*/
function setiteminfor(serial_no){
	var v_tr =  "activeTr"+serial_no;
	var v_table = "activeTablePro"+serial_no;
	var v_flag = "activeFlag_display"+serial_no;
	var v_div = "activeDiv"+serial_no;
	var v_image = "activeImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}

/*�����ʼ�*/
function sendMail(content,attendManCode,title,attend_man){
	var url = "<%=request.getContextPath()%>/marketing/mail/sendmail.jsp?content="+content+"&attendManCode="+attendManCode+"&title="+title+"&to_name="+attend_man+"&checkFlag=yes";
	window.location.href = url;
}
/*�鿴��������*/
function DownloadAttachment(serial_no,table_id){
	var url = "<%=request.getContextPath()%>/tool/download.jsp?serial_no="+serial_no+"&&table_id="+table_id;		
	window.location.href = url;	
}
	
</script>
</head>

<body class="body body-nox">
<form id="theform" name="theform" method="post">

<div id="queryCondition" class="qcMain" style="display:none;width:545px;height:160px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ��</td><!--��ѯ����-->
   			<td align="right">
   				<button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<!-- Ҫ����Ĳ�ѯ���� -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.meetingType",clientLocale)%> : </td><!--�������-->
			<td  align="left">
				<select name="q_meeting_type" id="q_meeting_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">
					<%=Argument.getDictParamOptions(3013,"")%>	
				</select>
			</td>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.meetingTheme",clientLocale)%> : </td><!--��������-->
			<td  align="left">
				<input type="text" id="q_meeting_theme" name="q_meeting_theme" value="<%= q_meeting_theme%>" style="width:120px"/>
			</td>			
		</tr>
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.startDate",clientLocale)%> : </td><!--��ʼ����-->
			<td  align="left">
				<input type="text" name="q_start_date_picker" style="width:120px" class=selecttext 
				<%if(q_start_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%> value="<%=Format.formatDateLine(q_start_date)%>"<%}%> >	&nbsp;&nbsp;
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_start_date_picker,theform.q_start_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_start_date" id="q_start_date" value=""/>	
			</td>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.endDate",clientLocale)%> : </td><!--��������-->
			<td  align="left">
				<input type="text" style="width:120px" name="q_end_date_picker" class=selecttext 
				<%if(q_end_date==null){%> value="<%=Format.formatDateLine(Utility.getCurrentDate())%>"
				<%}else{%>value="<%=Format.formatDateLine(q_end_date)%>"<%}%> >	&nbsp;&nbsp;
				<input TYPE="button" value="��" class=selectbtn onclick="javascript:CalendarWebControl.show(theform.q_end_date_picker,theform.q_end_date_picker.value,this);" tabindex="13">
				<input TYPE="hidden" name="q_end_date" id="q_end_date" value=""/>	
			</td>
		</tr>
		<tr>
			<td  align="right" style="width: 90px;"><%=LocalUtilis.language("class.meetingAddress",clientLocale)%> : </td><!--����ص�-->
			<td  align="left" colspan="3">
				<input type="text" id="q_meeting_address" name="q_meeting_address" value="<%= q_meeting_address%>" style="width:120px"/>
			</td>		
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"   class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;<!--ȷ��-->	 				
			</td>
		</tr>			
	</table>
</div>

<div>
	<div align="left" class="page-title ">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>
	<div align="right" class="btn-wrapper">
		<button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> <!--��ѯ-->(<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--��ѯ-->
		<button type="button"   class="xpbutton3" accessKey=n id="btnAppend" name="btnAppend" title="<%=LocalUtilis.language("message.append",clientLocale)%> " onclick="javascript:AppendAction();"><%=LocalUtilis.language("message.append",clientLocale)%> <!--����-->(<u>N</u>)</button>
		&nbsp;&nbsp;&nbsp;<!--����-->
		<button type="button"   class="xpbutton3" accessKey=d id="btnDel" name="btnDel" title="<%=LocalUtilis.language("message.delete",clientLocale)%> " onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> <!--ɾ��-->(<u>D</u>)</button>
		<!--ɾ��-->
	</div>
	<br>
</div>

<div  align="center">
	<TABLE id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
		<tr class="trh">
			<td width="15%" align="center">
			 	<input type="checkbox" name="btnCheckbox" class="selectAllBox" 
				 onclick="javascript:selectAllBox(document.theform.check_serial_no,this);">
				 <%=LocalUtilis.language("class.meetingTheme",clientLocale)%> 
			</td><!--��������-->
			<td width="10%" align="center"><%=LocalUtilis.language("class.meetingType",clientLocale)%> </td><!--�������-->
			<td width="15%" align="center"><%=LocalUtilis.language("class.attendMan",clientLocale)%> </td><!--������ʿ-->			
			<td width="*" align="center"><%=LocalUtilis.language("class.meetingAddress",clientLocale)%> </td><!--����ص�-->
			<td width="10%" align="center"><%=LocalUtilis.language("class.startTime",clientLocale)%> </td><!--��ʼʱ��-->	
			<td width="10%" align="center"><%=LocalUtilis.language("class.endTime",clientLocale)%> </td><!--����ʱ��-->
			<td width="8%" align="center"><%=LocalUtilis.language("class.details",clientLocale)%> </td><!--��ϸ-->					
			<td width="7%" align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
			<td width="7%" align="center"><%=LocalUtilis.language("class.mail",clientLocale)%> </td><!--�ʼ�-->
		</tr>	
<%
//�����ֶ�
Iterator iterator = list.iterator();

Integer serial_no;
String meeting_type_name;
String attend_man;
String attend_man_code;
String meeting_theme;
Integer meeting_date;
String meeting_address;	
String  start_date;
String  end_date;	
String remark;
//����������ر���
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
String origin_name="";
String save_name="";
boolean isAttachmentExist=false;

while(iterator.hasNext()){
		iCount++;
		map = (Map)iterator.next();
		
		serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
		meeting_type_name = Utility.trimNull(map.get("MEETING_TYPE_NAME"));
		attend_man = Utility.trimNull(map.get("ATTEND_MAN"));
		meeting_theme = Utility.trimNull(map.get("MEETING_THEME"));
		meeting_date = Utility.parseInt(Utility.trimNull(map.get("MEETING_DATE")),new Integer(0));
		meeting_address = Utility.trimNull(map.get("MEETING_ADDRESS"));
		start_date =Utility.trimNull(map.get("START_DATE"));
		end_date = Utility.trimNull(map.get("END_DATE"));
		remark = Utility.trimNull(map.get("REMARK"));
	    attend_man_code = Utility.trimNull(map.get("ATTEND_MAN_CODE"));
		//��ø�����Ϣ
		attachmentVO.setDf_serial_no(serial_no);
		attachmentVO.setDf_talbe_id(new Integer(1));//������1���������2
		attachmentVO.setInput_man(input_operatorCode);
		List attachmentList=null;
		Map attachmentMap=null;
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
		if(start_date.length()>0){
			start_date = start_date.substring(0,16);
		}	
		if(end_date.length()>0){
			end_date = end_date.substring(0,16);
		}
%>		
		<tr class="tr<%=iCount%2%>">
             <td align="center">      
             	<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="10%">
								<input type="checkbox" name="check_serial_no" value="<%= serial_no%>" class="flatcheckbox">   
						</td>
						<td width="90%" align="left">&nbsp;&nbsp;<%= meeting_theme%>   </td>
					</tr>
				</table>       	
             </td>
             <td align="center"><%= meeting_type_name%></td>
             <td align="left"><input type="text" style="width:100%" class="ednone" value="<%=attend_man%>" readonly></td>             
             <td align="left">&nbsp;&nbsp;<%= meeting_address%></td>   
              <td align="center"><%= start_date%></td>
             <td align="center"><%= end_date%></td> 
             <td align="center">
             	<button type="button"  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=serial_no%>);">
         			<IMG id="activeImage<%=serial_no%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
	         	</button>
	         	<input type="hidden" id="activeFlag_display<%=serial_no%>" name="activeFlag_display<%=serial_no%>" value="0">             
             </td>
             <td align="center">              	
              	<a href="javascript:ModiAction(<%=serial_no%>,<%=isAttachmentExist%>)">
               		<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif"  width="16" height="16">
               	</a>
             </td>
             <td align="center">              	
              	<a href="javascript:sendMail('<%=remark%>','<%= attend_man_code%>','<%= meeting_theme%>','<%= attend_man%>');">
               		<img border="0" src="<%=request.getContextPath()%>/images/email_close.gif"  width="16" height="16">
               	</a>
             </td>
		</tr> 
		
		<tr id="activeTr<%=serial_no%>" style="display: none">
		<td align="center" bgcolor="#FFFFFF" colspan="9" >
			<div id="activeDiv<%=serial_no%>" style="overflow-y:auto;" align="center">
				<table id="activeTablePro<%=serial_no%>" border="0" width="100%" bgcolor="#FFFFFF" cellspacing="1">
					<tr style="background:F7F7F7;">
						<td  width="15%">&nbsp;&nbsp;<%=LocalUtilis.language("class.theformRemark",clientLocale)%> ��</td><!--�����Ҫ-->
						<td  width="*"><%= remark%></td>
					</tr>
					<tr style="background:F7F7F7;">
						<td  width="15%">&nbsp;&nbsp;<%=LocalUtilis.language("class.meetingAttachment",clientLocale)%></td><!--���鸽��-->
						<%if(isAttachmentExist){%>
							<td  width="*"><a href="javascript:DownloadAttachment(<%=serial_no%>,1)"><%= origin_name%></a></td>
					    <%}else{%>
					    	<td  width="*"><%=origin_name%></td>
					    <%}%>
					</tr>
				</table>
			</div>
		</td>
	</tr>  
<%
}
for(int i=0;i<(t_sPagesize-iCount);i++){
%>  
       <tr class="tr<%=i%2%>">
         <td align="center">&nbsp;</td>
         <td align="center">&nbsp;</td>
         <td align="center">&nbsp;</td>         
         <td align="center">&nbsp;</td> 
         <td align="center">&nbsp;</td> 
         <td align="center">&nbsp;</td> 
          <td align="center">&nbsp;</td> 
         <td align="center">&nbsp;</td> 
         <td align="center">&nbsp;</td> 
       </tr>           
<%}%> 
		<tr class="trbottom">
			<td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
			<!-- <td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>			
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td>
			<td align="center" class="tdh"></td> -->
		</tr>
	</TABLE>
</div>
<br>
<div class="page-link">
	<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>

<% 
	meetingLocal.remove();
	attachmentLocal.remove();
%>
</form>
</body>

</html>
