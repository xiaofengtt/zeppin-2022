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
	<input id="scode" type="hidden" value="004006" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
        			<input type="hidden" id="formType" name ="formType" value="TXmFfdcjsxmxx"/>
        			<div class="box-body">
        				<div class="row">
                            <ol class="breadcrumb">
                                <li>非房地产建设项目信息</li>
                                
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
										<span class="required hide">*</span> 项目具体名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmjtmc" maxlength="200" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 非房地产项目类型：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<select name="xmlx" class="form-control select-group">
											<optgroup label="基础设施">
								                <optgroup label="&nbsp;&nbsp;&nbsp;&nbsp;交通基础设施">
								                	<option value="高速公路">&nbsp;&nbsp;&nbsp;&nbsp;高速公路</option>
								                	<option value="一级公路">&nbsp;&nbsp;&nbsp;&nbsp;一级公路</option>
								                	<option value="二级及以下公路">&nbsp;&nbsp;&nbsp;&nbsp;二级及以下公路</option>
								                	<option value="铁路">&nbsp;&nbsp;&nbsp;&nbsp;铁路</option>
								                	<option value="港口、码头等水运设施">&nbsp;&nbsp;&nbsp;&nbsp;港口、码头等水运设施</option>
								                	<option value="机场等航运设施">&nbsp;&nbsp;&nbsp;&nbsp;机场等航运设施</option>
								                	<option value="高其他交通基础设施">&nbsp;&nbsp;&nbsp;&nbsp;其他交通基础设施</option>
								                </optgroup>
								                <optgroup label="&nbsp;&nbsp;&nbsp;&nbsp;市政基础设施">
								                	<option value="城市道路建设及改造">&nbsp;&nbsp;&nbsp;&nbsp;城市道路建设及改造</option>
								                	<option value="地铁、轻轨／水、电、气、热等城市公用设施">&nbsp;&nbsp;&nbsp;&nbsp;地铁、轻轨／水、电、气、热等城市公用设施</option>
								                	<option value="城市片区改造">&nbsp;&nbsp;&nbsp;&nbsp;城市片区改造</option>
								                	<option value="其它市政基础设施">&nbsp;&nbsp;&nbsp;&nbsp;其它市政基础设施</option>
								                </optgroup>
								                <option value="水利及农村基础设施">&nbsp;&nbsp;&nbsp;&nbsp;水利及农村基础设施</option>
								                <option value="环境保护设施">&nbsp;&nbsp;&nbsp;&nbsp;环境保护设施</option>
								                <option value="社会事业">&nbsp;&nbsp;&nbsp;&nbsp;社会事业</option>
								                <option value="园区">&nbsp;&nbsp;&nbsp;&nbsp;园区</option>
								                <option value="土地储备中心">&nbsp;&nbsp;&nbsp;&nbsp;土地储备中心</option>
								                <optgroup label="&nbsp;&nbsp;&nbsp;&nbsp;保障性安居工程">
								                	<option value="公共租赁住房">&nbsp;&nbsp;&nbsp;&nbsp;公共租赁住房</option>
								                	<option value="廉租住房">&nbsp;&nbsp;&nbsp;&nbsp;廉租住房</option>
								                	<option value="棚户区及垦区危房改造">&nbsp;&nbsp;&nbsp;&nbsp;棚户区及垦区危房改造</option>
								                	<option value="其他保障性安居工程">&nbsp;&nbsp;&nbsp;&nbsp;其他保障性安居工程</option>
								                </optgroup>
								                <option value="其他基础分类">&nbsp;&nbsp;&nbsp;&nbsp;其他基础分类</option>
									        </optgroup>
								            <option value="矿产能源">矿产能源</option>
								            <option value="技术改造">技术改造</option>
								            <option value="工商企业项目">工商企业项目</option>        
								            <optgroup label="其他">
								            	<option value="冶金行业">冶金行业</option>
							                	<option value="证券行业">证券行业</option>
							                	<option value="非证券行业（金融机构）">非证券行业（金融机构）</option>
							                	<option value="艺术品市场">艺术品市场</option>
							                	<option value="酒店装修">酒店装修</option>
							                	<option value="实体其他">实体其他</option>
							                	<option value="虚拟其他">虚拟其他</option>
							                	<option value="其他">其他</option>
								            </optgroup>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 批准项目文件名称：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="pzxmwjmc" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 批准项目文件编号：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="pzxmwjbh" maxlength="2000" class="form-control"/>
									</div>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目总投资金额：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmztzje" class="form-control number form-money" title="项目总投资金额" />
									</div>
								</div>
							</div>
							<div class="col-xs-6">
								<div class="form-group">
									<label class="control-label col-sm-4" title="">
										<span class="required hide">*</span> 项目资本金比例：<i class="fa icon-question hide"></i></label>
									<div class="col-sm-8">
										<input name="xmzbjbl" class="form-control number form-decimal2" title="项目资本金比例" />
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
