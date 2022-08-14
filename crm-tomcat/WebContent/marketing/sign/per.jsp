<%@ page language="java" pageEncoding="GBK" import="java.util.*,java.math.*,enfo.crm.system.*,enfo.crm.intrust.*,enfo.crm.vo.*, enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.dao.*" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
//从预约表中得到客户Id 然后在客户表中获取客户信息；
Integer scust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));

//+++++++start card save++++++++++
CustomerLocal local = EJBFactory.getCustomer();
// 获得对象
String s=session.getAttribute("card_id")!=null?session.getAttribute("card_id").toString():"";
if(!s.equals("")){
		CustomerVO cust_vo = new CustomerVO();
		cust_vo.setCust_id(scust_id);
		cust_vo.setCard_id(session.getAttribute("card_id").toString());
		cust_vo.setCard_type(session.getAttribute("card_type").toString());
		cust_vo.setCust_name(session.getAttribute("name").toString());
		cust_vo.setNation_name(session.getAttribute("nation").toString());
		cust_vo.setCard_address(session.getAttribute("address").toString());
		cust_vo.setSex(Utility.parseInt(Utility.trimNull(session.getAttribute("sex")), new Integer(1)));
		cust_vo.setBirthday(Utility.parseInt(Utility.trimNull(session.getAttribute("birth")), new Integer(0)));
		cust_vo.setIssued_date(Utility.parseInt(Utility.trimNull(session.getAttribute("issueDate")), new Integer(0)));
		cust_vo.setIssued_org(session.getAttribute("issuePlace").toString());
		cust_vo.setCard_valid_date(Utility.parseInt(Utility.trimNull(session.getAttribute("valid_date")), new Integer(0)));
		cust_vo.setInput_man(input_operatorCode);
		
		java.io.InputStream tt=(java.io.InputStream)session.getAttribute("inputstream");

		java.io.InputStream tt2=(java.io.InputStream)session.getAttribute("inputstream2");
		cust_vo.setInputStream(tt);//照片1
		cust_vo.setInputStream1(tt2);//照片2
		//end session保存

	    session.removeAttribute("card_id");
		session.removeAttribute("sex" );
		session.removeAttribute("birth");
		session.removeAttribute("address");
		session.removeAttribute("name");
		session.removeAttribute("card_type");
		session.removeAttribute("issueDate");
		session.removeAttribute("period");
		session.removeAttribute("issuePlace");
		session.removeAttribute("nation");
		session.removeAttribute("inputstream1");
		session.removeAttribute("inputstream2");
        local.modify3(cust_vo);
}

//页面内容查询
Map cust_map = new HashMap();
CustomerVO vo = new CustomerVO();
vo.setCust_id(scust_id);
vo.setInput_man(input_operatorCode);
List cust_list=local.listCustomerLoad(vo);
String voc_type = "";
if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0); 
	if(!"".equals(Utility.trimNull(cust_map.get("VOC_TYPE_NAME")))){
		String[] voc =  Utility.splitString(Utility.trimNull(cust_map.get("VOC_TYPE_NAME")),"-"); 
		voc_type =  voc[0];
	}
	Integer card_valid_date = Utility.parseInt(Utility.trimNull(cust_map.get("CARD_VALID_DATE")),new Integer(0));
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>合格投资人资格确认登记表（自然人)</title>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<meta HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media=print>
.Noprint{display:none;}
.PageNext{page-break-after: always;}
</style>
<style>
 td{font-size:15px;padding:5px;}
 </style>
<script language=javascript>
function printreturn(){
	window.close();	
}
</script>

</head>
<body topmargin="0" leftmargin="8" rightmargin="8">
<form name="theform"  method="post">
<OBJECT  id=WebBrowser  classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2  height=0  width=0></OBJECT>
<table border="0" width="100%" class="Noprint" id="btnprint">
		<tr>
			<td width="100%" align="right">									
			<button type="button"  class="xpbutton4" accessKey=p id="btnPrint" name="btnPrint" onclick="javascript:if(confirm('确认要打印吗？'))	{	document.all.WebBrowser.ExecWB(6,6);}">直接打印(<u>P</u>)</button>
			&nbsp;&nbsp;&nbsp;			
			<button class="xpbutton4" accessKey=a id="btnPrintset" name="btnPrintset" onclick="javascript:document.all.WebBrowser.ExecWB(8,1);">页面设置(<u>A</u>)</button>
			&nbsp;&nbsp;&nbsp;
			<button type="button"  class="xpbutton3" accessKey=b name="btnReturn" onclick="printreturn()">关闭</button>
			</td>
		</tr>
</table>
<table style="width:210mm;" border="0" cellspacing="0" cellpadding="0"  align="center">
	<tr>
		<td align="center" colspan="4"><b><font size="+2">北京国际信托有限公司</font></b></td>
	</tr>
	<tr>
		<td align="center" colspan="4"><b><font size="+2">合格投资人资格确认登记表（自然人）<font></b></td>
	</tr>
	<tr>
		<td width="33%"></td>
		<td width="34%" align="center"><%=Format.formatDateCn(Utility.getCurrentDate())%></td>
		<td width="23%" align="right">编号:</td>	
		<td width="10%"></td>
	</tr>		
</table>
<table style="width:210mm" border="1" cellspacing="0" cellpadding="1" summary="合格投资人资格确认登记表（自然人）" align="center">
  	<tr >
		<td rowspan="9" width="4px" align="center"><strong>委托人填写</strong></td>
		<td width="13%">委托人名称</td>
		<td width="10%">&nbsp;<%=Utility.trimNull(cust_map.get("CUST_NAME"))%></td>
		<td width="9%">性别</td>
		<td width="6%">&nbsp;<%=Utility.trimNull(cust_map.get("SEX_NAME"))%>&nbsp;</td>
		<td width="7%">国籍</td>
		<td width="11%">&nbsp;<%=Utility.trimNull(Argument.getDictParamName(9997,Utility.trimNull(cust_map.get("COUNTRY"))))%></td>
		<td width="21%">职业</td>
		<td width="22%">&nbsp;<%=Utility.trimNull(cust_map.get("VOC_TYPE_NAME"))%></td>
  </tr>
  <tr>
    <td>身份证件/证明文件类型</td>
    <td>&nbsp;<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%></td>
    <td colspan="2">身份证件/证明文件号码</td>
    <td colspan="2">&nbsp;<%=Utility.trimNull(cust_map.get("CARD_ID"))%></td>
    <td>身份证件/证明文件有效期</td>
    <td>&nbsp;<%if(card_valid_date.intValue() >= 21000101) {%>长期<%}else{%><%=Utility.trimNull(cust_map.get("CARD_VALID_DATE"))%><%} %></td>
  </tr>
  <tr>
    <td>联系地址</td>
    <td colspan="5">&nbsp;<%=Utility.trimNull(cust_map.get("POST_ADDRESS"))%></td>
    <td>邮政编码</td>
    <td colspan="1">&nbsp;<%=Utility.trimNull(cust_map.get("POST_CODE"))%></td>
  </tr>
  <tr>
    <td>联系电话</td>
    <td colspan="5">&nbsp;</td>
    <td>电子邮箱</td>
    <td colspan="1">&nbsp;<%=Utility.trimNull(cust_map.get("E_MAIL"))%></td>
  </tr>
  <tr>
    <td colspan="8" width="100%">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td colspan="2"><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>投资于一个信托计划的最低金额不少于人民币100万元</td>
			</tr>
			<tr>
				<td colspan="2"><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>自然人投资于一个信托计划的最低金额少于人民币100万元的</td>
			</tr>
			<tr>
				<td align="right" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/checkbox.gif"/></td>
				<td>个人或家庭金融资产总计在其认购时超过100 万元人民币</td>
			</tr>
			<tr>
				<td align="right" valign="top">&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=request.getContextPath()%>/images/checkbox.gif"/></td>
				<td>个人收入在最近三年内每年收入超过20 万元人民币或者夫妻双方合计收入在最近三年内每年收入超过30 万元人民币</td>
			</tr>
			<tr>
				<td colspan="2"><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>投资于本公司发行的信托计划仍在存续期间的金额不少于人民币100万元</td>
			</tr>
		</table>
     </td>
  </tr>
  <tr>
    <td>代办人姓名</td>
    <td>&nbsp;</td>
    <td colspan="2">代办人国籍</td>
    <td colspan="2">&nbsp;</td>
    <td>联系电话</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>身份证件/证明文件类型</td>
    <td>&nbsp;</td>
    <td colspan="2">身份证件/证明文件号码</td>
    <td colspan="2">&nbsp;</td>
    <td>身份证件/证明文件有效期</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td>资金来源声明</td>
    <td colspan="7" >
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>工薪收入 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>经营性收入 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>财产性收入 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>投资收益  
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>捐赠收入 
		<img src="<%=request.getContextPath()%>/images/checkbox.gif"/>其他收入
	</td>
  </tr>
  <tr>
    <td><div> <strong>郑重声明</strong><br />
    </div></td>
    <td colspan="7" style="text-indent:2em;">
		<strong>本委托人交付给贵司的信托资金为本委托人本人合法所有的财产，不存在非法汇集他人资金参与信托计划的情形。上述资金与其他任何个人、法人及其他组织不存在法律上的任何纠纷。如因违反上述承诺而产生的一切法律后果均由本委托人全部承担。
  		</strong></br><br>
		<table width="100%">
			<tr>
				<td width="40%"> <strong>委托人或代办人签名：</strong></td>
				<td width="20%"><strong>（印鉴）</strong></td>
				<td width="10%"><strong>年</strong></td>
				<td width="10%"><strong>月</strong></td>
				<td width="10%"><strong>日</strong></td>
				<td width="*"></td>
			</tr>
		</table></td>
  </tr>
  <tr>
    <td><strong>资格审查人填写</strong> </td>

   <td colspan="8">
		<table width="100%" cellpadding="0" cellspacing="0" border="0">
			<tr>
				<td><strong>审核资料：</strong> </td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>申请人填妥的内容</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>银行盖章的存款证明（人民币或外币）</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>持有股票、基金及其他证券证明或资金账户余额证明（券商或托管机构出具）</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>投资其他信托计划或银行理财计划的证明</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>投资于券商理财计划证明</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>其他投资账户的资金证明</td>
			</tr>			
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>投资型保险产品证明</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>其他金融资产证明</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>个人所得税完税证明</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>其它收入证明</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>委托人身份证件原件及复印件</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>代办人身份证件原件及复印件</td>
			</tr>
			<tr>
				<td><img src="<%=request.getContextPath()%>/images/checkbox.gif"/>委托人授权委托书原件</td>
			</tr>
		</table>
		<table width="100%">
			<tr>
				<td width="25%"> 经办人：</td>
				<td width="25%"> 负责人：</td>
				<td width="15%">（盖章）</td>
				<td width="8%">年</td>
				<td width="8%">月</td>
				<td width="8%">日</td>
				<td width="*"></td>
			</tr>
		</table>
	</td>
  </tr>  
</table>
<table style="width:210mm" align="center">
	<tr>
		<td valign="top" align="right" style="width:18mm;">职业选项:</td>
		<td rowspan="3">1A：各类专业技术人员；1B：国家机关、党群组织、企事业单位的负责人；1C：办事人员和有关人员；1D：商业工作人员；1E：服务性工作人员；1F：农林牧渔劳动者；1G：生产工作、运输工作和部分体力劳动者；1H：不便分类的其他劳动者</td>
	</tr>
	<tr><td><td></tr>
	<tr><td><td></tr>
</table>
</form>
</body>
</html>
<%
local.remove();
%>