<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>应用商店管理</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/accountControllerList.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<script type="text/template" id="queboxTpl">
    <tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        <td>{{:#index + 1}}</td>
        <td>{{:webMarket}}</td>
        <td>{{:webMarketName}}</td>
        <td>
            {{if flagSwitch == true}}
                <span class="color_green">开</span>
            {{else}}
                <span class="color_red">关</span>
            {{/if}}
        </td>
        <td>{{:versionName}}</td>
        <td>{{:versionNum}}</td>
        <td>
            <a href="./storeSwitchEdit.jsp?uuid={{:uuid}}">编辑</a>
            {{if flagSwitch == true}}
                <a href="javascript:void(0);" class="change" data-uuid="{{:uuid}}"  data-switchFlag="false">关闭</a>
            {{else}}
                <a href="javascript:void(0);" class="change" data-uuid="{{:uuid}}"  data-switchFlag="true">开启</a>
            {{/if}}
            <a href="javascript:void(0);" class="delete" data-uuid="{{:uuid}}">删除</a>
        </td>
    </tr>
</script>
<style>
    .add_account{
        color: #FFFFFF;
        display: inline-block;
        font-size: 14px;
        height: 40px;
        width: 100px;
        line-height: 40px;
        background: #4D9CFF;
        text-align: center;
        -webkit-border-radius: 5px;
        -moz-border-radius: 5px;
        -ms-border-radius: 5px;
        -o-border-radius: 5px;
        border-radius: 5px;
        position: absolute;
        top: 20px;
        right: 24px;
    }
</style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00200029" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a class="current">应用商店管理</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">


                <div class="condition">
                    <div class="searchDiv" style="position:relative;width:260px;margin:0;top:0;left:0;">
                        <div class="input-group">
                            <input id="search" class="form-control" type="text" value="" placeholder="搜索">
                            <label class="input-group-addon" id="searchButton"></label>
                        </div>
                    </div>
                    <a href="./storeSwitchAdd.jsp" class="add_account">＋添加</a>
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="5%">#</th>
                        <th>应用市场标识码</th>
                        <th>应用市场名称</th>
                        <th>开关</th>
                        <th>版本名称</th>
                        <th>版本号</th>
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
	<script src="js/changePrice.js"></script>
	<script type="text/javascript" src="js/url.min.js"></script>
	<script type="text/javascript" src="js/flagSubmit.js"></script>
	<script type="text/javascript" src="js/jsrender.min.js"></script>
	<script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
	<script src="./js/storeSwitch.js"></script>
</body>
</html>
