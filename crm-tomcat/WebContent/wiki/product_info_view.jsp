<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.intrust.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0)); 
Integer product_id = null;

String preproduct_name = Utility.trimNull(request.getParameter("preproduct_name"));
String period = Utility.trimNull(request.getParameter("period"));         
Integer expre_start_date = Utility.parseInt(request.getParameter("expre_start_date"),new Integer(0));
BigDecimal pre_money = Utility.parseDecimal(request.getParameter("pre_money"),new BigDecimal(0));
String profit_date = Utility.trimNull(request.getParameter("profit_date"));    
String pre_rate = Utility.trimNull(request.getParameter("pre_rate"));        
String cash_usetype = Utility.trimNull(request.getParameter("cash_usetype"));   
Integer pre_start_date  = Utility.parseInt(request.getParameter("pre_start_date"),new Integer(0));
Integer pre_end_date = Utility.parseInt(request.getParameter("pre_end_date"),new Integer(0));   
String class_type1 = Utility.trimNull(request.getParameter("class_type1"));    
String class_type1_name = Utility.trimNull(request.getParameter("class_type1_name"));
String ensure_method = Utility.trimNull(request.getParameter("ensure_method"));   
String admin_manager3 = Utility.trimNull(request.getParameter("admin_manager3"));   
Integer direct_sale = Utility.parseInt(request.getParameter("direct_sale"),new Integer(0));      
String announce_url = Utility.trimNull(request.getParameter("announce_url"));    
String summary = Utility.trimNull(request.getParameter("summary"));         
String kfq = Utility.trimNull(request.getParameter("kfq"));            
String shsqtjq = Utility.trimNull(request.getParameter("shsqtjq"));          
String syzh = Utility.trimNull(request.getParameter("syzh"));            
String dxjg = Utility.trimNull(request.getParameter("dxjg"));             
String tzgw = Utility.trimNull(request.getParameter("tzgw"));            
String jjjl = Utility.trimNull(request.getParameter("jjjl"));                    
String kfr = Utility.trimNull(request.getParameter("kfr"));            
String xszt = Utility.trimNull(request.getParameter("xszt"));
String pre_status_name = "";
Integer product_status1 = Utility.parseInt(request.getParameter("status"),null);
Integer open_flag = Utility.parseInt(request.getParameter("open"),null);
String class1 = Utility.trimNull(request.getParameter("class1"));
    


ProductInfoReposLocal preProduct = EJBFactory.getProductInfoRepos();
ProductVO preVO = new ProductVO();
List listAll = null;
Map map = null;



if(preproduct_id.intValue() != 0 || product_id.intValue() != 0){
	preVO.setPreproduct_id(preproduct_id);
	preVO.setProduct_id(product_id);
	preVO.setInput_man(input_operatorCode);
	listAll = preProduct.listBySql(preVO);
	if(listAll.size() > 0){
		map = (Map)listAll.get(0); 
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0));
		expre_start_date = Utility.parseInt(Utility.trimNull(map.get("EXPRE_START_DATE")),new Integer(0));
		preproduct_name = Utility.trimNull(map.get("PRODUCT_NAME"));     
		period = Utility.trimNull(map.get("PERIOD"));            
		pre_start_date  = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
		pre_end_date = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0)); 
		direct_sale = Utility.parseInt(Utility.trimNull(map.get("DIRECT_SALE")),new Integer(0));
		profit_date = Utility.trimNull(map.get("PROFIT_DATE"));    
		pre_rate = Utility.trimNull(map.get("PRE_RATE"));        
		cash_usetype = Utility.trimNull(map.get("CASH_USETYPE"));   
		class_type1 = Utility.trimNull(map.get("CLASS_TYPE1"));    
		ensure_method = Utility.trimNull(map.get("ENSURE_METHOD"));   
		admin_manager3 = Utility.trimNull(map.get("ADMIN_MANAGER"));   
		announce_url = Utility.trimNull(map.get("ANNOUNCE_URL"));    
		summary = Utility.trimNull(map.get("SUMMARY"));         
		kfq = Utility.trimNull(map.get("KFQ"));            
		shsqtjq = Utility.trimNull(map.get("SHSQTJQ"));          
		syzh = Utility.trimNull(map.get("SYZH"));            
		dxjg = Utility.trimNull(map.get("DXJG"));             
		tzgw = Utility.trimNull(map.get("TZGW"));            
		jjjl = Utility.trimNull(map.get("JJJL"));                    
		kfr = Utility.trimNull(map.get("KFR"));            
		xszt = Utility.trimNull(map.get("XSZT"));
		pre_status_name = Utility.trimNull(map.get("STATUS_NAME"));
		class_type1_name = Utility.trimNull(map.get("CLASS_TYPE1_NAME"));
		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	}
}
//附件相关变量
AttachmentToCrmLocal attachmentLocal=EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO=new AttachmentVO();
attachmentVO.setDf_serial_no(preproduct_id);
attachmentVO.setDf_talbe_id(new Integer(1213));
attachmentVO.setInput_man(input_operatorCode);
List attachmentList=null;
Map attachmentMap=null;
String attachmentId="";
String origin_name="";
String save_name="";
attachmentList=attachmentLocal.load(attachmentVO);

%>
<html>
<head>
<title>产品信息预览-<%=Utility.trimNull(preproduct_name) %></title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
</head>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>

<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform"  method="post" action="#" onsubmit="javascript:return validateForm(this);">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="6"><br><br></td>
	</tr>
	<tr>
		<td colspan="6" class="direct-title" align="center">
		<font size="4"><b><%=Utility.trimNull(map.get("PRODUCT_NAME")) %></b></font>
		<span class="title-mark"><%=Utility.trimNull(map.get("STATUS_NAME")) %></span>
		</td>
	</tr>
	<tr height="30">
		<td align="left" colspan="2" width="25%">&nbsp;&nbsp;&nbsp;预约开始日：<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("EXPRE_START_DATE")),new Integer(0)))%></td>
		<td align="center" colspan="2" width="50%">推介起止日期：<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0)))%>&nbsp;-&nbsp;<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0)))%></td>
		<td align="left" colspan="2" width="*">&nbsp;&nbsp;</td>
	</tr>
</table>
	<p class="title-table"><b>预发行产品信息</b></p>
	<table width="97%" class="product-list" cellspacing="0" align="center" cellpadding="2" border="1">
		<tr height="25">
			<td align="right" width="10%">产品分类</td>
			<td width="20%">&nbsp;<%=class_type1_name%></td>
			<td align="right" width="10%">预募集规模</td>
			<td width="20%">&nbsp;<%=Format.formatMoney(pre_money.divide(new BigDecimal(10000),3, BigDecimal.ROUND_DOWN).doubleValue())%>&nbsp;(万元)</td>
			<td align="right" width="15%">账户信息</td>
			<td width="25%">&nbsp;<%=syzh%></td>
		</tr>
		<tr height="25">
			<td align="right">销售状态</td>
			<td>&nbsp;<%=pre_status_name%></td>
			<td align="right">销售方式</td>
			<td>&nbsp;<%if(direct_sale.intValue() ==1 ) out.print("代销"); if(direct_sale.intValue() == 2) out.print("直销");if(direct_sale.intValue() == 3) out.print("直销&代销");%></td>
			<td align="right">赎回申请提交期</td>
			<td>&nbsp;<%=shsqtjq%></td>
		</tr>
		<tr height="25">
			<td align="right">期限</td>
			<td>&nbsp;<%=period%></td>
			<td align="right">产品成立日</td>
			<td>&nbsp;<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>
			<td align="right">开放日</td>
			<td>&nbsp;<%=kfr%></td>
		</tr>
		<tr height="25">
			<td align="right">代销机构</td>
			<td>&nbsp;<%=dxjg%></td>
			<td align="right">基金经理</td>
			<td>&nbsp;<%=jjjl%></td>
			<td align="right">投资顾问/受益人代表</td>
			<td>&nbsp;<%=tzgw%></td>
		</tr>
		<tr height="25">
			<td align="right">开放期</td>
			<td>&nbsp;<%=kfq%></td>
			<td align="right">项目责任人</td>
			<td colspan="3">&nbsp;<%=admin_manager3%></td>
		</tr>
		<tr height="25">
			<td align="right">收益分配时间</td>
			<td colspan="5">&nbsp;<%=profit_date%></td>
		</tr>
		<tr height="25">
			<td align="right">资金运用方式</td>
			<td colspan="5">&nbsp;<%=cash_usetype%></td>
		</tr>
		<tr height="25">
			<td align="right">保障措施</td>
			<td colspan="5">&nbsp;<%=ensure_method%></td>
		</tr>
		<tr height="25">
			<td align="right">预期收益率</td>
			<td colspan="5">&nbsp;<%=pre_rate%></td>
		</tr>
		<tr height="25">
			<td align="right">简介</td>
			<td colspan="5">&nbsp;<%=summary%></td>
		</tr>
		<tr height="25">
			<td align="right">信息披露地址</td>
			<td colspan="5">&nbsp;<a href="<%=announce_url%>" target="_blank"><%=announce_url%></a></td>
		</tr>
	<tr>
		<td  align="right">附件</td>
		<td  align="left" colspan="5">
		<script>
			/*查看附件方法*/
			function DownloadAttachment(attachmentId){
				location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
			}
		</script>
<%
	for(int i=0;i<attachmentList.size();i++){
		attachmentMap=(Map)attachmentList.get(i);
		attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
		origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
	   	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
%>			
			<a href="<%=request.getContextPath()%>/tool/download1.jsp?attachmentId=<%=attachmentId%>"><%= origin_name%></a>
			
<%	
	if(i< attachmentList.size()-1){%>
			<br>
<%}	}
 %>		&nbsp;
		</td>
	</tr>

	</table>
</table>

<%
List productList = null;
Map productMap = null;
if(product_id.intValue() != 0){ 
	ProductLocal product = EJBFactory.getProduct();
	ProductVO productVO = new ProductVO();
	productVO.setProduct_id(product_id);
	productList = product.load(productVO);
	if(productList.size() >0 ){
		productMap = (Map)productList.get(0);
%>
<p class="title-table"><b>业务产品信息</b></p>

<table class="product-list" border="1" width="97%" cellspacing="0" align="center" cellpadding="0">
	<tr height="25">
			<td colspan="4" align="center">&nbsp;<font size="3"><b><%=Utility.trimNull(productMap.get("PRODUCT_NAME"))%></b></font></td>
		</tr>
		<tr height="25">
			<td align="right" width="10%">推介期</td>
			<td colspan="3">&nbsp;<%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(productMap.get("PRE_START_DATE")),new Integer(0)))%>&nbsp;-&nbsp;<%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(productMap.get("PRE_END_DATE")),new Integer(0)))%></td>
		</tr>
		<tr height="25">
			<td align="right">设立方式</td>
			<td width="40%">&nbsp;<%if(Utility.trimNull(productMap.get("INTRUST_FLAG3"))!=null){out.print((Utility.trimNull(productMap.get("INTRUST_FLAG3")).equals(new Integer(1))?"私募":"公募"));}%></td>
			<td align="right" width="10%">信托目的</td>
			<td>&nbsp;<%if(Utility.trimNull(productMap.get("INTRUST_FLAG4"))!=null){out.print((Utility.trimNull(productMap.get("INTRUST_FLAG4")).equals(new Integer(1))?"私益":"公益"));}%></td>
		</tr>
		<tr height="25">
			<td align="right">币种</td>
			<td>&nbsp;<%=Argument.getCurrencyName1(Utility.trimNull(productMap.get("CURRENCY_ID")))%></td>
			<td align="right">简称</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRODUCT_JC"))%></td>
		</tr>
		<tr height="25">
			<td align="right">预期发行份数</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRE_NUM"))%></td>
			<td align="right">预期发行金额</td>
			<td>&nbsp;<%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MONEY")),new BigDecimal(0))))%></td>
		</tr>
		<tr height="25">
			<td align="right">预约最大比例</td>
			<td>&nbsp;<%=Utility.trimZero((Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MAX_RATE")),new BigDecimal(0))).multiply(new BigDecimal(100)))%>%</td>
			<td align="right">最低发行金额</td>
			<td>&nbsp;<%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(productMap.get("MIN_MONEY")),new BigDecimal(0))))%></td>
		</tr>
		<tr height="25">
			<td align="right">合同编号前缀</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRE_CODE"))%></td>
			<td align="right">状态</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRODUCT_STATUS_NAME"))%></td>
		</tr>
		<tr height="25">
			<td align="right">执行经理</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("ADMIN_MANAGER"))%></td>
			<td align="right">产品期限</td>
			<td>&nbsp;<%if(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0))!=null)
					        {if(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0)).intValue()!=0){%>
					    <%=Utility.trimNull(Utility.parseInt(Utility.trimNull(productMap.get("VALID_PERIOD")),new Integer(0)))%>
						<%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0)))%>
						<%}else{%><%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0)))%><%}}%></td>
		</tr>
</table>
<%	}
}%>
<p class="title-table"><b>相关产品</b></p>
<table class="product-list" border="0" width="97%" cellspacing="0" align="center" cellpadding="0">
<% 
	List relatList = null;
	Map relatMap = null;
	ProductVO relatedVO = new ProductVO();
	relatedVO.setPreproduct_id(preproduct_id);
	relatedVO.setProduct_id(product_id);
	relatedVO.setInput_man(input_operatorCode);
	preProduct.relatedList(relatedVO);
	relatList = preProduct.relatedList(relatedVO);
	for(int i=0; i< relatList.size(); i++){
		relatMap = (Map)relatList.get(i);
 %>
	<tr height="25">
		<td align="left">&nbsp;&nbsp;&nbsp;<a href="product_info.jsp?product_id=<%=Utility.parseInt(Utility.trimNull(relatMap.get("PRODUCT_ID")),new Integer(0))%>&preproduct_id=<%=Utility.parseInt(Utility.trimNull(relatMap.get("PREPRODUCT_ID")),new Integer(0))%>" onclick="" id="Support"  title="<%=Utility.trimNull(relatMap.get("PRODUCT_NAME"))%>"><%=Utility.trimNull(relatMap.get("PRODUCT_NAME"))%></a></td>
	</tr>	
<%}%>
</table>
<table border="0" width="100%">
	<tr>
		<td align="right">
		<button type="button"  class="xpbutton3" accessKey=c id="btnBack" name="btnBack" onclick="javascript:window.close();">关闭(<u>C</u>)</button>
	</tr>
</table>
</form>
</body>
</html>

<%
preProduct.remove();
%>