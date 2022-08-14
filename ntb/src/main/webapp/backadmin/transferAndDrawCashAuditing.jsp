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
<link rel="stylesheet" href="./css/auditing.css">
<script type="text/template" id="queboxTpl">
    <div class="product_item">
        <table class="product_head" cellspacing="0" cellpadding="0">
            <tr class="tr_bg_blue">
                <td width="5%">
                    <span class="product_tip bg_green">{{:typeCN}}</span>
                </td>
                <td width="50%" class="product_name color_blue">
                    <a href="./transferAndDrawCashAuditingMsg.jsp?uuid={{:uuid}}">
                        【{{:companyAccountHistoryInfo.companyAccountOutInfo.typeCN}}】
                        {{:companyAccountHistoryInfo.companyAccountOutInfo.accountName}}
                        (尾号{{:companyAccountHistoryInfo.companyAccountOutInfo.accountNumLess}})
                        &ensp;
                        ->
                        【{{:companyAccountHistoryInfo.companyAccountInInfo.typeCN}}】
                        {{:companyAccountHistoryInfo.companyAccountInInfo.accountName}}
                        (尾号{{:companyAccountHistoryInfo.companyAccountInInfo.accountNumLess}})
                    </a>
                </td>
                <td width="10%" class="color_orange" style="border-left:1px solid #eee;border-right:1px solid #eee;">金额：{{:companyAccountHistoryInfo.totalAmountCN}}</td>
                <td width="10%" style="text-align:center;">
                    {{if status == "checked"}}
                        <a href="./transferAndDrawCashAuditingMsg.jsp?uuid={{:uuid}}" style="text-align:center;" class="color_gray">查看</a>
                    {{else status == "unchecked"}}
                        <p style="margin:0;">成功提交，等待审核...</p>
                    {{else status == "draft"}}
                        <a class="checked" href="javasrcipt:;" data-uuid="{{:uuid}}">提交审核</a>
                        <a style="margin-left:10px;" class="delete" href="javasrcipt:;" data-uuid="{{:uuid}}">删除</a>
                    {{else status == "unpassed"}}
                        <a class="checked" href="javascript:;" data-uuid="{{:uuid}}">重新提交</a>
                        <a style="margin-left:10px;" class="delete" href="javasrcipt:;" data-uuid="{{:uuid}}">删除</a>
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
    <input id="scode" type="hidden" value="00600066" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./auditingItem.jsp">申请事项</a><span>></span><a class="current">企业账户间转账申请</a></div>
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
                    <%-- <div class="statusDiv filter1">
                        <label>信息类型：</label>
                        <div>
                            <a class="statusLight" id="all">全部<span id="typeCount">(0)</span></a>
                            <a id="transfer">转账<span id="transferCount">(0)</span></a>
                            <a id="withdraw">提现<span id="withdrawCount">(0)</span></a>
                        </div>
                    </div> --%>
                </div>

                <div id="queboxCnt"></div>

                <div id="pageTool"></div>

            </div>
        </div>
    </div>
    <script src="./js/getHtmlDocName.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/transferAndDrawCashAuditing.js"></script>
</body>
</html>
