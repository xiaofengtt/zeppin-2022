<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<HTML>
<HEAD>
<TITLE>���֤��ȡ-ͨ��ѡ���ļ���ȡ</TITLE>
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

<%
//������ȡ
String result="";
String image="";
result=request.getParameter("resultTxt");
image=request.getParameter("image");
int pos_flag=Utility.parseInt(request.getParameter("pos_flag"), 0);
// ͼƬ���渨������
byte [] bx = new byte[0];
java.io.InputStream ins = new java.io.ByteArrayInputStream(bx);
java.io.InputStream in = null;

SmartUpload su = new SmartUpload();

if (request.getMethod().equals("POST")){	
	// �ϴ���ʼ�� 
	su.initialize(pageContext);
	su.setTotalMaxFileSize(50000000);
	su.setAllowedFilesList("jpg,gif,txt"); //����ĸ�ʽ
	su.upload();
	pos_flag = Utility.parseInt(su.getRequest().getParameter("pos_flag"),0);
System.out.println("su.upload--pos_flag="+pos_flag);
	int i= su.getFiles().getCount();
	if(i==2){
		com.jspsmart.upload.File file = su.getFiles().getFile(1);
		int iFileSize = su.getFiles().getFile(1).getSize();
		if (iFileSize > 5* 1024 * 1024) {
	   		throw new BusiException("�ļ���С���ܳ���5M��");
		}
		bx = new byte[file.getSize()];
		for (int ii = 0; ii != file.getSize(); ii++) {
			bx[ii] = file.getBinaryData(ii);
		}
		ins = new java.io.ByteArrayInputStream(bx);		
	}
}
session.setAttribute("inputstream",ins);
%>

//�ļ����� 
function readFile(){
    var path=document.theform.result.value;
    var content = "";  //��ȡ���
    var ForReading = 1; //���ļ���ʼ��
    var strs= new Array(); 

    var i=1;
    var fso = new ActiveXObject("Scripting.FileSystemObject"); // �������� 
    var b = fso.FileExists(path); //�ж��ļ��Ƿ���� 
    if(b){
        var ts = fso.OpenTextFile(path,ForReading); 
        while(!ts.AtEndofStream) {
            content=ts.ReadLine();
            if (i==1) { //֤������
                strs=content.split(":");
                if (strs[1]=="����֤") {
                    document.theform.card_type.value="110801";
                }
            } else if (i==2) { //֤������
                strs=content.split(":");
                document.theform.card_id.value=strs[1];
            } else if (i==3) { //����
                strs=content.split(":");
                document.theform.name.value=strs[1];
            } else if (i==4) { //�Ա�
                strs=content.split(":");
                document.theform.sex.value=strs[1];
                if(strs[1]=="��"){
                    document.theform.sex1.value=1;
                }
                if(strs[1]=="Ů") {
                    document.theform.sex1.value=2;
                }
            } else if (i==5) { //��������
                strs=content.split(":");
                document.theform.birth.value=strs[1];
            } else if (i==6) { //����
                strs=content.split(":");
                document.theform.nation.value=strs[1];
            } else if (i==7) { //סַ
                strs=content.split(":");
                document.theform.address.value=strs[1];
            } else if (i==8) { //ǩ������
                strs=content.split(":");
                document.theform.issueDate.value=strs[1];
            } else if (i==9) { //��Ч����
                strs=content.split(":");
                document.theform.period.value=strs[1];
            } else if (i==12) { //ǩ���ػ����
                strs=content.split(":");
                document.theform.issuePlace.value=strs[1];
            }
            i++;
        }
    }
    ts.Close(); 
}
/*ȡ��*/
	function CancelAction(){
			window.parent.document.getElementById("closeImg").click();	
	}
/*ȷ��*/
function SaveAction(){
	var name=document.theform.name.value;
	var card_type=document.theform.card_type.value;

    if(card_type==""){
        sl_alert("��ѡ��֤������");
        return ;
    }
    if(name!=""){
        document.theform.submit();
        //CancelAction();
	}else{
       sl_alert("�ͻ�����Ϊ��");
    }		 
}
</script>
</HEAD> <body onload="readFile()" class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" enctype="multipart/form-data" action="/marketing/cache_cardinfo.jsp">
<INPUT type="hidden" id="result" name="result" value="<%=result%>">
<INPUT type='hidden' id="pos_flag" name='pos_flag' value=<%=pos_flag %>>
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>�ֳ��豸֤��ʶ����</b></font>
	</div>
</div>
<div style="margin-left:10px;margin-top:5px;" >
	<div style="float:left;width:49%;" class="direct-panel">
		<fieldset style="height:362px;">
			<legend><b style="color: blue;">֤����Ϣ</b></legend>
			<table>
				<tr>
				<td align="right">֤������:</td>
				<td >
					<select onkeydown="javascript:nextKeyPress(this)" id ="card_type" name="card_type" style="WIDTH: 160px">
						<%=Argument.getCardTypeOptions2("")%>
					</select>
					<select onkeydown="javascript:nextKeyPress(this)" id ="card_type1"name="card_type1" style="WIDTH: 160px;display:none" >
						<%=Argument.getCardTypeJgOptions2("")%>
					</select>	
					<input maxlength="100" readonly="readonly" class="edline" name="input_value1" size="27" style="display: none;">
				</td>
				<td align="right">֤������:</td>
				<td >
					<input maxlength="100" name="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
				</tr>
	           <tr>
				<td align="right">����:</td>
				<td >
                    <input maxlength="100" name="name" id = "name" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
				<td align="right">�Ա�:</td>
				<td >
					<input maxlength="100" name="sex" id = "sex" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					<input maxlength="100" readonly="readonly" class="edline" name="sex1" id = "sex1" size="27" style="display: none;">
				</td>
				</tr>
                 <tr>
				<td align="right">����:</td>
				<td >
                    <input maxlength="100" name="birth" id = "birth" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
				<td align="right">����:</td>
				<td>
					<input maxlength="100" name="nation" id="nation" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
				</tr>

<tr>
				<td align="right">ǩ����:</td>
				<td >
                    <input maxlength="100" name="issuePlace" id = "issuePlace" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
				<td align="right">ǩ������:</td>
				<td>
					<input maxlength="100" name="issueDate" id="issueDate" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
				</tr>
 <tr>
       
<td align="right">��Ч����:</td>
				<td >
                    <input maxlength="100" name="period" id = "period" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
				</td>
<td align="right">סַ:</td>
				<td>
                    <textarea rows="3" name="address" id="address" onkeydown="javascript:nextKeyPress(this)" cols="27"></textarea>
				</td>
</tr>
			</table>
		</fieldset>
	</div>
	<div style="float:right;width:49%;" align="center" class="direct-panel">
		<fieldset style="height:362px;">
			<legend><b style="color: blue;">֤����</b></legend>
		<img border="0" src="<%=image%>" align="absmiddle" height=90% width="100%" ></img>
		</fieldset>
	</div>
</div>
<br>
<div align="right" style="margin-to[:5px">
	<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="ȷ��" onclick="javascript: SaveAction();">ȷ��</button>
	&nbsp;&nbsp;&nbsp;	
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">�ر� (<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>