<%@ page contentType="text/html; charset=GBK" %>
<%@page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*"%>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
try{
sPage = request.getParameter("page");
sPagesize = request.getParameter("pagesize");
String iQuery = request.getParameter("iQuery");
Integer df_serial_no = Utility.parseInt(request.getParameter("df_serial_no"),new Integer(0));	
Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));	
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));
int prov_flag = Utility.parseInt(request.getParameter("prov_flag"),new Integer(0)).intValue();	
int gain_flag2 = Utility.parseInt(request.getParameter("gain_flag"),new Integer(0)).intValue();
String cust_typename = Utility.trimNull(request.getParameter("cust_typename"));
String colDisplayStr = "style='display:none;'";
if(gain_flag2==2){
	colDisplayStr = "style='display:;'";
}
	
String prov_flag_msg = "" ;
if(prov_flag==1){
	prov_flag_msg="优先";
}else if(prov_flag==2){
	prov_flag_msg = "一般" ;
}else if(prov_flag==3){
	prov_flag_msg="劣后" ;
}
String productName = Utility.trimNull(request.getParameter("productName"));
//pro_name += Argument.getProductName(product_id)==null?"":Argument.getProductName(product_id);
//pro_name += Argument.getSubproductName(sub_product_id)==null?"":"("+Argument.getSubproductName(sub_product_id)+")" ;
String prov_level_name = Utility.trimNull(request.getParameter("prov_level_name"));
BigDecimal lower_limit = Utility.parseDecimal(Utility.trimNull(request.getParameter("lower_limit")),new BigDecimal(0));
BigDecimal upper_limit = Utility.parseDecimal(Utility.trimNull(request.getParameter("upper_limit")),new BigDecimal(0));
String summary = request.getParameter("summary");

Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),new Integer(0)) ;
int check_flag = Utility.parseInt(request.getParameter("check_flag"),-1);

GainLevelRateLocal local = EJBFactory.getGainLevelRate() ; 
GainLevelRateVO vo = new GainLevelRateVO();
boolean bSuccess = false ;
//删除
if (request.getMethod().equals("POST") && check_flag==1)
{
	String[] s = request.getParameterValues("s_id");
	if (s != null)
	{
		for(int i = 0;i < s.length; i++)
		{		    	
			serial_no = Utility.parseInt(s[i], new Integer(0));			
			vo.setSerial_no(serial_no);
			vo.setInput_man(input_operatorCode);
			
			local.del(vo);
		}
		bSuccess = true;
	}
}

vo.setDf_serial_no(df_serial_no) ;
vo.setSerial_no(serial_no) ;
List list = local.query(vo) ;
ProductLocal pLocal =EJBFactory.getProduct();
ProductVO pVo = new ProductVO();
pVo.setProduct_id(product_id);
List plist = pLocal.queryProductLimit(pVo);
Integer gain_flag = new Integer(0);
if(plist.size()>0)
{
	Map pMap = (Map)plist.get(0);
	gain_flag = Utility.parseInt(Utility.trimNull(pMap.get("GAIN_FLAG")),new Integer(0));
	pLocal.remove();
}



sUrl = "gainlevel_rate_set.jsp?pagesize=" + sPagesize  ;
 %>

<HTML>
<HEAD>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<base target="_self">
<title>收益率设置</title>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script language=javascript>
<%if(bSuccess){%>
	sl_alert("删除成功！");
	var urll = 'gainlevel_rate_set.jsp?iQuery=<%=iQuery%>&df_serial_no=<%=df_serial_no %>&product_id=<%=product_id %>&sub_product_id=<%=sub_product_id %>&prov_flag=<%=prov_flag%>&prov_level_name=<%=prov_level_name%>&lower_limit=<%=lower_limit%>&upper_limit=<%=upper_limit%>&summary=<%=summary%>';
location = urll;
<%}%>

window.onload = function(){
	//initQueryCondition()
};

function refreshPage(){
	document.theform.btnQuery.disabled = true;
	disableAllBtn(true);
	var url = 'gainlevel_rate_set.jsp?page=<%=sPage%>&pagesize=' + document.theform.pagesize.value 
			+"&df_serial_no="+document.theform.df_serial_no.value
			+"&serial_no="+document.theform.product_id.value
			+"&product_id="+document.theform.product_id.value
			+"&sub_product_id="+document.theform.sub_product_id.value
			+"&prov_flag="+document.theform.prov_flag.value
			+"&prov_level_name="+document.theform.prov_level_name.value
			+"&lower_limit="+document.theform.lower_limit.value
			+"&upper_limit="+document.theform.upper_limit.value
			+"&summary="+document.theform.summary.value;
	location = url;	
}

function StartQuery(){
	refreshPage();
}

function newInfo(df_serial_no , serial_no , gain_rate , start_date , end_date , float_rate){
	if(showModalDialog('gainlevel_rate_new.jsp?gain_flag=<%=gain_flag2 %>&df_serial_no='+df_serial_no + 
			'&serial_no='+serial_no+'&gain_rate='+gain_rate+
			'&start_date='+start_date+'&end_date='+end_date+'&float_rate='+float_rate,'',
			'dialogWidth:380px;dialogHeight:300px;status:0;help:0')!=null){
		sl_update_ok();
		location.reload();	
	}
}

function confirmRemove(serial_no,flag)
{	
	if(confirm('您确定选定的记录删除吗？'))
	{	
		disableAllBtn(true);
		document.theform.check_flag.value = flag;
		document.theform.s_id.value=serial_no;
		document.theform.submit();
	}
	
}

function o_exit(){
	tempArray = '<%=iQuery%>'.split('$');
	location = "gainlevel.jsp?page="+ tempArray[2] + "&pagesize=" + tempArray[3] +"&product_id="+tempArray[0]+"&sub_product_id="+tempArray[1]+"&prov_level="+tempArray[4]+"&prov_flag="+tempArray[5];
}

function queryInfo(productId,subProductId){
	if(showModalDialog('open_date_info.jsp?product_id='+productId+'&sub_product_id='+subProductId,'','dialogWidth:450px;dialogHeight:380px;status:0;help:0')!=null){
		//sl_update_ok();
		//location.reload();	
	}
}

</script>
</HEAD>
<BODY class="BODY body-nox" leftMargin=0 topMargin=0 rightmargin="0" bottommargin="0" onkeydown="javascript:chachEsc(window.event.keyCode)">
<form name="theform" method="post" action="gainlevel_rate_set.jsp" onsubmit="javascript:return validateForm(this);">
<input type=hidden name="check_flag" value="">
<input type=hidden name="s_id" value="" />
<input type=hidden name="df_serial_no" value="<%=df_serial_no %>">
<input type=hidden name="iQuery" value="<%=iQuery %>">
<TABLE height="100%" cellSpacing=0 cellPadding=0 border=0 width="100%">
	<TBODY>
		<TR>
			<TD vAlign=top align=left width="100%">
			<TABLE cellSpacing=0 cellPadding=4 align=center border=0 width="100%">
				<TBODY>
					<TR>
						<TD align=middle width="100%">
						<table border="0" width="100%" cellspacing="0" cellpadding="0">
							<tr>
								<td class="page-title"><font color="#215dc6"><b>收益率设置</b></font></td>
							</tr>
						</table>
						<table border="0" width="100%" cellspacing="3" cellpadding="3" class="product-list">
							<tr style="text-align: left;font-weight: bold; height: 20px;">
						    	<td colspan="4" ><p class="title-table">收益级别信息</p></td>
						   	</tr>
							<tr>
						    	<td align="right" width="10%">产品名称:</td >
						        <td align="left" width="45%" >
						        	<input type=hidden name="product_id" value="<%=product_id %>">
						        	<input type=hidden name="sub_product_id" value="<%=sub_product_id %>">
						        	<input readonly class="edline" type="text" name="productName" value="<%=Utility.trimNull(productName)%>" size="72">
						        </td >
								<td align="right" width="15%">客户类别:</td>
								<td align="left" width="30%">
									<input readonly class="edline" type="text" name="cust_typename" value="<%=cust_typename %>" size="18">
								</td>	
						   	</tr>
							<tr>
						    	<td align="right">收益优先级:</td >
						        <td align="left"><input type=hidden name="prov_flag" value="<%=prov_flag %>">
						        	<input readonly  class="edline" type="text" name="prov_flag_ms" value="<%=prov_flag_msg %>" size="18">
						        </td >
								<td align="right">收益级别:</td>
								<td align="left">
									<input readonly class="edline" type="text" name="prov_level_name" value="<%=prov_level_name %>" size="18">
								</td>	
						    </tr>
							<tr>
						    	<td align="right">份额下限:</td >
						        <td align="left"><input type=hidden name="lower_limit" value="<%=lower_limit %>">
						        	<input readonly class="edline" type="text" name="lower_limit_pk" value="<%=Format.formatMoney(lower_limit) %>" size="18">
						        </td >
								<td align="right">份额上限:</td>
								<td align="left"><input type=hidden name="upper_limit" value="<%=upper_limit %>">
									<input readonly class="edline" type="text"  name="upper_limit_pk" value="<%=Format.formatMoney(upper_limit) %>" size="18">
								</td>	
						    </tr>
							<tr>
								<td align="right">备&nbsp;注:</td>
								<td align="left" colspan="3">
									<input readonly class="edline" type="text" name="summary" value="<%=Utility.trimNull(summary) %>" size="125">
								</td>	
						    </tr>
						</table>
						<BR>
						<table border="0" width="100%" cellspacing="0" cellpadding="0" class="product-list">
							<tr style="text-align: left; font-weight: bold; height: 20px;">
								<td colspan="4"><p class="title-table">收益率信息</p></td>
							</tr>
							<tr>
							<td align="right">
								<button type="button"   class="xpbutton4" accessKey=n id="btnQuery" name="btnQuery" title="开放日查询" onclick="javascript:queryInfo(<%=product_id %>,<%=sub_product_id %>);">开放日查询</button>&nbsp;&nbsp;&nbsp;
								<%if (gain_flag==null||(gain_flag!=null&&gain_flag.intValue()!=2)) {%>
								<button type="button"   class="xpbutton3" accessKey=n id="btnNew" name="btnNew"
								title="新建记录" onclick="javascript:newInfo(<%=df_serial_no %> , null , null , null , null);">调置(<u>N</u>)</button>
								&nbsp;&nbsp;&nbsp;<%}%>
								<button type="button"   class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);o_exit();">返回(<u>B</u>)</button>
								</td>
							</tr>
							<tr>
								<td colspan="2">
								<hr noshade color="#808080" size="1">
								</td>
							</tr>
						</table>
						<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" class="product-list">
							<tr class="trh">
								<td align="center" width="25%">起始日期</td>
                                <td align="center" width="25%">截止日期</td>
                                <td align="center" width="25%">收益率</td>
                                <td align="center" <%=colDisplayStr %> width="25%">浮动收益率</td>
								<td align="center" width="25%">编辑</td>
							</tr>
<%
int iCount = 0;
int iCurrent = 0;

for(;iCount<list.size();iCount++){
	Map map = (Map)list.get(iCount);
	Integer start_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),null);
 	Integer end_date = Utility.parseInt(Utility.trimNull(map.get("END_DATE")),null);
 	BigDecimal gain_rate = Utility.parseDecimal(Utility.trimNull(map.get("GAIN_RATE")),new BigDecimal(0.00)).multiply(new BigDecimal(100));
 	df_serial_no = Utility.parseInt(Utility.trimNull(map.get("DF_SERIAL_NO")),new Integer(0));
 	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),new Integer(0));
 	BigDecimal float_rate = Utility.parseDecimal(Utility.trimNull(map.get("FLOAT_RATE")),new BigDecimal(0));
 %>
							<tr class="tr<%=(iCurrent % 2)%>">
								<td width="" align="center">
											<%=Format.formatDateCn(start_date) %>
								</td>
								<td width="" align="center" ><%=Format.formatDateCn(end_date) %></td>
								<td width="" align="center" ><%=Utility.trimZero(gain_rate)%>%</td>
								<td width="" align="center" <%=colDisplayStr%>><%=gain_rate%>%</td>
								<td width="" align="center" >
									<button type="button"   class="xpbutton2" accessKey=b id="btnCancel" name="btnCancel" name="btnEdit" title="修改记录" 
										onclick="javascript:newInfo('<%=df_serial_no%>','<%=serial_no%>',<%=gain_rate%>,<%=start_date%>,<%=end_date%>,<%=float_rate%>);">
										修改
									</button>
									&nbsp;&nbsp;
									<%if (gain_flag==null||(gain_flag!=null&&gain_flag.intValue()!=2)) {%>
										<button type="button"   class="xpbutton2" accessKey=d id="btnDelete"name="btnDelete" title="删除记录"onclick="javascript:if(confirmRemove('<%=serial_no%>',1)) document.theform.submit();">
											删除</button>
									<%}%>
								</td>
							</tr>
<%
iCurrent++ ;
} 
%>
							<tr class="trbottom">
								<td class="tdh" align="center" height="25"><b>合计 <%=list.size()%> 项</b></td>
								<td align="center" height="25">-</td>
								<td align="center" height="25">-</td>
								<td width="" align="center" <%=colDisplayStr %>>-</td>
								<td align="center" height="25">-</td>
							</tr>
						</table>
						<p></p>
						<BR>
<!-- 
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:disableAllBtn(true);o_exit();">返回(<u>B</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table> 
-->
						</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</form>
</BODY>
</HTML>
<%
local.remove() ;
}catch(Exception e){ throw e ;} %>
