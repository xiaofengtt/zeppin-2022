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
<link rel="stylesheet" href="./css/bootstrap2.css" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/imgMsg.css">
<link rel="stylesheet" href="./css/bank_form.css">
<link rel="stylesheet" href="./js/laydate-v1.1/laydate/need/laydate.css">
<link rel="stylesheet" href="./css/cardListAdd.css">
<style>
    .add_account_form .form_item input, .add_account_form .form_item select{
     box-sizing:border-box;
     -webkit-box-sizing:border-box;
    }
    .add_account_form .form_item{
     position: relative;
    }
    .add_account_form .form_item small {
     position: absolute;
     left: 64%;
    }
    input[type="number"]::-webkit-outer-spin-button,input::-webkit-inner-spin-button{
        -webkit-appearance:textfield;
    }
</style>
</head>

<body>
    <div class="text_box add_account_form box">
        <form:form action="../rest/backadmin/coupon/add" method="post" id="submit">
            <p class="text_title">添加优惠券</p>
            <div class="form_item">
                <span><i>* </i> 名称：</span>
                <input type="text" name="couponName" value="" step="0.01" id="couponName" placeholder="请输入名称..." autocomplete="off">
            </div>

            <div class="form_item">
                <span><i>* </i> 类型：</span>
                <select class="" name="couponType" id="couponType">
                    <option value="cash">现金券</option>
                    <option value="interests">加息券</option>
                </select>
            </div>

            <div class="form_item">
                <span><i>* </i> 面值：</span>
                <input type="number" step="0.01" name="couponPrice" value="" placeholder="请输入面值..." id="couponM" autocomplete="off">
                <small>元</small>
            </div>

            <div class="form_item" style="display:none">
                <span><i>* </i> 加息：</span>
                <input type="number" step="0.01" name="couponPrice" value="" placeholder="请输入加息..." id="couponJ" autocomplete="off">
                <small>%</small>
            </div>

            <div class="form_item">
                <span><i>* </i> 最小投资额：</span>
                <input type="number" name="minInvestAccount" value="" placeholder="请输入最小投资额..." id="minInvestAccount" autocomplete="off">
                <small>元</small>
            </div>

            <div class="form_item">
                <span><i>* </i> 使用期限：</span>
                <input type="checkbox" name="" value="" class="checkbox">发放日起
                <input type="number" name="deadline" value="" class="m_time" id="deadline" autocomplete="off" min="0" >天内
            </div>

            <div class="form_item">
                <span></span>
                <input type="checkbox" name="" value="" class="checkbox">截止时间
                <input type="text" name="expiryDate" value="" class="end_time" id="expriyDate" autocomplete="off" readonly>
            </div>

            <div class="btn_group">
                <input type="button" name="" value="取消" onclick="parent.$.colorbox.close()">
                <input type="button" name="" value="确认" id="allRight">
            </div>

        </form:form>
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
   <script type="text/javascript" src="js/jquery.form.js" ></script>
   <script src="./js/laydate-v1.1/laydate/laydate.js"></script>
   <script type="text/javascript" src="./js/cardListAdd.js" ></script>
</body>
</html>
