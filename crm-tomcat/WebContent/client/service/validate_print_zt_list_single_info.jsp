<%@ page contentType="text/html; charset=GBK"  import="enfo.crm.tools.*,enfo.crm.intrust.*,java.math.*,enfo.crm.customer.*,enfo.crm.vo.* ,enfo.crm.dao.*,java.util.*" %>
<%@ include file="/includes/operator.inc" %>
<%
ValidateprintLocal validate = EJBFactory.getValidateprint();

//��ҳ����ֵ
Integer product_id = Utility.parseInt(request.getParameter("product_id"),null);
String contract_sub_bh = request.getParameter("contract_sub_bh");
Integer serial_no = Utility.parseInt(request.getParameter("serial_no"),null);

//��������
String product_name = "";
BigDecimal chg_money = null;
BigDecimal chg_amount = null;
String toChina = "";


//��ѯ��Ϣ
validate.setProduct_id(product_id);
validate.setContract_sub_bh(contract_sub_bh);
validate.setSerial_no(serial_no);
validate.setInput_man(input_operatorCode);
List list = validate.listBySql1();

	Map map = (Map)list.get(0);
	product_name = Utility.trimNull(map.get("PRODUCT_NAME"));
	contract_sub_bh = Utility.trimNull(map.get("CONTRACT_SUB_BH"));
	validate.setContract_sub_bh(Utility.trimNull(map.get("CONTRACT_SUB_BH")));
	validate.setCust_name(Utility.trimNull(map.get("CUST_NAME")));
    Integer chg_type=Utility.parseInt(Utility.trimNull(map.get("BD_BUSI_ID")),null);

	if(chg_type.intValue()==1){
		validate.setChg_type_name("�Ϲ�");
	}else if(chg_type.intValue()==2){
		validate.setChg_type_name("�깺");
	}

	chg_money=Utility.parseDecimal(Utility.trimNull(map.get("ADD_MONEY")),null);
	validate.setChg_amount(Utility.parseDecimal(Utility.trimNull(map.get("ADD_AMOUNT")),null));
	validate.setSq_date(Utility.parseInt(Utility.trimNull(map.get("TRADE_DATE")),null));
	validate.setProduct_id(Utility.parseInt(Utility.trimNull(map.get("PRODUCT_ID")),null));
	serial_no = Utility.parseInt(Utility.trimNull(map.get("SERIAL_NO")),null);


	int intrust_flag1 = Utility.parseInt(Utility.trimNull(map.get("PRINT_FLAG")),1);

	chg_money = chg_money.setScale(2,2);
	toChina = Utility.numToChinese(Utility.trimNull(chg_money));
	chg_amount=validate.getChg_amount().setScale(2,2);
	
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
<LINK HREF="/includes/print.css" TYPE="text/css" REL="stylesheet">
<style media="screen">
.show     { display: none }
</style>
<style media="print">
.noprint     { display: none }
.trheight	 { height:50px }
</style>
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/investment.js"></SCRIPT>
<script language=javascript>
</script>
<BODY class="BODY" leftMargin=0 topMargin=0 rightmargin=0></br>
<div STYLE="page-break-after: always;">
	<p style=" margin-left=380" class="MsoNormal" >
		<strong style="mso-bidi-font-weight: normal margin-left=380">
			<span style="FONT-FAMILY: ����; FONT-SIZE: 15pt; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">�н�Ͷ�����������ι�˾</span>
		</strong>
	</p>
	<p style="margin-left=290" class="MsoNormal" >
		<strong style="mso-bidi-font-weight: normal">
			<span style="FONT-FAMILY: ����; FONT-SIZE: 15pt; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">�����ʽ����мƻ����е�λ�Ϲ�/�깺ȷ����</span>
		</strong>
	</p></br>
	<p style="MARGIN: 0cm 0cm 0pt 21pt" class="MsoNormal">
		<font size="3";>
			<font face="Times New Roman"><%=Format.formatDateCn(validate.getSq_date().intValue()) %></font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<span style="FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; mso-hansi-font-family: 'Times New Roman'">��λ�������Ԫ</span>
		</font>
	</p></br>	
	<table style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; BORDER-COLLAPSE: collapse; BORDER-TOP: medium none; 
			BORDER-RIGHT: medium none; mso-padding-alt: 0cm 5.4pt 0cm 5.4pt; mso-border-alt: solid windowtext .5pt; mso-yfti-tbllook: 480; 
			mso-border-insideh: .5pt solid windowtext; mso-border-insidev: .5pt solid windowtext" class="MsoTableGrid" border="1" cellspacing="0" cellpadding="0">
    	<tbody>
       		<tr style="HEIGHT: 22.85pt; mso-yfti-irow: 0; mso-yfti-firstrow: yes">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.85pt; BORDER-TOP: windowtext 1pt solid; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'">��Ŀ����
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent;
						 PADDING-LEFT: 5.4pt; WIDTH: 531.55pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.85pt; BORDER-TOP: windowtext 1pt solid; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: 
						solid windowtext .5pt" valign="top" width="709" colspan="3">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(product_name)%>
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 22.45pt; mso-yfti-irow: 1">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">ί��������
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(validate.getCust_name())%>
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
							mso-hansi-font-family: 'Times New Roman'">��ͬ���
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.45pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
							mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(contract_sub_bh)%>
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 22.75pt; mso-yfti-irow: 2">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'">�Ϲ�/�깺�ʽ𣨴�д��
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'"><%=Utility.trimNull(toChina)%>
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; 
						mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'">Сд
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; PADDING-LEFT: 5.4pt; 
						WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.75pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
						PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
						mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
									mso-hansi-font-family: 'Times New Roman'"><%=chg_money%>&nbsp;Ԫ
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 23.05pt; mso-yfti-irow: 3">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						 BORDER-TOP: #ece9d8;BORDER-RIGHT: windowtext 1pt solid; ">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3" face="Times New Roman"><%=Format.formatDateCn(validate.getSq_date().intValue())%>��ʼ���е�λ��ֵ</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
						PADDING-LEFT: 5.4pt; WIDTH: 531.55pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.05pt; BORDER-TOP: #ece9d8; 
						BORDER-RIGHT: windowtext 1pt solid; PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; 
						mso-border-left-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="709" colspan="3">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; mso-bidi-font-size: 10.5pt" lang="EN-US">
								<font face="Times New Roman">1.00&nbsp;Ԫ</font>
							</span>
						</font>
					</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 23.35pt; mso-yfti-irow: 4">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            	<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
					<font size="3">
						<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">�Ϲ�/�깺���е�λ����/��
						</span>
					</font>
				</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'"><%=chg_amount %>&nbsp;��
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">�������е�λ�ܷ���/��
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.2pt; PADDING-RIGHT: 5.4pt; HEIGHT: 23.35pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid; 
					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
           		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
					<font size="3">
						<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
							mso-hansi-font-family: 'Times New Roman'"><%=chg_amount %>&nbsp;��
						</span>
					</font>
				</p>
            	</td>
        	</tr>
        	<tr style="HEIGHT: 22.25pt; mso-yfti-irow: 5; mso-yfti-lastrow: yes">
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: windowtext 1pt solid; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 177.15pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.25pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
 					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-top-alt: solid windowtext .5pt" valign="top" width="236">
            		<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">��ע
							</span>
						</font>
					</p>
            	</td>
            	<td style="BORDER-BOTTOM: windowtext 1pt solid; BORDER-LEFT: #ece9d8; PADDING-BOTTOM: 0cm; BACKGROUND-COLOR: transparent; 
					PADDING-LEFT: 5.4pt; WIDTH: 531.55pt; PADDING-RIGHT: 5.4pt; HEIGHT: 22.25pt; BORDER-TOP: #ece9d8; BORDER-RIGHT: windowtext 1pt solid;
 					PADDING-TOP: 0cm; mso-border-alt: solid windowtext .5pt; mso-border-left-alt: solid windowtext .5pt; 
					mso-border-top-alt: solid windowtext .5pt" valign="top" width="709" colspan="3">
           			<p style="TEXT-ALIGN: center; LINE-HEIGHT: 200%; MARGIN: 0cm 0cm 0pt" class="MsoNormal" align="center">
						<font size="3">
							<span style="LINE-HEIGHT: 200%; FONT-FAMILY: ����; mso-bidi-font-size: 10.5pt; mso-ascii-font-family: 'Times New Roman'; 
								mso-hansi-font-family: 'Times New Roman'">
							</span>
						</font>
					</p>
            	</td>
        	</tr>
    	</tbody>
	</table></br>
		<p style="margin-left=770">
			<font size="3"></br></br>
	�н�Ͷ�����������ι�˾�����£�
			</font></br></br></br></br>
		</p>
	</br></br></br></br></br></br></br></br></br></br>
</div>
</BODY>
</HTML>