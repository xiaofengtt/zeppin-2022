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
    	<input id="scode" type="hidden" value="008001" />
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
                        <li class="active">数据导入</li>
                    </ol>
                </div>
                <div class="contain">
        			<div class="contain-right">
        				<div class="main-contain">	
        				<c:choose>
							<c:when test="${sessionScope.currentOperator.roleName == 'superAdmin'}">  
								<div class="row Multi" style="padding-left:15px; padding-right:15px;">
		        					<a class="col-md-2 btn btn-primary MultiExport" href="javascript:" style="width:100px;float:right;">多表导入</a>
		        					<div id="MultiuploadFileId" class="MultiuploadFile" data-type="" style="display:none;"></div>
													<input type="hidden" name="file" id="MultiuploadFile" value="">
		        				</div>
							</c:when>
						</c:choose>					
	        					
							<!-- 列表 -->
							<div class="row" style="padding-left:15px; padding-right:15px;">
								
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
		                            <tbody id="queboxCnt">
		                            	<c:forEach var="type" items="<%=typeList%>">
		                            	<tr class="tr${type}">
		                            		<td><input class="check" type="checkbox"></td>
		                            		<td class="serialNumber"></td>
		                            		<td class="tableNameUpper">${type}</td>
		                            		<td class="tableNameCN">${type}</td>
		                            		<td><a class="btn btn-sm btn-primary mr-10 fileOutput" data-type="${type}" href="javascript:">导出模板</a>
		                            			<a class="btn btn-sm btn-primary mr-10" data-type="${type}" onclick="fileAdd(this)">导入数据</a>
		                            			<div id="uploadFileId${type}" class="uploadFile" data-type="${type}" style="display:none;"></div>
												<input type="hidden" name="file" id="uploadFile${type}" value="">
		                            		</td>
		                            	</tr>
		                            	</c:forEach>
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
        <!-- 错误枚举 -->
    	<%@ include file="./errorTable.jsp" %>
        <script src="./js/inputList.js"></script>
	</body>
</html>
