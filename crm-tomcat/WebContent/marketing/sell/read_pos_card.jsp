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
<TITLE>POS机刷卡</TITLE>
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
		sl_alert("请指定POS客户端程序的位置！");
		getElement("name:client_file").focus();
		return;
	}

	var to_pay_money = parseFloat(getElement("to_pay_money"+btn.id).value);
	//alert(to_pay_money+" "+isNaN(to_pay_money)); // NaN
	if (isNaN(to_pay_money) || to_pay_money<=0 || to_pay_money>unpaid_money) {
		sl_alert(isNaN(to_pay_money)||to_pay_money<=0? "请填写有效的刷卡金额！": "刷卡金额超过未付认购金额！");
		getElement("to_pay_money"+btn.id).focus();
		getElement("to_pay_money"+btn.id).select();
		return;
	}

	btn.disabled = true;

	var outfile = exefile.substring(0, exefile.lastIndexOf("\\")+1)+"posret.txt"; // 每次执行的输出会覆盖掉原有的内容
	var fso = new ActiveXObject("Scripting.FileSystemObject");
	if (fso.fileExists(outfile)) fso.DeleteFile(outfile);

	var disk = outfile.substring(0,2);
	var folder = exefile.substring(0, exefile.lastIndexOf("\\")+1);

	//var cmd = "\""+exefile+"\" 1 1 \""+outfile+"\" 5 "+to_pay_money;
	var cmd = "cmd /c "+disk
				+" && cd \""+folder+"\""
				+" && \""+exefile+"\" 1 1 posret.txt 88 "+to_pay_money*100;

	//alert(cmd);
	var v1= new ActiveXObject("Wscript.Shell").run(cmd); // 并发执行	
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
							sl_alert("POS客户端程序输出的文件有误！");
						} else {
							line = line.substring(0, idx);
							var retval = line.substring(0,1);
							var retval_dec ="";
							
							retval_dec= retval=="0"? "刷卡成功！"
								: retval=="1"? "卡错误...！"
								: retval=="2"? "银行错误...！"
								: retval=="3"? "密码错误...！"
								: retval=="4"? "通讯错误...！"
								: retval=="5"? "交易错误...！"
								: retval=="6"? "金额错误...！"
								: retval=="7"? "请与银行联系....！"
								: retval=="8"? "POS未刷卡...！"
								: retval=="9"? "交易失败...！"
								: retval=="A"? "交易取消...！"
								: retval=="B"? "POS交易状态不对...！"
								: retval=="C"? "银联通讯超时...！"
								: retval=="D"? "打开通讯端口失败...！"
								: retval=="E"? "与POS通讯失败...！"
								: "未知错误！"
							sl_alert(retval_dec);

							if (retval=="0") {//刷卡成功
								total_paid_money += to_pay_money;        
								unpaid_money -= to_pay_money;

								getElement("total_paid_money").innerText = commafy(total_paid_money);
								cardno = line.substring(1,line.length); //

								getElement("to_pay_money"+btn.id).value = commafy(to_pay_money);
								getElement("to_pay_money"+btn.id).readOnly = true;
								getElement("to_pay_money"+btn.id).className = "edline";
								getElement("cardno"+btn.id).innerText = "刷卡卡号: "+cardno;

								utilityService.savePosCardRes(<%=input_operatorCode%>, to_pay_money, <%=product_id%>, <%=cust_id%>, cardno, '', '', retval, retval_dec,dwrCallbackInfo);//记录刷卡结果到数据库

								btn.style.display = "none";
								getElement("addBtn").style.display = "";

								if (unpaid_money == 0) {
									sl_alert("累计刷卡金额已达到认购金额！");
									getElement("closeBtn").focus();
								} else {								
									getElement("addBtn").focus();			
								}
							}else{//刷卡失败时，也记录下刷卡情况
								cardno=line.substring(1,line.length);
								utilityService.savePosCardRes(<%=input_operatorCode%>, to_pay_money, <%=product_id%>, <%=cust_id%>, cardno, '', '', retval, retval_dec,dwrCallbackInfo);//记录刷卡结果到数据库
							}
						}		
						btn.disabled = false;
					}, 3*1000); // 为防止写与读的冲突，再等3秒，确保写文件的进程已结束
			}

		}, 1*1000);
}
function dwrCallbackInfo(data){//alert("save OK！");
}

function oncemore(btn) {
	if (unpaid_money == 0) {
		sl_alert("累计刷卡金额已达到认购金额！");
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
	td.width = "70%"; // "*" 参数无效！
	td.align = "left";
	td.innerHTML = '<input type="text" id="to_pay_money'+idx+'" value="'+unpaid_money+'" size="14" onkeyup="javascript:showCnMoney(this.value,money_up2)"/>(元)'
			+ ' &nbsp;<button type="button"  class="xpbutton2" id="'+idx+'" onclick="javascript:read(this);">刷卡</button>'
			+ ' &nbsp;<span id="cardno'+idx+'"></span>';
	showCnMoney(getElement("to_pay_money"+idx).value,money_up2);//显示大写金额
}
//数字格式转换成千分位
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
			sl_alert("POS客户端程序在默认位置"+getElement("def_client_file").innerText+"不存在"
					 +"，请手动指定！");
			getElement("def_client_file").style.display = "none";
			getElement("name:client_file").style.display = "";
			getElement("name:client_file").focus();
		}
		showCnMoney(getElement("to_pay_money0").value,money_up2);//显示大写金额
	};

window.onunload = function() {
		window.returnValue 
			= {"total_paid_money":total_paid_money, "card_no":cardno};
	};
</script>
</HEAD>
<BODY>
<form name="theform">
<DIV align="center" style="font-size:20px">认购总金额：<%=rg_money %>元</DIV>
<TABLE id="mainTable" cellSpacing=0 cellPadding=0 width="100%" border=0>
	<tr>
		<td width="107px" align="right">POS客户端程序:&nbsp;&nbsp;</td>
		<td width="*" align="left">
			<span id="def_client_file">D:\POS\PCXT.EXE</span>
			<INPUT type="file" name="client_file" size="50" style="display:none"/> <!-- 不允许自己设置 这个是IE的安全策略。 -->
		<%--if (client_file.equals("")) { %>
			<INPUT type="file" name="client_file" size="50"/> <!-- 不允许自己设置 这个是IE的安全策略。 -->
		<% } else { %>
			<INPUT type="text" name="client_file" size="70" value="<%=client_file%>" readonly class="edline"/> 
			&nbsp;<button type="button"  class="xpbutton2" onclick="javascript:change();">更改</button>
		<% } --%>
		</td>
	</tr>
	<tr>
		<td width="107px" align="right">
			刷卡金额:&nbsp;&nbsp;
			<br/><button type="button"  id="addBtn" onclick="javascript:oncemore(this);" style="display:none">+</button>
		</td>
		<td width="*" align="left">
			<INPUT type="text" id="to_pay_money0" value="<%=rg_money%>" size="14" onkeyup="javascript:showCnMoney(this.value,money_up2)"/>(元)
			&nbsp;<button type="button"  class="xpbutton2" id="0" onclick="javascript:read(this);">刷卡</button>
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
			累计已刷金额：<span id="total_paid_money">0.0</span>元
		</td>
	</tr>
	<tr>
		<td align="right">		
			<button type="button"  class="xpbutton2" id="closeBtn" onclick="javascript:window.close();">关闭</button>
		</td>
	</tr>
</TABLE>
</form>
</BODY>
</HTML>