<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.*,enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*,java.math.*" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"),new Integer(0)); 
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
String preproduct_name = "";
String period = "";         
BigDecimal pre_money = new BigDecimal(0);
String profit_date = "";    
String pre_rate = "";        
String cash_usetype = "";   
Integer pre_start_date  = new Integer(0);
Integer pre_end_date = new Integer(0);
String class_type1 = Utility.trimNull(request.getParameter("class1"));    
String ensure_method = "";   
String admin_manager3 = "";   
Integer direct_sale =  new Integer(0);    
String announce_url = "";    
String summary = "";         
String kfq = "";            
String shsqtjq = "";          
String syzh = "";            
String dxjg = "";             
String tzgw = "";            
String jjjl = "";                    
String kfr = "";            
String pre_status = "";
Integer start_date = new Integer(0); //��Ʒ������
Integer pre_valid_days = new Integer(0);
String expre_start_time="", expre_end_time = "";
Integer serial_no = new Integer(0); //����ID

//״̬����
Integer product_status1 = Utility.parseInt(request.getParameter("status"),null);
Integer open_flag = Utility.parseInt(request.getParameter("open"),null);
String class1 = Utility.trimNull(request.getParameter("class1"));

ProductInfoReposLocal preProduct = EJBFactory.getProductInfoRepos();
ProductVO vo = new ProductVO();
String period_unit = null;
boolean bSuccess = false;	

if (preproduct_id.intValue() > 0 ){
	vo.setPreproduct_id(preproduct_id);
	vo.setInput_man(input_operatorCode);
	List listAll = preProduct.listBySql(vo);

	if (listAll.size() > 0) {
		Map map = (Map)listAll.get(0); 
		
		pre_money = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0));		
		preproduct_name = Utility.trimNull(map.get("PRODUCT_NAME"));     
		period = Utility.trimNull(map.get("PERIOD"));            
		start_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
		pre_valid_days = Utility.parseInt((Integer)map.get("PRE_VALID_DAYS"), new Integer(0));
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
		pre_status = Utility.trimNull(map.get("STATUS"));
		expre_start_time = Utility.trimNull(map.get("EXPRE_START_TIME")).substring(0,19);
		expre_end_time = Utility.trimNull(map.get("EXPRE_END_TIME")).substring(0,19);
	}
}

if(product_id.intValue() != 0 && preproduct_id.intValue() ==0){
	ProductVO productVO = new ProductVO();
	productVO.setProduct_id(product_id);
	List productList = preProduct.listBySql(productVO);
	Map productMap = (Map)productList.get(0);
	preproduct_name = Utility.trimNull(productMap.get("PRODUCT_NAME"));
	pre_money = Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MONEY")),new BigDecimal(0));
	pre_start_date  = Utility.parseInt(Utility.trimNull(productMap.get("PRE_START_DATE")),new Integer(0));
	pre_end_date = Utility.parseInt(Utility.trimNull(productMap.get("PRE_END_DATE")),new Integer(0)); 
	direct_sale = Utility.parseInt(Utility.trimNull(productMap.get("DIRECT_SALE")),new Integer(0));
} 

/**���Ӹ����ϴ� requestȡֵ��Ϊfile*****/ 
DocumentFileToCrmDB file=null;
if(request.getMethod().equals("POST")){
		file = new DocumentFileToCrmDB(pageContext);
   		file.parseRequest();
		
		vo.setPreproduct_id(Utility.parseInt(file.getParameter("preproduct_id"),new Integer(0)));
    	vo.setProduct_id(Utility.parseInt(file.getParameter("product_id"),new Integer(0)));
    	vo.setPreproduct_name(Utility.trimNull(file.getParameter("preproduct_name")));
    	vo.setPeriod(Utility.trimNull(file.getParameter("period")));    	
		vo.setStart_date(Utility.parseInt(file.getParameter("start_date"),new Integer(0)));
    	vo.setPre_money(Utility.parseDecimal(file.getParameter("pre_money"),new BigDecimal(0)).multiply(new BigDecimal(10000)));
    	vo.setProfit_date(Utility.trimNull(file.getParameter("profit_date")));
    	vo.setPre_rate(Utility.trimNull(file.getParameter("pre_rate")));
    	vo.setCash_usetype(Utility.trimNull(file.getParameter("cash_usetype")));
    	vo.setEnsure_method(Utility.trimNull(file.getParameter("ensure_method")));
    	vo.setPre_start_date(Utility.parseInt(file.getParameter("pre_start_date"),new Integer(0)));
    	vo.setPre_end_date(Utility.parseInt(file.getParameter("pre_end_date"),new Integer(0)));
    	vo.setClass_type1(Utility.trimNull(file.getParameter("class_type1")));
    	vo.setAdmin_manager3(Utility.trimNull(file.getParameter("admin_manager3")));
    	vo.setDirect_sale(Utility.parseInt(file.getParameter("direct_sale"),new Integer(0)));
    	vo.setAnnounce_url(Utility.trimNull(file.getParameter("announce_url")));
    	vo.setSummary(Utility.trimNull(file.getParameter("summary")));
    	vo.setKfq(Utility.trimNull(file.getParameter("kfq")));
    	vo.setShsqtjq(Utility.trimNull(file.getParameter("shsqtjq")));
    	vo.setSyzh(Utility.trimNull(file.getParameter("syzh")));
    	vo.setDxjg(Utility.trimNull(file.getParameter("dxjg")));
    	vo.setTzgw(Utility.trimNull(file.getParameter("tzgw")));
    	vo.setJjjl(Utility.trimNull(file.getParameter("jjjl")));
    	vo.setKfr(Utility.trimNull(file.getParameter("kfr")));
    	vo.setPre_status(Utility.trimNull(file.getParameter("pre_status")));
		vo.setPre_valid_days(Utility.parseInt(file.getParameter("pre_valid_days"),new Integer(0)));
		vo.setExpre_start_time(Utility.trimNull(file.getParameter("expre_start_time")));
		vo.setExpre_end_time(Utility.trimNull(file.getParameter("expre_end_time")));
    	vo.setInput_man(input_operatorCode);
		serial_no = preProduct.setPreProduct(vo);
		file.uploadAttchment(new Integer(1213),"TPREPRODUCT",serial_no,"",input_operatorCode); // 1213Ԥ���в�Ʒ��TPREPRODUCT 
		
		bSuccess = true;
}

preProduct.remove();
%>
<html>
<head>
<title>��ƷҪ��ά��</title>
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
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendarTime.js"></SCRIPT>
<script language=javascript>
<%if (bSuccess){%>		
	sl_alert("����ɹ�");
	location='product_list.jsp?class1=<%=file.getParameter("class1")%>'+'&status=<%=file.getParameter("status")%>'+'&open=<%=file.getParameter("open")%>';
<%}%>

function validateForm(form){
	if (!sl_check(form.preproduct_name, "���мƻ�����"))	return false;
   	syncDatePicker(document.theform.pre_start_date_picker,document.theform.pre_start_date);
	syncDatePicker(document.theform.pre_end_date_picker,document.theform.pre_end_date);
	syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
	if (form.pre_start_date.value>form.pre_end_date.value) {
	  sl_alert("�ƽ鿪ʼ���ڲ��ܴ����ƽ��������!");
	  return false;		
    }
	if(!sl_checkChoice(form.pre_status, "����״̬"))	return false;
	
	return sl_check_update();
}

var n=1;
function addline() { 
	n++;
	newline=document.all.test.insertRow()
	newline.id="fileRow"+n; 
	newline.insertCell().innerHTML="<input type='file' name=file_info"+n+" size='45'>"+"<input type='button' style='margin-left:5px; width:45px;' onclick='javascript:removeline(this)' value='�Ƴ�'/>";  
} 

function removeline(obj){	
	var objSourceRow=obj.parentNode.parentNode;
	var objTable=obj.parentNode.parentNode.parentNode.parentNode; 
	objTable.lastChild.removeChild(objSourceRow);	
}

/*�鿴��������*/
function DownloadAttachment(attachmentId){
	location.href = "<%=request.getContextPath()%>/tool/download1.jsp?attachmentId="+attachmentId;	
}

//ɾ�����ݿ������и����ķ���
function deleteDbAttachment(attachmentId,save_name){
    if (confirm("<%=LocalUtilis.language("message.confirmDeleteAttachment",clientLocale)%>")) //ȷ��ɾ��������
		location.href="productAttachMent.jsp?action=del&attachmentId="+attachmentId+"&save_name="+save_name
			+"&preproduct_id=<%=preproduct_id%>&product_id=<%=product_id%>&status=<%=product_status1%>&class1=<%=class1%>";	
}

function showCnMoney(value){
	if (trim(value) == "")
		to_money_cn.innerText = "(Ԫ)";
	else
		to_money_cn.innerText = "(" + numToChinese(value*10000) + ")";
}
</script>
<BODY class="BODY" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform"  method="post" action="product_new.jsp" onsubmit="javascript:return validateForm(this);" ENCTYPE="multipart/form-data">
<input type="hidden" name="status" value="<%=product_status1%>">
<input type="hidden" name="open" value="<%=open_flag%>">
<input type="hidden" name="class1" value="<%=class1%>">
<input type="hidden" name="preproduct_id" value="<%=preproduct_id%>">
<input type="hidden" name="product_id" value="<%=product_id%>">
<table border="0" width="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td align="left"><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28"><font color="#215dc6"><b><%=menu_info%>>>���Ӳ�Ʒ</b></font></td>
	</tr>
	<tr>
		<td>
		<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>
<table width="99%" cellspacing="0" cellpadding="2" border="0">
	<tr>
		<td align="right"><font color="red">*</font>���мƻ����ƣ�</td>
		<td colspan="3">
			<input type="text" maxlength="60" size="60" style="width:360"  name="preproduct_name" value="<%=preproduct_name%>">
		</td>
		<td align="right">���ޣ�</td>
		<td>
			<input type="text" style="width:120"  name="period" value="<%=period%>">
		</td>
		<td align="right">Ԥļ����ģ��</td>	
		<td>
			<input type="text" style="width:120"  name="pre_money" value="<%=pre_money.divide(new BigDecimal(10000),3, BigDecimal.ROUND_DOWN).doubleValue()%>" onkeyup="javascript:showCnMoney(this.value)">&nbsp;(��Ԫ)
		</td>
	</tr>
	<tr>
		<td align="right" colspan="8"><span id="to_money_cn" class="span">&nbsp;&nbsp;&nbsp;</span></td>
	</tr>
	<tr>
		<td align="right">��Ʒ�����գ�</td>
		<td>
			<input TYPE="text" style="width:100" NAME="start_date_picker" class=selecttext value="<%=Format.formatDateLine(start_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
	        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
	        <input TYPE="hidden" NAME="start_date" value="">
		</td>		
		<td align="right">�ƽ鿪ʼ���ڣ�</td>	
		<td>
			<input TYPE="text" style="width:100" NAME="pre_start_date_picker" class=selecttext value="<%=Format.formatDateLine(pre_start_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
	        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.pre_start_date_picker,theform.pre_start_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
	        <input TYPE="hidden" NAME="pre_start_date" value="">
		</td>
		<td align="right">�ƽ�������ڣ�</td>
		<td colspan="3">
			<input TYPE="text" style="width:100" NAME="pre_end_date_picker" class=selecttext value="<%=Format.formatDateLine(pre_end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
	        <input TYPE="button" value="��" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.pre_end_date_picker,theform.pre_end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
	        <input TYPE="hidden" NAME="pre_end_date" value="">
		</td>
	</tr>
	<tr>
		<td align="right"><font color="red">*</font>����״̬��</td>	
		<td>
			<select name="pre_status" id="pre_status" onchange = "javascript:" style="width:122">
				<%=Argument.getPre_status(pre_status)%>
			</select>
		</td>
		<td align="right">ֱ��/������</td><!--���з�ʽ-->	
		<td>
			<select name="direct_sale" id="direct_sale" style="width:122">
				<option>��ѡ��</option>
				<option value="1"<%if(direct_sale.intValue() == 1){%>selected<%}%>>����</option>
				<option value="2"<%if(direct_sale.intValue() == 2){%>selected<%}%>>ֱ��</option>
				<option value="3"<%if(direct_sale.intValue() == 3){%>selected<%}%>>ֱ��&����</option>
			</select>
		</td>
		<td align="right">��Ʒ���ࣺ</td><!--���з�ʽ-->	
		<td>
			<select name="class_type1" id="class_type1" onchange = "javascript:" style="width:122">
				<%=Argument.getClassType1Options(class_type1)%>
			</select>
		</td>
		<td align="right">��Ŀ�����ˣ�</td>	
		<td>
			<input type="text" style="width:120"  name="admin_manager3" value="<%=admin_manager3%>">
		</td>
	</tr>
	<tr>
		<td align="right">ԤԼ��ʼʱ�䣺</td>
		<td>
			<input type="text" name="expre_start_time" id="expre_start_time" size="25" onclick="javascript:setday(this);" readOnly
				value="<%=expre_start_time%>"/> 
		</td>
		<td align="right">ԤԼ��ֹʱ�䣺</td>
		<td>
			<input type="text" name="expre_end_time" id="expre_end_time" size="25" onclick="javascript:setday(this);" readOnly
				value="<%=expre_end_time%>"/> 	
		</td>
		<td align="right">ԤԼ��Ч������</td>
		<td colspan="3">
			<input type="text" name="pre_valid_days" value="<%=pre_valid_days%>" size="3" /> �� (0��ʾ������)
		</td>
	</tr>
	<tr>
		<td align="right" valign="top">Ԥ�������ʣ�</td>
		<td colspan="3">
			<textarea rows="6" name="pre_rate" cols=30"><%=pre_rate%></textarea>
			<script type="text/javascript">
				var oFCKeditor = new FCKeditor('pre_rate') ;
				oFCKeditor.BasePath = '/webEditor/' ;
				oFCKeditor.Width = '360' ;
				oFCKeditor.Height = '100';
				oFCKeditor.ReplaceTextarea();
			</script>
		</td>
		<td align="right" valign="top">��飺</td>
		<td colspan="3">
			<textarea rows="6" name="summary" cols=30"><%=summary%></textarea>
			<script type="text/javascript">
				var oFCKeditor = new FCKeditor('summary') ;
				oFCKeditor.BasePath = '/webEditor/' ;
				oFCKeditor.Width = '360' ;
				oFCKeditor.Height = '100';
				oFCKeditor.ReplaceTextarea();
			</script>
		</td>
	</tr>
	<tr>
		<td align="right" valign="top">�������ʱ�䣺</td>
		<td colspan="3">
			<textarea rows="3" name="profit_date" cols="67"><%=profit_date%></textarea>
		</td>
		<td align="right" valign="top">�ʽ����÷�ʽ��</td>
		<td colspan="3">
			<textarea rows="3" name="cash_usetype" cols="67"><%=cash_usetype%></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">�����գ�</td>	
		<td colspan="3">
			<input type="text" style="width:360"  name="kfr" value="<%=kfr%>">
		</td>
		<td align="right" valign="top" rowspan="2">���ϴ�ʩ��</td>	
		<td colspan="3" rowspan="2">
			<textarea rows="3" name="ensure_method" cols="67"><%=ensure_method%></textarea>
		</td>
	</tr>
	<tr>
		<td align="right">�����ڣ�</td>	
		<td colspan="3">
			<input type="text" style="width:360"  name="kfq" value="<%=kfq%>">
		</td>
	</tr>

    <tr>
        <td align="right">����������</td>
        <td colspan="3">
			<input type="text" style="width:360"  name="dxjg" value="<%=dxjg%>">
		</td>
		<td align="right">Ͷ�ʹ���/�����˴���</td>
	    <td colspan="3">
			<input type="text" style="width:360"  name="tzgw" value="<%=tzgw%>">
		</td>
	</tr>
	<tr>
		<td align="right">��������ύ�ڣ�</td>
		<td colspan="3">
			<input type="text" style="width:360"  name="shsqtjq" value="<%=shsqtjq%>">
		</td>
		<td align="right">�˻���Ϣ��</td>	
		<td colspan="3">
			<input type="text" style="width:360"  name="syzh" value="<%=syzh%>">
		</td>
	</tr>
	<tr>
		<td align="right">��Ϣ��¶��ַ��</td><!--���з�ʽ-->	
		<td colspan="3">
			<input type="text" maxlength="200" size="60" style="width:360"  name="announce_url" value="<%=announce_url%>"/>
		</td>
         <td align="right">������</td>
         <td colspan="3">
	        <input type="text" style="width:360"  name="jjjl" value="<%=jjjl%>"/>
		</td>
	</tr>
<%
AttachmentToCrmLocal attachmentLocal = EJBFactory.getAttachmentToCrm();
AttachmentVO attachmentVO = new AttachmentVO();
attachmentVO.setDf_serial_no(preproduct_id);
attachmentVO.setDf_talbe_id(new Integer(1213)); // 1213Ԥ���в�Ʒ��TPREPRODUCT 
attachmentVO.setInput_man(input_operatorCode);
List attachmentList =attachmentLocal.load(attachmentVO);
for (int i=0; i<attachmentList.size(); i++) {
	Map attachmentMap = (Map)attachmentList.get(i);
	String attachmentId=Utility.trimNull(attachmentMap.get("ATTACHMENTID"));
	String origin_name = Utility.trimNull(attachmentMap.get("ORIGIN_NAME"));
   	String save_name=Utility.trimNull(attachmentMap.get("SAVE_NAME")); %>
	<tr>
		<td align="right">����<%=i+1%>��</td>
		<td colspan="7" align="left">
			<a href="javascript:DownloadAttachment(<%=attachmentId%>);" style="width:200px;text-align:right"><%=origin_name%></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<button class="xpbutton3" id="btn_DelDbAttachment" name="btn_DelDbAttachment" onclick="javascript:deleteDbAttachment('<%=attachmentId%>','<%=save_name%>');">ɾ������</button>
		</td>
	</tr>
<%} 
attachmentLocal.remove();%>	
	<tr>
		<td align="right" valign="top"><input type="button" onclick="addline()" value="��Ӹ��฽��" style="line-height:15px;margin-top:5px;"/></td>	
		<td colspan="7">
			<div>
            	<div style="float:left">
	            	<table id="test" style="width:190px;" ><tr ><td><input type="file" name="file_info" size="45">&nbsp;</td></tr></table>
            	</div>
            <div>
		</td>
	</tr>				
</table>

<table border="0" width="100%">
	<tr>
		<td><br></td>
	</tr>
	<tr>
		<td align="right">
		<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()) document.theform.submit();"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
		&nbsp;&nbsp;<!--����-->
		<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:history.back();">���� (<u>B</u>)</button>
		&nbsp;&nbsp;<!--ȡ��-->
	</tr>
</table>
</form>
<script language=javascript>
function FCKeditor_OnComplete(editorInstance){
    //editorInstance.Commands.GetCommand('Source').Execute();  //ִ�С�Դ���롱����
    editorInstance.ToolbarSet.Collapse();  //���ع�����ToolbarStartExpanded
}

<%if(direct_sale.intValue()!=0){%>
	document.getElementById("direct_sale").value = '<%=direct_sale%>';
<%}%>
</script>
</body>
</html>