<%@ page contentType="text/html; charset=GBK" import="enfo.crm.web.*,enfo.crm.customer.*,enfo.crm.intrust.*,enfo.crm.tools.*,enfo.crm.dao.*,enfo.crm.system.*,enfo.crm.vo.*,java.math.*,java.util.*,java.io.*" %>
<%@ include file="/includes/operator.inc" %>
<%
	//String client_file = Utility.trimNull(request.getParameter("client_file"));
	BigDecimal rg_money = Utility.parseDecimal(request.getParameter("rg_money"), new BigDecimal(0.0));
	Integer product_id =Utility.parseInt(request.getParameter("product_id"), new Integer(0));
	Integer cust_id =Utility.parseInt(request.getParameter("cust_id"), new Integer(0));
 %>
<HTML>
<HEAD>
<TITLE>POS��ˢ��</TITLE>
<meta http-equiv="X-UA-Compatible" content="IE=7" >
<META http-equiv=Content-Type content="text/html; charset=gbk">
<LINK href="/includes/default.css" type=text/css rel=stylesheet>
<LINK href="/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Expires" content="0">
<SCRIPT LANGUAGE="vbscript" SRC="/includes/default.vbs"></SCRIPT>
<SCRIPT LANGUAGE="javascript" SRC="/includes/default.js"></SCRIPT>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/interface/utilityService.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=request.getContextPath()%>/dwr/util.js'></script>
<script type="text/javascript">
var rg_money = <%=rg_money%>;
var unpaid_money = rg_money;
var total_paid_money = 0.0;
var cardno = "";

function getElement(elem) {
    try {
        return elem.indexOf("name:")==0? 
                    document.getElementsByName(elem.substring("name:".length, elem.length))[0]
                : elem.indexOf("id:")==0? 
                    document.getElementById(elem.substring("id:".length, elem.length))
                :   document.getElementById(elem);
    } catch (err) {
        return null;
    }
}

function read(btn) {
	//alert("["+getElement("name:client_file").value+"]");
	var exefile = getElement("def_client_file").style.display!="none"?
						getElement("def_client_file").innerText
						: getElement("name:client_file").value;
	if (exefile == "") {
		sl_alert("��ָ��POS�ͻ��˳����λ�ã�");
		getElement("name:client_file").focus();
		return;
	}

	var to_pay_money = parseFloat(getElement("to_pay_money"+btn.id).value);
	//alert(to_pay_money+" "+isNaN(to_pay_money)); // NaN
	if (isNaN(to_pay_money) || to_pay_money<=0 || to_pay_money>unpaid_money) {
		sl_alert(isNaN(to_pay_money)||to_pay_money<=0? "����д��Ч��ˢ����": "ˢ������δ���Ϲ���");
		getElement("to_pay_money"+btn.id).focus();
		getElement("to_pay_money"+btn.id).select();
		return;
	}

	btn.disabled = true;

	var outfile = exefile.substring(0, exefile.lastIndexOf("\\")+1)+"posret.txt"; // ÿ��ִ�е�����Ḳ�ǵ�ԭ�е�����
	var fso = new ActiveXObject("Scripting.FileSystemObject");
	if (fso.fileExists(outfile)) fso.DeleteFile(outfile);

	var disk = outfile.substring(0,2);
	var folder = exefile.substring(0, exefile.lastIndexOf("\\")+1);

	//var cmd = "\""+exefile+"\" 1 1 \""+outfile+"\" 5 "+to_pay_money;
	var cmd = "cmd /c "+disk
				+" && cd \""+folder+"\""
				+" && \""+exefile+"\" 1 1 posret.txt 88 "+to_pay_money*100;

	//alert(cmd);
	var v1= new ActiveXObject("Wscript.Shell").run(cmd); // ����ִ��	
	setTimeout(
		function() {
			if (! fso.fileExists(outfile)) {
				//alert(arguments.callee);
				setTimeout(arguments.callee, 1*1000); //

			} else {
				setTimeout(
					function() {
						var fp = fso.OpenTextFile(outfile, 1, false);
			 			var line = fp.ReadLine();
						fp.close();
						fso.DeleteFile(outfile); //
			
						//alert("["+line+"]");
						var idx = line.indexOf("*");
						if (idx <= 0) {
							sl_alert("POS�ͻ��˳���������ļ�����");
						} else {
							line = line.substring(0, idx);
							var retval = line.substring(0,1);
							var retval_dec ="";
							
							retval_dec= retval=="0"? "ˢ���ɹ���"
								: retval=="1"? "������...��"
								: retval=="2"? "���д���...��"
								: retval=="3"? "�������...��"
								: retval=="4"? "ͨѶ����...��"
								: retval=="5"? "���״���...��"
								: retval=="6"? "������...��"
								: retval=="7"? "����������ϵ....��"
								: retval=="8"? "POSδˢ��...��"
								: retval=="9"? "����ʧ��...��"
								: retval=="A"? "����ȡ��...��"
								: retval=="B"? "POS����״̬����...��"
								: retval=="C"? "����ͨѶ��ʱ...��"
								: retval=="D"? "��ͨѶ�˿�ʧ��...��"
								: retval=="E"? "��POSͨѶʧ��...��"
								: "δ֪����"
							sl_alert(retval_dec);

							if (retval=="0") {//ˢ���ɹ�
								total_paid_money += to_pay_money;        
								unpaid_money -= to_pay_money;

								getElement("total_paid_money").innerText = commafy(total_paid_money);
								cardno = line.substring(1,line.length); //

								getElement("to_pay_money"+btn.id).value = commafy(to_pay_money);
								getElement("to_pay_money"+btn.id).readOnly = true;
								getElement("to_pay_money"+btn.id).className = "edline";
								getElement("cardno"+btn.id).innerText = "ˢ������: "+cardno;

								utilityService.savePosCardRes(<%=input_operatorCode%>, to_pay_money, <%=product_id%>, <%=cust_id%>, cardno, '', '', retval, retval_dec,dwrCallbackInfo);//��¼ˢ����������ݿ�

								btn.style.display = "none";
								getElement("addBtn").style.display = "";

								if (unpaid_money == 0) {
									sl_alert("�ۼ�ˢ������Ѵﵽ�Ϲ���");
									getElement("closeBtn").focus();
								} else {								
									getElement("addBtn").focus();			
								}
							}else{//ˢ��ʧ��ʱ��Ҳ��¼��ˢ�����
								cardno=line.substring(1,line.length);
								utilityService.savePosCardRes(<%=input_operatorCode%>, to_pay_money, <%=product_id%>, <%=cust_id%>, cardno, '', '', retval, retval_dec,dwrCallbackInfo);//��¼ˢ����������ݿ�
							}
						}		
						btn.disabled = false;
					}, 3*1000); // Ϊ��ֹд����ĳ�ͻ���ٵ�3�룬ȷ��д�ļ��Ľ����ѽ���
			}

		}, 1*1000);
}
function dwrCallbackInfo(data){//alert("save OK��");
}

function oncemore(btn) {
	if (unpaid_money == 0) {
		sl_alert("�ۼ�ˢ������Ѵﵽ�Ϲ���");
		getElement("closeBtn").focus();
		return;
	}

	btn.style.display = "none"; //

	var table = getElement("mainTable");
	var idx = table.rows.length - 1;
	var tr = table.insertRow(table.rows.length);
	var td = tr.insertCell(0);
	td.width = "107px";

	td = tr.insertCell(1);
	td.width = "70%"; // "*" ������Ч��
	td.align = "left";
	td.innerHTML = '<input type="text" id="to_pay_money'+idx+'" value="'+unpaid_money+'" size="14" onkeyup="javascript:showCnMoney(this.value,money_up2)"/>(Ԫ)'
			+ ' &nbsp;<button type="button"  class="xpbutton2" id="'+idx+'" onclick="javascript:read(this);">ˢ��</button>'
			+ ' &nbsp;<span id="cardno'+idx+'"></span>';
	showCnMoney(getElement("to_pay_money"+idx).value,money_up2);//��ʾ��д���
}
//���ָ�ʽת����ǧ��λ
function commafy(num){
   if(isNaN(num)){
      return num;
   }
   num = num+"";
   num=num.replace(" ","");
   if(/^.*\..*$/.test(num)){
      var pointIndex =num.lastIndexOf(".");
      var intPart = num.substring(0,pointIndex);
      var pointPart =num.substring(pointIndex+1,num.length);
      intPart = intPart +"";
      var re =/(-?\d+)(\d{3})/
      while(re.test(intPart)){
          intPart =intPart.replace(re,"$1,$2")
      }
      num = intPart+"."+pointPart;
   }else{
      num = num +"";
      var re =/(-?\d+)(\d{3})/
      while(re.test(num)){
         num =num.replace(re,"$1,$2")
      }
   }
    return num;
}


window.onload = function() {
		var fso = new ActiveXObject("Scripting.FileSystemObject");
		if (! fso.fileExists(getElement("def_client_file").innerText)) {
			sl_alert("POS�ͻ��˳�����Ĭ��λ��"+getElement("def_client_file").innerText+"������"
					 +"�����ֶ�ָ����");
			getElement("def_client_file").style.display = "none";
			getElement("name:client_file").style.display = "";
			getElement("name:client_file").focus();
		}
		showCnMoney(getElement("to_pay_money0").value,money_up2);//��ʾ��д���
	};

window.onunload = function() {
		window.returnValue 
			= {"total_paid_money":total_paid_money, "card_no":cardno};
	};
</script>
</HEAD>
<BODY>
<form name="theform">
<DIV align="center" style="font-size:20px">�Ϲ��ܽ�<%=rg_money %>Ԫ</DIV>
<TABLE id="mainTable" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr>
		<td width="107px" align="right">POS�ͻ��˳���:&nbsp;&nbsp;</td>
		<td width="*" align="left">
			<span id="def_client_file">D:\POS\PCXT.EXE</span>
			<INPUT type="file" name="client_file" size="50" style="display:none"/> <!-- �������Լ����� �����IE�İ�ȫ���ԡ� -->
		<%--if (client_file.equals("")) { %>
			<INPUT type="file" name="client_file" size="50"/> <!-- �������Լ����� �����IE�İ�ȫ���ԡ� -->
		<% } else { %>
			<INPUT type="text" name="client_file" size="70" value="<%=client_file%>" readonly class="edline"/> 
			&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:change();">����</button>
		<% } --%>
		</td>
	</tr>
	<tr>
		<td width="107px" align="right">
			ˢ�����:&nbsp;&nbsp;
			<br/><button type="button"  id="addBtn" onclick="javascript:oncemore(this);" style="display:none">+</button>
		</td>
		<td width="*" align="left">
			<INPUT type="text" id="to_pay_money0" value="<%=rg_money%>" size="14" onkeyup="javascript:showCnMoney(this.value,money_up2)"/>(Ԫ)
			&nbsp;<button type="button"  class="xpbutton2" id="0" onclick="javascript:read(this);">ˢ��</button>
			&nbsp;<span id="cardno0"></span>
		</td>
	</tr>
</table>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr id="money_up">
		<td width="107px">&nbsp;</td>
		<td width="*" align="left">
			<span id="money_up2" style="font-size:20px;color:#0000ff;"></span>
		</td>
	</tr>
</TABLE>
<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr>
		<td align="left">
			&nbsp;&nbsp;&nbsp;&nbsp;
			�ۼ���ˢ��<span id="total_paid_money">0.0</span>Ԫ
		</td>
	</tr>
	<tr>
		<td align="right">		
			<button type="button"  class="xpbutton2" id="closeBtn" onclick="javascript:window.close();">�ر�</button>
		</td>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>