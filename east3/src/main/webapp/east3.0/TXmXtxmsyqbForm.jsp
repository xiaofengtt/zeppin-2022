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
	<input id="scode" type="hidden" value="004010" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TXmXtxmsyqb"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>信托项目受益权表</li>
                                
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
										<span class="required hide">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgdm" maxlength="30" class="form-control" value="K0066H211000001" readonly/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jrxkzh" maxlength="30" class="form-control input-required" value="G10110105001163302" readonly />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托机构名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgmc" maxlength="200" class="form-control" value="北京国际信托有限公司" readonly />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 受益权编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="syqbh" maxlength="100" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托项目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="xtxmbm" class="form-control" data-live-search="true" id="xtxmbhSelect">
        									<c:forEach items="${sessionScope.productMap}" var="productMap">
	        									<option value="${productMap.key}">${productMap.key}-${productMap.value}</option>
	        								</c:forEach>	
        								</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托子项目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtzxmbm" maxlength="50" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 受益权分级：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="syqlx" class="form-control">
											<option value="优先">优先</option>
											<option value="中间">中间</option>
											<option value="劣后">劣后</option>
											<option value="普通">普通</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开始日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="syqlbqsr" maxlength="8" class="form-control form-date"  />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 结束日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="syqlbdqr" maxlength="8" class="form-control form-date"  />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 运行方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="syqyxfs" class="form-control">
											<option value="封闭式">封闭式</option>
											<option value="开放式">开放式</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开放频度：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="syqkfpd" class="form-control">
											<option value="日">日</option>
											<option value="周">周</option>
											<option value="旬">旬</option>
											<option value="月度">月度</option>
											<option value="季度">季度</option>
											<option value="年度">年度</option>
											<option value="其他频度">其他频度</option>
											<option value="不定期">不定期</option>
											<option value="无">无</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 分配频度：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="syfppd" class="form-control">
											<option value="到期一次付清">到期一次付清</option>
											<option value="周">周</option>
											<option value="旬">旬</option>
											<option value="月度">月度</option>
											<option value="季度">季度</option>
											<option value="年度">年度</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 分配方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="syfpfs" class="form-control">
											<option value="到期还本付息">到期还本付息</option>
											<option value="分期付息到期还本">分期付息到期还本</option>
											<option value="分期还本付息">分期还本付息</option>
											<option value="分期还本到期付息">分期还本到期付息</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 分红方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="fhfs" class="form-control">
											<option value="现金">现金</option>
											<option value="非现金">非现金</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 收益率计提方式：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="syljtfs" class="form-control">
											<option value="固定">固定</option>
											<option value="浮动">浮动</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 预期收益率：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="syqyqsyl" class="form-control number form-decimal2" title="预期收益率" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 预期收益率说明：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="yqsylsm" maxlength="1000" class="form-control"/>
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
