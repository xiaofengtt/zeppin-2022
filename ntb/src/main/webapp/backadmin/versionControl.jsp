<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/accountControllerList.css">
<link rel="stylesheet" href="./css/screening.css">
<link rel="stylesheet" href="./css/msg_table.css">
<script type="text/template" id="queboxTpl">
    <tr {{if #index%2 == 1}}style="background:#F7FBFF;"{{/if}}>
        <td>{{:#index + 1}}</td>
        <td>{{:versionName}}</td>
        <td>{{:version}}</td>
        <td>
            {{if device == "02"}}
                Android
            {{else device == "03"}}
                IOS
            {{/if}}
        </td>
        <td>
            {{if flagCompel == true}}
                <span class="color_red">{{:flagCompelCN}}</span>
            {{else}}
                <span class="color_green">{{:flagCompelCN}}</span>
            {{/if}}
        </td>
        <td>{{:statusCN}}</td>
        <td>
            <a href="./versionControlEdit.jsp?uuid={{:uuid}}">编辑</a>
            {{if status == "unpublish"}}
                <a class="change" href="javascript:void(0);" data-uuid="{{:uuid}}" data-status="published">发布</a>
            {{else status == "published"}}
                <a class="change" href="javascript:void(0);" data-uuid="{{:uuid}}" data-status="disable">停用</a>
            {{else status == "disable"}}
                <a class="change" href="javascript:void(0);" data-uuid="{{:uuid}}" data-status="unpublish">启用</a>
            {{/if}}

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
	<input id="scode" type="hidden" value="00200025" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">用户权限管理</a><span>></span><a class="current">版本控制</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="searchDiv" style="right:140px;">
                    <div class="input-group">
                        <input id="search" class="form-control" type="text" value="" placeholder="搜索">
                        <label class="input-group-addon" id="searchButton"></label>
                    </div>
                </div>

                <div class="condition">
                    <a href="./versionControlAdd.jsp" class="add_account">＋添加</a>
                    <div class="statusDiv filter filter-status">
                        <label>状态：</label>
                        <div>
                            <a class="statusLight" id="all">全部<span id="allCount">(0)</span></a>
                            <a id="published">已发布<span id="publishedCount">(0)</span></a>
                            <a id="unpublish">未发布<span id="unpublishCount">(0)</span></a>
                            <a id="disable">已停用<span id="disableCount">(0)</span></a>
                        </div>
                    </div>
                    <div class="statusDiv filter filter-device">
                        <label>设备平台：</label>
                        <div>
                            <a class="statusLight" id="all">全部</a>
                            <a id="03">IOS</a>
                            <a id="02">Android</a>
                        </div>
                    </div>
                </div>

                <table cellspacing="0" cellpadding="0" class="msg_table">
                    <tr class="first_tr">
                        <th width="5%">#</th>
                        <th>版本名</th>
                        <th>版本号</th>
                        <th>设备</th>
                        <th>强制更新</th>
                        <th>状态</th>
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
<script src="./js/changePrice.js"></script>
<script type="text/javascript" src="./js/url.min.js"></script>
<script type="text/javascript" src="./js/flagSubmit.js"></script>
<script type="text/javascript" src="./js/jsrender.min.js"></script>
<script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
<script src="./js/versionControl.js"></script>
</body>
</html>
