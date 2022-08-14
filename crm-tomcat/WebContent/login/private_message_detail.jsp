<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%
 //Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));
 Integer message_serial_no = Utility.parseInt(request.getParameter("message_serial_no"), new Integer(0));//通知的id
 int send_derection = Utility.parseInt(request.getParameter("send_derection"), 0);//1 等待我处理的事务 2 我发送给别人的事务
 
 //Utility.debugln("send_derection:"+send_derection);
 
 
 MessageLocal inform = EJBFactory.getMessage();
 MessageVO vo =new MessageVO();
 vo.setSerial_no(message_serial_no);
 List syslist=inform.listPrivateMessage(vo);
 Map sysmap=null;
 String title="";
 Integer read_times=new Integer(0);
 String info_str="";
 String input_time="";
 Integer input_man=new Integer(0);
 if(syslist.size()>0){
   sysmap=(Map)syslist.get(0);
   title=Utility.trimNull(sysmap.get("TITLE"));
   info_str=Utility.trimNull(sysmap.get("INFO_STR"));
   read_times=Utility.parseInt(Utility.trimNull(sysmap.get("READ_TIMES")),new Integer(0));
   input_man=Utility.parseInt(Utility.trimNull(sysmap.get("INPUT_MAN")),new Integer(0));
   input_time=Utility.trimNull(sysmap.get("INPUT_TIME"));
 }
  String no_read_check_status="";
  String readed_check_status="";
  String no_clew_check_status="";
 // List read_status_list=inform.listReaders(message_serial_no);
  //if(read_status_list.size()>0){
  // Map read_status_map=(Map)read_status_list.get(0);
   //  int temp_read_flag=Utility.parseInt(Utility.trimNull(read_status_map.get("READ_FLAG")),0);
    // if(temp_read_flag==1)
       // no_read_check_status="checked";
    // else if(temp_read_flag==2)
      //  readed_check_status="checked";
    // else if(temp_read_flag==3)
       // no_clew_check_status="checked";
  //}
 boolean success=false;
 //添加回复信息
 if(request.getMethod().equals("POST")){
    message_serial_no = Utility.parseInt(request.getParameter("message_serial_no"), new Integer(0));
   String post_reply_info= Utility.trimNull(request.getParameter("info_str"));
   inform.appendReply(message_serial_no,post_reply_info,input_operatorCode);
   success=true;
 }
%>

<HTML>
<HEAD>
<TITLE>EnFo个人通知信息</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<script type="text/javascript" src="/webEditor/fckeditor.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type='text/javascript' src='/dwr/interface/checkService.js'></script>
<script type='text/javascript' src='/dwr/engine.js'></script>
<script type='text/javascript' src='/dwr/util.js'></script>
<meta http-equiv="Expires" content="0">
<base target="_self">
<style type="text/css">
<!--
body, td, th {
	font-family: 宋体;
	font-size: 12px;
	color: #000000;
}

body {
	margin-top: 3px;
}

a:link {
	text-decoration: none;
	color: #000000;
}

a:visited {
	text-decoration: none;
	color: #000000;
}

a:hover {
	text-decoration: underline;
	color: #ff4000;
}

a:active {
	text-decoration: none;
	color: #ff4000;
}

.bk {
	border: thin dotted #000000;
}
-->
</style>
<script language="javascript">
<%if(success){%>
  // sl_alert("回复成功！");
  //location="private_message_detail.jsp";
<%}%>
var oFCKeditor = new FCKeditor( 'info_str' ) ;
oFCKeditor.BasePath = '/webEditor/' ;
//oFCKeditor.ToolbarSet = 'Basic' ;
oFCKeditor.Width = '100%' ;
oFCKeditor.Height = '120' ;										

function setLoad()
{
	firstImage.focus();
}
function setInfoPost()
{
    if(getContentValue() == '') 
    {
      alert('请输入回复内容');return false;
    }
    
	if(sl_confirm("要提交回复"))
	{
	
		document.theform.submit();
	}	
}

function setMessageReadStatusValue(form,cur_checkbox,flag){
   
   if(cur_checkbox.checked && flag==1){//未读
     form.readed.checked=false;
     form.no_clew.checked=false;
     form.read_flag.value=1;
   }else if(cur_checkbox.checked && flag==2){//已读
      form.no_read.checked=false;
      form.no_clew.checked=false;
      form.read_flag.value=2;
   }else if(cur_checkbox.checked && flag==3){//不再提示
      form.readed.checked=false;
      form.no_read.checked=false;
      <%if(send_derection==1){//等待我处理的事务%>
         form.read_flag.value=3;         
       <%} else if(send_derection==2) {//我发起的事务%> 
          form.read_flag.value=11; 
       <%}%>
   }else{
      form.read_flag.value=0;
   }  
   //alert(form.read_flag.value);   
}

function setMessageReadStatus(){
   checkService.setMessageReadStatus(<%=message_serial_no%>,document.theform.read_flag.value,<%=input_operatorCode%>,{callback: function(data){
                                                           if(data=="0")
                                                              sl_alert("执行更改该消息的读取状态失败！");                                                                                     }
                                                             });
}


function closeWindows(){
  setMessageReadStatus();
  window.returnValue = 1;
  window.close();
  
}

/**
add by tsg 2008-11-04
返回fckeditor控件的值
if(getContentValue() == '') {alert('请输入内容');return false;}	
*/
function getContentValue()
{
       var oEditor = FCKeditorAPI.GetInstance('info_str') ;
       var acontent=oEditor.GetXHTML();
       return acontent;
}
</script>
</HEAD>
<body background="/images/bg.gif" leftmargin="0" topmargin="0"
	marginwidth="0" marginheight="0" onload="javascript:setLoad();">
<form name="theform" method="POST" action="private_message_detail.jsp">	
<input type=hidden name="message_serial_no" value="<%=Utility.trimNull(message_serial_no)%>">
<input type=hidden name="send_derection" value="<%=send_derection%>">
<input type=hidden name="read_flag">
<table id="firstImage" width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" >

</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" bgcolor="#FD5818" class="font">
	<tr>
		<td   height="30" bgcolor="#fe5a1b"><font color="#FFFFFF">
		<div align="left"><strong>&nbsp;&nbsp;主&nbsp;&nbsp;题：<%=title%></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%//=read_times%></div>
		</font></td>
		<td   bgcolor="#fe5a1b" noWrap="true"  align="right"><font color="#FFFFFF" >
     		发布时间：<%=input_time%>&nbsp;&nbsp;&nbsp;发布人：<%=Argument.getOpName(input_man)%>    	
     </font></td>
	</tr>
	 <tr>
		<td height="1" colspan="2" bgcolor="#8F8F8F"></td>
	</tr>		
</table>

<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" class="font">
	<tr>
		<td height="233" valign="top" bgcolor="#FFFFFF">
	
		<table width="100%" border="0" align="right" cellpadding="2" cellspacing="0">
			<tr>
				<td colspan=2>&nbsp;&nbsp;<%=info_str%></td>
		   </tr>
		   	<tr>
				<td colspan=2  align="right">	
				  <table>
				    <tr>			        
				       <td style="display:none;"><input type="checkbox" name="no_read" class="selectAllBox" <%=no_read_check_status%> onclick="javascript:setMessageReadStatusValue(document.theform,this,1);"></td> 
				       <td style="display:none;">未读</td>    
				       <td  style="display:none;"><input type="checkbox" name="readed" class="selectAllBox" <%=readed_check_status%>  onclick="javascript:setMessageReadStatusValue(document.theform,this,2);"></td>
				       <td style="display:none;">已读</td> 
				       <td><input type="checkbox" name="no_clew" class="selectAllBox" <%=no_clew_check_status%>  onclick="javascript:setMessageReadStatusValue(document.theform,this,3);"></td> 
				       <td>不再提示</td>
			        </tr>
			      </table>
			  		 
				</td>  
		   </tr>
			
	<%
	   //回复列表
	   List reply_list=inform.listReply(message_serial_no);
	   Map reply_map=null;
	    String  replay_info_str="";
        String  replay_input_time="";
        Integer replay_input_man=new Integer(0);
		for(int i=0;i<reply_list.size();i++)
		{
		   reply_map=(Map)reply_list.get(i);
		    replay_info_str=Utility.trimNull(reply_map.get("REPLY_STR"));
		    replay_input_man=Utility.parseInt(Utility.trimNull(reply_map.get("REPLY_MAN")),new Integer(0));
		    replay_input_time=Utility.trimNull(reply_map.get("REPLY_TIME"));
		
		%>		
		<tr>
				<td colspan=2 ><hr noshade color="#808080" size="1"></td>
		</tr>
		<tr>
				<td colspan=2 bgcolor="D8D7D7">&nbsp;&nbsp;<%=replay_info_str%></td>
		</tr>
		<tr>
				<td bgcolor="D8D7D7">&nbsp;</td>
				
				<td bgcolor="D8D7D7" align=right>
				回复人：<%=Utility.trimNull(Argument.getOpName(replay_input_man))%>&nbsp;&nbsp;<%=replay_input_time%>
				
				</td>
		</tr>
     <%}%>
     
     <tr>
	  	<td align="left"><b>回复:</b></td>
	  	<td>
		</td>
		</tr>		
	  <tr>
	  	<td valign=bottom colspan=2><textarea rows="5" name="info_str" cols="40"></textarea>
			<script type="text/javascript">
			oFCKeditor.ReplaceTextarea() ;										
			</script>
		</td>
		</tr>
		
	</table>
		</td>
	</tr>
</table>

<table bgcolor="#FFFFFF" align="center" width="100%">
	<tr>
		<td cellpadding="10" align="center">
[<a href="#"  onclick="javascript:setInfoPost();return false;";class="font2">提交回复</a>]&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		[<a href="javascript:#" onclick="javascript:closeWindows();return false;" class="font2">关闭窗口</a>]</td>
	</tr>
</table>



</form>
</body>

</HTML>
<%inform.remove();
%>
