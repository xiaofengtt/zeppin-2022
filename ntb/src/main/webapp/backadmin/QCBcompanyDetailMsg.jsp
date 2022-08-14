<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/confirm.css">
<title>用户交易记录详情</title>
<style>
    .main{
        margin:0 auto;
    }
    .main__title{
        text-align: center;
        font-size:18px;
        background:#F7FBFF;
        padding:15px 0;
    }
    .close{
        width:100px;
        height:35px;
        color:#4D9CFF;
        border:1px solid #4D9CFF;
        font-size:16px;
        background:#FFFFFF;
        border-radius: 5px;
        transition: all 0.2s linear;
        outline:none;
    }
    .btn_group{
        text-align: center;
        margin-top:35px;
    }
    .close:hover{
        background-color:#4D9CFF;
        color:#FFFFFF;
    }
</style>
<script type="text/template" id="queboxTpl">
    <div class="confirm_item">
        <span class="confirm_item_title">交易类型：</span>
        {{if priceflag == true}}
            <span class="color_red">{{:typeCN}}</span>
        {{else priceflag == false}}
            <span class="color_green">{{:typeCN}}</span>
        {{/if}}
    </div>
    <div class="confirm_item">
        <span class="confirm_item_title">交易金额：</span>
        {{if priceflag == true}}
            <span class="color_orange">{{:price}}</span>
        {{else}}
            <span class="color_green">-{{:price}}</span>
        {{/if}}

    </div>
    <div class="confirm_item">
        <span class="confirm_item_title">交易方式：</span>
        <span>{{:orderTypeCN}}</span>
    </div>
    <div class="confirm_item">
        <span class="confirm_item_title">交易状态：</span>
        <span>{{:statusCN}}</span>
    </div>
    <div class="confirm_item">
        <span class="confirm_item_title">本次余额：</span>
        <span>{{:accountBalanceCN}}</span>
    </div>

    <div class="confirm_item">
        <span class="confirm_item_title">订单号：</span>
        <span>{{:orderNum}}</span>
    </div>
    <div class="confirm_item">
        <span class="confirm_item_title">交易时间：</span>
        <span>{{:createtimeCN}}</span>
    </div>
    <div class="confirm_item">
        <span class="confirm_item_title">备注：</span>
        <span>{{:remark}}</span>
    </div>
</script>
</head>
<body>
    <div class="main">
        <p class="main__title">详细信息</p>
        <div id="queboxCnt">

        </div>
        <div class="btn_group">
            <input type="button" name="" value="关闭" class="close">
        </div>
    </div>

    <script type="text/javascript" src="js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="js/bootstrap.js" ></script>
    <script type="text/javascript" src="js/jquery.colorbox.js"></script>
    <script type="text/javascript" src="js/query.js"></script>
    <script type="text/javascript" src="js/paging.js"></script>
    <script type="text/javascript" src="js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jsrender.min.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js" ></script>
    <script type="text/javascript" src="js/highcharts.js" ></script>
    <script type="text/javascript" src="./js/QCBcompanyDetailMsg.js" ></script>
</body>
</html>
