<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.vo.* ,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.affair.*,java.util.*,enfo.crm.marketing.*,java.math.*"%>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer productId = new Integer(0);
Integer sub_productId = Utility.parseInt(request.getParameter("sub_productId"),new Integer(0));
Integer teamType = Utility.parseInt(request.getParameter("teamType"),new Integer(0));
Integer teamOrMember = Utility.parseInt(request.getParameter("teamOrMember"),new Integer(0));
Integer preproduct_id = new Integer(0);

String sProduct_id = "";
String sPreproduct_id = "";
if (request.getParameter("productId")!=null) {
	String[] t = request.getParameter("productId").split("-");
	sPreproduct_id = t[0];
	preproduct_id = Utility.parseInt(sPreproduct_id, new Integer(0));

	if (t.length>1) {
		sProduct_id = t[1];
		productId = Utility.parseInt(sProduct_id, new Integer(0));
	}
}


String tempUrl = "";
String[] totalColumn = new String[0];

SaleParameterVO salevo = new SaleParameterVO();
SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

salevo.setProductID(productId);
salevo.setSub_product_id(sub_productId);
salevo.setPreproduct_id(preproduct_id);
salevo.setTeam_type(teamType);
salevo.setTeamID(teamOrMember);

IPageList pageList =sale_parameter.queryTeamOrPersonQuota(salevo,totalColumn,Utility.parseInt(sPage,1),Utility.parseInt(sPagesize,8));

tempUrl = tempUrl + "&productId=" + productId
				  + "&sub_productId=" + sub_productId 
				  + "&teamType=" + teamType
				  + "&teamOrMember=" + teamOrMember;
sUrl = sUrl + tempUrl;
//分页参数
int iCount = 0;
int iCurrent = 0;
Map map = null;
List list = pageList.getRsList();
%>


<HTML>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=7" >
	<META HTTP-EQUIV="Content-Type" content="text/html; charset=GBK">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
	<META HTTP-EQUIV="Expires" CONTENT="0">
	<BASE TARGET="_self">
	<title><%=LocalUtilis.language("index.menu.teamManagement",clientLocale)%> </title><!-- 团队管理 -->
	<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
	<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
	<script src="<%=request.getContextPath()%>/includes/default.vbs" language="vbscript"></script>
	<script src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js" language="javascript"></script>
	<SCRIPT SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js" LANGUAGE="javascript" ></SCRIPT>
	
	<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>

<script language="javascript">
	window.onload = function(){
		initQueryCondition();	
	}
	
	function QueryAction(){	
		if(document.theform.productId.value=="0-0"){alert("请选择产品！");return false;}
		if(document.getElementById("sub_product_flag").style.display!="none"&&document.theform.sub_productId.value==""){alert("请选择子产品！");return false;}
		if(document.theform.teamType.value==0){alert("请选择个人/团队！");return false;}
		disableAllBtn(true);
		var url = "sale_parameter_query.jsp?page=1&pagesize="+ document.theform.pagesize.value;
		url = url + "&productId=" + document.theform.productId.value;
		url = url + "&sub_productId=" + document.theform.sub_productId.value;
		url = url + "&teamType=" + document.theform.teamType.value;
		url = url + "&teamOrMember=" + document.theform.teamOrMember.value;
		window.location = url;
	}

function selectProduct(product_id){
	var sProduct_id = "";
	var sPreproduct_id = "";
	var preproduct_id;
	if (product_id!=null) {
		var t = product_id.split("-");
		sPreproduct_id = t[0];
		preproduct_id = parseInt(sPreproduct_id);

		if (t.length>1) {
			sProduct_id = t[1];
			product_id = parseInt(sProduct_id);
		}
	}
	if(product_id>0){
		utilityService.getSubProductFlag(product_id,function(data){
				var arrayL = data.split("$");
				var sub_product_flag = arrayL[0];			
				if(sub_product_flag==1){
					document.getElementById("sub_product_flag").style.display = "";
					utilityService.getSubProductOptionS(product_id,0,function(data1){
						$("select_id").innerHTML = "<select size='1' name='sub_productId' onkeydown='javascript:nextKeyPress(this)' class='productname'>"+
						data1+"</select>&nbsp;&nbsp;";
					});
				}else{
					document.getElementById("sub_productId").value = "";
					document.getElementById("sub_product_flag").style.display="none";
				}	
			}
		);
	}else{
		if(preproduct_id==0){
			sl_alert("产品不存在,请重新选择!");
			return false;
		}
		
	}
}

function selectTeam(teamType){
	if(teamType!=0){
		utilityService.getTeamOrTeammembers(teamType,function(data){
			$("select_id1").innerHTML = "<select name='teamOrMember' onkeydown='javascript:nextKeyPress(this)'>" + data + "</select>";
		});
	}else{
		$("select_id1").innerHTML = "<select name='teamOrMember' onkeydown='javascript:nextKeyPress(this)'></select>"
	}
}
</script>
</head>

<body class="body">
<form id="theform" method="post" name="theform">

<div id="queryCondition" class="qcMain" style="display:none;width:480px;">
	<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
  		<tr>
			<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%> ：</td><!-- 查询条件 -->
   			<td align="right">
   				<button type="button" class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
   			</td>
		</tr>
	</table> 
	<table border="0" width="100%" cellspacing="2" cellpadding="2">
		<tr>
			<td align="right">产品名称 : </td>
			<td align="left">
				<select name="productId" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectProduct(this.value);" class="productname">
					<%=Argument.getProductListOptions2(new Integer(2), "", input_operatorCode, preproduct_id+"-"+productId)%>
				</select>
			</td>
		</tr>
		<tr id="sub_product_flag" style="display:none;">
			<td align="right">子产品名称 :&nbsp;&nbsp;</td>
			<td align="left" id="select_id">
				<select name="sub_productId" onkeydown="javascript:nextKeyPress(this)" class="productname">
					<%=Argument.getSubProductOptions(productId, new Integer(0),sub_productId)%>			
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">个人/团队 ：</td>
			<td align="left">
				<select name="teamType" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:selectTeam(this.value);">
					<option value="0">请选择</option>
					<option value="1">个人</option>
					<option value="2">团队</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right">选择个人/团队名称 ：</td>
			<td align="left" id="select_id1">
				<select name='teamOrMember' onkeydown='javascript:nextKeyPress(this)'>
					
				</select>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="4">
                <!-- 确认 -->
				<button type="button" class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();"><%=LocalUtilis.language("message.ok",clientLocale)%> (<u>O</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;	 				
			</td>
		</tr>			
	</table>
</div>

<table cellSpacing=0 cellPadding=0 width="100%" align=center border=0>
	<tr>
		<td align="left">
			<img border="0" src="<%=request.getContextPath()%>/images/member.gif" align="absmiddle" width="32" height="28">
			<font color="#215dc6"><b><%=menu_info%></b></font>
		</td>
	</tr>
	<tr>
		<td align="right">
            <!-- 查询 -->
			<button type="button" class="xpbutton3" accessKey=q id="queryButton" name="queryButton" title="<%=LocalUtilis.language("message.query",clientLocale)%> " onclick="javascript:void(0);"><%=LocalUtilis.language("message.query",clientLocale)%> (<u>Q</u>)</button>
		</td>
	</tr>
	<tr>
		<td>
			<hr noshade color="#808080" size="1">
		</td>
	</tr>
</table>

<table valign="middle" align="center" cellspacing="0" cellpadding="0" width="100%">
<tr>
<td>
	<TABLE width="100%" class="tablelinecolor" cellspacing="1" cellpadding="1">
	<tr class="trh">
		<td align="center">名称</td>
		<td align="center">所属团队</td>
		<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
		<td align="center">已销售配额</td><!--销售配额-->
		<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
		<td align="center">已购投资人数</td><!--合格投资人数量配额-->
     </tr>
<%
String team_name = "";
Integer team_id = null;
String father_team_name = "";
BigDecimal quota_money = new BigDecimal(0);//销售配额
BigDecimal already_quota_money = new BigDecimal(0);
Integer quota_qualified_num = new Integer(0);//合格投资人数量配额
Integer already_quota_qualified_num = new Integer(0);
for(int i=0;i<list.size();i++){
	map = (Map)list.get(i);	
	iCount++;
	team_id = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));
	team_name = Utility.trimNull(map.get("TEAM_NAME"));
	father_team_name = Utility.trimNull(map.get("FATHER_TEAM_NAME"));
	quota_money = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),2,"1");
	already_quota_money = Utility.parseDecimal(Utility.trimNull(map.get("ALREADYSALE")), new BigDecimal(0),2,"1");
	quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
	already_quota_qualified_num = Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
%>   
         <tr class="tr<%=iCount%2%>">
            <td align="center"><%=team_name %></td>
            <td align="center"><%=father_team_name %></td>
            <td align="center"><%=quota_money %></td>
            <td align="center"><%=already_quota_money %></td>    
	        <td align="center"><%=quota_qualified_num %></td>
	        <td align="center"><%=already_quota_qualified_num %></td>                
         </tr>   
<%}%>   
<%
for(int i=0;i<(8-iCount);i++){
%>      	
         <tr class="tr0">
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>       
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>
            <td align="center">&nbsp;</td>            
         </tr>           
<%}%> 
		<tr class="trbottom">
            <!-- 合计 --><!-- 项 -->
			<td align="left" class="tdh" colspan="8">
				&nbsp;
				<b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=pageList.getTotalSize()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> 
				</b>
			</td>
		</tr>   
	</TABLE>
	</td>
	</tr>
	<tr>
    	<td>
    		<table border="0" width="100%">
				<tr valign="top">
					<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
				</tr>
			</table>
    	</td>
    </tr>
</table>

</form>
</body>
</html>