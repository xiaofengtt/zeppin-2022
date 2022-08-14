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
	<input id="scode" type="hidden" value="004011" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TXmXtxmqsxx"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>信托项目清算信息</li>
                                
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
										<span class="required">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgdm" maxlength="30" class="form-control input-required" value="K0066H211000001" readonly/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jrxkzh" maxlength="30" class="form-control" value="G10110105001163302" readonly />
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
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 信托子项目编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtzxmbmh" maxlength="50" class="form-control input-required"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 清算日期：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="qsrq" maxlength="8" class="form-control form-date"  />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否按约定日期清算：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="sfaydrqqs" class="form-control">
											<option value="提前">提前</option>
											<option value="正常">正常</option>
											<option value="推迟">推迟</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 实收信托余额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="ssxtye" class="form-control number form-money" title="实收信托余额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托本金累计给付额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtbjljjfe" class="form-control number form-money" title="信托本金累计给付额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托收益累计分配额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtsyljfpe" class="form-control number form-money" title="信托收益累计分配额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 投资顾问累计基本报酬：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="tzgwljjbbc" class="form-control number form-money" title="投资顾问累计基本报酬" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 投资顾问累计业绩报酬：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="tzgwljyjbc" class="form-control number form-money" title="投资顾问累计业绩报酬" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 受托人累计基本报酬：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="strljjbbc" class="form-control number form-money" title="受托人累计基本报酬" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 受托人累计业绩报酬：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="strljyjbc" class="form-control number form-money" title="受托人累计业绩报酬" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托费用：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtfy" class="form-control number form-money" title="信托费用" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托费用率：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtfyl" class="form-control number form-decimal3" title="信托费用率" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 实际收益：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="sjsy" class="form-control number form-money" title="实际收益" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 实际收益率：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="sjsyl" class="form-control number form-decimal2" title="实际收益率" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 实际信托报酬率：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="sjxtbcl" class="form-control number form-decimal2" title="实际信托报酬率" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 损失金额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="sunshie" class="form-control number form-money" title="损失金额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 涉诉金额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="shesuje" class="form-control number form-money" title="涉诉金额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 赔付金额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="pfje" class="form-control number form-money" title="赔付金额" />
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
