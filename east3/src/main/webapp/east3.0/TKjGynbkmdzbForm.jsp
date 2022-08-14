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
	<input id="scode" type="hidden" value="002004" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TKjGynbkmdzb"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>固有内部科目对照表</li>
                                
                                <li class="active"></li>
                            </ol>
                        </div>
                        <div class="col-md-12">
                        	<button type="button" class="btn btn-md btn-primary pull-right bt-15" id="formGoBackBtn"><i class="fa fa-reply-all"></i>返回</button>
                        </div>
                        <div class="clear"></div>
        				<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jrxkzh" maxlength="30" class="form-control" value="G10110105001163302" readonly />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 固有会计科目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gykjkmbh" maxlength="60" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 固有会计科目名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gykjkmmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 固有会计科目级次：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gykjkmjc" maxlength="18" class="form-control digits"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 上级科目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="sjkmbh" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 上级科目名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="sjkmmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 归属业务大类：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="gsywdl" class="form-control digits">
											<option value="1">资产</option>
											<option value="2">负债</option>
											<option value="3">所有者权益</option>
											<option value="4">损益</option>
											<option value="5">资产负债共同类</option>
											<option value="6">表外</option>
											<option value="7">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 归属业务子类：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="gsywzl" class="form-control form-checkbox" multiple="multiple">
											<option value="1">各项贷款</option>
											<option value="2">投资</option>
											<option value="3">拆借资金（拆出）</option>
											<option value="4">各项垫款</option>
											<option value="5">各项准备金</option>
											<option value="6">各项存款</option>
											<option value="7">拆借资金（拆入）</option>
											<option value="8">发行债券</option>
											<option value="9">担保（保函）</option>
											<option value="10">承兑票据</option>
											<option value="11">委托贷款</option>
											<option value="12">信用证</option>
											<option value="13">理财业务</option>
											<option value="14">交易</option>
											<option value="15">利息收入</option>
											<option value="16">中间业务收入</option>
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
