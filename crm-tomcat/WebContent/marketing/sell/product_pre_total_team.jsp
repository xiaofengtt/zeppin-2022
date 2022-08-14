<%@ page contentType="text/html; charset=GBK" import="java.net.URLDecoder,enfo.crm.customer.*,enfo.crm.marketing.*,java.util.Date,java.text.SimpleDateFormat,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.contractManage.*" %>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/parameter.inc" %>
<%@ include file="/includes/operator.inc" %>
<%
Integer product_id = Utility.parseInt(Utility.trimNull(request.getParameter("product_id")),new Integer(0));
Integer pre_product_id = Utility.parseInt(Utility.trimNull(request.getParameter("pre_product_id")),new Integer(0));
String pre_product_name = URLDecoder.decode(Utility.trimNull(request.getParameter("pre_product_name")));
String pre_money = Utility.trimNull(request.getParameter("pre_money"));
SaleParameterLocal local = EJBFactory.getSaleParameter();
SaleParameterVO vo = new SaleParameterVO();
vo.setPre_product_id(pre_product_id);
vo.setProductID(product_id);
List list = local.queryQuota(vo, input_operatorCode);
Map map = new HashMap();


%>
<HTML>
<HEAD>
<TITLE>��ƷԤԼͳ����Ϣ</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<script type="text/javascript">
//�鿴�Ŷ�ԤԼ��ϸ��Ϣ
function showTeamReserveInfo(pre_product_id, team_id, team_name){
	showModalDialog('<%=request.getContextPath()%>/marketing/sell/product_pre_total_team_info.jsp?pre_product_id=' + pre_product_id + '&team_id=' + team_id + '&team_name=' +team_name + '&pre_product_name=<%=pre_product_name%>', '', 'dialogWidth:800px;dialogHeight:400px;status:0;help:0;dialogLeft:200px;dialogTop:200px;')
}
</script>
</HEAD>
<BODY class="BODY body-nox" >
<%@ include file="/includes/waiting.inc"%>
<form name="theform" method="POST">
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>��ƷԤԼͳ����Ϣ</b></font>
	</div>
	<div align="left">
		<font color="#215dc6" size="+1">��Ʒ���ƣ�<%=pre_product_name %>&nbsp;&nbsp;&nbsp;&nbsp;��ԤԼ��ģ��<%=pre_money %></font>
	</div>
	<br/>
</div>
<div>
	<div>
	<table border="0" cellspacing="1" cellpadding="2" width="100%">
		<tr>
	   		<td width="70%" valign="top">
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%">
					<tr class="trh">
				   		<td rowspan="2" align="center">�Ŷ�����</td>
				    	<td colspan="2" align="center">���</td>
				    	<td colspan="3" align="center">��ǰԤԼ</td>
				    	<td colspan="3" align="center">��ǰ���(�ѵ���)</td>
				  	</tr>
				  	<tr class="trh">
				    	<td align="center">���</td>
				    	<td align="center">С�����</td>
				    	<td align="center">���</td>
				    	<td align="center">С�����</td>
						<td align="center">�ܷ���</td>
				    	<td align="center">���</td>
				    	<td align="center">С�����</td>
						<td align="center">�ܷ���</td>
				  	</tr>		
					<%
					BigDecimal q_money_total = new BigDecimal(0.00);
					int q_num_total = 0;
					BigDecimal p_money_total = new BigDecimal(0.00);
					int p_num_total = 0;
					BigDecimal d_money_total = new BigDecimal(0.00);
					int d_num_total = 0;
					int p_tnum_total=0;
					int dz_num_total=0;					


					int iCount = 0;
					String[] teamName = new String[]{""};
					String[] teamValue = new String[]{""};
					String[][] value = new String[][]{{""}};
					String[][] percentValue = new String[][]{{""}};
					String[] percentValuee = new String[]{""};
					String[] color = new String[]{""};
					int rows = list.size();
					teamName = new String[rows];
					teamValue = new String[2];
					color = new String[2];
					value = new String[2][rows];
					percentValue = new String[1][rows];
					percentValuee = new String[1];
					percentValuee[0] = "���˹�ģռԤԼ��ģ����";
					teamValue[0] = "�����";
					teamValue[1] = "������";
					color[0] = "AFD8F8";
					color[1] = "F6BD0F";
					boolean del_flag = false;
					for(int i=0;i< list.size();i++){
						map = (Map)list.get(i);
						teamName[i] = Utility.trimNull(map.get("TEAM_NAME"));
						value[0][i] = Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00)).divide(new BigDecimal(10000),BigDecimal.ROUND_HALF_UP));
						value[1][i] = Utility.trimNull(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00)).divide(new BigDecimal(10000),BigDecimal.ROUND_HALF_UP));
						del_flag = Utility.parseInt(Utility.trimNull(map.get("INTHISTEAM")), new Integer(0)).intValue() == 1;

						q_money_total = Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00)).add(q_money_total);
						q_num_total = q_num_total + Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")), 0);
						p_money_total = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00)).add(p_money_total);
						p_num_total = p_num_total + Utility.parseInt(Utility.trimNull(map.get("PRE3000000")), 0); //ԤԼС��300���С�����
						d_money_total = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONE")), new BigDecimal(0.00)).add(d_money_total);
						d_num_total = d_num_total + Utility.parseInt(Utility.trimNull(map.get("NUM3000000")), 0);
						p_tnum_total=p_tnum_total+Utility.parseInt(Utility.trimNull(map.get("PRE_TEAM_NUM")),0);
						dz_num_total=dz_num_total+Utility.parseInt(Utility.trimNull(map.get("DZ_NUM")),0);
						
						BigDecimal percent = BigDecimal.ZERO;
						BigDecimal test = Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONE")), new BigDecimal(0.00));//����
						BigDecimal test2 = Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00));//ԤԼ
						if(test2.compareTo(BigDecimal.ZERO) == 0){
							percent = BigDecimal.ZERO;
						}else{
							percent = test.divide(test2,2, BigDecimal.ROUND_HALF_UP);
						}
						percentValue[0][i] = percent.multiply(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP).stripTrailingZeros().toPlainString();
						
			 		%>
					<tr class="tr<%=(iCount%2)%>" <%if(del_flag && user_id.intValue()!=21){%>style="cursor: pointer;" title="�鿴�Ŷ�ԤԼ��ϸ��Ϣ" ondblclick="javascript:showTeamReserveInfo('<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(pre_product_id), new Integer(0)))%>','<%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("TEAM_ID")), new Integer(0)))%>','<%=Utility.trimNull(map.get("TEAM_NAME")) %>');"<%}%>>
						 <td height="30">
							<!-- ��ǰ����Ա���ڸ��Ŷӣ�����Բ鿴�����Ŷӵ�ԤԼ��ϸ��Ϣ -->
							<font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(map.get("TEAM_NAME")) %></font>
						 </td>
					     <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("QUOTAMONEY")), new BigDecimal(0.00)))) %></font></td><!-- �Ŷ������ -->
					     <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("QUOTA_QUALIFIED_NUM")), null)) %></font></td><!-- �Ŷ����С����� -->
					     <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("PRE_SALEMONEY")), new BigDecimal(0.00)))) %></font></td><!-- ��ԤԼ��� -->
					     <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("PRE3000000")),null)) %></font></td><!-- ��ԤԼС����� -->
						 <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("PRE_TEAM_NUM")),null)) %></font></td><!-- ��ԤԼ�ܷ��� -->
						 <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Format.formatMoney(Utility.parseDecimal(Utility.trimNull(map.get("DZ_MONE")), new BigDecimal(0.00)))) %></font></td><!-- �ѵ��˽�� -->
					     <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("NUM3000000")), null)) %></font></td><!-- �ѵ���С����� -->
						 <td  align="right"><font <%if(del_flag){%>color="red"<%}%> style="font-size: 12px;"><%=Utility.trimNull(Utility.parseInt(Utility.trimNull(map.get("DZ_NUM")),null)) %></font></td><!-- �ѵ����ܷ��� -->
					</tr>	
					<%
					iCount++;
					}
					%>
					<tr class="trbottom">
					<td class="tdh" align="left"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%><!--�ϼ�-->&nbsp;<%=list.size()%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> <!--��--></b></td>
						<td class="tdh" align="right"><%=Utility.trimNull(Format.formatMoney(q_money_total)) %></td>
						<td class="tdh" align="right"><%=q_num_total%></td>
						<td class="tdh" align="right"><%=Utility.trimNull(Format.formatMoney(p_money_total)) %></td>
						<td class="tdh" align="right"><%=p_num_total%></td>
						<td class="tdh" align="right"><%=p_tnum_total%></td>
						<td class="tdh" align="right"><%=Utility.trimNull(Format.formatMoney(d_money_total)) %></td>
						<td class="tdh" align="right"><%=d_num_total%></td>
						<td class="tdh" align="right"><%=dz_num_total %></td>
					</tr>				
				</table><%if (user_id.intValue()!=21){/*�����ȥ��*/ %><font color="red">ע��:��ɫ������˫���鿴��ϸ</font><%} %>
			</td>
	    	<td width="30%" valign="top">
				<%
				FusionCharts Chart = new FusionCharts();
				FusionChartsGanerate ChartCreator = new FusionChartsGanerate();
				String XMLStr = ChartCreator.generateStackedBar2D(teamName,percentValuee,percentValue,color,"","�Ŷ�����ͳ��ͼ(��λ��%25)","");
				int height = 42*iCount + 27;
				String chartHTMLCode = Chart.createChartHTML(request.getContextPath()+"/includes/charts/StackedBar2D.swf","",XMLStr,"ENFO",350,height,false);
				%>
				<table border="0" cellspacing="0" cellpadding="0" width="100%">
					<tr bgcolor="white">
						<td align="center">
							<%=chartHTMLCode%>
						</td>
					</tr>
				</table>
			</td>
	  	</tr>
	</table>
	
	</div>
</div>
<div align="right" style="height: 30px; vertical-align: bottom;">
	<button type="button"  class="xpbutton3" accessKey=b id="btnCancel" name="btnCancel" onclick="javascript:window.close();">�ر�(<u>B</u>)</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</div>				
</form>
<%@ include file="/includes/foot.inc" %>	
</BODY>
</HTML>
<%
local.remove();
%>
