<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>北京信托EAST3.0</title>
	<%@ include file="./globalLink.jsp" %>
	
	<script type="text/template" id="queboxTpl">
        <tr>
            <td>
                <input type="checkbox" name="" value="" class="check">
            </td>
            <td>{{:#index + 1}}</td>
            <td>{{:description}}</td>
			<td>{{:count}}</td>
            <td>
                <a href="./{{:name}}CheckList.jsp">查看</a>
            </td>
        </tr>
    </script>
</head>
<body>
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
    	<c:choose>
			<c:when test="${sessionScope.currentOperator.roleName == 'user'}">  
				<%@ include file="./menu.jsp" %>
			</c:when>
			<c:otherwise> 
				<%@ include file="./super-menu.jsp" %>
			</c:otherwise>
		</c:choose>
        <div id="content">
            <div class="box box-main hidden">
                <div class="box-body">
                	<div class="">
                        <ol class="breadcrumb">
                            <li>数据变更列表</li>
                        </ol>
                    </div>
                    <div class="row" style="padding-left:15px; padding-right:15px;">
                        <table class="table table-hover">
                            <tr>
                                <th>
                                    <input type="checkbox" id="allCheck" name="" value="">
                                </th>
                                <th>编号</th>
                                <th>表名</th>
                                <th>变更数据条数</th>
                                <th>操作</th>
                            </tr>
                            <tbody id="queboxCnt">

                            </tbody>
                        </table>
                    </div>
				</div>
			</div>
        </div><%-- content --%>
        <input id="scode" type="hidden" value="008007" />
    </div>
    <script src="./js/jsrender.js"></script>
    <script>
    	$(document).ready(function() {
    		getList();
    	});
    	$('.form-product').change(function(){
	    	getList();
	    });
    	function getList(listType) {
	        $.ajax({
	                url: "../rest/backadmin/menu/updateList",
	                type: 'get',
	                data: {
	                	"dataproduct":$("select.form-product").val()
	                }
	            })
	            .done(function(r) {
	                if (r.status == "SUCCESS") {
	                    if (r.totalResultCount != 0) {
	                        var template = $.templates("#queboxTpl");
	                        var html = template.render(r.data);
	                        $("#queboxCnt").html(html);
	                        $(".box-main").removeClass("hidden");
	                    }
	                } else {
	                    layer.msg(r.message, {
	                        time: 2000
	                    });
	                }
	            })
	            .fail(function() {
	                layer.msg("error", {
	                    time: 2000
	                });
	            });
	    } //getList
    </script>
</body>
</html>
