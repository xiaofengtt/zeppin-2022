<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%int pos_flag = Utility.parseInt(request.getParameter("pos_flag"), 0);
%>
<HTML>
<HEAD>
<TITLE>�������֤��ȡ</TITLE>
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
//�ļ����� 
function readFile(p){
    var path=p;//�ļ�·��
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
	    ts.Close(); 
	}
}

/*ȡ��*/
function CancelAction(){
	window.parent.document.getElementById("closeImg").click();	
}

/*ȷ��*/
function SaveAction(){
    var name = document.getElementsByName('theform')[0].name.value;
	var card_type=document.theform.card_type.value;
    var imageIdentification=document.theform.imageIdentification.value;
	
	var fso = new ActiveXObject("Scripting.FileSystemObject"); // �������� 
	if(!fso.FileExists("D://CARD//HeadImage.jpg")){
		sl_alert("֤��ͼƬ������,������ɨ��");
        return ;
	}
	//��ȡ����ͼƬStrBase64�ַ���'/
    document.theform.image1.value = Base64.EncodeFromFile("D://CARD//HeadImage.jpg");;
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
//����ͼƬ�ϴ��ĸ�ʽ
function checkFileType()  
{  
	var custFile = document.getElementById("imageIdentification");  
	var filePath = custFile.value;
	var dotNdx = filePath.lastIndexOf('.');          
	var exetendName = filePath.slice(dotNdx + 1).toLowerCase();    

	if((exetendName == "jpg")) {
 		return true;    
	}else{
		custFile.select()
  		document.execCommand("Delete");
   		custFile.focus(); 
		sl_alert("<%=LocalUtilis.language("message.invalidImgFormat",clientLocale)%> ��");//ͼƬ�ĸ�ʽ��Ч����ѡ����Ҫ�ϴ���JPG�ļ�
		return false; 
	}
}  
</script>
</HEAD> <body onload="readFile('D:\\Result.txt')" class="body body-nox">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" enctype="multipart/form-data" action="/marketing/cache_cardinfo.jsp">
<INPUT type='hidden' name='pos_flag' value=<%=pos_flag %>>
<input type="hidden" name ="image1" id ="image1" value="">
<input type="hidden" name ="image2" id ="image2" value="">
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>�������֤����</b></font>
	</div>
</div>
<br/>
<div style="margin-left:10px;margin-top:5px;">
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
</tr>
<tr>
<td align="right">סַ:</td>
				<td colspan="3">
                   <input maxlength="100" name="address" id="address" size="70" onkeydown="javascript:nextKeyPress(this)">
				</td>
</tr>
			</table>
<table>
		<tr><TD align="left"><font color="red"><strong>֤���Ķ�ͼƬ·�� D:\CARD\HeadImage.jpg</font></td></tr>
		
			<TR>
					<TD><INPUT type="hidden" name="imageIdentification" type="file" size="70"  value="" id="imageIdentification" onchange="javascript:checkFileType();"></TD>
			</TR>
		</table>
		</fieldset>
	</div>
	<div style="float:right;width:49%;" align="center" class="direct-panel">
		<fieldset style="height:362px;">
			<legend><b style="color: blue;">֤����</b></legend>
		<img border="0" src="D:\\CARD\\HeadImage.jpg" align="absmiddle" ></img>
		</fieldset>
	</div>
</div>
<br>
<div align="right" style="margin-to[:5px">
	<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="��һ��" onclick="javascript: SaveAction();">ȷ��</button>
	&nbsp;&nbsp;&nbsp;	
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">�ر� (<u>C</u>)</button>
</div>
</form>
<OBJECT id="Base64" name ="Base64"
    CLASSID="CLSID:28656ABB-8E12-11D2-950F-000000000000"
    codebase="/includes/Base64.dll"
    height = "0"
    width = "0">
</OBJECT>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>