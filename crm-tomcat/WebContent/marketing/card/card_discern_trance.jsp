<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.intrust.*,enfo.crm.customer.*,java.io.File"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer post_flag = Utility.parseInt(Utility.trimNull(request.getParameter("post_flag")), new Integer(0)); 
 %>
<HTML>
<HEAD>
<TITLE>֤��ɨ����ת</TITLE>
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
var objFSO = new ActiveXObject("Scripting.FileSystemObject");
// ����ļ����Ƿ����
if (!objFSO.FolderExists("D:\\CARD")){
// �����ļ���
	var strFolderName = objFSO.CreateFolder("D:\\CARD");
}
//ɨ��ʶ����
function FnTcScanOcr(){
    try {
        var ocxObj = document.getElementById('TcCardOcr');
		ocxObj.TcScanOcr('D:\\CARD\\BackValue.txt','D:\\CARD\\ScanSource.jpg','D:\\CARD\\CardImage.jpg','D:\\CARD\\HeadImage.jpg','D:\\CARD\\Result.txt');
		
		var rt = ocxObj.TcGetBackValue();
        var rm = ocxObj.TcGetBackMessage();
        if( rt == 0 ) {
            var rr = ocxObj.TcGetResultFile();
        } else {
            alert( "ɨ��ʧ�ܣ�ɨ���Ƿ���ֵ:" + rt + ";������Ϣ:" + rm );
        }
    } catch (e) {
        alert("֤��ɨ��ʧ�ܣ�����ɨ����������IE�������ȫ���ã�"+e.message);
        return false;
    }
    location = "card_discern.jsp?post_flag=<%=post_flag%>";
}
</script>
</HEAD>
<BODY class="BODY body-nox" onload="FnTcScanOcr();">
<%@ include file="/includes/waiting.inc"%>
<OBJECT
	  name=TcCardOcr
	  classid="clsid:6EAFC189-D17E-4E3F-905C-D5A2BC4E055A"
	  codebase="/includes/card/TcIdCard5.ocx"
	  width=0
	  height=0
	  hspace=0
	  vspace=0
>
</OBJECT>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>