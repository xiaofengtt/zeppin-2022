<%@ page contentType="text/html; charset=GBK" import="sun.misc.BASE64Decoder,enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%@ page import="com.jspsmart.upload.SmartUpload"%>
<%;
int post_flag = 0; 
// 图片保存辅助变量
byte [] bx = new byte[0];
java.io.InputStream ins1 = new java.io.ByteArrayInputStream(bx);
java.io.InputStream ins2 = new java.io.ByteArrayInputStream(bx);
SmartUpload su = new SmartUpload();
if (true){	
	// 上传初始化 
	su.initialize(pageContext);
	su.setTotalMaxFileSize(50000000);
	su.setAllowedFilesList("jpg,gif,txt"); //允许的格式
	su.upload();

	sun.misc.BASE64Decoder base64 = new sun.misc.BASE64Decoder();
	//证件正面图片
	String image1 =  su.getRequest().getParameter("image1");  
	if(image1 != null && !"".equals(image1)) {
		byte[] bb1 = base64.decodeBuffer(image1);
		ins1 = new java.io.ByteArrayInputStream(bb1);
		session.setAttribute("inputstream",ins1);
	}
	
	//证件反面图片
	String image2 =  su.getRequest().getParameter("image2");
	if(image2 != null && !"".equals(image2)) {
		byte[] bb2 = base64.decodeBuffer(image2);
		ins2 = new java.io.ByteArrayInputStream(bb2);
		session.setAttribute("inputstream2",ins2);
	}
   
	//证件扫描和阅读传递变量
	String card_id = Utility.trimNull(su.getRequest().getParameter("card_id"));
	String sex = Utility.trimNull(su.getRequest().getParameter("sex"));
	String birth = Utility.trimNull(su.getRequest().getParameter("birth"));
	String address = Utility.trimNull(su.getRequest().getParameter("address"));
	String card_type = Utility.trimNull(su.getRequest().getParameter("card_type"));
	
	String issueDate = Utility.trimNull(su.getRequest().getParameter("issueDate"));
	String issuePlace = Utility.trimNull(su.getRequest().getParameter("issuePlace"));
	String nation = Utility.trimNull(su.getRequest().getParameter("nation"));
	
	String valid_date = Utility.trimNull(su.getRequest().getParameter("valid_date"));
	//赋值
	session.setAttribute("card_id",card_id);
	session.setAttribute("sex",sex );
	session.setAttribute("birth",birth);
	session.setAttribute("address",address);
	session.setAttribute("name",Utility.trimNull(su.getRequest().getParameter("name")));
	session.setAttribute("card_type",card_type);
	session.setAttribute("issueDate",issueDate);
	session.setAttribute("issuePlace",issuePlace);
	session.setAttribute("nation",nation);
	session.setAttribute("valid_date",valid_date);

	post_flag=Utility.parseInt(su.getRequest().getParameter("post_flag"), 0);
}

%>
<HTML>
<HEAD>
<TITLE>现场签约</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>

<SCRIPT LANGUAGE="javascript">
//删除文件
    var fso = new ActiveXObject("Scripting.FileSystemObject"); // 创建对象 
	if( fso.FileExists("D:\\CARD\\HeadImage.jpg") ){
        fso.DeleteFile("D:\\CARD\\HeadImage.jpg",true);
    }
    if( fso.FileExists("D:\\CARD\\Result.txt") ){
        fso.DeleteFile("D:\\CARD\\Result.txt",true);
    }
    if( fso.FileExists("D:\\CARD\\BackValue.txt") ){
        fso.DeleteFile("D:\\CARD\\BackValue.txt",true);
    }
    if( fso.FileExists("D:\\CARD\\BackValue.txt") ){
        fso.DeleteFile("D:\\CARD\\BackValue.txt",true);
    }
    if( fso.FileExists("D:\\CARD\\CardImage.jpg") ){
        fso.DeleteFile("D:\\CARD\\CardImage.jpg",true);
    }
    if( fso.FileExists("D:\\CARD\\ScanSource.jpg") ){
        fso.DeleteFile("D:\\CARD\\ScanSource.jpg",true);
    }
	if( fso.FileExists("D:\\CARD\\HeadImage2.jpg") ){
        fso.DeleteFile("D:\\CARD\\HeadImage2.jpg",true);
    }
    if( fso.FileExists("D:\\CARD\\Result2.txt") ){
        fso.DeleteFile("D:\\CARD\\Result2.txt",true);
    }
    if( fso.FileExists("D:\\CARD\\BackValue2.txt") ){
        fso.DeleteFile("D:\\CARD\\BackValue2.txt",true);
    }
    if( fso.FileExists("D:\\CARD\\BackValue2.txt") ){
        fso.DeleteFile("D:\\CARD\\BackValue2.txt",true);
    }
    if( fso.FileExists("D:\\CARD\\CardImage2.jpg") ){
        fso.DeleteFile("D:\\CARD\\CardImage2.jpg",true);
    }
    if( fso.FileExists("D:\\CARD\\ScanSource2.jpg") ){
        fso.DeleteFile("D:\\CARD\\ScanSource2.jpg",true);
    }
//跳转
	//alert('<%=post_flag%>');
	window.parent.returnValue = 1;
	<% if (post_flag==4){//申购%>
		window.parent.location.href('sell/apply_purchase_add.jsp?page=1&sQuery=0$$0$$$8$0$');
	<% }else if (post_flag==2){//认购%>
		window.parent.document.getElementById("closeImg").click();	
		window.parent.getMarketingCustomer('customer','0');
	<%}else{%>
		window.parent.location.href('signed_spot.jsp?query_flag=1');
	<%}%>
</SCRIPT>
