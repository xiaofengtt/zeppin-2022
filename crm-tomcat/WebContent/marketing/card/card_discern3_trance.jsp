<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.File" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%int pos_flag = Utility.parseInt(request.getParameter("pos_flag"), 0);
File n_file = new File("D:\\CARD");
if(!n_file.exists())
	 n_file.mkdirs();
%>
<HTML>
<HEAD>
<TITLE>身份证读取中转页面</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
//读卡函数
function FnTcReadIdCardII(){
    try {
        var ocxObj = document.getElementById('TcCardOcr');
        ocxObj.TcReadIdCardII( 'D:\\CARD\\BackValue.txt','D:\\CARD\\HeadImage.jpg','D:\\CARD\\Result.txt',1001,10);
        var rt = ocxObj.TcGetBackValue();
        var rm = ocxObj.TcGetBackMessage();
        if( rt == 0 ) {
            var rr = ocxObj.TcGetResultFile();
        } else {
			alert( "扫描失败，扫描仪返回值:" + rt + ";返回信息:" + rm );
		}
    } catch (e) {
		alert("证件扫描失败，请检查扫描仪驱动或IE浏览器安全设置！");
        return false;
    }  
    location = "card_discern3.jsp?pos_flag=<%=pos_flag%>";
}
</script>
</HEAD> 
 <BODY class="BODY body-nox" onload="FnTcReadIdCardII();">
<%@ include file="/includes/waiting.inc"%>
<OBJECT
	  name=TcCardOcr
	  classid="clsid:6EAFC189-D17E-4E3F-905C-D5A2BC4E055A"
	  codebase="/includes/card/TcIdCard5.ocx"
	  width=0
	  height=0
	  align=center
	  hspace=0
	  vspace=0
>
</OBJECT>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>