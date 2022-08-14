<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%

//页面传递变量 getTransactionCustomer2 方法传入信息
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0)); 
String prefix = Utility.trimNull(request.getParameter("prefix"));
String task=Utility.trimNull(request.getParameter("task"));//操作任务
int readonly=Utility.parseInt(Utility.trimNull(request.getParameter("readonly")),0);
Integer select_flag = Utility.parseInt(Utility.trimNull(request.getParameter("select_flag")), new Integer(1)); 
int modi_flag = Utility.parseInt(request.getParameter("modi_flag"),0);
int pos_flag = Utility.parseInt(request.getParameter("pos_flag"),0);
if (pos_flag==0){//新选择客户时，先清除session中的客户信息
	session.removeAttribute("card_id");
	session.removeAttribute("sex");
	session.removeAttribute("birth");
	session.removeAttribute("address");
	session.removeAttribute("name");
	session.removeAttribute("card_type");
	session.removeAttribute("issueDate");
	session.removeAttribute("period");
	session.removeAttribute("issuePlace");
	session.removeAttribute("nation");
}
//客户选择 条件信息
String q_cust_no = Utility.trimNull(request.getParameter("q_cust_no"));
String q_cust_name = Utility.trimNull(request.getParameter("q_cust_name"));
String q_card_id = Utility.trimNull(request.getParameter("q_card_id"));
String q_vip_card_id = Utility.trimNull(request.getParameter("q_vip_card_id"));
String q_hgtzr_bh = Utility.trimNull(request.getParameter("q_hgtzr_bh"));
Integer q_is_deal = Utility.parseInt(request.getParameter("q_is_deal"), new Integer(0));
Integer q_is_link = Utility.parseInt(request.getParameter("q_is_link"), new Integer(0));
Integer q_true_flag = Utility.parseInt(request.getParameter("q_true_flag"), new Integer(0));

//帐套暂时设置
input_bookCode = new Integer(1);

//辅助变量
StringBuffer list = Argument.newStringBuffer();
String sReadonly = "readonly class='edline'";
String sDisabled= "disabled='disabled'";
Map map = null;
List rsList = null;//返回结果集

//获取对象
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO vo = new CustomerVO();

if(task.equals("list")){
	// 搜索功能
	vo.setBook_code(new Integer(1));
	vo.setInput_man(input_operatorCode);
	vo.setCust_no(q_cust_no);
	vo.setCust_name(q_cust_name);
	vo.setCard_id(q_card_id);
	vo.setVip_card_id(q_vip_card_id);
	vo.setHgtzr_bh(q_hgtzr_bh);
	vo.setIs_link(q_is_link);
	vo.setIs_deal(q_is_deal);	
	vo.setTrue_flag(q_true_flag);
	rsList = customer.listProcAllExt(vo);	
}
%>
<HTML>
<HEAD>
	<TITLE><%=LocalUtilis.language("menu.customerInfo2",clientLocale)%></TITLE><!--客户信息查询-->
	<BASE TARGET="_self">
	<META http-equiv=Content-Type content="text/html; charset=gbk">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

	<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
	
	<SCRIPT LANGUAGE="javascript">
	var user_id = <%=user_id%>;

	/*查询信息*/
	function queryInfo(){
		document.theform.cust_id.value = "";
		document.theform.method = "get";
		document.theform.task.value = "list";
		document.theform.action = "customer_detail_easy.jsp";
		disableAllBtn(true);
		document.theform.submit();
	}

	function changeCustomer(){	
		var dataValue = document.theform.id.value.split("$");
		document.theform.cust_id.value = dataValue[0];
		document.theform.modi_flag.value = dataValue[1];
		document.theform.method = "get";
		document.theform.task.value = "list";
		document.theform.action = "customer_detail_easy.jsp";
		disableAllBtn(true);
		document.theform.submit();
	}

	/*保存信息*/
	function saveAction(){		
		var odt_obj = document.getElementById("odt_cust_id");
		odt_obj = odt_obj[odt_obj.selectedIndex];
		var obj = eval("("+odt_obj.getAttribute("odt")+")");
		window.returnValue = obj;		
		window.close();
	}

	/*改变显示参数*/
	function changeAction(){
		var v_cust_id = document.theform.v_cust_id.value;
		document.getElementsByName("sonIframe")[0].src="customerInfo_details_view.jsp?select_flag=<%=select_flag%>&cust_id="+v_cust_id;
	}

	//新建
	function newInfo(){
		if (user_id==22) { // 22中投
			window.dialogHeight = "700px";
			document.getElementById("reload").click();			
			return;
		}

		document.getElementsByName("cust_id")[0].value= "" ;		
		if (document.getElementById("btnUpdate")) document.getElementById("btnUpdate").style.visibility="hidden";		
		document.getElementsByName("sonIframe")[0].src="customerInfo_details_action.jsp?select_flag=<%=select_flag%>";
	}

	//通过证件自动识别 新建
	function newInfo2(){
		document.getElementsByName("cust_id")[0].value= "" ;
		if (document.getElementById("btnUpdate")) document.getElementById("btnUpdate").style.visibility="hidden";		
		document.getElementsByName("sonIframe")[0].src="customerInfo_details_action.jsp?select_flag=<%=select_flag%>&pos_flag=<%=pos_flag%>";
	}
	
	//修改
	function updateInfo(){
		var v_cust_id = document.theform.v_cust_id.value;
		var url = "customerInfo_details_action.jsp?select_flag=<%=select_flag%>&cust_id="+v_cust_id;
		document.getElementsByName("sonIframe")[0].src= url;
	}

	//更新证件附件
	function uploadCard(){
		var v_cust_id = document.theform.v_cust_id.value;
		var url = '/marketing/customer_card_update.jsp?upload_flag=1&cust_id='+ v_cust_id ;	
		var v = showModalDialog(url,'','dialogWidth:720px;dialogHeight:500px;status:0;help:0;');
		if(v == 1)
			document.getElementsByName("sonIframe")[0].src="customerInfo_details_view.jsp?cust_id="+v_cust_id;
	}
	</SCRIPT>
</HEAD>

<BODY onload="javascript:document.all.q_cust_name.focus();">
<a id="reload" href="cust_new.jsp?op=init" style="display:none"></a>
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="post">
<input type="hidden" name="prefix" value="<%=Utility.trimNull(prefix)%>">
<input type="hidden" name="task" value="<%=task%>">
<input type="hidden" name="is_dialog" value="1">
<input type="hidden" name="readonly" value="<%=readonly%>">
<input type="hidden" name="cust_id" value="<%=cust_id%>">
<input type="hidden" name="select_flag" value="<%=select_flag%>">
<input type="hidden" name="modi_flag" value="<%=modi_flag %>"/>
<fieldset style="border:2px groove #f00; background-color:;">
	<legend style="background-color:white; margin-left:20px;" <%if(readonly==1) out.print("style='display:none'");%>>查询条件</legend>
	<table border="0" width="100%" cellspacing="2" cellpadding="2"  >
		
		
		<tr <%if(readonly==1) out.print("style='display:none'");%>>
			<td align=right><%=LocalUtilis.language("class.customerID",clientLocale)%> :&nbsp;&nbsp;</td><!--客户编号-->
			<td>
				<input onkeydown="javascript:nextKeyPress(this)" 
				       type="text" name="q_cust_no" size="25" value="<%=q_cust_no%>">
			</td>
			<td align=right><%=LocalUtilis.language("class.customerVipCardId",clientLocale)%> :&nbsp;&nbsp;</td><!--VIP卡编号-->
			<td>
				<input onkeydown="javascript:nextKeyPress(this)" 
				       type="text"  name="q_vip_card_id" size="25" value="<%=q_vip_card_id%>">
		    </td>
		</tr>
		
		<tr <%if(readonly==1) out.print("style='display:none'");%>>
			<td align="right"><%=LocalUtilis.language("class.custType",clientLocale)%> :&nbsp;&nbsp;</td><!--客户类型-->
			<td>
				<select name="q_is_deal" id="q_is_deal" onkeydown="javascript:nextKeyPress(this)" style="width:200px">	
					<%=Argument.getWTCustOptions(q_is_deal)%>
				</select>
			</td>
			<td align=right><%=LocalUtilis.language("class.customerHgtzrBh",clientLocale)%> :&nbsp;&nbsp;</td><!--合格投资人编号-->
			<td>
				<input onkeydown="javascript:nextKeyPress(this)" 
				       type="text"  name="q_hgtzr_bh" size="25" value="<%=q_hgtzr_bh%>">
			</td>
		</tr>		
		<tr <%if(readonly==1) out.print("style='display:none'");%>>
			<td align=right><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :&nbsp;&nbsp;</td><!--证件号码-->
			<td>
				<input onkeydown="javascript:nextKeyPress(this)" type="text" name="q_card_id" size="25"  value="<%=q_card_id%>" >
			</td>
			<td align=right><%=LocalUtilis.language("class.isLink",clientLocale)%> :&nbsp;&nbsp;</td><!--是否关联方-->
			<td>
				<input class="flatcheckbox" onkeydown="javascript:nextKeyPress(this)" 
				       type="checkbox"  name="q_is_link" value="1" <%if(q_is_link.intValue()==1) out.print("q_is_link");%> />				
			</td>
		</tr>
		<tr <%if(readonly==1) out.print("style='display:none'");%>>
			<td align=right><%=LocalUtilis.language("class.customerName",clientLocale)%> :&nbsp;&nbsp;</td><!--客户名称-->
			<td>			
				<input onkeydown="javascript:nextKeyPress(this);" type="text"  name="q_cust_name" size="25" value="<%=q_cust_name%>"/>
			</td>
			<td align=right>客户真实性 :&nbsp;&nbsp;</td>
			<td>
				<select name="q_true_flag" onkeydown="javascript:nextKeyPress(this)"><%=Argument.getCustInfoTrueFlagList(q_true_flag)%></select>
			</td>
		</tr>
		<tr <%if(readonly==1) out.print("style='display:none'");%>>
			<td colspan="4" align="right"  >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button class="xpbutton3" accessKey=q onclick="javascript:queryInfo();"><%=LocalUtilis.language("message.search",clientLocale)%> (<u>Q</u>)</button>	&nbsp;&nbsp;
				<!--搜索-->
			<%if (user_id.intValue()!=15){//建信不能对客户新建/修改 %>
				<% if (input_operator.hasFunc(menu_id, 121)){ %>
				<button class="xpbutton3" accessKey=n id="btnNew" name="btnNew" onclick="javascript:newInfo();"><%=LocalUtilis.language("message.new",clientLocale)%> (<u>N</u>)</button>&nbsp;&nbsp;
				<!--新建-->
				<%} %>
			<%if(cust_id.intValue()>0 && modi_flag==1 && input_operator.hasFunc(menu_id, 122)){%>
				<button class="xpbutton4" onclick="javascript:uploadCard();" <%if(user_id.intValue() != 2 ){ %> style="display:none"<%} %>>更新证件附件</button>
				&nbsp;&nbsp;&nbsp;
				<button class="xpbutton3" accessKey=u id="btnUpdate" name="btnUpdate" onclick="javascript:updateInfo();"><%=LocalUtilis.language("message.update",clientLocale)%> (<u>U</u>)</button>&nbsp;&nbsp;
				<!--修改-->
			<%}
			}%>&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</fieldset>
<br>
<fieldset style="border:2px groove #f00;">
	<legend style="background-color:white; margin-left:20px;"><%=LocalUtilis.language("message.searchResults",clientLocale)%> </legend>
<div align="left" style="margin-left:40px;margin-top:10px;<%if (sReadonly.equals("") || readonly==1 && cust_id.intValue()>0) out.print("display:none;");%>">			
		<select onkeydown="javascript:nextKeyPress(this)" id="odt_cust_id"  name="id" style="width:550px;" onchange="javascript:changeCustomer();">
			<option value=""><%=LocalUtilis.language("message.pleaseSelect",clientLocale)%> </option><!--请选择-->
				<%if ("list".equals(task)){
					Iterator iterator = rsList.iterator();
					
					while(iterator.hasNext()){	
						map = (Map)iterator.next();										
						Integer r_cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")), new Integer(0)); 
						String r_cust_name = Utility.trimNull(map.get("CUST_NAME"));
						String r_card_type_name = Utility.trimNull(map.get("CARD_TYPE_NAME"));
						String r_card_id = Utility.trimNull(map.get("CARD_ID"));
						modi_flag = Utility.parseInt(Utility.trimNull(map.get("MODI_FLAG")),0);//1 允许修改 , 2 不允许修改	
				%>
					<option odt="{'cust_id':'<%=r_cust_id%>','cust_name':'<%=r_cust_name%>'}" value="<%=r_cust_id%>$<%=modi_flag %>" <% if(r_cust_id.equals(cust_id)){%>selected<%}%>>							
						<%= (r_cust_name+"-"+r_card_type_name+"-"+r_card_id)%>	
					</option>
				<%
					}					
				}
				%>
		</select>	
</div>
</fieldset>	
<br>
<fieldset style="border:2px groove #f00;">
	<legend style="background-color:white; margin-left:20px;">客户信息</legend>
<!--子窗口-->
<iframe src ="customerInfo_details_view.jsp?cust_id=<%=cust_id%>&select_flag=<%=select_flag%>" name="sonIframe" frameborder="no" border="0" 
	height="800" width="700" scrolling="yes"></iframe>
	
</fieldset>

<!--子窗口 和 父窗口 的 联系节点-->
<input type="button" style="visibility:hidden;" name="saveButton" onclick="javascript:saveAction()"/>
<input type="button" style="visibility:hidden;" name="changeButton" onclick="javascript:changeAction()"/>
<input type="hidden" name="resultValue" value=""/>
<input type="hidden" name="v_cust_id" value="<%=cust_id%>"/>
<SCRIPT LANGUAGE="javascript">
<% if (pos_flag==2){
%>
	//新建
	newInfo2();
	//从session中给表单赋值
	document.theform.q_cust_name.value='<%=Utility.trimNull(session.getAttribute("name"))%>';
	document.theform.q_card_id.value='<%=Utility.trimNull(session.getAttribute("card_id"))%>';
<%}%>
</SCRIPT>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>