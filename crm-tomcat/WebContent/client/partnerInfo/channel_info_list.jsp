<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//页面传递变量 查询条件
String q_partn_name = Utility.trimNull(request.getParameter("q_partn_name"));
String q_partn_no = Utility.trimNull(request.getParameter("q_partn_no"));
String q_card_id = Utility.trimNull(request.getParameter("q_card_id"));
Integer q_partn_type = Utility.parseInt(Utility.trimNull(request.getParameter("q_partn_type")),new Integer(0));
Integer q_partn_type2_flag = new Integer(1);//渠道类型
String q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.channels",clientLocale);//渠道

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&q_partn_name="+q_partn_name;
tempUrl = tempUrl+"&q_partn_no="+q_partn_no;
tempUrl = tempUrl+"&q_card_id="+q_card_id;
tempUrl = tempUrl+"&q_partn_type="+q_partn_type;
tempUrl = tempUrl+"&q_partn_type2_flag="+q_partn_type2_flag;
sUrl = sUrl + tempUrl;

//页面用辅助变量
int iCount = 0;
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String[] totalColumn = new String[0];

//获得对象
PartnerLocal partnerLocal = EJBFactory.getPartner();
PartnerVO vo = new PartnerVO();

//设置查询条件
vo.setPartn_no(q_partn_no);
vo.setPartn_name(q_partn_name);
vo.setCard_id(q_card_id);
vo.setPartn_type(q_partn_type);
vo.setPartn_type2_flag(q_partn_type2_flag);

//查找信息
IPageList pageList = partnerLocal.pagelist_query(vo,totalColumn,t_sPage,t_sPagesize);

List list = pageList.getRsList();
Map map = null;
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.partnersSet",clientLocale)%> </title><!--合作伙伴设置-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}

/**合作伙伴信息添加**/
function AppendAction(partn_type){
	//1个人 2机构
	if(partn_type ==1){
		var url = "partner_info_action.jsp?actionFlag=1&q_partn_type2_flag=1";
		showModalDialog(url,'','dialogWidth:950px;dialogHeight:450px;status:0;help:0');
		window.location.reload();
	}
	else if(partn_type == 2){
		var url = "partner_info_action2.jsp?actionFlag=1&q_partn_type2_flag=1";
		showModalDialog(url,'','dialogWidth:950px;dialogHeight:450px;status:0;help:0');
		window.location.reload();
	}
}

function ModiAction(partn_type,partn_id){		
	//1个人 2机构
	if(partn_type ==1){
		var url = "partner_info_action.jsp?actionFlag=2&q_partn_type2_flag=1&partn_id="+partn_id;
		showModalDialog(url,'','dialogWidth:1200px;dialogHeight:450px;status:0;help:0');
		window.location.reload();
	}
	else if(partn_type == 2){
		var url = "partner_info_action2.jsp?actionFlag=2&q_partn_type2_flag=1&partn_id="+partn_id;
		showModalDialog(url,'','dialogWidth:950px;dialogHeight:450px;status:0;help:0');
		window.location.reload();
	}
}

/*刷新*/
function refreshPage(){
	QueryAction();
}

/*查询功能*/
function QueryAction(){
	var url = "channel_info_list.jsp?page=1&pagesize="+ document.theform.pagesize.value;	
	var url = url + "&q_partn_name=" + document.getElementById("q_partn_name").getAttribute("value");
	var url = url + "&q_partn_no=" + document.getElementById("q_partn_no").getAttribute("value");
	var url = url + "&q_partn_type=" + document.getElementById("q_partn_type").getAttribute("value");
	var url = url + "&q_card_id=" + document.getElementById("q_card_id").getAttribute("value");
	
	disableAllBtn(true);		
	window.location = url;
}

/*删除功能*/
function DelAction(){
	if(checkedCount(document.getElementsByName("partn_id")) == 0){
		sl_alert("<%=LocalUtilis.language("message.selectRecordsToDelete",clientLocale)%> ！");//请选定要删除的记录
		return false;
	}
	 
	if(sl_check_remove()){			
		var url = "partner_info_del.jsp";
		document.getElementsByName("theform")[0].setAttribute("action",url);
		document.getElementsByName("theform")[0].submit();	
		return true;		
	}
	
	return false;			
}

function ShowAction(partn_type,partn_id){
	var url ;
	if(partn_type ==1)						
		url = "partner_info_show.jsp?q_partn_type2_flag=1&partn_id="+partn_id;
	else if(partn_type == 2)
		url = "partner_info_show2.jsp?q_partn_type2_flag=2&partn_id="+partn_id;

	showModalDialog(url,'','dialogWidth:840px;dialogHeight:450px;status:0;help:0');
}

/**联系人**/
function setContact(partn_id, cust_type){
	var url = "<%=request.getContextPath()%>/client/clientinfo/client_contacts_list.jsp?cust_id="+partn_id+"&cust_type="+cust_type+"&contactType=2";
	window.location.href = url;
}

</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" name="q_partn_type2_flag"	id="q_partn_type2_flag" value="<%= q_partn_type2_flag%>" />
<div id="queryCondition" class="qcMain" style="display:none;width:480px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
		<!-- 要加入的查询内容 -->
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.partnNo",clientLocale)%> ： </td><!--渠道编号-->
			<td  align="left">
					<input type="text" name="q_partn_no" id="q_partn_no" value="<%= q_partn_no%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>	
			<td  align="right"><%=LocalUtilis.language("class.partnName",clientLocale)%> ： </td><!--渠道名称-->
			<td  align="left">
				<input type="text" name="q_partn_name" id="q_partn_name" value="<%= q_partn_name%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
		</tr>
		<tr>
			<td  align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%> ： </td><!--渠道类型-->
			<td  align="left">
				<select name="q_partn_type" id="q_partn_type" onkeydown="javascript:nextKeyPress(this)" style="width:120px">	
						<%=Argument.getCustTypeOptions(q_partn_type)%>
				</select>
			</td>	
			<td  align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> ： </td><!--证件号码-->
			<td  align="left">
				<input type="text" name="q_card_id" id="q_card_id" value="<%= q_card_id%>"onkeydown="javascript:nextKeyPress(this)" style="width:120px">
			</td>		
		</tr>
		
		<tr>
			<td align="center" colspan="4">
				<!--确定-->
                <button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>
				&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>	
	</table>
</div>

<div>
	<div align="left">
		<img border="0" src="<%=request.getContextPath()%>/images/member.gif"  width="32" height="28">
		<font color="#215dc6"><b><%=menu_info%></b></font>
	</div>	
	<div align="right">
		<%if (input_operator.hasFunc(menu_id, 108)) {%>
		<!--查询-->
        <button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title='<%=LocalUtilis.language("message.query",clientLocale)%>' onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		&nbsp;&nbsp;&nbsp;<%}%>
		
		<%if (input_operator.hasFunc(menu_id, 100)) {%>
			<!--新建机构-->
            <button type="button"  class="xpbutton5"  name="btnNew" title='<%=LocalUtilis.language("message.newOrganization",clientLocale)%>' onclick="javascript:disableAllBtn(true);AppendAction(2);"><%=LocalUtilis.language("message.newOrganization",clientLocale)%> </button>
			&nbsp;&nbsp;&nbsp;
			<!--新建个人-->
            <button type="button"  class="xpbutton5" name="btnNew" title='<%=LocalUtilis.language("message.newPersonal",clientLocale)%>' onclick="javascript:disableAllBtn(true);AppendAction(1);"><%=LocalUtilis.language("message.newPersonal",clientLocale)%> </button>
			&nbsp;&nbsp;&nbsp;
		<%}%>
		
		<%if (input_operator.hasFunc(menu_id, 101)) {%>
		<!--删除-->
        <button type="button"  class="xpbutton5" accessKey=d id="btnDel" name="btnDel" title='<%=LocalUtilis.language("message.delete",clientLocale)%>' onclick="javascript:DelAction();"><%=LocalUtilis.language("message.delete",clientLocale)%> (<u>D</u>)</button>
		<%}%>
	</div>	
	<hr noshade color="#808080" size="1" width="100%">
</div>		

<div align="center" >
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
				<td align="center" width="15%">
					<input type="checkbox" 
								name="btnCheckbox" 
								class="selectAllBox"	
								onclick="javascript:selectAllBox(document.theform.partn_id,this);" />
					<%=q_partn_type2_name%><%=LocalUtilis.language("class.ID",clientLocale)%> <!--编号-->
				</td>		
				<td align="center" width="*"><%=q_partn_type2_name%><%=LocalUtilis.language("class.name",clientLocale)%> </td><!--名称-->
				<td align="center" width="8%"><%=q_partn_type2_name%><%=LocalUtilis.language("class.levelID2",clientLocale)%> </td><!--类别-->
				<td align="center" width="10%"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> </td><!--联系方式-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.companyPhone",clientLocale)%> </td><!--公司电话-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.mobile",clientLocale)%> </td><!--手机号码-->
				<td align="center" width="*%"><%=LocalUtilis.language("class.email",clientLocale)%> </td><!--电子邮件-->
				<td align="center" width="8%"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
				<td align="center" width="8%"><%=LocalUtilis.language("class.contact",clientLocale)%> </td><!--联系人-->
			</tr>
<%
//声明字段
Iterator iterator = list.iterator();
Integer partn_id = new Integer(0);
Integer partn_type = new Integer(0);
String card_type = "";
String card_type_name = "";
String touchTypeName = "";
String card_id = "";
String partn_no = "";
String partn_name = "";
String partn_type_name = "";
String o_hel = "";
String mobile = "";
String post_address = "";
String e_mail = "";

while(iterator.hasNext()){
	iCount++;
	map = (Map)iterator.next();

	partn_id = Utility.parseInt(Utility.trimNull(map.get("PARTN_ID")),new Integer(0));
	card_type= Utility.trimNull(map.get("CARD_TYPE"));
	card_type_name = Utility.trimNull(map.get("CARD_TYPE_NAME"));
	card_id = Utility.trimNull(map.get("CARD_ID"));
	partn_no = Utility.trimNull(map.get("PARTN_NO"));
	partn_name = Utility.trimNull(map.get("PARTN_NAME"));
	partn_type_name = Utility.trimNull(map.get("PARTN_TYPE_NAME"));
	o_hel = Utility.trimNull(map.get("O_TEL"));
	mobile = Utility.trimNull(map.get("MOBILE"));
	post_address = Utility.trimNull(map.get("POST_ADDRESS"));
	partn_type = Utility.parseInt(Utility.trimNull(map.get("PARTN_TYPE")),new Integer(0));
	touchTypeName = Utility.trimNull(map.get("TOUCH_TYPE_NAME"));
	e_mail = Utility.trimNull(map.get("E_MAIL"));
%>
	<tr class="tr<%=iCount%2%>">
		  <td align="center">
				<table border="0" width="100%" cellspacing="0" cellpadding="0">
					<tr>
						<td width="10%">			 
								<input type="checkbox" name="partn_id" value="<%=partn_id%>" class="flatcheckbox">
						</td>
						<td width="90%" align="center">&nbsp;&nbsp;<%=partn_no%></td>
					</tr>
				</table>
			</td>
			<td align="left">&nbsp;&nbsp;<a href="#" onclick="javascript:ShowAction(<%=partn_type%>,<%=partn_id%>);" class="a2"><%= partn_name%></a></td>      
			<td align="center"><%= partn_type_name%></td>    
			<td align="center"><%= touchTypeName%></td>    
			<td align="center"><%= o_hel%></td>    
			<td align="center"><%= mobile%></td>     
			<td align="left">&nbsp;&nbsp;<%=e_mail%></td>
			<td align="center">
				<a href="#" onclick="javascript: ModiAction(<%=partn_type%>,<%=partn_id%>); ">
					<img src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" title='<%=LocalUtilis.language("message.edit",clientLocale)%> '/><!--编辑-->
				</a>
			</td>
			<td align="center">
				<a href="#" onclick="javascript: setContact(<%=partn_id%>,<%=q_partn_type2_flag%>); ">
					<img src="<%=request.getContextPath()%>/images/icons_works_views.gif" width="16" height="16" title='<%=LocalUtilis.language("class.contact",clientLocale)%> ' /><!--联系人-->
				</a>
			</td>
	  </tr>         
<%}%>

	<%for(int i=0;i<(t_sPagesize-iCount);i++){%>     	
	         <tr class="tr0">
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>
	            <td align="center"></td>     
	            <td align="center"></td>
	            <td align="center"></td>          
	          	<td align="center"></td>
	          	<td align="center"></td>
	         </tr>           
	<%}%> 
		<tr class="trbottom">
			<!--合计--><!--项-->
            <td class="tdh" align="left" colspan="9"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>	
		</tr>
		</table>
</div>	
<br>
<div>
	&nbsp;<%=pageList.getPageLink(sUrl,clientLocale)%>
</div>	
<%partnerLocal.remove();%>

</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>