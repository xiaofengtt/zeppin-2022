<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html">
		<title>后台管理系统</title>
		<%@ include file="./globalLink.jsp" %>
		<link rel="stylesheet" href="./css/operatorMethodList.css" />
		<link rel="stylesheet" href="./css/operatorContent.css" />
	</head>
	<body>
		<input id="scode" type="hidden" value="009002" />
        <%@ include file="./header.jsp" %>

        <div class="clearfix" id="main">
            <%@ include file="./super-menu.jsp" %>

            <div id="content">
                <div class="">
                    <ol class="breadcrumb">
                        <li class="active">权限管理</li>
                    </ol>
                </div>
                <div class="contain">
        			<div class="contain-right">
        				<div class="main-contain">
        					<%-- <div class="operator-block">
        						<span>选择用户：</span>
        						<div class="operator-list"  id="operatorSelect"></div>
        					</div> --%>
                            <div class="row">
                                <div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label" title="">
	        								选择用户</label>
	        							<div class="col-sm-8">
	        								<select class="form-control" id="operatorSelect" name="" data-live-search="true">
                                            <option value="">请选择用户</option>
                                        </select>
	        							</div>
	        						</div>
	        					</div>
                                <div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label" title="">
	        								权限</label>
	        							<div class="col-sm-8">
	        								<select class="form-control operablePower">
	        									<option value="1">编辑</option>
	        									<option value="2">审核</option>
	        								</select>
	        							</div>
	        						</div>
	        					</div>
                            </div>
        					<div class="clear"></div>
        					<form:form id="formsubmit" role="form" action="#" method="post">
        					<div id="contain-all" class="hidden">
        						<div class="contain-head">
        							<span><span id="operatorName"></span>用户权限设置</span>
        							<button class="btn btn-primary btn-sm" type="submit">提交修改</button>
        							<button class="btn btn-success btn-sm" type="button" onclick="selectAll(this) ">全选</button>
        						</div>
        						<input type="hidden" id="operator" name="operator"/>
        						<div class="contain-body">
        							<div class="contain-body-left" id="contain-body-left">
        								<ul id="left-ul"></ul>
        							</div>
        							<div class="contain-body-right" id="contain-body-right"></div>
        						</div>
        					</div>
        					</form:form>
        				</div>
        			</div>
        			<div class="clear"></div>
        		</div>
            </div>
        </div>
        <script src="./js/operatorMethodList.js"></script>
	</body>
</html>
