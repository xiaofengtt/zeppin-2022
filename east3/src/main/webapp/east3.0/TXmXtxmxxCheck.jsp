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
						<input name="xtzxmbh" maxlength="50" class="form-control" value="{{:xtzxmbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托项目全称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtxmqc" maxlength="200" class="form-control" value="{{:xtxmqc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托业务分类：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtywfl" maxlength="30" class="form-control" value="{{:xtywfl}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 单一集合标志：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="dyjhbz" maxlength="6" class="form-control" value="{{:dyjhbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目成立日：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmclr" maxlength="8" class="form-control" value="{{:xmclr}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 预计到期日：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yjdqr" maxlength="8" class="form-control" value="{{:yjdqr}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目终止日：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmzzr" maxlength="8" class="form-control" value="{{:xmzzr}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 期限说明：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="qxsm" maxlength="1000" class="form-control" value="{{:qxsm}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 设立方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="slfs" maxlength="12" class="form-control" value="{{:slfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 受益方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="syfs" maxlength="12" class="form-control" value="{{:syfs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托功能：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtgn" maxlength="9" class="form-control" value="{{:xtgn}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 业务特征：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ywtz" maxlength="60" class="form-control" value="{{:ywtz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 受托人主要职责：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="strzz" maxlength="60" class="form-control" value="{{:strzz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 管理运用和处分方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="glyyhcffs" maxlength="30" class="form-control" value="{{:glyyhcffs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 运行方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yxfs" maxlength="10" class="form-control" value="{{:yxfs}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 开放频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="kfpd" maxlength="10" class="form-control" value="{{:kfpd}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目结构属性：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jgsx" maxlength="12" class="form-control" value="{{:jgsx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 优先劣后受益权比例：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="yxlhsyqbl" maxlength="18" class="form-control digits" value="{{:yxlhsyqbl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 投资范围：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="tzfw" maxlength="20" class="form-control" value="{{:tzfw}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 管理方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="glfs" maxlength="40" class="form-control" value="{{:glfs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合作模式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hzms" maxlength="12" class="form-control" value="{{:hzms}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目合作来源：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmhzly" maxlength="20" class="form-control" value="{{:xmhzly}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合作机构编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hzjgbh" maxlength="40" class="form-control" value="{{:hzjgbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 合作机构名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="hzjgmc" maxlength="200" class="form-control" value="{{:hzjgmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否集团推荐：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfjttj" maxlength="3" class="form-control" value="{{:sfjttj}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 集团内部机构名称：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jtnbjgmc" maxlength="200" class="form-control" value="{{:jtnbjgmc}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否净值型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jfjzx" maxlength="3" class="form-control" value="{{:jfjzx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 净值评估频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jzpgpd" maxlength="10" class="form-control" value="{{:jzpgpd}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 净值披露频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jzplpd" maxlength="10" class="form-control" value="{{:jzplpd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 资产管理报告频度：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zcglbgpd" maxlength="10" class="form-control" value="{{:zcglbgpd}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托续存规模：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtxcgm" class="form-control number" value="{{:xtxcgm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托初始成立份额：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtcsclfe" class="form-control number" value="{{:xtcsclfe}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 初始认购价格：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="csrgjg" class="form-control number" value="{{:csrgjg}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 报酬计提方式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="bsjtfs" maxlength="8" class="form-control" value="{{:bsjtfs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托报酬率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xtbcl" class="form-control number" value="{{:xtbcl}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最低预期收益率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zdyqsyl" class="form-control number" value="{{:zdyqsyl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 最高预期收益率：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zgyqsyl" class="form-control number" value="{{:zgyqsyl}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 可提前终止：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ktqzz" maxlength="3" class="form-control" value="{{:ktqzz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 可赎回标识：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="kshbz" maxlength="3" class="form-control" value="{{:kshbz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 产品增信标识：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="cpzxbz" maxlength="3" class="form-control" value="{{:cpzxbz}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 增信机构类型：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zxjglx" maxlength="20" class="form-control" value="{{:zxjglx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 增信形式：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="zxxs" maxlength="12" class="form-control" value="{{:zxxs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 主推介地：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ztjd" maxlength="6" class="form-control" value="{{:ztjd}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目TOT标识：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmtotbs" maxlength="15" class="form-control" value="{{:xmtotbs}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 关联母项目：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="glmxm" maxlength="40" class="form-control" value="{{:glmxm}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 所属部门：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="ssbm" maxlength="40" class="form-control" value="{{:ssbm}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 信托经理编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmjlbh" maxlength="40" class="form-control" value="{{:xmjlbh}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 项目主管编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="xmzgbh" maxlength="40" class="form-control" value="{{:xmzgbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 结算币种：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="jsbz" maxlength="3" class="form-control" value="{{:jsbz}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否现金类：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfxjl" maxlength="3" class="form-control" value="{{:sfxjl}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 是否伞形：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="sfsx" maxlength="3" class="form-control" value="{{:sfsx}}" readonly />
					</div>
				</div>
			</div>
			<div class="col-xs-6">
				<div class="form-group">
					<label class="control-label col-sm-4" title="">
						<span class="required hide">*</span> 分管领导编号：<i class="fa icon-question hide"></i></label>
					<div class="col-sm-8">
						<input name="fgldbh" maxlength="40" class="form-control" value="{{:fgldbh}}" readonly />
					</div>
				</div>
			</div>
		</div>
		<input name="id" type="hidden"/>
    </script>
</head>
<body>
	<input id="scode" type="hidden" value="004001" />
	<%@ include file="./header.jsp" %>

    <div class="clearfix" id="main">
        <c:choose><c:when test="${sessionScope.currentOperator.roleName == 'user'}"><%@ include file="./menu.jsp" %></c:when><c:otherwise><%@ include file="./super-menu.jsp" %></c:otherwise></c:choose>
        <div id="content">
            <div class="box box-main">
        		<form id="inputForm" action="" method="post" class="form-horizontal">
					<input type="hidden" id="formType" name ="formType" value="TXmXtxmxx"/>
                    <div class="box-body">
                        <div class="row">
                            <ol class="breadcrumb">
                                <li>信托项目信息</li>
                                <li><a href="./TXmXtxmxxCheckList.jsp">数据审核</a></li>
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
