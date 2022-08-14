<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<jsp:directive.page import="enfo.crm.tools.LocalUtilis;"/>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//取得页面查询参数
Integer cell_id = Utility.parseInt(Utility.trimNull(request.getParameter("cell_id")),new Integer(0));
String channel_type =Utility.trimNull(request.getParameter("channel_type"));
String channel_name = Utility.trimNull(request.getParameter("channel_name"));
Integer begin_date = Utility.parseInt(Utility.trimNull(request.getParameter("begin_date")),new Integer(0));
Integer end_date = Utility.parseInt(Utility.trimNull(request.getParameter("end_date")),new Integer(0));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&cell_id="+cell_id;
tempUrl = tempUrl+"&channel_type="+channel_type;
tempUrl = tempUrl+"&channel_name="+channel_name;
tempUrl = tempUrl+"&begin_date="+begin_date;
tempUrl = tempUrl+"&end_date="+end_date;
sUrl = sUrl + tempUrl;
//辅助变量
input_bookCode = new Integer(1);
//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
//获得对象
ChannelQueryLocal local = EJBFactory.getChannelQuery();
ChannelVO vo = new ChannelVO();

vo.setCell_id(cell_id);
vo.setChannel_type(channel_type);
vo.setChannelName(channel_name);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
IPageList pageList = local.query(vo,t_sPage,t_sPagesize);
//分页辅助参数
List list = pageList.getRsList();
int iCount = 0;
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
<title></title><!--代销渠道管理-->
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet" />
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/widgets/ext/resources/css/ComboBoxTree.js"></script>
<script language="javascript">
/*启动加载*/	
window.onload = function(){
	initQueryCondition();
}
function refreshPage(){
	disableAllBtn(true);
	QueryAction();
}

/*查询功能*/
function QueryAction(){		
	syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	if(intrustType_Flag == '1'){
		document.theform.channel_type.value = comboBoxTree.getValue();
	}
	var _pagesize = document.getElementsByName("pagesize")[0];		
	//url 组合
	var url = "channel_buydetail.jsp?page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");		
	var url = url + "&cell_id=" + document.getElementById("cell_id").getAttribute("value");
	var url = url + "&channel_type=" + document.getElementById("channel_type").getAttribute("value");
	var url = url + "&channel_name=" + document.getElementById("channel_name").getAttribute("value");
	var url = url + "&begin_date=" + document.getElementById("begin_date").getAttribute("value");
	var url = url + "&end_date=" + document.getElementById("end_date").getAttribute("value");

	window.location = url;
}

var comboBoxTree;
var invest_type_name = '<%=channel_type%>';
var intrustType_Flag;
Ext.BLANK_IMAGE_URL = '<%=request.getContextPath()%>/widgets/ext/resources/images/default/tree/s.gif';

Ext.onReady(function(){
	comboBoxTree = new Ext.ux.ComboBoxTree({
		renderTo : 'comboBoxTree',
		width:220,
		tree : {
			xtype:'treepanel',
			width:220,
			loader: new Ext.tree.TreeLoader({dataUrl:'<%=request.getContextPath()%>/client/channel/treeData.jsp'}),
       	 	root : new Ext.tree.AsyncTreeNode({id:'-1',text:'<%=LocalUtilis.language("class.partnType",clientLocale)%> '}),//渠道信息
       	 	listeners:{
       	 		beforeload:function(node){
       	 			if(node.id!='-1')
       	 				this.loader.dataUrl = '<%=request.getContextPath()%>/client/channel/treeData.jsp?value='+node.id;
       	 		}
       	 	}
    	},
    	
		//all:所有结点都可选中
		//exceptRoot：除根结点，其它结点都可选(默认)
		//folder:只有目录（非叶子和非根结点）可选
		//leaf：只有叶子结点可选
		selectNodeModel:'leaf'
	});
}); 

</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<div id="queryCondition" class="qcMain" style="display:none;width:550px;height:90px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right" colspan="3">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>		
	</table> 
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr>
			<td  align="right">产品名称: </td>
			<td  align="left" colspan="3">
				<select size="1" name="cell_id" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getProductListOptions(input_bookCode, cell_id,"",input_operatorCode,0)%>
				</select>
			</td>	
		</tr>
		<tr>
			<td  align="right">渠道名称: </td>
			<td  align="left">
				<input name="channel_name" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=channel_name%>" />
			</td>		
			<td  align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>: </td>
			<td  align="left"><!-- 渠道类别 -->
				<div id="comboBoxTree"></div>
			</td>
		</tr>
		<tr>
			<td align="right"><%=LocalUtilis.language("class.startDate",clientLocale)%> :</td><!--开始日期-->
			<td>
				<input TYPE="text" style="width:120" NAME="begin_date_picker" class=selecttext value="<%=Format.formatDateLine(begin_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.begin_date_picker,theform.begin_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="begin_date" value="">	</td>
			<td align="right"><%=LocalUtilis.language("class.endDate",clientLocale)%> :</td><!--结束日期-->
			<td>
				<input TYPE="text" style="width:120" NAME="end_date_picker" class=selecttext value="<%=Format.formatDateLine(end_date)%>" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="button" value="▼" class=selectbtn onClick="javascript:CalendarWebControl.show(theform.end_date_picker,theform.end_date_picker.value,this);" onKeyDown="javascript:nextKeyPress(this)"> 
        <input TYPE="hidden" NAME="end_date" value="">	</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
				<button type="button"  class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
				<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)</button><!--确定-->
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
		<button type="button"  class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%>" onclick="javascript:void(0);">
		   <%=LocalUtilis.language("message.query",clientLocale)%>(<u>Q</u>)</button><!--查询-->
		&nbsp;&nbsp;&nbsp;<%}%>
	</div>
	<hr noshade color="#808080" size="1" width="100%">
</div>

<div valign="middle" align="center">
	<table border="0"  width="100%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<td align="center" width="*"><%=LocalUtilis.language("class.productID2",clientLocale)%></td><!-- 产品编号 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.productName",clientLocale)%></td><!-- 产品名称 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnType",clientLocale)%></td><!-- 渠道类别 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnName",clientLocale)%></td><!-- 渠道名称 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnAttachmentInfo",clientLocale)%></td><!-- 渠道附加信息 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.subscriptionDate",clientLocale)%></td><!-- 认购日期 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.customerName",clientLocale)%></td><!-- 客户 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.rg_money",clientLocale)%></td> <!-- 认购金额 -->
			<td align="center" width="*">信托单位类别</td> <!-- 信托单位类别 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.emissionFace",clientLocale)%></td> <!-- 代销费用 -->
		</tr>
		<%
			Iterator iterator = list.iterator();
			String product_code;
			String product_name;
			String channel_code;
			String channel_type_name;
			Integer channel_id;
			String channel_memo;
			Integer cust_id;
			String cust_name;
			Integer start_date;
			BigDecimal rg_money;
			String prov_level_name = "";
			BigDecimal channel_fare = new BigDecimal(0);
			
			while(iterator.hasNext()){
				iCount++;
				map = (Map)iterator.next();
				product_code = Utility.trimNull(map.get("PRODUCT_CODE"));
				product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
				channel_type = Utility.trimNull(map.get("CHANNEL_TYPE"));
				channel_type_name = Utility.trimNull(map.get("CHANNEL_TYPE_NAME"));
				channel_id = Utility.parseInt(Utility.trimNull(map.get("CHANNEL_ID")),new Integer(0));
				channel_code =Utility.trimNull(map.get("CHANNEL_CODE"));
				channel_name =Utility.trimNull(map.get("CHANNEL_NAME"));
				channel_memo =Utility.trimNull(map.get("CHANNEL_MEMO"));
				cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
				cust_name =Utility.trimNull(map.get("CUST_NAME"));
				start_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
				rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),null);
				prov_level_name = Utility.trimNull(map.get("PROV_LEVEL_NAME"));
				channel_fare = Utility.parseDecimal(Utility.trimNull(map.get("CHANNEL_FARE")), new BigDecimal(0),2,"1");
 
		%>
		<tr class="tr<%=iCount%2%>">
			<td align="center" width="*"><%=product_code %> </td>
			<td align="left" width="*">&nbsp;&nbsp;<%=product_name %> </td>
			<td align="center" width="*"><%=channel_type_name%></td>
			<td align="center" width="*"><%=channel_name%></td>
			<td align="center" width="*"><%=channel_memo%></td>
			<td align="center" width="*"><%=Format.formatDateCn(start_date)%></td>
			<td align="center" width="*"><%=cust_name %></td>
			<td align="right" width="*"><%=Format.formatMoney(rg_money)%>&nbsp;&nbsp;</td>
			<td><%=Utility.trimNull(prov_level_name) %></td>
			<td align="right" width="*"><%=channel_fare %></td>
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
				<td align="center"></td>                  
	         </tr>           
		<%}%> 
		<tr class="tr1">
			<td class="tdh" colspan="11" align="left">&nbsp;<b><%=LocalUtilis.language("message.total",clientLocale)%><!--合计--> <%=pageList.getTotalSize()%> <%=LocalUtilis.language("message.entries",clientLocale)%><!--项--></b></td>
		</tr>
	</TABLE>
</div>
<br>
<div>
	&nbsp;&nbsp;<%=pageList.getPageLink(sUrl)%>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</body>
</html>
