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
Integer group_type = Utility.parseInt(Utility.trimNull(request.getParameter("group_type")),new Integer(5));
Integer group_detail = Utility.parseInt(Utility.trimNull(request.getParameter("group_detail")),new Integer(2));
String channel_coopertype = Utility.trimNull(request.getParameter("channel_coopertype"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String channel_type_name_tree = Utility.trimNull(Argument.getDictParamName(5500,channel_type),"");
Integer sub_product_id = Utility.parseInt(request.getParameter("sub_product_id"),new Integer(0));

Integer list_serviceman = Utility.parseInt(Utility.trimNull(request.getParameter("list_serviceman")),new Integer(2));
Integer list_saleman = Utility.parseInt(Utility.trimNull(request.getParameter("list_saleman")),new Integer(2));
Integer list_channel = Utility.parseInt(Utility.trimNull(request.getParameter("list_channel")),new Integer(2));
Integer list_product = Utility.parseInt(Utility.trimNull(request.getParameter("list_product")),new Integer(2));
Integer list_coopertype = Utility.parseInt(request.getParameter("list_coopertype"),new Integer(2));
Integer list_cust = Utility.parseInt(request.getParameter("list_cust"),new Integer(2));


Integer recommend_flag = Utility.parseInt(request.getParameter("recommend_flag"),new Integer(2));
Integer normal_flag = Utility.parseInt(request.getParameter("normal_flag"),new Integer(1));
Integer end_flag = Utility.parseInt(request.getParameter("end_flag"),new Integer(2));


Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")),new Integer(0));
Integer channel_id = Utility.parseInt(Utility.trimNull(request.getParameter("channel_id")),new Integer(0));
Integer sale_man = Utility.parseInt(Utility.trimNull(request.getParameter("sale_man")),new Integer(0));
Integer service_man = Utility.parseInt(Utility.trimNull(request.getParameter("service_man")),new Integer(0));
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")),new Integer(0));
int chagne_detail = Utility.parseInt(Utility.trimNull(request.getParameter("chagne_detail")),0);
int flag = Utility.parseInt(Utility.trimNull(request.getParameter("flag")),2);
String product_name = Utility.trimNull(request.getParameter("product_name"));

//url设置
String tempUrl = "";
tempUrl = tempUrl+"&cell_id="+cell_id+"&sub_product_id="+sub_product_id;
tempUrl = tempUrl+"&channel_type="+channel_type;
tempUrl = tempUrl+"&channel_name="+channel_name+"&recommend_flag="+recommend_flag+"&normal_flag="+normal_flag;
tempUrl = tempUrl+"&begin_date="+begin_date+"&list_cust="+list_cust+"&end_flag="+end_flag;
tempUrl = tempUrl+"&end_date="+end_date+"&cust_id="+cust_id+"&list_coopertype="+list_coopertype+"&channel_coopertype="+channel_coopertype;
tempUrl = tempUrl+"&group_type="+group_type+"&product_id="+product_id+"&channel_id="+channel_id+"&sale_man="+sale_man+"&service_man="+service_man+"&chagne_detail="+chagne_detail;
tempUrl = tempUrl+"&group_detail="+group_detail+"&list_serviceman="+list_serviceman+"&list_saleman="+list_saleman+"&list_channel="+list_channel+"&list_product="+list_product;
sUrl = sUrl + tempUrl;
//辅助变量
input_bookCode = new Integer(1);
//页面用辅助变量
int t_sPage = Utility.parseInt(sPage,1);
int t_sPagesize = Utility.parseInt(sPagesize,8);
String str = ""; 

//获得对象
ChannelQueryLocal local = EJBFactory.getChannelQuery();
ChannelVO vo = new ChannelVO();

vo.setCell_id(cell_id);
vo.setChannel_type(channel_type);
vo.setChannelName(channel_name);
vo.setBegin_date(begin_date);
vo.setEnd_date(end_date);
vo.setGroup_type(group_type);
vo.setIntrust_flag1(new Integer(2));

vo.setList_channel(list_channel);
vo.setList_product(list_product);
vo.setList_saleman(list_saleman);
vo.setList_serviceman(list_serviceman);
vo.setList_channel_coopertype(list_coopertype);
vo.setList_cust(list_cust);
vo.setRecommend_flag(recommend_flag);
vo.setNormal_flag(normal_flag);
vo.setEnd_flag(end_flag);
vo.setSub_product_id(sub_product_id);
if(chagne_detail==2){ //渠道
	vo.setChannel_id(channel_id);
	if(list_saleman.intValue()==1)
		vo.setSale_man(sale_man);
	if(list_serviceman.intValue()==1)
		vo.setService_man(service_man);
	if(list_product.intValue()==1)
		vo.setCell_id(product_id);
	if(list_coopertype.intValue()==1)
		vo.setChannel_coopertype(channel_coopertype);	
	if(list_cust.intValue()==1)
		vo.setCust_name(cust_name);		
}if(chagne_detail==1){ //产品
	if(product_id.intValue()==0){
		vo.setCell_id(cell_id);
	}else{
		vo.setCell_id(product_id);
		cell_id = product_id;
	}

	if(list_channel.intValue()!=0)
		vo.setChannel_id(channel_id);	
	if(list_saleman.intValue()==1)
		vo.setSale_man(sale_man);
	if(list_serviceman.intValue()==1)
		vo.setService_man(service_man);
	if(list_coopertype.intValue()==1)
		vo.setChannel_coopertype(channel_coopertype);	
	if(list_cust.intValue()==1)
		vo.setCust_name(cust_name);			
}if(chagne_detail==3){  //销售人员
	vo.setSale_man(sale_man);
	if(list_channel.intValue()!=0)
		vo.setChannel_id(channel_id);	
	if(list_serviceman.intValue()==1)
		vo.setService_man(service_man);
	if(list_product.intValue()==1)
		vo.setCell_id(product_id);
	if(list_coopertype.intValue()==1)
		vo.setChannel_coopertype(channel_coopertype);
	if(list_cust.intValue()==1)
		vo.setCust_name(cust_name);				
}if(chagne_detail == 5){ //渠道合作方式
	vo.setChannel_coopertype(channel_coopertype);
	if(list_channel.intValue()!=0)
		vo.setChannel_id(channel_id);	
	if(list_serviceman.intValue()==1)
		vo.setService_man(service_man);
	if(list_product.intValue()==1)
		vo.setCell_id(product_id);
	if(list_saleman.intValue()==1)
		vo.setSale_man(sale_man);	
	if(list_cust.intValue()==1)
		vo.setCust_name(cust_name);		
}if(chagne_detail == 6){ //按受益人
	vo.setCust_name(cust_name);
	if(list_channel.intValue()!=0)
		vo.setChannel_id(channel_id);	
	if(list_serviceman.intValue()==1)
		vo.setService_man(service_man);
	if(list_product.intValue()==1)
		vo.setCell_id(product_id);
	if(list_saleman.intValue()==1)
		vo.setSale_man(sale_man);	
	if(list_coopertype.intValue()==1)	
		vo.setChannel_coopertype(channel_coopertype);	
}if(chagne_detail == 4){ //按客户经理
	vo.setService_man(service_man);
	if(list_channel.intValue()!=0)
		vo.setChannel_id(channel_id);	
	if(list_serviceman.intValue()==1)
		vo.setService_man(service_man);
	if(list_product.intValue()==1)
		vo.setCell_id(product_id);
	if(list_saleman.intValue()==1)
		vo.setSale_man(sale_man);	
	if(list_coopertype.intValue()==1)	
		vo.setChannel_coopertype(channel_coopertype);	
	if(list_cust.intValue()==1)
		vo.setCust_name(cust_name);		
}


IPageList pageList = null;		
if(group_detail.intValue() == 1){
	flag = 0;
	vo.setChannel_id(channel_id);
	vo.setProduct_id(product_id);
	vo.setSale_man(sale_man);
	vo.setCust_id(cust_id);
	pageList = local.query(vo,t_sPage,t_sPagesize);
}else{
	pageList = local.querychannel(vo,t_sPage,t_sPagesize);
	
}

if(product_id.intValue()>0)
	cell_id = product_id;

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
<script type='text/javascript' src='<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js'></script>
<script language="javascript">
/*启动加载*/
window.onload = function(){
	initQueryCondition();
	flag = <%=group_detail%>
	if(flag == 1)
	{
		document.getElementById("style_1").style.display='none';
		document.getElementById("style_2").style.display='none';
		document.getElementById("style_3").style.display='none';
		document.getElementById("style_5").style.display='none';
		document.getElementById("style_4").style.display='none';
	}
	else
	{
		document.getElementById("style_1").style.display='';
		document.getElementById("style_2").style.display='';
		document.getElementById("style_3").style.display='';
		document.getElementById("style_5").style.display='';
		document.getElementById("style_4").style.display='';
	}

	<%if(cell_id.intValue()!=0){%>
		document.getElementById("sub_product_flag").style.display = "";
		getSubProductOption(<%=cell_id%>,<%=sub_product_id%>);
	<%}%>
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
	var  sub_product_id = 0;
	if(DWRUtil.getValue("cell_id")!=0)
		sub_product_id = DWRUtil.getValue("SUB_PRODUCT_ID");

	var _pagesize = document.getElementsByName("pagesize")[0];
	
	var list_channel;
	var list_product;
	var list_saleman;
	var list_serviceman;
	var list_coopertype;
	var list_cust;
	
	var recommend_flag = 2;
	var normal_flag=2;
	var end_flag=2;

	if($("recommend_flag").checked)
		recommend_flag = 1;
	if($("normal_flag").checked)
		normal_flag = 1;
	if($("end_flag").checked)
		end_flag = 1;		

	
	if(document.theform.group_detail.value==2)
	{   
		if(document.theform.list_product.checked)
				list_product = '1';
		
		if(document.theform.list_channel[0].checked)
				list_channel = '0';
		if(document.theform.list_channel[1].checked)
				list_channel = '1';
		if(document.theform.list_channel[2].checked)
				list_channel = '2';
				
		if(document.theform.list_saleman.checked)
				list_saleman = '1';
		
		if(document.theform.list_serviceman.checked)
				list_serviceman = '1';
	
	
		if(document.theform.list_coopertype.checked)
				list_coopertype = '1';

		
		if(document.theform.list_cust.checked)
				list_cust = '1';								
	}		
	
	//url 组合	
	var url = "channel_stat1.jsp?flag=2&page=<%=sPage%>&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");
	var url = url + "&cell_id=" + document.getElementById("cell_id").getAttribute("value");
	var url = url + "&channel_type=" + document.getElementById("channel_type").getAttribute("value");
	var url = url + "&channel_name=" + document.getElementById("channel_name").getAttribute("value");
	var url = url + "&begin_date=" + document.getElementById("begin_date").getAttribute("value");
	var url = url + "&end_date=" + document.getElementById("end_date").getAttribute("value");
	var url = url + "&group_type=" + document.getElementById("group_type").getAttribute("value");
	var url = url + "&cust_id=<%=cust_id%>&group_detail=" + document.getElementById("group_detail").getAttribute("value")
					+"&list_product="+list_product+"&list_channel="+list_channel+"&list_saleman="+list_saleman
					+"&list_serviceman="+list_serviceman+"&list_coopertype="+list_coopertype+"&list_cust="+list_cust
					+"&product_name="+document.theform.product_name.value
					+"&recommend_flag="+recommend_flag
					+"&normal_flag="+normal_flag
					+"&end_flag="+end_flag+"&sub_product_id="+sub_product_id;

	window.location = url;
}

function queryDetail(product_id,channel_id,sale_man,service_man,cust_id,channel_type,channel_name)
{
	syncDatePicker(document.theform.begin_date_picker, document.theform.begin_date);
	syncDatePicker(document.theform.end_date_picker, document.theform.end_date);
	if(intrustType_Flag == '1'){
		document.theform.channel_type.value = comboBoxTree.getValue();
	}
	var _pagesize = document.getElementsByName("pagesize")[0];
	var  sub_product_id = 0;

	if(DWRUtil.getValue("cell_id")!=0)
		sub_product_id = DWRUtil.getValue("SUB_PRODUCT_ID");
	

	
	var list_channel = <%=list_channel%>;
	var list_product = <%=list_product%>;
	var list_saleman = <%=list_saleman%>;
	var list_serviceman = <%=list_serviceman%>;
	var list_coopertype = <%=list_coopertype%>
	var list_cust = <%=list_cust%>
	var chagne_detail = document.theform.chagne_detail.value;
	if(chagne_detail==1)
		list_product = 1;
	if(chagne_detail==2)
		list_channel = 1;
	if(chagne_detail==3)
		list_saleman = 1;
	if(chagne_detail==4)
		list_serviceman = 1;
	if(chagne_detail==5)
		list_coopertype = 1	
	if(chagne_detail==6)	
		list_cust = 1;
	if(chagne_detail==0){
		sl_alert('最末级了,请返回!');
		return false;
	}
	
	var recommend_flag = 2;
	var normal_flag=2;
	var end_flag=2;

	if($("recommend_flag_2").checked)
		recommend_flag = 1;
	if($("normal_flag_2").checked)
		normal_flag = 1;
	if($("end_flag_2").checked)
		end_flag = 1;	

	//url 组合
	var url = "channel_stat1.jsp?flag=2&page=1&pagesize=" + _pagesize[_pagesize.selectedIndex].getAttribute("value");
	var url = url + "&cell_id=" +DWRUtil.getValue("cell_id");
	var url = url + "&channel_type=" + channel_type;
	var url = url + "&channel_name=" + channel_name+"&product_name="+document.theform.product_name.value;
	var url = url + "&begin_date=" + document.getElementById("begin_date").getAttribute("value");
	var url = url + "&end_date=" + document.getElementById("end_date").getAttribute("value");
	var url = url + "&group_type=" + document.getElementById("group_type").getAttribute("value");
	var url = url + "&group_detail=" + document.getElementById("group_detail").getAttribute("value")
					+"&list_product="+list_product+"&list_channel="+list_channel+"&list_saleman="+list_saleman+"&list_serviceman="+list_serviceman
					+"&product_id="+product_id+"&channel_id="+channel_id+"&sale_man="+sale_man+"&service_man="+service_man
					+"&chagne_detail="+chagne_detail+"&cust_id="+cust_id+"&list_coopertype="+list_coopertype+"&list_cust="+list_cust
					+"&recommend_flag="+recommend_flag
					+"&normal_flag="+normal_flag
					+"&end_flag="+end_flag+"&sub_product_id="+sub_product_id;	
	window.location = url;
}

var comboBoxTree;
var invest_type_name = '<%=channel_type_name_tree%>';
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
		selectNodeModel:'all'
	});
});

function detail(flag)
{
	if(flag == 1)
	{
		document.getElementById("style_1").style.display='none';
		document.getElementById("style_2").style.display='none';
		document.getElementById("style_3").style.display='none';
		document.getElementById("style_5").style.display='none';
		document.getElementById("style_4").style.display='none';
	}
	else
	{
		document.getElementById("style_1").style.display='';
		document.getElementById("style_2").style.display='';
		document.getElementById("style_3").style.display='';
		document.getElementById("style_5").style.display='';
		document.getElementById("style_4").style.display='';
	}
}

function checkBoxSelect()
{
	if(document.theform.recommend_flag_2.checked){//推荐期
		document.theform.recommend_flag.checked = true;
	}else{
		document.theform.recommend_flag.checked = false;	
	}	
	
	if(document.theform.normal_flag_2.checked){	//正常期
		document.theform.normal_flag.checked = true; 
	}else{
		document.theform.normal_flag.checked = false	
	}
	
	if(document.theform.end_flag_2.checked){	//结束期
		document.theform.end_flag.checked = true;	
	}else{
		document.theform.end_flag.checked = false;	
	}
	
	QueryAction();
}

var j$ = jQuery.noConflict();
function filterProduct(product_name){
    if(event.keyCode==13){    
        j$("[name='cell_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='cell_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='cell_id']").children(":not([text*='"+product_name+"'])").remove();
		onChangeProduct();
    }else{
        return false;    
    }           
}

function productFilter(product_name){    
        j$("[name='cell_id']").children().remove().append("<option value='0'>全部</option>");
        j$("[name='all_product_id']").children().each(function(){
            j$("[name='cell_id']").append("<option value='"+this.value+"'>"+this.text+"</option>");        
        });
        j$("[name='cell_id']").children(":not([text*='"+product_name+"'])").remove();     
		onChangeProduct();  
}

/*选择产品显示子产品*/
function onChangeProduct()
{
	var product_id = DWRUtil.getValue("cell_id");
	if(product_id != 0)
	{
		document.getElementById("sub_product_flag").style.display = "";
		getSubProductOption(product_id,0);;
	}
	else
	{
		document.getElementById("sub_product_flag").style.display = "none";
	}	
}

//添加子产品下拉 
function getSubProductOption(product_id,sub_product_id)
{
	utilityService.getSubProductOptionS(product_id,sub_product_id,{callback:function(data){
		$("select_id_2").innerHTML = "<select style='width:410px;' name='sub_product_id' onkeydown='javascript:nextKeyPress(this)'>"+data+"</SELECT>";
	}});
}

</script>
</head>
<body class="body">
<%@ include file="/includes/waiting.inc"%>
<form id="theform" name="theform" method="get">
<input type="hidden" id="channel_type" name="channel_type" value="<%=channel_type%>"/>
<div id="queryCondition" class="qcMain" style="display:none;width:650px;height:100px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
   			<td align="right">
   				<button type="button"  class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table>
	<!-- 要加入的查询内容 -->
	<table border="0" width="95%" cellspacing="2" cellpadding="2">
		<tr id="style_5">
			<td  align="right">产品名称: </td>
			<td  align="left" colspan="3">
				<input type="text"  name="product_name" value="<%=product_name %>" size="35" onkeydown="javascript:filterProduct(this.value);nextKeyPress(this)" >
				<button type="button"  class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:productFilter(document.theform.product_name.value);" /></button>
			</td>
		</tr>
		
		<tr>
			<td  align="right">产品选择: </td>
			<td  align="left" colspan="3">
				<select size="1" name="cell_id" onkeydown="javascript:nextKeyPress(this)" style="width: 410px;" onchange="javascript:onChangeProduct();">
					<%=Argument.getProductListOptions(input_bookCode, cell_id,"",input_operatorCode,10)%>
				</select>
				<SELECT name="all_product_id" style="display:none" style="width: 410px;">
					<%=Argument.getProductListOptions(input_bookCode, cell_id,"",input_operatorCode,10)%>
				</SELECT>
			</td>
		</tr>
		<tr id="sub_product_flag" <%if(product_id.intValue()==0){%>style="display: none;"<%} %>>
			<td align="right"><%=LocalUtilis.language("class.subProductID",clientLocale)%> </td><!--子产品选择-->
			<td align=left colspan=3 id="select_id_2">
				
			</td>
		</tr>
		<tr>
			<td  width="100px" align="right"><%=LocalUtilis.language("class.partnType",clientLocale)%>: </td>
			<td  width="220px" align="left"><!-- 渠道类别 -->
				<div id="comboBoxTree"></div>
			</td>
			<td  align="right">渠道名称: </td>
			<td  align="left">
				<input name="channel_name" onkeydown="javascript:nextKeyPress(this)" maxlength="6" size="20" value="<%=channel_name%>" />
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
			<td  align="right">汇总方式: </td>
			<td  align="left">
				<select size="1" name="group_type" onkeydown="javascript:nextKeyPress(this)" class="productname" style="width:120">
					<option value="5" <%if(group_type.intValue()==5) out.print("selected");%>>全&nbsp;部</option>
					<option value="1" <%if(group_type.intValue()==1) out.print("selected");%>>按&nbsp;周</option>
					<option value="2" <%if(group_type.intValue()==2) out.print("selected");%>>按&nbsp;月</option>
					<option value="3" <%if(group_type.intValue()==3) out.print("selected");%>>按&nbsp;季</option>
					<option value="4" <%if(group_type.intValue()==4) out.print("selected");%>>按&nbsp;年</option>
				</select>
			</td>
			<td  align="right">是否汇总明细: </td>
			<td  align="left">
				<select size="1" name="group_detail" onkeydown="javascript:nextKeyPress(this)" class="group_detail" style="width:120" onchange="javascript:detail(this.value);">
					<%=Argument.getGroupProduct(group_detail)%>
				</select>
			</td>
		</tr>
		<tr id="style_1">
			<td align="right"></td>
			<td>
				<input type="checkbox" class="flatcheckbox" name="list_product" value="1" <%if(list_product.intValue()==1) out.print("checked");%>>列示产品
			</td>	
			<td align="right">列示渠道:</td>
			<td>
				<input type="radio" class="flatcheckbox" name="list_channel" value="0" <%if(list_channel.intValue()==0) out.print("checked");%>>否
				<input type="radio" class="flatcheckbox" name="list_channel" value="1" <%if(list_channel.intValue()==1) out.print("checked");%>>所有渠道
				<input type="radio" class="flatcheckbox" name="list_channel" value="2" <%if(list_channel.intValue()==2) out.print("checked");%>>一级渠道
			</td>
		</tr>		
		<tr id="style_2">	
			<td align="right"></td>
			<td>
				<input type="checkbox" class="flatcheckbox" name="list_saleman" value="1" <%if(list_saleman.intValue()==1) out.print("checked");%>>列示销售人员
			</td>	
			<td align="right"></td>
			<td>
				<input type="checkbox" class="flatcheckbox" name="list_serviceman" value="1" <%if(list_serviceman.intValue()==1) out.print("checked");%>>列示客户经理
			</td>
		</tr>
		<tr id="style_3">	
			<td align="right">&nbsp;</td>
			<td>
				<input type="checkbox" class="flatcheckbox" name="list_coopertype" value="1" <%if(list_coopertype.intValue()==1) out.print("checked");%>>列示渠道合作方式
			</td>	
			<td align="right">&nbsp;</td>
			<td>
				<input type="checkbox" class="flatcheckbox" name="list_cust" value="1" <%if(list_cust.intValue()==1) out.print("checked");%>>列示受益人
			</td>	
		</tr>
		<tr id="style_4">	
			<td align="right">产品状态:</td>
			<td colspan="3">
				<input type="checkbox" class="flatcheckbox" name="recommend_flag" value="1" <%if(recommend_flag.intValue()==1) out.print("checked");%>>推荐期
				<input type="checkbox" class="flatcheckbox" name="normal_flag" value="1" <%if(normal_flag.intValue()==1) out.print("checked");%>>正常期
				<input type="checkbox" class="flatcheckbox" name="end_flag" value="1" <%if(end_flag.intValue()==1) out.print("checked");%>>已结束
			</td>	
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
		<button type="button"  class="xpbutton3" accessKey=b id="queryButton" name="queryButton" title="返回" onclick="javascript:window.returnValue=null;history.back();">
		  返回(<u>B</u>)</button><!--返回-->
	</div>
	<hr noshade color="#808080" size="1" width="100%">
</div>
<%if(group_detail.intValue()==2){ %>
<div valign="middle" align="left">
	&nbsp;&nbsp;根据条件汇总:
	<select name="chagne_detail">		
	<%if(list_product.intValue()==2){ %>
		<option value="1" <%if(chagne_detail==1) out.print("selected");%>>按产品</option>
	<%}if(list_channel.intValue()==0){ %>	
		<option value="2" <%if(chagne_detail==2) out.print("selected");%>>按渠道</option>
	<%}if(list_cust.intValue()==2){ %>
		<option value="6" <%if(chagne_detail==6) out.print("selected");%>>按受益人</option>
	<%}if(list_saleman.intValue()==2){ %>
		<option value="3" <%if(chagne_detail==3) out.print("selected");%>>按销售人员</option>
	<%}if(list_serviceman.intValue()==2){ %>
		<option value="4" <%if(chagne_detail==4) out.print("selected");%>>按客户经理</option>
	<%}if(list_coopertype.intValue()==2){ %>
		<option value="5" <%if(chagne_detail==5) out.print("selected");%>>按渠道合作方式</option>
	<%} %>	
	<option value="0">最末级</option>	
	</select>
	&nbsp;&nbsp;
	产品状态:
	<input type="checkbox" class="flatcheckbox" name="recommend_flag_2" value="1" <%if(recommend_flag.intValue()==1) out.print("checked");%> onclick="javascript:checkBoxSelect();">推荐期
	<input type="checkbox" class="flatcheckbox" name="normal_flag_2" value="1" <%if(normal_flag.intValue()==1) out.print("checked");%> onclick="javascript:checkBoxSelect();">正常期
	<input type="checkbox" class="flatcheckbox" name="end_flag_2" value="1" <%if(end_flag.intValue()==1) out.print("checked");%> onclick="javascript:checkBoxSelect();">已结束
		
	
</div>
<%} %>	
<div valign="middle" align="center">
	<table border="0"  width="99%" cellspacing="1" cellpadding="2"	class="tablelinecolor">
		<tr class="trh">
			<%if(list_product.intValue()==1 || chagne_detail==1){ %>
			<td align="center" width="*"><%=LocalUtilis.language("class.productID2",clientLocale)%></td><!-- 产品编号 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.productName",clientLocale)%></td> <!-- 产品名称 -->
			<td align="center" width="*">产品状态</td> <!-- 产品状态 -->
			<td align="center" width="*">产品总规模</td> <!--产品总规模 -->
			<%} if(group_type.intValue()!=5){%>
			<td align="center" width="*"><%=LocalUtilis.language("class.summaryTime",clientLocale)%></td><!-- 汇总时间 -->
			<%} if(list_channel.intValue()!=0 || chagne_detail==2){ %>
			<td align="center" width="*"><%=LocalUtilis.language("class.partnType",clientLocale)%></td><!-- 渠道类别 -->
			<td align="center" width="*"><%=LocalUtilis.language("class.partnName",clientLocale)%></td><!-- 渠道名称 -->
			
			<%} if(list_coopertype.intValue()==1 || chagne_detail==5){%>
			<td align="center" width="*">渠道合作方式</td> <!--渠道合作方式 -->
			<%} if(list_cust.intValue()==1 || chagne_detail==6){%>
			<td align="center" width="*">受益人</td> <!--受益人 -->
			
			<%} if(list_saleman.intValue()==1 || chagne_detail==3){%>
			<td align="center" width="*">销售人员</td> <!-- 销售人员 -->
			<%} if(list_serviceman.intValue()==1 || chagne_detail==4){%>
			 <td align="center" width="*">客户经理</td>  <!--客户经理-->
			<%} if(group_detail.intValue()==1){%>
			<td align="center" width="*">客户名称</td>
			<td align="center" width="*">合同编号</td>
			<td align="center" width="*">签订日期</td>
			<%}%>
			<td align="center" width="*"><%=LocalUtilis.language("class.rg_money",clientLocale)%></td> <!-- 认购金额 -->
			<td align="center" width="*">新增客户数</td> <!--新增客户数 -->
			<td align="center" width="*">客户总数</td> <!--客户总数 -->
			<td align="center" width="*">存续</td> <!--存续 -->
		</tr>
		<%
			//Iterator iterator = list.iterator();

			String channel_code;
			String channel_type_name;
			BigDecimal rg_money;
			Integer dateno;
			String product_code;
			String full_name;
			String count_contract_bh;
			BigDecimal rg_money_total = new BigDecimal(0.00);
			BigInteger newCust_number_total =  new BigInteger("0");
			BigInteger activeCust_number_total = new BigInteger("0");
			BigDecimal fact_money_total = new BigDecimal(0.00);
			BigDecimal ben_amount_total = new BigDecimal(0.00);
			int col_total = 0;
	
			for(int i=0;i<list.size();i++){
				iCount++;
				map = (Map)list.get(i);
				channel_type = Utility.trimNull(map.get("CHANNEL_TYPE"));
				channel_type_name = Utility.trimNull(map.get("CHANNEL_TYPE_NAME"));
				channel_name = Utility.trimNull(map.get("CHANNEL_NAME"));
				full_name = Utility.trimNull(map.get("FULL_NAME"));
				channel_code =Utility.trimNull(map.get("CHANNEL_CODE"));
				rg_money = Utility.parseDecimal(Utility.trimNull(map.get("RG_MONEY")),new BigDecimal(0.00));
				dateno = Utility.parseInt(Utility.trimNull(map.get("DATENO")),null);
				product_code =Utility.trimNull(map.get("PRODUCT_CODE"));
				product_name =Utility.trimNull(map.get("PRODUCT_NAME"));
				count_contract_bh = Utility.trimNull(map.get("COUNT_CONTRACT_BH"));
				Integer procuct_id = Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),new Integer(0));
				channel_id = Utility.parseInt(Utility.trimNull(map.get("CHANNEL_ID")),new Integer(0));
				sale_man = Utility.parseInt(Utility.trimNull(map.get("SALE_MAN")),new Integer(0));
				service_man = Utility.parseInt(Utility.trimNull(map.get("SERVICE_MAN")),new Integer(0));
				cust_id = Utility.parseInt(Utility.trimNull(map.get("CUST_ID")),new Integer(0));
				String sale_man_name = Utility.trimNull(map.get("SALE_MAN_NAME"));
				String service_man_name = Utility.trimNull(map.get("SERVICE_MAN_NAME"));
				cust_name = Utility.trimNull(map.get("CUST_NAME"));				
				String contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
				Integer rg_date = Utility.parseInt(Utility.trimNull(map.get("START_DATE")),new Integer(0));
				channel_coopertype = Utility.trimNull(map.get("CHANNEL_COOPERTYPE"));
				String channel_coopertype_name = Utility.trimNull(map.get("CHANNEL_COOPERTYPE_NAME"));
				String product_status_name = Utility.trimNull(map.get("PRODUCT_STATUS_NAME"));
				String sub_product_name = Utility.trimNull(map.get("SUB_PRODUCT_NAME"));
				if(!"".equals(sub_product_name) && sub_product_name!=null)
					sub_product_name = "("+sub_product_name+")";
				
				BigInteger newCust_number = new BigInteger(Utility.trimNull(map.get("NEWCUST_NUMBER")));
				BigInteger activeCust_number = new BigInteger(Utility.trimNull(map.get("ACTIVECUST_NUMBER")));
				BigDecimal fact_money = Utility.parseDecimal(Utility.trimNull(map.get("FACT_MONEY")),new BigDecimal(0.00));
				BigDecimal ben_amount = Utility.parseDecimal(Utility.trimNull(map.get("BEN_AMOUNT")),new BigDecimal(0.00));
				
				rg_money_total = rg_money_total.add(rg_money);
				activeCust_number_total = activeCust_number_total.add(activeCust_number);
				newCust_number_total = newCust_number_total.add(newCust_number);
				fact_money_total = fact_money_total.add(fact_money);
				ben_amount_total = ben_amount_total.add(ben_amount);
		%>
		<tr class="tr<%=iCount%2%>" <%if(flag==2){%>ondblclick="javascript:queryDetail('<%=procuct_id %>','<%=channel_id %>','<%=sale_man %>','<%=service_man %>','<%=cust_id %>','<%=channel_type %>','<%=channel_name %>');" style="cursor:hand;"<%} %>>
			<%if(list_product.intValue()==1 || chagne_detail==1){ %>
			<td align="left" width="*">&nbsp;&nbsp;<%=product_code%></td>
			<td align="left" width="*">&nbsp;&nbsp;<%=product_name%><%=sub_product_name %></td>
			<td align="left" width="*">&nbsp;&nbsp;<%=product_status_name%></td>
			<td align="right" width="*">&nbsp;&nbsp;<%=Format.formatMoney(fact_money) %>&nbsp;&nbsp;</td> 
			<%} if(group_type.intValue()!=5){%>
			<td align="center" width="*">&nbsp;&nbsp;<%=Utility.trimNull(dateno)%></td>
			<%} if(list_channel.intValue()!=0 || chagne_detail==2){ %>
			<td align="left" width="*">&nbsp;&nbsp;<%=channel_type_name%></td>
			<td align="left" width="*">&nbsp;&nbsp;<%=full_name%></td>
			
			<%} if(list_coopertype.intValue()==1 || chagne_detail==5){%>
			<td align="center" width="*"><%=channel_coopertype_name %></td> 
			<%} if(list_cust.intValue()==1 || chagne_detail==6){%>
			<td align="center" width="*">&nbsp;<%=cust_name %></td> <!--受益人 -->
			<%} if(list_saleman.intValue()==1 || chagne_detail==3){%>
			<td align="left" width="*"><%=Utility.trimNull(sale_man_name) %></td> 
			<%} if(list_serviceman.intValue()==1 || chagne_detail==4){%>
			<td align="left" width="*"><%=Utility.trimNull(service_man_name) %></td>
			<%} if(group_detail.intValue()==1){%>
			<td align="left" width="*"><%=Utility.trimNull(cust_name)%></td>
			<td align="left" width="*"><%=Utility.trimNull(contract_sub_bh)%></td>
			<td align="left" width="*"><%=Format.formatDateCn(rg_date)%></td>
			<%}%>
			<td align="right" width="*"><%=Format.formatMoney(rg_money)%>&nbsp;&nbsp;</td>
			<td align="right" width="*"><%=Utility.trimNull(newCust_number)%>&nbsp;&nbsp;</td>
			<td align="right" width="*"><%=Utility.trimNull(activeCust_number)%>&nbsp;&nbsp;</td>
			<td align="right" width="*"><%=Format.formatMoney(ben_amount)%>&nbsp;&nbsp;</td>
		</tr>
		<%}%>
		<%for(int i=0;i<pageList.getBlankSize();i++){%>
         <tr class="tr0">
         	<%if(list_product.intValue()==1 || chagne_detail==1){ %>
            <td align="center"></td>
            <td align="center"></td>
            <td align="center">&nbsp;&nbsp;</td>
            <td align="center"></td>
            <%} if(group_type.intValue()!=5){%>
            <td align="center"></td>
            <%} if(list_channel.intValue()!=0 || chagne_detail==2){ %>
            <td align="center"></td>
            <td align="center"></td>
            <%} if(list_coopertype.intValue()==1 || chagne_detail==5){%>
			<td align="center" width="*"></td> 
            <%} if(list_cust.intValue()==1 || chagne_detail==6){%>
			<td align="center" width="*"></td> 
            <%} if(list_saleman.intValue()==1 || chagne_detail==3){%>
			<td align="center" width="*"></td> 
			<%} if(list_serviceman.intValue()==1 || chagne_detail==4){%>
			<td align="center" width="*"></td>
			<%}%>
            <td align="center"></td>
           <%if(group_detail.intValue()==1){%>
			<td align="center" width="*"></td>
			<td align="center" width="*"></td>
			<td align="center" width="*"></td>
		   <%}%>
		   <td align="center" width="*"></td>
		   <td align="center" width="*"></td>
		   <td align="center" width="*"></td>
		   <td align="center" width="*"></td>
         </tr>
		<%}%>
		<tr class="tr1">
			<%if(list_product.intValue()==1 || chagne_detail==1){ %>
			<td align="center" width="*"></td>
			<td align="center" width="*"></td> 
			<td align="center" width="*"></td> 
			<td align="right" width="*"><b><font color="red"><%=Format.formatMoney(fact_money_total) %>&nbsp;</font></b></td> 
			<%} if(group_type.intValue()!=5){%>
			<td align="center" width="*"></td>
			<%} if(list_channel.intValue()!=0 || chagne_detail==2){ %>
			<td align="center" width="*"></td>
			<td align="center" width="*"></td>
			<%} if(list_coopertype.intValue()==1 || chagne_detail==5){%>
			<td align="center" width="*"></td> 
			<%} if(list_cust.intValue()==1 || chagne_detail==6){%>
			<td align="center" width="*"></td> 
			<%} if(list_saleman.intValue()==1 || chagne_detail==3){%>
			<td align="center" width="*"></td>
			<%} if(list_serviceman.intValue()==1 || chagne_detail==4){%>
			<td align="center" width="*"></td> 
			<%} if(group_detail.intValue()==1){%>
			<td align="center" width="*"></td>
			<td align="center" width="*"></td>
			<td align="center" width="*"></td>
			<%}%>
			<td align="right"><b><font color="red"><%=Format.formatMoney(rg_money_total) %>&nbsp;</font></b></td>
			<td align="right"><b><font color="red"><%=Utility.trimNull(newCust_number_total) %>&nbsp;</font></b></td>
			<td align="right"><b><font color="red"><%=Utility.trimNull(activeCust_number_total) %>&nbsp;</font></b></td>
			<td align="right"><b><font color="red"><%=Format.formatMoney(ben_amount_total) %>&nbsp;</font></b></td>
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
