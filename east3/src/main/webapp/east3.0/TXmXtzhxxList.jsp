<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.List,java.util.Map,cn.zeppin.product.utility.JSONUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>北京信托EAST3.0</title>
    <%@ include file="./globalLink.jsp" %>
	<script>
    	<%	
    		String pageType = "TXmXtzhxx";
    		Boolean flagCheck = false;
    		if(session.getAttribute("methodList") != null){
    			List<String> methodList = (List<String>) session.getAttribute("methodList");
    			if(methodList.contains(pageType+":check")){
    				flagCheck = true;
    			}
    		}
    	Map<String, String> productMap = (Map<String, String>) session.getAttribute("productMap");
    		String productJson = JSONUtils.obj2json(productMap);
    	%>
     	var flagCheck = "<%=flagCheck%>";
     	var productMap = JSON.parse('<%=productJson%>');
    </script>
    <script type="text/template" id="queboxTpl">
        <tr>
            <td>
                <input type="checkbox" name="" value="" class="check" data-id="{{:id}}">
            </td>
            <td>{{:#index + 1}}</td>
            <td>{{:xtxmbm}}</td>
          	<td class="xtxmbh">{{:xtxmbm}}</td>
            <td>{{:cjrq}}</td>
            <td class="createTime"></td>
            <td class="updateTime"></td>
            <td>
                <a href="./TXmXtzhxxForm.jsp?id={{:id}}">修改</a>
				<a href="javascript:" onclick="deleteList('{{:id}}')">删除</a>
            </td>
        </tr>
    </script>
</head>
<body>
    <input id="listType" type="hidden" name="" value="TXmXtzhxx">
    <input id="xtxmbhName" type="hidden" name="" value="xtxmbm">
	<input id="scode" type="hidden" value="004002" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
                <div class="box-body">
                    <div class="row">
                        <ol class="breadcrumb">
                            <li>信托账户信息</li>
                            <li class="active">数据采集</li>
                        </ol>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <a class="btn btn-sm btn-primary check-button hidden" href="./TXmXtzhxxCheckList.jsp">审核列表</a>
                            <a class="btn btn-sm btn-primary pull-right cboxElement dataOutput" href="javascript:">导出数据</a>
                            <a class="btn btn-sm btn-primary pull-right mr-10" href="./TXmXtzhxxForm.jsp">添加数据</a>
                            <a class="btn btn-sm btn-primary pull-right mr-10" onclick="fileAdd()">导入数据</a>
                            <a class="btn btn-sm btn-primary pull-right mr-10" href="javascript:" id="fileOutput">导出模板</a>
                            <div id="uploadFileId" style="display:none;"></div>
							<div id="uploadFileAdd"><input type="hidden" name="file" id="uploadFile" value=""></div>
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
                                <th>信托项目编号</th>
                                <th>信托项目名称</th>
                                <th>采集日期</th>
                                <th>录入时间</th>
                                <th>修改时间</th>
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


    <!-- 错误枚举 -->
    <%@ include file="./errorTable.jsp" %>
    <%@ include file="./batchExam.jsp" %>	
	<script src="./js/jsrender.js"></script>
    <script src="./js/globalList.js"></script>
</body>
</html>
