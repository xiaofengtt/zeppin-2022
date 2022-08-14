<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>牛投帮-后台管理系统</title>
<link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
<link rel="stylesheet" href="./css/select2.min.css">
<link rel="stylesheet" href="./css/bank_form.css">
    <style>
        .select2-container{
            padding:0 !important;
        }
        .select2-selection__rendered{
            text-align: left;
            font-size:13px;
            color:#555555;
        }
        .select2-container--default{
            margin-bottom:10px;
            height:30px !important;
        }
        .select2-selection--single{
            border:1px solid #C1C1C1 !important;
            height:30px !important;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">财务管理</a><span>></span><a href="./accountControllerList.jsp">账户管理</a><span>></span><a class="current">添加账户</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <div class="submit_logo" action="" method="post">
                    <img src="" alt="">
                </div>

                <form:form class="add_account_form" action="../rest/backadmin/companyAccount/add" method="post" id="add">
                    <div class="form_item">
                        <span><i>*</i> 账户类型</span>
                        <select name="type" id="type">
                            <option value="0">请选择账户类型...</option>
                            <option value="third">第三方账户</option>
                            <option value="invest">投资户</option>
                            <option value="collect">募集户</option>
                            <option value="redem">结算户</option>
                        </select>
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 开户行</span>
                        <select name="bank" id="bankList">
                            <option value="0">请选择开户行...</option>
                        </select>
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 支行</span>
                        <select name="branchBank" id="branchBankList">
                            <option value="0">请选择支行...</option>
                        </select>
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 开户名</span>
                        <input type="text" name="companyName" id="companyName" value="" autocomplete="off" placeholder="请输入开户名...">
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 开户别名</span>
                        <input type="text" name="accountName" id="accountName" value="" autocomplete="off" placeholder="请输入开户别名...">
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 账号</span>
                        <input type="text" name="accountNum" id="accountNum" value="" autocomplete="off" placeholder="请输入账号...">
                    </div>



                    <%-- <div class="form_item">
                        <span><i>*</i> 绑定账户</span>
                        <select name="bindAccount" id="bindAccount">
                            <option value="0">请选择绑定...</option>
                        </select>
                    </div> --%>

                    <div class="form_item">
                        <span><i>*</i> 状态</span>
                        <select name="status" id="status">
                            <option value="0">请选择状态...</option>
                            <option value="normal">正常</option>
                            <option value="disable">禁用</option>
                        </select>
                    </div>

                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.href = document.referrer;">
                        <input type="button" name="" value="保存" id="add_submit">
                    </div>
                </form:form>


            </div>
        <div>
    </div>
    <script type="text/javascript" src="./js/url.min.js"></script><script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="./js/jquery.form.js"></script>
    <script type="text/javascript" src="./js/layer-v3.0.1/layer/layer.js"></script>
    <script type="text/javascript" src="./js/select2.min.js"></script>
    <script src="./js/addAccount.js"></script>
</body>
</html>
