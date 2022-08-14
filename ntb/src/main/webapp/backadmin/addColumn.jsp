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
<link rel="stylesheet" href="./css/bank_form.css">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:include page="navigation.jsp"/>
	<input id="scode" type="hidden" value="00600061" />
    <div class="contain">
        <jsp:include page="contentLeft.jsp"/>

        <div class="contain-right">
            <div class="location">
                <div class="locationLeft"><a href="">内容管理CMS</a><span>></span><a href="./columnList.jsp">栏目管理</a><span>></span><a class="current">添加栏目</a></div>
                <div class="clear"></div>
            </div>
            <div class="main-contain pt-13 pl-14 pr-16">

                <%-- <div class="submit_logo" action="" method="post">
                    <img src="" alt="">
                </div> --%>

                <form:form class="add_account_form" action="../rest/backadmin/companyAccount/add" method="post" id="add">
                    <div class="form_item">
                        <span><i>*</i> 栏目地址</span>
                        <input type="text" name="companyName" id="lmdz" value="" autocomplete="off" placeholder="请输入栏目地址...">
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 栏目名称</span>
                        <input type="text" name="companyName" id="lmmc" value="" autocomplete="off" placeholder="请输入栏目名称...">
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 仅新用户可见</span>
                        <select class="" name="" id="xyh">
                            <option value="">是</option>
                            <option value="">否</option>

                        </select>
                    </div>

                  <div class="form_item">
                        <span><i>*</i> 顺序</span>
                        <input type="number" name="accountName" id="sx" value="" autocomplete="off" placeholder="请输入顺序...">
                    </div>

                    <div class="form_item">
                        <span><i>*</i> 栏目类型</span>
                        <select class="" name="" id="lmlx">
                            <option value="">理财产品</option>
                            <option value="">图文链接</option>
                        </select>
                    </div>
                    <div class="form_item">
                        <span><i>*</i> 编号</span>
                        <input type="number" name="accountNum" id="bh" value="" autocomplete="off" placeholder="请输入栏目类型...">
                    </div>
                    <div class="btn_group">
                        <input type="button" name="" value="取消" onclick="window.location.href = document.referrer;">
                        <input type="button" name="" value="保存" id="add_submit">
                    </div>
                </form:form>


            </div>
        <div>
    </div>
    <script type="text/javascript" src="js/url.min.js"></script>
    <script type="text/javascript" src="js/flagSubmit.js"></script>
    <script type="text/javascript" src="js/jquery.form.js"></script>
    <script type="text/javascript" src="js/layer-v3.0.1/layer/layer.js"></script>
    <script src="./js/addColumn.js"></script>
</body>
</html>
