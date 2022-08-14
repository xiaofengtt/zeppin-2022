<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,enfo.crm.dao.*,java.util.*,java.math.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
	ScoreProductVo vo = new ScoreProductVo();
	ScoreProductBean scoreProductBean = new ScoreProductBean();

	Integer rule_id = new Integer(0);
	Integer product_id = Utility.parseInt(request.getParameter("product_id"),new Integer(0));
	Integer subproduct_id = Utility.parseInt(request.getParameter("subproduct_id"),new Integer(0));
	String product_name = "";
	String list_name = "";
	String summary = "";

	BigDecimal score_rate = new BigDecimal(0);
	
	vo.setProduct_id(product_id);
	vo.setSubproduct_id(subproduct_id);

	String[] s = request.getParameterValues("rule_id");
	//若选中不为空
	if (s != null){
		for(int i = 0;i < s.length; i++){
			if(s[i] != "0"){
				vo.setRule_id(new Integer(s[i]));
				scoreProductBean.delScoreProduct(vo);
			}
		}
	}

	IPageList pageList = null;
	Map map = null;
	pageList = scoreProductBean.queryScoreProduct(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
	Iterator it =  pageList.getRsList()!=null? pageList.getRsList().iterator(): new ArrayList().iterator();

	String options = Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48);
	options = options.replaceAll("\"", "'");
%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet"/>

		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/calendar.js"></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
		<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
		<script type="text/javascript">
			/*启动加载*/
			window.onload = function(){
				initQueryCondition();
				getSubProductOptions(document.theform.product_id.value,<%=subproduct_id%>,0);
			};

			function QueryAction(){
				disableAllBtn(true);
				document.theform.submit();
			}

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'queryScoreProduct.jsp?page=1&pagesize=' + document.theform.pagesize.value
								+ '&product_id=' + <%=product_id%>
								+ '&subproduct_id=' + document.theform.subproduct_id.value;
			}
			/*新增方法*/
			function AppendAction(){
				var url = "addScoreProduct.jsp";
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}


			//查询条件 加载对应产品的子产品
			function getSubProductOptions(value1,value2) {
				if(value1!=0){
					utilityService.getSubProductOptionS(value1,value2,{callback: function(data){
						if(data=='<option value="">请选择</option>'){
							document.getElementById('subProduct_style').style.display = 'none';			
						}else{ 		
							document.getElementById('subProduct_style').style.display = ''; 
						}			
						$("subProductOptions").innerHTML = "<select name='subproduct_id' style='width:335px;'>"+data+"</select>"
					}});
				}else{
					document.getElementById('subProduct_style').style.display = 'none';
				}
			}
	
			function setProduct(value){
				prodid=0;
				if (event.keyCode == 13 && value != "")	{	
			        j = value.length;
					for(i=0;i<document.theform.product_id.options.length;i++)
					{
						if(document.theform.product_id.options[i].text.substring(0,j)==value)
						{
							document.theform.product_id.focus();
							document.theform.product_id.options[i].selected=true;
							prodid = document.theform.product_id.options[i].value;
							getSubProductOptions(prodid,'');//加载子产品
							break;
						}	
					}
					if (prodid==0)
					{
						sl_alert('输入的产品编号不存在!');
						document.theform.productid.value="";
						document.theform.product_id.options[0].selected=true;	
					}		
				}
			}
			function searchProduct(value) {
				prodid=0;
				if (value != "") {
			        j = value.length;
			        
					for(i=0;i<document.theform.product_id.options.length;i++) {
						if(document.theform.product_id.options[i].text.substring(0,j)==value) {
							document.theform.product_id.focus();
							document.theform.product_id.options[i].selected=true;
							prodid = document.theform.product_id.options[i].value;
							getSubProductOptions(prodid,'');//加载子产品
							break;
						}	
					}
					if (prodid==0) {
						sl_alert('输入的产品编号不存在!');
						document.theform.productid.value="";
						document.theform.product_id.options[0].selected=true;	
					}
					document.theform.product_id.focus();					
				}	
			}

			function searchProductName(value){
				var list = [];
				var list1 = [];
				document.getElementById("select_id").innerHTML = 
							"<SELECT name='product_id' onkeydown='javascript:nextKeyPress(this)' onchange='javascript:getSubProductOptions(this.value,'');'  style='width: 335px;'>"+"<%=options%>"+"</SELECT>";
				if (value != ""){
					for (var i=0; i<document.theform.product_id.options.length; i++) {
						var j = document.theform.product_id.options[i].text.indexOf(value);
						if (j>0) {
							list.push(document.theform.product_id.options[i].text);
							list1.push(document.theform.product_id.options[i].value);
						}
					}	
					if (list.length==0) {
						sl_alert('输入的产品名称不存在 ！');//输入的产品名称不存在
						document.theform.productid.value = "";
						document.theform.product_id.options[0].selected=true;
					} else {
						document.theform.product_id.options.length=0;
						for (var i=0; i<list.length; i++)
							document.theform.product_id.options.add(new Option(list[i],list1[i]));
					}
					document.theform.product_id.focus();
				}
			}
			
			/*删除方法*/
			function DelAction(){	
				if(confirmRemove(document.theform.rule_id)){
					disableAllBtn(true);
					document.theform.submit();
				}
			}

			/*修改方法*/
			function modiAction(rule_id,product_name,list_name,score_rate,summary ){
				var url = "modiScoreProduct.jsp?rule_id="+rule_id+"&product_name="+product_name+"&list_name="+list_name+"&score_rate="+score_rate+"&summary="+summary;		
				if(showModalDialog(url,'', 'dialogWidth:380px; dialogHeight:350px; status:0;help:0')){
					sl_update_ok();
					location.reload();
				}
				showWaitting(0);
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="scoreProduct.jsp">
			<div id="queryCondition" class="qcMain" style="display:none;width:450px;">
				<table  id="qcTitle" class="qcTitle" align="center" width="100%" cellspacing="0" cellpadding="2">  				
			  		<tr>
						<td><%=LocalUtilis.language("message.queryCondition",clientLocale)%>：</td><!--查询条件-->
			   			<td align="right">
			   				<button class="qcClose" accessKey=c id="qcClose" name="qcClose" onclick="javascript:cancelQuery();"></button>
			   			</td>
					</tr>		
				</table> 
				<!-- 要加入的查询内容 -->
				<table border="0" width="100%" cellspacing="2" cellpadding="2">
					<tr>
				    	<td align="right">产品编号:</td >
				        <td align="left" >
				        	<input type="text" maxlength="16" name="productid" onkeydown="javascript:setProduct(this.value);nextKeyPress(this);" maxlength=8 size="13">&nbsp;
				        	<button class="searchbutton" onkeydown="javascript:nextKeyPress(this)" onclick="javascript:searchProduct(document.theform.productid.value);" /></button>
				        </td>
						<td align="right">产品名称:</td>
						<td>
							<input name="product_name" value='' onkeydown="javascript:nextKeyPress(this)" size="18">&nbsp;
							<button class="searchbutton" onclick="javascript:searchProductName(product_name.value);" /></button>
						</td>
				    </tr>
					<tr>
					    <td align="right">产品选择:</td >
					    <td align="left" colspan="3" id="select_id">
					       <SELECT name="product_id" onkeydown="javascript:nextKeyPress(this)" onchange="javascript:getSubProductOptions(this.value,'');"  style="width: 335px;">
					       	<%=Argument.getProductListOptions(input_bookCode, product_id,"",input_operatorCode,48)%></SELECT>
					    </td>
					</tr>
					<tr id="subProduct_style" style="display:none;">
						<td align="right">子产品名称:</td>
						<td colspan="3" id="subProductOptions">
				
						</td>
					</tr>
					<tr>
						<td align="center" colspan="4">
							<button class="xpbutton3" accessKey=O id="btnQuery" onclick="javascript:QueryAction();">
								<%=LocalUtilis.language("message.confirm",clientLocale)%>(<u>O</u>)
							</button><!--确定-->	 				
						</td>
					</tr>
				</table>
			</div>
			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b><%=menu_info%></b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=q id="queryButton" name="queryButton" onclick="javascript:QueryAction();">查询(<u>Q</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=n id="btnAppend" name="btnQue" title="新建" onclick="javascript:AppendAction();">新建(<u>N</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="删除" onclick="javascript:DelAction();">删除(<u>D</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.cust_id,this);">
							序号
						</td><!--编号-->
						<td align="center" width="*">产品</td>
						<td align="center" width="*">子产品</td>
						<td align="center" width="*">倍率</td>
						<td align="center" width="*">说明</td>
						<td align="center" width="*">修改</td>
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							rule_id = Utility.parseInt(Utility.trimNull(map.get("RULE_ID")),new Integer(0));
							product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
							list_name = Utility.trimNull(map.get("LIST_NAME"));
							score_rate = Utility.parseDecimal(Utility.trimNull(map.get("SCORE_RATE")), new BigDecimal(0), 2,"1");
							summary = Utility.trimNull(map.get("SUMMARY"));
							iCurrent++;
							iCount++;
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="rule_id" value="<%=rule_id%>" class="flatcheckbox">
									</td>
									<td width="90%" align="center"><%=iCurrent%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=product_name%></td>
						<td align="center"><%=list_name%></td>
						<td align="center"><%=score_rate%></td>
						<td align="center"><%=summary%></td>
						<td align="center">
							<img border="0" src="<%=request.getContextPath()%>/images/edit2.gif" width="16" height="16" style="cursor:hand" onclick="javascript:modiAction('<%=rule_id%>','<%=product_name %>','<%=list_name %>','<%=score_rate %>','<%=summary %>');"/>
						</td>
					</tr>
					<%
						}
						for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){ 
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="6"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>