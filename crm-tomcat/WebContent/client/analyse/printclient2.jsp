<%@ page contentType="text/html; charset=GBK" import="enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>

<HTML>
<HEAD>
<TITLE>国民信托对账单</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/print.css" type=text/css rel=stylesheet>

</HEAD>

<BODY  leftMargin=0 topMargin=0 rightmargin=0>
<FORM name="theform" method="post">
<DIV style="width:100%;height:90%" align="center">
	<DIV style="height:955px；">
		<div align="center"  style="font-size:22px;line-height:50px">国民·东方远见一期集合资金信托计划对帐单</div>
		<div align="left" style="margin-left:50px;">
				<div style="font-size:16px;" align="left"><b>信托合同编号：NT托字07-016-04-     号</b></div>
				<div style="font-size:16px;" align="left"><b>委托人名称：</b></div>
				<div style="font-size:16px;" align="left"><b>身份证号码：</b></div>
				<div style="font-size:16px;" align="left"><b>对账时间：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;至&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></div>
		
				<br>
				<div style="font-size:16px;" align="left"><b>一、本期账户信托财产余额：（截至：      ）</b></div>
				<p>
				<div style="font-size:16px;" align="left">
					<table cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
						<tr style="background:F7F7F7;">
							<td  style="font-size:16px;" align="center"><b>项目名称</b></td>
							<td  style="font-size:16px;" align="center"><b>信托单位份数</b></td>
							<td  style="font-size:16px;" align="center"><b>单位净值</b></td>
							<td  style="font-size:16px;" align="center"><b>信托财产净值</b></td>
						</tr>
					</table>
				</div>
				<p>
				<div style="font-size:16px;" align="left"><b>二、本期交易明细：（         至         ）</b></div>
				<p>
				<div style="font-size:16px;" align="left">
					<table cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
						<tr style="background:F7F7F7;">
							<td  style="font-size:16px;" align="center"><b>申请日期</b></td>
							<td  style="font-size:16px;" align="center"><b>认购份额</b></td>
							<td  style="font-size:16px;" align="center"><b>业务类型</b></td>
							<td  style="font-size:16px;" align="center"><b>当日净值</b></td>
							<td  style="font-size:16px;" align="center"><b>确认份额</b></td>
							<td  style="font-size:16px;" align="center"><b>确认金额</b></td>
							<td  style="font-size:16px;" align="center"><b>交易费用</b></td>
						</tr>
					</table>
				</div>
		</div>
	</DIV>
</DIV>
</FROM>
</BODY>
</HTML>
