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

cust_vo.setCust_id(cust_id);
List cust_list = cust_local.listCustomerLoad(cust_vo);
if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0);
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
						<td align="center" colspan="2"><font size="4"><b>合格投资人资格确认登记表（自然人）</b></font></td>
					</tr>
					<tr>	
						<td align="right" style="letter-spacing:40pt;" width="60%"><font size="3">年月日</font></td>
						<td align="center"><font size="3">编号:&nbsp;<%=Utility.trimNull(cust_map.get("HGTZR_BH"))%></font></td>
					</tr>
				</table>

				<table bordercolor="#000000" border ="1" cellSpacing="0" cellPadding="5" width="100%">
					<tr>
						<td class="tdrightbottom" width="1px" rowspan="10" style="BORDER-BOTTOM: 3px solid;"><font size="2"><b>委托人填写</b></font></td>
						<td class="tdrightbottom" align="center" width="13%"><b>委托人姓名</b></td>
						<td class="tdrightbottom" width="10%">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
						<td class="tdrightbottom" align="center" colspan="2" width="5%"><b>性别</b></td>
						<td class="tdrightbottom" width="7%" align="center">&nbsp;<%=Utility.trimNull(cust_map.get("SEX_NAME"))%>&nbsp;</td>
						<td class="tdrightbottom" align="center" colspan="2" width="5%"><b>国籍</b></td>
						<td class="tdrightbottom" width="15%" >&nbsp;<%=Utility.trimNull(Argument.getDictParamName(9997,Utility.trimNull(cust_map.get("COUNTRY"))))%></td>
						<td class="tdrightbottom" align="center" colspan="2" width="10%"><b>职业</b></td>
						<td class="tdbottom" colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("VOC_TYPE_NAME"))%></td>
					</tr>
					
					<tr>
						<td align="center"  class="tdrightbottom"><b>身份证件/证明文件类型</b></td>
						<td class="tdrightbottom" width="15%" colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
						<td align="center"  class="tdrightbottom" colspan="3" width="12%"><b>身份证件/证明文件号码</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
						<td align="center"  class="tdrightbottom" colspan="2" width="14%"><b>身份证件/证明文件有效期</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull(Format.formatDateLine(Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0))))%></td>
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>联系地址</b></td>
						<td class="tdbottom" colspan="11">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%>&nbsp;</td>
						
					</tr>
					<tr>
						<td class="tdrightbottom" align="center"><b>邮政编码</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
						<td class="tdrightbottom" align="center"   colspan="3"><b>联系电话</b></td>
			 			<td class="tdbottom"colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("MOBILE"))%></td>
					</tr>
					<tr>
						<td class="tdbottom" colspan="12" style="font-size:14;">
							<input type="checkbox" value="" class="flatcheckbox" disabled>投资于一个信托计划的最低金额不少于人民币100万元&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>自然人投资于一个信托计划的最低金额少于人民币100万元的&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>个人或家庭金融资产总计在其认购时超过100 万元人民币&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>个人收入在最近三年内每年收入超过20 万元人民币或者夫妻双方合计收入在最近三年内每年收入超过30 万元人民币。<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>投资于本公司发行的信托计划仍在存续期间的金额不少于人民币100万元<br>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>代办人姓名</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="3" width="12%"><b>代办人国籍</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>联系电话</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull("")%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>身份证件/证明文件类型</b></td>
						<td class="tdrightbottom" colspan="2">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="3"><b>身份证件/证明文件号码</b></td>
						<td class="tdrightbottom" colspan="3">&nbsp;<%=Utility.trimNull("")%></td>
						<td align="center"  class="tdrightbottom" colspan="2"><b>身份证件/证明文件有效期</b></td>
			 			<td class="tdbottom">&nbsp;<%=Utility.trimNull("")%></td>
					</tr>
					<tr>
						<td align="center"  class="tdrightbottom"><b>资金来源声明</b></td>
			 			<td class="tdbottom" colspan="11" style="font-size:13;">&nbsp;
			 		<%
					prod_vo.setBookcode(new Integer(0));
					prod_vo.setTb_flag(new Integer(2));
					prod_vo.setField_caption("资金来源");
					List prod_list = prod_local.list(prod_vo);
					if(prod_list != null && prod_list.size() > 0)
					{
						prod_map = (Map)prod_list.get(0);
						String fieldValue = Utility.trimNull(prod_map.get("FIELD_VALUE"));
					%>
							<input type="checkbox" value="" class="flatcheckbox" disabled <%if(fieldValue.indexOf("工薪收入") != -1){%>checked<%}%>>工薪收入&nbsp;
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
						<td class="tdnoall" colspan="11">
							<font size="3"><b>&nbsp; &nbsp; &nbsp; &nbsp; 本委托人交付给贵司的信托资金为本委托人本人合法所有的财产，不存在非法汇集他人资金参与信托计划的情形。
							上述资金与其他任何个人、法人及其他组织不存在法律上的任何纠纷。如因违反上述承诺而产生的一切法律后果均由本委托人全部承担。</b></font>
						</td>
					</tr>
					<tr>
						<td class="tdbottom" colspan="4" style="BORDER-BOTTOM: 3px solid;">
							<font size="2"><b>委托人或代办人签名：</b></font>
						</td>
						<td class="tdbottom" colspan="7" style="letter-spacing:17px;BORDER-BOTTOM: 3px solid;" align="right">
							<font size="2" ><b>（印鉴）   年   月   日</b></font>
						</td>
					</tr>
					<tr>
						<td class="tdright" width="1px" rowspan="2"><font size="2"><b>资格审查人填写</b></font></td>
						<td class="tdnoall" colspan="12" valign="top" style="font-size:14;">
							<font size="2"><b>审核资料:<br></b></font>
							<input type="checkbox" value="" class="flatcheckbox" disabled>申请人填妥的内容&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>银行盖章的存款证明（人民币或外币）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>持有股票、基金及其他证券证明或资金账户余额证明（券商或托管机构出具）&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>投资其他信托计划或银行理财计划的证明&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>投资于券商理财计划证明&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>其他投资账户的资金证明&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>投资型保险产品证明&nbsp;<br>
			 				<input type="checkbox" value="" class="flatcheckbox" disabled>其他金融资产证明&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>个人所得税完税证明&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>其它收入证明&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>委托人身份证件原件及复印件&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>代办人身份证件原件及复印件&nbsp;<br>
							<input type="checkbox" value="" class="flatcheckbox" disabled>委托人授权委托书原件&nbsp;<br>
						</td>
					</tr>
					<tr>
						<td class="tdnoall" colspan="3">
							<font size="2"><b>经办人:</b></font>
						</td>
						<td class="tdnoall" colspan="3">
							<font size="2"><b>负责人:</b></font>
						</td>
						<td class="tdnoall" colspan="6" style="letter-spacing:17px;" align="right">
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
%>