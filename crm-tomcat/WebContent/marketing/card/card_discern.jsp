<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.intrust.*,enfo.crm.customer.*,java.io.File"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer post_flag = Utility.parseInt(Utility.trimNull(request.getParameter("post_flag")), new Integer(0)); 
File n_file = new File("D:\\CARD");
if(!n_file.exists())
	 n_file.mkdirs();
 %>
<HTML>
<HEAD>
<TITLE>֤��ɨ��</TITLE>
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
//�ļ����� 
function readFile(){
    var content = "";  //��ȡ���
    var ForReading = 1; //���ļ���ʼ��
    var strs= new Array();
    var i=1;
    var fso = new ActiveXObject("Scripting.FileSystemObject"); // ��������
    var b = fso.FileExists('D:\\CARD\\Result.txt'); //�ж��ļ��Ƿ���� 
    if(b){
		
        var ts = fso.OpenTextFile('D:\\CARD\\Result.txt',ForReading); 
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
                document.theform.sex1.value=strs[1];
                if(strs[1]=="��"){
                    document.theform.sex.value=1;
                }
                if(strs[1]=="Ů") {
                    document.theform.sex.value=2;
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
            }
			i++;
		}
		ts.Close(); 
	}
	var b2 = fso.FileExists('D:\\CARD\\Result2.txt'); //�ж��ļ��Ƿ���� 
    if(b2){
        var ts2 = fso.OpenTextFile('D:\\CARD\\Result2.txt',ForReading); 
		i = 0;
        while(!ts2.AtEndofStream) {
            content2 = ts2.ReadLine();
			if (i == 8) { //��Ч����
                strs = content2.split("-");
				if(strs.length > 1 && strs[1] != "")
					if(strs[1] == '����'){
						document.theform.valid_date_input.value= '����';
						document.theform.valid_date.value = 30000101;//��Ч����Ϊ30000101Ϊ����
					}
					else{
						document.theform.valid_date_input.value= strs[1].replace(/\./g,"");
						document.theform.valid_date.value = document.theform.valid_date_input.value;
					}	
            }
			i++;
		}
		ts2.Close(); 
	}
}

/*ȡ��*/
function CancelAction(){
	window.parent.document.getElementById("closeImg").click();	
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

//ɨ��ʶ����
function FnTcScanOcr(flag){
    try {
        var ocxObj = document.getElementById('TcCardOcr');
		if(flag == 2)
			//����
        	ocxObj.TcScanOcr( 'D:\\CARD\\BackValue2.txt','D:\\CARD\\ScanSource2.jpg','D:\\CARD\\CardImage2.jpg','D:\\CARD\\HeadImage2.jpg','D:\\CARD\\Result2.txt');
		else
			//����
			ocxObj.TcScanOcr( 'D:\\CARD\\BackValue.txt','D:\\CARD\\ScanSource.jpg','D:\\CARD\\CardImage.jpg','D:\\CARD\\HeadImage.jpg','D:\\CARD\\Result.txt');
        var rt = ocxObj.TcGetBackValue();
        var rm = ocxObj.TcGetBackMessage();
        if( rt == 0 ) {
            var rr = ocxObj.TcGetResultFile();
        } else {
            alert( "ɨ��ʧ�ܣ�ɨ���Ƿ���ֵ:" + rt + ";������Ϣ:" + rm );
        }
    } catch (e) {
        alert("֤��ɨ��ʧ�ܣ�����ɨ����������IE�������ȫ���ã�");
        return false;
    }
    location = "card_discern.jsp?post_flag=<%=post_flag%>";       
}
function SaveAction(){
	if(document.all.Base64.object == null) { 
		alert("��ȡͼƬ�ؼ������ڣ���ע��ؼ���") 
		return;
	}
    var name = document.getElementsByName('theform')[0].name.value;
    var card_type = document.theform.card_type.value;
    if(card_type==""){
        sl_alert("��ѡ��֤������");
        return ;
    }
    if(name!=""){
		var fso = new ActiveXObject("Scripting.FileSystemObject"); // �������� 
		if(!fso.FileExists("D://CARD//CardImage.jpg")){
			sl_alert("֤��ͼƬ������,��ɨ��");
	        return ;
		}
		//��ȡ����ͼƬStrBase64�ַ���'/
    	document.theform.image1.value = Base64.EncodeFromFile("D://CARD//CardImage.jpg");;
		//��ȡ����ͼƬStrBase64�ַ���
    	document.theform.image2.value = Base64.EncodeFromFile("D://CARD//CardImage2.jpg");;
    	document.theform.submit();
    }else{
        sl_alert("�ͻ�����Ϊ��");
    }		 
}
</script>
</HEAD>
<BODY class="BODY body-nox" onload="readFile();">
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" enctype="multipart/form-data" action="/marketing/cache_cardinfo.jsp">
<input type="hidden" name ="post_flag" id ="post_flag" value="<%=post_flag %>">
<input type="hidden" name ="image1" id ="image1" value="">
<input type="hidden" name ="image2" id ="image2" value="">
<input type="hidden" name ="valid_date" id ="valid_date" value="">
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>֤��ɨ��</b></font>
	</div>
</div>
<div style="margin-left:10px;margin-top:5px;">
	<div class="direct-panel">
		<fieldset>
			<legend><b style="color: blue;">֤����Ϣ</b></legend>
			<table>
				<tr>
					<td align="right">֤������:</td>
					<td >
						<select onkeydown="javascript:nextKeyPress(this)" id ="card_type" name="card_type" style="WIDTH: 160px">
							<%=Argument.getCardTypeOptions2("")%>
						</select>
						<input maxlength="100" readonly="readonly" class="edline" name="input_value1" size="27" style="display: none;">
					</td>
					<td align="right">֤������:</td>
					<td >
						<input maxlength="100" name="card_id" size="27"  onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
					<td align="right">����:</td>
					<td >
	                    <input maxlength="100" name="birth" id = "birth" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
				</tr>
	           <tr>
					<td align="right">����:</td>
					<td >
	                    <input maxlength="100" name="name" id = "name" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
					<td align="right">�Ա�:</td>
					<td >
						<input maxlength="100" name="sex1" id = "sex1" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
						<input maxlength="100" readonly="readonly" class="edline" name="sex" id = "sex" size="27" style="display: none;">
					</td>
					<td align="right">����:</td>
					<td>
						<input maxlength="100" name="nation" id="nation" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
				</tr>
				<tr>
			       <td align="right">סַ:</td>
					<td colspan="3">
						<input maxlength="100" name="address" id="address" size="70" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
					<td align="right">����:</td>
					<td>
						<input maxlength="100" name="valid_date_input" id="valid_date_input" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
				</tr>
			</table>
			<br>
		</fieldset>
	</div>
</div>
<div style="margin-left:10px;margin-top:5px;">
	<div style="float:left;width:48%;" class="direct-panel">
		<fieldset style="height:280px;">
			<legend><b style="color: blue;">֤��������</b></legend>
		    <img border="0" src="D:\\CARD\\CardImage.jpg?temp=<%=Math.random()%>'" align="absmiddle" height="270" onerror="this.style.display='none'"></img>
		</fieldset>
	</div>
	<div style="float:right;width:48%" class="direct-panel">
		<fieldset style="height:280px;">
			<legend><b style="color: blue;">֤���շ���</b></legend>
		    <img border="0" src="D:\\CARD\\CardImage2.jpg?temp=<%=Math.random()%>" align="absmiddle" height="270" onerror="this.style.display='none'"></img>
		</fieldset>
	</div>	
</div>
<br>
<div align="right" >
	<button type="button"  class="xpbutton3" onclick="javascript: FnTcScanOcr('1');">����ɨ��</button>
	&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" onclick="javascript: FnTcScanOcr('2');">����ɨ��</button>
	&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="ȷ��" onclick="javascript: SaveAction();">ȷ��</button>
</div>
</form>
<OBJECT id="Base64" name ="Base64"
        CLASSID="CLSID:28656ABB-8E12-11D2-950F-000000000000"
        codebase="/includes/Base64.dll"
        height = "0"
        width = "0">
</OBJECT>
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