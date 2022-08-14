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
        <%@ include file="./header.jsp" %>

        <div class="clearfix" id="main">
            <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>

            <div id="content">
                <div class="">
                    <ol class="breadcrumb">
                        <li class="active">撤销审核</li>
                    </ol>
                </div>
                <div class="contain">
        			<div class="contain-right">
        				<div class="main-contain">
        					<div class="row">
	        					<div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label" title="">
	        								表名</label>
	        							<div class="col-sm-8">
	        								<select class="form-control operableTable">
	        									<option value="">请选择</option>
	        								</select>
	        							</div>
	        						</div>
	        					</div>
	        					<a class="col-md-2 btn btn-primary" onclick="getList();" style="width:100px;">查询</a>
	        				</div>
							<!-- 列表 -->
							<div class="row" style="padding-left:15px; padding-right:15px;">
		                        <table class="table table-hover">
		                            <tr>
		                                <th>序号</th>
		                                <th>表名</th>
		                                <th>表名称</th>
		                                <th>类型</th>
		                                <th>操作人员</th>
		                                <th>操作时间</th>
		                                <th>操作</th>
		                            </tr>
		                            <tbody id="queboxCnt">
		
		                            </tbody>
		                        </table>
		                    </div>
							<div class="box-footer">
			                    <div id="pageTool">
			
			                    </div>
			                </div>
        				</div>
        				
        			</div>
        			<div class="clear"></div>
        		</div>
            </div>
        </div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                    <h4 class="modal-title" id="myModalLabel">提示</h4>
	                </div>
	                <div class="modal-body">
	                    <h4>确认撤销审核吗？</h4>
	                </div>
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
	                    <button type="button" class="btn btn-primary" id="btnSubmit" data-status="">确认</button>
	                </div>
	            </div>
	            <!-- /.modal-content -->
	        </div>
	        <!-- /.modal -->
	    </div>
	    <input id="scode" type="hidden" value="008003" />
        <script src="./js/checkRollback.js"></script>
	</body>
</html>
