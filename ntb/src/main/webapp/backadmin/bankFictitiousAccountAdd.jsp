<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/confirm.css">
<link rel="stylesheet" href="./css/bankFictitiousAccountAdd.css">
</head>
<body>

    <div class="text_box">
        <p class="text_title">批量生成银行虚拟账号</p>

        <div class="confirm_item">
            <span class="confirm_title">
                <i class="color_red">*</i>
                选择银行账户：
            </span>
            <span id="bank-list">
                <div class="bank">
                    <p class="bank-name"></p>
                    <p class="img-num">
                        <img src="" alt="" class="bank-img">
                        <label>尾号：</label>
                        <span class="bank-num"></span>
                    </p>
                </div>
                <ul class="bank-list">

                </ul>
            </span>
        </div>
        <form:form id="form" action="../rest/backadmin/qcbVirtualAccounts/batchAdd" method="post">
            <input type="hidden" id="companyAccount" name="companyAccount" value="">
            <div class="confirm_item">
                <span class="confirm_title">
                    <i class="color_red">*</i>
                    虚拟账号起始：
                </span>
                <span>
                    1109 2953 1710 201
                    <input type="number" id="start-num" name="start" value="">
                </span>
            </div>
            <div class="confirm_item">
                <span class="confirm_title">
                    <i class="color_red">*</i>
                    虚拟账号结束：
                </span>
                <span>
                    1109 2953 1710 201
                    <input type="number" id="end-num" name="end" value="">
                </span>
            </div>
        </form:form>
        <div class="btn_group">
            <input type="button" name="" value="保存" id="sub">
        </div>
    </div>

    <script type="text/javascript" src="./js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="./js/bootstrap.js" ></script>
    <script type="text/javascript" src="./js/url.min.js"></script>
    <script type="text/javascript" src="./js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jsrender.min.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/bankFictitiousAccountAdd.js"></script>
</body>
</html>
