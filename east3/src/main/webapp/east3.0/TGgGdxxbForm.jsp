<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>北京信托EAST3.0</title>
    <%@ include file="./globalLink.jsp" %>
</head>
<body>
	<input id="scode" type="hidden" value="001003" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TGgGdxxb"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>股东信息表</li>
                                
                                <li class="active"></li>
                            </ol>
                        </div>
                        <div class="col-md-12">
                        	<button type="button" class="btn btn-md btn-primary pull-right bt-15" id="formGoBackBtn"><i class="fa fa-reply-all"></i>返回</button>
                        </div>
                        <div class="clear"></div>
        				<div class="row">
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input name="jrxkzh" maxlength="30" class="form-control" value="G10110105001163302" readonly />
        							</div>
        						</div>
        					</div>
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input name="xtjgdm" maxlength="30" class="form-control input-required" value="K0066H211000001" readonly/>
        							</div>
        						</div>
        					</div>
        				</div>
        				<div class="row">
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 信托机构名称：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input name="xtjgmc" maxlength="200" class="form-control" value="北京国际信托有限公司" readonly />
        							</div>
        						</div>
        					</div>
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 股东名称：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input name="gdmc" maxlength="200" class="form-control" value=""/>
        							</div>
        						</div>
        					</div>
        				</div>
        				<div class="row">
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 股东类型：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<select name="gdlx" class="form-control">
        									<option value="个人">个人</option>
        									<option value="金融机构">金融机构</option>
        									<option value="非金融机构">非金融机构</option>
        								</select>
        							</div>
        						</div>
        					</div>
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 股东类别：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<select name="gdlb" class="form-control">
        									<option value="大股东">大股东</option>
        									<option value="一般股东">一般股东</option>
        								</select>
        							</div>
        						</div>
        					</div>
        				</div>
        				<div class="row">
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required">*</span> 股东证件类型：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input name="gdzjlx" maxlength="60" class="form-control input-required" value=""/>
        							</div>
        						</div>
        					</div>
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required">*</span> 股东证件号码：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input name="gdzjhm" maxlength="60" class="form-control input-required" value=""/>
        							</div>
        						</div>
        					</div>
        				</div>
        				<div class="row">
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 控股方式：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<select name="kgfs" class="form-control">
        									<option value="相对控股">相对控股</option>
        									<option value="绝对控股">绝对控股</option>
        								</select>
        							</div>
        						</div>
        					</div>
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 持股比例：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<input type="number" name="cgbl" class="form-control number form-decimal2" value=""/>
        							</div>
        						</div>
        					</div>
        				</div>
        				<div class="row">
        					<div class="col-md-6">
        						<div class="form-group">
        							<label class="control-label col-sm-4" title="">
        								<span class="required hide">*</span> 股东状态：<i class="fa icon-question hide"></i></label>
        							<div class="col-sm-8">
        								<select name="gdzt" class="form-control">
        									<option value="有效">有效</option>
        									<option value="无效">无效</option>
        								</select>
        							</div>
        						</div>
        					</div>
        				</div>
        				<input name="id" type="hidden"/>
        			</div>
        			<div class="box-footer">
        				<div class="row">
        					<div class="col-sm-offset-2 col-sm-10">
        						<button type="button" class="btn btn-md btn-primary" data-toggle="modal" data-target="#myModal"><i class="fa fa-check"></i>保存</button>&nbsp;
        						<button type="button" class="btn btn-md btn-default" id="btnCancel"><i class="fa fa-reply-all"></i>取消</button>
        					</div>
        				</div>
        			</div>
        		</form>
        	</div>
        </div><%-- content --%>
    </div>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-hidden="true">
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
    <script src="./js/globalForm.js"></script>
</body>
</html>
