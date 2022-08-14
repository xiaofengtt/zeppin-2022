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
		<script>
	    	<%	
	    		List<String> typeList = new ArrayList<String>();
	    		if(session.getAttribute("methodList") != null){
	    			List<String> methodList = (List<String>) session.getAttribute("methodList");
	    			for(String method:methodList){
	    				if(method.indexOf(":edit") > -1){
	    					typeList.add(method.substring(0, method.indexOf(":edit")));
	    				}
	    			}
	    		}
	    	%>
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
                <div class="">
                    <ol class="breadcrumb">
                        <li class="active">勾稽验证</li>
                    </ol>
                </div>
                <div class="contain">
        			<div class="contain-right">
        				<div class="main-contain">							        					
							<!-- 列表 -->
							
							<div class="row" style="padding-left:15px; padding-right:15px;">							
									<button type="button" class="btn btn-md btn-primary pull-right bt-15" id="formGoBackBtn"><i class="fa fa-reply-all"></i>返回</button>							
			                        <table class="table table-hover" style="table-layout:fixed;">
			                            <tr>
			                                <th width="5%">序号</th>
			                                <th width="10%">表名</th>
			                                <th width="15%">表名称</th>
			                                <th width="10%">字段名</th>
			                                <th width="10%">字段类型</th>
			                                <th width="10%">字段内容</th>
			                                <th width="30%">勾稽类型条件可选值</th>
			                                <th width="10%">操作</th>
			                            </tr>
			                            <tbody id="queboxCnt">		                            	
			                            	                            
			                            </tbody>
			                        </table>
			                        
			                    </div>
							</div>
        			</div>
        			<div class="clear"></div>
        		</div>
            </div>
            <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-hidden="true">
		        <div class="modal-dialog">
		            <div class="modal-content">
		                <div class="modal-header">
		                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                    <h4 class="modal-title" id="myModalLabel">提示</h4>
		                </div>
		                <div class="modal-body">
		                    <h4>确认提交吗？</h4>
		                </div>
		                <div class="modal-footer">
		                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
		                    <button type="button" class="btn btn-primary" id="btnSubmit">确认</button>
		                </div>
		            </div>
		            <!-- /.modal-content -->
		        </div>
		        <!-- /.modal -->
    </div>
        </div>
        <input id="scode" type="hidden" value="008004" />
        <script type="text/javascript" src="js/url.min.js"></script>
        <script src="./js/crossCheckResult.js"></script>
	</body>
</html>
