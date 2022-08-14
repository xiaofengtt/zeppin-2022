<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),null);


ProductInfoReposLocal product = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();
//附件相关
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO(); 
AttachmentVO fileVO=new AttachmentVO();

attachmentVO.setDf_serial_no(preproduct_id);
attachmentVO.setDf_talbe_id(new Integer(1213));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);
//删除文件
for(int i=0;i<attachmentList.size();i++){
		attachmentMap=(Map)attachmentList.get(i);
		save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
		fileVO.setSave_name(save_name);
		attachmentLocal.deleteFile(fileVO);	
}


//状态参数
Integer product_status1 = Utility.parseInt(request.getParameter("status"),null);
Integer open_flag = Utility.parseInt(request.getParameter("open"),null);
String class1 = Utility.trimNull(request.getParameter("class1"));

if (preproduct_id.intValue() != 0)
{
	vo.setPreproduct_id(preproduct_id);
	vo.setInput_man(input_operatorCode);
	product.delete(vo);
}
%>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript">
	window.returnValue = 1;
	location =  "product_list.jsp?status=<%=product_status1%>&open=<%=open_flag%>&class1=<%=class1%>";
</SCRIPT>
<%product.remove();%>