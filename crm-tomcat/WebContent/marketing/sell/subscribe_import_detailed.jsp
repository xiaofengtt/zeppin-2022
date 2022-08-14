<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<%
//获得页面传递变量
String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
String cust_name = Utility.trimNull(request.getParameter("cust_name"));
String product_name = Utility.trimNull(request.getParameter("product_name"));
//页面辅助参数
String[] totalColumn = new String[0];
List contractList = null;
Map contractMap = new HashMap();
//获得对象及结果集
ContractLocal contractLocal = EJBFactory.getContract();
ContractVO vo = new ContractVO();
//设置参数
vo.setProduct_name(product_name);
vo.setCust_name(cust_name);
vo.setContract_bh(contract_bh);
//获得结果
IPageList pageList  = contractLocal.queryOldTemp(vo,totalColumn,1,-1);
contractList = pageList.getRsList();
if(contractList.size()>0){
	contractMap = (Map)contractList.get(0);	
}
Integer str_qs_date = Utility.parseInt(Utility.trimNull(contractMap.get("合同签订日期")),new Integer(0));
Integer str_jk_date = Utility.parseInt(Utility.trimNull(contractMap.get("缴款日期")),new Integer(0));
Integer str_end_date = Utility.parseInt(Utility.trimNull(contractMap.get("合同截止日期")),new Integer(0));
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<style media="print">
.noprint { display: none }
</style>

<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>

<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>

</script>
<BODY>
<FORM>
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD><b><%=Utility.trimNull(contractMap.get("项目名称"))%></b></TD>
		</TR>
		<TR>
			<TD width="100%">
				<FIELDSET >
					<LEGEND><%=LocalUtilis.language("message.contractInfo",clientLocale)%> </LEGEND><!--合同信息-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.contractID",clientLocale)%> :</TD><!--合同编号-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("合同编号"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.signedDate",clientLocale)%> :</TD>	<!--签订日期-->
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_qs_date)%>"></TD>						
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.dzDate",clientLocale)%> :</TD><!--缴款日期-->
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_jk_date)%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.endDate3",clientLocale)%> :</TD><!--截止日期-->
							<TD><input class="edline" readonly  type="text" size="25" value="<%=Format.formatDateCn(str_end_date)%>"></TD>
						</TR>
						<TR>	
							<TD align="right"><%=LocalUtilis.language("class.validPeriod",clientLocale)%> :</TD><!--合同期限-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("合同期限"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.rgMoney3",clientLocale)%> :</TD><!--合同金额-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("合同金额"))%>"></TD>
						</TR>
						<TR>	
							<TD align="right">产品期数 :</TD><!--产品期数-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("产品期数"))%>"></TD>
						</TR>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND><%=LocalUtilis.language("message.principalInfo",clientLocale)%> </LEGEND><!--委托人信息-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.custName3",clientLocale)%> :</TD><!--委托人名称-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.principalType",clientLocale)%> :</TD><!--委托人类型-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人类型"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</TD><!--联系方式-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人联系方式"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.address",clientLocale)%> :</TD><!--地址-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人地址"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</TD><!--邮政编码-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人邮编"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> </TD><!--证件类型-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人证件名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</TD><!--证件号码-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人证件编号"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</TD><!--法人姓名-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人法人代表"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.oTel2",clientLocale)%> :</TD><!--固定电话-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人固定电话"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</TD><!--手机-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人手机"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</TD><!--电子邮件-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("委托人电子邮件"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>
						
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND><%=LocalUtilis.language("message.custInfo",clientLocale)%> </LEGEND><!--受益人信息-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.qCustName",clientLocale)%> :</TD><!--受益人姓名-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.benType",clientLocale)%> :</TD><!--受益人类型-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人类型"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.serviceWay",clientLocale)%> :</TD><!--联系方式-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人联系方式"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.address",clientLocale)%> :</TD><!--地址-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人地址"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.postcode",clientLocale)%> :</TD><!--邮政编码-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人邮编"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerCardType",clientLocale)%> :</TD><!--证件类型-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人证件名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.customerCardID",clientLocale)%> :</TD><!--证件号码-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人证件编号"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.corporate",clientLocale)%> :</TD><!--法人姓名-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人法人代表"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.oTel2",clientLocale)%> :</TD><!--固定电话-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人固定电话"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.customerMobile",clientLocale)%> :</TD><!--手机-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人手机"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.email",clientLocale)%> :</TD><!--电子邮件-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("受益人电子邮件"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND><%=LocalUtilis.language("message.otherInfo",clientLocale)%> </LEGEND><!--其他信息-->
					<TABLE width="100%">
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.bankName",clientLocale)%> :</TD><!--开户银行-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("开户银行名称"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.branchName",clientLocale)%> :</TD><!--支行名称-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("支行名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.bankAcct",clientLocale)%> :</TD><!--银行账号-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("银行账号"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.acctUsername",clientLocale)%> :</TD><!--开户户名-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("开户户名"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.propertyType",clientLocale)%> :</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("财产类别"))%>"></TD>
							<TD align="right"><%=LocalUtilis.language("class.propertyName",clientLocale)%> :</TD><!--财产名称-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("财产名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right"><%=LocalUtilis.language("class.feeType",clientLocale)%> :</TD><!--缴款方式-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("缴款方式"))%>"></TD>
							<TD align="right">资产来源:</TD><!--资产来源-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("资产来源"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">资产来源细分:</TD><!--资产来源细分-->
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(contractMap.get("资产来源细分"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>							
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<!--返回-->
            <TD align="right"><button type="button"  class="xpbutton3" accessKey=b onclick="javascript:disableAllBtn(true);history.back();"><%=LocalUtilis.language("message.back",clientLocale)%> (<u>B</u>)</button></TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
</BODY>
</HTML>