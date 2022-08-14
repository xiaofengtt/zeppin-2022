<%@ page contentType="text/html; charset=GBK"  %>
<%@ page import="java.util.*" %>
<%@page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
Integer preproduct_id = Utility.parseInt(request.getParameter("preproduct_id"), new Integer(-1));

ProductLocal product = EJBFactory.getProduct();
boolean bSuccess = false ;

ProductVO vo = new ProductVO();
vo.setPreproduct_id(preproduct_id);
vo.setInput_man(input_operatorCode);

//删除
if (request.getMethod().equals("POST")) {
	String[] s = request.getParameterValues("s_id");
	if (s != null)	{
		for(int i = 0;i < s.length; i++) {
			Integer serial_no = Utility.parseInt(s[i], new Integer(0));
			vo.setSerial_no(serial_no);
			product.delPreproductProv(vo);
		}
		bSuccess = true;
	}
}

IPageList pagelist = product.queryPreproductProv(vo, 1, -1) ;
List list = pagelist.getRsList();

//sUrl += "&preproduct_id="+preproduct_id;

String qs = Utility.getQueryString(request, new String[]{"page", "pagesize", "status", "class1", "open"});

product.remove();
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
<title>收益级别设置</title>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<SCRIPT language="javascript">
<%if(bSuccess){%>
	sl_alert("删除成功！");
	location.href = "preproduct_gainlevel_list.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>";
<%}%>

/*
function refreshPage(){
	location.search = "?preproduct_id=<%=preproduct_id%>&page=0&pagesize=" + document.theform.pagesize.value;	
}*/

//新增
function newInfo() {
	location.href = 'preproduct_gainlevel_set.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>';
}

function edit(serial) {
	location.href = 'preproduct_gainlevel_set.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>&serial_no='+serial;
}

/*
//收益率设置
function rateSet(serial_no, preproduct_id, prov_flag , 
			prov_level_name , lower_limit,upper_limit,summary,gain_flag ,cust_typename,productName){
	//主页查询条件
	var iQuery = document.theform.preproduct_id.value + "$" + "<%=sPage%>" + "$" + document.theform.pagesize.value;
	location = 'gainlevel_rate_set.jsp?df_serial_no='+serial_no + '&preproduct_id='+ preproduct_id +'&productName='+productName+
				'&prov_flag=' + prov_flag + '&prov_level_name='+prov_level_name +
				'&lower_limit='+lower_limit + '&upper_limit='+upper_limit + '&summary='+summary +'&gain_flag='+gain_flag+'&cust_typename='+cust_typename+'&iQuery='+iQuery;
}*/

//删除
function confirmRemove(serial_no,flag) {	
	if (document.theform.s_id==null) {
		sl_alert("没有需要删除的记录！");
		return false;
	}
		
	if(checkedCount(document.theform.s_id) == 0) {
		sl_alert("请选定要删除的记录！");
		return false;
	}
	
	if(confirm('您确定选定的记录删除吗？')) {	
		disableAllBtn(true);
		document.theform.submit();
	}	
}

function back() {
	location.href = "product_info.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>";
}
</SCRIPT>
</HEAD>
<BODY class="BODY body-nox"><%@ include file="/includes/waiting.inc"%> 
<form name="theform" action="preproduct_gainlevel_list.jsp?preproduct_id=<%=preproduct_id%>&<%=qs%>" method="post">
<%--div id="queryCondition" class="qcMain" style="display:none;width:450px;">
<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
	<tr>
		<td>查询条件：</td>
	   	<td align="right">
	   		<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
	   	</td>
  	</tr>
</table>
<table border="0" width="100%" cellspacing="2" cellpadding="2">	
	<tr>
    	<td align="right">产品编号:</td >
        <td align="left" >
        	<input type="text" maxlength="16" name="productid" value="" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="13">&nbsp;
        	<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
        </td>
		<td align="right">产品名称:</td>
		<td>
			<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
			<button type="button"  class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
		</td>
    </tr>
	<tr>
	    <td align="right">产品选择:</td >
	    <td align="left" colspan="3" id="select_id">
	       <SELECT name="product_id" onkeydown="javascript:nextKeyPress(this)" class="productname" onchange="javascript:getSubProductOptions(this.value,'');"  style="width: 335px;">
	       	<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48)%></SELECT>
	    </td>
	</tr>
	<tr id="subProduct_style" style="display:none;">
		<td align="right">子产品名称:</td>
		<td colspan="3" id="subProductOptions">

		</td>
	</tr>
	<tr>
    	<td align="right">优先级:</td >
        <td align="left">
			<SELECT name="prov_flag" style="width:120px;">
				<OPTION  value="0" >请选择</OPTION>
				<%=Argument.getTableOptions2(3003,new Integer(0))%>
			</SELECT>
        </td>
        <td align="right">收益级别:</td>
		<td>
			<SELECT name="prov_level" onkeydown="javascript:nextKeyPress(this)" style="width:120px;">
			  	<%=Argument.getProvlevelOptions("1204")%>
		    </SELECT>
		</td>
    </tr>	
	<tr>						
		<td align="right" colspan="3">
			<button type="button"  class="xpbutton3" accessKey=s name="btnQuery"onclick="javascript:StartQuery();">确定(<u>S</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;								
		</td>
	</tr>					
</table>
</div--%>

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
								<td class="page-title"><b><%=menu_info%></b></td>
								<td>
							</tr>
							<tr>
							<td align="right" >
							<div class="btn-wrapper">
								<%--button class="xpbutton3" accessKey=q id="queryButton" name="queryButton">查询(<u>Q</u>)</button--%>
								&nbsp;&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=n id="btnNew" name="btnNew"	title="新建记录" onclick="javascript:newInfo();">新建(<u>N</u>)</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=d id="btnDelete"name="btnDelete" title="删除所选记录"onclick="javascript:if(confirmRemove(document.theform.serial_no,1)) document.theform.submit();">删除(<u>D</u>)</button>
								&nbsp;&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=r id="btnBack"name="btnBack" title="返回"onclick="javascript:back();">返回(<u>R</u>)</button>
								</div>
								</td>
							</tr>
							
						</table>
<br/>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
							<tr class="trh">
								<td align="center" height="25" width="25px">
                                	<input type="checkbox" name="btnCheckbox" class="selectAllBox"  onclick="javascript:selectAllBox(document.theform.s_id,this);">
                                </td>
								<td align="center" height="25">产品名称</td>	
								<td align="center" height="25">优先级</td>	
								<td align="center" height="25">收益级别</td>
								<td align="center" height="25">收益率(%)</td>
								<td align="center" height="25">份额下限</td>
								<td align="center" height="25">份额上限</td>
								<%-- %>td align="center" height="25">收益率调整</td--%>								
								<td align="center" height="25" width="8%">编辑</td>
							</tr>

<%
int iCount = 0;
int iCurrent = 0;

for (int i=0; i<list.size(); i++) {
	Map map = (Map)list.get(i);

	String prov_flag_name = "" ;
	Integer prov_flag = (Integer)map.get("PROV_FLAG");
	if (prov_flag.intValue()==1)
		prov_flag_name = "优先";
	else if (prov_flag.intValue()==2)
		prov_flag_name = "一般";
	else if(prov_flag.intValue()==3)
		prov_flag_name = "劣后";	
 %>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" align="center" width="25px">
									<input type="checkbox" name="s_id" value="<%=map.get("SERIAL_NO")%>" class="flatcheckbox"/>					
								</td>
								<td align="left"><%=Utility.trimNull(map.get("PRODUCT_NAME"))%></td> 
								<td width="" align="center" ><%=prov_flag_name%></td>
								<td width="" align="center" ><%=map.get("PROV_LEVEL_NAME")%></td>
								<td width="" align="right" ><%=Utility.parseBigDecimal((BigDecimal)map.get("GAIN_RATE"),new BigDecimal(0.00)).multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP) %></td>
								<td width="" align="right" ><%=Format.formatMoney2((BigDecimal)map.get("LOWER_LIMIT"))%></td>
								<td width="" align="right" ><%=Format.formatMoney2((BigDecimal)map.get("UPPER_LIMIT"))%></td>
								<%-- %>td width="" align="center" > 
									<button type="button"  class="xpbutton2" accessKey=b id="btnCancel" name="btnCancel" name="btnEdit" title="收益率设置" 
									onclick="javascript:rateSet('<%=map.get("SERIAL_NO") %>',
																'<%=map.get("PREPRODUCT_ID") %>',						
																'<%=map.get("PROV_FLAG") %>', 
																'<%=map.get("PROV_LEVEL_NAME") %>',
																'<%=map.get("LOWER_LIMIT") %>',
																'<%=map.get("UPPER_LIMIT") %>',
																'<%=Utility.trimNull(map.get("SUMMARY"))%>',
																'<%=Utility.trimNull(map.get("PRODUCT_NAME"))%>');">>></button>
								</td--%>
								<td width="" align="center" >
									<button type="button"  class="xpbutton2" accessKey=b id="btnCancel" name="btnCancel" name="btnEdit"
												title="修改记录" onclick="javascript:disableAllBtn(true);edit(<%=map.get("SERIAL_NO")%>);">>></button>
								</td>
							</tr>
<%
	iCount++ ;
	iCurrent++ ;
} 

//for (; iCurrent < pagelist.getPageSize(); iCurrent++) {%>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td class="tdh" width="25px" align="center"></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<td width="" align="center" ></td>
								<%-- %>td width="" align="center" ></td--%>
								<td width="" align="center" ></td>
							</tr>
<%//}%>
						</table>
						<p></p>

						<%-- %>table border="0" width="100%">
							<tr valign="top">
								<td><%=pagelist.getPageLink(sUrl) %></td>
							</tr>
						</table--%>
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