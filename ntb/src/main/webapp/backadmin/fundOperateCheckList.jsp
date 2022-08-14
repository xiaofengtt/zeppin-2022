<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/auditingItem.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00900093" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">活期理财管理</a><span>></span><a class="current">待审批事项</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="auditing_item">
                    <a href="./fundListAuditingCheck.jsp">1、投资产品变动申请</a>
                    <span><i id="fund"></i>项</span>
                </div>

                <div class="auditing_item">
                    <a href="./fundPublishAuditingCheck.jsp">2、发布产品变动申请</a>
                    <span><i id="fundPublish"></i>项</span>
                </div>
            </div>
        </div>
    </div>
        <script src="./js/fundOperateCheckList.js"></script>
</body>
</html>
