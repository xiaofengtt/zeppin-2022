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
						<span class="required hide">*</span> 交易流水编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jylsbh" maxlength="40" class="form-control" value="{{:jylsbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易时间：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jysj" maxlength="20" class="form-control" value="{{:jysj}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托业务标识：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ywbz" maxlength="20" class="form-control" value="{{:ywbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 资金方向：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjfx" maxlength="6" class="form-control" value="{{:zjfx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托项目编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtxmbh" maxlength="40" class="form-control" value="{{:xtxmbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托子项目编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtzxmbh" maxlength="50" class="form-control" value="{{:xtzxmbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托合同编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xthtbh" maxlength="40" class="form-control" value="{{:xthtbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 受益凭据编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sypjbh" maxlength="80" class="form-control" value="{{:sypjbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 转让登记编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zrdjbh" maxlength="40" class="form-control" value="{{:zrdjbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 受益人类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="srrlx" maxlength="15" class="form-control" value="{{:srrlx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 受益人编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="srrbh" maxlength="60" class="form-control" value="{{:srrbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 分红方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="fhfs" maxlength="10" class="form-control" value="{{:fhfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 币种代码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hsbz" maxlength="3" class="form-control" value="{{:hsbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 单位净值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dwjz" class="form-control number form-decimal4" value="{{:dwjz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jyje" class="form-control number" value="{{:jyje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易份额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jyfe" class="form-control number" value="{{:jyfe}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易费用：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jyfy" class="form-control number" value="{{:jyfy}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 收益金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="syje" class="form-control number" value="{{:syje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 本方账号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bfzh" maxlength="60" class="form-control" value="{{:bfzh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 本方开户行名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bfkhhmc" maxlength="200" class="form-control" value="{{:bfkhhmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 本方开户行编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bfkhhbh" maxlength="30" class="form-control" value="{{:bfkhhbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 对方账号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dfzh" maxlength="60" class="form-control" value="{{:dfzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 对方开户行名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dfkhhmc" maxlength="200" class="form-control" value="{{:dfkhhmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 对方开户行编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dfkhhbh" maxlength="30" class="form-control" value="{{:dfkhhbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 冲补抹标志：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="cbmbz" maxlength="6" class="form-control" value="{{:cbmbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="005001" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TJyXtzjmjjfpl"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>信托资金募集及分配流水</li>
                                <li><a href="./TJyXtzjmjjfplCheckList.jsp">数据审核</a></li>
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
