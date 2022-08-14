<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.webcall.*,java.util.*,java.math.*,enfo.crm.marketing.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.callcenter.*"%>
<%@ include file="/includes/operator.inc" %>

<%
CustomerLocal cust_local = EJBFactory.getCustomer();//客户Bean
CustomerVO cust_vo = new CustomerVO();
TCrmWebCallLocal webcall_local = EJBFactory.getTCrmWebCall();
TCrmWebCallVO webcall_vo = new TCrmWebCallVO();

//页面传递变量
String webcallId = Utility.trimNull(request.getParameter("webcallId"));
String cust_no = request.getParameter("cust_no");
Integer cust_id = Utility.parseInt(request.getParameter("cust_id"), null);
String queryCondition = Utility.trimNull(request.getParameter("queryCondition"));
int subflag = Utility.parseInt(request.getParameter("subflag"), 0);
int iCount = 0 ;
int webcallFlag = 0;//访客ID是否需要绑定 标志
boolean bSuccess = false;

//绑定
if (request.getMethod().equals("POST")){
	webcall_vo = new TCrmWebCallVO();
	webcall_vo.setInput_man(input_operatorCode);
	webcall_vo.setCust_id(cust_id);
	webcall_vo.setWebcallId(webcallId);	
	webcall_local.appendCrmWebcall(webcall_vo);
	bSuccess = true;
}
//获得客户详细信息、赋值、转换
cust_vo.setCust_id(cust_id);
cust_vo.setInput_man(input_operatorCode);
List cust_list = cust_local.listProcAll(cust_vo);
Map cust_map = new HashMap();
if(cust_list!=null&&cust_list.size()>0){
	cust_map = (Map) cust_list.get(0);
}
//查看访客ID是否需要绑定
webcall_vo = new TCrmWebCallVO();
webcall_vo.setWebcallId(webcallId);

List rsList = webcall_local.listCrmWebcall(webcall_vo);
if(rsList!=null&&rsList.size()>0){
	webcallFlag = 1 ;
}
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">

<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/webcall/webcall.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<base target="_self">
<style>
.headdarkbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/headdark_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 90px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}

.headbutton {
	cursor: hand;
	BORDER-RIGHT: 0px solid;
	BORDER-TOP: 0px solid;
	BACKGROUND-IMAGE: url(<%=request.getContextPath()%>/images/head_00_01.gif);
	PADDING-BOTTOM: 0px;
	BORDER-LEFT: 0px solid;
	WIDTH: 90px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}

.edline_webcall{
    BACKGROUND-COLOR: #EBF3F6;
    BORDER-BOTTOM: #000000 1px solid;
    BORDER-LEFT: medium none;
    BORDER-RIGHT: medium none;
    BORDER-TOP: medium none;
}

</style>
</style>
<script language=javascript>
function show(parm){
   for(i=0;i<3;i++){  
     if(i!= parm){
        eval("document.getElementById('d" + i + "').background = '<%=request.getContextPath()%>/images/headdark_00_01.gif'");
       if(document.getElementById("r"+i)!=null)
		 eval("document.getElementById('r" + i + "').style.display = 'none'");
	 }
	 else{
	    eval("document.getElementById('d"+i+"').background = '<%=request.getContextPath()%>/images/head_00_01.gif'");
	    if(document.getElementById("r"+i)!=null)
		  eval("document.getElementById('r" + i + "').style.display = ''");
	 } 
  }
}
function showProduct(product_id){	
	showModalDialog('<%=request.getContextPath()%>/marketing/base/product_list_detail.jsp?product_id='+product_id, '','dialogWidth:950px;dialogHeight:580px;status:0;help:0');
}
/*查看明细*/
function setiteminfor(serial_no){
	var v_tr =  "detailsTr"+serial_no;
	var v_table = "detailsTable"+serial_no;
	var v_flag = "detailsFlag_display"+serial_no;
	var v_div = "detailsDiv"+serial_no;
	var v_image = "detailsImage"+serial_no;
	var flag = document.getElementById(v_flag).value;
	
	if(flag==0){		
		document.getElementById(v_tr).style.display="";
		document.getElementById(v_table).style.display="";
		
		if(document.getElementById(v_div).offsetHeight>200){
			document.getElementById(v_div).style.height=200;
		}
		
		document.getElementById(v_flag).value = 1;
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/up_enabled.gif";
		
	}
	else if(flag==1){
		document.getElementById(v_tr).style.display="none";
		document.getElementById(v_table).style.display="none";
		document.getElementById(v_flag).value = 0;	
		document.getElementById(v_image).src="<%=request.getContextPath()%>/images/down_enabled.gif";
	}
}
function backAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_list.jsp?queryCondition=<%=queryCondition%>&webcallId=<%=webcallId%>";
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
/*保存*/
function saveAction(){
	document.getElementsByName('theform')[0].action="customer_info_webcall_detail.jsp";
	document.getElementsByName('theform')[0].method = "post";
	document.getElementsByName('theform')[0].submit();
} 
function queryAction(){
	var url = "<%=request.getContextPath()%>/webcall/customer_info_webcall_query.jsp?webcallId=<%=webcallId%>";
	//window.showModalDialog(url,window,'dialogWidth:300px;dialogHeight:350px');
	var a = document.createElement("a");
    a.href = url;
    document.body.appendChild(a);
    a.click();	
}
</script>
</HEAD>
<BODY class="BODY2" style="overflow-y: hidden;">
<form name="theform">
<input type="hidden" name="queryCondition" value="<%=queryCondition %>">
<input type="hidden" name="cust_id" value="<%=cust_id %>">
<input type="hidden" id="webcallId" name="webcallId" value="<%=webcallId%>">
<div align="left"  class="topDiv">
	<b><font size="3">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></font>				
	<font face="新宋体">(<%=Utility.trimNull(cust_map.get("CUST_NO"))%>)&nbsp;</font></b><br>
	<%if(webcallFlag==0){%><font color="red">该访客无对应客户信息</font><%}%>
	<%if(bSuccess){%><font color="red">保存成功！</font><%}%>
</div>
<div align="center">
	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0" class="edline_webcall">
		<TBODY>
			<TR>
				<TD vAlign=top>&nbsp;</TD>
				<!--基本信息-->
                <TD id=d0 style="background-repeat: no-repeat" onclick=show(0) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 0) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;&nbsp;&nbsp;基本信息 </TD>
				<!--认购信息-->
                <TD id=d1 style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 1) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;&nbsp;&nbsp;认购信息 </TD>
				<!--办理业务-->
                <TD id=d2 style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=80 height=20 background='<%=request.getContextPath()%>/images/<%if (subflag == 2) out.print("head_00_01.gif"); else out.print("headdark_00_01.gif");%>'>&nbsp;&nbsp;&nbsp;&nbsp;办理业务 </TD>
			</TR>
		</TBODY>
	</TABLE>

	<TABLE cellSpacing=0 cellPadding=0 width="100%" border="0">
		<TR id=r0 <%if (subflag == 0) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
			<TD>
				<TABLE cellSpacing=1 cellPadding=2 width="100%" class="tablelinecolor" border=0>
						<tr bgColor=#ffffff>
							<td align="right" width="100px">证件类型 :</td><!--证件类型-->
							<td><%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right" width="100px">证件号码 :</td><!--证件号码-->
							<td><%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						</tr>
					<%if(new Integer(cust_map.get("CUST_TYPE").toString()).intValue()==1)  {%>	
						<tr bgColor=#ffffff>
							<td align="right" width="100px">出生日期 :</td><!--出生日期-->
							<td><%if(cust_map.get("BIRTHDAY")!=null)Format.formatDateCn(new Integer(cust_map.get("BIRTHDAY").toString()));%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right" width="100px">年龄 :</td><!--年龄-->
							<td><%=Utility.trimNull(cust_map.get("AGE"))%></td>
						</tr>
						<tr bgColor=#ffffff>
							<td align="right" width="100px">性别 :</td><!--性别-->
							<td><%=Utility.trimNull(cust_map.get("SEX_NAME"))%></td>
						</tr>
					<%}%>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">手机 :</td><!--手机-->
						<td><a href="#" class="a2" onclick="javascript:callCust(<%=cust_map.get("CUST_ID")%>)"><%=Utility.trimNull(cust_map.get("MOBILE"))%></a></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">电子邮件  :</td>
						<td><%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">公司电话 :</td><!--公司电话-->
						<td><%=Utility.trimNull(cust_map.get("O_TEL"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">家庭电话 :</td><!--家庭电话-->
						<td><%=Utility.trimNull(cust_map.get("H_TEL"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">最近购买时间 :</td><!--最近购买时间-->
						<td><%if(cust_map.get("LAST_RG_DATE")!=null)Format.formatDateCn(new Integer(cust_map.get("LAST_RG_DATE").toString()));%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">客户级别 :</td><!--客户级别-->
						<td><%=Utility.trimNull(cust_map.get("CUST_LEVEL_NAME"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">联系方式 :</td><!--联系方式-->
						<td><%=Utility.trimNull(cust_map.get("TOUCH_TYPE_NAME"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">邮寄地址 :</td><!--邮寄地址-->
						<td><%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">邮政编码 :</td><!--邮政编码-->
						<td><%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px">备注信息 :</td><!--备注信息-->
						<td ><%=Utility.trimNull(cust_map.get("SUMMARY"))%></td>
					</tr>
					<tr bgColor=#ffffff>
						<td align="right" width="100px"><%=LocalUtilis.language("class.accountManager",clientLocale)%> :</td><!-- 客户经理 -->
						<td><%=Utility.trimNull(Argument.getOpName(Utility.parseInt(Utility.trimNull(cust_map.get("SERVICE_MAN")),new Integer(0))))%></td>
					</tr>
				</TABLE>
			</TD>
		</TR>
		<TR id=r1 <%if (subflag == 1) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#ffffff>
			<TD>
				<table border="0" cellpadding="2" cellspacing="1" class="tablelinecolor" width="100%">
					<tr bgColor=#EBF3F6>
						<td align="left" colspan="6" height="15"><font color="#CC99CC"size="-1">预约信息（只显示最近三条）</font></td><!--预约信息-->
					</tr>
					<tr class="trh">
						<td align="center" height="15">产品名称 </td><!--产品名称-->
						<td align="center" height="15">预约编号 </td><!--预约编号-->		
						<td align="center" height="15">详细信息</td>
					</tr>
					<%
						PreContractLocal precontract_local = EJBFactory.getPreContract();//预约Bean
						PreContractVO precontract_vo = new PreContractVO();
						precontract_vo.setBook_code(new Integer(1));
						precontract_vo.setCust_id(cust_id);
						iCount = 0;
						//搜索
						List pre_list = precontract_local.queryPrecontractByCustID(precontract_vo);
						Map pre_map = new HashMap();
						for(int i=0; i<pre_list.size()&&i<3; i++){
							pre_map = (Map)pre_list.get(i);
							Integer query_product_id = Utility.parseInt(Utility.trimNull(pre_map.get("PRODUCT_ID")),new Integer(0));
							iCount++;						
					%>
					<tr bgColor=#ffffff>
						<td align="left" height="25"><%=Argument.getProductName(query_product_id)%></td>
						<td align="left" height="25"><%=Utility.trimNull(pre_map.get("PRE_CODE"))%></td>
			             <td align="center">
			             	<button  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=pre_map.get("PRE_CODE")%>);">
			         			<IMG id="detailsImage<%=pre_map.get("PRE_CODE")%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
				         	</button>
				         	<input type="hidden" id="detailsFlag_display<%=pre_map.get("PRE_CODE")%>" name="detailsFlag_display<%=pre_map.get("PRE_CODE")%>" value="0">             
			             </td>	
					</tr>
					<tr id="detailsTr<%=pre_map.get("PRE_CODE")%>" style="display: none">
						<td align="center" bgcolor="#FFFFFF" colspan="17" >
							<div id="detailsDiv<%=pre_map.get("PRE_CODE")%>" style="overflow-y:auto;" align="center">
								<table id="detailsTable<%=pre_map.get("PRE_CODE")%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
									<tr style="background:F7F7F7;" >
										<td align="center">预约金额 ：</td><!--公司电话-->
										<td  width="*"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(pre_map.get("PRE_MONEY")))))%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center">已认购金额 ：</td><!--公司电话-->
										<td  width="*"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(pre_map.get("RG_MONEY")))))%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center">预约日期 ：</td><!--公司电话-->
										<td  width="*"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(pre_map.get("PRE_DATE")), new Integer(0)))%></td>
									</tr>
									<tr style="background:F7F7F7;" >
										<td align="center">预约状态 ：</td><!--公司电话-->
										<td  width="*"><%=Utility.trimNull(pre_map.get("PRE_STATUS_NAME"))%></td>
									</tr>
								</table>
							</div>
						</td>
					</tr>  
					<%}%>
				</table>
				<!-- 认购 -->
				<table border="0" cellpadding="2" cellspacing="1"
					class="tablelinecolor" width="100%">
					<tr bgColor=#EBF3F6>
						<td align="left" colspan="8" height="15"><font color="#CC99CC" size="-1">认购信息（只显示最近三条）</font></td><!--认购信息-->
					</tr>
					<tr class="trh"">
						<td align="center" height="15">产品名称[合同编号] </td>					
						<td align="center" height="15">详细信息</td>
					</tr>
					<%
					iCount = 0;
					String sub_product_name = "";
					BigDecimal exp_rate1 = new  BigDecimal(0);
					BigDecimal exp_rate2 = new  BigDecimal(0);
					String display_flag = "";

					ContractLocal contract_local = EJBFactory.getContract();//合同Bean
					ContractVO contract_vo = new ContractVO();
					contract_vo.setBook_code(new Integer(1));
					contract_vo.setCust_id(cust_id);

					List contract_list = contract_local.queryContractByCustID(contract_vo);
					Map contract_map = new HashMap();

					for(int i=0;i<contract_list.size()&&i<3;i++,iCount++){
						contract_map = (Map)contract_list.get(i);
						Integer query_product_id = Utility.parseInt(Utility.trimNull(contract_map.get("PRODUCT_ID")),new Integer(0));
						Integer pre_product_id = Argument.getPreProduct_id(query_product_id);

						sub_product_name = Utility.trimNull(contract_map.get("LIST_NAME"));
						exp_rate1 = Utility.parseDecimal(Utility.trimNull(contract_map.get("PRODUCT_EXP_RATE1")), new BigDecimal(0.00),2,"100");
						exp_rate2 = Utility.parseDecimal(Utility.trimNull(contract_map.get("PRODUCT_EXP_RATE2")), new BigDecimal(0.00),2,"100");

						if(sub_product_name.length()>0){
							exp_rate1 = Utility.parseDecimal(Utility.trimNull(contract_map.get("SUB_EXP_RATE1")), new BigDecimal(0.00),2,"100");
							exp_rate2 = Utility.parseDecimal(Utility.trimNull(contract_map.get("SUB_EXP_RATE2")), new BigDecimal(0.00),2,"100");
						}
						display_flag = Utility.trimNull(contract_map.get("SERIAL_NO"));
					%>
					<tr bgColor=#ffffff>
					    <td align="left" height="25"><%=Utility.trimNull(contract_map.get("PRODUCT_NAME"))%><%if(sub_product_name.length()>0){ %>[<%=sub_product_name%>]<%}%>[<%=Utility.trimNull(contract_map.get("CONTRACT_SUB_BH"))%>]</td>						
   						<td align="center">
			             	<button  class="xpbutton2" name="" onclick="javascript:setiteminfor(<%=display_flag%>);">
			         			<IMG id="detailsImage<%=display_flag%>" src="<%=request.getContextPath()%>/images/down_enabled.gif"  width="7" height="9">         		
				         	</button>
				         	<input type="hidden" id="detailsFlag_display<%=display_flag%>" name="detailsFlag_display<%=display_flag%>" value="0">             
			             </td>	
					</tr>
	
						<tr id="detailsTr<%=display_flag%>" style="display: none">
							<td align="center" bgcolor="#FFFFFF" colspan="17" >
								<div id="detailsDiv<%=display_flag%>" style="overflow-y:auto;" align="center">
									<table id="detailsTable<%=display_flag%>" border="0" width="100%" bgcolor="#000000" cellspacing="1">
										<tr style="background:F7F7F7;" >
											<td align="center">签署日期 ：</td><!--公司电话-->
											<td  width="*"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("QS_DATE")), new Integer(0)))%></td>
										</tr>
										<tr style="background:F7F7F7;" >
											<td align="center">已认购金额 ：</td><!--公司电话-->
											<td  width="*"><%=Utility.trimNull(Format.formatMoney(Utility.stringToDouble(Utility.trimNull(contract_map.get("RG_MONEY")))))%></td>
										</tr>
										<tr style="background:F7F7F7;" >
											<td align="center">预期收益率 ：</td><!--公司电话-->
											<td  width="*"><%=exp_rate1%>%-<%=exp_rate2%>%</td>
										</tr>
										<tr style="background:F7F7F7;" >
											<td align="center">到期日期 ：</td><!--公司电话-->
											<td  width="*"><%=Format.formatDateCn(Utility.parseInt(Utility.trimNull(contract_map.get("END_DATE")), new Integer(0)))%></td>
										</tr>
												<tr style="background:F7F7F7;" >
											<td align="center">合同状态 ：</td><!--公司电话-->
											<td  width="*"><%=Utility.trimNull(contract_map.get("HT_STATUS_NAME"))%></td>
										</tr>
										<tr style="background:F7F7F7;" >
											<td align="center">审核标志 ：</td><!--公司电话-->
											<td  width="*"><%if(Utility.parseInt(Utility.trimNull(contract_map.get("CHECK_FLAG")), 0)==1){%>未审核<%}else{%>审核<%}%></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>  
					<%}
					%>
				</table>
			</TD>
		</TR>
		<TR id=r2 <%if (subflag == 2) out.print("style='display:'"); else out.print("style='display:none'");%> bgColor=#EBF3F6>
			<TD align="center" vAlign=middle>
				<br><br>
				<button class="xpbutton31"  id="btnBack" name="btnBack" onclick="javascript:">预登记</button><br><br>
				<button class="xpbutton31"  id="btnBack" name="btnBack" onclick="javascript:">预约</button>	<br><br>
				<button class="xpbutton31"  id="btnBack" name="btnBack" onclick="javascript:">认购</button>	<br><br>	
				<button class="xpbutton31"  id="btnSave" name="btnSave" onclick="javascript:saveAction();">绑定</button><br><br>			
				<button class="xpbutton31"  id="btnQuery" onclick="javascript:queryAction();">客户搜索</button>		
			</TD>
		</TR>
	</TABLE>
</div>

<div class="bottomDiv">
	&nbsp;&nbsp;<font size="2">您好！<%=input_operator.getOp_name()%></font>&nbsp;&nbsp;
	<%if(queryCondition.length()!=0){ %>
	<button class="xpbutton2"  id="btnBack" name="btnBack" onclick="javascript:backAction();">返回</button>	
	<%}%>
</div>
</form>
</BODY>
</HTML>