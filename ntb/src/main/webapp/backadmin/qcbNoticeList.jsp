<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<script type="text/template" id="queboxTpl">
    <tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        <td title="">{{:title}}</td>
        <td title="">{{:starttimeCN}}</td>
		<td title="">{{:endtimeCN}}</td>
        <td title="">
            {{if status == "normal"}}正常{{/if}}
            {{if status == "disabled"}}停用{{/if}}
        </td>
        <td title="">{{:creatorCN}}</td>
        <td>
            <a href="./qcbNoticeEdit.jsp?uuid={{:uuid}}">修改</a>
        </td>
    </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00800086" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">企财宝网站管理</a><span>></span><a class="current">企财宝通知管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="screening">
                    <div class="item filter">
                        <label>状态：</label>
                        <div>
                            <a id="all" class="statusLight">全部<span id="allCount">(0)</span></a>
                            <a id="normal">正常<span id="normalCount">(0)</span></a>
                            <a id="disabled">停用<span id="disabledCount">(0)</span></a>
                        </div>
                    </div>
                    <a href="./qcbNoticeAdd.jsp" class="add_account">+&ensp;添加通知</a>
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th>标题</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>状态</th>
                        <th>创建人</th>
                        <th>操作</th>
                    </tr>
                    <tbody id="queboxCnt"></tbody>
                </table>

                <div id="pageTool"></div>
                <%--//--%>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="./js/getHtmlDocName.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/qcbNoticeList.js"></script>
</body>
</html>
