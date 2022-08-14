<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/coupon.css">
<link rel="stylesheet" href="./css/confirm.css">
<title>投放策略审核－详细信息</title>
<style>
.card_td{
    padding:10px;
    height:140px;
}
.box{
    margin:0 auto;
    width:90%;
    margin-top:20px;
}
.text_title{
    margin-bottom:20px;
}

.add_account_form .btn_group input:nth-child(2){
    margin-left:0;
}
</style>

<%--优惠券list模板--%>
<script type="text/template" id="cardTemplate">
    <li>{{:couponPrice}}元｜{{:couponTypeCN}}｜{{:minInvestAccount}}元以上投资可用｜{{:deadline}}日内有效</li>
</script>

<%--列表模板--%>
<script type="text/template" id="queboxTpl">
    <tr>
        <td class="card_td">
            <div class="card">
                {{if couponType == "cash"}}
                    <span class="card_title">{{:couponPriceCN}}</span><small class="card_title_small">元</small>
                {{else}}
                    <span class="card_title">{{:couponPriceCN}}</span><small class="card_title_small">%</small>
                {{/if}}
                <span class="card_title_right color_orange">{{:couponTypeCN}}</span>
                <p class="card_remark_top">可用于{{:minInvestAccount}}元以上的投资</p>
                <p class="card_remark_bottom">发放起{{:deadline}}日内有效</p>
            </div>
        </td>

        <td>
            <input type="number" name="" value="{{:count}}" style="width:50px;margin:0;" min="1" class="card-num" readonly> 张
        </td>
        <!-- <td>
            <a href="javascript:void(0);" class="delete" data-uuid="{{:uuid}}">删除</a>
        </td> -->
    </tr>
</script>
</head>


<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
    <input id="scode" type="hidden" value="00400047" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>
        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a href="./ruleListAuditingCheck.jsp">投放策略待审核事项</a><span>></span><a class="current">信息</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">
                <div class="recharge_confirm" style="display:block">
                    <div class="confirm">
                        <div class="text_box add_account_form box">
                            <p class="text_title">投放策略</p>
                            <div class="confirm_item">
                                <span class="confirm_item_title">投放ID：</span>
                                <span id="strategyIdentification"></span>
                            </div>

                            <div class="confirm_item">
                                <span class="confirm_item_title">截止时间：</span>
                                <span id="expiryDate"></span>
                            </div>

                            <div class="confirm_item">
                                <span class="confirm_item_title">提交人：</span>
                                <span id="createName"></span>
                            </div>
                            <div class="confirm_item">
                                <span class="confirm_item_title">提交时间：</span>
                                <span id="createTime"></span>
                            </div>
                            <div class="confirm_item" id="reasonValue">
                                <span class="confirm_item_title">审核信息：</span>
                                <span><input type="text" name="" value="" style="margin:0;" id="reason" autocomplete="off"></span>
                            </div>
                            <div class="confirm_item" id="reasonConfirm">
                                <span class="confirm_item_title">审核信息：</span>
                                <span class="" id="reasonMsg"></span>
                            </div>
                            <div class="confirm_item" id="statusConfirm">
                                <span class="confirm_item_title">操作：</span>
                                <span class="color_red" id="statusMsg"></span>
                            </div>

                            <table class="msg_table" cellpadding="0" cellpadding="0">
                                <tr class="first_tr">
                                    <th width="50%">投放的优惠券</th>
                                    <th>数量</th>
                                    <%-- <th>操作</th> --%>
                                </tr>
                                <tbody id="queboxCnt">

                                </tbody>

                            </table>

                            <div class="btn_group">
                                <input type="button" class="auditing_check" value="通过" data-uuid="" data-status="checked">
                                <input type="button" class="auditing_check" value="不通过" data-uuid="" data-status="unpassed">
                                <input type="button" id="edit" name="" value="取消" onclick="window.location.href = document.referrer">
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

 <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/laydate-v1.1/laydate/laydate.js"></script>
    <script type="text/javascript" src="./js/ruleListAuditingMsgCheck.js"></script>
</body>
</html>
