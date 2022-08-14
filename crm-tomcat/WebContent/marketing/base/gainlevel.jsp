<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.util.*" %>
<%@page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%
try{
String sPage = request.getParameter("page");
String sPagesize = request.getParameter("pagesize");
Integer product_id = Utility.parseInt(request.getParameter("product_id"), overall_product_id);
String product_name = Utility.trimNull(request.getParameter("product_name"));
Integer  sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
String prov_level = Utility.trimNull(request.getParameter("prov_level")) ;
Integer serial_no = Utility.parseInt(request.getParameter("serial_no") , new Integer(0)) ;
Integer prov_flag = Utility.parseInt(request.getParameter("prov_flag") , new Integer(0)) ;
int check_flag = Utility.parseInt(request.getParameter("check_flag"),-1);

GainLevelLocal gainLevel = EJBFactory.getGainLevel();
boolean bSuccess = false ;
//ɾ��
if (request.getMethod().equals("POST") && check_flag==1) {
	String[] s = request.getParameterValues("s_id");
	if (s != null) {
		for(int i = 0;i < s.length; i++) {		    	
			serial_no = Utility.parseInt(s[i], new Integer(0));			
			gainLevel.setSerial_no(serial_no);
			gainLevel.setInput_man(input_operatorCode);
			
			gainLevel.delete();
		}
		bSuccess = true;
	}
}

gainLevel.setProv_level(prov_level) ;
//gainLevel.setSerial_no(serial_no);
gainLevel.setProduct_id(product_id) ;
gainLevel.setSub_product_id(sub_product_id) ;
gainLevel.setProv_flag(prov_flag);
gainLevel.query() ;

String sUrl = "gainlevel.jsp?product_id="+product_id+"&sub_product_id="+sub_product_id+"&prov_flag="+prov_flag+"&prov_level="+prov_level;
gainLevel.gotoPage(sPage, sPagesize);

String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48);
options = options.replaceAll("\"", "'");

%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>���漶������</title>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT language="javascript">
<%if(bSuccess){%>
	sl_alert("ɾ���ɹ���");
	location = "gainlevel.jsp?page=<%=sPage%>&pagesize=<%=sPagesize%>&product_id=<%=product_id%>&sub_product_id<%=sub_product_id%>&prov_flag=<%=prov_flag%>&prov_level=<%=prov_level%>";
<%}%>

window.onload = function(){
		initQueryCondition();
		getSubProductOptions(document.theform.product_id.value,<%=sub_product_id%>,0);
	};

function refreshPage(){
	disableAllBtn(true);	
	location.search = '?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
						+"&prov_level="+document.theform.prov_level.value
						+"&prov_flag="+document.theform.prov_flag.value
						+"&product_id="+document.theform.product_id.value 
						+ (document.theform.sub_product_id? ("&sub_product_id="+document.theform.sub_product_id.value): "");
}

function StartQuery(){
	refreshPage();
}

//����
function newInfo(serial_no , flag){
	//��ѯ����
	var iQuery = document.theform.product_id.value + "$<%=sub_product_id%>$<%=sPage%>" 
					+ "$"+document.theform.pagesize.value+ "$"+document.theform.prov_level.value+ "$"+document.theform.prov_flag.value;
	location.href = flag==0? ('gainlevel_info_new.jsp?iQuery='+iQuery)
						   : ('gainlevel_info_new.jsp?serial_no='+serial_no+'&iQuery='+iQuery);	
}

//����������
function rateSet(serial_no,product_id , sub_product_id , prov_flag , 
			prov_level_name , lower_limit,upper_limit,summary,gain_flag ,cust_typename,productName){
	//��ҳ��ѯ����
	var iQuery = document.theform.product_id.value + "$" +<%=sub_product_id %> + "$" + "<%=sPage%>" + "$" + document.theform.pagesize.value+ "$" + document.theform.prov_level.value+ "$" + document.theform.prov_flag.value;
	location = 'gainlevel_rate_set.jsp?df_serial_no='+serial_no + '&product_id='+ product_id +'&productName='+productName+
				'&sub_product_id='+sub_product_id + '&prov_flag=' + prov_flag + '&prov_level_name='+prov_level_name +
				'&lower_limit='+lower_limit + '&upper_limit='+upper_limit + '&summary='+summary +'&gain_flag='+gain_flag+'&cust_typename='+cust_typename+'&iQuery='+iQuery;
}

//ɾ��
function confirmRemove(serial_no,flag) {	
	if (document.theform.s_id==null) {
		sl_alert("û����Ҫɾ���ļ�¼��");
		return false;
	}
		
	if(checkedCount(document.theform.s_id) == 0) {
		sl_alert("��ѡ��Ҫɾ���ļ�¼��");
		return false;
	}
	
	if(confirm('��ȷ��ѡ���ļ�¼ɾ����')) {	
		disableAllBtn(true);
		document.theform.check_flag.value = flag;
		document.theform.submit();
	}	
}

//��ѯ���� ���ض�Ӧ��Ʒ���Ӳ�Ʒ
function getSubProductOptions(value1,value2) {
	if(value1!=0){
		utilityService.getSubProductOptionS(value1,value2,{callback: function(data){
			if(data=='<option value="">��ѡ��</option>'){
				document.getElementById('subProduct_style').style.display = 'none';			
			}else{ 		
				document.getElementById('subProduct_style').style.display = ''; 
			}			
			$("subProductOptions").innerHTML = "<select name='sub_product_id' style='width:335px;'>"+data+"</select>"
		}});
	}else{
		document.getElementById('subProduct_style').style.display = 'none';
	}
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")	{	
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				getSubProductOptions(prodid,'');//�����Ӳ�Ʒ
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('����Ĳ�Ʒ��Ų�����!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}		
	}
}
 
function searchProduct(value) {
	prodid=0;
	if (value != "") {
        j = value.length;
        
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.focus();
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				getSubProductOptions(prodid,'');//�����Ӳ�Ʒ
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('����Ĳ�Ʒ��Ų�����!');
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function searchProductName(value){
	var list = [];
	var list1 = [];
	document.getElementById("select_id").innerHTML = 
				"<SELECT name='product_id'	class='productname' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:getSubProductOptions(this.value,'');'  style='width: 335px;'>"+"<%=options%>"+"</SELECT>";
	if (value != ""){
		for (var i=0; i<document.theform.product_id.options.length; i++) {
			var j = document.theform.product_id.options[i].text.indexOf(value);
			if (j>0) {
				list.push(document.theform.product_id.options[i].text);
				list1.push(document.theform.product_id.options[i].value);
			}
		}	
		if (list.length==0) {
			sl_alert('����Ĳ�Ʒ���Ʋ����� ��');//����Ĳ�Ʒ���Ʋ�����
			document.theform.productid.value = "";
			document.theform.product_id.options[0].selected=true;
		} else {
			document.theform.product_id.options.length=0;
			for (var i=0; i<list.length; i++)
				document.theform.product_id.options.add(new Option(list[i],list1[i]));
		}
		document.theform.product_id.focus();
	}
}
//�鿴��Ʒ��Ϣ
function showProduct(product_id){	
	showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}
</SCRIPT>
</HEAD>
<BODY class="BODY body-nox"><%@ include file="/includes/waiting.inc"%> 
<form name="theform" action="gainlevel.jsp" method="post">

<input type="hidden" name="check_flag" value="">
<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	<tr>
		<td>��ѯ������</td>
	   	<td align="right">
	   		<button type="button"   class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
	   	</td>
  	</tr>
</table>
<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
    	<td align="right">��Ʒ���:</td >
        <td align="left" >
        	<input type="text" maxlength="16" name="productid" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="13">&nbsp;
        	<button type="button"   class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
        </td>
		<td align="right">��Ʒ����:</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"   class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
    </tr>
	<tr>
	    <td align="right">��Ʒѡ��:</td >
	    <td align="left" colspan="3" id="select_id">
	       <SELECT name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:getSubProductOptions(this.value,'');"  style="width: 335px;">
	       	<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48)%></SELECT>
	    </td>
	</tr>
	<tr id="subProduct_style" style="display:none;">
		<td align="right">�Ӳ�Ʒ����:</td>
		<td colspan="3" id="subProductOptions">

		</td>
	</tr>
	<tr>
    	<td align="right">���ȼ�:</td >
        <td align="left">
			<SELECT name="prov_flag" style="width:120px;">
				<OPTION value="0">��ѡ��</OPTION>
				<%=Argument.getTableOptions2(3003, prov_flag)%>
			</SELECT>
        </td>
        <td align="right">���漶��:</td>
		<td>
			<SELECT name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width:120px;">
			  	<%=Argument.getProvlevelOptions(prov_level)%>
		    </SELECT>
		</td>
    </tr>	
	<tr>						
		<td align="right" colspan="3">
			<button type="button"   class="xpbutton3" accessKey=s name="btnQuery"onclick="javascript:StartQuery();">ȷ��(<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
		</td>
	</tr>					
</table>
</div>

<TABLE cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left>
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle>

						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title" ><b><%=menu_info%></b></td>
								<td>
							</tr>
							<tr>
							<td align="right">
								<div class="btn-wrapper">
								<button type="button"   class="xpbutton3" accessKey=q id="queryButton" name="queryButton">��ѯ(<u>Q</u>)</button>
							<%//if (user_id.intValue()!=15/*����*/) {%>
								&nbsp;&nbsp;&nbsp;
								<%if (input_operator.hasFunc(menu_id, 100) ) {%>
								<button type="button"   class="xpbutton3" accessKey=n id="btnNew" name="btnNew"
								title="�½���¼" onclick="javascript:newInfo('',0);">�½�(<u>N</u>)</button>
								&nbsp;&nbsp;&nbsp;
								<%}if (input_operator.hasFunc(menu_id, 101)) {%>
								<button type="button"   class="xpbutton3" accessKey=d id="btnDelete"name="btnDelete" title="ɾ����ѡ��¼"onclick="javascript:if(confirmRemove(document.theform.serial_no,1)) document.theform.submit();">ɾ��(<u>D</u>)</button>
								<%}
							//}%>	
								</div>	
								<br/>					
								</td>
							</tr>
						</table>

						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
							<tr class="trh">
								<td align="center" height="25">
								<%if (input_operator.hasFunc(menu_id, 101)) /*ɾ��*/{%>
                                	<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.s_id,this);">
								<%}%>&nbsp;��Ʒ���
                                </td>
								<td align="center" height="25">��Ʒ����</td>	
								<td align="center" height="25">���ȼ�</td>	
								<td align="center" height="25">���漶��</td>
								<td align="center" height="25">������(%)</td>
								<td align="center" height="25">�ͻ����</td>
								<td align="center" height="25">�ݶ�����</td>
								<td align="center" height="25">�ݶ�����</td>
							<%if (user_id.intValue()!=15/*����*/) {%>
								<td align="center" height="25">�����ʵ���</td>								
								<td align="center" height="25" width="8%">�༭</td>
							<%} %>
							</tr>

<%
int iCount = 0;
int iCurrent = 0;
String cust_typename = "" ;
while( gainLevel.getNextLevel() && iCurrent<gainLevel.getPageSize()){
	String prov_flag_msg = "" ;
	if(gainLevel.getProv_flag().intValue()==1){
		prov_flag_msg="����";
	}else if(gainLevel.getProv_flag().intValue()==2){
		prov_flag_msg = "һ��" ;
	}else if(gainLevel.getProv_flag().intValue()==3){
		prov_flag_msg="�Ӻ�" ;
	}
	if(gainLevel.getCust_type().intValue()==1){
		cust_typename="����";
	}else if(gainLevel.getCust_type().intValue()==2){
		cust_typename = "����" ;
	}else{
		cust_typename="ȫ��" ;
	}		
 %>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh"  width="" align="center">
									<table border="0" width="100%" cellspacing="0" cellpadding="0">
										<tr>
											<td width="10%">
											<%if (input_operator.hasFunc(menu_id, 101)) /*ɾ��*/{%>
                                				<input type="checkbox" name="s_id" value="<%=gainLevel.getSerial_no()%>" class="flatcheckbox">
											<%}%>												
											</td>
											<td width="90%" align="left">
												<%=Utility.trimNull(gainLevel.getProduct_code())%>			
											</td>
										</tr>
									</table>
								</td>
								<td align="left"><a href="#" onclick="javascript:showProduct('<%=gainLevel.getProduct_id()%>');"><%=Utility.trimNull(gainLevel.getProduct_name())%></a></td> 
								<td width="" align="center" ><%=prov_flag_msg%></td>
								<td width="" align="center" ><%=gainLevel.getProv_level_name() %></td>
								<td width="" align="right" ><%=Utility.parseBigDecimal(gainLevel.getGain_rate(),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP) %></td>
								<td width="" align="center" ><%=cust_typename %></td>
								<td width="" align="right" ><%=Format.formatMoney(gainLevel.getLower_limit()) %></td>
								<td width="" align="right" ><%=Format.formatMoney(gainLevel.getUpper_limit())%></td>
							<%if (user_id.intValue()!=15/*����*/) {%>
								<td width="" align="center" > 
									<button type="button"   class="xpbutton2" accessKey=b id="btnCancel" name="btnCancel" name="btnEdit" title="����������" 
									onclick="javascript:rateSet('<%=gainLevel.getSerial_no() %>',
																'<%=gainLevel.getProduct_id() %>',
																'<%=gainLevel.getSub_product_id() %>',
																'<%=gainLevel.getProv_flag() %>', 
																'<%=gainLevel.getProv_level_name() %>',
																'<%=gainLevel.getLower_limit() %>',
																'<%=gainLevel.getUpper_limit() %>',
																'<%=Utility.trimNull(gainLevel.getSummary()) %>',
																'<%=Utility.trimNull(gainLevel.getGain_flag()) %>',
																'<%=cust_typename %>',
																'<%=Utility.trimNull(gainLevel.getProduct_code())%>_<%=Utility.trimNull(gainLevel.getProduct_name())%>');">>></button>
								</td>
								<td width="" align="center" >
									<button type="button"   class="xpbutton2" accessKey=b id="btnCancel" name="btnCancel" name="btnEdit"
												title="�޸ļ�¼" onclick="javascript:disableAllBtn(true);newInfo('<%=gainLevel.getSerial_no() %>',1);">>></button>
								</td>
							<%}%>
							</tr>
<%
iCount++ ;
iCurrent++ ;
} 
for (; iCurrent < gainLevel.getPageSize(); iCurrent++) {%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh"  width="" align="center"></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
							<%if (user_id.intValue()!=15/*����*/) {%>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
							<%}%>
							</tr>
<%}%>
							<tr class="trbottom">
								<td class="tdh" align="center" height="25"><b>�ϼ� <%=gainLevel.getRowCount() %> ��</b></td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
							<%if (user_id.intValue()!=15/*����*/) {%>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
							<%}%>
							</tr>
						</table>
						<p></p>
						<table border="0" width="100%" class="page-link">
							<tr valign="top">
								<td><%=gainLevel.getPageLink(sUrl) %></td>
							</tr>
						</table>
						</TD>
					</TR>
			</TABLE>
			</TD>
		</TR>
</TABLE>
</form>
<%@ include file="/includes/foot.inc"%> 
</BODY>
</HTML>
<SCRIPT language="javascript">
getSubProductOptions(<%=product_id%>,'');//�����Ӳ�Ʒ�����б��ӳټ�������
</script>
<%gainLevel.remove();
}catch(Exception e){
	throw e ;
} 
%>