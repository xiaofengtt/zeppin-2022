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
						<span class="required hide">*</span> 信托机构代码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtjgdm" maxlength="30" class="form-control" value="{{:xtjgdm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 金融许可证号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jrxkzh" maxlength="30" class="form-control" value="{{:jrxkzh}}" readonly />
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
						<input name="xtzxmbm" maxlength="50" class="form-control" value="{{:xtzxmbm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 运用合同编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yyhtbh" maxlength="40" class="form-control" value="{{:yyhtbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 运用合同名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yyhtmc" maxlength="200" class="form-control" value="{{:yyhtmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 业务种类：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ywzl" maxlength="120" class="form-control" value="{{:ywzl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 对手方类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dsflx" maxlength="15" class="form-control" value="{{:dsflx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 对手方编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dsfbh" maxlength="60" class="form-control" value="{{:dsfbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合同签订日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="htqdrq" maxlength="8" class="form-control" value="{{:htqdrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 首次放款日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="scfkrq" maxlength="8" class="form-control" value="{{:scfkrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合同到期日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="htdqrq" maxlength="8" class="form-control" value="{{:htdqrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合同签订金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="htqdje" class="form-control number" value="{{:htqdje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合同签约利率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="htqyll" class="form-control number form-decimal3" value="{{:htqyll}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易对手年化资金成本：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jydsnhzjcb" maxlength="18" class="form-control digits" value="{{:jydsnhzjcb}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 投向行业：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="txhy" maxlength="12" class="form-control" value="{{:txhy}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 投向行业明细：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="txhymx" maxlength="6" class="form-control" value="{{:txhymx}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 资金运用地区：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjtxdq" maxlength="6" class="form-control" value="{{:zjtxdq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 资金运用方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjyyfs" maxlength="200" class="form-control" value="{{:zjyyfs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 还款方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hkfs" maxlength="20" class="form-control" value="{{:hkfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 提前终止：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tqzz" maxlength="3" class="form-control" value="{{:tqzz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 退出方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tcfs" maxlength="20" class="form-control" value="{{:tcfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 资产五级分类：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zcwjfl" maxlength="10" class="form-control" value="{{:zcwjfl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 标的资产类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bdzclx" maxlength="20" class="form-control" value="{{:bdzclx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 标的资产名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bdzcmc" maxlength="200" class="form-control" value="{{:bdzcmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 标的资产入账价值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bdzcrzjz" class="form-control number" value="{{:bdzcrzjz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 原始合同号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yshth" maxlength="40" class="form-control" value="{{:yshth}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 原始对手方类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ysdsflx" maxlength="6" class="form-control" value="{{:ysdsflx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 原始对手方编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ysdsfbh" maxlength="40" class="form-control" value="{{:ysdsfbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 投融资余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="trzye" class="form-control number" value="{{:trzye}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="004004" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TXmXtzjyyhtxx"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>信托资金运用合同信息</li>
                                <li><a href="./TXmXtzjyyhtxxCheckList.jsp">数据审核</a></li>
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
