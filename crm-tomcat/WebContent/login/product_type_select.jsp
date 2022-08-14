<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.intrust.*,enfo.crm.web.*,enfo.crm.system.*,enfo.crm.vo.*,enfo.crm.service.*,enfo.crm.dao.*,enfo.crm.affair.*,enfo.crm.tools.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%
ProductLocal local = EJBFactory.getProduct();
ProductVO vo = new ProductVO();

String query_product_code = Utility.trimNull(request.getParameter("query_product_code"));
String query_product_name = Utility.trimNull(request.getParameter("query_product_name"));
input_bookCode = new Integer(1);

vo = new ProductVO();
vo.setBook_code(input_bookCode);
vo.setProduct_code(query_product_code);
vo.setProduct_name(query_product_name);
vo.setInput_man(input_operatorCode);
//选择产品
List list = local.queryProductTable(vo);
Map map = new HashMap();
%>
<HTML>
<HEAD>
<TITLE>选择产品</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<link rel="stylesheet" href="<%=request.getContextPath()%>/includes/default.css" type=text/css  >
<link rel="stylesheet" href="<%=request.getContextPath()%>/includes/jQuery/tree/style/demo.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/includes/jQuery/tree/style/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/tree/script/jquery.ztree-2.6.min.js"></script>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var setting; 
setting = {
	expandSpeed: "",
	checkable : false,
	async: true,
	expandSpeed : "show", //折叠时的动画速度 或 取消动画
	asyncParam: ["id","productId"],
	asyncParamOther : {"query_product_code":""},
	asyncParamOther : {"query_product_name":""}
};

//加载初始化Tree
$(document).ready(function(){
	$("body").bind("mousedown", 
		function(event){
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
				$("#rMenu").hide();
			}
		}
	);
});


//产品搜索
function selectProduct(){
	//tree查询参数赋值
	setting.asyncParamOther.query_product_code = $("#query_product_code").val();
	setting.asyncParamOther.query_product_name = encodeURI(encodeURI($("#query_product_name").val()));

	//获取表格数据
	$.post("product_table_select.jsp", 
		{
			 "query_product_code": $("#query_product_code").val(), 
			 "query_product_name": encodeURIComponent($("#query_product_name").val())
		},
		function callback(json){
			var userlist = $.parseJSON(json);//json转换
			var table = document.getElementById("mybody"); 
			//先删除
			var rowlen=table.rows.length;   
		    for(var s=rowlen-1;s>=0;s--){   
		        table.deleteRow(s);   
		    }
			//再添加 
			$.each(userlist, function(key, val) {
			    var tr = table.insertRow();

				tr.style.setAttribute("backgroundColor","#FFFFFF");
				tr.style.setAttribute("cursor","pointer");
				tr.setAttribute("title","双击选择产品");
				tr.ondblclick = function(){dbProductTable(val.PRODUCT_ID, val.PRODUCT_CODE);};
	
			    var td_1=tr.insertCell();  td_1.setAttribute("width","70px"); td_1.setAttribute("height","25px");
			    var td_2=tr.insertCell();
	
				td_1.innerHTML=val.PRODUCT_CODE;
				td_2.innerHTML=val.PRODUCT_NAME;
			});
	  	}
  	);
}

//回车调用搜索方法
function keyDown(){
	if (event.keyCode == 13){
		selectProduct();
	}
}

//双击产品表
function dbProductTable(product_id, product_code){
	$.ajax({
	  type: 'POST',
	  url: "product_type_select_do.jsp",
	  data: {
			product_id:product_id,
			product_code:product_code
	  },
	  success: function(data){
			window.opener = null;
			window.returnValue =data;
			window.close();
	  },
	  dataType: "html"
	});
}
</SCRIPT>
</HEAD>
<BODY class="body body-nox">
<form name="theform" method="post" action="#">
<input type="hidden" name="tree_table_flag" id="tree_table_flag" value="<%=input_operator.getTree_table_flag()%>">

<table border="0" width="100%" cellspacing="2" cellpadding="2">
	<tr>
		<td align="left" style="padding-left:10px;" class="page-title"><b>选择产品</b></td>
	</tr>
	<tr>
		<td colspan="2">
		<TABLE style="BORDER-COLLAPSE: collapse" borderColor=#CCFFFF cellSpacing=4 rules=rows width="100%" align=left bgColor=#e1f8ff border=1>
			<TBODY>
				<TR>
					<TD align="right">产品代码：</TD>
					<td>
						<input type="text" maxlength="16" name="query_product_code" id="query_product_code" value="<%=query_product_code%>" onkeydown="javascript:keyDown();" maxlength=8 size="19">	
					</td>
					<td align="right">产品名称：</td>
					<td colspan="3">
						<input type="text" maxlength="16" name="query_product_name" id="query_product_name" value="<%=query_product_name%>" onkeydown="javascript:keyDown();" size="61">
					</td>
				</TR>
				<TR>
					<td align="center" colspan="6" class="btn-wrapper" style="top:2px;right:0;">
					<font color="red">说明：鼠标双击选择产品</font>
						<button type="button"  onkeydown="javascript:nextKeyPress(this);"  class="xpbutton3" name="btnSosuo1" onclick="javascript:dbProductTable(0,'');">全部产品</button>&nbsp;
						<button type="button"  onkeydown="javascript:nextKeyPress(this);"  class="xpbutton3" accessKey=s name="btnSosuo" onclick="javascript:selectProduct();">搜索(<u>S</u>)</button>&nbsp;
						<button type="button"  onkeydown="javascript:nextKeyPress(this);"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();">取消(<u>C</u>)</button>&nbsp;
					</td>
				</TR>
			</TBODY>
		</TABLE>
		</td>
	</tr>
</table>

<!-- 表 -->
<div style="overflow-y:hidden; height:422px; width:630px; text-align:left;">	
	<div id="table_id" style="display: block">
		<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
			<tr class="trh">
				<td width="70px;">产品编号</td>
				<td>产品名称</td>
			</tr>
		</table>
		<div style="overflow:auto; height:380px;width:630px;text-align:left;">
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" >
				<tbody id="mybody">
				<%
					int iCount = 0;
					int iCurrent = 0;
					if(list != null && list.size() != 0){
					for(int i=0; i<list.size(); i++){
						map = (Map)list.get(i);
				%>
			  	<tr style="background-color: #FFFFFF;cursor: pointer;" class="tr<%=(iCurrent % 2)%>" title="双击选择产品" ondblclick="javascript:dbProductTable('<%=Utility.trimNull(map.get("PRODUCT_ID")) %>','<%=Utility.trimNull(map.get("PRODUCT_CODE")) %>');">
					<td width="70px;" height="20px;"><%=Utility.trimNull(map.get("PRODUCT_CODE")) %></td>
					<td><%=Utility.trimNull(map.get("PRODUCT_NAME")) %></td>
				</tr>   
			<%
					iCurrent++;
					iCount++;
				}
			}
			%>	
			</tbody>
		</table>
		</div>
	</div>
</div>
</form>
</BODY>
</HTML>
