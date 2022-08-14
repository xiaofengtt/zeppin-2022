<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@page import="enfo.crm.customer.CustomerLocal"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0));
Integer product_id = Utility.parseInt(request.getParameter("product_id"), new Integer(0));//产品ID
Integer group_id = Utility.parseInt(request.getParameter("group_id"), new Integer(0));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String cust_no = Utility.trimNull(request.getParameter("cust_no"));
CustomerLocal customer = EJBFactory.getCustomer();
CustomerVO customerVO = new CustomerVO();
customerVO.setCust_name(cust_name);
customerVO.setCust_no(cust_no);
customerVO.setProduct_id(product_id);
customerVO.setGroupID(group_id);
customerVO.setInput_man(input_operatorCode);
List customerList = customer.listProcAllExt(customerVO);

ActivityLocal activity = EJBFactory.getActivity();
List listActivity = activity.queryByListCustomerInfo(serial_no);
%>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<BASE TARGET="_self">
<title><%=LocalUtilis.language("menu.addActivity",clientLocale)%> </title>
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
	/*设置产品*/
	function setProduct(value)
	{
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
				sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
				document.theform.productid.value="";
				document.theform.product_id.options[0].selected=true;	
			}			
		}
		nextKeyPress(this);
	}
	
	/*查询产品*/
	function searchProduct(value)
	{
		prodid=0;
		if (value != "")
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
				sl_alert('<%=LocalUtilis.language("message.idIsNotExists",clientLocale)%> ！');//输入的产品编号不存在
				document.theform.productid.value="";
				document.theform.product_id.options[0].selected=true;	
			}
			document.theform.product_id.focus();					
		}	
	}
	
	function refreshButtons()
	{
		document.theform.btnAdd.disabled = (document.theform.availableMenu.selectedIndex == -1);
		document.theform.btnAddAll.disabled = (document.theform.availableMenu.length == 0);
	
		document.theform.btnRemove.disabled = (document.theform.currentMenu.selectedIndex == -1);
		document.theform.btnRemoveAll.disabled = (document.theform.currentMenu.length == 0);
	}
	
	function addMenu(index, noSort)
	{
		if(index != -1)
		{
			var opt = document.theform.availableMenu.options[index];
			document.theform.currentMenu.add(document.createElement("OPTION"));
			document.theform.currentMenu.options[document.theform.currentMenu.length - 1].text = opt.text;
			document.theform.currentMenu.options[document.theform.currentMenu.length - 1].value = opt.value;
			document.theform.availableMenu.remove(index);
			if(index < document.theform.availableMenu.length)
				document.theform.availableMenu.selectedIndex = index;
		}
		if (!noSort)
		{
			sortOptions(document.theform.currentMenu);
			refreshButtons();
		}
	}
	
	function addMultiMenu()
	{
		var items = new Array();
		var index = 0;
		for(var i = 0; i < document.theform.availableMenu.length; i++)
			if(document.theform.availableMenu.options[i].selected)
				items[index++] = i;
		for(i = items.length - 1; i >= 0; i--)
			addMenu(items[i]);
	}
	
	function addAllMenu()
	{
		for(var i = document.theform.availableMenu.length - 1; i >= 0; i--)
			addMenu(i, true);
	//	sortOptions(document.theform.currentMenu);
		refreshButtons();
	}
	
	
	
	function removeMenu(index, noSort)
	{
		if(index != -1)
		{
			var opt = document.theform.currentMenu.options[index];
			document.theform.availableMenu.add(document.createElement("OPTION"));
			document.theform.availableMenu.options[document.theform.availableMenu.length - 1].text = opt.text;		
			document.theform.availableMenu.options[document.theform.availableMenu.length - 1].value = opt.value;
			document.theform.currentMenu.remove(index);
			if(index < document.theform.currentMenu.length)
				document.theform.currentMenu.selectedIndex = index;
		}
		if (!noSort)
		{
			//sortOptions(document.theform.availableMenu);
			refreshButtons();
		}
	}
	
	function removeMultiMenu()
	{
		var items = new Array();
		var index = 0;
		for(var i = 0; i < document.theform.currentMenu.length; i++)
			if(document.theform.currentMenu.options[i].selected)
				items[index++] = i;
		
		for(i = items.length - 1; i >= 0; i--)
			removeMenu(items[i]);
	}
	
	function removeAllMenu()
	{
		for(var i = document.theform.currentMenu.length - 1; i >= 0; i--)
			removeMenu(i, true);
		//sortOptions(document.theform.availableMenu);
		refreshButtons();
	}
	
	function queryCustomerinfo()
	{
		document.theform.method = "get";
		document.theform.submit();
	}
	
	function submitCustomerinfo()
	{
		var len = document.theform.currentMenu.length;
		var ret = "";
		for(var i = 0; i < len; i++)  
		ret+= document.theform.currentMenu.options[i].value+'$';
		ret=ret.substring(0,ret.length-1);
	
		window.returnValue = ret;
		window.close();
	}
	
</script>
</head>
  <body class="BODY body-nox">
  	<form name="theform" method="post" action="activity_custominfo_query.jsp">
  		<table width="100%">
  			<tr>
				<td align="right"><%=LocalUtilis.language("class.productID",clientLocale)%> :<!--产品编号--></td>
				<td>	
					<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);" maxlength=8
						   size="10" onkeydown="javascript:nextKeyPress(this)"> &nbsp;
					<button type="button"  class="searchbutton" onclick="javascript:searchProduct(document.theform.productid.value);" />	
				</td>
			</tr>
			<tr>
				<td align="right"><%=LocalUtilis.language("class.productName",clientLocale)%> :</td><!--产品名称-->
				<td colspan=3 align="left">
					<select size="1" name="product_id" class="productname" onkeydown="javascript:nextKeyPress(this)" style="width: 220px;">
					<%=Argument.getProductListOptions(new Integer(1), product_id, "",input_operatorCode,status)%>
					</select>
				</td>
			</tr>
			<tr>
				<td valign="bottom" align="right"><%=LocalUtilis.language("class.custGroups",clientLocale)%> :</td><!--客户群组-->
				<td align="left" colspan="3">
					<select name="group_id" id="group_id" onkeydown="javascript:nextKeyPress(this)" style="width: 220px;">
						<%=Argument.getCustGroupOption2(new Integer(0),group_id)%>
					</select>
				</td>
			</tr>
			<tr>
				<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerID",clientLocale)%> :</td><!--客户编号-->
				<td valign="bottom" align="left"><input type="text" name="cust_no"
					onkeydown="javascript:nextKeyPress(this)"
					value="<%=cust_no%>" size="25">
				</td>
				<td valign="bottom" align="right"><%=LocalUtilis.language("class.customerName",clientLocale)%> :</td><!--客户名称-->
				<td valign="bottom" align="left"><input type="text" maxlength="100"
					name="cust_name" onkeydown="javascript:nextKeyPress(this)"
					value="<%=cust_name%>" size="25">
				</td>	
			</tr>
			
			<tr>
				<td colspan="4" align="right">
					<!-- 查询 -->
					 <button type="button"  class="xpbutton5" accessKey="q" id="queryButton" name="queryButton" onclick="javascript:queryCustomerinfo();">
					 	<%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="4" >
				<hr noshade color="#808080" size="1">
				</td>
			</tr>
  		</table>
  		<table>
  		<tr>
			<td align="right"><%=LocalUtilis.language("class.customerchange",clientLocale)%>  &nbsp;</td><!-- 客户选择 -->
			<td width="100%">
				<TABLE>
					<TBODY>
						<TR  >
							<TD height="224">
							
							<SELECT size="16" name="availableMenu" multiple style="width: 250px"
									onchange="javascript: refreshButtons();"
								ondblclick="javascript:addMenu(document.theform.availableMenu.selectedIndex);">
<%
for(int i=0;i<customerList.size();i++){
	Map map = (Map)customerList.get(i);
 %>						
						<option value="<%=Utility.trimNull(map.get("CUST_ID")) %>"><%=Utility.trimNull(map.get("CUST_NAME")) %></option>
<%} %>						  
                          </SELECT></td>
								<TD height="224" align="center">
								<TABLE>
									<TBODY>
										<TR>
											<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnAdd" name="btnAdd" onclick="javascript:addMultiMenu();">&gt;</button></TD>
										</TR>
										<TR>
											<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnMultiAdd" name="btnAddAll" onclick="javascript:addAllMenu();">&gt;&gt;</button></TD>
										</TR>
										<TR>
											<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnRemove" name="btnRemove" onclick="javascript:removeMultiMenu();">&lt;</button></TD>
										</TR>
										<TR>
											<TD width="98" align="center" valign="middle"><button type="button"  class="xpbutton2" id="btnMultiRemove" name="btnRemoveAll" onclick="javascript:removeAllMenu();">&lt;&lt;</button></TD>
										</TR>
									</TBODY>
								</TABLE>
								</TD>
								<TD  align="right">
								<select size="16" name="currentMenu" multiple style="width:250px" onchange="javascript: refreshButtons();" ondblclick="javascript:removeMenu(document.theform.currentMenu.selectedIndex);">
<%for(int k=0;k<listActivity.size();k++){ 
	Map map = (Map)listActivity.get(k);
	Integer cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
%>
									<option value="<%=Utility.trimNull(map.get("CUST_ID")) %>">
										<%=Argument.getCustomerName(cust_id,input_operatorCode) %>
									</option>
<%} %>
								</select>
								</TD>
							</TR>
						</TBODY>
					</TABLE>
			</td>
		</tr>
		<tr>
			<td colspan="4" align="right">
				<!-- 保存 -->
				 <button type="button"  class="xpbutton3" accessKey="s" id="queryButton" name="queryButton" onclick="javascript:submitCustomerinfo();">
				 	<%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>&nbsp;&nbsp;&nbsp;
				 <!-- 取消 -->	
				 <button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
			</td>
		</tr>
  		</table>
  	</form>
  </body>
</html>
