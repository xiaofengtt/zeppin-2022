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
						<span class="required hide">*</span> 交易对手编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dsbh" maxlength="40" class="form-control" value="{{:dsbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易对手全称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jydsqc" maxlength="200" class="form-control" value="{{:jydsqc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 客户类别：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xhlb" maxlength="30" class="form-control" value="{{:xhlb}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 交易对手社会信用代码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="shxydm" maxlength="40" class="form-control" value="{{:shxydm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 证件类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjlx" maxlength="60" class="form-control" value="{{:zjlx}}" readonly />
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
						<input name="zjhm" maxlength="60" class="form-control" value="{{:zjhm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 所属国别：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ssgb" maxlength="3" class="form-control" value="{{:ssgb}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 所属地区：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ssdq" maxlength="6" class="form-control" value="{{:ssdq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 企业规模：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qygm" maxlength="20" class="form-control" value="{{:qygm}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 企业性质：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qyxz" maxlength="30" class="form-control" value="{{:qyxz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 行业分类：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hyfl" maxlength="12" class="form-control" value="{{:hyfl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 行业明细：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hymx" maxlength="6" class="form-control" value="{{:hymx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 注册类别：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zclb" maxlength="20" class="form-control" value="{{:zclb}}" readonly />
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
						<input name="zczb" class="form-control number" value="{{:zczb}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 注册地址：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zcdz" maxlength="200" class="form-control" value="{{:zcdz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 法定代表人：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="fddbr" maxlength="200" class="form-control" value="{{:fddbr}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 法定代表人证件类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="fddbrzjlx" maxlength="60" class="form-control" value="{{:fddbrzjlx}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 法定代表人证件号码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="fddbrzjhm" maxlength="60" class="form-control" value="{{:fddbrzjhm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 企业成立日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qyclrq" maxlength="8" class="form-control" value="{{:qyclrq}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 登记到期日期：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="djdqrq" maxlength="8" class="form-control" value="{{:djdqrq}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 企业总资产：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qyzzc" class="form-control number" value="{{:qyzzc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 企业总负债：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qyzfz" class="form-control number" value="{{:qyzfz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 上市标志：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ssbz" maxlength="12" class="form-control" value="{{:ssbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 上市地：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ssd" maxlength="30" class="form-control" value="{{:ssd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否集团：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfjt" maxlength="3" class="form-control" value="{{:sfjt}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 集团名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jtmc" maxlength="200" class="form-control" value="{{:jtmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 三农标志：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="snbz" maxlength="3" class="form-control" value="{{:snbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 经济成份：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jjcf" maxlength="30" class="form-control" value="{{:jjcf}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 平台标志：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ptbz" maxlength="20" class="form-control" value="{{:ptbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 平台政府名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ptzfmc" maxlength="200" class="form-control" value="{{:ptzfmc}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 平台政府级别：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ptzfjb" maxlength="10" class="form-control" value="{{:ptzfjb}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 关联类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="gllx" maxlength="60" class="form-control" value="{{:gllx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 传真电话：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="czdh" maxlength="30" class="form-control" value="{{:czdh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 邮政编码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yzbm" maxlength="6" class="form-control" value="{{:yzbm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 通讯地址：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="txdz" maxlength="201" class="form-control" value="{{:txdz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="003004" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TKhJydsjg"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>交易对手（机构）</li>
                                <li><a href="./TKhJydsjgCheckList.jsp">数据审核</a></li>
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
