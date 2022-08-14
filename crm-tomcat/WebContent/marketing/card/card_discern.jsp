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
<TITLE>证件扫描</TITLE>
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
// 检查文件夹是否存在
if (!objFSO.FolderExists("D:\\CARD")){
// 创建文件夹
	var strFolderName = objFSO.CreateFolder("D:\\CARD");
}
//文件操作 
function readFile(){
    var content = "";  //读取结果
    var ForReading = 1; //读文件开始行
    var strs= new Array();
    var i=1;
    var fso = new ActiveXObject("Scripting.FileSystemObject"); // 创建对象
    var b = fso.FileExists('D:\\CARD\\Result.txt'); //判断文件是否存在 
    if(b){
		
        var ts = fso.OpenTextFile('D:\\CARD\\Result.txt',ForReading); 
        while(!ts.AtEndofStream) {
            content=ts.ReadLine();
            if (i==1) { //证件类型
                strs=content.split(":");
                if (strs[1]=="二代证") {
                    document.theform.card_type.value="110801";
                }
            } else if (i==2) { //证件号码
                strs=content.split(":");
                document.theform.card_id.value=strs[1];
            } else if (i==3) { //姓名
                strs=content.split(":");
                document.theform.name.value=strs[1];
            } else if (i==4) { //性别
                strs=content.split(":");
                document.theform.sex1.value=strs[1];
                if(strs[1]=="男"){
                    document.theform.sex.value=1;
                }
                if(strs[1]=="女") {
                    document.theform.sex.value=2;
                }
            } else if (i==5) { //出生日期
                strs=content.split(":");
                document.theform.birth.value=strs[1];
            } else if (i==6) { //民族
                strs=content.split(":");
                document.theform.nation.value=strs[1];
            } else if (i==7) { //住址
                strs=content.split(":");
                document.theform.address.value=strs[1];
            }
			i++;
		}
		ts.Close(); 
	}
	var b2 = fso.FileExists('D:\\CARD\\Result2.txt'); //判断文件是否存在 
    if(b2){
        var ts2 = fso.OpenTextFile('D:\\CARD\\Result2.txt',ForReading); 
		i = 0;
        while(!ts2.AtEndofStream) {
            content2 = ts2.ReadLine();
			if (i == 8) { //有效期限
                strs = content2.split("-");
				if(strs.length > 1 && strs[1] != "")
					if(strs[1] == '长期'){
						document.theform.valid_date_input.value= '长期';
						document.theform.valid_date.value = 30000101;//有效期限为30000101为长期
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

/*取消*/
function CancelAction(){
	window.parent.document.getElementById("closeImg").click();	
}

//检验图片上传的格式
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
		sl_alert("<%=LocalUtilis.language("message.invalidImgFormat",clientLocale)%> ！");//图片的格式无效，请选择您要上传的JPG文件
		return false; 
	}                                                 
}

//扫描识别函数
function FnTcScanOcr(flag){
    try {
        var ocxObj = document.getElementById('TcCardOcr');
		if(flag == 2)
			//反面
        	ocxObj.TcScanOcr( 'D:\\CARD\\BackValue2.txt','D:\\CARD\\ScanSource2.jpg','D:\\CARD\\CardImage2.jpg','D:\\CARD\\HeadImage2.jpg','D:\\CARD\\Result2.txt');
		else
			//正面
			ocxObj.TcScanOcr( 'D:\\CARD\\BackValue.txt','D:\\CARD\\ScanSource.jpg','D:\\CARD\\CardImage.jpg','D:\\CARD\\HeadImage.jpg','D:\\CARD\\Result.txt');
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
    location = "card_discern.jsp?post_flag=<%=post_flag%>";       
}
function SaveAction(){
	if(document.all.Base64.object == null) { 
		alert("读取图片控件不存在，请注册控件！") 
		return;
	}
    var name = document.getElementsByName('theform')[0].name.value;
    var card_type = document.theform.card_type.value;
    if(card_type==""){
        sl_alert("请选择证件类型");
        return ;
    }
    if(name!=""){
		var fso = new ActiveXObject("Scripting.FileSystemObject"); // 创建对象 
		if(!fso.FileExists("D://CARD//CardImage.jpg")){
			sl_alert("证件图片不存在,请扫描");
	        return ;
		}
		//读取正面图片StrBase64字符串'/
    	document.theform.image1.value = Base64.EncodeFromFile("D://CARD//CardImage.jpg");;
		//读取反面图片StrBase64字符串
    	document.theform.image2.value = Base64.EncodeFromFile("D://CARD//CardImage2.jpg");;
    	document.theform.submit();
    }else{
        sl_alert("客户姓名为空");
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
		<font color="#215dc6"><b>证件扫描</b></font>
	</div>
</div>
<div style="margin-left:10px;margin-top:5px;">
	<div class="direct-panel">
		<fieldset>
			<legend><b style="color: blue;">证件信息</b></legend>
			<table>
				<tr>
					<td align="right">证件类型:</td>
					<td >
						<select onkeydown="javascript:nextKeyPress(this)" id ="card_type" name="card_type" style="WIDTH: 160px">
							<%=Argument.getCardTypeOptions2("")%>
						</select>
						<input maxlength="100" readonly="readonly" class="edline" name="input_value1" size="27" style="display: none;">
					</td>
					<td align="right">证件号码:</td>
					<td >
						<input maxlength="100" name="card_id" size="27"  onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
					<td align="right">生日:</td>
					<td >
	                    <input maxlength="100" name="birth" id = "birth" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
				</tr>
	           <tr>
					<td align="right">姓名:</td>
					<td >
	                    <input maxlength="100" name="name" id = "name" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
					<td align="right">性别:</td>
					<td >
						<input maxlength="100" name="sex1" id = "sex1" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
						<input maxlength="100" readonly="readonly" class="edline" name="sex" id = "sex" size="27" style="display: none;">
					</td>
					<td align="right">民族:</td>
					<td>
						<input maxlength="100" name="nation" id="nation" size="27" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
				</tr>
				<tr>
			       <td align="right">住址:</td>
					<td colspan="3">
						<input maxlength="100" name="address" id="address" size="70" onkeydown="javascript:nextKeyPress(this);" value="">
					</td>
					<td align="right">期限:</td>
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
			<legend><b style="color: blue;">证件照正面</b></legend>
		    <img border="0" src="D:\\CARD\\CardImage.jpg?temp=<%=Math.random()%>'" align="absmiddle" height="270" onerror="this.style.display='none'"></img>
		</fieldset>
	</div>
	<div style="float:right;width:48%" class="direct-panel">
		<fieldset style="height:280px;">
			<legend><b style="color: blue;">证件照反面</b></legend>
		    <img border="0" src="D:\\CARD\\CardImage2.jpg?temp=<%=Math.random()%>" align="absmiddle" height="270" onerror="this.style.display='none'"></img>
		</fieldset>
	</div>	
</div>
<br>
<div align="right" >
	<button type="button"  class="xpbutton3" onclick="javascript: FnTcScanOcr('1');">正面扫描</button>
	&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" onclick="javascript: FnTcScanOcr('2');">反面扫描</button>
	&nbsp;&nbsp;&nbsp;
	<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="确认" onclick="javascript: SaveAction();">确认</button>
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