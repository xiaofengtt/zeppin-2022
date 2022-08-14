<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>牛头帮－后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/accountControllerList.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/rechargeDrawStatus.css">
<script type="text/template" id="queboxTpl">
    <tr>
        <td>
            <a href="./advertEdit.jsp?uuid={{:uuid}}">{{:title}}</a>
        </td>
        <td>
            <a href="../{{:pictureUrl}}" target="_blank">
                <img src="../{{:pictureUrl}}" alt="" style="height:40px;width:100px;">
            </a>
        </td>
        <td>
            {{if status == "normal"}}
                <span class="color_green">有效</span>
            {{else}}
                <span class="color_red">无效</span>
            {{/if}}
        </td>
        <td>{{:creatorRealname}}</td>
        <td>{{:createDate}}</td>
        <td>
            {{if status == "normal"}}
                <a href="./advertEdit.jsp?uuid={{:uuid}}">编辑</a>
            {{else}}
                <a class="color_gray" href="./advertEdit.jsp?uuid={{:uuid}}">查看</a>
            {{/if}}
            <!-- <a href="javascript:void(0);" class="delete">删除</a> -->
            <input type="hidden" name="" value="{{:uuid}}">
        </td>
    </tr>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a class="current">广告内容管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="screening">
                    <div class="button_search">
                        <div class="search_div" style="width:200px;">
                            <div class="input-group">
                                <input type="text" class="form-control" placeholder="搜索..." id="search">
                                <label class="input-group-addon" id="searchButton"></label>
                            </div>
                        </div>
                    </div>
                    <a href="./advertAdd.jsp" class="add_account">添加</a>
                    <!-- <a class="add_account" id="recharge1" style="right:120px;">模拟用户充值</a>
                    <a class="add_account" id="draw1" style="right:240px;">模拟用户提现</a> -->
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="30%">标题</th>
                        <th>图片</th>
                        <th>状态</th>
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>操作</th>
                    </tr>
                    <tbody id="queboxCnt">

                    </tbody>
                </table>

                <div id="pageTool"></div>
                <%--//--%>
            </div>
        </div>
    </div>


</body>
</html>
<script type="text/javascript" src="./js/getHtmlDocName.js"></script>
<script src="./js/changePrice.js"></script>
<script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/advertList.js"></script>
