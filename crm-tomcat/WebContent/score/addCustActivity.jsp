<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,java.util.*,java.text.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt"%>
<%@ include file="/includes/operator.inc"%>
<%@ include file="/includes/parameter.inc"%>
<%

	CustActivityVo vo = new CustActivityVo();
	CustActivityBean custActivityBean = new CustActivityBean();
	//页面变量
	String custId = Utility.trimNull(request.getParameter("custId"));
	System.out.println("custId="+custId);
	Integer activity_id = Utility.parseInt(request.getParameter("activity_id"),new Integer(0));
	String summary = Utility.trimNull(request.getParameter("summary"));

	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
	Integer current_date = Utility.parseInt(df.format(new Date()),new Integer(0));
	System.out.println("current_date="+current_date);
	boolean bSuccess = false;
	if(request.getMethod().equals("POST")){
		String[] cust_id = custId.split("\\|"); 
		for(int i = 0; i<cust_id.length; i++ ) {
			System.out.println(cust_id[i]);
			vo.setCust_id(new Integer(cust_id[i]));
			vo.setActivity_id(activity_id);
			vo.setInput_man(input_operatorCode);
			vo.setSummary(summary);
			custActivityBean.addCustActivity(vo);
		}
		bSuccess = true;
	}
%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		<base TARGET="_self">
		<title>增加活动</title>

		<link href="<%=request.getContextPath()%>/includes/default.css" type=text/css rel=stylesheet>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type=text/css rel=stylesheet>

		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>

		<script language=javascript>
			window.onload = function(){
				var v_bSuccess = document.getElementById("bSuccess").value;
				
				if(v_bSuccess=="true"){		
					window.returnValue = 1;
					window.close();
				}
			}

			function validateForm(form){
				if(document.theform.activity_id.value==""){
					alert("请选择活动");
					return false;
				}
				return true;
			}
		
		
		</script>
	</head>

	<body class="body">
		<form name="theform" method="post" action="addCustActivity.jsp" onsubmit="javascript:return validateForm(this);">
			<input type="hidden" name="bSuccess" id="bSuccess" value="<%=bSuccess %>"/>
			<input type="hidden" name="custId" id="custId" value="<%=custId %>"/>
			<table border="0" width="100%" cellspacing="0" cellpadding="0">
				<tr><!--增加活动-->
					<td><img border="0" src="<%=request.getContextPath()%>/images/ico_area.gif" width="32" height="28"><font color="#215dc6"><b>增加活动</b></font></td>
				</tr>
				<tr>
					<td>
						<hr noshade color="#808080" size="1">
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="right" width="150px">积分活动 :&nbsp;&nbsp;</td>
					<td align="left">
						<select style="width: 280px;" name="activity_id" onkeydown="javascript:nextKeyPress(this)">
							<%=Argument.getActivity(current_date, 1,input_operatorCode,activity_id+"")%>
						</select>
					</td>
				</tr>
				<tr>
					<td align="right" width="150px">说明 :&nbsp;&nbsp;</td>
					<td align="left">
						<textarea rows="5" style="width:200px;" name="summary"><%=summary %></textarea>
					</td>
				</tr>
			</table>
			<table border="0" width="100%">
				<tr>
					<td align="center">
						<button class="xpbutton3" accessKey=s id="btnSave" name="btnSave" onclick="javascript:if(document.theform.onsubmit()){document.theform.btnSave.disabled='true'; document.theform.submit();}"><%=LocalUtilis.language("message.save",clientLocale)%> (<u>S</u>)</button>
						&nbsp;&nbsp;<!--保存-->
						<button class="xpbutton3" accessKey=c id="btnCancel" name="btnCancel" onclick="javascript:window.returnValue=null;window.close();"><%=LocalUtilis.language("message.cancel",clientLocale)%> (<u>C</u>)</button>
						&nbsp;&nbsp;<!--取消-->
					</td>
				</tr>
			</table>
		</form>
	</body>
</html>