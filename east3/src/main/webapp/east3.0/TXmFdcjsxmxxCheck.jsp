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
						<input name="xtzxmbm" maxlength="50" class="form-control" value="{{:xtzxmbm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 运用合同编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="trzhtbh" maxlength="40" class="form-control" value="{{:trzhtbh}}" readonly />
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
						<input name="fdcxmlx" maxlength="60" class="form-control" value="{{:fdcxmlx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目具体名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmjtmc" maxlength="200" class="form-control" value="{{:xmjtmc}}" readonly />
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
						<input name="xmdlwz" maxlength="200" class="form-control" value="{{:xmdlwz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目所在地邮编：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xxszdyb" maxlength="6" class="form-control" value="{{:xxszdyb}}" readonly />
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
						<input name="kfszzjgdm" maxlength="40" class="form-control" value="{{:kfszzjgdm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 开发商资质：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="kfszz" maxlength="12" class="form-control" value="{{:kfszz}}" readonly />
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
						<input name="ztzje" class="form-control number" value="{{:ztzje}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 总建筑面积：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zjzmj" class="form-control number" value="{{:zjzmj}}" readonly />
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
						<input name="zzdmj" class="form-control number" value="{{:zzdmj}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 容积率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="rjl" class="form-control number" value="{{:rjl}}" readonly />
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
						<input name="zbjbl" class="form-control number form-decimal2" value="{{:zbjbl}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否办理了国有土地使用证：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="gytdsyz" maxlength="3" class="form-control" value="{{:gytdsyz}}" readonly />
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
						<input name="gytdsyzh" maxlength="2000" class="form-control" value="{{:gytdsyzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否办理了建设用地规划许可证：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jsydghxkz" maxlength="3" class="form-control" value="{{:jsydghxkz}}" readonly />
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
						<input name="jsydghxkzh" maxlength="2000" class="form-control" value="{{:jsydghxkzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否办理了建筑工程规划许可证：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jzgcghxkz" maxlength="3" class="form-control" value="{{:jzgcghxkz}}" readonly />
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
						<input name="jzgcghxkzh" maxlength="2000" class="form-control" value="{{:jzgcghxkzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否办理了建筑工程施工许可证：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jzgcsgxkz" maxlength="3" class="form-control" value="{{:jzgcsgxkz}}" readonly />
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
						<input name="jzgcsgxkzh" maxlength="2000" class="form-control" value="{{:jzgcsgxkzh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目预计的贷款总额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmyjddkze" class="form-control number" value="{{:xmyjddkze}}" readonly />
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
						<input name="xmsfyjrsgjd" maxlength="3" class="form-control" value="{{:xmsfyjrsgjd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目完成进度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmwgjd" class="form-control number" value="{{:xmwgjd}}" readonly />
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
						<input name="sfqdysxkz" maxlength="3" class="form-control" value="{{:sfqdysxkz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 预售许可证号码：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ysxkzhm" maxlength="2000" class="form-control" value="{{:ysxkzhm}}" readonly />
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
						<input name="xmsfyksxs" maxlength="3" class="form-control" value="{{:xmsfyksxs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目销售进度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmxsjd" class="form-control number form-decimal2" value="{{:xmxsjd}}" readonly />
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
						<input name="xmxshkze" class="form-control number" value="{{:xmxshkze}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 销售回款资金监控安排：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xshkzjjkap" maxlength="30" class="form-control" value="{{:xshkzjjkap}}" readonly />
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
						<input name="xtgsdxmdpj" maxlength="200" class="form-control" value="{{:xtgsdxmdpj}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
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
                                <li><a href="./TXmFdcjsxmxxCheckList.jsp">数据审核</a></li>
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
