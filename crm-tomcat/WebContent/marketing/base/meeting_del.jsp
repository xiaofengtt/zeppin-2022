<%@ page contentType="text/html; charset=GBK" import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>

<%
//获取主键ID 
String[] temp_checks = request.getParameterValues("check_serial_no");
Integer t_serial_no;

//获得对象
MeetingLocal meetingLocal = EJBFactory.getMeeting();
MeetingVO vo = new MeetingVO();
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
List attachmentList=null;
Map attachmentMap=null;
boolean isAttachmentExist=false;
String save_name="";
//逐个删除
if(temp_checks!=null){
	for(int i=0;i<temp_checks.length;i++){
		t_serial_no = Utility.parseInt(temp_checks[i], new Integer(0));
		
		if(t_serial_no.intValue()!=0){				
			vo.setSerial_no(t_serial_no);
			vo.setInput_man(input_operatorCode);
			//添加删除附件记录和文件的操作
			attachmentVO.setDf_serial_no(t_serial_no);
			attachmentVO.setDf_talbe_id(new Integer(1));
			attachmentVO.setInput_man(input_operatorCode);
			attachmentList=attachmentLocal.load(attachmentVO);
			if(!attachmentList.isEmpty()){
			    isAttachmentExist=true;
				attachmentMap=(Map)attachmentList.get(attachmentList.size()-1);
		    	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
		    	attachmentVO.setSave_name(save_name);
		    	attachmentVO.setInput_man(input_operatorCode);
		    	attachmentLocal.delete(attachmentVO);
		    	attachmentLocal.deleteFile(attachmentVO);
			}
			
			meetingLocal.delete(vo);			
		}
	}
}

meetingLocal.remove();
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	alert("<%=LocalUtilis.language("message.deleteOk",clientLocale)%> ！");//删除成功
	window.returnValue = 1;
	location =  "meeting_list.jsp";
</SCRIPT>
