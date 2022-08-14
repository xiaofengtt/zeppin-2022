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
						<input name="xtxmbm" maxlength="40" class="form-control" value="{{:xtxmbm}}" readonly />
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
						<span class="required hide">*</span> 质或抵押物编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zhdywbh" maxlength="60" class="form-control form-decimal2" value="{{:zhdywbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 担保合同号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dbhth" maxlength="40" class="form-control" value="{{:dbhth}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质或抵押物名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zhdywmc" maxlength="100" class="form-control" value="{{:zhdywmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质或抵押物类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zhdywlx" maxlength="300" class="form-control" value="{{:zhdywlx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质或抵押物账面价值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zhdywzmjz" class="form-control number" value="{{:zhdywzmjz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 币种：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bz" maxlength="3" class="form-control" value="{{:bz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托公司认定价值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yxrdjz" class="form-control number" value="{{:yxrdjz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 评估价值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="pgjz" class="form-control number" value="{{:pgjz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 评估日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="pgrq" maxlength="8" class="form-control" value="{{:pgrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 评估机构名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="pgjgmc" maxlength="200" class="form-control" value="{{:pgjgmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质或抵押率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zhdyl" class="form-control number" value="{{:zhdyl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 抵押物所有权人名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dywsyqrmc" maxlength="200" class="form-control" value="{{:dywsyqrmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 已抵押价值：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ydyjz" class="form-control number" value="{{:ydyjz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 登记日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="djrq" maxlength="8" class="form-control" value="{{:djrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 实物收取日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="swsqrq" maxlength="8" class="form-control" value="{{:swsqrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 登记机构：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="djjg" maxlength="40" class="form-control" value="{{:djjg}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 操作机构：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="czjg" maxlength="30" class="form-control" value="{{:czjg}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 担保起始日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dbqsrq" maxlength="8" class="form-control" value="{{:dbqsrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 担保到期日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dbdqrq" maxlength="8" class="form-control" value="{{:dbdqrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押票证账号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zypzzh" maxlength="60" class="form-control" value="{{:zypzzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押票证类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zypzlx" maxlength="60" class="form-control" value="{{:zypzlx}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押票证号码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zypzhm" maxlength="300" class="form-control" value="{{:zypzhm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押票证金额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zypzje" class="form-control number" value="{{:zypzje}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押物保管方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zywbgfs" maxlength="60" class="form-control" value="{{:zywbgfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押物评估方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zywpgfs" maxlength="40" class="form-control" value="{{:zywpgfs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押票证签发机构：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zypzqfjg" maxlength="200" class="form-control" value="{{:zypzqfjg}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 质押票证开立日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zypzklrq" maxlength="8" class="form-control" value="{{:zypzklrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 保险单号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bxdh" maxlength="60" class="form-control" value="{{:bxdh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 核保日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hbrq" maxlength="8" class="form-control" value="{{:hbrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 权证登记号码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qzdjhm" maxlength="30" class="form-control" value="{{:qzdjhm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 权证名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qzmc" maxlength="300" class="form-control" value="{{:qzmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 权证有效到期日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qzyxdqrq" maxlength="8" class="form-control" value="{{:qzyxdqrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 登记有效终止日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="djyxzzrq" maxlength="8" class="form-control" value="{{:djyxzzrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否纳入表外核算：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfnrbwhs" maxlength="3" class="form-control" value="{{:sfnrbwhs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 表外核算开始日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bwhsksrq" maxlength="8" class="form-control" value="{{:bwhsksrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="004009" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TXmXtdzywb"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>信托抵质押物表</li>
                                <li><a href="./TXmXtdzywbCheckList.jsp">数据审核</a></li>
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
