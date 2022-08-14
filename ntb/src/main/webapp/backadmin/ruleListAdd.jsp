<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛头帮－后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<%-- <link rel="stylesheet" href="css/bootstrap.css" /> --%>
<link rel="stylesheet" href="css/bootstrap2.css" />
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./css/msg_table.css">
<link rel="stylesheet" href="./css/coupon.css">
<link rel="stylesheet" href="./css/ruleListAdd.css">
<link rel="stylesheet" href="./js/laydate-v1.1/laydate/need/laydate.css">

<%--优惠券list模板--%>
<script type="text/template" id="cardTemplate">
    {{if couponType == "cash"}}
        <li>{{:couponPrice}}元｜{{:couponTypeCN}}｜{{:minInvestAccount}}元以上投资可用｜{{:deadline}}日内有效</li>
    {{else}}
        <li>{{:couponPrice}}%｜{{:couponTypeCN}}｜{{:minInvestAccount}}元以上投资可用｜{{:deadline}}日内有效</li>
    {{/if}}
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
            <input type="number" name="" value="{{:count}}" style="width:50px;margin:0;" min="1" class="card-num"> 张
        </td>
        <td>
            <a href="javascript:void(0);" class="delete" data-uuid="{{:uuid}}">删除</a>
        </td>
    </tr>
</script>
</head>

<body>

    <div class="text_box add_account_form box">
        <p class="">添加投放策略</p>
        <form:form id="form" action="../rest/backadmin/couponStrategy/add" method="post">
            <div class="form_item">
                <span><i>* </i> 投放ID：</span>
                <input type="text" name="strategyIdentification" value="" id="strategyIdentification" placeholder="请输入投放ID...">
            </div>

            <div class="form_item">
                <span><i>* </i> 截止时间：</span>
                <input type="text" name="expiryDate" value="" placeholder="请输入截止时间..." id="expiryDate" readonly>
            </div>
            <input type="hidden" name="coupon" id="coupon" value="">
        </form:form>

        <div class="add-card-box">
            <a href="javascript:void(0);" class="add-card">+&ensp;添加优惠券</a>
            <ul class="card-list" id="card-list">

            </ul>
        </div>

        <table class="msg_table" cellpadding="0" cellpadding="0">
            <tr class="first_tr">
                <th width="30%">投放的优惠券</th>
                <th>数量</th>
                <th>操作</th>
            </tr>
            <tbody id="queboxCnt">
                <tr>
                    <td colspan="3">请选择优惠券...</td>
                </tr>
            </tbody>

        </table>

        <div class="btn_group">
            <input type="button" name="" value="取消" onclick="parent.$.colorbox.close()">
            <input type="button" name="" value="确认" id="allRight">
        </div>
    </div>


    <script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <script type="text/javascript" src="js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
    <script type="text/javascript" src="js/highcharts.js" ></script>
    <script src="./js/laydate-v1.1/laydate/laydate.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/ruleListAdd.js"></script>
</body>
</html>
