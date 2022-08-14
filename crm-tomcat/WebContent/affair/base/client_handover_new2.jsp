<%@ page contentType="text/html; charset=GBK"  
	import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*,java.math.*" %>
<%@ include file="/includes/parameter.inc"%>
<%@ include file="/includes/operator.inc"%>
<%
String no = Utility.trimNull(request.getParameter("no"));
String name = Utility.trimNull(request.getParameter("name"));
String card_id = Utility.trimNull(request.getParameter("card_id"));
String tel = Utility.trimNull(request.getParameter("tel"));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));
Integer is_link = Utility.parseInt(request.getParameter("is_link"),new Integer(0));//是否关联方
Integer sourceManagerID = Utility.parseInt(request.getParameter("sourceManagerID"), null);//客户经理
String cust_level = Utility.trimNull(request.getParameter("cust_level"));
BigDecimal min_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("min_total_money")));//最低购买金额
BigDecimal max_total_money = Utility.stringToDouble(Utility.trimNull(request.getParameter("max_total_money")));//最高购买金额
BigDecimal ben_amount_min = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_min")));//最低受益金额
BigDecimal ben_amount_max = Utility.stringToDouble(Utility.trimNull(request.getParameter("ben_amount_max")));//最高受益金额
Integer start_rg_times = Utility.parseInt(request.getParameter("start_rg_times"), new Integer(0));//开始购买次数
Integer end_rg_times = Utility.parseInt(request.getParameter("end_rg_times"), new Integer(0));//结束购买次数

List list = new ArrayList();
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo_cust = new CustomerVO();
vo_cust.setCust_no(no);
vo_cust.setCust_name(name);	
vo_cust.setProduct_id(product_id);
vo_cust.setIs_link(is_link);
vo_cust.setCard_id(card_id);
vo_cust.setH_tel(tel);
vo_cust.setCust_level(cust_level);
vo_cust.setMin_times(start_rg_times);
vo_cust.setMax_times(end_rg_times);
vo_cust.setMin_total_money(min_total_money);
vo_cust.setMax_total_money(max_total_money);
vo_cust.setBen_amount_min(ben_amount_min);
vo_cust.setBen_amount_max(ben_amount_max);	
vo_cust.setInput_man(input_operatorCode);
vo_cust.setService_man(sourceManagerID);
vo_cust.setExport_flag(new Integer(102));//客户移交查询时，本参数指定值102

list = customer.listProcAllExt(vo_cust, 1, -1).getRsList();
customer.remove();

//TcustmanagersLocal tcustmanagers_Bean = EJBFactory.getTcustmanagers();

//List sourceManagersList= tcustmanagers_Bean.getManagerList_sortByAuth(input_operatorCode);
//List tagertManagersList = tcustmanagers_Bean.getManagerList_sortByAuth(new Integer(888));

Integer managerid_now = Utility.parseInt(request.getParameter("managerID"), new Integer(0));
boolean bSuccess = false;

if (request.getMethod().equals("POST")) {
	CustManagerChangesLocal local = EJBFactory.getCustManagerChanges();
	TcustmanagerchangesVO vo = new TcustmanagerchangesVO();		
	vo.setManagerid_before(sourceManagerID);
	vo.setManagerid_now(managerid_now);
	vo.setInput_man(input_operatorCode);

	String[] cust_ids = request.getParameterValues("cust_id");
	if (cust_ids!=null) {
		try{
			for (int i=0; i<cust_ids.length; i++) {
				Integer custId = Utility.parseInt(cust_ids[i], new Integer(0));
				if (custId.intValue()>0) {
					vo.setCust_id(custId);

					bSuccess = false;
					local.append(vo);
					bSuccess = true;
				}
			}
	
		}catch(Exception e){
			 String message = enfo.crm.tools.LocalUtilis.language("message.saveFail", clientLocale); //保存失败
			 out.println("<script language=\"javascript\">alert(\""+message+","+e.getMessage()+"\"); window.returnValue = null;window.close();</script>");
		}	
	}
	local.remove();
}

//tcustmanagers_Bean.remove();
%>
<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>客户移交-新建</title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language=javascript>
<% if(bSuccess){ %>
	sl_alert("新建成功！");
	location.href = "client_handover.jsp";
<%}%>

function checkAll(checkbox, chkboxArrName, win) {
	var aChkbox;
	if (win) 
		aChkbox = win.document.getElementsByName(chkboxArrName);
	else
		aChkbox = document.getElementsByName(chkboxArrName);

	for (var i=0; i<aChkbox.length; i++) 
		aChkbox[i].checked = checkbox.checked;		
}

function validateForm(form){	
	var errmsg = '';

	if (document.theform.sourceManagerID.value == '') 
		errmsg += '未选定源<%=LocalUtilis.language("class.accountManager",clientLocale)%>！请选择源<%=LocalUtilis.language("class.accountManager",clientLocale)%>！\n';

	if (document.theform.managerID.value == '') 
		errmsg += '未选定目标<%=LocalUtilis.language("class.accountManager",clientLocale)%>！请选择目标<%=LocalUtilis.language("class.accountManager",clientLocale)%>！\n';

	var cust_ids = document.getElementsByName('cust_id');
	var i;
	for (i=0; i<cust_ids.length; i++) if (cust_ids[i].checked) break;
	if (cust_ids.length==0 || i==cust_ids.length)
		errmsg += '未选定任何客户！请选择要移交的客户！\n';		
	
	if (errmsg!='') {
		sl_alert(errmsg);
		return false;
	}

	return sl_check_update();	
}

function SaveAction(){
	if (document.getElementsByName('theform')[0].onsubmit())
		document.getElementsByName('theform')[0].submit();
}

function CancelAction(){
	location.href = "client_handover.jsp";
}

function setProduct(value){
	prodid=0;
	if (event.keyCode == 13 && value != "")
	{
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++)
		{
			if(document.theform.product_id.options[i].text.substring(0,j)==value)
			{
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0)
		{
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}			
	}
	nextKeyPress(this);
}

function searchProduct(value){
	prodid=0;
	if (value != "") {
        j = value.length;
		for(i=0;i<document.theform.product_id.options.length;i++) {
			if(document.theform.product_id.options[i].text.substring(0,j)==value) {
				document.theform.product_id.options[i].selected=true;
				prodid = document.theform.product_id.options[i].value;
				break;
			}	
		}
		if (prodid==0) {
			sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> !');//输入的产品编号不存在
			document.theform.productid.value="";
			document.theform.product_id.options[0].selected=true;	
		}
		document.theform.product_id.focus();					
	}	
}

function QueryAction(action_flag){
	var is_link = document.theform.is_link.checked? 1: 0;
	location.search = "?name="+document.theform.name.value
				+"&no="+document.theform.no.value+"&card_id="+document.theform.card_id.value
				+"&tel="+document.theform.tel.value+"&sourceManagerID="+document.theform.sourceManagerID.value
				+"&managerID="+document.theform.managerID.value
				+"&cust_level="+document.theform.cust_level.value+"&is_link="+is_link			
				+"&product_id="+document.theform.product_id.value
				+"&start_rg_times="+document.theform.start_rg_times.value
				+"&end_rg_times="+document.theform.end_rg_times.value
				+"&min_total_money="+document.theform.min_total_money.value
				+"&max_total_money="+document.theform.max_total_money.value
				+"&ben_amount_min="+document.theform.ben_amount_min.value
				+"&ben_amount_man="+document.theform.ben_amount_max.value;
}

window.onload = function(){
		initQueryCondition();
	};
</script>
</HEAD>
<BODY class="BODY body-nox">
<%//if (tagertManagersList.isEmpty()) {%>
<script language="javascript">
	//sl_alert('您没有可以授权的同级别客户经理，所以不能授权！');
	//location.href = 'auth_rapid.jsp';
</script>
<%//} %>

<form name="theform" method="post" action="client_handover_new2.jsp" onsubmit="javascript:return validateForm(this);"> 
	<div class="page-title"><font color="#215dc6"><b><%=menu_info%></b></font></div>
 
<div id="queryCondition" class="qcMain" style="display:none;width:550px;" >
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2" class="product-list">
		<tr>
			<td align="right" width="13%"><%=LocalUtilis.language("class.sourceManager", clientLocale)%>:</td><!-- 客户经理 -->
			<td align="left">
				<select name="sourceManagerID">
					<%=Argument.getManager_Value(sourceManagerID)%>
				</select> 
			</td>
		</tr>
		<tr>
			<td align="right" width="13%"><%=LocalUtilis.language("class.customerID",clientLocale)%>:</td><!-- 客户编号 -->
			<td align="left">
				<input name="no" value='<%=no%>' onkeydown="javascript:nextKeyPress(this)" size="25">
			</td>
			<td align="right" width="13%"><%=LocalUtilis.language("class.customerName",clientLocale)%>:</td><!-- 客户名称 -->
			<td align="left">
				<input name="name" value='<%=name%>' onkeydown="javascript:nextKeyPress(this)" size="25" maxlength="100">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%>:</td><!-- 证件号码 -->
			<td align="left">
				<input name="card_id" value='<%=card_id%>' onkeydown="javascript:nextKeyPress(this)" size="25">
			</td>
			<td align="right"><%=LocalUtilis.language("login.telephone",clientLocale)%>:</td><!-- 联系电话 -->
			<td align="left">
				<input name="tel" value='<%=tel%>' onkeydown="javascript:nextKeyPress(this)" size="25">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerLevel",clientLocale)%> </td><!-- 客户级别 -->
			<td align="left">
				<select style="width:150px" name="cust_level" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getDictParamOptions2(1111, cust_level)%>
				</select>
			</td>
			<td align="right"><%=LocalUtilis.language("class.isLink",clientLocale)%>:</td><!-- 是否关联方 -->
			<td align="left">
			 	<input type="checkbox" name="is_link" value="1" class="flatcheckbox" <%=is_link.intValue()==1?"checked":""%> >
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%>:</td><!-- 产品编号 -->
			<td align="left" colspan="3">
				<input type="text" maxlength="16" name="productid"
					value="" onkeydown="javascript:setProduct(this.value);" maxlength=8
					size="18" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
				<button type="button"  class="searchbutton"
					onclick="javascript:searchProduct(document.theform.productid.value);"></button>
			</td>	
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.productNumber",clientLocale)%>:</td><!-- 产品选择 -->
			<td align="left" colspan="3">
				<SELECT name="product_id" style="width: 320px;" onkeydown="javascript:nextKeyPress(this)">
					<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
				</SELECT>
			</td>
		</tr>
		<tr>  
			<td align="right"><%=LocalUtilis.language("class.rg_times",clientLocale)%>:</td><!-- 购买次数 -->
			<td align="left" colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> 
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="start_rg_times" size="3" value="<%=start_rg_times%>">
	            <!-- 到 -->&nbsp;&nbsp;<%=LocalUtilis.language("message.end",clientLocale)%>&nbsp;&nbsp;  
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="9" name="end_rg_times" size="3" value="<%=end_rg_times%>">
			</td>
		</tr>
		<tr>
			<!-- 购买金额 -->
	        <td align="right"><%=LocalUtilis.language("class.totalMoney",clientLocale)%>:</td>
	        <!-- 从  -->
			<td align="left" colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> 
			 	<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="min_total_money" size="20" value="<%=min_total_money%>"> 
				<!-- 到 -->&nbsp;&nbsp;<%=LocalUtilis.language("message.end",clientLocale)%>&nbsp;&nbsp;  
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="max_total_money" size="20" value="<%=max_total_money%>">
			</td>
		</tr>
		<tr>
			<!-- 受益金额 -->
	        <td align="right"><%=LocalUtilis.language("class.ben_amount",clientLocale)%>:</td>
	        <!-- 从 -->
			<td align="left" colspan="3"><%=LocalUtilis.language("message.start",clientLocale)%> 
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_min" size="20" value="<%=ben_amount_min%>">
				<!-- 到 -->&nbsp;&nbsp;<%=LocalUtilis.language("message.end",clientLocale)%>&nbsp;&nbsp;   
				<input type="text" onkeydown="javascript:nextKeyPress(this)" maxlength="10" name="ben_amount_max" size="20"value="<%=ben_amount_max%>">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="QueryAction()"><%=LocalUtilis.language("message.confirm",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	
			</td>
		</tr>	
	</table>
</div>
<table border="0" width="100%" cellspacing="5" cellpadding="0" class="product-list">
	<tr>
		<td align="right">*<%=LocalUtilis.language("class.targetManager", clientLocale)%> ：</td>
		<td align="left">
			<select name="managerID" style="width:110px;">
				<%=Argument.getManager_Value(managerid_now)%>
			</select>
		</td>
	</tr>

	<tr>
		<td align="right" valign="top">*要移交的客户 ：</td>
		<td align="left" colspan="2">
			<table width="489px" cellspacing="1" cellpadding="2">
				<tr class="trh">
					<td align="center" width="25%">
						<input type="checkbox" class="selectAllBox"	
							onclick="javascript:checkAll(this, 'cust_id');"><%=LocalUtilis.language("class.customerID", clientLocale)%><!-- 客户编号 -->
					</td>
					<td align="center" width="20%"><%=LocalUtilis.language("class.sourceManager",clientLocale)%></td><!-- 经理名称 -->
					<td align="center" width="30%"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!-- 客户名称 -->
					<td align="center" width="18%"><%=LocalUtilis.language("class.customerType",clientLocale)%></td><!-- 客户类别 -->
				</tr>  
			<%for (int i=0; i<list.size(); i++) {
				Map map = (Map)list.get(i);			%>
				<tr class="tr<%=i%2%>">
					<td align="left" width="25%">
						<input type="checkbox" name="cust_id" value="<%=map.get("CUST_ID")%>"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<%=map.get("CUST_NO")%>
					</td>
					<td align="center" width="20%"><%=map.get("SERVICE_MAN_NAME") == null ? "未分配" : map.get("SERVICE_MAN_NAME")%></td>
					<td align="center" width="30%"><%=map.get("CUST_NAME")%></td>
					<td align="center" width="*"><%=map.get("CUST_TYPE_NAME")%></td><!-- 客户类别 -->
				</tr>
			<%} %>
			</table>
		</td>		
		<td align="left" valign="top">
			<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="筛选" onclick="javascript:void(0);">筛选 (<u>Q</u>)</button>
		</td>
	</tr>
</table>

<div align="right"><br>
	<!-- 保存 -->
	<button type="button" class="xpbutton3" accessKey=s id="btnSave" onclick="javascript:SaveAction();"><%=LocalUtilis.language("message.save", clientLocale)%> (<u>S</u>)</button>
	&nbsp;&nbsp;&nbsp;&nbsp; <!-- 返回 -->
	<button type="button"  class="xpbutton3" accessKey=c onclick="javascript:CancelAction();"><%=LocalUtilis.language("message.back", clientLocale)%> (<u>C</u>)</button>
	&nbsp;&nbsp;
</div>
</form>
</BODY>
</HTML>