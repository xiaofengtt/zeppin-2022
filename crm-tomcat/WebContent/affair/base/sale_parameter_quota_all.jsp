<%@ page contentType="text/html; charset=GBK"   import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.marketing.*,enfo.crm.affair.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<% 
//��ȡurl�е�ֵ
Integer teamId = Utility.parseInt(request.getParameter("teamId"),new Integer(0));
Integer productId = Utility.parseInt(request.getParameter("productId"),new Integer(0));
Integer subProductId = Utility.parseInt(request.getParameter("subProductId"),new Integer(0));
String first_flag = request.getParameter("first_flag");//�ж��Ƿ��ǵ�һ����ʾҳ��
String showT = Utility.trimNull(request.getParameter("showT"));
if(showT == ""){
	showT = "true";
}
//����ʵ��
ProductLocal product = EJBFactory.getProduct();//��Ʒ�¼�
ProductVO vo = new ProductVO();//��Ʒ����
List productList = null;
Map productMap = null;
//��Ʒ��Ϣ
String productCode = "";//��Ʒ���
String productJc = "";//��Ʒ���
BigDecimal preMoney = new BigDecimal(0);//Ԥ���н��
Integer preNum = new Integer(0);//Ԥ���з���
BigDecimal minMoney = new BigDecimal(0);//��ͷ��н��
Integer preStartDate = null;//�ƽ���ʼ����
Integer preEndDate = null;//�ƽ���ֹ����
//�Ŷ���Ϣ
String teamName = "";//�Ŷ�����
String description = "";//�Ŷ�����
BigDecimal freeMoney = new BigDecimal(0);//�ɷ����������
BigDecimal quotaMoney = new BigDecimal(0);//�������
BigDecimal alreadyPre = new BigDecimal(0);//��ʹ�����
Integer quotaQualifiedNum = new Integer(0);//С��ϸ�Ͷ������
Integer	alreadyQualifiedNum = new Integer(0);//�ѹ�Ͷ������
Integer freeNum = new Integer(0);//�ɷ���С��ϸ�Ͷ������
Integer teamNum = new Integer(0);//�����ŶӸ���
Integer parentId = new Integer(0);//���Ŷ�ID
Integer pParentId = new Integer(0);//�����Ŷ�ID
BigDecimal quotaMoneyTotal = new BigDecimal(0);//�����ܵ��������
int quotaQualifiedNumTotal = 0;//����С��ϸ�Ͷ��������
Integer tzQualifiedNum =  new Integer(0);//�ɵ�������
//������Ϣ
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
 
//���û���¼û��Ȩ��ʱ�����ݲ���ԱId����teamId �� �ж��Ƿ���Ŷӻ��Ǹ���
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
			//ѡ���Ʒ
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
					sl_alert("����ѡ���Ʒ,������ѡ��!");
					return false;
				}
			}
			//ѡ���Ӳ�Ʒ
			function selectSubProduct(subProductId){
				disableAllBtn(true);
				document.getElementById("theform").submit();
			}
			//ѡ���Ŷ�
			function showTeam(productId){
				<%if(!queryAll&&id[1].intValue()==1){%>
					alert("û���ŶӲ���Ȩ��");
					return false;
				<%}%>
				if(productId==0){
					alert("����ѡ���Ʒ");
				}else{
					var url = "<%=request.getContextPath()%>/affair/base/choose_team.jsp?&productId="+productId+"&sub_productId="+document.getElementById("subProductId").value;
					var return_url = showModalDialog(url,'', 'dialogWidth:360px;dialogHeight:350px;status:0;help:0');
					if(return_url != null && return_url.length > 0){
						if(return_url == 0){
							alert("û���¼��Ŷ�");
						}else{
							location = return_url;
						}
					}
				}
			}
			//�鿴�Ŷӳ�Ա
			function showTeamInfo(team_name){
				var _event = window.event.srcElement;
				var url = "<%=request.getContextPath()%>/marketing/base/team_member.jsp?team_id="+arguments[0];
				if(showModalDialog(url,'', 'dialogWidth:360px;dialogHeight:350px;status:0;help:0')){
					sl_update_ok();
					location='<%=sUrl%>';
				}	
			}
			//�����Ŷ����
			function showInfoT(productId,teamId,teamName,subProductId,quotaMoney,quotaQualifiedNum,freeMoney,freeNum,tzQualifiedNum){
				if(productId==0||productId==null){
					alert("��ѡ���Ʒ��");
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
			//���ø������
			function showInfoP(productId,teamId,opCode,opName,subProductId,freeMoney,quotaMoney,quotaQualifiedNum,freeNum,tzQualifiedNum){
				if(productId==0||productId==null){
					alert("��ѡ���Ʒ��");
					return false;
				}
				var url='<%=request.getContextPath()%>/affair/base/sale_parameter_person_QuotaUpdate_new.jsp?productId=' + productId + '&teamId='+ teamId +'&opCode=' + opCode+'&opName='+opName+'&subProductId='+subProductId;
				url = url+'&freeMoney='+freeMoney+'&quotaMoney='+quotaMoney+"&quotaQualifiedNum="+quotaQualifiedNum+"&freeNum="+freeNum+"&tzQualifiedNum="+tzQualifiedNum;
				if(showModalDialog(url,'','dialogWidth:380px;dialogHeight:350px;status:0;help:0') != null){	
					sl_update_ok();
					location = '<%=sUrl%>'+"&showT=false";
				}
			}
			//��һ���Ŷ�
			function nextTeam(teamId,quotaMoney){
				if(quotaMoney!=0 &&quotaMoney!=null){
					var url = "sale_parameter_quota_all.jsp?productId="+<%=productId%>+"&subProductId="+<%=subProductId%>+"&teamId="+teamId;
					location = url;
				}else{
					alert("�������ñ��Ŷӵ����");
				}
			}
			//��һ���Ŷ�
			function lastTeam(parentId){
				var url = "sale_parameter_quota_all.jsp?productId="+<%=productId%>+"&subProductId="+<%=subProductId%>+"&teamId="+parentId;
				location = url;
			}
			//��������
			function nextPerson(teamId,quotaMoney){
				if(quotaMoney!=0 &&quotaMoney!=null){
					var url = "sale_parameter_quota_all.jsp?productId="+<%=productId%>+"&subProductId="+<%=subProductId%>+"&teamId="+teamId+"&showT=false";
					location = url;
				}else{
					alert("�������ñ��Ŷӵ����");
				}
			}
			//��ѯ��Ʒ
			function searchProductList(value,input_operatorCode,flag) {
				$.ajax({
					url: "search_product_list.jsp",
					type : "get",
					data: {inputSearch:value,flag:flag,input_operatorCode:input_operatorCode},
					success: function(data){
						newData = data.replace(/\r\n/g,'');

						if(newData == ""){
							if(flag==0){
								sl_alert("����Ĳ�Ʒ��Ų����ڣ�");
							}else{
								sl_alert("����Ĳ�Ʒ���Ʋ����ڣ�");
							}
							var obj = document.theform.productId; 
							obj.options[0].selected = true;
						}else{
							$("#productname").empty();
							$("#productname").append("<option selected value=''>��ѡ��</option>");
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
					<td align="right">��Ʒ��� :&nbsp;&nbsp;</td><!--��Ʒ���-->
					<td align="left">
						<input type="text" maxlength="16" name="productCode" size="12" onkeydown="javascript:searchProductListKey(document.theform.productCode.value,<%=input_operatorCode %>,0);"/>&nbsp;&nbsp;
						<button class="searchbutton" onclick="javascript:searchProductList(document.theform.productCode.value,<%=input_operatorCode %>,0);"/></button>
						&nbsp;&nbsp;��Ʒ���� :&nbsp;&nbsp;
						<input type="text" maxlength="16" name="productName" size="12" onkeydown="javascript:searchProductListKey(document.theform.productName.value,<%=input_operatorCode %>,1);"/>&nbsp;&nbsp;
						<button class="searchbutton"onclick="javascript:searchProductList(document.theform.productName.value,<%=input_operatorCode %>,1);"/></button>
					</td>
				</tr>	
				<tr>
					<td align="right">��Ʒѡ�� :&nbsp;&nbsp;</td>
					<td align="left">
						<select size="1" name="productId" class="productname" id="productname" onchange="selectProduct(this.value)">
							<option value="0">��ѡ�� </option><!--��ѡ��-->
							<%=Argument.getProductListOptionsCRM(new Integer(1),"",new Integer(102),input_operatorCode,"","",productId)%>
						</select>
					</td>
				</tr>
				<tr id="sub_product_flag" style="display:none;">
					<td align="right">�Ӳ�Ʒ���� :&nbsp;&nbsp;</td>
					<td align="left" id="select_id">
						<select size="1" id="subProductId" name="subProductId" onkeydown="javascript:nextKeyPress(this)" class="subProductId" onchange="javascript:selectSubProduct(this.value);">
							<%=Argument.getSubProductOptions(productId, new Integer(0),subProductId)%>
						</select>
					</td>
				</tr>
				<%if(subProductId.intValue()==0){ %>
				<tr>
					<td  align="right" width="120px">&nbsp;&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.productShortName",clientLocale)%> :&nbsp;&nbsp;</td><!--��Ʒ���-->
					<td  align="left">
						<input type="text" id="productJC" name="productJC" readonly="readonly" class="edline" value="<%=productJc %>" size="32">
					</td>
				</tr>
				<%} %>
				<tr>
					<td align="right" width="120px">Ԥ���з��� :&nbsp;&nbsp;</td>
					<td><input type="text" name="preNum" readonly="readonly" class="edline" value="<%=preNum %>" size="32"></td>
				</tr>
				<tr>
					<td align="right" width="120px">Ԥ���н�� :&nbsp;&nbsp;</td>
					<td><input type="text" name="preMoney" readonly="readonly" class="edline" value="<%=preMoney %>" size="32"></td>
					<td align="right" width="120px">��ͷ��н�� :&nbsp;&nbsp;</td>
					<td><input type="text" name="minMoney" readonly="readonly" class="edline" value="<%=minMoney %>" size="32"></td>
				</tr>
				<tr>
					<td align="right" width="120px"><%=LocalUtilis.language("class.preStartDate",clientLocale)%> :&nbsp;&nbsp;</td><!--�ƽ���ʼ����-->
					<td><input type="text" name="preStartDate" readonly="readonly" class="edline" value="<%=Format.formatDateLine(preStartDate) %>" size="32"></td>
					<td align="right" width="120px"><%=LocalUtilis.language("class.preEndDate",clientLocale)%> :&nbsp;&nbsp;</td><!--�ƽ���ֹ����-->
					<td><input type="text" name="preEndDate" readonly="readonly" class="edline" value="<%=Format.formatDateLine(preEndDate) %>" size="32"></td>
				</tr>
			</table>
			<br/>
			<%  //�鿴�б�������Ϣ
				SaleParameterVO salevo = new SaleParameterVO();
				SaleParameterLocal sale_parameter = EJBFactory.getSaleParameter();

				int iCount = 0;//ͳ�ƶ�����
			
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
						<button class="xpbutton4" name="btnEdit" onclick="javascript:showTeam('<%=productId %>');">ѡ���Ŷ�</button>
					</td>
				</tr>
			</table>
			<br/>
			<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
				<tr class=trtagsort>
					<%if("true".equals(showT)){%>
					<td align="center"><%=LocalUtilis.language("class.teamName",clientLocale)%> </td><!--�Ŷ�����-->
					<td align="center">�ɷ����������</td>
					<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--�������-->
					<td align="center">��ʹ�����</td>
					<%if(user_id.intValue()!=5) {%>
					<td align="center">ʣ��ɷ���Ͷ������</td>
					<%} %>
					<td align="center">С�����</td><!--�ϸ�Ͷ�����������-->
					<td align="center">��ʹ��С��</td><!--�ϸ�Ͷ�����������-->
					<td align="center"><%=LocalUtilis.language("class.TeamMember",clientLocale)%> </td><!--�Ŷӳ�Ա-->
					<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
					<td align="center" width="160px">�Ŷ�</td>
					<%}else{ %>
					<td align="center">��������</td>
					<td align="center">�����Ŷ�</td>
					<td align="center">�ɷ����������</td>
					<td align="center"><%=LocalUtilis.language("class.saleQuota",clientLocale)%> </td><!--�������-->
					<td align="center">���������</td><!--�������-->
					<%if(user_id.intValue()!=5) {%>
					<td align="center">�ɷ����������</td>
					<%} %>
					<td align="center"><%=LocalUtilis.language("class.quotaQualifiedNum",clientLocale)%></td><!--�ϸ�Ͷ�����������-->
					<td align="center">�ѹ�Ͷ������</td><!--�ϸ�Ͷ�����������-->
					<td align="center"><%=LocalUtilis.language("message.edit",clientLocale)%> </td><!--�༭-->
					<td align="center" width="160px">�Ŷ�</td>
					<%} %>
				</tr>
				<%
					for(int i=0; i<list.size(); i++){
						if("true".equals(showT)){
							iCount++;
							map = (Map)list.get(i);
							teamId = Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")),new Integer(0));//�Ŷ�ID
							teamName = Utility.trimNull(map.get("TEAM_NAME"));//�Ŷ�����
							description = Utility.trimNull(map.get("DESCRIPTION"));;//�Ŷ�����
							freeMoney = Utility.parseDecimal(Utility.trimNull(map.get("FREE_MONEY")), new BigDecimal(0),0,"1");//�ɷ����������
							quotaMoney = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0),0,"1");//�������
							alreadyPre = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0),0,"1");//��ʹ�����
							//alreadySale = Utility.parseDecimal(Utility.trimNull(map.get("ALREADYSALE")), new BigDecimal(0),0,"1");//���������
							quotaQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")),new Integer(0));;//С��ϸ�Ͷ������
							alreadyQualifiedNum = Utility.parseInt(Utility.trimNull(map.get("ALREADY_QUALIFIED_NUM")),new Integer(0));//�ѹ�Ͷ������
							freeNum = Utility.parseInt(Utility.trimNull(map.get("FREE_NUM")),new Integer(0));//�ɷ���С��ϸ�Ͷ������
							teamNum = Utility.parseInt(Utility.trimNull(map.get("TEAM_NUM")),new Integer(0));//�����ŶӸ���
							parentId = Utility.parseInt(Utility.trimNull(map.get("PARENT_ID")),new Integer(0));//���Ŷ�ID
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
							alreadyPre = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0),0,"1");//��ʹ�����
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
						<button class="xpbutton2" style="margin-right: 5px;" name="btnEdit" onclick="lastTeam('<%=pParentId%>')">��һ��</button>
						<%} %>
						<%if(!new Integer(0).equals(teamNum)) {%>
						<button class="xpbutton2" name="btnEdit" onclick="nextTeam('<%=teamId%>','<%=quotaMoney%>')">��һ��</button>
						<%}else{ %>
						<button class="xpbutton2" name="btnEdit" onclick="nextPerson('<%=teamId%>','<%=quotaMoney%>')">��������</button>
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
						<button class="xpbutton2" style="margin-right: 5px;" name="btnEdit" onclick="lastTeam('<%=parentId%>')">�ϼ��Ŷ�</button>
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
							<button class="xpbutton2" style="margin-right:  5px;" name="btnEdit" onclick="history.back()">����</button>
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
