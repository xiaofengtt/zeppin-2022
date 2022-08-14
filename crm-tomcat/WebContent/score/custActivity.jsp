<%@ page contentType="text/html; charset=GBK" import="enfo.crm.tools.LocalUtilis,enfo.crm.tools.Utility,enfo.crm.score.*,enfo.crm.dao.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/tld/fmt.tld" prefix="fmt" %>
<%@ include file="/includes/operator.inc" %>
<%@ include file="/includes/parameter.inc" %>
<%

	CustActivityVo vo = new CustActivityVo();
	CustActivityBean custActivityBean = new CustActivityBean();

	String qCustName = Utility.trimNull(request.getParameter("qCustName"));
	Integer cust_id = Utility.parseInt(request.getParameter("cust_id"),new Integer(0));
	Integer activity_id = new Integer(0);
	Integer score_date = new Integer(0);
	Integer score = new Integer(0);
	Integer input_man = new Integer(0);
	String summary = "";
	String activity_name = "";
	
	String[] s = request.getParameterValues("activity_id");
	//若选中不为空
	if (s != null){
		for(int i = 0;i < s.length; i++){
			if(s[i] != "0"){
				vo.setCust_id(cust_id);
				vo.setActivity_id(new Integer(s[i]));
				custActivityBean.delCustActivity(vo);
			}
		}
	}

	IPageList pageList = null;
	Map map = null;
	vo.setCust_id(cust_id);
	pageList = custActivityBean.queryCustActivity(vo,Utility.parseInt(sPage, 1),Utility.parseInt(sPagesize, 8));
	Iterator it =  pageList.getRsList()!=null? pageList.getRsList().iterator(): new ArrayList().iterator();

%>
<html>
	<head>
		<meta http-equiv=Content-Type content="text/html; charset=gbk">
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">
		
		<link href="<%=request.getContextPath()%>/includes/default.css" type="text/css" rel="stylesheet"/>
		<link href="<%=request.getContextPath()%>/includes/queryEx/css/queryEx.css" type="text/css" rel="stylesheet"/>

		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/queryEx/scripts/queryEx.js"></script>
		<script language="vbscript" src="<%=request.getContextPath()%>/includes/default.vbs"></script>
		<script type="text/javascript" src="<%=request.getContextPath()%>/includes/default_<%=languageType%>.js"></script>
		<script language="javascript" src="<%=request.getContextPath()%>/includes/calendar.js"></script>
		<script type="text/javascript">

			function refreshPage() {
				disableAllBtn(true);
				location.search = 'custActivity.jsp.jsp?page=1&pagesize=' + document.theform.pagesize.value;
			}

			/*删除方法*/
			function DelAction(){	
				if(confirmRemove(document.theform.activity_id)){
					disableAllBtn(true);
					document.theform.submit();
				}
			}

			function goback(){
				disableAllBtn(true);
				location = "custScore.jsp?qCustName=" + document.theform.qCustName.value;
			}
		</script>
	</head>

	<body class="body">
		<form id="theform" name="theform" method="get" action="custActivity.jsp">
			<input type="hidden" name="cust_id" value="<%=cust_id %>"/>
			<input type="hidden" name="qCustName" id="qCustName" value="<%=qCustName %>">
			<div>
				<div align="left">
					<img border="0" src="<%=request.getContextPath()%>/images/member.gif" width="32" height="28">
					<font color="#215dc6"><b>基本管理>>活动奖励</b></font>
				</div>
				<div align="right">
					<button class="xpbutton3" accessKey=d id="btnAppend" name="btnDel" title="删除" onclick="javascript:DelAction();">删除 (<u>D</u>)</button>
					&nbsp;&nbsp;&nbsp;
					<button class="xpbutton3" accessKey=b id="backButton" name="backButton" onclick="javascript:goback();">返回 (<u>B</u>)</button>
				</div>
				<hr noshade color="#808080" size="1" width="100%">
			</div>

			<div>
				<table id="table3" border="0" cellspacing="1" cellpadding="2" class="tablelinecolor" width="100%" style="margin-top:5px">
					<tr class="trh">
						<td align="center" width="100">
							<input type="checkbox" name="btnCheckbox" class="selectAllBox" onclick="javascript:selectAllBox(document.theform.activity_id,this);">
							序号
						</td><!--编号-->
						<td align="center" width="*">活动名称</td>
						<td align="center" width="*">参加活动日期</td>
						<td align="center" width="*">活动积分</td>
						<td align="center" width="*">说明</td>
					</tr>
					<%
						int iCount = 0;
						int iCurrent = 0;
						while (it.hasNext()) {
							map = (Map) it.next();
							iCurrent++;
							iCount++;
							activity_id = Utility.parseInt(Utility.trimNull(map.get("ACTIVITY_ID")),new Integer(0));
							score_date = Utility.parseInt(Utility.trimNull(map.get("SCORE_DATE")),new Integer(0));
							score = Utility.parseInt(Utility.trimNull(map.get("SCORE")),new Integer(0));
							input_man = Utility.parseInt(Utility.trimNull(map.get("INPUT_MAN")),new Integer(0));
							summary =  Utility.trimNull(map.get("SUMMARY"));
							activity_name =  Utility.trimNull(map.get("ACTIVITY_NAME"));
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh">
							<table border="0" width="100%" cellspacing="0" cellpadding="0">
								<tr>
									<td width="10%">
										<input type="checkbox" name="activity_id" value="<%=activity_id%>" class="flatcheckbox">
									</td>
									<td width="90%" align="center"><%=iCurrent%></td>
								</tr>
							</table>
						</td>
						<td align="center"><%=activity_name%></td>
						<td align="center"><%=score_date%></td>
						<td align="center"><%=score%></td>
						<td align="center"><%=summary%></td>
					</tr>
					<%
						}
						for(int i=0;i<(Utility.parseInt(sPagesize,8)-iCount);i++){
					%>
					<tr class="tr<%=iCurrent%2%>">
						<td class="tdh" align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
						<td align="center"></td>
					</tr>
					<%}%>
					<tr class="trbottom">
						<!--合计--><!--项-->
                        <td class="tdh" align="left" colspan="5"><b>&nbsp;<%=LocalUtilis.language("message.total",clientLocale)%>&nbsp;<%=(pageList!=null)?pageList.getTotalSize():0%>&nbsp;<%=LocalUtilis.language("message.entries",clientLocale)%> &nbsp;</b></td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr valign="top">
						<td><%=pageList.getPageLink(sUrl,clientLocale)%></td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>