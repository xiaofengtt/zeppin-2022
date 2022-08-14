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
						<input name="xtzxmbh" maxlength="50" class="form-control" value="{{:xtzxmbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托证券专户账号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtzqzhzh" maxlength="60" class="form-control" value="{{:xtzqzhzh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托财产单位净值评估频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtccdwjzjspd" maxlength="10" class="form-control" value="{{:xtccdwjzjspd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托财产单位净值披露频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtccdwjzplpd" maxlength="10" class="form-control" value="{{:xtccdwjzplpd}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托财产单位净值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtccdwjz" class="form-control number" value="{{:zjsdxtccdwjz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有股票市值金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcygpje" class="form-control number" value="{{:zjsdxtxmcygpje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有债券金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcyzqje" class="form-control number" value="{{:zjsdxtxmcyzqje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有基金金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcyjjje" class="form-control number" value="{{:zjsdxtxmcyjjje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有空单的持仓余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcykddccye" class="form-control number" value="{{:zjsdxtxmcykddccye}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有多单的持仓余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcydddccye" class="form-control number" value="{{:zjsdxtxmcydddccye}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有净空单的持仓余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcyjkddccye" class="form-control number" value="{{:zjsdxtxmcyjkddccye}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有空头保证金占用：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcyktbzjzy" class="form-control number" value="{{:zjsdxtxmcyktbzjzy}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有多头保证金占用：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcydtbzjzy" class="form-control number" value="{{:zjsdxtxmcydtbzjzy}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目期货资金账号结算准备金余额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmqhzjzhjszbjye" class="form-control number" value="{{:zjsdxtxmqhzjzhjszbjye}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持有其他有价证券金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmcyqtyjzqje" class="form-control number" value="{{:zjsdxtxmcyqtyjzqje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近时点信托项目持仓比例：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjsdxtxmccbl" class="form-control number" value="{{:zjsdxtxmccbl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目成立以来净值增长率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmclyljzzzl" class="form-control number" value="{{:xmclyljzzzl}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 本年度信托累计净值增长率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bndxtljjzzzl" class="form-control number" value="{{:bndxtljjzzzl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近追加资金日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjzjzjrq" maxlength="8" class="form-control" value="{{:zjzjzjrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近追加资金金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjzjzjje" class="form-control number" value="{{:zjzjzjje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 累计追加资金金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ljzjzjje" class="form-control number" value="{{:ljzjzjje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近取回资金日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjqhzjrq" maxlength="8" class="form-control" value="{{:zjqhzjrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最近取回资金金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjqhzjje" class="form-control number" value="{{:zjqhzjje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 累计取回资金金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ljqhzjje" class="form-control number" value="{{:ljqhzjje}}" readonly />
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
						<input name="zjzcglbgrq" maxlength="8" class="form-control" value="{{:zjzcglbgrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="005003" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TJyQjglxxzq"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>期间管理信息（证劵类）</li>
                                <li><a href="./TJyQjglxxzqCheckList.jsp">数据审核</a></li>
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
