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
						<span class="required hide">*</span> 交易对手编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jydsbh" maxlength="60" class="form-control" value="{{:jydsbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 资产管理报告频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zcglbgpd" maxlength="10" class="form-control" value="{{:zcglbgpd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最新资产管理报告日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zxzcglbgrq" maxlength="8" class="form-control" value="{{:zxzcglbgrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否有保证金：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfybzzj" maxlength="3" class="form-control" value="{{:sfybzzj}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 保证金（资金归集）到位履约情况：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bzjdwlyqk" maxlength="40" class="form-control" value="{{:bzjdwlyqk}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点融资余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdrzye" class="form-control number" value="{{:zjsdrzye}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否发生展期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sffszq" maxlength="3" class="form-control" value="{{:sffszq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近一次展期的日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjyczqdrq" maxlength="8" class="form-control" value="{{:zjyczqdrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 展期次数：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zqcs" maxlength="18" class="form-control digits" value="{{:zqcs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否发生借新还旧：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sffsjxhj" maxlength="3" class="form-control" value="{{:sffsjxhj}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否未按期还息：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfwaqhx" maxlength="3" class="form-control" value="{{:sfwaqhx}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 拖欠利息天数：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tqlxts" maxlength="18" class="form-control digits" value="{{:tqlxts}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 拖欠利息金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tqlxje" class="form-control number" value="{{:tqlxje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否存在逾期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfczyq" maxlength="3" class="form-control" value="{{:sfczyq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 累计逾期天数：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ljyqts" maxlength="18" class="form-control digits" value="{{:ljyqts}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 拖欠本金金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tqbjje" class="form-control number" value="{{:tqbjje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 抵（质）押率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dzyl" class="form-control number" value="{{:dzyl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近期信托资产五级分类：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjqxtzcwjfl" maxlength="10" class="form-control" value="{{:zjqxtzcwjfl}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点投资余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdtzye" class="form-control number" value="{{:zjsdtzye}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 投资标的最新评估日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tzbddzxpgrq" maxlength="8" class="form-control" value="{{:tzbddzxpgrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 投资标的最新评估价值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tzbddzxpgjz" class="form-control number" value="{{:tzbddzxpgjz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 实际兑付资金来源：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sjdfzjly" maxlength="40" class="form-control" value="{{:sjdfzjly}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="005004" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TJyQjglxxfzq"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>期间管理信息（非证劵类）</li>
                                <li><a href="./TJyQjglxxfzqCheckList.jsp">数据审核</a></li>
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
