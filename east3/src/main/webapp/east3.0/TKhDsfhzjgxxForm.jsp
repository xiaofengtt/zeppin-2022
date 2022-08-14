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
	<input id="scode" type="hidden" value="003006" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TKhDsfhzjgxx"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>第三方合作机构信息</li>
                                
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
										<span class="required">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgdm" maxlength="30" class="form-control input-required" value="K0066H211000001" readonly/>
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
										<span class="required">*</span> 第三方机构编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="dsfjgbh" maxlength="40" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 社会信用代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="shxydm" maxlength="40" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 第三方机构类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="dsflx" class="form-control">
											<option value="证券期货投资咨询">证券期货投资咨询</option>
											<option value="公募基金管理人">公募基金管理人</option>
											<option value="私募基金管理人">私募基金管理人</option>
											<option value="资产管理资格">资产管理资格</option>
											<option value="银行">银行</option>
											<option value="信托">信托</option>
											<option value="保险">保险</option>
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
										<span class="required hide">*</span> 第三方名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="dsfmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 证件类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="zjlx" class="form-control">
											<option value="统一社会信用代码">统一社会信用代码</option>
											<option value="组织机构代码">组织机构代码</option>
											<option value="金融许可证">金融许可证</option>
											<option value="营业执照号">营业执照号</option>
											<option value="税务登记号">税务登记号</option>
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
										<span class="required hide">*</span> 证件号码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zjdm" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 法定代表人：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="fddbr" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 法定代表人证件类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="fddbrzjlx" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 法定代表人证件号码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="fddbrzjhm" maxlength="60" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 注册资本：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zczb" class="form-control number form-money"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 内部评级：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="nbpj" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 成立日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="clrq" maxlength="8" class="form-control form-date"  />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否登记：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="sfdj" class="form-control">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 登记类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="djlx" class="form-control">
											<option value="银行">银行</option>
											<option value="证券公司">证券公司</option>
											<option value="基金公司或基金子公司">基金公司或基金子公司</option>
											<option value="保险公司">保险公司</option>
											<option value="信托公司">信托公司</option>
											<option value="其他金融机构">其他金融机构</option>
											<option value="普通法人机构">普通法人机构</option>
											<option value="普通非法人机构">普通非法人机构</option>
											<option value="政府类">政府类</option>
											<option value="合伙人">合伙人</option>
											<option value="私募基金管理人">私募基金管理人</option>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 登记日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="djrq" maxlength="8" class="form-control form-date"  />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 登记编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="djbh" maxlength="40" class="form-control"/>
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
