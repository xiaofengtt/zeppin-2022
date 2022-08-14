<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
//声明页面参数
input_bookCode = new Integer(1);//帐套暂时设置
//获取页面传递变量
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
Integer serial_no = Utility.parseInt(Utility.trimNull(request.getParameter("serial_no")),new Integer(0));
//声明变量

//--2.客户信息
String cust_no = "";
String cust_name = "";
Integer cust_type = new Integer(0);
String cust_type_name = "";
Integer service_man= new Integer(0);
Integer card_type = new Integer(0);
String post_code = "";
String post_address = "";
Integer is_link = new Integer(0);
String legal_man= null;
String contact_man= null;
//------------------------------------
String card_id = "";
String cust_tel = "";
String o_tel = "";
String h_tel = "";
String mobile= "";
String e_mail = "";
String bp = "";
//------------------------------------
String h_card_id = "";
String h_cust_tel = "";
String h_o_tel = "";
String h_h_tel = "";
String h_mobile= "";
String h_e_mail = "";
String h_bp = "";
//服务信息
String serviceTime="";
String serviceInfo="";
String serviceSummary="";
String content = "";
String subject = "";
String step_flag_name = "";
String info_level_name = "";
String service_man_name = "";
String executor_name = "";
//获取对象
Customer_INSTLocal  customer_inst = EJBFactory.getCustomer_INST();
CustomerLocal customer = EJBFactory.getCustomer();//客户
CustomerVO c_vo = new CustomerVO();
CustomerVO cust_vo = new CustomerVO();

CustServiceLogLocal custServiceLogLocal=EJBFactory.getCustServiceLog();
CustServiceLogVO custServiceLogVO=new CustServiceLogVO();
//维护记录信息
if(serial_no.intValue()>0){
	List rsList_service = null;
	Map map_service = null;
	//当前值
	c_vo = new CustomerVO();
	c_vo.setSerial_no(serial_no);
	c_vo.setInput_man(input_operatorCode);
	IPageList pageList = customer.getCustomerMaintenanceRecord(c_vo,1,8);
	rsList_service = pageList.getRsList();

	if(rsList_service.size()>0){
		map_service = (Map)rsList_service.get(0);
	}

	if(map_service!=null){	
		serviceTime = Utility.trimNull(map_service.get("SERVICE_TIME")).substring(0,16);;
		serviceInfo = Utility.trimNull(map_service.get("SERVICE_INFO"));
		serviceSummary = Utility.trimNull(map_service.get("SERVICE_SUMMARY"));
		content = Utility.trimNull(map_service.get("CONTENT"));
		subject = Utility.trimNull(map_service.get("SUBJECT"));
		step_flag_name = Utility.trimNull(map_service.get("STEP_FLAG_NAME")); 
		info_level_name = Utility.trimNull(map_service.get("INFO_LEVEL_NAME")); 
		executor_name = Utility.trimNull(map_service.get("EXECUTOR_NAME"));
		service_man_name = Utility.trimNull(map_service.get("SERVICE_MAN_NAME"));
	}
}
//客户信息详细
if(cust_id.intValue()>0){
	List rsList_cust = null;
	Map map_cust = null;	
	//客户单个值		
	c_vo = new CustomerVO();
	c_vo.setCust_id(cust_id);
	c_vo.setInput_man(input_operatorCode);
	rsList_cust = customer.listByControl(c_vo);
			
	if(rsList_cust.size()>0){
		map_cust = (Map)rsList_cust.get(0);		
	}

	if(map_cust!=null){	
		cust_no = Utility.trimNull(map_cust.get("CUST_NO"));
		cust_name = Utility.trimNull(map_cust.get("CUST_NAME"));
		cust_type = Utility.parseInt(Utility.trimNull(map_cust.get("CUST_TYPE")),new Integer(0));
		service_man = Utility.parseInt(Utility.trimNull(map_cust.get("SERVICE_MAN")),new Integer(0));		
		legal_man = Utility.trimNull(map_cust.get("LEGAL_MAN"));
		contact_man  = Utility.trimNull(map_cust.get("CONTACT_MAN"));
		post_code = Utility.trimNull(map_cust.get("POST_CODE"));
		post_address = Utility.trimNull(map_cust.get("POST_ADDRESS"));
		card_type = Utility.parseInt(Utility.trimNull(map_cust.get("CARD_TYPE")),new Integer(0));
		cust_type_name = Utility.trimNull(map_cust.get("CUST_TYPE_NAME"));
		is_link = Utility.parseInt(Utility.trimNull(map_cust.get("IS_LINK")),new Integer(0));
		//保密信息
		card_id = Utility.trimNull(map_cust.get("CARD_ID"));
		o_tel = Utility.trimNull(map_cust.get("O_TEL"));
		h_tel = Utility.trimNull(map_cust.get("H_TEL"));
		mobile = Utility.trimNull(map_cust.get("MOBILE"));
		bp = Utility.trimNull(map_cust.get("BP"));
		e_mail  = Utility.trimNull(map_cust.get("E_MAIL"));
		
		h_card_id = Utility.trimNull(map_cust.get("H_CARD_ID"));
		h_o_tel = Utility.trimNull(map_cust.get("H_O_TEL"));
		h_h_tel = Utility.trimNull(map_cust.get("H_H_TEL"));
		h_mobile= Utility.trimNull(map_cust.get("H_MOBILE"));
		h_e_mail = Utility.trimNull(map_cust.get("H_E_MAIL"));
		h_bp = Utility.trimNull(map_cust.get("H_BP"));
	}		
}

%>

<HTML>
<HEAD>
<TITLE>查看客户服务记录</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<base target="_self">
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript" src="<%=request.getContextPath()%>/webEditor/fckeditor.js"></script>
</HEAD>
<BODY class="BODY">
<form name="theform" method="post">
<div align="left">
	<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" align="absbottom" width="32" height="28">
	<hr noshade color="#808080" size="1">
</div>

<!--客户选择-->
<div vAlign=top align=left>
<table cellSpacing=0 cellPadding=4 width="100%" border=0>
			<tr>
				<td align="left"><b><%=LocalUtilis.language("menu.customerInformation",clientLocale)%> </b></td>	<!--客户信息-->			
			<tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_cust_name" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=cust_name%>">&nbsp;&nbsp;&nbsp;
				</td>
				<td align="right"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!--客户经理-->
				<td ><input maxlength="100" readonly class='edline'  name="customer_service_man" size="50" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(Argument.getManager_Name(service_man))%>">&nbsp;&nbsp;&nbsp;
				</td>
			</tr>	
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerType",clientLocale)%> :</td><!--客户类别-->
				<td ><INPUT readonly class='edline' name="customer_cust_type_name" size="20" value="<%=cust_type_name%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</td><!--证件号码-->
				<td><input readonly class='edline' name="customer_card_id" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=card_id%>" size="20"></td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerHTel",clientLocale)%> :</td><!--家庭电话-->
				<td><input readonly class='edline' name="customer_h_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=h_tel%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> :</td><!--公司电话-->
				<td><input readonly class='edline' name="customer_o_tel" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=o_tel%>" size="20"></td>
				</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 1:</td><!--手机1-->
				<td><input readonly class='edline' name="customer_mobile" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=mobile%>" size="20"></td>
				<td align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2:</td><!--手机2-->
				<td><input readonly class='edline' name="customer_bp" onkeydown="javascript:nextKeyPress(this);"  maxlength="40" value="<%=bp%>" size="20"></td>
			
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.postAddress",clientLocale)%> :</td><!--联系地址-->
				<td ><INPUT readonly class='edline' name="customer_post_address" size="50" value="<%=post_address%>" onkeydown="javascript:nextKeyPress(this);"></td>
				<td align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</td><!--邮政编码-->
				<td ><INPUT readonly class='edline' name="customer_post_code" size="20" value="<%=post_code%>" onkeydown="javascript:nextKeyPress(this);"></td>
			</tr>
			
			<tr>
				<td colspan="4"><hr size="1"></td>
			</tr> 
		<tr>
			<td align="right">工作进展:</td>
			<td >
				<input readonly class='edline' name="step_flag_name" value="<%=step_flag_name%>">
			</td>							
			<td  align="right">信息等级:</td>
			<td colspan=3>
				<input readonly class='edline' name="info_level_name" value="<%=info_level_name%>">
			</td>	
		</tr>
		<tr>
			<td align="right">客户经理 :</td>
			<td>
				<input readonly class='edline' name="service_man_name" value="<%=service_man_name%>">
			</td>
			<td align="right">执行人 :</td>
			<td>
				<input readonly class='edline' name="executor_name" value="<%=executor_name%>">
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.serviceTime",clientLocale)%> :</td><!--维护时间-->
			<td ><input type="text" readonly class='edline' name="serviceTime" id="serviceTime" size="30" value="<%=serviceTime%>"/></td>
			<td align="right"><%=LocalUtilis.language("class.ServiceProject",clientLocale)%> :</td><!--服务项目-->
			<td ><INPUT type="text" readonly class='edline' name="serviceInfo" size="30" value="<%=serviceInfo%>"></td>
		</tr>
	
		<tr>						
			<td  align="right">交流主题:</td>
			<td colspan=5><input name="subject" readonly class='edline' maxlength="60" size="88" onkeydown="javascript:nextKeyPress(this)" value="<%=subject%>"></td>
		</tr>
		<tr>
			<td align="right" valign="top"><%=LocalUtilis.language("class.serviceSummary",clientLocale)%> :</td><!--服务项目详细描述-->
			<td colspan="3"><textarea rows="3" readonly name="serviceSummary" cols="80"><%=serviceSummary%></textarea>
									<script type="text/javascript">
									var oFCKeditor = new FCKeditor( 'serviceSummary' ) ;
									oFCKeditor.BasePath = '/webEditor/' ;
									//oFCKeditor.ToolbarSet = 'Basic' ;
									oFCKeditor.Width = '90%' ;
									oFCKeditor.Height = '200' ;	
									oFCKeditor.ReplaceTextarea() ;										
									</script>
			</td>
		</tr>
		<tr>
			<td align="right" valign="top">交流内容 :</td>
			<td colspan="3"><textarea rows="3" readonly name="scontent" cols="80"><%=content%></textarea>
									<script type="text/javascript">
									var oFCKeditor = new FCKeditor( 'scontent' ) ;
									oFCKeditor.BasePath = '/webEditor/' ;
									//oFCKeditor.ToolbarSet = 'Basic' ;
									oFCKeditor.Width = '90%' ;
									oFCKeditor.Height = '200' ;	
									oFCKeditor.ReplaceTextarea() ;										
							</script>
			</td>
		</tr>
</table>
</div>
<br>
<div align="right" style="margin-right:5px">
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">关闭 (<u>G</u>)</button>
	<!--关闭-->
</div>
<br>
</form>
</BODY>
</HTML>
<%
custServiceLogLocal.remove();
customer_inst.remove();
customer.remove();
%>