<%@ page contentType="text/html; charset=GBK" import="enfo.crm.marketing.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,enfo.crm.customer.*,java.io.File" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%
File n_file = new File("D:\\CARD");
if(!n_file.exists())
	 n_file.mkdirs();
//��ԤԼ���еõ��ͻ�Id Ȼ���ڿͻ����л�ȡ�ͻ���Ϣ��
Integer scust_id = Utility.parseInt(Utility.trimNull(request.getParameter("cust_id")), new Integer(0));
CustomerLocal local = EJBFactory.getCustomer();
Map cust_map = new HashMap();
CustomerVO vo = new CustomerVO();
vo.setCust_id(scust_id);
vo.setInput_man(input_operatorCode);
List cust_list=local.listCustomerLoad(vo);

if(cust_list != null && cust_list.size() > 0)
	cust_map = (Map)cust_list.get(0);

%>
<HTML>
<HEAD>
<TITLE>֤�����</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<LINK href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
<LINK href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></SCRIPT>
<SCRIPT LANGUAGE="vbscript" SRC="<%=request.getContextPath()%>/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="<%=request.getContextPath()%>/includes/calendar.js"></SCRIPT>
<script language=javascript>
//���������֤
function FnTcIdCheck(){
	
    var pathPhoto = "D:\\CARD\\ResultCheckImage.jpg";
	try {
		var pid = document.getElementById('card_id').value;
		var pname = document.getElementById('name').value;
		var ocxObj = document.getElementById('TcCardOcr');
		ocxObj.TcIDCheck( pid,pname,'D:\\CARD\\ResultCheckText.txt',pathPhoto);
	    var rt = ocxObj.TcGetBackValue();
	    var rm = ocxObj.TcGetBackMessage();
        var rr = ocxObj.TcGetResultFile();
        //alert( "����ֵ:" + rt + ";������Ϣ:" + rm );
	   switch(parseInt(rr))
			   ����{
                   case 0:
                         sl_alert("���֤��Ϣƥ��һ�£���������Ƭ���˲�ɹ�");
				 ����    document.theform.summary.value="���֤��Ϣƥ��һ�£���������Ƭ���˲�ɹ�";
                         document.theform.state.value="1";
                         document.getElementById('image').src = "D:\\CARD\\ResultCheckImage.jpg";
				 ����    break
			����   case 1:
                         sl_alert("���֤��Ϣƥ��һ�£�����Ƭ���أ��˲�ɹ�");
			����         document.theform.summary.value="���֤��Ϣƥ��һ�£�����Ƭ���أ��˲�ɹ�";
                         document.theform.state.value="2";
                         break
                   case 2:
                         sl_alert("�д�֤�ţ���������֤�Ų���,�˲�ʧ��");
                         document.theform.summary.value="�д�֤�ţ���������֤�Ų���,�˲�ʧ��";
                         document.theform.state.value="3";
				 ����    break
			����   case 3:
                         sl_alert("�����޴˺ţ�,�˲�ʧ��");
                         document.theform.summary.value="�����޴˺ţ�,�˲�ʧ��";
                         document.theform.state.value="4";
                         break	
				   case 4:
				����     sl_alert("�˲鱻�ܾ�");
				����     break	
				   case 200:
				 ����    sl_alert("��֤�豸�����ߣ�������֤�豸��ŵȲ�����Ȩ��Χ");
				 ����    break
                   case 202:
				 ����    sl_alert("SYSINI.INI�ļ�����");
				 ����    break
			����   case 203:
			����         sl_alert("SYSINI.INI�ļ�����");
                         break
                   case 205:
				����     sl_alert("�˲鳬ʱ�����");
				����     break
                   case 206:
				 ����    sl_alert("�˲鳬ʱ�����");
				 ����    break
			����   case 207:
			����         sl_alert("�˲鷵����Ϣ�ǿյ�");
                         break
			����   default:
                        //���n������1Ҳ����2����ִ�д˴���
                        sl_alert("�˲�ʧ��,���ʵ��֤����Ƿ�װ��ȷ");
	       }
		} catch (e) {
			return false;
		}  
}
/*ȡ��*/
function CancelAction(){
	window.parent.document.getElementById("closeImg").click();	
}
/*ȷ��*/
function SaveAction(){
	var name = document.getElementsByName('theform')[0].name.value;
    if(name!=""){
        document.theform.submit();
	}else{
        sl_alert("�ͻ�����Ϊ��");
    }		 
}
</script>
</HEAD>
<BODY class="BODY body-nox" onload="FnTcIdCheck();">
<%@ include file="/includes/waiting.inc"%>
<OBJECT
	  name=TcCardOcr
	  classid="clsid:6EAFC189-D17E-4E3F-905C-D5A2BC4E055A"
	  codebase="/includes/card/TcIdCard5.ocx"
	  width=0
	  height=0
	  align=center
	  hspace=0
	  vspace=0
>
</OBJECT>
<form name="theform" method="get" action="card_discern4_exe.jsp" action="card_discern4_exe.jsp">
<div>
	<div align="left" class="page-title">
		<font color="#215dc6"><b>֤���˲�</b></font>
	</div>
</div>
<div style="margin-left:10px;margin-top:5px;">
	<div style="float:left;width:49%;" >
		<fieldset style="height:362px;">
			<legend><b style="color: blue;">֤����Ϣ</b></legend>
			<table>
				<tr>
				<td align="right">֤������:</td>
				<td >
					<input maxlength="100" readonly="readonly"  name="input_value1" size="27" style="display: block;" value="<%=Utility.trimNull(cust_map.get("CARD_TYPE_NAME"))%>">
				</td>
				<td align="right">֤������:</td>
				<td >
					<input maxlength="100" name="card_id" id="card_id" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("CARD_ID"))%>">
				</td>
				</tr>
	           <tr>
				<td align="right">����:</td>
				<td >
                    <input maxlength="100" name="name" id = "name" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("CUST_NAME"))%>">
				</td>
				<td align="right">�Ա�:</td>
				<td >
					<input maxlength="100" name="sex" id = "sex" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("SEX_NAME"))%>">
					<input maxlength="100" readonly="readonly" class="edline" name="sex1" id = "sex1" size="27" style="display: none;">
				</td>
				</tr>
                 <tr>
				<td align="right">����:</td>
				<td >
                    <input maxlength="100" name="birth" id = "birth" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("BIRTHDAY"))%>">
				</td>
				<td align="right">����:</td>
				<td>
					<input maxlength="100" name="nation" id="nation" size="27" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("NATION"))%>">
				</td>
				</tr>
 	<tr>
       <td align="right">סַ:</td>
				<td colspan="3">
					<input maxlength="100" name="address" id="address" size="70" onkeydown="javascript:nextKeyPress(this);" value="<%=Utility.trimNull(cust_map.get("CARD_ADDRESS"))%>">
				</td>
	</tr>

        <tr><td align="right">&nbsp;</td></tr>
            <tr><td align="right">�˲���:</td><td colspan="3"><input maxlength="100" readonly="readonly" class="edline" name="summary" id="summary" size="70" value=""></td></tr>
			</table>
            <INPUT TYPE="hidden" NAME="state" id="state" value="">
		</fieldset>
	</div>
	<div style="float:right;width:49%;" class="direct-panel">
		<fieldset style="height:362px;">
			<legend><b style="color: blue;">������֤����</b></legend>
		   <img border="0" id="image" name="image" src="D:\\ResultCheckImage.jpg" align="absmiddle" height="345" width="100%"></img>
		</fieldset>
	</div>
</div>
<br>
<div align="right" style="margin-to[:5px">
	<button type="button"  class="xpbutton3" accessKey=n id="btnNext" name="btnNext" title="ȷ��" onclick="javascript: SaveAction();">ȷ��</button>
	&nbsp;&nbsp;&nbsp;	
	<button type="button"  class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:CancelAction();">�ر� (<u>C</u>)</button>
</div>
</form>
<%@ include file="/includes/foot.inc"%>
</BODY>
</HTML>