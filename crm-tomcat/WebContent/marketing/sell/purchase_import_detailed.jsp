<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.web.*,java.io.*" %>
<%@ page import="enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.marketing.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
try{
	ContractLocal contract = EJBFactory.getContract();
	String contract_bh = Utility.trimNull(request.getParameter("contract_bh"));
	String cust_name = Utility.trimNull(request.getParameter("cust_name"));
	String product_name = Utility.trimNull(request.getParameter("product_name"));
	int dealFlag = Utility.parseInt(request.getParameter("dealFlag"),1);

	// IPageList pageList = contract.queryOldTemp(product_name,query_contract_bh,cust_name,2, _page, pagesize);
	List list = contract.queryOldTemp(product_name,contract_bh,cust_name,dealFlag, 1, -1).getRsList();
	
	//contract.getOldTempNext();	
	Map map = (Map)list.get(0);
	Integer str_qs_date = Utility.parseInt((String)map.get("合同签订日期"),new Integer(0));
	Integer str_jk_date = Utility.parseInt((String)map.get("缴款日期"),new Integer(0));

	int bzjFlag = Argument.getSyscontrolValue_intrust("BZJCLFS");//Argument.getSyscontrolValue("BZJCLFS");//保证金开关 1有,2没有
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<style media="print">
.noprint { display: none }
</style>

<style>
 @media Print { .Noprn { DISPLAY: none }}
</style>

<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
<script language=javascript>

</script>
<BODY class="body body-nox">
<FORM>
<TABLE border="0" width="100%">
	<TBODY>
		<TR>
			<TD><b><%=Utility.trimNull(map.get("项目名称"))%></b></TD>
		</TR>
		<TR>
			<TD width="100%">
				<FIELDSET >
					<LEGEND>合同信息</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">合同编号:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("合同编号"))%>"></TD>
							<TD align="right">合同签订日期:</TD>	
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_qs_date)%>"></TD>						
						</TR>
						<TR>
							<TD align="right">缴款日期:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Format.formatDateCn(str_jk_date)%>"></TD>
							<TD align="right">截止日期:</TD>
							<TD><input class="edline" readonly  type="text" size="25" 
									value="<%=Format.formatDateCn(Utility.parseInt((String)map.get("合同截止日期"), new Integer(0)))%>"/></TD>
						</TR>
						<TR>	
							<TD align="right">合同期限:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("合同期限"))%>"></TD>
							<TD align="right">合同金额:</TD>
							<TD><input class="edline"  type="text" size="25" 
									value="<%=Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("合同金额")).trim(), new BigDecimal(0)))%>"></TD>
						</TR>
						<%if(bzjFlag == 1) {%>
						<TR>	
							<TD align="right">是否为保证金:</TD>
							<TD colspan="3"><%=Utility.trimNull(map.get("保证金标志")) %>
								是:<input type="radio" name="bzj_flag" class="flatcheckbox" <%=Utility.parseInt((String)map.get("保证金标志"),new Integer(2)).equals(new Integer(1)) ? "checked" : "disabled"%>>&nbsp;&nbsp;
								否:<input type="radio" name="bzj_flag" class="flatcheckbox" <%=Utility.parseInt((String)map.get("保证金标志"),new Integer(2)).equals(new Integer(2)) ? "checked" : "disabled"%>>
							</TD>
						</TR>
						<%}%>
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND>委托人信息</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">委托人:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人"))%>"></TD>
							<TD align="right">委托人类型:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人类型"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">联系方式:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人联系方式"))%>"></TD>
							<TD align="right">地址:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人地址"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">邮政编码:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人邮编"))%>"></TD>
							<TD align="right">证件名称:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人证件名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">证件编号:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人证件编号"))%>"></TD>
							<TD align="right">法人代表:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人法人代表"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">固定电话:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人固定电话"))%>"></TD>
							<TD align="right">手机:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人手机"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">电子邮件:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("委托人电子邮件"))%>"></TD>
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
					<LEGEND>受益人信息</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">受益人:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人"))%>"></TD>
							<TD align="right">受益人类型:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人类型"))%>"></TD>
						</TR>
						<TR>	
							<TD align="right">受益优先级别:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("收益级别"))%>"></TD>
							<TD align="right">收益级别:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益优先级别"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">联系方式:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人联系方式"))%>"></TD>
							<TD align="right">地址:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人地址"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">邮政编码:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人邮编"))%>"></TD>
							<TD align="right">证件名称:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人证件名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">证件号码:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人证件编号"))%>"></TD>
							<TD align="right">法人代表:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人法人代表"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">固定电话:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人固定电话"))%>"></TD>
							<TD align="right">手机:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人手机"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">电子邮件:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("受益人电子邮件"))%>"></TD>
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
					<LEGEND>渠道信息</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">渠道类别:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("渠道类别"))%>"></TD>
							<TD align="right">渠道名称:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("渠道名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">渠道备注:</TD>
							<TD colspan="3"><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("渠道备注"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">省:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("省"))%>"></TD>
							<TD align="right">市:</TD>
							<TD><input class="edline"  type="text" size="25" value="<%=Utility.trimNull(map.get("市"))%>"></TD>
						</TR>				
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD>
				<FIELDSET>
					<LEGEND>其他信息</LEGEND>
					<TABLE width="100%">
						<TR>
							<TD align="right">开户银行:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("开户银行名称"))%>"></TD>
							<TD align="right">支行名称:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("支行名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">银行账号:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Format.formatBankNo(Utility.trimNull(map.get("银行账号")))%>"></TD>
							<TD align="right">开户户名:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("开户户名"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">财产类别:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("财产类别"))%>"></TD>
							<TD align="right">财产名称:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("财产名称"))%>"></TD>
						</TR>
						<TR>
							<TD align="right">缴款方式:</TD>
							<TD><input class="edline"  type="text" size="35" value="<%=Utility.trimNull(map.get("缴款方式"))%>"></TD>
							<TD align="right"></TD>
							<TD></TD>
						</TR>						
					</TABLE>
				</FIELDSET>
			</TD>
		</TR>
		<TR>
			<TD align="right"><button type="button"  class="xpbutton3" accessKey=b onclick="javascript:disableAllBtn(true);history.back();">返回(<u>B</u>)</button></TD>
		</TR>
	</TBODY>
</TABLE>
</FORM>
</BODY>
</HTML>
<%
}catch(Exception e){
	throw e;
}
%>