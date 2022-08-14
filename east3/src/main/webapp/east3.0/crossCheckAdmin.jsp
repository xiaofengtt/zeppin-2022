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
								<div class="firstDiv">
									<div class="row">
			        					<a class="col-md-2 btn btn-primary btn-sm testbtn" style="width:100px;">勾稽验证</a>	 
			        					<a class="col-md-2 btn btn-primary btn-sm pull-right exporbtn" style="width:100px;">按部门统计导出</a>	 	        					
			        				</div>
			        				 <table class="table table-hover">
			        				 	<tr>
			                            	<th>
			                                    <input type="checkbox" id="allCheck" name="" value="">
			                                </th>
			                                <th>序号</th>
			                                <th>表名</th>
			                                <th>表名称</th>
			                                <th>操作</th>
			                            </tr>
			                            <c:forEach var="type" items="<%=typeList%>">
			                            	<tr class="tr${type}">
			                            		<td><input class="check" type="checkbox"><input class="" type="hidden" name="types" value="${type}"></td>
			                            		<td class="serialNumber"></td>
			                            		<td class="tableNameUpper">${type}</td>
			                            		<td class="tableNameCN">${type}</td>
			                            		<td class="${type}Td lastTd"><a>勾稽验证</a></td>
			                            	</tr>
			                            </c:forEach>		                                                     	                            	
			        				 </table>
								</div>
								<div class="secondDiv">
									<a class="col-md-2 btn btn-primary btn-sm exportResult text-center" href="javascript:" style="width:120px;margin-left:15px;">导出验证结果</a>
									<button type="button" class="btn btn-md btn-primary pull-right bt-15" id="formGoBackBtn"><i class="fa fa-reply-all"></i>返回</button>							
			                        <table class="table table-hover" style="table-layout:fixed;">
			                            <tr>
			                                <th width="5%">序号</th>
			                                <th width="15%">表名</th>
			                                <th width="10%">字段名</th>
			                                <th width="30%">勾稽类型条件可选值</th>
			                                <th width="10%">所属信托项目编号</th>
			                                <th width="10%">所属信托项目名称</th>
			                                <th width="10%">项目管理员</th>
			                                <th width="10%">所属部门</th>
			                            </tr>
			                            <tbody id="queboxCnt">		                            	
			                            	                            
			                            </tbody>
			                        </table>
								</div>
								

							
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
        <script src="./js/crossCheckAdmin.js"></script>
	</body>
</html>
