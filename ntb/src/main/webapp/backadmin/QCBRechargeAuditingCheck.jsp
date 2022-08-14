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
                <td width="7%">
                    <span class="product_tip {{if type == 'edit'}}bg_black{{else}}bg_green{{/if}}">{{:typeCN}}</span>
                </td>
                <td width="50%" class="product_name color_blue">

                    {{if type == "recharge"}}
                        <a href="./QCBRechargeAuditingMsgCheck.jsp?uuid={{:uuid}}">
                            【{{:typeCN}}】
                            {{:qcbCompany}}
                        </a>
                    {{else}}
                        <a href="./QCBexpendAuditingMsgCheck.jsp?uuid={{:uuid}}">
                            【{{:typeCN}}】
                            {{:qcbCompany}}
                        </a>
                    {{/if}}
                </td>
                <td width="20%" class="product_name color_blue" style="padding:0 10px;border-left:1px solid #c1c1c1;border-right:1px solid #c1c1c1;">
                    金额：{{:price}}
                </td>
                <td width="20%" style="padding:0 10px;text-align:center;">
                    {{if status == "checked"}}
                        {{if type == "recharge"}}
                            <a href="./QCBRechargeAuditingMsgCheck.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
                        {{else}}
                            <a href="./QCBexpendAuditingMsgCheck.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
                        {{/if}}

                    {{else status == "unpassed"}}
                        {{if type == "recharge"}}
                            <a href="./QCBRechargeAuditingMsgCheck.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
                        {{else}}
                            <a href="./QCBexpendAuditingMsgCheck.jsp?uuid={{:uuid}}" class="color_gray">查看</a>
                        {{/if}}

                    {{else status == 'unchecked'}}
                        {{if type == "recharge"}}
                            <a href="./QCBRechargeAuditingMsgCheck.jsp?uuid={{:uuid}}">审核</a>
                        {{else}}
                            <a href="./QCBexpendAuditingMsgCheck.jsp?uuid={{:uuid}}">审核</a>
                        {{/if}}

                        <!-- <div class="btn-group">

                            <div class="popover">
                                <div class="popover-content">
                                    <p><input class="form-control reason" type="text" name="reason"></p>
                                    <div>
                                        <button class="btn btn-primary auditing_check" type="button" data-uuid="{{:uuid}}" data-status="checked">通过</button>
                                        <button class="btn btn-primary auditing_check" type="button" data-uuid="{{:uuid}}" data-status="unpassed">不通过</button>
                                    </div>
                                </div>
                            </div>
                        </div> -->
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
    <input id="scode" type="hidden" value="00800083" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">企财宝网站管理</a><span>></span><a class="current">待审批事项</a></div>
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
                    <div class="statusDiv shortStatusDiv filter">
                        <label>信息类型：</label>
                        <div>
                            <a class="statusLight" id="all">全部<span id="typeCount">(0)</span></a>
                            <a id="recharge">充值<span id="rechargeCount">(0)</span></a>
                            <a id="expend">费用扣除<span id="expendCount">(0)</span></a>
                        </div>
                    </div>
                </div>

                <div id="queboxCnt"></div>

                <div id="pageTool"></div>
            </div>
        </div>
    </div>
    <script src="./js/getHtmlDocName.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/getStatusList.js"></script>
    <script type="text/javascript" src="./js/QCBRechargeAuditingCheck.js"></script>
</body>
</html>
