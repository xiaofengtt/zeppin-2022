<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>北京信托EAST3.0</title>
	<link rel="stylesheet" href="./css/operatorContent.css" />
	<%@ include file="./globalLink.jsp" %>
	
	<script type="text/template" id="queboxTpl">
        <tr>
            <td>
                <input type="checkbox" name="" value="" class="check">
				<input class="" type="hidden" name="types" value="{{:name}}"></td>
            </td>
            <td>{{:#index + 1}}</td>
            <td class="name">{{:name}}</td>
			<td>{{:description}}</td>
			<td>{{:count}}</td>
            <td>
                <a href="./{{:name}}CheckList.jsp">查看</a>
            </td>
        </tr>
    </script>
</head>
<body>
    <input id="scode" type="hidden" value="008002" />
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
                            <li>待审核数据列表</li>
                        </ol>
                    </div>
                    <c:choose>
						<c:when test="${sessionScope.currentOperator.roleName == 'superAdmin'}">  
							<div class="row">
		       					<div class="col-md-5">
		        					<a class="col-md-2 btn btn-primary btn-sm" onclick="batchExam();" style="width:100px;">批量审核</a>
		       					</div>
		       				</div>  
						</c:when>
					</c:choose>					                 
                    <div class="row" style="padding-left:15px; padding-right:15px;">
                    	
                        <table class="table table-hover">
                            <tr>
                                <th>
                                    <input type="checkbox" id="allCheck" name="" value="">
                                </th>
                                <th>编号</th>
                                <th>表名</th>
                                <th>表名称</th>
                                <th>待审核数据条数</th>
                                <th>操作</th>
                            </tr>
                            <tbody id="queboxCnt">

                            </tbody>
                        </table>
                    </div>
				</div>
			</div>
        </div><%-- content --%>
    </div>
    <script src="./js/jsrender.js"></script>
    <script>
	    var types = [];
	    var paraTypes;
	    var typeUrl="";
    	$(document).ready(function() {    		
    		getList();
    		
    	});
    	function getList(listType) {
	        $.ajax({
	                url: "../rest/backadmin/menu/checkList",
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
	                        $(".name").each(function(){
	                        	$(this).html(getUpper($(this).html()));
	                        });
	                      //选中
	                	    $(".check").unbind("change").change(function() {
	                	        var checkFlag = true,
	                	            _this = "";
	                	        types = [];
	                	        typeUrl=""
	                	        $(".check").each(function(index, el) {
	                	            _this = $(".check").eq(index);
	                	            if (_this.prop("checked") == true) {
	                	            	types.push($(this).next().val());
	                	            	typeUrl = typeUrl+"&types="+$(this).next().val();
	                	            } else {
	                	                checkFlag = false;
	                	            }
	                	        });
	                	        if (checkFlag) {
	                	            $("#allCheck").prop("checked", true);
	                	        } else {
	                	            $("#allCheck").prop("checked", false);
	                	        }
	                	    	
	                	    });
	                		//全选
	                	    $("#allCheck").on("change", function() {
	                	        if ($(this).is(":checked") == true) {
	                	            types = [];
	                	            typeUrl=""
	                	            $(".check").prop("checked", true);
	                	            $(".check").each(function(index, el) {
	                	            	types.push($(this).next().val());
	                	            	typeUrl = typeUrl+"&types="+$(this).next().val();
	                	            });
	                	        } else {
	                	            $(".check").prop("checked", false);
	                	            types = [];
	                	            typeUrl = "";
	                	        }
	                	    });
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
		
	    $('.form-product').change(function(){
	    	getList();
	    });
	    //批量审核
	    function batchExam(){
	    	if(typeUrl!=""){
	    		var index = layer.load(1, {shade: false});
	    		$(".layui-layer").append("<span style='line-height:40px;'>正在审核，请稍后</span>");
	    		$.get('../rest/backadmin/check/check?status=checked'+typeUrl,function(r){
	    			layer.closeAll();
	    	    	if(r.status=="SUCCESS"){
	    				layer.msg("审核成功", {
		                    time: 2000
		                },function(){
		                	window.location.href=window.location.href;
		                });	    			
	    			}else{
	    	    		layer.msg(r.message);
	    	    	}
	    		}).fail(function(){
	    			layer.closeAll();
	    			layer.msg("error");
	    		});
	    	}else{
	    		layer.msg("请选择要审核的数据");
	    	}
	    }
    </script>
</body>
</html>
