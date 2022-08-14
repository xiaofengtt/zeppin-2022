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
	    <script>
	    	<%	
	    		String pageType = "TGgJgxxb";
	    		Boolean flagEdit = false;Boolean flagCheck = false;
	    		if(session.getAttribute("methodList") != null){
	    			List<String> methodList = (List<String>) session.getAttribute("methodList");
	    			if(methodList.contains(pageType+":edit")){
	    				flagEdit = true;
	    			}
	    			if(methodList.contains(pageType+":check")){
	    				flagCheck = true;
	    			}
	    		}
	    	%>
			var flagEdit = "<%=flagEdit%>";
	    </script>
        <style>
            .label{
                padding:.3em .6em .3em;
            }
        </style>
    <script type="text/template" id="queboxTpl">
        <tr>
			<td>
                <input type="checkbox" name="" value="" class="check" data-id="{{:id}}">
            </td>
            <td>{{:#index + 1}}</td>
            <td style="vertical-align:middle">
                {{if type == "add"}}
                    <span class="label label-info">添加</span>
                {{/if}}
                {{if type == "edit"}}
                    <span class="label label-success">修改</span>
                {{/if}}
            </td>
            <td>{{:cjrq}}</td>
			<td class="updateTime">{{:updatetime}}</td>
			<td>{{:creatorName}}</td>
            <td>
				<a href="./TGgJgxxbForm.jsp?id={{:dataid}}">修改</a>
				<% if(flagCheck){ %>
					<a href="./TGgJgxxbCheck.jsp?id={{:id}}">审核</a>
				<% } %>
            </td>
        </tr>
    </script>
</head>
<body>
    <input id="listType" type="hidden" name="" value="TGgJgxxb">
	<input id="scode" type="hidden" value="001001" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
                <div class="box-body">
                    <div class="row">
                        <ol class="breadcrumb">
                            <li>机构信息表</li>
                            <li class="active">数据变更</li>
                        </ol>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <a href="./TGgJgxxbList.jsp" class="btn btn-sm btn-primary edit-button hidden">数据列表</a>
                            <button type="button" class="btn btn-sm btn-primary checkBtn pull-right" data-toggle="modal" data-target="#myModal" data-status="checked"><i class="fa fa-check"></i>批量审核</button>
                        </div>
                    </div>
                    <h5></h5>
                    <div class="row" style="padding-left:15px; padding-right:15px;">
                        <table class="table table-hover">
                            <tr>
                            	<th>
                                    <input type="checkbox" id="allCheck" name="" value="">
                                </th>
                                <th>编号</th>
                                <th>类型</th>
                                <th>采集日期</th>
                                <th>修改时间</th>
                                <th>创建人</th>
                                <th>操作</th>
                            </tr>
                            <tbody id="queboxCnt">

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="box-footer">
                    <div id="pageTool">

                    </div>
                </div>
            </div>


        </div><%-- content --%>
    </div>
    <%@ include file="./batchExam.jsp" %>
	<script src="./js/jsrender.js"></script>
    <script src="./js/globalCheckList.js"></script>
</body>
</html>
