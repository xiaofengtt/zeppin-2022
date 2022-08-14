<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.intrust.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,java.math.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0)); 
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));

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
String pre_status_name = "";
Integer start_date = new Integer(0);

Integer product_status1 = Utility.parseInt(request.getParameter("status"),null);
Integer open_flag = Utility.parseInt(request.getParameter("open"),null);
String class1 = Utility.trimNull(request.getParameter("class1"));
    
ProductInfoReposLocal preProduct = EJBFactory.getProductInfoRepos();
ProductVO preVO = new ProductVO();
boolean bSuccess = false;	

Map map = new HashMap();

//Ԥ���в�Ʒ��Ϣ
if(preproduct_id.intValue() != 0 || product_id.intValue() != 0){
	preVO.setPreproduct_id(preproduct_id);
	preVO.setProduct_id(product_id);
	preVO.setInput_man(input_operatorCode);
	List listAll = preProduct.listBySql(preVO);
	if(listAll.size()>0){
		map = (Map)listAll.get(0); 
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0));
		expre_start_date = Utility.parseInt(Utility.trimNull(map.get("EXPRE_START_DATE")),new Integer(0));
		preproduct_name = Utility.trimNull(map.get("PRODUCT_NAME"));     
		period = Utility.trimNull(map.get("PERIOD"));            
		pre_start_date  = Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0));
		pre_end_date = Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0)); 
		direct_sale = Utility.parseInt(Utility.trimNull(map.get("DIRECT_SALE")),new Integer(0));
		start_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
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
		pre_status_name = Utility.trimNull(map.get("STATUS_NAME"));
		class_type1_name = Utility.trimNull(map.get("CLASS_TYPE1_NAME"));
		product_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	}
}

//������ر���
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

String qs = Utility.getQueryString(request, new String[]{"page", "pagesize", "status", "class1", "open"});
String returl = "product_list.jsp?"+qs;
%>
<html>
<head>
<title>��ƷҪ��ά��</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
<script language=javascript>
<%if (bSuccess){%>
	window.returnValue = 1;
	window.close();
<%}%>

function showProduct(){
	var ret = showModalDialog('product_bind.jsp?preproduct_id=<%=preproduct_id%>&product_name=<%=preproduct_name%>&task=task' ,'','dialogWidth:650px;dialogHeight:400px;status:0;help:0');
	if (ret) {	
		sl_alert("�󶨳ɹ�");
		location = 'product_info.jsp?product_id='+ret[0]+'&preproduct_id='+ret[1];
	}
}

function remove() {
	if (! sl_confirm("ɾ����Ԥ���в�Ʒ")) return;
	location.href = "product_remove.jsp?preproduct_id=<%=preproduct_id%>&status=<%=product_status1%>&open=<%=open_flag%>&class1=<%=class1%>";
}

/*�鿴��������*/
function DownloadAttachment(attachmentId) {
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}

function showProductIntroMaterial(showFlag) { 
	showModalDialog('product_intro_material_dialog.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&showFlag='+ showFlag
						,'','dialogWidth:700px;dialogHeight:700px;status:0;help:0');
}

function playProductIntoStudyVoice() { 
	showModalDialog('product_intro_study_voice_play.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>'
						,'','dialogWidth:550px;dialogHeight:200px;status:0;help:0');
}
</script>
</head>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform"  method="post" action="#" onsubmit="javascript:return validateForm(this);">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="6"><br><br></td>
	</tr>
	<tr>
		<td colspan="6" class="direct-title" align="center"><font size="4"><b><%=Utility.trimNull(map.get("PRODUCT_NAME")) %></b></font>
		<span class="title-mark"><%=Utility.trimNull(map.get("STATUS_NAME")) %>
		<%if (preproduct_id.intValue() != 0) { 
			if (input_operator.hasFunc(menu_id, 100)) {
				if(product_id.intValue() == 0) {
		%>
					<a href="#" onclick="showProduct()">��</a>&nbsp;&nbsp;
					<a href="#" onclick="remove()">ɾ��</a>
		<% 		
				} else if (product_id.intValue() != 0) {
		%>
					<a href="#" onclick="showProduct()">���İ�</a>&nbsp;&nbsp;
		<%		} 
			}
		}
		
		if(input_operator.hasFunc(menu_id, 100)){%>
					&nbsp;&nbsp;<a href="product_new.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&status=<%=product_status1%>&open=<%=open_flag%>&class1=<%=class1%>">�༭</a>
		<%}
		if(input_operator.hasFunc(menu_id, 110)){%>
					&nbsp;&nbsp;<a href="product_intro_material_edit.jsp?preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&status=<%=product_status1%>&open=<%=open_flag%>&class1=<%=class1%>&page=<%=sPage%>&pagesize=<%=sPagesize%>">¼���Ʒ�ƽ�����</a>
		<%} %>
		
		
		</span></td>
	</tr>
	<tr height="30">
		<td align="left" colspan="2" width="25%">
			&nbsp;&nbsp;&nbsp;ԤԼ��ʼ�գ�<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("EXPRE_START_DATE")),new Integer(0)))%>			
		</td>
		<td align="left" colspan="2" width="25%">
			&nbsp;&nbsp;&nbsp;ԤԼ��Ч������<%=Utility.parseInt((Integer)map.get("PRE_VALID_DAYS"),new Integer(0))%>�� (0��ʾ������)
		</td>
		<td align="center" colspan="2" width="50%">�ƽ���ֹ���ڣ�<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("PRE_START_DATE")),new Integer(0)))%>&nbsp;-&nbsp;<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("PRE_END_DATE")),new Integer(0)))%></td>
	</tr>
</table>
	<p class="title-table"><b>Ԥ���в�Ʒ��Ϣ</b></p>
	<table width="97%" class="product-list" cellspacing="0" align="center" cellpadding="2" border="1">
		<tr height="25">
			<td align="right" width="10%">��Ʒ����</td>
			<td width="20%">&nbsp;<%=class_type1_name%></td>
			<td align="right" width="10%">Ԥļ����ģ</td>
			<td width="20%">&nbsp;<%=Format.formatMoney(pre_money.divide(new BigDecimal(10000),3, BigDecimal.ROUND_DOWN).doubleValue())%>&nbsp;(��Ԫ)</td>
			<td align="right" width="15%">�˻���Ϣ</td>
			<td width="25%">&nbsp;<%=syzh%></td>
		</tr>
		<tr height="25">
			<td align="right">����״̬</td>
			<td>&nbsp;<%=pre_status_name%></td>
			<td align="right">���۷�ʽ</td>
			<td>&nbsp;<%if(direct_sale.intValue() ==1 ) out.print("����"); if(direct_sale.intValue() == 2) out.print("ֱ��");if(direct_sale.intValue() == 3) out.print("ֱ��&����");%></td>
			<td align="right">��������ύ��</td>
			<td>&nbsp;<%=shsqtjq%></td>
		</tr>
		<tr height="25">
			<td align="right">����</td>
			<td>&nbsp;<%=period%></td>
			<td align="right">��Ʒ������</td>
			<td>&nbsp;<%=Format.formatDateLine(Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0)))%></td>
			<td align="right">������</td>
			<td>&nbsp;<%=kfr%></td>
		</tr>
		<tr height="25">
			<td align="right">��������</td>
			<td>&nbsp;<%=dxjg%></td>
			<td align="right">������</td>
			<td>&nbsp;<%=jjjl%></td>
			<td align="right">Ͷ�ʹ���/�����˴���</td>
			<td>&nbsp;<%=tzgw%></td>
		</tr>
		<tr height="25">
			<td align="right">������</td>
			<td>&nbsp;<%=kfq%></td>
			<td align="right">��Ŀ������</td>
			<td colspan="3">&nbsp;<%=admin_manager3%></td>
		</tr>
		<tr height="25">
			<td align="right">�������ʱ��</td>
			<td colspan="5">&nbsp;<%=profit_date%></td>
		</tr>
		<tr height="25">
			<td align="right">�ʽ����÷�ʽ</td>
			<td colspan="5">&nbsp;<%=cash_usetype%></td>
		</tr>
		<tr height="25">
			<td align="right">���ϴ�ʩ</td>
			<td colspan="5">&nbsp;<%=ensure_method%></td>
		</tr>
		<tr height="25">
			<td align="right">Ԥ��������</td>
			<td colspan="5">&nbsp;<%=pre_rate%></td>
		</tr>
		<tr height="25">
			<td align="right">���</td>
			<td colspan="5">&nbsp;<%=summary%></td>
		</tr>
		<tr height="25">
			<td align="right">��Ϣ��¶��ַ</td>
			<td colspan="5">&nbsp;<a href="<%=announce_url%>" target="_blank"><%=announce_url%></a></td>
		</tr>
	<tr>
		<td  align="right">����</td>
		<td  align="left" colspan="5">
<%
for (int i=0; i<attachmentList.size(); i++) {
		attachmentMap=(Map)attachmentList.get(i);
		attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
		origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
	   	save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME"));
%>			
			<a href="#" onclick="javascript:DownloadAttachment(<%=attachmentId%>)"><%= origin_name%></a>			
<%	
	if(i< attachmentList.size()-1){%>
			<br/>
<%	}	
}%>		&nbsp;
		</td>
	</tr>
	</table>

<%
Map i_map = new HashMap();
if(preproduct_id.intValue()>0 || product_id.intValue()>0){
	ProductInfoReposLocal prod_info = EJBFactory.getProductInfoRepos();
	ProductVO vo = new ProductVO();
	vo.setProduct_id(product_id);
	vo.setPreproduct_id(preproduct_id);
	vo.setInput_man(input_operatorCode);

	List list = prod_info.queryIntroMaterial(vo);
	if (list.size()>0) 
		i_map = (Map)list.get(0);

	String s = Utility.trimNull(i_map.get("FEASSTUDY"));
	if (! s.equals(""))
		i_map.put("FEASSTUDY_ORIGIN_NAME", s.substring(s.lastIndexOf("//")+2, s.lastIndexOf('~')));

	s = Utility.trimNull(i_map.get("FEASSTUDY_EASY"));
	if (! s.equals(""))
		i_map.put("FEASSTUDY_EASY_ORIGIN_NAME", s.substring(s.lastIndexOf("//")+2, s.lastIndexOf('~')));

	prod_info.remove();
}
%>
	<table class="product-list" width="97%" cellspacing="0" align="center" cellpadding="2">
		<tr>
			<td width="10%" align="right">��Ʒ˵���飺</td>
			<td align="left" width="23%">
				<a title="�鿴��Ʒ˵����" href="javascript:showProductIntroMaterial(1)">�鿴</a>
			</td>
			<td width="10%" align="right">�ƽ�֪ͨ����</td>
			<td align="left" width="23%">
				<a title="�鿴�ƽ�֪ͨ��" href="javascript:showProductIntroMaterial(2)">�鿴</a>
			</td>
			<td width="10%" align="right">��ǰ��ѵ��¼����</td>
			<td align="left" width="*">
		<% if (! Utility.trimNull(i_map.get("STUDY_VOICE")).equals("")) { %>			
				<a title="������ǰ��ѵ��¼��" href="javascript:playProductIntoStudyVoice()">����</a>
		<% } else { %>
				<span style="width:150px">��</span>				
		<% }%>
			</td>
		</tr>		
		<tr>
			<td width="10%" align="right">���б��棺</td>
			<td align="left" width="*" colspan="5">
			<% if (! Utility.trimNull(i_map.get("FEASSTUDY")).equals("")) { %>
				<a title="�鿴����" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(i_map.get("FEASSTUDY")),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("FEASSTUDY_ORIGIN_NAME"))%>"><%=Utility.trimNull(i_map.get("FEASSTUDY_ORIGIN_NAME"))%></a>
			<%  } else { %>
				<span style="width:150px">��</span>				
			<% }%>
				&nbsp;&nbsp;&nbsp;�ͻ��棺
			<% if (! Utility.trimNull(i_map.get("FEASSTUDY_EASY")).equals("")) { %>				
				<a title="�鿴����" href="<%=request.getContextPath()%>/system/basedata/downloadattach.jsp?file_name=<%=Utility.replaceAll(Utility.trimNull(i_map.get("FEASSTUDY_EASY")),"\\","/")%>&name=<%=Utility.trimNull(i_map.get("FEASSTUDY_EASY_ORIGIN_NAME"))%>"><%=Utility.trimNull(i_map.get("FEASSTUDY_EASY_ORIGIN_NAME"))%></a>
			<% } else { %>
				<span style="width:150px">��</span>				
			<% }%>
			</td>
		</tr>
	<%if (preproduct_id.intValue()>0) { %>
		<tr>
			<td width="10%" align="right">���漶��</td>
			<td align="left" width="*" colspan="5">
				<a title="���漶������" href="preproduct_gainlevel_list.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>">����</a>
			</td>
		</tr>
	<%} %>
	</table>
</table>

<table border="0" align="center" width="97%" cellspacing="0" cellpadding="0">
	<tr>
		<td><br><br></td>
	</tr>
</table>
<%
if (product_id!= null && product_id.intValue() != 0){ 
	ProductLocal product = EJBFactory.getProduct();
	ProductVO productVO = new ProductVO();
	productVO.setProduct_id(product_id);
	List productList = product.load(productVO);
	if (productList.size() > 0) {
		Map productMap = (Map)productList.get(0);
%>
<p class="title-table"><b>ҵ���Ʒ��Ϣ</b></p>
<table class="product-list" border="1" width="97%" cellspacing="0" align="center" cellpadding="0">
	<tr height="25">
			<td colspan="4" align="center">&nbsp;<font size="3"><b><%=Utility.trimNull(productMap.get("PRODUCT_NAME"))%></b></font></td>
		</tr>
		<tr height="25">
			<td align="right" width="10%">�ƽ���</td>
			<td colspan="3">&nbsp;<%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(productMap.get("PRE_START_DATE")),new Integer(0)))%>&nbsp;-&nbsp;<%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(productMap.get("PRE_END_DATE")),new Integer(0)))%></td>
		</tr>
		<tr height="25">
			<td align="right">������ʽ</td>
			<td width="40%">&nbsp;<%if(Utility.trimNull(productMap.get("INTRUST_FLAG3"))!=null){out.print((Utility.trimNull(productMap.get("INTRUST_FLAG3")).equals("1")?"˽ļ":"��ļ"));}%></td>
			<td align="right" width="10%">����Ŀ��</td>
			<td>&nbsp;<%if(Utility.trimNull(productMap.get("INTRUST_FLAG4"))!=null){out.print((Utility.trimNull(productMap.get("INTRUST_FLAG4")).equals("1")?"˽��":"����"));}%></td>
		</tr>
		<tr height="25">
			<td align="right">����</td>
			<td>&nbsp;<%=Argument.getCurrencyName1(Utility.trimNull(productMap.get("CURRENCY_ID")))%></td>
			<td align="right">���</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRODUCT_JC"))%></td>
		</tr>
		<tr height="25">
			<td align="right">Ԥ�ڷ��з���</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRE_NUM"))%></td>
			<td align="right">Ԥ�ڷ��н��</td>
			<td>&nbsp;<%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MONEY")),new BigDecimal(0))))%></td>
		</tr>
		<tr height="25">
			<td align="right">ԤԼ������</td>
			<td>&nbsp;<%=Utility.trimZero((Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MAX_RATE")),new BigDecimal(0))).multiply(new BigDecimal(100)))%>%</td>
			<td align="right">��ͷ��н��</td>
			<td>&nbsp;<%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(productMap.get("MIN_MONEY")),new BigDecimal(0))))%></td>
		</tr>
		<tr height="25">
			<td align="right">��ͬ���ǰ׺</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRE_CODE"))%></td>
			<td align="right">״̬</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("PRODUCT_STATUS_NAME"))%></td>
		</tr>
		<tr height="25">
			<td align="right">ִ�о���</td>
			<td>&nbsp;<%=Utility.trimNull(productMap.get("ADMIN_MANAGER"))%></td>
			<td align="right">��Ʒ����</td>
			<td>&nbsp;<%if(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0))!=null)
					        {if(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0)).intValue()!=0){%>
					    <%=Utility.trimNull(Utility.parseInt(Utility.trimNull(productMap.get("VALID_PERIOD")),new Integer(0)))%>
						<%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0)))%>
						<%}else{%><%=Argument.getProductUnitName(Utility.parseInt(Utility.trimNull(productMap.get("PERIOD_UNIT")),new Integer(0)))%><%}}%></td>
		</tr>
</table>
<%	}
}%>
<p class="title-table"><b>��ز�Ʒ</b></p>
<table class="product-list" border="0" width="97%" cellspacing="0" cellpadding="0">
<% 
	ProductVO relatedVO = new ProductVO();
	relatedVO.setPreproduct_id(preproduct_id);
	relatedVO.setProduct_id(product_id);
	relatedVO.setInput_man(input_operatorCode);
	preProduct.relatedList(relatedVO);
	List relatList = preProduct.relatedList(relatedVO);
	for(int i=0; i< relatList.size(); i++){
		Map relatMap = (Map)relatList.get(i);
 %>
	<tr height="25">
		<td align="left">&nbsp;&nbsp;&nbsp;<a href="product_info.jsp?product_id=<%=Utility.parseInt(Utility.trimNull(relatMap.get("PRODUCT_ID")),new Integer(0))%>&preproduct_id=<%=Utility.parseInt(Utility.trimNull(relatMap.get("PREPRODUCT_ID")),new Integer(0))%>" onclick="" id="Support"  title="<%=Utility.trimNull(relatMap.get("PRODUCT_NAME"))%>"><%=Utility.trimNull(relatMap.get("PRODUCT_NAME"))%></a></td>
	</tr>	
<%}%>
</table>
<table border="0" width="100%">
	<tr>
		<td><br></td>
	</tr>
	<tr>
		<td align="right">
		<button type="button"   class="xpbutton3" accessKey=c id="btnBack" name="btnBack" onclick="javascript:location.href='<%=returl%>';">���� (<u>B</u>)</button>
	</tr>
</table>
</form>
</body>
</html>
<%
preProduct.remove();
%>