<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>北京信托EAST3.0</title>
    <%@ include file="./globalLink.jsp" %>

    <%-- add --%>
    <script type="text/template" id="boxTpl">
        <div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jrxkzh" maxlength="30" class="form-control" value="{{:jrxkzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtjgdm" maxlength="30" class="form-control" value="{{:xtjgdm}}" readonly />
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
						<input name="xtjgmc" maxlength="200" class="form-control" value="{{:xtjgmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 委托人编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wtrbh" maxlength="60" class="form-control" value="{{:wtrbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 委托人类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wtrlx" maxlength="15" class="form-control" value="{{:wtrlx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 受益权编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="syqbh" maxlength="100" class="form-control" value="{{:syqbh}}" readonly />
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
						<input name="xtxmbh" maxlength="40" class="form-control" value="{{:xtxmbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托子项目编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtzxmbm" maxlength="50" class="form-control" value="{{:xtzxmbm}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托项目全称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtxmqc" maxlength="200" class="form-control" value="{{:xtxmqc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 内部合同编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="nbhtbh" maxlength="40" class="form-control" value="{{:nbhtbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 文本合同编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wbhtbh" maxlength="40" class="form-control" value="{{:wbhtbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托合同名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wthtmc" maxlength="200" class="form-control" value="{{:wthtmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 销售方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xsfs" maxlength="60" class="form-control" value="{{:xsfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 销售商名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xssmc" maxlength="200" class="form-control" value="{{:xssmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交付资产类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jfzclx" maxlength="10" class="form-control" value="{{:jfzclx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 推介地信息：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wtrqc" maxlength="200" class="form-control" value="{{:wtrqc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合同签订地：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="htqdd" maxlength="6" class="form-control" value="{{:htqdd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 签订日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qdrq" maxlength="8" class="form-control" value="{{:qdrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 委托金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wtje" class="form-control number" value="{{:wtje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 首次资金到账日：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sczjdzr" maxlength="8" class="form-control" value="{{:sczjdzr}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 委托资金性质：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="wtzjxz" maxlength="12" class="form-control" value="{{:wtzjxz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否金融机构理财产品：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfjrjglccp" maxlength="3" class="form-control" value="{{:sfjrjglccp}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 理财产品编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="clcpbh" maxlength="40" class="form-control" value="{{:clcpbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="004003" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TXmXtzjmjhtxx"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>信托资金募集合同信息</li>
                                <li><a href="./TXmXtzjmjhtxxCheckList.jsp">数据审核</a></li>
                                <li class="active">审核</li>
                            </ol>
                        </div>
                        <div id="queboxCnt">

                        </div>
                    </div>
        			<div class="box-footer">
        				<div class="row">
        					<div class="col-sm-offset-2 col-sm-10">
                                <button type="button" class="btn btn-md btn-primary checkBtn" data-toggle="modal" data-target="#myModal" data-status="checked"><i class="fa fa-check"></i>审核通过</button>&nbsp;<button type="button" class="btn btn-md btn-primary checkBtn" data-toggle="modal" data-target="#myModal" data-status="nopass"><i class="fa fa-check"></i>审核不通过</button>&nbsp;
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
                    <button type="button" class="btn btn-primary" id="btnSubmit" data-status="">提交</button>
                </div>
            </div>
            <!-- /.modal-content -->
        </div>
        <!-- /.modal -->
    </div>
    <script src="./js/jsrender.js"></script>
    <script src="./js/globalCheck.js"></script>
</body>
</html>
