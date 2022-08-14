<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css" />
<link rel="stylesheet" href="./css/paging.css" />
<link rel="stylesheet" href="./css/page.css" />
<link rel="stylesheet" href="./css/table.css" />
<link rel="stylesheet" href="./css/financeList.css" />
<!--[if lte IE 8]>
	<script src="js/html5shiv.js"></script>
	<script src="js/respond.js"></script>
<![endif]-->
	<style>
		::-webkit-input-placeholder {
			/* WebKit browsers */
			color: #CCCCCC;
		}

		:-moz-placeholder {
			/* Mozilla Firefox 4 to 18 */
			color: #CCCCCC;
		}

		::-moz-placeholder {
			/* Mozilla Firefox 19+ */
			color: #CCCCCC;
		}

		:-ms-input-placeholder {
			/* Internet Explorer 10+ */
			color: #CCCCCC;
		}
	</style>
</head>
<body>
	<input id="pageId" type="hidden" value="e8d666c3-ff26-11e7-ac6d-fcaa14314cbe"/>
	<jsp:include page="header.jsp"/>
	<main>
		<jsp:include page="contentLeft.jsp"/>
		<!-- 打印 -->

		<div class="main-right">
			<div class="main-right-body box loadingOver">
				<form:form id="print" action="../rest/qcb/companyAccountHistory/export" method="post">
				<input name="deadline" type="hidden" value="2" />
				<input name="type" type="hidden" value="" />
				<h3>账务明细</h3>
				<div class="termbar">
					<a class="color-orange" deadline="2">最近7天</a>
					<a deadline="3">最近30天</a>
					<a deadline="4">最近90天</a>
					<a class="custom">自定义</a>
					<div class="date-box" style="display:none;">
						<input type="text" id="startTime" name="starttime" placeholder="选择起始日期" readonly/> ~
						<input type="text" id="endTime" name="endtime" placeholder="选择结束日期" readonly/>
					</div>
				</div>
				<div class="type-bar">
					<a class="type-light" type="">全部</a>
					<a type="income">充值</a>
					<a type="withdraw">提现</a>
					<a type="payroll">福利发放</a>
					<a type="expend">支出</a>
					<a type="buy">投资</a>
					<a type="redeem">赎回/收益</a>
					<div class="type-bar-right">
						<button id="down-load" type="submit" onkeydown="if(event.keyCode==13){return false;}"></button>
						<a class="nb-l" id="stamp"></a>
						<div class="clear"></div>
					</div>
				</div>

				<table class="table" cellspacing="0" cellpadding="0" id="table">

				</table>
				<div id="pageTool">

				</div>
				</form:form>
			</div>
			
			<!-- loading -->
			<div class="loadingDiv"></div>
			
		</div>

	</main>
	<jsp:include page="footer.jsp"/>
	<script src="./js/url.min.js"></script>
	<script src="./js/query.js"></script>
	<script src="./js/paging.js"></script>
	<script src="./laydate-v1.1/laydate/laydate.js"></script>
	<script src="./js/financeList.js?v=<%=Math.random()%>"></script>
</body>
</html>
