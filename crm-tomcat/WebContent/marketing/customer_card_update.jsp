<%@ page contentType="text/html; charset=GBK" import="java.util.*,enfo.crm.util.*,enfo.crm.service.*,java.math.*,enfo.crm.intrust.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.web.*" %>
<jsp:directive.page import="com.enfo.webservice.client.SAPClient"/>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
int upload_flag = Utility.parseInt(request.getParameter("upload_flag"),0);
// 页面变量
boolean bSuccess = false;
int iCount = 0;
List list = null;
Map map = new HashMap();
// 客户信息显示辅助变量
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer card_type = new Integer(0);
String card_type_name = "";
String card_id =  "";
// 图片保存辅助变量
byte [] bx = new byte[0];
java.io.InputStream ins = new java.io.ByteArrayInputStream(bx);
java.io.InputStream ins1 = new java.io.ByteArrayInputStream(bx);
String filePathName = null;
// 获得对象
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();//同步用客户对象
CustomerLocal cust_local = EJBFactory.getCustomer();
CustomerVO cust_vo = new CustomerVO();

java.io.InputStream ins_saved1 = new java.io.ByteArrayInputStream(bx);
java.io.InputStream ins_saved2 = new java.io.ByteArrayInputStream(bx);
String returnV = ""; //cust_local.listByAll(1);
//将图片转换成二进制流在保存
SmartUpload su = new SmartUpload();
if (request.getMethod().equals("POST")){
	DocumentFile2 file2 = new DocumentFile2(pageContext);
	file2.parseRequest();
	cust_type = Utility.parseInt(file2.getParameter("cust_type"), new Integer(0));
	upload_flag = Utility.parseInt(file2.getParameter("upload_flag"), 0);
 	if(cust_type.intValue() ==2 || upload_flag == 1){//机构
		cust_id = Utility.parseInt(file2.getParameter("cust_id"), new Integer(0));
		if(file2.uploadAttchment(new Integer(4),cust_id,"",null,null,input_operatorCode)){
			bSuccess = true;
		}
	}else{//
		// 上传初始化 
		su.initialize(pageContext);
		su.setTotalMaxFileSize(500000000);
		su.setAllowedFilesList("jpg,gif,pdf"); //允许的格式
		su.upload();
		cust_id = Utility.parseInt(su.getRequest().getParameter("cust_id"),new Integer(0));		
		// 逐一提取上传文件信息，同时可保存文件。 
		for (int i = 0; i < su.getFiles().getCount(); i++) {
			com.jspsmart.upload.File file = su.getFiles().getFile(i);
			// 若文件不存在则继续 
			if (file.isMissing())continue;
			int iFileSize = su.getFiles().getFile(i).getSize();
			
			if (iFileSize == 0)
				continue;
			if (iFileSize > 50* 1024 * 1024)
				throw new BusiException("文件大小不能超过50M！");
			
			bx = new byte[file.getSize()];
			for (int ii = 0; ii != file.getSize(); ii++) {
				bx[ii] = file.getBinaryData(ii);
			}
			if(i>0){
				ins1 = new java.io.ByteArrayInputStream(bx);
			}else{
				ins = new java.io.ByteArrayInputStream(bx);
			}
			
		}
		//保存输入流	
		cust_vo.setInputStream(ins);
		cust_vo.setInputStream1(ins1);
		//保存输入信息
		cust_vo.setCust_id(cust_id);
		cust_vo.setCard_type(su.getRequest().getParameter("card_type"));
		cust_vo.setCard_id(su.getRequest().getParameter("card_id"));
		cust_vo.setInput_man(input_operatorCode);
		cust_local.cudProcEdit1(cust_vo);
		bSuccess = true;
	}	
} 
//cust_id不等于0，则查询查询单条客户信息
if (cust_id.intValue()>0){
	cust_vo.setCust_id(cust_id);
	cust_vo.setInput_man(input_operatorCode);
	list = cust_local.listProcAll(cust_vo);
	if(list.size()>0){
		map = (Map) list.get(0);
		cust_name = Utility.trimNull(map.get("CUST_NAME"));
		cust_type = Utility.parseInt(Utility.trimNull(map.get("CUST_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map.get("CUST_TYPE_NAME"));
		card_type = Utility.parseInt(Utility.trimNull(map.get("CARD_TYPE")),new Integer(0));
		card_type_name = Utility.trimNull(map.get("CARD_TYPE_NAME"));
		card_id = Utility.trimNull(map.get("CARD_ID"));
	}		
}
%>

<HTML>
<HEAD>
<TITLE>更新客户证件信息</TITLE>
<BASE TARGET="_self">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<style>
h1 {
	text-align:center;
	font-size:2.2em;
}
#divc {
	border:1px solid #555;
}
.des {
	width:500px;
	background-color:lightyellow;
	border:1px solid #555;
	padding:25px;
	margin-top:25px;
}
.mouseover {
	color:#ffffff;
	background-color:highlight;
	width:100%;
	cursor:default;
}
.mouseout {
	color:#000;
	width:100%;
	background-color:#ffffff;
	cursor:default;
}
</style>
<script language=javascript>
<%if(bSuccess){%>
	sl_update_ok();
	window.returnValue = 1;		
	window.close();
<%}%>
//检验图片上传的格式
function checkFileType(filePath)  
{   
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
//验证客户名称
function validateForm(form)
{
	var cust_type = document.theform.cust_type.value;
	var upload_flag = document.theform.upload_flag.value;
	if(cust_type == 2 || upload_flag == 1){
		var jg_card = document.theform.jg_card.value;
		if(jg_card == ""){
	        sl_alert("请选择上传文件");
	        return ;
	    }
	}else{
		var imageIdentification = document.theform.imageIdentification.value;
		if(imageIdentification == ""){
	        sl_alert("请选择上传照片路径");
	        return ;
	    }
	}
	document.theform.submit();
}
function confirmRemoveAttach(obj,serial_no)
{
	if(confirm('<%=LocalUtilis.language("message.tAreYouSure",clientLocale)%>？'))
	{

		var updatehref = '/marketing/sell/attach_remove.jsp?serial_no='+serial_no;
    	if(showModalDialog(updatehref, '', 'dialogWidth:0px;dialogHeight:0px;dialogLeft:0px;dialogTop:0px;status:0;help:0') != null)
		{
		}
		if(obj != null)
			obj.style.display="none";
	}
}
function goUrl(url){
	var ifr = document.getElementById('download');   
	ifr.src = url;  
}
</script>
</HEAD>
<BODY class="BODY body-nox">&nbsp; 
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post" action="customer_card_update.jsp" enctype="multipart/form-data">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type="hidden" name="upload_flag" value="<%=upload_flag%>">
<div class="page-title">
	<font color="#215dc6"><b>更新客户证件信息</b></font><!--客户信息-->
</div>
<div  id="r1" align="left"  style="margin-top:10px;margin-right:10px;height:350px;">
	<table border="0" width="100%" cellspacing="3" cellpadding="3" class="product-list">
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> ：</td><!--客户名称-->
			<td>
				<input name = "cust_name" readonly class="edline" value="<%=Utility.trimNull(cust_name) %>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.custType",clientLocale)%>：</td><!--客户类型-->
			<td>
				<input name="cust_type_name" readonly class='edline' value="<%=Utility.trimNull(cust_type_name)%>" size="25">
				<input type="hidden" name="cust_type" value="<%=Utility.trimNull(cust_type)%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> ：</td><!--证件类型-->
			<td>
				<input name = "card_type_names" readonly class='edline' value="<%=Utility.trimNull(card_type_name)%>">
				<input type="hidden" name="card_type" value="<%=Utility.trimNull(card_type)%>">
			</td>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> ：</td><!--证件号码-->
			<td>
				<!--客户如果是个人，输入完成后回车自动显示客户对应的性别，生日，年龄-->
				<input name="card_id" readonly class='edline' value="<%=Utility.trimNull(card_id)%>" size="25">
			</td>
		</tr>
		<%if(cust_type.intValue() == 2 || upload_flag == 1) {
			//获得客户附件
			AttachmentLocal attachmentLocal = EJBFactory.getAttachment();
			AttachmentVO attachment_vo = new AttachmentVO();
			attachment_vo.setDf_talbe_id(new Integer(4));
			attachment_vo.setDf_serial_no(cust_id);
			List attachmentList = attachmentLocal.load(attachment_vo);
			Iterator attachment_it = attachmentList.iterator();
			HashMap attachment_map = new HashMap();
			if(attachmentList.size() > 0){	
		%>
		<tr>
			<td colspan="1" align="right">已上传客户证件：</td>
			<td colspan="3">
		<%	
			
	       while(attachment_it.hasNext()){
				attachment_map = (HashMap)attachment_it.next();
				String attachment_id = Utility.trimNull(attachment_map.get("ATTACHMENTID"));
				String origin_name = Utility.trimNull(attachment_map.get("ORIGIN_NAME"));
		%>
				<div id="divattach<%=Utility.trimNull(attachment_map.get("ATTACHMENTID"))%>" style="display:">
				<a title="下载附件" href="#" onclick="javascript:goUrl('/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>&name=<%=origin_name%>');" ><%=origin_name%></a>
		            	&nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button"  class="xpbutton2" accessKey=d id="btnSave" name="btnSave"
		            		onclick="javascript:confirmRemoveAttach(divattach<%=attachment_id%>,'<%=attachment_id%>$<%=Utility.replaceAll(Utility.trimNull(attachment_map.get("SAVE_NAME")),"\\","/")%>');">删除</button><br>
				</div>	
				
		<%}
			}%>
			<iframe id="download" name="download" height="0px" width="0px"></iframe><!--用iframe模拟文件下载-->
			</td>
		</tr>
		<tr>
			<td colspan="4">&nbsp;</td>
		</tr>	
		<tr>
			<td align="right"><font color="red"><b>*</b></font>上传客户证件：</td><!--证件扫描图片-->
			<td colspan="3"><INPUT name="jg_card" type="file" size="70" id="jg_card"></td>
		</tr>
		<%}else{ %>
		<tr>
			<td align="right"><font color="red"><b>*</b></font><%=LocalUtilis.language("class.imageIdentification",clientLocale)%>(正面) ：</td><!--证件扫描图片-->
			<td colspan="3"><INPUT name="imageIdentification" type="file" size="70" id="imageIdentification" onchange="javascript:checkFileType(this.value);"></td>
		</tr>
		<tr>
			<td align="right"> <%=LocalUtilis.language("class.imageIdentification",clientLocale)%> (反面) ：</td><!--证件扫描图片-->
			<td colspan="3"><INPUT name="imageIdentification2" type="file" size="70" id="imageIdentification2" onchange="javascript:checkFileType(this.value);"></td>
		</tr>
		<%} %>
				
	</table>
</div>

<br>
<div align="right" style="margin-right:5px">
	<!--保存-->
    <button type="button"  class="xpbutton3" accesskey="s" id="btnSave" name="btnSave" onclick="javascript:validateForm(document.theform);"><%=LocalUtilis.language("message.save",clientLocale)%> (<U>S</U>)</BUTTON>
	&nbsp;&nbsp;
	<!--关闭-->
    <button type="button"  class="xpbutton3" accesskey="b" id="btnReturn" name="btnReturn" onclick="javascript:disableAllBtn(true);window.close();"><%=LocalUtilis.language("message.close",clientLocale)%> (<U>B</U>)</BUTTON>
</div>
<br>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
<%cust_local.remove();%>
