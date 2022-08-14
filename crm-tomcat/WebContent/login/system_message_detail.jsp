<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.tools.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%
 Integer serial_no = Utility.parseInt(request.getParameter("serial_no"), new Integer(0));//获得连续的编号
 MessageLocal inform = EJBFactory.getMessage();
 MessageVO vo =new MessageVO();
 vo.setSerial_no(serial_no);
 List syslist=inform.listSystemMessage(vo);
 Map sysmap=null;
 String title="";
 Integer read_times=new Integer(0);
 String info_str="";
 Integer message_serial_no = new Integer(0);
 if(syslist.size()>0){
   sysmap=(Map)syslist.get(0);
   title=Utility.trimNull(sysmap.get("TITLE"));
   info_str=Utility.trimNull(sysmap.get("TASK_INFO"));
   read_times=Utility.parseInt(Utility.trimNull(sysmap.get("READ_TIMES")),new Integer(0));
   message_serial_no=Utility.parseInt(Utility.trimNull(sysmap.get("REC_NO")),new Integer(0));
 }
  String no_read_check_status="";
  String readed_check_status="";
  String no_clew_check_status="";
%>

<HTML>
<HEAD>
<TITLE>EnFo系统提醒信息</TITLE>
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
	if(sl_confirm("要提交回复"))
	{
		document.theform.df_serial_no.value=document.theform.serial_no.value;
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
      form.read_flag.value=3;         
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
</script>
</HEAD>
<body background="/images/bg.gif" leftmargin="0" topmargin="0"
	marginwidth="0" marginheight="0" onload="javascript:setLoad();">
<form name="theform" method="POST" >	
<input type=hidden name="read_flag">
<input type=hidden name="serial_no" value="<%=Utility.trimNull(serial_no)%>">
<table id="firstImage" width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" >
	
</table>
<table width="100%" border="0" align="center" cellpadding="0"
	cellspacing="0" bgcolor="#FD5818" class="font">
	<tr>
		<td   height="30" bgcolor="#fe5a1b">
		  <font color="#FFFFFF">
			 <div align="center">
			    <strong><%=title%></strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;阅览次数：<%=read_times%>
			 </div>
		 </font> 
		</td>		
	</tr>
	<tr>
		<td height="1"  bgcolor="#8F8F8F"></td>
	</tr>
 
</table>
<TABLE width="100%" border="0" align="center" cellpadding="0" bgcolor="#FFFFFF">
	<tr>
		<td >&nbsp;&nbsp;<%=info_str%></td>
   </tr>
	<tr>
				<td   align="right">	
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
		   <tr>
				<td ><hr noshade color="#808080" size="1"></td>
		</tr>
</TABLE>
<table bgcolor="#FFFFFF" align="center" width="100%">
	<tr>
		<td cellpadding="10" align="center">		
		[<a href="javascript:#" onclick="javascript:closeWindows();return false;"class="font2">关闭窗口</a>]</td>
	</tr>
</table>



</form>
</body>

</HTML>
<%inform.remove();
%>
