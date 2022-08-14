<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.intrust.*,enfo.crm.system.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer cust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));

ProductAddLocal prod_local = EJBFactory.getProductAdd();//产品自定义要素
CustomerLocal cust_local = EJBFactory.getCustomer();
ProductAddVO prod_vo = new ProductAddVO();
CustomerVO cust_vo = new CustomerVO();

Map cust_map = new HashMap();
Map prod_map = new HashMap();
Map amCust_map = new HashMap();

cust_vo.setCust_id(cust_id);
List cust_list = cust_local.listCustomerLoad(cust_vo);
if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0);

AmCustInfoLocal amCust_local = EJBFactory.getAmCustInfo();
AmCustInfoVO amCust_vo = new AmCustInfoVO();
amCust_vo.setCust_id(cust_id);
List amCust_list = amCust_local.load(amCust_vo);
if(amCust_list != null && amCust_list.size() > 0)
	amCust_map = (Map)amCust_list.get(0);
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
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/calendar.js"></SCRIPT>
</HEAD>
<BODY  >
<form name="theform" method="post" action="ent_info_print.jsp" onsubmit="javascript:return validateForm(this);">
<input type="hidden" name="cust_id" value="<%=cust_id%>"> 


<TABLE height="100%" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<TR>
		<%//@ include file="menu.inc" %>
		<TD vAlign=top align=left>
		<table border="0" width="100%" cellspacing="0" cellpadding="1" >
			<tr>
				<td>
				<table border="0" width="100%" cellspacing="1" cellpadding="4">
					<tr>
						<td align="center" colspan="2"><font size="4"><%=Argument.getCompanyName(user_id)%></font></td>
					</tr>
					<tr>	
						<td align="center" colspan="2"><font size="4"><b>合格投资人资格确认登记表（法人、其他组织和个体工商户）</b></font></td>
					</tr>
					<tr>	
						<td align="right" style="letter-spacing:40pt;" width="60%"><font size="3">年月日</font></td>
						<td align="center"><font size="3">编号:&nbsp;<%=Utility.trimNull(cust_map.get("HGTZR_BH"))%></font></td>
					</tr>	
				</table>

				<table bordercolor="#000000" border ="1" cellSpacing="0" cellPadding="5" width="100%">
					<tr>
						<td class="tdrightbottom" width="1px" rowspan="14" style="BORDER-BOTTOM: 3px solid;"><font size="2"><b>委托人填写</b></font></td>
						<td class="tdrightbottom" align="center" width="17%"><b>委托人姓名</b></td>
						<td class="tdbottom" colspan="7">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
					</tr>
					
					<tr>
						<td align="center"  class="tdrightbottom"><b>营业执照/证明文件名称</b></td>
						<td class="tdrightbottom" width="15%">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
						<td align="center"  class="tdrightbottom" colspan="2" width="12%"><b>营业执照/证明文件号码</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						<td align="center"  class="tdrightbottom"><b>营业执照/证明文件有效期</b></td>
			 			<td class="tdbottom" width="15%">&nbsp;<%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0))))%></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>住所和邮编</b></td>
						<td class="tdbottom" colspan="7">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>&nbsp;&nbsp;&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>经营范围</b></td>
						<td class="tdbottom" colspan="7">&nbsp;<%=Utility.trimNull(amCust_map.get("CBSC"))%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>法定代表人/负责人姓名</b></td>
						<td class="tdrightbottom">&nbsp;<%=Utility.trimNull(amCust_map.get("CRNM"))%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>身份证件/证明文件类型</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull(amCust_map.get("CRIT"))%></td>
						<td align="center"  class="tdrightbottom"><b>身份证件/证明文件号码</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull(amCust_map.get("CRID"))%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>法人身份证件/证明文件有效期</b></td>
						<td class="tdrightbottom" colspan="2" width="20%">&nbsp;<%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(amCust_map.get("CRVT")),new Integer(0))))%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>联系电话</b></td>
			 			<td class="tdbottom" colspan="3">&nbsp;<%=Utility.trimNull(cust_map.get("MOBILE"))%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>组织机构代码</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull(amCust_map.get("COGC"))%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>税务登记证号码</b></td>
			 			<td class="tdbottom" colspan="3">&nbsp;<%=Utility.trimNull(amCust_map.get("CTRN"))%></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>控股股东或实际控制人</b></td>
						<td class="tdbottom" colspan="7">&nbsp;<%=Utility.trimNull(cust_map.get("FACT_CONTROLLER"))%></td>
					</tr>
					<tr>
						<td class="tdbottom" class="tdrightbottom" colspan="8">
							<b>
								<input type="checkbox" value="" class="flatcheckbox" disabled>&nbsp;投资于一个信托计划的最低金额不少于人民币100万元<br>
								<input type="checkbox" value="" class="flatcheckbox" disabled>&nbsp;投资于本公司发行的信托计划仍在存续期间的金额不少于人民币100万元
							</b>
						</td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>代办人姓名</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>联系电话</b></td>
			 			<td class="tdbottom" colspan="3">&nbsp;<%=Utility.trimNull("")%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>身份证件/证明文件类型</b></td>
						<td class="tdrightbottom">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>身份证件/证明文件号码</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom"><b>身份证件/证明文件有效期</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull("")%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>资金来源声明</b></td>
			 			<td class="tdbottom" colspan="7" style="font-size:14;">&nbsp;
			 		<%
					prod_vo.setBookcode(input_bookCode);
					prod_vo.setTb_flag(new Integer(2));
					prod_vo.setField_caption("资金来源");
					List prod_list = prod_local.list(prod_vo);
					if(prod_list.size()>0)
					{
						prod_map = (Map)prod_list.get(0);
						String fieldValue = Utility.trimNull(prod_map.get("FIELD_VALUE"));
	
					%>
			 			<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("经营性收入") != -1){%>checked<%}%>>经营性收入&nbsp;
			 			<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("财产性收入") != -1){%>checked<%}%>>财产性收入&nbsp;
			 			<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("投资收益") != -1){%>checked<%}%>>投资收益&nbsp;
			 			<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("捐赠收入") != -1){%>checked<%}%>>捐赠收入&nbsp;
			 			<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("其他收入") != -1){%>checked<%}%>>其他收入&nbsp;
					<%}%>			 				
			 			</td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center" rowspan="2" style="BORDER-BOTTOM: 3px solid;"><font size="3"><b>郑重声明</b></font></td>
						<td class="tdnoall" colspan="7">
						<font size="3"><b>&nbsp; &nbsp; &nbsp; &nbsp; 本委托人交付给贵司的信托资金为本委托人本人合法所有的财产，
							不存在非法汇集他人资金参与信托计划的情形。上述资金与其他任何个人、法人及其他组织不存在法律上的任何纠纷。
							如因违反上述承诺而产生的一切法律后果均由本委托人全部承担。</b></font>
						</td>
					</tr>
					<tr>
						<td class="tdbottom" colspan="4" style="BORDER-BOTTOM: 3px solid;">
							<font size="2"><b>委托人或代办人签名：</b></font>
						</td>
						<td class="tdbottom" colspan="3" style="letter-spacing:17px;BORDER-BOTTOM: 3px solid;" align="right">
							<font size="2" ><b>（印鉴）   年   月   日</b></font>
						</td>
					</tr>
					<tr>
						<td class="tdright" width="1px" rowspan="2"><font size="2"><b>资格审查人填写</b></font></td>
						<td class="tdnoall" colspan="8" valign="top" style="font-size:14;">
							<font size="2"><b>审核资料:<br></b></font>
							<input type="checkbox" value="" class="flatcheckbox" disabled>申请人填妥的内容&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>营业执照复印件（盖公章）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>组织机构代码证复印件（盖公章）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>税务登记证复印件（盖公章）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>法定代表人/负责人身份证复印件（盖公章）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>经办人身份证原件及复印件&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>法定代表人授权委托书原件（盖公章）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>公司章程复印件（盖公章）&nbsp;<br>

						</td>
					</tr>
					<tr>
						<td class="tdnoall" colspan="3">
							<font size="3"><b>经办人:</b></font>
						</td>
						<td class="tdnoall" >
							<font size="2"><b>负责人:</b></font>
						</td>
						<td class="tdnoall" colspan="4" style="letter-spacing:17px;" align="right">
							<font size="2"><b>（盖章）   年   月  日</b></font>
						</td>
					</tr>
					</table>	
					
					<table border="0" width="100%" cellspacing="1" cellpadding="4" id=table99>
					<tr>
						<td>
						<table border="0" width="100%">
							<tr>
								<td align="right">
								<button type="button"  class="xpbutton3" accessKey=p name="btnPrint" title="打印" onclick="javascript:table99.style.display = 'none';window.print();table99.style.display = '';">打印(<u>P</u>)</button>	 
								&nbsp;&nbsp;&nbsp;
								<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:history.back();">返回(<u>B</u>)</button>
								&nbsp;&nbsp;
								</td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</TD>
</TABLE>
</form>
</BODY>
</HTML>
<%
prod_local.remove();
cust_local.remove();
amCust_local.remove();
%>