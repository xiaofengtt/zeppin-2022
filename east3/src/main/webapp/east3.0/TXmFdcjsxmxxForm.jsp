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
	<input id="scode" type="hidden" value="004005" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TXmFdcjsxmxx"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>房地产建设项目信息</li>
                                
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
										<span class="required">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jrxkzh" maxlength="30" class="form-control input-required" value="G10110105001163302" readonly />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtjgdm" maxlength="30" class="form-control" value="K0066H211000001" readonly/>
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
										<select name="xtxmbh" class="form-control" data-live-search="true" id="xtxmbhSelect">
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
										<input name="xtzxmbm" maxlength="50" class="form-control input-required"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required">*</span> 运用合同编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="trzhtbh" maxlength="40" class="form-control input-required"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 房地产项目类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="fdcxmlx" class="form-control select-group">
											<optgroup label="住宅">
												<option value="普通住房">普通住房</option>
												<option value="低密度高端住房">低密度高端住房</option>
											</optgroup>
											<optgroup label="商用">
												<option value="商用房">商用房</option>
												<option value="商住两用房">商住两用房</option>
												<option value="办公用房">办公用房</option>
												<option value="其他商用房">其他商用房</option>
											</optgroup>
											<optgroup label="工业">
												<option value="园区房">园区房</option>
												<option value="在建工程">在建工程</option>
												<option value="其他工业房">其他工业房</option>
											</optgroup>
											<optgroup label="保障">
												<option value="公共租赁房">公共租赁房</option>
												<option value="廉租房">廉租房</option>
												<option value="棚户区及垦区危房改造">棚户区及垦区危房改造</option>
												<option value="经适房">经适房</option>
												<option value="限价商品住房">限价商品住房</option>
												<option value="农村危房改造">农村危房改造</option>
												<option value="游牧民定居工程">游牧民定居工程</option>
												<option value="其他保障房">其他保障房</option>
												<option value="土地储备">土地储备</option>
												<option value="别墅">别墅</option>
											</optgroup>
											<option value="其他">其他</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目具体名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmjtmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目地理位置：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmdlwz" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目所在地邮编：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xxszdyb" maxlength="6" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开发商组织机构代码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="kfszzjgdm" maxlength="40" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 开发商资质：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="kfszz" class="form-control">
											<option value="一级">一级</option>
											<option value="二级">二级</option>
											<option value="暂定二级">暂定二级</option>
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
										<span class="required hide">*</span> 总投资金额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="ztzje" class="form-control number form-money" title="总投资金额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 总建筑面积：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zjzmj" class="form-control number"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 总占地面积：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zzdmj" class="form-control number"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 容积率：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="rjl" class="form-control number"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 资本金比例：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="zbjbl" class="form-control number form-decimal2" title="资本金比例" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否办理了国有土地使用证：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="gytdsyz" class="form-control">
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
										<span class="required hide">*</span> 国有土地使用证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="gytdsyzh" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否办理了建设用地规划许可证：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="jsydghxkz" class="form-control">
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
										<span class="required hide">*</span> 建设用地规划许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jsydghxkzh" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否办理了建筑工程规划许可证：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="jzgcghxkz" class="form-control">
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
										<span class="required hide">*</span> 建筑工程规划许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jzgcghxkzh" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否办理了建筑工程施工许可证：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="jzgcsgxkz" class="form-control">
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
										<span class="required hide">*</span> 建筑工程施工许可证号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="jzgcsgxkzh" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目预计的贷款总额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmyjddkze" class="form-control number form-money" title="项目预计的贷款总额" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目是否已进入施工阶段：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="xmsfyjrsgjd" class="form-control">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目完成进度：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmwgjd" class="form-control number"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 是否取得预售许可证：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="sfqdysxkz" class="form-control">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 预售许可证号码：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="ysxkzhm" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目是否已开始销售：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="xmsfyksxs" class="form-control">
											<option value="是">是</option>
											<option value="否">否</option>
										</select>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目销售进度：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmxsjd" class="form-control number form-decimal2" title="项目销售进度" />
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目销售回款总额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmxshkze" class="form-control number form-money" title="项目销售回款总额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 销售回款资金监控安排：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="xshkzjjkap" class="form-control">
											<option value="未监控">未监控</option>
											<option value="信托公司直接监控">信托公司直接监控</option>
											<option value="委托银行监控">委托银行监控</option>
											<option value="其他机构监控">其他机构监控</option>											
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 信托公司对项目的评级：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xtgsdxmdpj" maxlength="200" class="form-control"/>
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
