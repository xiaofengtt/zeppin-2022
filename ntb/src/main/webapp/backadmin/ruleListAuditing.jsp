<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>投放策略变动申请</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/fundList.css">
<link rel="stylesheet" href="./css/auditing.css">
<script type="text/template" id="queboxTpl">
    <div class="product_item">
        <table class="product_head" cellspacing="0" cellpadding="0">
            <tr class="tr_bg_blue">
                <td width="7%">
                    {{if type == "edit"}}
                        <span class="product_tip bg_black">修改</span>
                    {{else type == "add"}}
                        <span class="product_tip bg_green">添加</span>
                    {{else type == "open"}}
                        <span class="product_tip bg_blue">开启</span>
                    {{else type == "close"}}
                        <span class="product_tip bg_orange">关闭</span>
                    {{else type == "delete"}}
                        <span class="product_tip bg_gray">删除</span>
                    {{/if}}
                </td>
                <td width="60%" class="product_name">
                    <a href="./ruleListAuditingMsg.jsp?uuid={{:uuid}}" class="color_blue">【{{:strategyIdentification}}】({{:couponStrategyName}})</a>
                </td>
                <td width="20%"></td>
                <td width="20%">

                    {{if status == "checked"}}
                        <a href="./ruleListAuditingMsg.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
                    {{else status == "unchecked"}}
                        <p style="margin:0;">成功提交，等待审核...</p>
                    {{else status == "draft"}}
                        <a class="checked" data-uuid="{{:uuid}}">提交审核</a>
                        <a style="margin-left:10px;" class="delete" data-uuid="{{:uuid}}">删除</a>
                    {{else status == "unpassed"}}
                        <a class="checked" href="javascript:void(0);" data-uuid="{{:uuid}}">重新提交</a>
                        <a style="margin-left:10px;" class="delete" data-uuid="{{:uuid}}">删除</a>
                    {{/if}}
                </td>
            </tr>
        </table>

        <div class="product_content">
            <div class="product_content_item">
                <span>填报人：{{:creatorName}}</span>
                <span>填报时间：{{:createtimeCN}}</span>
                <span>提交时间：{{:submittimeCN}}</span>
            </div>
            <div class="product_content_item">
                <span>审核人：{{:checkerName}}</span>
                <span>审核时间：{{:checktimeCN}}</span>

                {{if status=='unchecked'}}
                    <span>审核信息：<i class="color_red">{{:reason}}</i></span>
                {{else status=='checked'}}
                    <span>审核信息：<i class="color_green">{{:reason}}</i></span>
                {{else status=='unpassed'}}
                    <span>审核信息：<i class="color_red">{{:reason}}</i></span>
                {{else}}
                    <span>审核信息：<i class="color_red">{{:reason}}</i></span>
                {{/if}}
            </div>
        </div>
        {{if status == "unchecked"}}
            <img src="./img/uncheck.png" class="status_img" />
        {{else status == "checked"}}
            <img src="./img/check.png" class="status_img" />
        {{else status == "unpassed"}}
            <img src="./img/failed.png" class="status_img" />
        {{/if}}
    </div>
</script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00400044" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a class="current">投放策略申请</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div class="condition">
                    <div class="statusDiv shortStatusDiv filter">
                        <label>审核状态：</label>
                        <div>
                            <a class="statusLight" id="all">全部<span id="allCount">(0)</span></a>
                            <a id="draft">草稿<span id="draftCount">(0)</span></a>
                            <a id="unchecked">待审核<span id="uncheckedCount">(0)</span></a>
                            <a id="checked">审核通过<span id="checkedCount">(0)</span></a>
                            <a id="unpassed">审核不通过<span id="unpassedCount">(0)</span></a>
                        </div>
                    </div>
                    <div class="statusDiv filter1">
                        <label>信息类型：</label>
                        <div>
                            <a class="statusLight" id="all">全部<span id="typeCount">(0)</span></a>
                            <a id="add">添加<span id="addCount">(0)</span></a>
                            <a id="edit">修改<span id="editCount">(0)</span></a>
                            <a id="open">开启<span id="openCount">(0)</span></a>
                            <a id="close">关闭<span id="closeCount">(0)</span></a>
                            <a id="delete">删除<span id="deleteCount">(0)</span></a>
                        </div>
                    </div>
                </div>

                <div id="queboxCnt"></div>

                <div id="pageTool"></div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="./js/getHtmlDocName.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/ruleListAuditing.js"></script>
</body>
</html>