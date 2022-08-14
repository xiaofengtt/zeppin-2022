<%@ page contentType="text/html; charset=GBK"   import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*,enfo.crm.affair.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
//获取url中的值
Integer teamId = Utility.parseInt(request.getParameter("teamId"),new Integer(0));
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));
String first_flag = request.getParameter("first_flag");//判断是否是第一次显示页面
String showT = Utility.trimNull(request.getParameter("showT"));
if(showT == ""){
	showT = "true";
}
//创建实例
ProductLocal product = EJBFactory.getProduct();//产品事件
ProductVO vo = new ProductVO();//产品对象
List productList = null;
Map productMap = null;
//产品信息
String productCode = "";//产品编号
String productJc = "";//产品简称
BigDecimal preMoney = new BigDecimal(0);//预发行金额
Integer preNum = new Integer(0);//预发行份数
BigDecimal minMoney = new BigDecimal(0);//最低发行金额
Integer preStartDate = null;//推介起始日期
Integer preEndDate = null;//推介终止日期
//团队信息
String teamName = "";//团队名称
String description = "";//团队描述
BigDecimal freeMoney = new BigDecimal(0);//可分配销售配额
BigDecimal quotaMoney = new BigDecimal(0);//销售配额
BigDecimal alreadyPre = new BigDecimal(0);//已使用配额
Integer quotaQualifiedNum = new Integer(0);//小额合格投资人数
Integer	alreadyQualifiedNum = new Integer(0);//已购投资人数
Integer freeNum = new Integer(0);//可分配小额合格投资人数
Integer teamNum = new Integer(0);//下属团队个数
Integer parentId = new Integer(0);//父团队ID
Integer pParentId = new Integer(0);//父父团队ID
BigDecimal quotaMoneyTotal = new BigDecimal(0);//设置总的销售配额
int quotaQualifiedNumTotal = 0;//设置小额合格投资人数和
Integer tzQualifiedNum =  new Integer(0);//可调整参数
//个人信息
Integer opCode = new Integer(0);
String opName = "";
Integer grandfID = new Integer(0);

boolean queryAll = input_operator.hasFunc(menu_id, 131);
System.out.println("---menu_id---"+menu_id);

if(subProductId.intValue()==0){
	if(productId.intValue()!=0){
		vo.setProduct_id(productId);
		productList = product.load(vo);
		productMap = (Map)productList.get(0);
		productCode = Utility.trimNull(productMap.get("PRODUCT_CODE"));
		productJc = Utility.trimNull(productMap.get("PRODUCT_JC"));
		preMoney = Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MONEY")), new BigDecimal(0),2,"1");
		preNum = Utility.parseInt(Utility.trimNull(productMap.get("PRE_NUM")),new Integer(0));
		minMoney = Utility.parseDecimal(Utility.trimNull(productMap), new BigDecimal(0),2,"1");
		preStartDate = Utility.parseInt(Utility.trimNull(productMap.get("PRE_START_DATE")),new Integer(0));
		preEndDate = Utility.parseInt(Utility.trimNull(productMap.get("PRE_END_DATE")),new Integer(0));
	}
}else{
	vo.setProduct_id(productId);
	vo.setSub_product_id(subProductId);
	productList = product.listSubProduct(vo);
	productMap = (Map)productList.get(0);
	preMoney = Utility.parseDecimal(Utility.trimNull(productMap.get("PRE_MONEY")), new BigDecimal(0),2,"1");
	preNum = Utility.parseInt(Utility.trimNull(productMap.get("PRE_NUM")),new Integer(0));
	minMoney = Utility.parseDecimal(Utility.trimNull(productMap.get("MIN_MONEY")), new BigDecimal(0),2,"1");
	preStartDate = Utility.parseInt(Utility.trimNull(productMap.get("PRE_START_DATE")),new Integer(0));
	preEndDate = Utility.parseInt(Utility.trimNull(productMap.get("PRE_END_DATE")),new Integer(0));
}

Integer ID  = new Integer(0);
Integer id[] = Argument.getTeamID(input_operatorCode);
 
//当用户登录没有权限时，根据操作员Id来查teamId 和 判断是否查团队还是个人
if(id!=null && id.length!=0){
	ID = id[0];
	if((!queryAll)&&teamId.intValue()==0){
		teamId = ID;
		if(id[1].intValue()==1){
			showT = "false";
		}
	}
}

sUrl=request.getContextPath()+"/affair/base/sale_parameter_quota_all.jsp?productId="+productId.toString()+"&subProductId="+subProductId.toString()+"&teamId="+teamId.toString();
%>

<html>
	<head>

		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/widgets/ext/resources/css/ext-all.css" type="text/css"  rel="stylesheet"/> 

		<script language="javascript" src="<%=request.getContextPath()%>/includes/default.js"></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/contract.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/jQuery/js/jquery-1.8.3.js" ></script>

		<script language=javascript>
			window.onload=function(){
				utilityService.getSubProductFlag(<%=productId%>,function(data){
					var arrayL = data.split("$");
					var sub_product_flag = arrayL[0];
					if(sub_product_flag==1){
						document.getElementById("sub_product_flag").style.display="";
					}
				});

				if(<%=subProductId.intValue()%>!=0){
					document.getElementById("sub_product_flag").style.display="";
				}
			};
			//选择产品
			function selectProduct(product_id){
				if(product_id>0){
					utilityService.getSubProductFlag(product_id,function(data){
						var arrayL = data.split("$");
						var sub_product_flag = arrayL[0];
						if(sub_product_flag==1){
							disableAllBtn(true);
							document.getElementById("theform").submit();
						}else{
							disableAllBtn(true);
							document.getElementById("subProductId").value = 0 ;
							document.getElementById("theform").submit();
						}	
					});			
				}else{
					sl_alert("必需选择产品,请重新选择!");
					return false;
				}
			}
			//选择子产品
			function selectSubProduct(subProductId){
				disableAllBtn(true);
				document.getElementById("theform").submit();
			}
			//选择团队
			function showTeam(productId){
				<%if(!queryAll&&id[1].intValue()==1){%>
					alert("没有团队操作权限");
					return false;
				<%}%>
				if(productId==0){
					alert("请先选择产品");
				}else{
					var url = "<%=request.getContextPath()%>/affair/base/choose_team.jsp?&productId="+productId+"&sub_productId="+document.getElementById("subProductId").value;
					var return_url = showModalDialog(url,'', 'dialogWidth:360px;dialogHeight:350px;status:0;help:0');
					if(return_url != null && return_url.length > 0){
						if(return_url == 0){
							alert("没有下级团队");
						}else{
							location = return_url;
						}
					}
				}
			}
			//查看团队成员
			function showTeamInfo(team_name){
				var _event = window.event.srcElement;
				var url = "<%=request.getContextPath()%>/marketing/base/team_member.jsp?team_id="+arguments[0];
				if(showModalDialog(url,'', 'dialogWidth:360px;dialogHeight:350px;status:0;help:0')){
					sl_update_ok();
					location='<%=sUrl%>';
				}	
			}
			//设置团队配额
			function showInfoT(productId,teamId,teamName,subProductId,quotaMoney,quotaQualifiedNum,freeMoney,freeNum,tzQualifiedNum){
				if(productId==0||productId==null){
					alert("请选择产品！");
					return false;
				}
				var url = '<%=request.getContextPath()%>/marketing/base/sale_parameter_team_QuotaUpdate_new.jsp?productId=' + productId + '&subProductId='+ subProductId;
				url =  url+'&teamId='+ teamId+'&teamName='+teamName +'&quotaMoney='+quotaMoney+'&quotaQualifiedNum='+quotaQualifiedNum+"&freeMoney="+freeMoney+"&freeNum="+freeNum+"&tzQualifiedNum="+tzQualifiedNum;
				if(showModalDialog(url , '' , 'dialogWidth:380px;dialogHeight:350px;status:0;help:0') != null)
				{
					sl_update_ok();
					location='<%=sUrl%>';
				}
			}
			//设置个人配额
			function showInfoP(productId,teamId,opCode,opName,subProductId,freeMoney,quotaMoney,quotaQualifiedNum,freeNum,tzQualifiedNum){
				if(productId==0||productId==null){
					alert("请选择产品！");
					return false;
				}
				var url='<%=request.getContextPath()%>/affair/base/sale_parameter_person_QuotaUpdate_new.jsp?productId=' + productId + '&teamId='+ teamId +'&opCode=' + opCode+'&opName='+opName+'&subProductId='+subProductId;
				url = url+'&freeMoney='+freeMoney+'&quotaMoney='+quotaMoney+"&quotaQualifiedNum="+quotaQualifiedNum+"&freeNum="+freeNum+"&tzQualifiedNum="+tzQualifiedNum;
				if(showModalDialog(url,'','dialogWidth:380px;dialogHeight:350px;status:0;help:0') != null){	
					sl_update_ok();
					location = '<%=sUrl%>'+"&showT=false";
				}
			}
			//下一级团队
			function nextTeam(teamId,quotaMoney){
				if(quotaMoney!=0 &&quotaMoney!=null){
					var url = "sale_parameter_quota_all.jsp?productId="+<%=productId%>+"&subProductId="+<%=subProductId%>+"&teamId="+teamId;
					location = url;
				}else{
					alert("请先设置本团队的配额");
				}
			}
			//上一级团队
			function lastTeam(parentId){
				var url = "sale_parameter_quota_all.jsp?productId="+<%=productId%>+"&subProductId="+<%=subProductId%>+"&teamId="+parentId;
				location = url;
			}
			//下属个人
			function nextPerson(teamId,quotaMoney){
				if(quotaMoney!=0 &&quotaMoney!=null){
					var url = "sale_parameter_quota_all.jsp?productId="+<%=productId%>+"&subProductId="+<%=subProductId%>+"&teamId="+teamId+"&showT=false";
					location = url;
				}else{
					alert("请先设置本团队的配额");
				}
			}
			//查询产品
			function searchProductList(value,input_operatorCode,flag) {
				$.ajax({
					url: "search_product_list.jsp",
					type : "get",
					data: {inputSearch:value,flag:flag,input_operatorCode:input_operatorCode},
					success: function(data){
						newData = data.replace(/\r\n/g,'');

						if(newData == ""){
							if(flag==0){
								sl_alert("输入的产品编号不存在！");
							}else{
								sl_alert("输入的产品名称不存在！");
							}
							var obj = document.theform.productId; 
							obj.options[0].selected = true;
						}else{
							$("#productname").empty();
							$("#productname").append("<option selected value=''>请选择</option>");
							$("#productname").append(newData);
						}
					}
				});
				document.theform.productname.focus();
			}
			function searchProductListKey(value,input_operatorCode,flag) {
				if (event.keyCode == 13 && value != "") {
			       searchProductList(value,input_operatorCode,flag)
				}
			}
		</script>
	</head>

	<body class="body">
		<%@ include file="/includes/waiting.inc"%>
		<form id="theform" name="theform" method="post" action="sale_parameter_quota_all.jsp">
			<div align="left">
				<img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28">
				<font color="#215dc6"><b><%=menu_info%></b></font>
				<hr noshade color="#808080" size="1">
			</div>
			<table  border="0" width="100%" cellspacing="4" cellpadding="2" style="border: 1px; border-style: dashed; border-color: blue; margin-top:5px;">
				<tr>
					<td align="right">产品编号 :&nbsp;&nbsp;</td><!--产品编号-->
					<td align="left">
						<input type="text" maxlength="16" name="productCode" size="12" onkeydown="javascript:searchProductListKey(document.theform.productCode.value,<%=input_operatorCode %>,0);"/>&nbsp;&nbsp;
						<button class="searchbutton" onclick="javascript:searchProductList(document.theform.productCode.value,<%=input_operatorCode %>,0);"/></button>
						&nbsp;&nbsp;产品名称 :&nbsp;&nbsp;
						<input type="text" maxlength="16" name="productName" size="12" onkeydown="javascript:searchProductListKey(document.theform.productName.value,<%=input_operatorCode %>,1);"/>&nbsp;&nbsp;
						<button class="searchbutton"onclick="javascript:searchProductList(document.theform.productName.value,<%=input_operatorCode %>,1);"/></button>
					</td>
				</tr>	
				<tr>
					<td align="right">产品选择 :&nbsp;&nbsp;</td>
					<td align="left">
						<select size="1" name="productId" class="productname" id="productname" onchange="selectProduct(this.value)">
							<option value="0">请选择 </option><!--请选择-->
							<%=Argument.getProductListOptionsCRM(new Integer(1),"",new Integer(102),input_operatorCode,"","",productId)%>
						</select>
					</td>
				</tr>
				<tr id="sub_product_flag" style="display:none;">
					<td align="right">子产品名称 :&nbsp;&nbsp;</td>
					<td align="left" id="select_id">
						<select size="1" id="subProductId" name="subProductId" onkeydown="javascript:nextKeyPress(this)" class="subProductId" onchange="javascript:selectSubProduct(this.value);">
							<%=Argument.getSubProductOptions(productId, new Integer(0),subProductId)%>
						</select>
					</td>
				</tr>
				<%if(subProductId.intValue()==0){ %>
				<tr>
					<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--产品简称-->
					<td  align="left">
						<input type="text" id="productJC" name="productJC" readonly="readonly" class="edline" value="<%=productJc %>" size="32">
					</td>
				</tr>
				<%} %>
				<tr>
					<td align="right" width="120px">预发行份数 :&nbsp;&nbsp;</td>
					<td><input type="text" name="preNum" readonly="readonly" class="edline" value="<%=preNum %>" size="32"></td>
				</tr>
				<tr>
					<td align="right" width="120px">预发行金额 :&nbsp;&nbsp;</td>
					<td><input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=preMoney %>" size="32"></td>
					<td align="right" width="120px">最低发行金额 :&nbsp;&nbsp;</td>
					<td><input type="text" name="minMoney" readonly="readonly" class="edline" value="<%=minMoney %>" size="32"></td>
				</tr>
				<tr>
					<td align="right" width="120px"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> :&nbsp;&nbsp;</td><!--推介起始日期-->
					<td><input type="text" name="preStartDate" readonly="readonly" class="edline" value="<%=Format.formatDateLine(preStartDate) %>" size="32"></td>
					<td align="right" width="120px"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> :&nbsp;&nbsp;</td><!--推介终止日期-->
					<td><input type="text" name="preEndDate" readonly="readonly" class="edline" value="<%=Format.formatDateLine(preEndDate) %>" size="32"></td>
				</tr>
			</table>
			<br/>
			<%  //查看列表销售信息
				SaleParameterVO salevo = new SaleParameterVO();
				SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

				int iCount = 0;//统计多少项
			
				salevo.setProductID(productId);
				salevo.setInput_man(input_operatorCode);
				salevo.setTeamID(teamId);
				salevo.setSub_product_id(subProductId);
				if(queryAll){
					salevo.setQueryAll(new Integer(1));
				}else{
					salevo.setQueryAll(new Integer(0));
				}
				List list = null;
				Map map = null;
				if("true".equals(showT)){
					list = sale_parameter.queryTTeamValue2(salevo);
				}else{
					list = sale_parameter.queryPersonValueNew(salevo);
				}
			%>
			<table id="table3" width="100%">
				<tr align="right">
					<td>
						<button class="xpbutton4" name="btnEdit" onclick="javascript:showTeam('<%=productId %>');">选择团队</button>
					</td>
				</tr>
			</table>
			<br/>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class=trtagsort>
					<%if("true".equals(showT)){%>
					<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--团队名称-->
					<td align="center">可分配销售配额</td>
					<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
					<td align="center">已使用配额</td>
					<%if(user_id.intValue()!=5) {%>
					<td align="center">剩余可分配投资人数</td>
					<%} %>
					<td align="center">小额配额</td><!--合格投资人数量配额-->
					<td align="center">已使用小额</td><!--合格投资人数量配额-->
					<td align="center"><%=LocalUtilis.language("class.TeamMember",clientLocale)%> </td><!--团队成员-->
					<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
					<td align="center" width="160px">团队</td>
					<%}else{ %>
					<td align="center">个人名称</td>
					<td align="center">所在团队</td>
					<td align="center">可分配销售配额</td>
					<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--销售配额-->
					<td align="center">已销售配额</td><!--销售配额-->
					<%if(user_id.intValue()!=5) {%>
					<td align="center">可分配数量配额</td>
					<%} %>
					<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--合格投资人数量配额-->
					<td align="center">已购投资人数</td><!--合格投资人数量配额-->
					<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--编辑-->
					<td align="center" width="160px">团队</td>
					<%} %>
				</tr>
				<%
					for(int i=0; i<list.size(); i++){
						if("true".equals(showT)){
							iCount++;
							map = (Map)list.get(i);
							teamId = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));//团队ID
							teamName = Utility.trimNull(map.get("TEAM_NAME"));//团队名称
							description = Utility.trimNull(map.get("DESCRIPTION"));;//团队描述
							freeMoney = Utility.parseDecimal(Utility.trimNull(map.get("FREE_MONEY")), new BigDecimal(0),0,"1");//可分配销售配额
							quotaMoney = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),0,"1");//销售配额
							alreadyPre = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0),0,"1");//已使用配额
							//alreadySale = Utility.parseDecimal(Utility.trimNull(map.get("ALREADYSALE")), new BigDecimal(0),0,"1");//已销售配额
							quotaQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));;//小额合格投资人数
							alreadyQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM")),new Integer(0));//已购投资人数
							freeNum = Utility.parseInt(Utility.trimNull(map.get("FREE_NUM")),new Integer(0));//可分配小额合格投资人数
							teamNum = Utility.parseInt(Utility.trimNull(map.get("TEAM_NUM")),new Integer(0));//下属团队个数
							parentId = Utility.parseInt(Utility.trimNull(map.get("PARENT_ID")),new Integer(0));//父团队ID
							pParentId = Utility.parseInt(Utility.trimNull(map.get("GRANDF_ID")),new Integer(0));
							tzQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("TZ_QUALIFIED_NUM")),new Integer(0));
							quotaMoneyTotal = quotaMoneyTotal.add(quotaMoney);
							quotaQualifiedNumTotal += quotaQualifiedNum.intValue();
						}else{
							iCount++;
							map = (Map)list.get(i);
							opCode = Utility.parseInt(Utility.trimNull(map.get("OP_CODE2")),new Integer(0));
							opName = Utility.trimNull(map.get("OP_NAME"));
							teamName = Utility.trimNull(map.get("TEAM_NAME"));
							freeMoney = Utility.parseDecimal(Utility.trimNull(map.get("FREE_MONEY")), new BigDecimal(0),0,"1");
							quotaMoney = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),0,"1");
							alreadyPre = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0),0,"1");//已使用配额
							freeNum = Utility.parseInt(Utility.trimNull(map.get("FREE_NUM")),new Integer(0));
							quotaQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));
							alreadyQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM")),new Integer(0));
							parentId = Argument.getParentIdByTeamId(teamId); 
							quotaMoneyTotal = quotaMoneyTotal.add(quotaMoney);
							quotaQualifiedNumTotal += quotaQualifiedNum.intValue(); 
							grandfID = Utility.parseInt(Utility.trimNull(map.get("GRANDF_ID")),new Integer(0));
							tzQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("TZ_QUALIFIED_NUM")),new Integer(0));
						}
				%>
				<tr class="tr<%=iCount%2%>">
					<%if("true".equals(showT)){ %>	
					<td align="center"><%=teamName %></td>
					<td align="center"><%=freeMoney %></td>
					<td align="center"><%=quotaMoney %></td>
					<td align="center"><%=alreadyPre %></td>
					<%if(user_id.intValue()!=5){%>
					<td align="center"><%=freeNum %></td>
					<%}%>
					<td align="center"><%=quotaQualifiedNum %></td>
					<td align="center"><%=alreadyQualifiedNum %></td>
					<td align="center">
						<button class="xpbutton2" name="btnEdit" onclick="javascript:showTeamInfo('<%=teamId%>');">&gt;&gt;</button>
					</td>  
					<td align="center" width="50px">
						<%if (input_operator.hasFunc(menu_id, 102)) {%>
						<a href="#" onclick="javascript:showInfoT(<%=productId %>,<%=teamId %>,'<%=teamName %>',<%=subProductId %>,<%=quotaMoney %>,<%=quotaQualifiedNum %>,'<%=freeMoney %>','<%=freeNum %>','<%=tzQualifiedNum %>');">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16"/>
						</a>
						<%} %>
					</td>
					<td align="center">
						<%if((queryAll&&parentId.intValue()!=0) || (!queryAll&&parentId.intValue()!=ID.intValue())){ %>
						<button class="xpbutton2" style="margin-right: 5px;" name="btnEdit" onclick="lastTeam('<%=pParentId%>')">上一级</button>
						<%} %>
						<%if(!new Integer(0).equals(teamNum)) {%>
						<button class="xpbutton2" name="btnEdit" onclick="nextTeam('<%=teamId%>','<%=quotaMoney%>')">下一级</button>
						<%}else{ %>
						<button class="xpbutton2" name="btnEdit" onclick="nextPerson('<%=teamId%>','<%=quotaMoney%>')">下属个人</button>
						<%} %>
					</td>
					<%}else{ %>
					<td align="center"><%=opName %><input type="hidden" name="op_code" value="<%=opCode%>"></td>
					<td align="center"><%=teamName %></td>
					<td align="center"><%=freeMoney %></td>
					<td align="center"><%=quotaMoney %></td>
					<td align="center"><%=alreadyPre %></td>
					<%if(user_id.intValue()!=5) {%>
					<td align="center"><%=freeNum %></td>
					<%} %>
					<td align="center"><%=quotaQualifiedNum %></td>
					<td align="center"><%=alreadyQualifiedNum %></td> 
					<td align="center" width="50px">
						<%if (input_operator.hasFunc(menu_id, 102)) {%>
						<a href="#" onclick="javascript:showInfoP('<%=productId %>','<%=teamId %>','<%=opCode %>','<%=opName %>','<%=subProductId %>','<%=freeMoney %>','<%=quotaMoney %>','<%=quotaQualifiedNum %>','<%=freeNum %>','<%=tzQualifiedNum %>');">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16">
						</a>
						<%} %>
					</td>
					<td align="center">
						<%if(queryAll||grandfID.intValue()!=0){%>
						<button class="xpbutton2" style="margin-right: 5px;" name="btnEdit" onclick="lastTeam('<%=parentId%>')">上级团队</button>
						<%} %>
					</td>
					<%} %>
				</tr>
				<%} %>
				<tr class="trbottom">
				<%if("1".equals(first_flag)){ %>
					<td align="center">&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%></td>
					<td align="center"></td>
					<td align="center"><%=quotaMoneyTotal %></td>
					<td align="center"></td>
					<%if(user_id.intValue()!=5){%>
					<td align="center"></td>
					<%} %>
					<td align="center"><%=quotaQualifiedNumTotal %></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
					<td align="center"></td>
				<%}else{ %>
					<%if("true".equals(showT)){ %>
						<td align="center">&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%></td>
						<td align="center"></td>
						<td align="center"><%=quotaMoneyTotal %></td>
						<td align="center"></td>
						<%if(user_id.intValue()!=5){%>
						<td align="center"></td>
						<%} %>
						<td align="center"><%=quotaQualifiedNumTotal%></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>	
					<%} else{%>
						<%if(iCount==0){ %>
						<td align="left" <%if(user_id.intValue()==5) {%>colspan="8"<%}else{ %>colspan="9"<%} %>><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> </b></td>
						<td align="center">
							<button class="xpbutton2" style="margin-right:  5px;" name="btnEdit" onclick="history.back()">返回</button>
						</td>
						<%} else{%>
						<td align="center">&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=iCount%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"><%=quotaMoneyTotal %></td>
						<td align="center"></td>
						<%if(user_id.intValue()!=5){%>
						<td align="center"></td>
						<%} %>
						<td align="center"><%=quotaQualifiedNumTotal %></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<%} %>
					<%} %>
				<%} %>
				</tr>
			</table>
		</form>
		<p><%sale_parameter.remove();product.remove();%><%@ include file="/includes/foot.inc"%></p>
	</body>
</html>
