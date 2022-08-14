<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc"%>
      <%
             int display_flag = Utility.parseInt(request.getParameter("display_flag"),0);
      
	              MessageLocal inform = EJBFactory.getMessage();
	 
	              //当前操作员发送的工作提示
	              MessageVO toOtherVo=new MessageVO();
	              toOtherVo.setBook_code(input_bookCode);
	              toOtherVo.setInput_man(input_operatorCode);
	              toOtherVo.setOp_code(input_operatorCode);   
	              List toOthersList=inform.listPrivateMessage(toOtherVo);
	              Map toOthersMap=null;
	              int toOthersListSize=toOthersList.size();
	              
	              //系统发生给当前操作员的工作提示    
	               MessageVO sysVo=new MessageVO();
	              sysVo.setBook_code(input_bookCode);	             
	              sysVo.setOp_code(input_operatorCode);         
	              List systemList=inform.listSystemMessage(sysVo);
	              Map systemMap=null;
	              int systemListSize=systemList.size();
	              
	              //别人发给给当前操作员的工作提示   ----我要处理事务   
	              MessageVO toMeVo=new MessageVO();
	              toMeVo.setBook_code(input_bookCode);
	              toMeVo.setInput_man(input_operatorCode); 
	                
	              List toMeList=inform.listPrivateMessage(toMeVo);
	              Map toMeMap=null;
	              int toMeListSize=toMeList.size();
	         %>
<html>
<head>
	<title>Enfo 金融财务软件系统</title>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
	<meta http-equiv="Cache-Control" content="no-store"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<link href="/includes/default.css" type=text/css rel=stylesheet>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-base.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/ext-all.js"></script>
	<script src="/includes/default.js"></script>
	<script language=javascript>

function showInfo(message_serial_no,send_derection)
{	
    
	if(showModalDialog('private_message_detail.jsp?message_serial_no='+message_serial_no+'&send_derection='+send_derection,'','scrollbars=yes;resizable=no;dialogLeft:10; dialogTop:10;dialogWidth:785px;dialogHeight:480px')){
	  location.reload();
	}
	
}

function showSysInfo(serial_no)
{	
	if(showModalDialog('system_message_detail.jsp?serial_no=' + serial_no,'','scrollbars=yes;resizable=no;dialogLeft:10; dialogTop:10;dialogWidth:785px;dialogHeight:480px')){
	  location.reload();
	}	
	//window.open('system_message_detail.jsp?serial_no=' + serial_no,'','scrollbars=yes,resizable=no, left=10, top=10,width715px,height=480px');
}

</script>
</head>

<body class="body">
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<%if(toMeListSize>0 && display_flag==1){%>
	    <TR>
			  <TD width="90%" height="30"align=center style="background-repeat: no-repeat;background-image: url(/images/mytranbyother.gif);">
			  
			  </td>  
			  <TD width="10%" >
			      
			  <TD>
         </TR>         
	         <% for(int i=0;i<toMeList.size() ;i++){
	              toMeMap=(Map)toMeList.get(i); 
	              Integer toMeMap_serial_no=Utility.parseInt(Utility.trimNull(toMeMap.get("SERIAL_NO")),new Integer(0));
	              Integer message_serial_no=Utility.parseInt(Utility.trimNull(toMeMap.get("SERIAL_NO_INFO")),new Integer(0));
	              String toMeMap_title=Utility.trimNull(toMeMap.get("TITLE"));
	              Integer toMeMap_menu_id=Utility.parseInt(Utility.trimNull(toMeMap.get("MENU_ID")),new Integer(0));
	              String  input_time=Utility.trimNull(toMeMap.get("INPUT_TIME"));   
                  Integer  input_man = Utility.parseInt(Utility.trimNull(toMeMap.get("INPUT_MAN")), new Integer(0)); 
	          %> 
	       <tr class="tr<%=(i % 2)%>">

		       <td COLSPAN=2 align="left" noWrap="true">			  		 
		        <a href="javascript:showInfo(<%=message_serial_no%>,1);"	title="<%=toMeMap_title%>"><u><font color="#000000" size="2"><%=Utility.intercepetCharLength(Utility.trimNull(toMeMap_title), 18)%></font></u></a>(<%=Argument.getOpName(input_man)%>于<%=input_time%>)
		        
		      </td>
	       </tr>
	        <%}
	        }
	        %>
	        
	     <%if(toOthersListSize>0 && display_flag==2){%>
	    <TR>
			  <TD width="90%" height="30"align=center style="background-repeat: no-repeat;background-image: url(/images/toothertran.gif);">
			  
			  </td>  
			  <TD width="10%" >
			     
			  <TD>
         </TR>         
	         <% for(int i=0;i<toOthersList.size() ;i++){
	              toOthersMap=(Map)toOthersList.get(i); 
	              Integer toOthersMap_serial_no=Utility.parseInt(Utility.trimNull(toOthersMap.get("SERIAL_NO")),new Integer(0));
	              Integer message_serial_no=Utility.parseInt(Utility.trimNull(toOthersMap.get("SERIAL_NO_INFO")),new Integer(0));
	              String toOthersMap_title=Utility.trimNull(toOthersMap.get("TITLE"));
	              Integer toOthersMap_menu_id=Utility.parseInt(Utility.trimNull(toOthersMap.get("MENU_ID")),new Integer(0));
	              String  input_time=Utility.trimNull(toOthersMap.get("INPUT_TIME"));   
                  Integer  input_man = Utility.parseInt(Utility.trimNull(toOthersMap.get("INPUT_MAN")), new Integer(0)); 
	          %> 
	       <tr class="tr<%=(i % 2)%>">

		       <td COLSPAN=2 align="left" noWrap="true">			  		 
		        <a href="javascript:showInfo(<%=message_serial_no%>,2);"	title="<%=toOthersMap_title%>"><u><font color="#000000" size="2"><%=Utility.intercepetCharLength(Utility.trimNull(toOthersMap_title), 18)%></font></u></a>(<%=Argument.getOpName(input_man)%>于<%=input_time%>)
		        
		      </td>
	       </tr>
	        <%}
	        }
	        %>
	        
	     <%if(systemListSize>0 && display_flag==3){%> 
	    <TR>
			  <TD width="90%" height="30"align=center style="background-repeat: no-repeat;background-image: url(/images/systemtran.gif);"> 
			  
			  </td>  
			  <TD width="10%" >
			      
			  <TD>
         </TR>         
	         <% for(int i=0;i<systemList.size() ;i++){
	              systemMap=(Map)systemList.get(i); 
	              Integer toOthersMap_serial_no=Utility.parseInt(Utility.trimNull(systemMap.get("SERIAL_NO")),new Integer(0));
	              String toOthersMap_title=Utility.trimNull(systemMap.get("TITLE"));
	              Integer toOthersMap_menu_id=Utility.parseInt(Utility.trimNull(systemMap.get("MENU_ID")),new Integer(0));
	              String  input_time=Utility.trimNull(systemMap.get("INPUT_TIME"));   
                  Integer  input_man = Utility.parseInt(Utility.trimNull(systemMap.get("INPUT_MAN")), new Integer(0)); 
	          %> 
	       <tr class="tr<%=(i % 2)%>">

		       <td COLSPAN=2 align="left" noWrap="true">			  		 
		        <a href="javascript:showSysInfo(<%=toOthersMap_serial_no%>);"	title="<%=toOthersMap_title%>"><u><font color="#000000" size="2"><%=Utility.intercepetCharLength(Utility.trimNull(toOthersMap_title), 18)%></font></u></a>(<%=Argument.getOpName(input_man)%>于<%=input_time%>) 
		        <%if(toOthersMap_menu_id.intValue()!=0){%><a href="javascript:location='../addlog.jsp?menu_id=<%=toOthersMap_menu_id%>';">[进入操作]</a><%}%>	
		      </td>
	       </tr>
	        <%}
	        }
	        %>
	        
	        
	        <TR>
			  <TD></td>  
			  <TD align="right"><a href="javascript:location='main.jsp?display_flag=1'";>[<font color="#0000FF">返回</font>]</a><TD>
         </TR> 
	</table>

</body>
</html>