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
		<input id="scode" type="hidden" value="009003" />
        <%@ include file="./header.jsp" %>

        <div class="clearfix" id="main">
            <%@ include file="./super-menu.jsp" %>

            <div id="content">
                <div class="">
                    <ol class="breadcrumb">
                        <li class="active">查看日志</li>
                    </ol>
                </div>
                <div class="contain">
        			<div class="contain-right">
        				<div class="main-contain">
							<div class="row">
	        					<div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label col-sm-4" title="">表名</label>
	        							<div class="col-sm-8">
	        								<select name="datatype" class="form-control operableTable">
	        									<option value="">请选择</option>
	        								</select>
	        							</div>
	        						</div>
	        					</div>
	        					<div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label col-sm-4" title="">
	        								操作人员</label>
	        							<div class="col-sm-8">
	        								<select class="form-control" id="operatorSelect" name="creator">
	                                            <option value="">请选择</option>
	                                        </select>
	        							</div>
	        						</div>
	        					</div>
	        				</div>
	        				<div class="row">
	        					<div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label col-sm-4" title="">起始日期</label>
	        							<div class="col-sm-8">
	        								<input name="starttime" maxlength="10" class="form-control form-date" value=""/>
	        							</div>
	        						</div>
	        					</div>
	        					<div class="col-md-5">
	        						<div class="form-group">
	        							<label class="control-label col-sm-4" title="">
	        								截止日期</label>
	        							<div class="col-sm-8">
	        								<input name="endtime" maxlength="10" class="form-control form-date" value=""/>
	        							</div>
	        						</div>
	        					</div>
	        					<div class="col-md-2 btn btn-primary btn-sm" onclick="getList();" style="width:100px;">查询</div>
	        				</div>	
							<!-- 列表 -->
							<div class="row" style="padding-left:15px; padding-right:15px;">
		                        <table class="table table-hover">
		                            <tr>
		                                <th>序号</th>
		                                <th>表名</th>
		                                <th>表名称</th>
		                                <th>类型</th>
		                                <th>说明</th>
		                                <th>操作人员</th>
		                                <th>操作时间</th>
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
        <script src="./js/modifyLog.js"></script>
	</body>
</html>
