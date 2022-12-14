<%@ page contentType="text/html; charset=GBK" import="enfo.crm.affair.*,enfo.crm.intrust.*,enfo.crm.customer.*,enfo.crm.tools.*,enfo.crm.marketing.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>


<%
Integer partn_id =  Utility.parseInt(Utility.trimNull(request.getParameter("partn_id")),new Integer(0));

//渠道\媒体
Integer q_partn_type2_flag = Utility.parseInt(Utility.trimNull(request.getParameter("q_partn_type2_flag")),new Integer(2));
String q_partn_type2_name = "";

if(q_partn_type2_flag.intValue()==1){
	q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.channels",clientLocale);//渠道
}
else if(q_partn_type2_flag.intValue()==2){
	q_partn_type2_name =enfo.crm.tools.LocalUtilis.language("message.media",clientLocale);//媒体
}

//声明字段
String partn_name = "";
String partn_no = "";
String card_type ="";
String card_type_name = "";
String card_id ="";
Integer sex = new Integer(0);
String sex_name = "";
Integer age =  new Integer(0);
Integer birthday =  new Integer(0);
String voc_type="";
String voc_type_name = "";
String contract_man ="";
String summary ="";
String touch_type ="";
String touch_type_name = "";
String o_tel = "";
String h_tel = "";
String mobile ="";
String bp = "";
String fax = "";
String e_mail = "";
String post_code = "";
String post_address = "";
String report_type = "";
String report_type_name = "";
String legal_man  = "";

//获得对象
PartnerLocal partnerLocal = EJBFactory.getPartner();
PartnerVO vo = null;

if(partn_id.intValue()>0){
	vo = new PartnerVO();
	
	vo.setPartn_id(partn_id);
	List modiList = partnerLocal.query(vo);
	Map modiMap = null; 
	
	if(modiList.size()>0){
		modiMap = (Map)modiList.get(0);
	}
	
	if(modiMap!=null){
		partn_no = Utility.trimNull(modiMap.get("PARTN_NO"));
		partn_name = Utility.trimNull(modiMap.get("PARTN_NAME"));
		card_type =Utility.trimNull(modiMap.get("CARD_TYPE"));
		card_type_name = Utility.trimNull(modiMap.get("CARD_TYPE_NAME"));
		card_id =Utility.trimNull(modiMap.get("CARD_ID"));
		sex = Utility.parseInt(Utility.trimNull(modiMap.get("SEX")),new Integer(1));
		sex_name = Utility.trimNull(modiMap.get("SEX_NAME"));
		age = Utility.parseInt(Utility.trimNull(modiMap.get("AGE")),new Integer(0));
		birthday = Utility.parseInt(Utility.trimNull(modiMap.get("BIRTHDAY")),new Integer(Utility.getCurrentDate()));
		voc_type = Utility.trimNull(modiMap.get("VOC_TYPE"));
		voc_type_name = Utility.trimNull(modiMap.get("VOC_TYPE_NAME"));
		//contract_man = Utility.trimNull(modiMap.get("CARD_ID"));
		summary =Utility.trimNull(modiMap.get("SUMMARY"));
		touch_type = Utility.trimNull(modiMap.get("TOUCH_TYPE"));
		touch_type_name = Utility.trimNull(modiMap.get("TOUCH_TYPE_NAME"));
		o_tel =Utility.trimNull(modiMap.get("O_TEL"));
		h_tel =Utility.trimNull(modiMap.get("H_TEL"));
		mobile = Utility.trimNull(modiMap.get("MOBILE"));
		bp = Utility.trimNull(modiMap.get("BP"));
		fax = Utility.trimNull(modiMap.get("FAX"));
		e_mail = Utility.trimNull(modiMap.get("E_MAIL"));
		post_code =Utility.trimNull(modiMap.get("POST_CODE"));
		post_address =Utility.trimNull(modiMap.get("POST_ADDRESS"));
		report_type = Utility.trimNull(modiMap.get("REPORT_TYPE"));
		report_type_name =  Utility.trimNull(modiMap.get("REPORT_TYPE_NAME"));
		legal_man =  Utility.trimNull(modiMap.get("LEGAL_MAN"));
	}
}


%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<title><%=q_partn_type2_name%><%=LocalUtilis.language("message.QueryAllInfo",clientLocale)%> </title><!--信息综合查询-->
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
	WIDTH: 72px;
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
	WIDTH: 72px;
	PADDING-TOP: 0px;
	BACKGROUND-COLOR: white;
	BORDER-BOTTOM: 0px solid;
	HEIGHT: 20px;
}
</style>

<script language=javascript>
/*启动加载*/	
window.onload = function(){
	show(1);
}

function show(parm){
   for(i=1;i<3;i++) {  
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
</script>
</HEAD>

<BODY class="BODY">
<%@ include file="/includes/waiting.inc"%>
<div>
	<table id="titleNameTable" border="0" width="95%" cellspacing="1" cellpadding=0>
			<tr>
				<td>
					&nbsp;&nbsp;<img border="0" src="<%=request.getContextPath()%>/images/Feichuang5.jpg"  height="36">
					<b><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= partn_name%></font></b>
				</td>
			</tr>
	</table>
</div>

<div align="center">
		<TABLE cellSpacing=0 cellPadding=0 width="95%" border="0" class="edline">
					<TBODY>
						<TR>
							<TD vAlign=top>&nbsp;</TD>							
							<!--基本信息-->
                            <TD id="d1" style="background-repeat: no-repeat" onclick=show(1) vAlign=top width=70 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </TD>
							<!--联系方式-->
                            <TD id="d2" style="background-repeat: no-repeat" onclick=show(2) vAlign=top width=70 height=20 background='<%=request.getContextPath()%>/images/headdark_00_01.gif'>&nbsp;&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> </TD>
						</TR>
				</TBODY>
		</TABLE>
</div>

<div id="r1" align="center" style="display:none;margin-top:30px;">
<br>
<table  id="table1" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
		 	<tr style="background:F7F7F7;">
		 			<td colspan="4" align="left"><font size="4" face="微软雅黑"><b>&nbsp;&nbsp;<%=LocalUtilis.language("message.basicInformation",clientLocale)%> </b></font></td><!--基本信息-->
		 	</tr>
		 	
	 		 <tr style="background:F7F7F7;">	
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.ID",clientLocale)%> ：</font></td><!--编号-->
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= partn_no%></font>  </td>
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=q_partn_type2_name%><%=LocalUtilis.language("class.name",clientLocale)%> ：</font></td><!--名称-->
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= partn_name%></font> </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCardType",clientLocale)%> ：</font></td><!--证件类型-->
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= card_type_name%></font>  </td>
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerCardID",clientLocale)%> ：</font></td><!--证件号码-->
				 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= card_id%></font> </td>
			 </tr>
			 
			 <tr style="background:F7F7F7;">	
				 <%if(q_partn_type2_flag.intValue()==1){%>
			 			 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.industry",clientLocale)%> ：</font></td><!--所属行业-->
			 			<td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= voc_type_name%></font> </td>
				 	<%
				 	}else if(q_partn_type2_flag.intValue()==2) {%>
						 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.reportType",clientLocale)%> ：</font></td><!--报道形式-->
			 			<td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= report_type_name%></font> </td>
					<%}%>
					 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.corporate",clientLocale)%> ：</font></td><!--法人姓名-->
					 <td width="25%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= legal_man%></font> </td>
			 </tr>
			 
			<tr style="background:F7F7F7;">
				<td>&nbsp;&nbsp;<%=LocalUtilis.language("class.summary",clientLocale)%> ：</td><!--备注信息-->
				<td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= summary%></font> </td>
			</tr>		
</table>
</div>

<div id="r2" align="center" style="display:none;">
		<br>
		<table  id="table2" cellSpacing="1" cellPadding="2" width="95%"  bgcolor="#CCCCCC">
			<tr style="background:F7F7F7;">
				<td ><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.serviceWay",clientLocale)%> ：</font><br></td><!--联系方式-->
				<td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= touch_type_name%></font><br></td>
			</tr>
			
			<tr style="background:F7F7F7;">
				<td width="15%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerHTel",clientLocale)%> ：</font><br></td><!--家庭电话-->
				<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= h_tel%></font><br></td>
				<td width="15%"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.companyPhone",clientLocale)%> ：</font><br></td><!--公司电话-->
				<td width="*"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= o_tel%></font><br></td>
			</tr>
			
			<tr style="background:F7F7F7;">
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerMobile",clientLocale)%> ：</font><br></td><!--手机-->
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= mobile%></font><br></td>
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.customerMobile",clientLocale)%> 2：</font><br></td><!--手机-->
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= bp%></font><br></td>
			</tr>
			
			<tr style="background:F7F7F7;">
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.fax",clientLocale)%> ：</font><br></td><!--传真-->
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= fax%></font><br></td>
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;E-MAIL：</font><br></td>
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= e_mail%></font><br></td>
			</tr>
			
			<tr style="background:F7F7F7;">
				<td><font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.postcode",clientLocale)%> ：</font><br></td><!--邮政编码-->
				<td colspan="3"><font size="2" face="微软雅黑">&nbsp;&nbsp;<%= post_code%></font><br></td>
			</tr>
			
			<tr style="background:F7F7F7;">
				<td>&nbsp;<font size="2" face="微软雅黑">&nbsp;&nbsp;<%=LocalUtilis.language("class.postAddress2",clientLocale)%> ：</font><br></td><!--邮寄地址-->
				<td colspan="3">&nbsp;<font size="2" face="微软雅黑">&nbsp;&nbsp;<%= post_address%></font><br></td>
			</tr>			
		</table>
</div>

<% partnerLocal.remove();%>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>
