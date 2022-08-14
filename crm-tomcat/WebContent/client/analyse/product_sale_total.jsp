<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*, enfo.crm.system.*,enfo.crm.vo.* ,enfo.crm.system.*,enfo.crm.dao.*,java.util.*,java.math.*,enfo.crm.intrust.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
ChannelLocal local = EJBFactory.getChannel();
ChannelVO vo = new ChannelVO();


Integer product_id 		= Utility.parseInt(request.getParameter("product_id"),null);
Integer start_date 		= Utility.parseInt(request.getParameter("start_date"),new Integer(Utility.getCurrentYear()*10000+100+1));
Integer end_date 		= Utility.parseInt(request.getParameter("end_date"),new Integer(Utility.getCurrentDate()));
Integer intrust_flag 	= Utility.parseInt(request.getParameter("intrust_flag"),new Integer(2));
String product_status 	= Utility.trimNull(request.getParameter("product_status"),"110203");
String product_name 	= Utility.trimNull(request.getParameter("product_name"));
String[] totalColumn 	= new String[0];
int flag = 1;//产品状态为 正常，已结束
vo.setProduct_id(product_id);
vo.setStart_date(start_date);
vo.setEnd_date(end_date);
vo.setSellFlag(new Integer(1));
vo.setInput_man(input_operatorCode);
vo.setProduct_status(product_status);
vo.setIntrust_flag(intrust_flag);
vo.setProduct_name(product_name); 
if("110202".equals(product_status))
	flag = 2;
String[] totalCoumn = {"RG_AMOUNT","SG_AMOUNT","SH_AMOUNT","PRE_MONEY","PRE_MONEY_2","NEW_CUST_NUM","TOTAL_CUST_NUM","PRE_NUM"};
IPageList pageList  = local.queryPageAnalysis(vo,totalCoumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8),flag);
sUrl = sUrl+"&product_status="+product_status+"&start_date="+start_date+"&end_date="+end_date
	   +"&intrust_flag="+intrust_flag+"&product_id="+product_id+"&product_name="+product_name;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>产品销售统计</title>  
    <meta http-equiv="X-UA-Compatible" content="IE=7" > 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
	<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
	<style>
		.headdarkbutton
		{
			cursor:hand;
			BORDER-RIGHT: 0px solid;
		    BORDER-TOP: 0px solid;
		    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
		    PADDING-BOTTOM: 0px;
		    BORDER-LEFT: 0px solid;
		    WIDTH: 80px;
		    PADDING-TOP: 0px;
		    BACKGROUND-COLOR:white;		
		    BORDER-BOTTOM: 0px solid;
		    HEIGHT: 20px;
		}
		
		.headbutton
		{
			cursor:hand;
			BORDER-RIGHT: 0px solid;
		    BORDER-TOP: 0px solid;
		    BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
		    PADDING-BOTTOM: 0px;
		    BORDER-LEFT: 0px solid;
		    WIDTH: 80px;
		    PADDING-TOP: 0px;
		    BACKGROUND-COLOR:white;		
		    BORDER-BOTTOM: 0px solid;
		    HEIGHT: 20px;
		}
	</style>
	<script type="text/javascript">
		function show(flag,status)
		{	
			for(i=0;i<3;i++)
			{  
		     	if(i!= flag)
		     	{
		        	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
			 	}
			 	else
			 	{
			    	eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");
			 	} 
		  	}
			loadMenu(status);
		}
		
		function query()
		{
			syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
			document.theform.action = "product_sale_total.jsp?product_status="+DWRUtil.getValue("product_status");
			document.theform.submit();
		}
		


		//通过产品ID 针对渠道 or 客户经理 进行统计
		function queryDetail(product_name,product_id,start_date)
		{
			showModalDialog('queryChannel.jsp?flag=<%=flag%>&product_status=<%=product_status%>&product_id=' + product_id +'&start_date='+start_date+'&product_name='+product_name, '', 'dialogWidth:690px;dialogHeight:400px;status:0;help:0'); 
		}

		function queryProductDetail(product_id)
		{
			showModalDialog('/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
		}

		function loadMenu(product_status)
		{
			syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
			document.theform.product_status.value = product_status;
			location = 'product_sale_total.jsp?product_status='+product_status
							+'&intrust_flag='+DWRUtil.getValue("intrust_flag")
							+'&product_name=' + DWRUtil.getValue("product_name")
						   	+'&intrust_flag='+DWRUtil.getValue("intrust_flag")
						   	+'&start_date='+DWRUtil.getValue("start_date")
						   	+'&end_date='+DWRUtil.getValue("end_date")
		} 
		
		function refreshPage()
		{	
			syncDatePicker(document.theform.start_date_picker,document.theform.start_date);
			syncDatePicker(document.theform.end_date_picker,document.theform.end_date);
			disableAllBtn(true);			
			location = 'product_sale_total.jsp?page=<%=sPage%>&pagesize='+document.theform.pagesize.value 
					   +'&product_name=' + DWRUtil.getValue("product_name")
					   +'&intrust_flag='+DWRUtil.getValue("intrust_flag")
					   +'&start_date='+DWRUtil.getValue("start_date")
					   +'&end_date='+DWRUtil.getValue("end_date")
					   +'product_status=<%=product_status%>';
		}

	</script>
  </head>
  
  <body class="BODY">
    	<form name="theform"  method="get">
		<input type="hidden" name="product_status" value="<%=product_status %>">
    		<table border="0" width="100%" cellspacing="1" cellpadding="4">
				<tr>
					<td><br>&nbsp;<img border="0" src="<%=request.getContextPath()%>/images/Feichuang5.jpg" width="38" height="36">
					    <b>产品销售统计</b>
					</td>
				</tr>		
			</table>
    		<table  border="0"  width="99%" cellspacing="1" cellpadding="2">
    			<tr>
    				<td width="">
						产品名称:
						<INPUT TYPE="text" NAME="product_name" size="25" VALUE="<%=product_name %>">&nbsp;&nbsp;
						<SELECT name="intrust_flag" style="width: 80px;">
							<OPTION value="0" <%if(intrust_flag.intValue()==0) out.print("selected");%>>全&nbsp;部</OPTION>
							<OPTION value="1" <%if(intrust_flag.intValue()==1) out.print("selected");%>>单&nbsp;一</OPTION>
							<OPTION value="2" <%if(intrust_flag.intValue()==2) out.print("selected");%>>集&nbsp;合</OPTION>							
						</SELECT>
    				</td> 
					<td colspan="3">&nbsp;</td>					
    			</tr>
				<tr>
					<td>
						起始日期:
						<INPUT TYPE="text" NAME="start_date_picker" size="" class=selecttext value="<%=Format.formatDateLine(start_date)%>"> 
						<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
									onclick="javascript:CalendarWebControl.show(theform.start_date_picker,theform.start_date_picker.value,this);"> 
						<INPUT TYPE="hidden" NAME="start_date" value="">
    					&nbsp;&nbsp;结束日期:
    					<INPUT TYPE="text" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>"> 
						<INPUT TYPE="button" value="▼"class=selectbtn tabindex="13"
									onclick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);"> 
						<INPUT TYPE="hidden" NAME="end_date" value="">
    					&nbsp;&nbsp;
    					<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" onclick="javascript:query();">查询(<u>Q</u>)</button>	
					</td>
					<td colspan="3">&nbsp;</td>					
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td align="right" id=d0 style="background-repeat: no-repeat;cursor: hand;" onclick=show(0,110203) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if ("110203".equals(product_status)) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;正常期</td>
                    <td align="right" id=d1 style="background-repeat: no-repeat;cursor: hand;" onclick=show(1,110202) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if ("110202".equals(product_status)) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>推介期</td>
                    <td align="right" id=d2 style="background-repeat: no-repeat;cursor: hand;" onclick=show(2,110204) vAlign=top width=60 height=20 background='<%=request.getContextPath()%>/images/<%if ("110204".equals(product_status)) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;已结束</td>
				</tr>
				<tr>
					<td colspan="4">
					<hr noshade color="#808080" size="1">
					</td>
				</tr>
    		</table>
    		<table border="0"  width="99%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
				<tr class="trh">
					<td class="tdh" width="" align="center">产品编号</td>
					<td class="tdh" width="" align="center">产品名称</td>
					<td class="tdh" width="" align="center">产品状态</td>
					<%if("110202".equals(product_status)) {%>
					<td class="tdh" width="" align="center">预期发行总金额</td>
					<td class="tdh" width="" align="center">预期发行总份数</td>
					<td class="tdh" width="" align="center">已募集</td>
					<%} %>
					<td class="tdh" width="" align="center">成立时间</td>
					<td class="tdh" width="" align="center">结束时间</td>
					<%if(!"110202".equals(product_status)) {%>
					<td class="tdh" width="" align="center">认购份额</td>
					<td class="tdh" width="" align="center">申购份额</td>
					<td class="tdh" width="" align="center">赎回份额</td>
					<%} %>
					<td class="tdh" width="" align="center">新增客户数</td>
					<td class="tdh" width="" align="center">客户总人数</td>
				</tr>
<%
int iCurrent = 0;
List list = pageList.getRsList();
for(int i=0;i<list.size();i++){	
	
	Map map = (Map)list.get(i);
	String product_code    = Utility.trimNull(map.get("PRODUCT_CODE"));
	product_name	       = Utility.trimNull(map.get("PRODUCT_NAME"));
	product_id 			   = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
	start_date 	   		   = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
	end_date 	   		   = Utility.parseInt(Utility.trimNull(map.get("END_DATE")),new Integer(0));
	BigDecimal rg_amount   = Utility.parseDecimal(Utility.trimNull(map.get("RG_AMOUNT")),new BigDecimal(0));
	BigDecimal sg_amount   = Utility.parseDecimal(Utility.trimNull(map.get("SG_AMOUNT")),new BigDecimal(0));
	BigDecimal sh_amount   = Utility.parseDecimal(Utility.trimNull(map.get("SH_AMOUNT")),new BigDecimal(0));
	BigDecimal pre_money   = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY")),new BigDecimal(0));
	BigDecimal pre_money_2 = Utility.parseDecimal(Utility.trimNull(map.get("PRE_MONEY_2")),new BigDecimal(0));
	Integer new_cust_num   = Utility.parseInt(Utility.trimNull(map.get("NEW_CUST_NUM")),null);
	Integer total_cust_num = Utility.parseInt(Utility.trimNull(map.get("TOTAL_CUST_NUM")),null);
	String product_status_name =Utility.trimNull(map.get("PRODUCT_STATUS_NAME"));
	Integer pre_num		   = Utility.parseInt(Utility.trimNull(map.get("PRE_NUM")),null);
 %>
				<tr class="tr<%=(iCurrent % 2)%>" ondblclick="javascript:queryDetail('<%=product_code+"_"+product_name %>',<%=product_id %>,<%=start_date %>);" style="cursor: hand;">
					<td align="center"><a href="#" onclick="javascript:queryProductDetail(<%=product_id%>)"><%=Utility.trimNull(product_code) %></a></td>
					<td align="left"><%=Utility.trimNull(product_name) %></td>
					<td align="center"><%=Utility.trimNull(product_status_name) %></td>
					<%if("110202".equals(product_status)) {%>
					<td align="right"><%=Format.formatMoney(pre_money) %></td>
					<td align="right"><%=Utility.trimNull(pre_num) %></td>
					<td align="right"><%=Format.formatMoney(pre_money_2) %></td>
					<%} %>
					<td align="center"><%=Format.formatDateLine(start_date) %></td>
					<td align="center"><%=Format.formatDateLine(end_date) %></td>
					<%if(!"110202".equals(product_status)) {%>
					<td align="right"><%=Format.formatMoney(rg_amount) %></td>
					<td align="right"><%=Format.formatMoney(sg_amount) %></td>
					<td align="right"><%=Format.formatMoney(sh_amount) %></td>
					<%} %>
					<td align="right"><%=Utility.trimNull(new_cust_num) %></td>
					<td align="right"><%=Utility.trimNull(total_cust_num) %></td>
				</tr>
<%iCurrent++;} 

for(int k=0;k<pageList.getBlankSize();k++){
%>
				<tr class="tr<%=(k % 2)%>">
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<%if("110202".equals(product_status)) {%>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<%} %>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<%if(!"110202".equals(product_status)) {%>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<%} %>
					<td>&nbsp;</td>
				</tr>
<%} %>
				<tr class="tr1">
					<td colspan="0">
						<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--合计-->&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--项--></b>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<%if("110202".equals(product_status)) {%>
					<td align="right"><font color="red"><%=Format.formatMoney(pageList.getTotalValue("PRE_MONEY")) %></font></td>
					<td align="right"><font color="red"><%=Utility.trimNull(pageList.getTotalValue("PRE_NUM")) %></font></td>
					<td align="right"><font color="red"><%=Format.formatMoney(pageList.getTotalValue("PRE_MONEY_2")) %></font></td>
					<%} %>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<%if(!"110202".equals(product_status)) {%>
					<td align="right"><font color="red"><%=Format.formatMoney(pageList.getTotalValue("RG_AMOUNT")) %></font></td>
					<td align="right"><font color="red"><%=Format.formatMoney(pageList.getTotalValue("SG_AMOUNT")) %></font></td>
					<td align="right"><font color="red"><%=Format.formatMoney(pageList.getTotalValue("SH_AMOUNT")) %></font></td>
					<%} %>
					<td align="right"><font color="red"><%=Utility.trimNull(pageList.getTotalValue("NEW_CUST_NUM")) %></font></td>
					<td align="right"><font color="red"><%=Utility.trimNull(pageList.getTotalValue("TOTAL_CUST_NUM")) %></font></td>
				</tr>
    		</table>
    		<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
			</table>
    	</form>
  </body>
</html>
